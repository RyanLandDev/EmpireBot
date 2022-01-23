package net.ryanland.empire.sys.gameplay.building.impl.resource;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.Interaction;
import net.dv8tion.jda.api.interactions.components.Button;
import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.sys.interactions.menu.ActionMenuBuilder;
import net.ryanland.colossus.sys.message.DefaultPresetType;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.sys.gameplay.building.BuildingActionState;
import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.building.impl.Building;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoBuilder;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoElement;
import net.ryanland.empire.sys.gameplay.currency.Price;
import net.ryanland.empire.util.DateUtil;
import net.ryanland.empire.util.NumberUtil;
import net.ryanland.empire.util.RandomUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public abstract class ResourceGeneratorBuilding extends ResourceBuilding {

    protected Date lastCollect;

    @Override
    public Building deserialize(@NotNull List<?> data) {
        stage = (int) data.get(1);
        health = (int) data.get(2);
        lastCollect = (Date) data.get(3);
        return this;
    }

    @Override
    public Building defaults() {
        stage = BUILDING_START_STAGE;
        health = getMaxHealth();
        lastCollect = new Date();
        return this;
    }

    @Override
    public List<?> serialize(@NotNull Building building) {
        return Arrays.asList(building.getId(), building.getStage(), building.getHealth(),
            (Object) ((ResourceGeneratorBuilding) building).getLastCollect());
    }

    @Override
    public BuildingType getType() {
        return BuildingType.RESOURCE_GENERATOR;
    }

    @Override
    public BuildingInfoBuilder getBuildingInfoBuilder() {
        return super.getBuildingInfoBuilder().replaceSegment(1, segment -> segment
            .insertElement(0, BuildingInfoElement.upgradable(
                getEffectiveCurrency().getName() + " per minute", "🏭", getEffectiveCurrency().getEmoji(),
                getUnitPerMin(), getUnitPerMin(stage + 1),
                "The resources this building makes per minute.\n" + AIR +
                    " Time left to fill up: " +
                    DateUtil.formatRelative(new Date(Math.max(0,
                        (long) (getCapacity().amount() / getUnitPerMs().amount()) -
                            (System.currentTimeMillis() - lastCollect.getTime()))))
            ))
        );
    }

    @Override
    public ActionMenuBuilder getActionMenuBuilder() throws CommandException {
        return super.getActionMenuBuilder()
            .insertButton(0, 2,
                Button.secondary("collect", "Collect" +
                        (canCollect() ? "" : String.format(" (%s)", getCollectState().getName())))
                    .withEmoji(Emoji.fromMarkdown(getEffectiveCurrency().getEmoji()))
                    .withDisabled(!canCollect()),
                event -> {
                    Price<Integer> holding = getHolding();
                    int xp = getCollectXp();

                    executeButtonAction(event, () -> collect(xp, event, new PresetBuilder(DefaultPresetType.SUCCESS, String.format(
                        "Collected %s from %s.",
                        holding.format() + (xp > 0 ? String.format(" and %s %s", XP, NumberUtil.format(xp)) : ""),
                        format()
                    )).build()));

                    refreshMenu(event);
                }
            );
    }

    @Override
    public int getMinimumAllowed() {
        return 1;
    }

    public enum CollectState implements BuildingActionState {

        BROKEN("Broken", Building::isUsable),
        EMPTY("Empty", building -> ((ResourceGeneratorBuilding) building).getHolding().amount() > 0),
        CAPACITY_FULL("Capacity Full", building -> building.getProfile()
            .roomFor(((ResourceGeneratorBuilding) building).getHolding())),
        NOT_EXISTING("Not Existing", Building::exists),

        AVAILABLE("Available", building -> false);

        private final String name;
        private final Predicate<Building> check;

        CollectState(String name, Predicate<Building> check) {
            this.name = name;
            this.check = check;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Predicate<Building> getCheck() {
            return check;
        }
    }

    public CollectState getCollectState() {
        return BuildingActionState.get(this, CollectState.class);
    }

    public boolean canCollect() {
        return getCollectState() == CollectState.AVAILABLE;
    }

    public void collect(int xp, Interaction interaction, MessageEmbed... embeds) throws CommandException {
        if (!canCollect()) {
            throw new CommandException("You cannot collect from this building.");
        }

        getHolding().give(profile);
        profile.giveXp(xp, interaction, embeds);

        lastCollect = new Date();
        profile.setBuilding(this).update();
    }

    public int getCollectXp() {
        return (int) (
            (getHolding().amount() * getEffectiveCurrency().getCollectXpMultiplier())
                * RandomUtil.randomFloat(0.75f, 1.75f));
    }

    @Override
    public Price<Integer> getHolding() {
        if (!isUsable()) {
            return new Price<>(getEffectiveCurrency(), 0);
        }

        double unitPerMs = getUnitPerMs().amount();
        long msDiff = System.currentTimeMillis() - lastCollect.getTime();
        int holding = (int) (unitPerMs * msDiff);

        return new Price<>(getEffectiveCurrency(), Math.min(holding, getCapacity().amount()));
    }

    @Override
    public void repair() throws CommandException {
        if (!canRepair())
            throw new CommandException("You cannot repair this building.");

        if (!isUsable())
            lastCollect = new Date();

        getRepairPrice().buy(profile);
        health = getMaxHealth();
        profile.setBuilding(this).update();
    }

    @Override
    public void crystalRepair() throws CommandException {
        if (!canCrystalRepair())
            throw new CommandException("You cannot repair this building.");

        if (!isUsable())
            lastCollect = new Date();

        getCrystalRepairPrice().buy(profile);
        health = getMaxHealth();
        profile.setBuilding(this).update();
    }

    public Date getLastCollect() {
        return lastCollect;
    }

    public abstract Price<Integer> getUnitPerMin();

    public final Price<Integer> getUnitPerMin(int stage) {
        int originalStage = this.stage;
        this.stage = stage;

        Price<Integer> result = getUnitPerMin();
        this.stage = originalStage;

        return result;
    }

    public Price<Double> getUnitPerMs() {
        return new Price<>(getEffectiveCurrency(), (double) getUnitPerMin().amount() / 60000);
    }

    public final Price<Double> getUnitPerMs(int stage) {
        int originalStage = this.stage;
        this.stage = stage;

        Price<Double> result = getUnitPerMs();
        this.stage = originalStage;

        return result;
    }

}
