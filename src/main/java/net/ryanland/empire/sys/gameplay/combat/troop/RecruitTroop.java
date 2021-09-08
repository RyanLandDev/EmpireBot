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
    public int getHealth() {
        return 10;
    }

    @Override
    public int getDamage() {
        return (int) (1.25 * (stage - 1) + 4);
    }

    @Override
    public int getSpeedInMs() {
        return -120 * (stage - 1) + 1100;
    }

    @Override
    public int getXp() {
        return (int) (8.3 * (stage - 1) + 25);
    }
}
