package net.ryanland.empire.bot.command.impl.dev;


import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.help.Category;
import net.ryanland.empire.bot.command.help.CommandData;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.permissions.Permission;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;


public class DevNamesCommand extends Command {

    @Override
    public CommandData getData() {
        return new CommandData()
                .name("devnames")
                .description("Lists the current developers' names.")
                .category(Category.DEVELOPER)
                .permission(Permission.DEVELOPER)
                .flags();
    }

    @Override
    public ArgumentSet getArguments() { return new ArgumentSet(); }

    @Override
    public void run(CommandEvent event) {
        // lol imagine reading json files (lame!)
        // can always add that though, just needs caching though so that the file isn't read from every invocation.
        event.reply(
                new PresetBuilder(
                        """
                                The current developers are;

                                • **Ryanland** (Owner)
                                • **Erobus**
                                • **General_Mudkip**
                        """)
                        .setTitle("Developers")
        );
    }
}
