package net.ryanland.empire.bot.command.impl.profile;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.file.database.DocumentCache;
import net.ryanland.empire.sys.file.database.documents.impl.UserDocument;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

import java.util.Date;

public class StartCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("start")
                .description("Start your Empire adventure.")
                .category(Category.PROFILE);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void run(CommandEvent event) {
        UserDocument document = DocumentCache.get(event.getUser(), UserDocument.class, true);

        if (document != null) {
            event.reply(
                    new PresetBuilder(PresetType.ERROR, "You already have a profile.")
                    .addLogo()
            );

        } else {
            event.getUserDocument()
                    .setCreated(new Date())
                    .update();

            event.reply(
                    new PresetBuilder(
                            PresetType.SUCCESS,
                            "Your profile has been created! Good luck!\n*It is recommended to use the /tutorial.*",
                            "Profile created")
                    .addLogo()
            );
        }
    }
}
