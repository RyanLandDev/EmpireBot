package net.ryanland.empire.bot.command.arguments.types.impl.Enum;

public enum Claim implements EnumArgument.InputEnum {

    HOURLY("Hourly"),
    DAILY("Daily"),
    MEMBER("Member")
    ;

    private final String title;
    private final boolean hidden;

    Claim(String title) {
        this(title, false);
    }

    Claim(String title, boolean hidden) {
        this.title = title;
        this.hidden = hidden;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }
}
