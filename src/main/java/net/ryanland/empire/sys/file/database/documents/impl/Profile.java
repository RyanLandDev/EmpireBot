package net.ryanland.empire.sys.file.database.documents.impl;

import net.dv8tion.jda.api.entities.User;
import net.ryanland.empire.sys.file.Partition;
import net.ryanland.empire.sys.file.database.DocumentCache;
import net.ryanland.empire.sys.file.database.documents.SnowflakeDocument;
import net.ryanland.empire.sys.file.serializer.user.BuildingsSerializer;
import net.ryanland.empire.sys.gameplay.building.BuildingType;
import net.ryanland.empire.sys.gameplay.building.impl.Building;
import net.ryanland.empire.sys.gameplay.building.impl.resource.ResourceBuilding;
import net.ryanland.empire.sys.gameplay.building.impl.resource.ResourceGeneratorBuilding;
import net.ryanland.empire.sys.gameplay.building.impl.resource.ResourceStorageBuilding;
import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.gameplay.currency.Price;
import net.ryanland.empire.sys.message.Emojis;
import net.ryanland.empire.sys.util.NumberUtil;
import net.ryanland.empire.sys.util.StringUtil;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    public Integer getCrystalsInt() {
        return document.getCrystals();
    }

    public Price<Integer> getCrystals() {
        return new Price<>(Currency.CRYSTALS, getCrystalsInt());
    }

    public String getFormattedCrystals() {
        return getFormattedCrystals(false);
    }

    public String getFormattedCrystals(boolean includeEmoji) {
        return formattedNumberWithEmoji(getCrystalsInt(), CRYSTALS, includeEmoji);
    }

    public Integer getGoldInt() {
        return document.getGold();
    }

    public Price<Integer> getGold() {
        return new Price<>(Currency.GOLD, getGoldInt());
    }

    public String getFormattedGold() {
        return getFormattedGold(false);
    }

    public String getFormattedGold(boolean includeEmoji) {
        return formattedNumberWithEmoji(getGoldInt(), GOLD, includeEmoji);
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

    public Integer getWave() {
        return document.getWave();
    }

    public List<Building> getBuildings() {
        return BuildingsSerializer.getInstance().deserialize(document.getBuildings());
    }

    public Building getBuilding(int position) {
        return getBuildings().get(position - 1);
    }

    public Date getCreated() {
        return document.getCreated();
    }

    public String getFormattedCreated() {
        return StringUtil.format(getCreated());
    }

    private String formattedNumberWithEmoji(Number number, String emoji, boolean includeEmoji) {
        return (includeEmoji ? emoji + " " : "") + NumberUtil.format(number);
    }

    public Price<Integer> getCapacity(Currency currency) {
        List<ResourceBuilding> buildings = getBuildings().stream()
                .filter(b -> b.isUsable() &&
                        b.getBuildingType() == BuildingType.RESOURCE_STORAGE &&
                        ((ResourceBuilding) b).getEffectiveCurrency() == currency)
                .map(b -> (ResourceBuilding) b)
                .collect(Collectors.toList());

        int capacity = currency.getDefaultCapacity();

        for (ResourceBuilding building : buildings) {
            capacity += building.getCapacity().amount();
        }

        return new Price<>(currency, capacity);
    }

    public int getCapacityInt(Currency currency) {
        return getCapacity(currency).amount();
    }

    public boolean capacityIsFull(Currency currency) {
        return capacityIsFull(currency, getCapacity(currency));
    }

    public boolean capacityIsFull(Currency currency, Price<Integer> capacity) {
        return capacityIsFull(currency, capacity.amount());
    }

    public boolean capacityIsFull(Currency currency, int capacity) {
        return capacity < currency.getAmountInt(this);
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
        int capacity = getCapacityInt(currency);
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
