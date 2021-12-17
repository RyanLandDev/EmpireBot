package net.ryanland.empire.bot.command.impl.gameplay.games;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.ryanland.empire.bot.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.empire.bot.command.arguments.number.IntegerArgument;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.gameplay.currency.Price;
import net.ryanland.empire.sys.message.builders.PresetType;
import net.ryanland.empire.util.RandomUtil;

public class GambleCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
            .name("gamble")
            .description("Bet gold, and get double or nothing.")
            .category(Category.GAMES)
            .requiresProfile();
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet()
            .addArguments(
                new IntegerArgument() {
                    @Override
                    public OptionType getType() {
                        return OptionType.STRING;
                    }
                }
                    .min(1)
                    .id("amount")
                    .description("The amount to bet, or 'half'/'all'.")
                    .fallback((options, event) -> {
                        switch (options.pop().getAsString().toLowerCase()) {
                            case "half" -> {
                                return (int) ((float) event.getProfile().getGold().amount() / 2);
                            }
                            case "all" -> {
                                return event.getProfile().getGold().amount();
                            }
                            default -> throw new MalformedArgumentException("Invalid input.");
                        }
                    })
            );
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        int amount = event.getArgument("amount");
        Price<Integer> price = new Price<>(Currency.GOLD, amount);

        price.buy(event.getProfile());

        // Lose
        if (RandomUtil.randomInt(0, 1) == 0) {
            event.reply(new PresetBuilder(PresetType.ERROR,
                "You have lost " + price.format() + ".", "Loss"));
            // Win
        } else {
            price.give(event.getProfile());
            event.reply(new PresetBuilder(PresetType.SUCCESS,
                "You have won " + price.format() + ".", "Win"));
        }

        event.getProfile().getDocument().update();
    }
}
