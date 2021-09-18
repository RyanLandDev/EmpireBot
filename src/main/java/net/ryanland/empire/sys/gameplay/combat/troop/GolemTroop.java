package net.ryanland.empire.sys.gameplay.combat.troop;

public class GolemTroop extends Troop {

    @Override
    public String getName() {
        return "Golem";
    }

    @Override
    public String getEmoji() {
        return ":man_superhero:";
    }

    @Override
    public int getMaxHealth() {
        return (int) (Math.pow(stage, 1.5) * 57 + 82);
    }

    @Override
    public int getDamage() {
        return 8 * stage + 19;
    }

    @Override
    public int getSpeed() {
        return -760 * stage + 9000;
    }

    @Override
    public int getXp() {
        return 22 * stage + 34;
    }
}
