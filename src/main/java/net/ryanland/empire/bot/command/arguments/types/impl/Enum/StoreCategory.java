package net.ryanland.empire.bot.command.arguments.types.impl.Enum;

public enum StoreCategory implements EnumArgument.InputEnum {

    BUILDINGS("buildings")
    ;

    private final String title;
    private final boolean hidden;

    StoreCategory(String title) {
        this(title, false);
    }

    StoreCategory(String title, boolean hidden) {
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
