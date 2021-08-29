package net.ryanland.empire.sys.gameplay.building.impl;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.GenericComponentInteractionCreateEvent;
import net.dv8tion.jda.api.interactions.Interaction;
import net.dv8tion.jda.api.interactions.components.Button;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.executor.functional_interface.CommandRunnable;
import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.file.serializer.Serializer;
import net.ryanland.empire.sys.gameplay.building.BuildingActionState;
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
import net.ryanland.empire.sys.message.interactions.menu.confirm.ConfirmMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class Building
        implements Serializable, Serializer<List<?>, Building>, Emojis {

    public static final int BUILDING_START_STAGE = 1;
    public static final int BASE_MAX_HEALTH = 100;
    public static final int USABLE_HEALTH = 75;
    public static final int LAYOUT_DISPLAY_PER_ROW = 7;

    @SuppressWarnings("all")
    public static final Building[] BUILDINGS = new Building[]{

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
                                            getRepairPrice().format(true), getCrystalRepairPrice().format(true)),
                                    false))
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

                    event -> {
                        Price<Integer> repairPrice = getRepairPrice();

                        executeButtonAction(event, this::repair);
                        refreshMenu(event);

                        event.replyEmbeds(new PresetBuilder(PresetType.SUCCESS, String.format(
                                "Repaired your %s for %s.", getFormattedName(), repairPrice.format()
                        )).build()).setEphemeral(true).queue();
                    }
            ).addButton(
                    Button.success("repairCrystal", String.format("Repair (%s)",
                                    canCrystalRepair() ? getCrystalRepairPrice().currency().getName() : "Not Enough"))
                            .withEmoji(Emoji.fromMarkdown(getCrystalRepairPrice().currency().getEmoji()))
                            .withDisabled(!canCrystalRepair()),

                    event -> {
                        Price<Integer> crystalRepairPrice = getCrystalRepairPrice();

                        executeButtonAction(event, this::crystalRepair);
                        refreshMenu(event);

                        event.replyEmbeds(new PresetBuilder(PresetType.SUCCESS, String.format(
                                "Repaired your %s for %s.", getFormattedName(), crystalRepairPrice.format()
                        )).build()).setEphemeral(true).queue();
                    }
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
                Button.secondary("upgrade", "Upgrade" + (canUpgrade() ? "" : " (%s)".formatted(getUpgradeState().getName())))
                        .withEmoji(Emoji.fromMarkdown(UPGRADE))
                        .withDisabled(!canUpgrade()),

                event -> {
                    Price<Integer> upgradePrice = getUpgradePrice();

                    executeButtonAction(event, this::upgrade);
                    refreshMenu(event);

                    event.replyEmbeds(new PresetBuilder(PresetType.SUCCESS, String.format(
                            "Upgraded your %s for %s.", getFormattedName(), upgradePrice.format()
                    )).build()).setEphemeral(true).queue();
                }
        ).addButton(
                Button.secondary("sell", "Sell" + (canSell() ? "" : " (%s)".formatted(getSellState().getName())))
                        .withEmoji(Emoji.fromMarkdown(SELL))
                        .withDisabled(!canSell()),

                event -> executeButtonAction(event, () -> sell(event))
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
    }

    @SuppressWarnings("all")
    public void refreshMenu(ButtonClickEvent event) throws CommandException {
        refreshMenu(event.getMessage());
    }

    public void refreshMenu(Message menuMessage) throws CommandException {
        menuMessage.editMessageEmbeds(((ActionMenuBuilder) getMenuBuilder()).getEmbed().build()).setActionRows(
                exists() ? InteractionUtil.of(getActionMenuBuilder().getButtons().stream()
                        .map(ActionButton::button)
                        .collect(Collectors.toList()))
                        : Collections.emptyList()
                ).queue();
    }

    public MessageEmbed.Field getEmbedField() {
        return new MessageEmbed.Field("\u200b",
                String.format("%s\n%s\n**Price:** %s\n\u200b",
                        getFormattedName(true), getDescription(), getPrice().format()),
                        true);
    }

    public void executeButtonAction(ButtonClickEvent event, CommandRunnable action) throws CommandException {
        try {
            action.execute();
        } catch (CommandException e) {
            refreshMenu(event);
            throw e;
        }
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
        health = getMaxHealth();

        profile.setBuilding(this);
        profile.getDocument().update();
    }

    @SuppressWarnings("all")
    public void sell(GenericComponentInteractionCreateEvent event) throws CommandException {
        if (!canSell()) {
            throw new CommandException("You cannot sell this building.");
        }

        new ConfirmMenu("Are you sure you want to sell this building?", () -> {
            getSellPrice().give(profile);

            profile.removeBuilding(layer);
            profile.getDocument().update();

            refreshMenu(event.getMessage());

        }, String.format(
                "Sold your %s for %s.", getFormattedName(), getSellPrice().format()
        )).send(event);
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

    public enum SellState implements BuildingActionState {

        NOT_MAXED("Broken", Building::isHealthMaxed),
        MINIMUM_OCCURRENCES("Minimum Reached", building -> building.getOccurrences() - 1 >= building.getMinimumAllowed()),
        NOT_EXISTING("Not Existing", Building::exists),

        AVAILABLE("Available", building -> false)
        ;

        private final String name;
        private final Predicate<Building> check;

        SellState(String name, Predicate<Building> check) {
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

    public SellState getSellState() {
        return BuildingActionState.get(this, SellState.class);
    }

    public boolean canSell() {
        return getSellState() == SellState.AVAILABLE;
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

    public enum UpgradeState implements BuildingActionState {

        NOT_MAXED("Broken", Building::isHealthMaxed),
        STAGE_LIMIT("Stage Limit", building -> building.getStage() + 1 > building.getProfile().getBuildingStageLimit()),
        NOT_ENOUGH("Not Enough", building -> building.getMainCurrency().get(building.getProfile()).amount() >= building.getUpgradePrice().amount()),
        NOT_EXISTING("Not Existing", Building::exists),

        AVAILABLE("Available", building -> false)
        ;

        private final String name;
        private final Predicate<Building> check;

        UpgradeState(String name, Predicate<Building> check) {
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

    public UpgradeState getUpgradeState() {
        return BuildingActionState.get(this, UpgradeState.class);
    }

    public boolean canUpgrade() {
        return getUpgradeState() == UpgradeState.AVAILABLE;
    }

    public Currency getMainCurrency() {
        return getPrice().currency();
    }

    /**
     * Returns the amount of buildings of this type the profile has.
     * If {@link Building#exists} is true this will always be at least {@code 1}.
     */
    public int getOccurrences() {
        return (int) profile.getBuildings().stream()
                .filter(building -> building.getType() == getType())
                .count();
    }

    /**
     * Gets the formatted name of this building.
     * @return A string in the form of "[emoji] **[name]**".
     */
    public String getFormattedName() {
        return getFormattedName(false);
    }

    public String getFormattedName(boolean underlined) {
        return String.format("%s %3$s**%s**%3$s", getEmoji(), getName(), underlined ? "__" : "");
    }

    /**
     * Returns the amount of buildings of this type every player must have.
     */
    public int getMinimumAllowed() {
        return 0;
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

    public static Building find(String name) throws CommandException {
        try {
            return Arrays.stream(BUILDINGS)
                    .filter(building -> building.getName()
                            .replaceAll("[ _-]", "")
                            .equalsIgnoreCase(name
                                    .replaceAll("[ _-]", "")))
                    .collect(Collectors.toList())
                    .get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException("A building with the name `" + name + "` was not found.");
        }
    }

}
