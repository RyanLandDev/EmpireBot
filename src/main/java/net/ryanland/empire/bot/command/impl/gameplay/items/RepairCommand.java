package net.ryanland.empire.bot.command.impl.gameplay.items;

import net.ryanland.colossus.command.CommandException;
import net.ryanland.colossus.command.arguments.ArgumentSet;
import net.ryanland.colossus.command.arguments.types.EnumArgument;
import net.ryanland.colossus.command.regular.CombinedCommand;
import net.ryanland.colossus.command.regular.CommandBuilder;
import net.ryanland.colossus.events.command.CommandEvent;
import net.ryanland.colossus.sys.message.DefaultPresetType;
import net.ryanland.colossus.sys.message.PresetBuilder;
import net.ryanland.empire.sys.file.database.Profile;
import net.ryanland.empire.sys.gameplay.building.impl.Building;
import net.ryanland.empire.sys.gameplay.currency.Currency;
import net.ryanland.empire.sys.gameplay.currency.Price;

import java.util.List;
import java.util.stream.Collectors;

@CommandBuilder(
    name = "repair",
    description = "Repairs all buildings with the provided currency."
)
public class RepairCommand extends ItemsCommand implements CombinedCommand {

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArguments(
            new EnumArgument<>(Currency.class)
                .name("currency")
                .description("The currency to repair with.")
        );
    }

    @Override
    public void execute(CommandEvent event) throws CommandException {
        Profile profile = Profile.of(event);
        Currency currency = event.getArgument("currency");

        List<Building> buildingsToRepair = profile.getBuildings().stream()
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
            event.reply(new PresetBuilder(DefaultPresetType.ERROR,
                "You do not have enough " + currency.getName() + " to repair any building.", "Repair"));
        } else if (buildingsRepaired == 0) {
            event.reply(new PresetBuilder(DefaultPresetType.ERROR,
                "No buildings can currently be repaired using " + currency.getName() + ".", "Repair"));
        } else {
            event.reply(new PresetBuilder(DefaultPresetType.SUCCESS,
                "Repaired %s building%s for %s.".formatted(
                    buildingsRepaired, buildingsRepaired == 1 ? "" : "s",
                    new Price<>(currency, capital - owned).format()),
                "Repair"));
            profile.update();
        }
    }
}
