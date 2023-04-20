package net.ryanland.empire.sys.gameplay.building.impl;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.command.executor.functional_interface.CommandRunnable;
import net.ryanland.colossus.events.ButtonClickEvent;
import net.ryanland.colossus.sys.interactions.button.BaseButton;
import net.ryanland.colossus.sys.interactions.button.ButtonLayout;
import net.ryanland.colossus.sys.interactions.button.ButtonRow;
import net.ryanland.colossus.sys.interactions.menu.ConfirmMenu;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.gameplay.building.BuildingActionState;
import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.building.impl.defense.ranged.ArcherBuilding;
import net.ryanland.empire.sys.gameplay.building.impl.defense.ranged.MortarBuilding;
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
import net.ryanland.empire.sys.message.Formattable;
import net.ryanland.empire.sys.message.builders.Preset;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class Building implements Formattable, Emojis {

    public static final int BUILDING_START_STAGE = 1;
    public static final int BASE_MAX_HEALTH = 100;
    public static final int USABLE_HEALTH = 75;
    public static final int LAYOUT_DISPLAY_PER_ROW = 7;

    @SuppressWarnings("all")
    public static final List<Class<? extends Building>> BUILDINGS = Arrays.asList(

        // Defense Ranged
        ArcherBuilding.class,
        MortarBuilding.class,
        // Defense Thorned
        WallBuilding.class,

        // Resource Generator
        GoldMineBuilding.class,
        // Resource Storage
        BankBuilding.class

    );

    public static List<Building> getBuildingsInstances() {
        return BUILDINGS.stream()
            .map(buildingClass -> {
                try {
                    return buildingClass.getDeclaredConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
                throw new IllegalArgumentException();
            })
            .collect(Collectors.toList());
    }

    private static final HashMap<String, Class<? extends Building>> NAME_BUILDINGS = new HashMap<>(
        BUILDINGS.stream()
            .collect(Collectors.toMap(buildingClass -> {
                try {
                    return buildingClass.getDeclaredConstructor().newInstance().getName();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
                throw new IllegalArgumentException();
            }, Function.identity()))
    );

    private static final HashMap<Integer, Class<? extends Building>> ID_BUILDINGS = new HashMap<>(
        BUILDINGS.stream()
            .collect(Collectors.toMap(buildingClass -> {
                try {
                    return buildingClass.getDeclaredConstructor().newInstance().getId();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
                throw new IllegalArgumentException();
            }, Function.identity()))
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

    public final Building setHealth(int health) {
        this.health = health;
        return this;
    }

    public final Building damage(int amount) {
        health -= amount;
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
                .addElement(BuildingInfoElement.capacitable("Health", HEALTH,
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
            return getProfile().getBuildings().get(getLayer() - 1).serialize().equals(serialize());
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    protected ButtonLayout getMenuButtonLayout() throws CommandException {
        ButtonRow row1 = new ButtonRow();

        if (canRepairAny()) {
            row1.add(new BaseButton(
                Button.success("repairMain", String.format("Repair (%s)",
                        canRepair() ? getRepairPrice().currency().getName() : "Not Enough"))
                    .withEmoji(Emoji.fromUnicode(getMainCurrency().getEmoji()))
                    .withDisabled(!canRepair()),

                event -> {
                    Price<Integer> repairPrice = getRepairPrice();

                    executeButtonAction(event, this::repair);
                    refreshMenu(event);

                    event.reply(new PresetBuilder(Preset.SUCCESS, String.format(
                        "Repaired your %s for %s.", format(), repairPrice.format()
                    )).setEphemeral(true));
                }), new BaseButton(
                Button.success("repairCrystal", String.format("Repair (%s)",
                        canCrystalRepair() ? getCrystalRepairPrice().currency().getName() : "Not Enough"))
                    .withEmoji(Emoji.fromUnicode(getCrystalRepairPrice().currency().getEmoji()))
                    .withDisabled(!canCrystalRepair()),

                event -> {
                    Price<Integer> crystalRepairPrice = getCrystalRepairPrice();

                    executeButtonAction(event, this::crystalRepair);
                    refreshMenu(event);

                    event.reply(new PresetBuilder(Preset.SUCCESS, String.format(
                        "Repaired your %s for %s.", format(), crystalRepairPrice.format()
                    )).setEphemeral(true));
                }));
        } else {
            row1.add(new BaseButton(
                Button.secondary("repair", "Repair (Maxed)")
                    .withEmoji(Emoji.fromUnicode(REPAIR))
                    .asDisabled()));
        }

        row1.add(new BaseButton(
            Button.secondary("upgrade", "Upgrade" + (canUpgrade() ? "" : " (%s)".formatted(getUpgradeState().getName())))
                .withEmoji(Emoji.fromUnicode(UPGRADE))
                .withDisabled(!canUpgrade()),

            event -> {
                Price<Integer> upgradePrice = getUpgradePrice();

                executeButtonAction(event, this::upgrade);
                refreshMenu(event);

                event.reply(new PresetBuilder(Preset.SUCCESS, String.format(
                    "Upgraded your %s for %s.", format(), upgradePrice.format()
                )).setEphemeral(true));
            }), new BaseButton(
            Button.secondary("sell", "Sell" + (canSell() ? "" : " (%s)".formatted(getSellState().getName())))
                .withEmoji(Emoji.fromUnicode(SELL))
                .withDisabled(!canSell()),

            event -> executeButtonAction(event, () -> sell(event))
        ));

        // Previous/next building buttons
        ButtonRow row2 = new ButtonRow(
            new BaseButton(
                Button.primary("previous", "Previous")
                    .withEmoji(Emoji.fromUnicode("◀"))
                    .withDisabled(getLayer() == 1),
                event -> event.reply(getProfile().getBuilding(getLayer() - 1).getMenu())),
            new BaseButton(
                Button.primary("next", "Next")
                    .withEmoji(Emoji.fromUnicode("▶"))
                    .withDisabled(getLayer() == getProfile().getBuildings().size()),
                event -> event.reply(getProfile().getBuilding(getLayer() + 1).getMenu()))
        );

        return new ButtonLayout(row1, row2);
    }

    public PresetBuilder getMenu() throws CommandException {
        if (!exists()) {
            return new PresetBuilder("This building does not exist anymore.");
        }
        return getBuildingInfo().build()
            .setTitle(String.format("%s %s (%s)", getEmoji(), getName(), getType().getFullName()))
            .setButtonLayout(getMenuButtonLayout());
    }

    @SuppressWarnings("all")
    public void refreshMenu(ButtonClickEvent event) throws CommandException {
        event.reply(getMenu());
    }

    public MessageEmbed.Field getEmbedField() {
        return new MessageEmbed.Field("\u200b",
            String.format("%s\n%s\n**Price:** %s\n**ID:** `%s`\n\u200b",
                format(true), getDescription(), getPrice().format(), getId()),
            true);
    }

    public void executeButtonAction(ButtonClickEvent event, CommandRunnable action) throws CommandException {
        try {
            action.run();
        } catch (CommandException e) {
            refreshMenu(event);
            throw e;
        }
    }

    public void repair() throws CommandException {
        if (!canRepair())
            throw new CommandException("You cannot repair this building.");

        getRepairPrice().buy(profile);
        health = getMaxHealth();
        profile.setBuilding(this);
        profile.update();
    }

    public void crystalRepair() throws CommandException {
        if (!canCrystalRepair())
            throw new CommandException("You cannot repair this building.");

        getCrystalRepairPrice().buy(profile);
        health = getMaxHealth();
        profile.setBuilding(this);
        profile.update();
    }

    public void upgrade() throws CommandException {
        if (!canUpgrade())
            throw new CommandException("You cannot upgrade this building.");

        getUpgradePrice().buy(profile);
        stage += 1;
        health = getMaxHealth();

        profile.setBuilding(this);
        profile.update();
    }

    @SuppressWarnings("all")
    public void sell(ButtonClickEvent event) throws CommandException {
        if (!canSell())
            throw new CommandException("You cannot sell this building.");

        new ConfirmMenu("Are you sure you want to sell this building?",
            String.format("Sold your %s for %s.", format(), getSellPrice().format()), true, evt -> {
                getSellPrice().give(profile);

                profile.removeBuilding(layer);
                profile.update();

                refreshMenu(event);
        }).send(event);
    }


    public Price<Integer> getSellPrice() {
        return new Price<>(getMainCurrency(),
            (int) (0.65f * stage * getPrice().amount()));
    }

    public Price<Integer> getRepairPrice() {
        return new Price<>(getMainCurrency(),
            (int) (((double) getMaxHealth() - health) / 100 * getSellPrice().amount() * 0.10f));
    }

    public Price<Integer> getCrystalRepairPrice() {
        return new Price<>(Currency.CRYSTALS,
            (int) Math.max(Math.floor(getRepairPrice().amount() * 0.03), 1));
    }

    public Price<Integer> getUpgradePrice() {
        return new Price<>(getMainCurrency(),
            (int) (0.15 * (stage + 1) * getPrice().amount()));
    }

    public enum SellState implements BuildingActionState {

        NOT_MAXED("Broken", Building::isHealthMaxed),
        MINIMUM_OCCURRENCES("Minimum Reached", building -> building.getOccurrences() - 1 >= building.getMinimumAllowed()),
        NOT_EXISTING("Not Existing", Building::exists),

        AVAILABLE("Available", building -> false);

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
        STAGE_LIMIT("Stage Limit", building -> building.getStage() + 1 <= building.getProfile().getBuildingStageLimit()),
        NOT_ENOUGH("Not Enough", building -> building.getMainCurrency().get(building.getProfile()).amount() >= building.getUpgradePrice().amount()),
        NOT_EXISTING("Not Existing", Building::exists),

        AVAILABLE("Available", building -> false);

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
     *
     * @return A string in the form of "[emoji] **[name]**".
     */
    @Override
    public String format() {
        return format(false);
    }

    public String format(boolean underlined) {
        return String.format("%s %3$s**%s**%3$s", getEmoji(), getName(), underlined ? "__" : "");
    }

    /**
     * Returns the amount of buildings of this type every player must have.
     */
    public int getMinimumAllowed() {
        return 0;
    }

    public final int getProperty(Supplier<Integer> getter, int stage) {
        int originalStage = this.stage;
        this.stage = stage;

        int result = getter.get();
        this.stage = originalStage;

        return result;
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
        try {
            return ID_BUILDINGS.get(id).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NullPointerException ignored) {
        }
        throw new IllegalArgumentException();
    }

    public static Building of(String name) {
        try {
            return NAME_BUILDINGS.get(name).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

}
