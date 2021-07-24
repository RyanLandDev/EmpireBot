package net.ryanland.empire.bot.command.impl.dev;

import net.dv8tion.jda.api.EmbedBuilder;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.EndlessStringArgument;
import net.ryanland.empire.bot.command.help.Category;
import net.ryanland.empire.bot.command.help.CommandData;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

public class EvalCommand extends Command {

    @Override
    public CommandData getData() {
        return new CommandData()
                .name("eval")
                .aliases("evaluate", "java", "ev")
                .description("Evaluate Java code.")
                .category(Category.DEVELOPER)
                .permission(Permission.DEVELOPER);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
                new EndlessStringArgument()
                    .id("code")
        );
    }

    private static final List<String> IMPORTS = List.of(
            "net.dv8tion.jda.core",
            "net.dv8tion.jda.core.managers",
            "net.dv8tion.jda.core.entities",
            "net.dv8tion.jda.core.entities.impl",
            "net.dv8tion.jda.core.utils",

            "net.ryanland.empire.Empire"
    );

    @Override
    public void run(CommandEvent event) {
        String code = event.getArgument("code");

        ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
        engine.put("jda", event.getJDA());
        engine.put("event", event);

        EmbedBuilder builder = new PresetBuilder(PresetType.DEFAULT).builder();
        builder.addField("Code", String.format("```js\n%s```", code), true);

        code = IMPORTS.stream().map(s -> "import " + s + ".*;").collect(Collectors.joining(" ")) + " " + code;

        try {
            Object object = engine.eval(code); // Returns an object of the eval

            builder.setTitle("Eval Result");
            builder.addField("Object Returned:", String.format("```js\n%s```", object), false);
            event.getChannel().sendMessage(builder.build()).queue();

        } catch (Throwable e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String sStackTrace = sw.toString();

            builder.setTitle("Eval failed!");
            event.getChannel().sendMessage(builder.build()).queue();
            event.getChannel().sendMessage(String.format("```%s```", sStackTrace.length() >= 1500 ? sStackTrace.substring(0, 1500) : sStackTrace)).queue();
        }
    }
}
