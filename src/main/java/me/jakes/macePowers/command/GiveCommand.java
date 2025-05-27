package me.jakes.macePowers.command;

import me.jakes.macePowers.mace.arachnidsTreasure.ArachnidsTreasure;
import me.jakes.macePowers.mace.godMace.GodMace;
import me.jakes.macePowers.mace.kingsMace.KingsMace;
import me.jakes.macePowers.mace.starWrought.StarWrought;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class GiveCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (args.length != 2 || !args[0].equalsIgnoreCase("give")) {
            player.sendMessage("Usage: /macepowers give <item>");
            return true;
        }

        String itemName = args[1].toLowerCase();
        ItemStack item = switch (itemName) {
            case "starwrought" -> StarWrought.getInstance().getMace();
            case "arachnidstreasure" -> ArachnidsTreasure.getInstance().getMace();
            case "kingsmace" -> KingsMace.getInstance().getMace();
            case "godmace" -> GodMace.getInstance().getMace();
            default -> null;
        };

        if (item == null) {
            player.sendMessage("Unknown item: " + itemName);
            return true;
        }

        player.getInventory().addItem(item);
        player.sendMessage("Gave you: " + itemName);
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length == 1) {
            return List.of("give");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            return Stream.of("starwrought", "arachnidstreasure", "kingsmace", "godmace")
                    .filter(s -> s.startsWith(args[1].toLowerCase()))
                    .toList();
        }
        return Collections.emptyList();
    }
}