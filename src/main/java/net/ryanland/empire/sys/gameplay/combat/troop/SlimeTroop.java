package net.ryanland.empire.sys.gameplay.combat.troop;

public class SlimeTroop extends Troop {

    @Override
    public String getName() {
        return "Slime";
    }

    @Override
    public String getEmoji() {
        return "🥬";
    }

    @Override
    public int getMaxHealth() {
        return (int) (Math.pow(stage, 1.3) * stage * 9 + 16);
    }

    @Override
    public int getDamage() {
        return 5 * stage + 6;
    }

    @Override
    public int getSpeedInMs() {
        return -140 * stage + 1600;
    }

    @Override
    public int getXp() {
        return 5 * stage + 11;
    }
}
