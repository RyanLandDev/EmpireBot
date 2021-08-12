package net.ryanland.empire.bot.command.impl.empire;

import net.dv8tion.jda.api.entities.User;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.help.Category;
import net.ryanland.empire.bot.command.help.CommandInfo;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.database.DocumentCache;
import net.ryanland.empire.sys.database.documents.impl.UserDocument;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;
import org.bson.Document;

import java.util.Date;

public class StartCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("start")
                .description("Initialize your profile!")
                .category(Category.EMPIRE);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void run(CommandEvent event) {
        final User USER = event.getUser();
        // TODO: - Check if user already has existing profile
        //       - Call initializeDefaults() from UserDocument
        UserDocument document = DocumentCache.get(event.getUser(), UserDocument.class, true);


        if (document != null) {
            event.reply(new PresetBuilder(PresetType.ERROR)
                    .setTitle("Error!")
                    .addField("You already have an account.","",false)
                    .addLogo()
            ).setEphemeral(true).queue();
        } else {
            UserDocument newDocument = new UserDocument(new Document("id", event.getUser().getId()));
            newDocument.setCreated(new Date());

            event.reply(new PresetBuilder(PresetType.SUCCESS)
                    .setTitle("Profile created!")
                    .addField("Access it with /profile.","",false)
                    .addLogo()
            ).setEphemeral(true).queue();
        }
    }
}
