package net.ryanland.empire.bot.command.impl.gameplay.combat;

import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.command.regular.CombinedCommand;
import net.ryanland.colossus.command.regular.CommandBuilder;
import net.ryanland.colossus.events.command.CommandEvent;
import net.ryanland.colossus.events.command.MessageCommandEvent;
import net.ryanland.colossus.events.command.SlashCommandEvent;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.gameplay.action.BuffedAction;
import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.building.impl.Building;
import net.ryanland.empire.sys.gameplay.building.impl.defense.DefenseRangedBuilding;
import net.ryanland.empire.sys.gameplay.collectible.potion.DefenseBuildingDamagePotion;
import net.ryanland.empire.sys.gameplay.collectible.potion.Potion;
import net.ryanland.empire.sys.gameplay.combat.team.Team;
import net.ryanland.empire.sys.gameplay.combat.troop.Troop;
import net.ryanland.empire.sys.gameplay.combat.wave.Wave;
import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.gameplay.currency.Price;
import net.ryanland.empire.sys.message.builders.Preset;
import net.ryanland.empire.util.NumberUtil;

import java.util.List;

@CommandBuilder(
    name = "newwave",
    description = "Starts a new enemy wave."
)
public class NewWaveCommand extends CombatCommand implements CombinedCommand {

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void execute(CommandEvent event) throws CommandException {
        debug(System.currentTimeMillis());

        // Get the user's profile
        Profile profile = getProfile(event);

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
            double defenseDmgPerMs = getDefenseDmgPerMs(building.getLayer(), buildings, profile);
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

                    debug("attackDmgPerMs " + attackDmgPerMs);
                    debug("defenseDmgPerMs " + defenseDmgPerMs);
                    debug("msToDestroyBuilding " + msToDestroyBuilding);
                    debug("msToKillTroop " + msToKillTroop);

                    // Damage this troop based on the amount of time passed
                    int troopDamage = Math.min((int) (msPassed / msToKillTroop * troop.getHealth()), troop.getHealth());
                    damageDealt += troopDamage;
                    if (troopDamage == troop.getHealth()) {
                        troopsKilled++;
                        xpEarned += troop.getXp();
                        debug("TROOP KILLED");
                    }
                    debug("troopDamage " + troopDamage);
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
                debug("buildingDamage " + buildingDamage);
                if (destroyed) {
                    debug("BUILDING DESTROYED");
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
            profile.setWave(wave + 1);
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
        PresetBuilder embed = new PresetBuilder(win ? Preset.SUCCESS : Preset.ERROR,
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
        if (event instanceof MessageCommandEvent)
            profile.giveXp((int) xpEarned, ((MessageCommandEvent) event).getMessage(), embed.embed());
        else
            profile.giveXp((int) xpEarned, ((SlashCommandEvent) event).getInteraction(), false, embed.embed());

        // Update user document
        profile.setBuildings(buildings).update();

        debug(System.currentTimeMillis());
    }

    private static double getAttackDmgPerMs(List<Troop> troops) {
        return troops.stream()
            .mapToDouble(troop -> (double) troop.getDamage() / troop.getSpeedInMs())
            .sum();
    }

    private static double getDefenseDmgPerMs(int layer, List<Building> buildings, Profile profile) {
        return new BuffedAction<Double>() {
            @Override
            public Potion getPotion() {
                return new DefenseBuildingDamagePotion();
            }

            @Override
            public Double run(Profile profile) {
                return buildings.stream()
                    .filter(building -> building.getType() == BuildingType.DEFENSE_RANGED && building.isUsable())
                    .map(building -> (DefenseRangedBuilding) building)
                    .filter(building -> NumberUtil.inRange(layer,
                        building.getLayer() - building.getRange(), building.getLayer() + building.getRange()))
                    .mapToDouble(building -> (double) building.getDamage() / building.getSpeedInMs())
                    .sum() * multiplier;
            }
        }.perform(profile);
    }

}
