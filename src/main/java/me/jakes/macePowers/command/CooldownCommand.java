package me.jakes.macePowers.command;

import me.jakes.macePowers.MacePowers;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class CooldownCommand implements CommandExecutor {
    private final static NamespacedKey abilityCooldownIdentifier = new NamespacedKey(MacePowers.getPlugin(), "ability_cooldown");
    private final static NamespacedKey dashCooldownIdentifier = new NamespacedKey(MacePowers.getPlugin(), "dash_cooldown");
    private final static NamespacedKey guiCooldownIdentifier = new NamespacedKey(MacePowers.getPlugin(), "god_mace_gui_cooldown");

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            player.getPersistentDataContainer().set(abilityCooldownIdentifier, PersistentDataType.LONG, 0L);
            player.getPersistentDataContainer().set(dashCooldownIdentifier, PersistentDataType.LONG, 0L);
            player.getPersistentDataContainer().set(guiCooldownIdentifier, PersistentDataType.LONG, 0L);
            player.sendMessage("Cooldowns reset");
        } else {
            commandSender.sendMessage("Only players can use this command!");
        }
        return true;
    }
}
