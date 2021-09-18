package net.ryanland.empire.sys.gameplay.combat.troop;

public class SkeletonTroop extends Troop {

    @Override
    public String getName() {
        return "Skeleton";
    }

    @Override
    public String getEmoji() {
        return "🦴";
    }

    @Override
    public int getMaxHealth() {
        return (int) (Math.pow(stage, 1.4) * stage * 9 + 13);
    }

    @Override
    public int getDamage() {
        return 7 * stage + 9;
    }

    @Override
    public int getSpeedInMs() {
        return -50 * stage + 1230;
    }

    @Override
    public int getXp() {
        return 7 * stage + 14;
    }
}
