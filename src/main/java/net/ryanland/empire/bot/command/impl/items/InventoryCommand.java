package net.ryanland.empire.bot.command.impl.items;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.ryanland.empire.bot.command.arguments.ArgumentSet;
import net.ryanland.empire.bot.command.executor.exceptions.CommandException;
import net.ryanland.empire.bot.command.impl.Command;
import net.ryanland.empire.bot.command.info.Category;
import net.ryanland.empire.bot.command.info.CommandInfo;
import net.ryanland.empire.bot.events.CommandEvent;
import net.ryanland.empire.sys.file.database.documents.impl.Profile;
import net.ryanland.empire.sys.gameplay.collectible.CollectibleHolder;
import net.ryanland.empire.sys.gameplay.collectible.Item;
import net.ryanland.empire.sys.gameplay.collectible.box.Box;
import net.ryanland.empire.sys.gameplay.collectible.box.Boxes;
import net.ryanland.empire.sys.message.builders.PresetBuilder;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InventoryCommand extends Command {

    @Override
    public CommandInfo getInfo() {
        return new CommandInfo()
                .name("inventory")
                .description("View all the items in your inventory.")
                .category(Category.ITEMS)
                .requiresProfile();
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public void run(CommandEvent event) throws CommandException {
        PresetBuilder embed = new PresetBuilder(
                "Here is an overview of all the items in your inventory.\nYou can use an item with `/use <name>`.\n\u200b",
                "Inventory");

        // Add inventory items here
        embed.addFields(build(event.getProfile(),
                new InventoryCategory("📦", "Boxes",
                        InventoryItem.box(Boxes.HOURLY),
                        InventoryItem.box(Boxes.DAILY),
                        InventoryItem.box(Boxes.MEMBER),
                        InventoryItem.box(Boxes.MYTHICAL)
                )
        ));

        event.reply(embed);
    }

    private static MessageEmbed.Field[] build(Profile profile, InventoryCategory... categories) {
        MessageEmbed.Field[] fields = Arrays.stream(categories)
                .map(category -> category.build(profile))
                .filter(Objects::nonNull)
                .toArray(MessageEmbed.Field[]::new);

        if (fields.length == 0) {
            return new MessageEmbed.Field[]{
                    new MessageEmbed.Field("*Empty*", "", true)
            };
        } else {
            return fields;
        }
    }

    private record InventoryCategory(String emoji, String name, InventoryItem... items) {

        MessageEmbed.Field build(Profile profile) {
            String value = Arrays.stream(items)
                    .filter(item -> item.quantityGetter.apply(profile) > 0)
                    .map(item -> item.build(profile))
                    .collect(Collectors.joining("\n")
                    );

            if (value.isEmpty()) {
                return null;
            } else {
                return new MessageEmbed.Field(emoji + " " + name,
                        value + "\n\u200b",
                        true);
            }
        }
    }

    private record InventoryItem(String itemName, Function<Profile, Integer> quantityGetter) {

        static InventoryItem box(Boxes box) {
            return box(box.getName());
        }

        static InventoryItem box(String name) {
            return new InventoryItem(name,
                    profile -> (int) profile.getInventory().stream()
                            .filter(item -> item instanceof Box && item.getName().equals(name))
                            .count()
            );
        }

        String build(Profile profile) {
            Item item = CollectibleHolder.getItem(itemName);
            int quantity = quantityGetter.apply(profile);

            return "%s %s %s".formatted(
                    quantity == 1 ? AIR : "*" + quantity + "x* \u200b",
                    item.getEmoji(),
                    item.getName()
            );
        }
    }

}
