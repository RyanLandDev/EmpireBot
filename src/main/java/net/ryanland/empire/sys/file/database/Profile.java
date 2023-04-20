package net.ryanland.empire.sys.file.database;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.Interaction;
import net.ryanland.colossus.Colossus;
import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.command.cooldown.Cooldown;
import net.ryanland.colossus.events.command.CommandEvent;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.sys.file.Partition;
import net.ryanland.empire.sys.file.serializer.BuildingsSerializer;
import net.ryanland.empire.sys.file.serializer.InventorySerializer;
import net.ryanland.empire.sys.file.serializer.PerksSerializer;
import net.ryanland.empire.sys.file.serializer.PotionsSerializer;
import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.building.impl.Building;
import net.ryanland.empire.sys.gameplay.building.impl.resource.ResourceBuilding;
import net.ryanland.empire.sys.gameplay.building.impl.resource.generator.GoldMineBuilding;
import net.ryanland.empire.sys.gameplay.building.impl.resource.storage.BankBuilding;
import net.ryanland.empire.sys.gameplay.collectible.Item;
import net.ryanland.empire.sys.gameplay.collectible.box.Boxes;
import net.ryanland.empire.sys.gameplay.collectible.perk.Perk;
import net.ryanland.empire.sys.gameplay.collectible.potion.Potion;
import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.gameplay.currency.Price;
import net.ryanland.empire.sys.message.Emojis;
import net.ryanland.empire.sys.message.builders.Preset;
import net.ryanland.empire.util.DateUtil;
import net.ryanland.empire.util.NumberUtil;
import net.ryanland.empire.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class Profile extends Table<User> implements Emojis {

    public final static String LEVEL_KEY = "lvl";
    public final static String XP_KEY = "xp";
    public final static String GOLD_KEY = "gold";
    public final static String CRYSTALS_KEY = "crys";
    public final static String WAVE_KEY = "wv";
    public final static String BUILDINGS_KEY = "blds";
    public final static String CREATION_DATE_KEY = "cr";
    public final static String COOLDOWNS_KEY = "cd";
    public final static String INVENTORY_KEY = "inv";
    public final static String POTIONS_KEY = "pot";
    public final static String PERKS_KEY = "prk";

    public static final int DEFAULT_LEVEL = 1;
    public static final int DEFAULT_XP = 0;
    public static final int DEFAULT_GOLD = 1000;
    public static final int DEFAULT_CRYSTALS = 100;
    public static final int DEFAULT_WAVE = 1;
    @SuppressWarnings("all")
    public static final List<List> DEFAULT_BUILDINGS = Arrays.asList(
        Building.of(GoldMineBuilding.ID).defaults().serialize(),
        Building.of(BankBuilding.ID).defaults().serialize()
    );
    @SuppressWarnings("all")
    public static final List<List> DEFAULT_COOLDOWNS = Collections.emptyList();
    public static final List<String> DEFAULT_INVENTORY = Collections.emptyList();
    @SuppressWarnings("all")
    public static final List<List> DEFAULT_POTIONS = Collections.emptyList();
    @SuppressWarnings("all")
    public static final List<List> DEFAULT_PERKS = Collections.emptyList();

    // -------------------------------------------------------------------------

    private User user;

    public Profile(String clientId) {
        super(clientId);
    }

    public Profile(String clientId, Map<String, Object> data) {
        super(clientId, data);
    }

    public static Profile of(CommandEvent event) {
        return of(event.getUser());
    }

    public static Profile of(User user) {
        Profile profile = (Profile) Colossus.getDatabaseDriver().get(user);
        profile.user = user;
        return profile;
    }

    public User getUser() {
        return user;
    }

    // ------------------------------------------------------------------------

    public Integer getLevel() {
        return get(LEVEL_KEY, DEFAULT_LEVEL);
    }

    /**
     * Sets the user's level.
     * <strong>WARNING:</strong> Does not call {@link #update()}
     */
    public Profile setLevel(int level) {
        put(LEVEL_KEY, level);
        return this;
    }

    public Price<Integer> getCrystals() {
        return new Price<>(Currency.CRYSTALS, get(CRYSTALS_KEY, DEFAULT_CRYSTALS));
    }

    /**
     * Sets the user's crystals.
     * <strong>WARNING:</strong> Does not call {@link #update()}
     */
    public Profile setCrystals(int crystals) {
        put(CRYSTALS_KEY, crystals);
        return this;
    }

    public String getFormattedCrystals() {
        return getFormattedCrystals(false);
    }

    public String getFormattedCrystals(boolean includeEmoji) {
        return formattedNumberWithEmoji(getCrystals().amount(), CRYSTALS, includeEmoji);
    }

    public Price<Integer> getGold() {
        return new Price<>(Currency.GOLD, get(GOLD_KEY, DEFAULT_GOLD));
    }

    /**
     * Sets the user's gold.
     * <strong>WARNING:</strong> Does not call {@link #update()}
     */
    public Profile setGold(int gold) {
        put(GOLD_KEY, gold);
        return this;
    }

    public String getFormattedGold() {
        return getFormattedGold(false);
    }

    public String getFormattedGold(boolean includeEmoji) {
        return formattedNumberWithEmoji(getGold().amount(), GOLD, includeEmoji);
    }

    public Integer getXp() {
        return get(XP_KEY, DEFAULT_XP);
    }

    public String getFormattedXp() {
        return getFormattedXp(false);
    }

    public String getFormattedXp(boolean includeEmoji) {
        return formattedNumberWithEmoji(getXp(), XP, includeEmoji);
    }

    public int getRequiredXp() {
        return (int) (8 * Math.pow(getLevel(), 2.2) + (30 * getLevel()) + 10);
    }

    public String getFormattedRequiredXp() {
        return NumberUtil.format(getRequiredXp());
    }

    public String getXpProgressBar() {
        return NumberUtil.progressBar(10, getXp(), getRequiredXp());
    }

    public void giveXp(int xp, Interaction interaction, MessageEmbed... embeds) {
        giveXp(xp, interaction, true, embeds);
    }

    /**
     * Increases the profile's xp by the provided value and accounts for level ups.
     * <strong>WARNING:</strong> Does not call {@link #update()}
     *
     * @param xp The amount of XP to give to the profile.
     */
    public void giveXp(int xp, Interaction interaction, boolean ephemeral, MessageEmbed... embeds) {
        int newXp = getXp() + xp;

        // Level up
        if (newXp >= getRequiredXp()) {
            newXp -= getRequiredXp();

            int newLevel = getLevel() + 1;
            setLevel(newLevel);

            interaction.replyEmbeds(new PresetBuilder(Preset.NOTIFICATION,
                String.format("""
                        You have leveled up to **Level %s**!

                        :homes: **Building limit:** *%s* %s %s
                        %s The maximum amount of buildings you can have.
                        %s **Building stage limit:** *%s* %3$s %s
                        %5$s The maximum stage a building can be.

                        %3$s Received %s""",
                    newLevel, getBuildingLimit(), ARROW_RIGHT, getBuildingLimit(newLevel), AIR,
                    STAGE, getBuildingStageLimit(), getBuildingStageLimit(newLevel),
                    Boxes.MYTHICAL.give(of(interaction.getUser()))),
                XP + " Level Up!")
                .build(), embeds).setEphemeral(ephemeral).queue();
        } else {
            interaction.replyEmbeds(Arrays.asList(embeds)).setEphemeral(ephemeral).queue();
        }

        // Update xp
        setXp(newXp);
    }

    /**
     * Increases the profile's xp by the provided value and accounts for level ups.
     * <strong>WARNING:</strong> Does not call {@link #update()}
     *
     * @param xp The amount of XP to give to the profile.
     */
    public void giveXp(int xp, Message message, MessageEmbed... embeds) {
        int newXp = getXp() + xp;

        // Level up
        if (newXp >= getRequiredXp()) {
            newXp -= getRequiredXp();

            int newLevel = getLevel() + 1;
            setLevel(newLevel);

            message.replyEmbeds(new PresetBuilder(Preset.NOTIFICATION,
                String.format("""
                        You have leveled up to **Level %s**!

                        :homes: **Building limit:** *%s* %s %s
                        %s The maximum amount of buildings you can have.
                        %s **Building stage limit:** *%s* %3$s %s
                        %5$s The maximum stage a building can be.

                        %3$s Received %s""",
                    newLevel, getBuildingLimit(), ARROW_RIGHT, getBuildingLimit(newLevel), AIR,
                    STAGE, getBuildingStageLimit(), getBuildingStageLimit(newLevel),
                    Boxes.MYTHICAL.give(of(message.getAuthor()))),
                XP + " Level Up!")
                .build(), embeds).queue();
        } else {
            message.replyEmbeds(Arrays.asList(embeds)).queue();
        }

        // Update xp
        setXp(newXp);
    }

    /**
     * Sets the user's XP.
     * <strong>WARNING:</strong> Does not call {@link #update()}
     */
    public Profile setXp(int xp) {
        put(XP_KEY, xp);
        return this;
    }

    public boolean canAfford(@NotNull Price<Integer> price) {
        return price.currency().get(this).amount() >= price.amount();
    }

    /**
     * Checks if the profile has enough capacity to receive this {@link Price}
     *
     * @param price The price to check.
     * @return True or false, depending on the check.
     */
    public boolean roomFor(Price<Integer> price) {
        return getCapacity(price.currency()).amount() >= price.currency().get(this).amount() + price.amount();
    }

    public Integer getWave() {
        return get(WAVE_KEY, DEFAULT_WAVE);
    }

    /**
     * Sets the user's wave.
     * <strong>WARNING:</strong> Does not call {@link #update()}
     */
    public Profile setWave(int wave) {
        put(WAVE_KEY, wave);
        return this;
    }

    public Price<Integer> getWaveGoldReward() {
        return new Price<>(Currency.GOLD,
            (int) (800 + (getWave() - 1) * 430 + 12 * Math.pow(getWave(), 2.1)));
    }

    public List<Cooldown> getCooldowns() {
        return CooldownsSerializer.getInstance().deserialize(get(COOLDOWNS_KEY, DEFAULT_COOLDOWNS));
    }

    public List<Item> getInventory() {
        return InventorySerializer.getInstance().deserialize(get(INVENTORY_KEY, DEFAULT_INVENTORY));
    }

    public List<Perk> getPerks() {
        return PerksSerializer.getInstance().deserialize(get(PERKS_KEY, DEFAULT_PERKS));
    }

    /**
     * Sets the user's perks.
     * <strong>WARNING:</strong> Does not call {@link #update()}
     */
    public Profile setPerks(List<Perk> perks) {
        put(PERKS_KEY, PerksSerializer.getInstance().serialize(perks));
        return this;
    }

    /**
     * Gets all <strong>currently active</strong> potions of all scopes.
     * @see #getUserPotions()
     * @see #getClanPotions()
     * @see #getGlobalPotions()
     */
    public List<Potion> getPotions() {
        List<Potion> potions = new ArrayList<>(getUserPotions());
        //potions.addAll(getClanPotions()); TODO
        //potions.addAll(getGlobalPotions());
        return potions;
    }

    @SuppressWarnings("all")
    public void cleanPotions() {
        List<Potion> potions = getUserPotions();
        List<Potion> newPotions = potions.stream()
            .filter(potion -> {
                return potion.getExpires().getTime() > System.currentTimeMillis();
            })
            .collect(Collectors.toList());

        if (potions.size() != newPotions.size()) {
            setUserPotions(newPotions);
            update();
        }
    }

    /**
     * Sets the user's potions.
     * <strong>WARNING:</strong> Does not call {@link #update()}
     */
    public Profile setUserPotions(List<Potion> potions) {
        put(POTIONS_KEY, PotionsSerializer.getInstance().serialize(potions));
        return this;
    }

    /**
     * Gets all <strong>currently active</strong> potions with {@link Potion.Scope#USER}.
     * @see #getPotions()
     * @see #getClanPotions()
     * @see #getGlobalPotions()
     */
    public List<Potion> getUserPotions() {
        cleanPotions();
        return PotionsSerializer.getInstance().deserialize(get(POTIONS_KEY, DEFAULT_POTIONS));
    }

    /**
     * Gets all <strong>currently active</strong> potions with {@link Potion.Scope#CLAN}
     * @see #getPotions()
     * @see #getUserPotions()
     * @see #getGlobalPotions()
     */
    public List<Potion> getClanPotions() {
        return null;//TODO
    }

    /**
     * Gets all <strong>currently active</strong> potions with {@link Potion.Scope#GLOBAL}
     * @see #getPotions()
     * @see #getUserPotions()
     * @see #getClanPotions()
     */
    public List<Potion> getGlobalPotions() {
        return null;//TODO
    }

    public List<Building> getBuildings() {
        return BuildingsSerializer.getInstance().deserialize(get(BUILDINGS_KEY, DEFAULT_BUILDINGS), this);
    }

    public Building getBuilding(int position) {
        return getBuildings().get(position - 1);
    }

    /**
     * Sets a building at a specific layer.
     * <strong>WARNING:</strong> Does not call {@link #update()}
     *
     * @param building The building to set. This building's {@code layer} field will be used.
     */
    @SuppressWarnings("all")
    public Profile setBuilding(Building building) {
        List<Building> newBuildings = new ArrayList<>(getBuildings());

        newBuildings.remove(building.getLayer() - 1);
        newBuildings.add(building.getLayer() - 1, building);

        setBuildings(newBuildings);
        return this;
    }

    /**
     * Sets the user's buildings.
     * <strong>WARNING:</strong> Does not call {@link #update()}
     */
    public Profile setBuildings(List<Building> buildings) {
        put(BUILDINGS_KEY, BuildingsSerializer.getInstance().serialize(buildings));
        return this;
    }

    /**
     * Sets the user's inventory.
     * <strong>WARNING:</strong> Does not call {@link #update()}
     */
    public Profile setInventory(List<Item> inventory) {
        put(INVENTORY_KEY, InventorySerializer.getInstance().serialize(inventory));
        return this;
    }

    /**
     * Adds a building to the profile's empire.
     * <strong>WARNING:</strong> Does not call {@link #update()}
     *
     * @param building The building to add.
     */
    @SuppressWarnings("all")
    public Profile addBuilding(Building building) throws CommandException {
        List<Building> newBuildings = getBuildings();

        if (newBuildings.size() + 1 > getBuildingLimit())
            throw new CommandException("You have hit the **Building Limit**!\nLevel up to increase this limit.");

        newBuildings.add(building.defaults());
        setBuildings(newBuildings);
        return this;
    }

    /**
     * Removes a building at a specific layer.
     * <strong>WARNING:</strong> Does not call {@link #update()}
     *
     * @param layer The layer the building is at to remove.
     */
    public Profile removeBuilding(int layer) {
        List<Building> newBuildings = getBuildings();
        newBuildings.remove(layer - 1);
        setBuildings(newBuildings);
        return this;
    }

    public int getBuildingLimit() {
        return getBuildingLimit(getLevel());
    }

    public static int getBuildingLimit(int level) {
        return (int) (level * 1.25 + 3);
    }

    public int getBuildingStageLimit() {
        return getBuildingStageLimit(getLevel());
    }

    public static int getBuildingStageLimit(int level) {
        return level;
    }

    public Date getCreated() {
        return get("cr");
    }

    public String getFormattedCreated() {
        return DateUtil.format(getCreated());
    }

    public String getTimestampCreated() {
        return DateUtil.getDiscordTimestamp(getCreated());
    }

    private String formattedNumberWithEmoji(Number number, String emoji, boolean includeEmoji) {
        return (includeEmoji ? emoji + " " : "") + NumberUtil.format(number);
    }

    public Price<Integer> getCapacity(Currency currency) {
        return new Price<>(currency, getBuildings().stream()
            .filter(building -> building.isUsable() &&
                building.getType() == BuildingType.RESOURCE_STORAGE &&
                ((ResourceBuilding) building).getEffectiveCurrency() == currency)
            .map(building -> ((ResourceBuilding) building).getCapacity().amount())
            .reduce(0, Integer::sum)
            + currency.getDefaultCapacity());
    }

    public boolean capacityIsFull(Currency currency) {
        return capacityIsFull(currency, getCapacity(currency));
    }

    public boolean capacityIsFull(Currency currency, Price<Integer> capacity) {
        return capacityIsFull(currency, capacity.amount());
    }

    public boolean capacityIsFull(Currency currency, int capacity) {
        return capacity < currency.getAmount(this).amount();
    }

    /**
     * Equivalent of {@link #getFormattedCapacity(Currency, boolean)} with {@code false} as {@code includeFull} parameter.
     *
     * @param currency The currency to get the capacity of.
     * @return The formatted capacity string.
     */
    public String getFormattedCapacity(Currency currency) {
        return getFormattedCapacity(currency, false);
    }

    /**
     * Gets the user's capacity of the currency provided and formats it using {@link NumberUtil#format(Number)}.
     *
     * @param currency    The currency to get the capacity of.
     * @param includeFull If the capacity is full and this option is set to true, appends {@code " **FULL**"} to the result.
     * @return The formatted capacity string.
     */
    public String getFormattedCapacity(Currency currency, boolean includeFull) {
        int capacity = getCapacity(currency).amount();
        return NumberUtil.format(capacity) + (includeFull && capacityIsFull(currency, capacity) ? " **FULL**" : "");
    }

    public String getFormattedLayout() {
        List<String> rows = new ArrayList<>();

        int i = 0;
        Partition<Building> buildingPartition = new Partition<>(getBuildings(), Building.LAYOUT_DISPLAY_PER_ROW);

        for (List<Building> buildings : buildingPartition) {
            List<String> layerRow = new ArrayList<>();
            List<String> buildingRow = new ArrayList<>();

            for (Building building : buildings) {
                i++;
                layerRow.add(StringUtil.getNumberEmoji(i));
                buildingRow.add(building.getEmoji());
            }

            rows.add("Layer" + StringUtil.genTrimProofSpaces(9) + String.join(" ", layerRow));
            rows.add("Building" + StringUtil.genTrimProofSpaces(4) + String.join(" ", buildingRow));
        }

        return String.join("\n", rows);
    }

    /**
     * Updates this profile to the database.
     */
    public void update() {
        Colossus.getDatabaseDriver().updateTable(user, this);
    }

}
