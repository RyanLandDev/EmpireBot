package net.ryanland.empire.bot.command.arguments.types.impl.Enum;

import net.ryanland.empire.sys.file.database.documents.impl.UserDocument;

import java.util.function.BiFunction;
import java.util.function.Function;

public enum Balance implements EnumArgument.InputEnum {

    GOLD("gold", UserDocument::getGold, UserDocument::setGold),
    CRYSTALS("crystals", UserDocument::getCrystals, UserDocument::setCrystals),
    XP("xp", UserDocument::getXp, UserDocument::setXp)
    ;

    private final String name;
    private final Function<UserDocument, Integer> getter;
    private final BiFunction<UserDocument, Integer, UserDocument> setter;
    private final boolean hidden;

    Balance(String name,
            Function<UserDocument, Integer> getter,
            BiFunction<UserDocument, Integer, UserDocument> setter,
            boolean hidden) {
        this.name = name;
        this.getter = getter;
        this.setter = setter;
        this.hidden = hidden;
    }

    Balance(String name,
            Function<UserDocument, Integer> getter,
            BiFunction<UserDocument, Integer, UserDocument> setter) {
        this(name, getter, setter, false);
    }

    @Override
    public String getTitle() {
        return name;
    }

    public boolean getHidden() {
        return hidden;
    }

    public Function<UserDocument, Integer> getGetter() {
        return getter;
    }

    public BiFunction<UserDocument, Integer, UserDocument> getSetter() {
        return setter;
    }
}
