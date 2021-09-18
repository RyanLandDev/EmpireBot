package net.ryanland.empire.bot.command.impl.gameplay.items;

import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.arguments.types.impl.Enum.EnumArgument;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.gameplay.building.impl.Building;
import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.gameplay.currency.Price;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import net.ryanland.empire.sys.message.builders.PresetType;

import java.util.List;
import java.util.stream.Collectors;

public class RepairCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
            .name("repair")
            .description("Repairs all buildings with the provided currency.")
            .category(Category.ITEMS)
            .requiresProfile();
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
            new EnumArgument<>(Currency.class)
                .id("currency")
                .description("The currency to repair with.")
        );
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        Profile profile = event.getProfile();
        Currency currency = event.getArgument("currency");

        List<Building> buildingsToRepair = event.getProfile().getBuildings().stream()
            .filter(building -> !building.isHealthMaxed() && building.exists() &&
                (currency == Currency.CRYSTALS || currency == building.getRepairPrice().currency()))
            .collect(Collectors.toList());

        int capital = currency.get(profile).amount();
        int owned = currency.get(profile).amount();
        int buildingsRepaired = 0;

        for (Building building : buildingsToRepair) {
            Price<Integer> repairPrice = currency == Currency.CRYSTALS &&
                building.getRepairPrice().currency() != Currency.CRYSTALS ? building.getCrystalRepairPrice() :
                building.getRepairPrice();

            if (repairPrice.amount() > owned) {
                break;
            }
            owned -= repairPrice.amount();

            if (currency == Currency.CRYSTALS && building.getRepairPrice().currency() != Currency.CRYSTALS)
                building.crystalRepair();
            else
                building.repair();

            buildingsRepaired++;
        }

        if (buildingsRepaired == 0 && buildingsToRepair.size() > 0) {
            event.reply(new PresetBuilder(PresetType.ERROR,
                "You do not have enough " + currency.getName() + " to repair any building.", "Repair"));
        } else if (buildingsRepaired == 0) {
            event.reply(new PresetBuilder(PresetType.ERROR,
                "No buildings can currently be repaired using " + currency.getName() + ".", "Repair"));
        } else {
            event.reply(new PresetBuilder(PresetType.SUCCESS,
                "Repaired %s building%s for %s.".formatted(
                    buildingsRepaired, buildingsRepaired == 1 ? "" : "s",
                    new Price<>(currency, capital - owned).format()),
                "Repair"));
            profile.getDocument().update();
        }
    }
}
