package net.ryanland.empire.bot.command.arguments.Enum;

import net.ryanland.colossus.command.arguments.types.EnumArgument;
import net.ryanland.empire.sys.file.database.Profile;

import java.util.function.BiFunction;
import java.util.function.Function;

public enum Balance implements EnumArgument.InputEnum {

    GOLD("gold", profile -> profile.getGold().amount(), Profile::setGold),
    CRYSTALS("crystals", profile -> profile.getCrystals().amount(), Profile::setCrystals),
    XP("xp", Profile::getXp, Profile::setXp);

    private final String name;
    private final Function<Profile, Integer> getter;
    private final BiFunction<Profile, Integer, Profile> setter;
    private final boolean hidden;

    Balance(String name,
            Function<Profile, Integer> getter,
            BiFunction<Profile, Integer, Profile> setter,
            boolean hidden) {
        this.name = name;
        this.getter = getter;
        this.setter = setter;
        this.hidden = hidden;
    }

    Balance(String name,
            Function<Profile, Integer> getter,
            BiFunction<Profile, Integer, Profile> setter) {
        this(name, getter, setter, false);
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    public Function<Profile, Integer> getGetter() {
        return getter;
    }

    public BiFunction<Profile, Integer, Profile> getSetter() {
        return setter;
    }
}
