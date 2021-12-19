package net.ryanland.empire.bot.command.impl.gameplay.combat;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.ryanland.colossus.command.CombinedCommand;
import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.command.annotations.CommandBuilder;
import net.ryanland.colossus.command.arguments.Argument;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.colossus.command.arguments.types.primitive.IntegerArgument;
import net.ryanland.colossus.events.CommandEvent;
import net.ryanland.colossus.events.MessageCommandEvent;
import net.ryanland.colossus.events.SlashEvent;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.sys.gameplay.combat.troop.Troop;
import net.ryanland.empire.sys.message.builders.InfoValueCollection;
import net.ryanland.empire.util.NumberUtil;

import java.util.Deque;

import static net.ryanland.empire.util.NumberUtil.clean;

@CommandBuilder(
    name = "enemy",
    description = "Displays information about an enemy."
)
public class EnemyCommand extends CombatCommand implements CombinedCommand {

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
            new Argument<Troop>() {
                @Override
                public OptionType getSlashCommandOptionType() {
                    return OptionType.STRING;
                }

                @Override
                public Troop resolveSlashCommandArgument(Deque<OptionMapping> args, SlashEvent event) throws ArgumentException {
                    String name = args.pop().getAsString();
                    return Troop.find(name);
                }

                @Override
                public Troop resolveMessageCommandArgument(Deque<String> args, MessageCommandEvent event) throws ArgumentException {
                    String name = args.pop();
                    return Troop.find(name);
                }
            }
                .id("troop")
                .description("The troop to get information of."),
            new IntegerArgument()
                .min(1)
                .id("stage")
                .description("The stage of the troop.")
        );
    }

    @Override
    public void execute(CommandEvent event) throws CommandException {
        Troop troop = event.getArgument("troop");
        troop.set(event.getArgument("stage"));

        InfoValueCollection infos = new InfoValueCollection()
            .addRegular("Stage", STAGE, troop.getStage())
            .addEmpty()
            .addUpgradable("Health", HEALTH, troop.getMaxHealth(), troop.getMaxHealth(troop.getStage() + 1))
            .addUpgradable("Damage", DAMAGE, troop.getDamage(), troop.getDamage(troop.getStage() + 1))
            .addUpgradable("Speed", SPEED,
                clean(NumberUtil.round(0.1, ((double) troop.getSpeedInMs()) / 1000)) + "s",
                clean(NumberUtil.round(0.1, ((double) troop.getSpeedInMs(troop.getStage() + 1) / 1000))) + "s")
            .addUpgradable("XP", XP, troop.getXp(), troop.getXp(troop.getStage() + 1))
            ;

        event.reply(new PresetBuilder(infos.build(), troop.getEmoji() + " " + troop.getName()));
    }
}
