package net.ryanland.empire.sys.gameplay.building.impl;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.components.Button;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.file.serializer.Serializer;
import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.building.impl.defense.ranged.ArcherBuilding;
import net.ryanland.empire.sys.gameplay.building.impl.defense.thorned.WallBuilding;
import net.ryanland.empire.sys.gameplay.building.impl.resource.generator.GoldMineBuilding;
import net.ryanland.empire.sys.gameplay.building.impl.resource.storage.BankBuilding;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfo;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoBuilder;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoElement;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoSegmentBuilder;
import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.gameplay.currency.Price;
import net.ryanland.empire.sys.message.Emojis;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;
import net.ryanland.empire.sys.message.interactions.InteractionUtil;
import net.ryanland.empire.sys.message.interactions.menu.InteractionMenuBuilder;
import net.ryanland.empire.sys.message.interactions.menu.action.ActionButton;
import net.ryanland.empire.sys.message.interactions.menu.action.ActionMenu;
import net.ryanland.empire.sys.message.interactions.menu.action.ActionMenuBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class Building
        implements Serializable, Serializer<List<?>, Building>, Emojis {

    public static final int BUILDING_START_STAGE = 1;
    public static final int BASE_MAX_HEALTH = 100;
    public static final int USABLE_HEALTH = 75;
    public static final int LAYOUT_DISPLAY_PER_ROW = 7;

    @SuppressWarnings("all")
    private static final Building[] BUILDINGS = new Building[]{

            // Defense Ranged
            new ArcherBuilding(),
            // Defense Thorned
            new WallBuilding(),

            // Resource Generator
            new GoldMineBuilding(),
            // Resource Storage
            new BankBuilding()

    };

    private static final HashMap<String, Building> NAME_BUILDINGS = new HashMap<>(
            Arrays.stream(BUILDINGS)
                    .collect(Collectors.toMap(Building::getName, Function.identity()))
    );

    private static final HashMap<Integer, Building> ID_BUILDINGS = new HashMap<>(
            Arrays.stream(BUILDINGS)
                    .collect(Collectors.toMap(Building::getId, Function.identity()))
    );

    protected int stage;
    protected int health;

    protected int layer;
    protected Profile profile;

    @Override
    public Building deserialize(@NotNull List<?> data) {
        stage = (int) data.get(1);
        health = (int) data.get(2);
        return this;
    }

    public Building defaults() {
        stage = BUILDING_START_STAGE;
        health = getMaxHealth();
        return this;
    }

    public final List<?> serialize() {
        return serialize(this);
    }

    @Override
    public List<?> serialize(@NotNull Building building) {
        return Arrays.asList(building.getId(), building.getStage(), building.getHealth());
    }

    public final Building setProfile(Profile profile) {
        this.profile = profile;
        return this;
    }

    public final Building setLayer(int layer) {
        this.layer = layer;
        return this;
    }

    public int getStage() {
        return stage;
    }

    public int getHealth() {
        return health;
    }

    public int getLayer() {
        return layer;
    }

    public Profile getProfile() {
        return profile;
    }

    public int getMaxHealth() {
        return BASE_MAX_HEALTH;
    }

    public boolean isHealthMaxed() {
        return getHealth() >= getMaxHealth();
    }

    public boolean isUsable() {
        return health >= USABLE_HEALTH;
    }

    public final BuildingInfo getBuildingInfo() {
        return getBuildingInfoBuilder().build();
    }

    public BuildingInfoBuilder getBuildingInfoBuilder() {
        return new BuildingInfoBuilder().setBuilding(this)
                .addSegment(new BuildingInfoSegmentBuilder()
                    .addElement("Layer", "🏘", getLayer(), String.format(
                            "Move this building to another layer using `/move %s <new layer>`.", getLayer()))
                    .addElement("Stage", "🧱", getStage(), String.format(
                            "Upgrade this building to __Stage %s__ for %s.", getStage() + 1, getUpgradePrice().format()))
                    .addElement(BuildingInfoElement.capacitable("Health", "❤",
                            getHealth(), getMaxHealth(),
                            isHealthMaxed() ? "The building's health may go down when under attack." : String.format(
                                            "Repair this building for %s or %s.",
                                            getRepairPrice().format(true), getCrystalRepairPrice().format(true))))
                )
                .addSegment(new BuildingInfoSegmentBuilder()
                    .addElement("Sell Price", "💵", getSellPrice(),
                            "Sell this building (irreversible).\n\u200b")
                );
    }

    public boolean exists() {
        try {
            return getProfile().getBuilding(layer).equals(this);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public ActionMenuBuilder getActionMenuBuilder() throws CommandException {
        ActionMenuBuilder builder = new ActionMenuBuilder();

        if (canRepairAny()) {
            builder.addButton(
                    Button.success("repairMain", String.format("Repair (%s)",
                                    canRepair() ? getRepairPrice().currency().getName() : "Not Enough"))
                            .withEmoji(Emoji.fromMarkdown(getMainCurrency().getEmoji()))
                            .withDisabled(!canRepair()),

                    (event, aBuilding) -> {
                        Building building = (Building) aBuilding;
                        building.repair();

                        refreshMenu(event);
                        event.replyEmbeds(new PresetBuilder(PresetType.SUCCESS, String.format(
                                "Repaired your %s for %s.", building.getFormattedName(), building.getRepairPrice().format()
                        )).build()).setEphemeral(true).queue();

                    }, this
            ).addButton(
                    Button.success("repairCrystal", String.format("Repair (%s)",
                                    canCrystalRepair() ? getCrystalRepairPrice().currency().getName() : "Not Enough"))
                            .withEmoji(Emoji.fromMarkdown(getCrystalRepairPrice().currency().getEmoji()))
                            .withDisabled(!canCrystalRepair()),

                    (event, aBuilding) -> {
                        Building building = (Building) aBuilding;
                        building.crystalRepair();

                        refreshMenu(event);
                        event.replyEmbeds(new PresetBuilder(PresetType.SUCCESS, String.format(
                                "Repaired your %s for %s.", building.getFormattedName(), building.getCrystalRepairPrice().format()
                        )).build()).setEphemeral(true).queue();

                    }, this
            );
        } else {
            builder.addButton(
                    Button.secondary("repair", "Repair (Maxed)")
                            .withEmoji(Emoji.fromMarkdown(REPAIR))
                            .asDisabled(),
                    event -> {}
            );
        }

        builder.addButton(
                Button.secondary("upgrade", "Upgrade" + (canUpgrade() ? "" : " (Not Enough)"))
                        .withEmoji(Emoji.fromMarkdown(UPGRADE))
                        .withDisabled(!canUpgrade()),

                (event, aBuilding) -> {
                    Building building = (Building) aBuilding;
                    building.upgrade();

                    refreshMenu(event);
                    event.replyEmbeds(new PresetBuilder(PresetType.SUCCESS, String.format(
                            "Upgraded your %s for %s.", building.getFormattedName(), building.getUpgradePrice().format()
                    )).build()).setEphemeral(true).queue();

                }, this
        ).addButton(
                Button.secondary("sell", "Sell")
                        .withEmoji(Emoji.fromMarkdown(SELL)),

                (event, aBuilding) -> {
                    Building building = (Building) aBuilding;
                    building.sell();

                    refreshMenu(event);
                    event.deferReply(true).addEmbeds(new PresetBuilder(PresetType.SUCCESS, String.format(
                            "Sold your %s for %s.", building.getFormattedName(), building.getSellPrice().format()
                    )).build()).queue();

                }, this
        );

        return builder;
    }

    public InteractionMenuBuilder<ActionMenu> getMenuBuilder() throws CommandException {
        if (!exists()) {
            return new ActionMenuBuilder()
                    .setEmbed(new PresetBuilder("This building does not exist anymore."));
        }
        return getActionMenuBuilder()
                .setEmbed(getBuildingInfo().build()
                        .setTitle(String.format("%s %s (%s)", getEmoji(), getName(), getType().getFullName()))
                );
        // TODO test repair buttons
    }

    @SuppressWarnings("all")
    public void refreshMenu(ButtonClickEvent event) throws CommandException {
        event.getMessage().editMessageEmbeds(((ActionMenuBuilder) getMenuBuilder()).getEmbed().build()).setActionRows(
                exists() ? InteractionUtil.of(getActionMenuBuilder().getButtons().stream()
                        .map(ActionButton::button)
                        .collect(Collectors.toList()))
                        : Collections.emptyList()
                ).queue();
    }

    public void repair() throws CommandException {
        if (!canRepair()) {
            throw new CommandException("You cannot repair this building.");
        }

        getRepairPrice().buy(profile);
        health = getMaxHealth();
        profile.setBuilding(this);
        profile.getDocument().update();
    }

    public void crystalRepair() throws CommandException {
        if (!canCrystalRepair()) {
            throw new CommandException("You cannot repair this building.");
        }

        getCrystalRepairPrice().buy(profile);
        health = getMaxHealth();
        profile.setBuilding(this);
        profile.getDocument().update();
    }

    public void upgrade() throws CommandException {
        if (!canUpgrade()) {
            throw new CommandException("You cannot upgrade this building.");
        }

        getUpgradePrice().buy(profile);
        stage += 1;
        profile.setBuilding(this);
        profile.getDocument().update();
    }

    public void sell() throws CommandException {
        if (!canSell()) {
            throw new CommandException("You cannot sell this building.");
        }

        getSellPrice().give(profile);
        profile.removeBuilding(layer);
        profile.getDocument().update();
    }


    public Price<Integer> getSellPrice() {
        return new Price<>(getMainCurrency(),
                (int) Math.floor(0.65f * stage * getPrice().amount()));
    }

    public Price<Integer> getRepairPrice() {
        return new Price<>(getMainCurrency(),
                (int) Math.floor(((double) getMaxHealth() - health) / 100 * getSellPrice().amount() * 0.10f));
    }

    public Price<Integer> getCrystalRepairPrice() {
        return new Price<>(Currency.CRYSTALS,
                (int) Math.max(Math.floor(getRepairPrice().amount() * 0.03), 1));
    }

    public Price<Integer> getUpgradePrice() {
        return new Price<>(getMainCurrency(),
                (int) Math.floor(0.15 * (stage + 1) * getPrice().amount()));
    }

    public boolean canSell() {
        return exists();
    }

    public boolean canRepair() {
        return !isHealthMaxed() && profile.canAfford(getRepairPrice()) && exists();
    }

    public boolean canCrystalRepair() {
        return !isHealthMaxed() && profile.canAfford(getCrystalRepairPrice()) && exists();
    }

    public boolean canRepairAny() {
        return canRepair() || canCrystalRepair();
    }

    public boolean canUpgrade() {
        return getMainCurrency().get(profile).amount() >= getUpgradePrice().amount() && exists();
    }

    public Currency getMainCurrency() {
        return getPrice().currency();
    }

    /**
     * Gets the formatted name of this building.
     * @return A string in the form of "[emoji] **[name]**".
     */
    public String getFormattedName() {
        return String.format("%s **%s**", getEmoji(), getName());
    }

    public abstract int getId();

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getEmoji();

    public abstract Price<Integer> getPrice();

    public abstract BuildingType getType();

    @SuppressWarnings("unchecked")
    public <R extends Building> R cast() {
        return (R) this;
    }

    public static Building of(int id) {
        return ID_BUILDINGS.get(id);
    }

    public static Building of(String name) {
        return NAME_BUILDINGS.get(name);
    }

}
