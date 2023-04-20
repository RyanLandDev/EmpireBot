package net.ryanland.empire.bot.command.impl.profile;

import net.ryanland.colossus.command.BaseCommand;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.command.regular.CombinedCommand;
import net.ryanland.colossus.command.regular.CommandBuilder;
import net.ryanland.colossus.events.command.CommandEvent;
import net.ryanland.colossus.sys.message.DefaultPresetType;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.bot.command.inhibitor.RequiresProfileInhibitor;
import net.ryanland.empire.sys.file.database.Profile;

import java.util.Date;

@CommandBuilder(
    name = "start",
    description = "Start your Empire adventure."
)
public class StartCommand extends ProfileCommand implements CombinedCommand {

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void execute(CommandEvent event) {
        Profile profile = Profile.of(event);

        if (!RequiresProfileInhibitor.hasProfile(event.getUser())) {
            event.reply(new PresetBuilder(DefaultPresetType.ERROR, "You already have a profile.").addLogo());
        } else {
            ((Profile) profile
                .put(Profile.CREATION_DATE_KEY, new Date())
                .put(Profile.BUILDINGS_KEY, Profile.DEFAULT_BUILDINGS))
                .update();

            event.reply(
                new PresetBuilder(
                    DefaultPresetType.SUCCESS,
                    "Your profile has been created! Good luck!\n*It is recommended to use the /tutorial.*",
                    "Profile created")
                    .addLogo()
            );
        }
    }
}
