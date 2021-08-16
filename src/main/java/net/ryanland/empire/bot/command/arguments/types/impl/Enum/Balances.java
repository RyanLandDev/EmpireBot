package net.ryanland.empire.bot.command.arguments.types.impl.Enum;

public enum Balances implements EnumArgument.InputEnum {

    GOLD("gold"),
    CRYSTALS("crystals"),
    XP("xp")
    ;

    private final String name;

    Balances(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
