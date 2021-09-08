package net.ryanland.empire.sys.gameplay.combat.wave;

import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.sys.gameplay.combat.troop.Troop;

public class Wave {

    public static Troop[] getTroops(int wave) throws CommandException {
        switch (wave) {
            case 1 -> {
                return new Troop[]{
                    Troop.of("Recruit").set(1, 3)
                };
            }
            case 2 -> {
                return new Troop[]{
                    Troop.of("Recruit").set(1, 5)
                };
            }
            case 3 -> {
                return new Troop[]{
                    Troop.of("Recruit").set(1, 8)
                };
            }
            case 4 -> {
                return new Troop[]{
                    Troop.of("Recruit").set(1, 12)
                };
            }
            case 5 -> {
                return new Troop[]{
                    Troop.of("Recruit").set(2, 4)
                };
            }
            case 6 -> {
                return new Troop[]{
                    Troop.of("Recruit").set(2, 5),
                    Troop.of("Recruit").set(1, 3)
                };
            }
            case 7 -> {
                return new Troop[]{
                    Troop.of("Recruit").set(2, 6),
                    Troop.of("Recruit").set(1, 5)
                };
            }
            case 8 -> {
                return new Troop[]{
                    Troop.of("Recruit").set(2, 7),
                    Troop.of("Recruit").set(1, 8)
                };
            }
            case 9 -> {
                return new Troop[]{
                    Troop.of("Recruit").set(2, 8),
                    Troop.of("Recruit").set(1, 9)
                };
            }
            case 10 -> {
                return new Troop[]{
                    Troop.of("Recruit").set(2, 14)
                };
            }
            case 11 -> {
                return new Troop[]{
                    Troop.of("Recruit").set(2, 10),
                    Troop.of("Recruit").set(1, 5)
                };
            }
            case 12 -> {
                return new Troop[]{
                    Troop.of("Recruit").set(2, 10),
                    Troop.of("Recruit").set(1, 10)
                };
            }
        }

        throw new CommandException("This wave does not exist yet! Please come back later.");
    }

}
