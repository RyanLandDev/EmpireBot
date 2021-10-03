package net.ryanland.empire.bot.command.impl.gameplay.items;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.utils.MarkdownSanitizer;
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
import net.ryanland.empire.sys.gameplay.collectible.potion.DefenseBuildingDamagePotion;
import net.ryanland.empire.sys.gameplay.collectible.potion.Potion;
import net.ryanland.empire.sys.message.builders.PresetBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
            "Here is an overview of all the items in your inventory.\nYou can use an item with `/use <id>`.\n\u200b",
            "Inventory");

        // Add inventory items here
        embed.addFields(build(event.getProfile(),
            new InventoryCategory("📦", "Boxes",
                InventoryItem.box(Boxes.HOURLY),
                InventoryItem.box(Boxes.DAILY),
                InventoryItem.box(Boxes.MEMBER),
                InventoryItem.box(Boxes.MYTHICAL)
            ),
            new InventoryCategory(POTION, "Potions",
                InventoryItem.item(new DefenseBuildingDamagePotion()
                    .set(Potion.Multiplier.ONE_AND_A_HALF, Potion.Length.FIVE_MINUTES, Potion.Scope.USER)))
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
                new MessageEmbed.Field("*Empty*", "", false)
            };
        } else {
            return fields;
        }
    }

    private record InventoryCategory(String emoji, String name, InventoryItem... items) {

        MessageEmbed.@Nullable Field build(Profile profile) {
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
                    false);
            }
        }
    }

    private record InventoryItem(Item item, Function<Profile, Integer> quantityGetter) {

        @Contract("_ -> new")
        static @NotNull InventoryItem box(Boxes box) {
            return item(CollectibleHolder.getItem(box.getId()));
        }

        @Contract("_ -> new")
        static @NotNull InventoryItem item(Item item) {
            return new InventoryItem(item,
                profile -> (int) profile.getInventory().stream()
                    .filter(invItem -> invItem.equals(item))
                    .count()
            );
        }

        String build(Profile profile) {
            int quantity = quantityGetter.apply(profile);

            return (quantity == 1 ? AIR : "*" + quantity + "x* \u200b") +
                " " + MarkdownSanitizer.sanitize(item.format()) +
                "\n" + AIR + " ID: `" + item.getIdentifier() + "`";
        }
    }

}
