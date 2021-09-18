package net.ryanland.empire.sys.gameplay.combat.troop;

public class RecruitTroop extends Troop {

    @Override
    public String getName() {
        return "Recruit";
    }

    @Override
    public String getEmoji() {
        return "🤵";
    }

    @Override
    public int getMaxHealth() {
        return (int) (Math.pow(stage, 1.3) * 7 + 7);
    }

    @Override
    public int getDamage() {
        return (int) (2.5 * stage + 4);
    }

    @Override
    public int getSpeed() {
        return -180 * stage + 1100;
    }

    @Override
    public int getXp() {
        return 13 * stage + 15;
    }
}
