package net.ryanland.empire.bot.command.impl.gameplay.games;

import net.ryanland.colossus.command.CombinedCommand;
import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.command.annotations.CommandBuilder;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.command.arguments.parsing.exceptions.ArgumentException;
import net.ryanland.colossus.command.arguments.parsing.exceptions.MalformedArgumentException;
import net.ryanland.colossus.command.arguments.types.primitive.ArgumentStringResolver;
import net.ryanland.colossus.events.CommandEvent;
import net.ryanland.colossus.sys.message.DefaultPresetType;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.gameplay.currency.Price;
import net.ryanland.empire.util.RandomUtil;

@CommandBuilder(
    name = "gamble",
    description = "Bet gold, and get double or nothing."
)
public class GambleCommand extends GamesCommand implements CombinedCommand {

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
                new ArgumentStringResolver<Integer>() {
                    @Override
                    public Integer resolve(String input, CommandEvent event) throws ArgumentException {
                        if (input.equals("half"))
                            return (int) ((float) Profile.of(event).getGold().amount() / 2);
                        else if (input.equals("all"))
                            return Profile.of(event).getGold().amount();
                        else {
                            try {
                                return Integer.parseInt(input);
                            } catch (NumberFormatException e) {
                                throw new MalformedArgumentException("Invalid input. Must be a valid amount, 'half' or 'all'.");
                            }
                        }
                    }
                }
                    .id("amount")
                    .description("The amount to bet, or 'half'/'all'.")
            );
    }

    @Override
    public void execute(CommandEvent event) throws CommandException {
        int amount = event.getArgument("amount");
        Price<Integer> price = new Price<>(Currency.GOLD, amount);
        Profile profile = Profile.of(event);

        price.buy(profile);

        // Lose
        if (RandomUtil.randomInt(0, 1) == 0) {
            event.reply(new PresetBuilder(DefaultPresetType.ERROR,
                "You have lost " + price.format() + ".", "Loss"));
            // Win
        } else {
            price.give(profile);
            event.reply(new PresetBuilder(DefaultPresetType.SUCCESS,
                "You have won " + price.format() + ".", "Win"));
        }

        profile.update();
    }
}
