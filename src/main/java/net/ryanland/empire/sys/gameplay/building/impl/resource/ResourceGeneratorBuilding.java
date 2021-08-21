package net.ryanland.empire.sys.gameplay.building.impl.resource;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.interactions.components.Button;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.sys.gameplay.building.BuildingActionState;
import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.building.impl.Building;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoBuilder;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoElement;
import net.ryanland.empire.sys.gameplay.currency.Price;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;
import net.ryanland.empire.sys.message.interactions.menu.action.ActionMenuBuilder;
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
                        "The resources this building makes per minute."))
        );
    }

    @Override
    public ActionMenuBuilder getActionMenuBuilder() throws CommandException {
        return super.getActionMenuBuilder()
                .insertButton(2,
                        Button.secondary("collect", "Collect" +
                                        (canCollect() ? "" : String.format(" (%s)", getCollectState().getName())))
                                .withEmoji(Emoji.fromMarkdown(getEffectiveCurrency().getEmoji()))
                                .withDisabled(!canCollect()),
                        (event, aBuilding) -> {
                            ResourceGeneratorBuilding building = (ResourceGeneratorBuilding) aBuilding;
                            Price<Integer> holding = building.getHolding();

                            building.collect();

                            refreshMenu(event);
                            event.replyEmbeds(new PresetBuilder(PresetType.SUCCESS, String.format(
                                    "Collected %s from %s.", holding.format(), building.getFormattedName()
                            )).build()).setEphemeral(true).queue();

                        }, this
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

        AVAILABLE("Available", building -> false)
        ;

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

    public void collect() throws CommandException {
        if (!canCollect()) {
            throw new CommandException("You cannot collect from this building.");
        }

        getHolding().give(profile);
        lastCollect = new Date();
        profile.setBuilding(this);
        profile.getDocument().update();
    }

    @Override
    public Price<Integer> getHolding() {
        if (!isUsable()) {
            return new Price<>(getEffectiveCurrency(), 0);
        }

        double unitPerMs = getUnitPerMs().amount();
        long msDiff = new Date().getTime() - lastCollect.getTime();
        int holding = (int) Math.floor(unitPerMs * msDiff);

        return new Price<>(getEffectiveCurrency(), Math.min(holding, getCapacityInt()));
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
