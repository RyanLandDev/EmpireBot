package net.ryanland.empire.sys.gameplay.combat.troop;

public class GoblinTroop extends Troop {

    @Override
    public String getName() {
        return "Goblin";
    }

    @Override
    public String getEmoji() {
        return "👹";
    }

    @Override
    public int getMaxHealth() {
        return (int) (Math.pow(stage, 1.2) * stage * 4 + 6);
    }

    @Override
    public int getDamage() {
        return 2 * stage + 3;
    }

    @Override
    public int getSpeedInMs() {
        return -50 * stage + 600;
    }

    @Override
    public int getXp() {
        return 4 * stage + 8;
    }
}
