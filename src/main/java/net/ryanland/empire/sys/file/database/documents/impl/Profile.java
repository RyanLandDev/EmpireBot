package net.ryanland.empire.sys.file.database.documents.impl;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.Interaction;
import net.ryanland.empire.bot.command.executor.cooldown.Cooldown;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.sys.file.Partition;
import net.ryanland.empire.sys.file.database.DocumentCache;
import net.ryanland.empire.sys.file.database.documents.SnowflakeDocument;
import net.ryanland.empire.sys.file.serializer.BuildingsSerializer;
import net.ryanland.empire.sys.file.serializer.CooldownsSerializer;
import net.ryanland.empire.sys.file.serializer.InventorySerializer;
import net.ryanland.empire.sys.gameplay.box.Box;
import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.building.impl.Building;
import net.ryanland.empire.sys.gameplay.building.impl.resource.ResourceBuilding;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoBuilder;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoElement;
import net.ryanland.empire.sys.gameplay.building.info.BuildingInfoSegmentBuilder;
import net.ryanland.empire.sys.gameplay.collectible.Collectible;
import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.gameplay.currency.Price;
import net.ryanland.empire.sys.message.Emojis;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;
import net.ryanland.empire.util.DateUtil;
import net.ryanland.empire.util.NumberUtil;
import net.ryanland.empire.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Profile implements SnowflakeDocument, Emojis {

    private final User user;
    private final UserDocument document;

    public Profile(User user) {
        this.user = user;
        document = DocumentCache.get(user, UserDocument.class);
    }

    public UserDocument getDocument() {
        return document;
    }

    // ---------------------------------------

    public User getUser() {
        return user;
    }

    @Override
    public String getId() {
        return document.getId();
    }

    public Integer getLevel() {
        return document.getLevel();
    }

    public Price<Integer> getCrystals() {
        return new Price<>(Currency.CRYSTALS, document.getCrystals());
    }

    public String getFormattedCrystals() {
        return getFormattedCrystals(false);
    }

    public String getFormattedCrystals(boolean includeEmoji) {
        return formattedNumberWithEmoji(getCrystals().amount(), CRYSTALS, includeEmoji);
    }

    public Price<Integer> getGold() {
        return new Price<>(Currency.GOLD, document.getGold());
    }

    public String getFormattedGold() {
        return getFormattedGold(false);
    }

    public String getFormattedGold(boolean includeEmoji) {
        return formattedNumberWithEmoji(getGold().amount(), GOLD, includeEmoji);
    }

    public Integer getXp() {
        return document.getXp();
    }

    public String getFormattedXp() {
        return getFormattedXp(false);
    }

    public String getFormattedXp(boolean includeEmoji) {
        return formattedNumberWithEmoji(getXp(), XP, includeEmoji);
    }

    public int getRequiredXp() {
        return (int) Math.floor(8 * Math.pow(getLevel(), 2.2) + (30 * getLevel()) + 10);
    }

    public String getFormattedRequiredXp() {
        return NumberUtil.format(getRequiredXp());
    }

    public String getXpProgressBar() {
        return NumberUtil.progressBar(10, getXp(), getRequiredXp());
    }

    /**
     * Increases the profile's xp by the provided value and accounts for level ups.
     * <strong>WARNING:</strong> Does not call {@link UserDocument#update}
     * @param xp The amount of XP to give to the profile.
     */
    public void giveXp(int xp, Interaction interaction, MessageEmbed... embeds) {
        int newXp = getXp() + xp;

        // Level up
        if (newXp >= getRequiredXp()) {
            newXp -= getRequiredXp();

            int newLevel = getLevel() + 1;
            document.setLevel(newLevel);

            interaction.replyEmbeds(new PresetBuilder(PresetType.NOTIFICATION,
                    String.format("""
                            You have leveled up to **Level %s**!

                            :homes: **Building limit:** *%s* %s %s
                            %s The maximum amount of buildings you can have.
                            :bricks: **Building stage limit:** *%s* %3$s %s
                            %5$s The maximum stage a building can be.

                            %3$s Received %s""",
                            newLevel, getBuildingLimit(), ARROW_RIGHT, getBuildingLimit(newLevel), AIR,
                            getBuildingStageLimit(), getBuildingStageLimit(newLevel),
                            "todo xd"),
                    XP + " Level Up!")
                    .build(), embeds).setEphemeral(true).queue();
            //TODO add Mythical box as reward
        } else {
            interaction.replyEmbeds(Arrays.asList(embeds)).setEphemeral(true).queue();
        }

        // Update xp
        document.setXp(newXp);
    }

    public boolean canAfford(@NotNull Price<Integer> price) {
        return price.currency().get(this).amount() >= price.amount();
    }

    /**
     * Checks if the profile has enough capacity to receive this {@link Price}
     * @param price The price to check.
     * @return True or false, depending on the check.
     */
    public boolean roomFor(Price<Integer> price) {
        return getCapacity(price.currency()).amount() >= price.currency().get(this).amount() + price.amount();
    }

    public Integer getWave() {
        return document.getWave();
    }

    public List<Cooldown> getCooldowns() {
        return CooldownsSerializer.getInstance().deserialize(document.getCooldowns());
    }

    public List<Collectible> getInventory() {
        return InventorySerializer.getInstance().deserialize(document.getInventory());
    }

    public List<Building> getBuildings() {
        return BuildingsSerializer.getInstance().deserialize(document.getBuildings(), this);
    }

    public Building getBuilding(int position) {
        return getBuildings().get(position - 1);
    }

    /**
     * Sets a building at a specific layer.
     * <strong>WARNING:</strong> Does not call {@link UserDocument#update}
     * @param building The building to set. This building's {@code layer} field will be used.
     */
    @SuppressWarnings("all")
    public void setBuilding(Building building) {
        List<List> newBuildings = new ArrayList<>(document.getBuildings());

        newBuildings.remove(building.getLayer() - 1);
        newBuildings.add(building.getLayer() - 1, building.serialize());

        getDocument().setBuildingsRaw(newBuildings);
    }

    /**
     * Adds a building to the profile's empire.
     * <strong>WARNING:</strong> Does not call {@link UserDocument#update}
     * @param building The building to add.
     */
    @SuppressWarnings("all")
    public void addBuilding(Building building) throws CommandException {
        List<List> newBuildings = new ArrayList<>(document.getBuildings());

        if (newBuildings.size() + 1 > getBuildingLimit()) {
            throw new CommandException("You have hit the **Building Limit**!\nLevel up to increase this limit.");
        }

        newBuildings.add(building.defaults().serialize());
        getDocument().setBuildingsRaw(newBuildings);
    }

    /**
     * Removes a building at a specific layer.
     * <strong>WARNING:</strong> Does not call {@link UserDocument#update}
     * @param layer The layer the building is at to remove.
     */
    public void removeBuilding(int layer) {
        List<Building> newBuildings = getBuildings();
        newBuildings.remove(layer - 1);
        getDocument().setBuildings(newBuildings);
    }

    public int getBuildingLimit() {
        return getBuildingLimit(getLevel());
    }

    public static int getBuildingLimit(int level) {
        return (int) Math.floor(level * 1.25 + 3);
    }

    public int getBuildingStageLimit() {
        return getBuildingStageLimit(getLevel());
    }

    public static int getBuildingStageLimit(int level) {
        return level;
    }

    public Date getCreated() {
        return document.getCreated();
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
     * @param currency The currency to get the capacity of.
     * @return The formatted capacity string.
     */
    public String getFormattedCapacity(Currency currency) {
        return getFormattedCapacity(currency, false);
    }

    /**
     * Gets the user's capacity of the currency provided and formats it using {@link NumberUtil#format(Number)}.
     * @param currency The currency to get the capacity of.
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
        Partition<Building> buildingPartition = Partition.ofSize(getBuildings(), Building.LAYOUT_DISPLAY_PER_ROW);

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
}
