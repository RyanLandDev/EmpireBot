package net.ryanland.empire.bot.command.impl.gameplay.combat;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.file.serializer.BuildingsSerializer;
import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.building.impl.Building;
import net.ryanland.empire.sys.gameplay.building.impl.defense.DefenseRangedBuilding;
import net.ryanland.empire.sys.gameplay.combat.team.Team;
import net.ryanland.empire.sys.gameplay.combat.troop.Troop;
import net.ryanland.empire.sys.gameplay.combat.wave.Wave;
import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.gameplay.currency.Price;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;
import net.ryanland.empire.util.NumberUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewWaveCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
            .name("newwave")
            .description("Starts a new enemy wave.")
            .category(Category.COMBAT)
            .requiresProfile();
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        System.out.println(System.currentTimeMillis());

        // Get the user's profile
        Profile profile = event.getProfile();

        // Get the profile's wave
        int wave = profile.getWave();

        // Get the troops associated with the current wave
        List<Troop> troops = Wave.retrieveTroops(wave);

        // Get the profile's buildings
        List<Building> buildings = profile.getBuildings();

        // Initialize statistic variables
        // The amount of buildings the attacking team has destroyed
        int buildingsDestroyed = 0;
        // The amount of troops the defending team has killed
        int troopsKilled = 0;
        // The amount of health the defending team has lost from their buildings
        int damageTaken = 0;
        // The amount of health the defending team has dealt to the attacking team's troops
        int damageDealt = 0;
        // The amount of time that has passed in milliseconds
        int totalMsPassed = 0;
        // The amount of XP the defending team has earned by killing troops
        double xpEarned = 0;
        // The team that has won this game, if this is not updated the attacking team has won
        Team winningTeam = Team.ATTACK;

        // Iterate over every building, working through it as attacking team
        for (Building building : buildings) {

            // Amount of damage defense deals per millisecond
            double defenseDmgPerMs = getDefenseDmgPerMs(building.getLayer(), buildings);
            // Whether the building has been destroyed or not
            boolean destroyed;

            for (Troop troop : troops) {
                // Skip this troop if it is inactive
                if (!troop.isActive()) continue;

                double msPassed = 0;

                // Calculate the amount of attack damage dealt to the defending team per millisecond
                double attackDmgPerMs = getAttackDmgPerMs(troops);
                // Calculate the amount of milliseconds the attacking team will take to destroy this building
                double msToDestroyBuilding = building.getHealth() / attackDmgPerMs;

                // Increase the amount of time passed by the amount of time it takes defense to kill this troop,
                // or by the amount of time it takes the troops to kill defense in case defense does not deal any
                // damage on this layer
                if (defenseDmgPerMs == 0) {
                    msPassed += msToDestroyBuilding;
                } else {
                    double msToKillTroop = troop.getHealth() / defenseDmgPerMs;
                    msPassed += msToKillTroop;

                    System.out.println("attackDmgPerMs " + attackDmgPerMs);
                    System.out.println("defenseDmgPerMs " + defenseDmgPerMs);
                    System.out.println("msToDestroyBuilding " + msToDestroyBuilding);
                    System.out.println("msToKillTroop " + msToKillTroop);

                    // Damage this troop based on the amount of time passed
                    int troopDamage = Math.min((int) (msPassed / msToKillTroop * troop.getHealth()), troop.getHealth());
                    damageDealt += troopDamage;
                    if (troopDamage == troop.getHealth()) {
                        troopsKilled++;
                        xpEarned += troop.getXp();
                        System.out.println("TROOP KILLED");
                    }
                    System.out.println("troopDamage " + troopDamage);
                    troop.damage(troopDamage);
                }

                // Update statistics
                totalMsPassed += msPassed;

                // Damage this building based on the amount of time passed
                int buildingDamage =
                    Math.min((int) (msPassed / msToDestroyBuilding * building.getHealth()), building.getHealth());
                damageTaken += buildingDamage;
                destroyed = buildingDamage == building.getHealth();
                building.damage(buildingDamage);
                System.out.println("buildingDamage " + buildingDamage);
                if (destroyed) {
                    System.out.println("BUILDING DESTROYED");
                    buildingsDestroyed++;
                    // The building can't be destroyed twice, so break the loop and move on to the next building
                    break;
                }

            }

            // Check if no troops are active, meaning the defending team has won
            if (troops.stream().noneMatch(Troop::isActive)) {
                winningTeam = Team.DEFENSE;
                break;
            }
        }

        // Give rewards and send the game's result

        // Has the player won?
        boolean win = winningTeam == Team.DEFENSE;

        // Increase the profile's wave number if the game was won
        if (win) {
            profile.getDocument().setWave(wave + 1);
        }

        // Reward gold
        // Set the amount of gold earned to zero, if the user lost this will be the reward
        Price<Integer> goldEarned = new Price<>(Currency.GOLD, 0);
        // Indicates the gold was not claimed due to the profile not having enough capacity
        boolean notEnoughGoldCapacity = false;
        if (win) {
            goldEarned = profile.getWaveGoldReward();
            // Try giving it, if there is an error (such as not having enough capacity) it is caught
            try {
                goldEarned.give(profile);
            } catch (CommandException e) {
                goldEarned = new Price<>(Currency.GOLD, 0);
                notEnoughGoldCapacity = true;
            }
        }

        // Calculate xp
        // You cannot get decimals of xp, so floor the value
        xpEarned = Math.floor(xpEarned);
        // Multiply the xp earned if the user won the game
        if (win) {
            xpEarned *= Wave.WAVE_WIN_XP_MULTIPLIER;
        }

        // Build embed
        PresetBuilder embed = new PresetBuilder(win ? PresetType.SUCCESS : PresetType.ERROR,
            "You have **" + (win ? "won" : "lost") + "** the match!\n\u200b")

            .setEphemeral(false)
            .setTitle((win ? "Win" : "Loss") + ": Wave " + wave)
            .addLogo()

            .addField(":stopwatch: Match time",
                NumberUtil.clean(NumberUtil.round(0.1, (double) totalMsPassed / 1000)) + "s", true)
            .addField(":drop_of_blood: Damage taken",
                damageTaken + " health", true)
            .addField(":axe: Damage dealt",
                (int) (damageDealt) + " health", true)
            .addField(":house_abandoned: Buildings destroyed",
                buildingsDestroyed + " building" + (buildingsDestroyed == 1 ? "" : "s"), true)
            .addField(":skull: Troops killed",
                troopsKilled + " kill" + (troopsKilled == 1 ? "" : "s"), true)
            .addField(":ribbon: Rewards",
                XP + " **+" + (int) (xpEarned) + "** XP" + (win ? " (x" + Wave.WAVE_WIN_XP_MULTIPLIER + ")" : "")
                    + " and " + goldEarned.format() + (notEnoughGoldCapacity ? " (Not enough capacity)" : ""), true);

        // Give the xp
        profile.giveXp((int) xpEarned, event.getInteraction(), false, embed.build());

        // Update user document
        profile.getDocument().setBuildingsRaw(BuildingsSerializer.getInstance().serialize(buildings));
        profile.getDocument().update();

        System.out.println(System.currentTimeMillis());
    }

    private static double getAttackDmgPerMs(List<Troop> troops) {
        return troops.stream()
            .mapToDouble(troop -> (double) troop.getDamage() / troop.getSpeedInMs())
            .sum();
    }

    private static double getDefenseDmgPerMs(int layer, List<Building> buildings) {
        return buildings.stream()
            .filter(building -> building.getType() == BuildingType.DEFENSE_RANGED && building.isUsable())
            .map(building -> (DefenseRangedBuilding) building)
            .filter(building -> NumberUtil.inRange(layer,
                building.getLayer() - building.getRange(), building.getLayer() + building.getRange()))
            .mapToDouble(building -> (double) building.getDamage() / building.getSpeedInMs())
            .sum();
    }

}
