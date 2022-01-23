package net.ryanland.empire.sys.gameplay.currency;

import net.ryanland.colossus.command.CommandException;
import net.ryanland.empire.bot.command.exceptions.CannotAffordException;
import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.message.Formattable;
import net.ryanland.empire.util.NumberUtil;
import org.jetbrains.annotations.NotNull;

public record Price<T extends Number>(Currency currency, T amount) implements Formattable {

    @Override
    public String format() {
        return format(false);
    }

    public String format(boolean underlined) {
        return String.format("%s %3$s%s%3$s", currency.getEmoji(), formatAmount(), underlined ? "__" : "");
    }

    public @NotNull String formatAmount() {
        return NumberUtil.format(amount);
    }

    @SuppressWarnings("unchecked")
    public void give(Profile profile) throws CommandException {
        if (!profile.roomFor((Price<Integer>) this))
            throw new CommandException("You do not have enough capacity.");

        currency.update(profile, currency.get(profile).amount() + amount.intValue());
    }

    public Price<Integer> difference(Price<Integer> otherPrice) {
        return new Price<>(currency, (int) amount - otherPrice.amount());
    }

    @SuppressWarnings("unchecked")
    public void buy(Profile profile) throws CommandException {
        Price<Integer> price = (Price<Integer>) this;

        if (!profile.canAfford(price)) {
            throw new CannotAffordException(
                "You cannot afford this. You need " + price.difference(currency.get(profile)).format() +
                    " more.");
        }

        currency.update(profile, currency.get(profile).amount() - amount.intValue());
    }

}
