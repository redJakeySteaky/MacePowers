package me.jakes.macePowers.command;

import me.jakes.macePowers.DataManager;
import me.jakes.macePowers.MacePowers;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class StageCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /givemace  <item>");
            return true;
        }

        String stageNum = args[0];

        switch (stageNum) {
            case "1":
                MacePowers.registerStarWroughtRecipe();
                DataManager.getInstance().saveStageInitiated(1, true);
                break;
            case "2":
                MacePowers.registerArachnidsTreasureRecipe();
                DataManager.getInstance().saveStageInitiated(2, true);
                break;
            case "3":
                MacePowers.registerKingsMaceRecipe();
                DataManager.getInstance().saveStageInitiated(3, true);
                break;
            case "4":
                MacePowers.registerGodMaceRecipe();
                DataManager.getInstance().saveStageInitiated(4, true);
                break;
            case "reset":
                MacePowers.removeRecipes();
                DataManager.getInstance().saveStageInitiated(1, false);
                DataManager.getInstance().saveStageInitiated(2, false);
                DataManager.getInstance().saveStageInitiated(3, false);
                DataManager.getInstance().saveStageInitiated(4, false);
                sender.sendMessage("All recipes for all stages have been disabled.");
                return true;
            default:
                sender.sendMessage("Stage: " + stageNum + " not recognized!");
                return true;
        }
        sender.sendMessage("Initiating stage: " + stageNum + "!");

        // discover recipes
        for (Player player : Bukkit.getOnlinePlayers()) {
            MacePowers.checkAndDiscoverRecipes(player);
        }


        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length == 1) {
            return Stream.of("1", "2", "3", "4", "reset")
                    .filter(s -> s.startsWith(args[0].toLowerCase()))
                    .toList();
        }
        return List.of();
    }
}
