package net.ryanland.empire.bot.command.impl.gameplay.combat;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.empire.bot.command.arguments.number.IntegerArgument;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.sys.gameplay.combat.troop.Troop;
import net.ryanland.empire.sys.message.builders.InfoValueCollection;
import net.ryanland.empire.util.NumberUtil;

import java.util.Deque;

import static net.ryanland.empire.util.NumberUtil.clean;

public class EnemyCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
            .name("enemy")
            .description("Displays information about an enemy.")
            .category(Category.COMBAT);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
            new Argument<Troop>() {
                @Override
                public Troop parseArg(Deque<OptionMapping> arguments, CommandEvent event) throws ArgumentException {
                    String name = arguments.pop().getAsString();
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
    public void run(CommandEvent event) throws CommandException {
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
