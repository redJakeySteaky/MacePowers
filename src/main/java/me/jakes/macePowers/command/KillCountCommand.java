package me.jakes.macePowers.command;

import me.jakes.macePowers.DataManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class KillCountCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Global killcount is: " + DataManager.getInstance().getKillCount(5));
            return true;
        }
        switch (args[0]) {
            case "starwrought":
                sender.sendMessage("StarWrought killcount is: " + DataManager.getInstance().getKillCount(1));
                break;
            case "arachnidstreasure":
                sender.sendMessage("Arachnid's Treasure killcount is: " + DataManager.getInstance().getKillCount(2));
                break;
            case "kingsmace":
                sender.sendMessage("King's Mace killcount is: " + DataManager.getInstance().getKillCount(3));
                break;
            case "godmace":
                sender.sendMessage("GOD Mace killcount is: " + DataManager.getInstance().getKillCount(4));
                break;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return Stream.of("starwrought", "arachnidstreasure", "kingsmace", "godmace")
                    .filter(s -> s.startsWith(args[0].toLowerCase()))
                    .toList();
        }
        return List.of();
    }
}
