package net.ryanland.empire.bot.command.impl.dev;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.EndlessStringArgument;
import net.ryanland.empire.bot.command.executor.data.Flag;
import net.ryanland.empire.bot.command.help.Category;
import net.ryanland.empire.bot.command.help.CommandInfo;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

public class EvalCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("eval")
                .aliases("evaluate", "java", "ev")
                .description("Evaluate Java code.")
                .category(Category.DEVELOPER)
                .permission(Permission.DEVELOPER)
                .flags(Flag.NO_DISABLE);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
                new EndlessStringArgument()
                    .id("code")
                    .description("Code to execute")
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

        PresetBuilder builder = new PresetBuilder()
            .addField("Code", String.format("```js\n%s```", code), true);

        code = IMPORTS.stream()
                .map(s -> String.format("import %s.*;", s))
                .collect(Collectors.joining(" ")) + " " + code;

        ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
        engine.put("jda", event.getJDA());
        engine.put("event", event);

        try {
            Object object = engine.eval(code); // Returns an object of the eval

            builder.setTitle("Eval Result")
                    .addField("Object Returned:", String.format("```js\n%s```", object), false);
            event.reply(builder).queue();

        } catch (Throwable e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String sStackTrace = sw.toString();

            builder.setTitle("Eval failed!");
            event.reply(builder).queue();
            event.getChannel().sendMessage(String.format("```%s```",
                    sStackTrace.length() >= 1500 ? sStackTrace.substring(0, 1500) : sStackTrace
            )).queue();
        }
    }
}
