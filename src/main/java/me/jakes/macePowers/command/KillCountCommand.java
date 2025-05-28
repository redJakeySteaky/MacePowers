package me.jakes.macePowers.command;

import me.jakes.macePowers.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class KillCountCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Total global killcount is: " + DataManager.getInstance().getTotalKillCount());
            return true;
        }

        if (args.length == 1) {
            sender.sendMessage("Usage: /killcount [player|mace] <item>");
            return true;
        }

        if (args.length == 2 && args[0].equals("mace")) {
            switch (args[1]) {
                case "starwrought":
                    sender.sendMessage("StarWrought killcount is: " + DataManager.getInstance().getMaceKillCount(1));
                    break;
                case "arachnidstreasure":
                    sender.sendMessage("Arachnid's Treasure killcount is: " + DataManager.getInstance().getMaceKillCount(2));
                    break;
                case "kingsmace":
                    sender.sendMessage("King's Mace killcount is: " + DataManager.getInstance().getMaceKillCount(3));
                    break;
                case "godmace":
                    sender.sendMessage("GOD Mace killcount is: " + DataManager.getInstance().getMaceKillCount(4));
                    break;
                default:
                    sender.sendMessage("Mace " + args[1] + " not found.");
                    break;
            }
            return true;
        } else if (args.length == 2 && args[0].equals("player")) {
            Player player = Bukkit.getPlayer(args[1]);
            if (player == null) {
                sender.sendMessage("Player " + args[1] + " not found.");
                return true;
            }
            sender.sendMessage("Total killcount for " + args[1] + " is " + DataManager.getInstance().getPlayerTotalKillCount(player));
        } else if (args.length == 3) {
            Player player = Bukkit.getPlayer(args[1]);
            if (player == null) {
                sender.sendMessage("Player " + args[1] + " not found.");
                return true;
            }
            switch (args[2]) {
                case "starwrought":
                    sender.sendMessage(args[1] + "'s StarWrought killcount is: " + DataManager.getInstance().getPlayerMaceKillCount(player, 1));
                    break;
                case "arachnidstreasure":
                    sender.sendMessage(args[1] + "'s Arachnid's Treasure killcount is: " + DataManager.getInstance().getPlayerMaceKillCount(player, 2));
                    break;
                case "kingsmace":
                    sender.sendMessage(args[1] + "'s King's Mace killcount is: " + DataManager.getInstance().getPlayerMaceKillCount(player, 3));
                    break;
                case "godmace":
                    sender.sendMessage(args[1] + "'s GOD Mace killcount is: " + DataManager.getInstance().getPlayerMaceKillCount(player, 4));
                    break;
                default:
                    sender.sendMessage("Mace " + args[2] + " not found.");
                    break;
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return Stream.of("player", "mace")
                    .filter(s -> s.startsWith(args[0].toLowerCase()))
                    .toList();
        } else if (args.length == 2) {
            if (args[0].equals("player")) {
                OfflinePlayer[] offlinePlayers = Bukkit.getOfflinePlayers();
                return Arrays.stream(offlinePlayers)
                        .map(OfflinePlayer::getName)
                        .filter(Objects::nonNull)
                        .filter(s -> s.startsWith(args[1]))
                        .toList();
            } else if (args[0].equals("mace")) {
                return Stream.of("starwrought", "arachnidstreasure", "kingsmace", "godmace")
                        .filter(s -> s.startsWith(args[1].toLowerCase()))
                        .toList();
            }
        } else if (args.length == 3 && args[0].equals("player")) {
            return Stream.of("starwrought", "arachnidstreasure", "kingsmace", "godmace")
                    .filter(s -> s.startsWith(args[2].toLowerCase()))
                    .toList();
        }


        return List.of();
    }
}
