package net.ryanland.empire.sys.gameplay.combat.troop;

public class GiantTroop extends Troop {

    @Override
    public String getName() {
        return "Giant";
    }

    @Override
    public String getEmoji() {
        return "🦒";
    }

    @Override
    public int getMaxHealth() {
        return (int) (Math.pow(stage, 1.5) * 37 + 52);
    }

    @Override
    public int getDamage() {
        return 7 * stage + 18;
    }

    @Override
    public int getSpeed() {
        return -350 * stage + 4000;
    }

    @Override
    public int getXp() {
        return 19 * stage + 28;
    }
}
