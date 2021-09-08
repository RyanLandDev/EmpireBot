package net.ryanland.empire.sys.gameplay.combat.team;

public enum Team {

    ATTACK("Attack"),
    DEFENSE("Defense");

    private final String name;

    Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
