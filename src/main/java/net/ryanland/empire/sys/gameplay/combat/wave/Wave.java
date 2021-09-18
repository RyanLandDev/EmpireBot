package net.ryanland.empire.sys.gameplay.combat.wave;

import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.sys.gameplay.combat.troop.RecruitTroop;
import net.ryanland.empire.sys.gameplay.combat.troop.Troop;
import net.ryanland.empire.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

public class Wave {

    public static double WAVE_WIN_XP_MULTIPLIER = 1.5d;

    public static List<Troop> retrieveTroops(int wave) throws CommandException {
        List<Troop> troops = getTroops(wave);
        List<Troop> newTroops = new ArrayList<>(troops);

        for (Troop troop : troops) {
            if (RandomUtil.randomInt(0, 100) < troop.getRating()) {
                newTroops.add(Troop.of(troop.getBuilder()).get(0));
            }
        }

        return newTroops;
    }

    public static List<Troop> getTroops(int wave) throws CommandException {
        switch (wave) {
            case 1 -> {
                // Wave 1
                // - 3x recruit 1
                return Troop.of(RecruitTroop.class, 1, 3);
            }
            case 2 -> {
                // Wave 2
                // - 5x recruit 1
                return Troop.of(RecruitTroop.class, 1, 5);
            }
            case 3 -> {
                // Wave 3
                // - 8x recruit 1
                return Troop.of(RecruitTroop.class, 1, 8);
            }
            case 4 -> {
                // Wave 4
                // - 12x recruit 1
                return Troop.of(RecruitTroop.class, 1, 12);
            }
            case 5 -> {
                // Wave 5
                // - 10x recruit 2
                return Troop.of(RecruitTroop.class, 2, 10);
            }
            case 6 -> {
                // Wave 6
                // - 8x recruit 2
                // - 3x recruit 1
                return Troop.of(
                    new Troop.TroopBuilder(RecruitTroop.class, 2, 8),
                    new Troop.TroopBuilder(RecruitTroop.class, 1, 3)
                );
            }
            case 7 -> {
                // Wave 7
                // - 8x recruit 2
                // - 5x recruit 1
                return Troop.of(
                    new Troop.TroopBuilder(RecruitTroop.class, 2, 8),
                    new Troop.TroopBuilder(RecruitTroop.class, 1, 5)
                );
            }
            case 8 -> {
                // Wave 8
                // - 10x recruit 2
                // - 8x recruit 1
                return Troop.of(
                    new Troop.TroopBuilder(RecruitTroop.class, 2, 10),
                    new Troop.TroopBuilder(RecruitTroop.class, 1, 8)
                );
            }
            case 9 -> {
                // Wave 9
                // - 12x recruit 2
                // - 10x recruit 1
                return Troop.of(
                    new Troop.TroopBuilder(RecruitTroop.class, 2, 12),
                    new Troop.TroopBuilder(RecruitTroop.class, 1, 10)
                );
            }
            case 10 -> {
                // Wave 10
                // - 18x recruit 2
                return Troop.of(RecruitTroop.class, 2, 18);
            }
            case 11 -> {
                // Wave 11
                // - 14x recruit 3
                return Troop.of(RecruitTroop.class, 3, 14);
            }
            case 12 -> {
                // Wave 12
                // - 14x recruit 3
                // - 8x recruit 2
                return Troop.of(
                    new Troop.TroopBuilder(RecruitTroop.class, 3, 14),
                    new Troop.TroopBuilder(RecruitTroop.class, 2, 8)
                );
            }
        }

        throw new CommandException("This wave does not exist yet! Please come back later.");
    }

}
