package net.ryanland.empire.bot.command.arguments.types.impl.Enum;

public enum Test implements EnumArgument.InputEnum {

    TEST_ONE("one", "lol one"),
    TEST_TWO("two", "lol two")
    ;

    private final String name;
    private final String extraThing;

    Test(String name, String extraThing) {
        this.name = name;
        this.extraThing = extraThing;
    }

    @Override
    public String getTitle() {
        return name;
    }

    public String getExtraThing() {
        return extraThing;
    }
}
