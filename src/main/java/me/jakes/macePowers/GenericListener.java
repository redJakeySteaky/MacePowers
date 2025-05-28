package me.jakes.macePowers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class GenericListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        MacePowers.checkAndDiscoverRecipes(event.getPlayer());
    }

    @EventHandler
    public void onCraftEvent(CraftItemEvent event) {
        if (event.getRecipe().getResult().getPersistentDataContainer().has(new NamespacedKey(MacePowers.getPlugin(), "starwrought"))) {
            MacePowers.broadcastTitle(Title.title(Component.text("StarWrought Has Been Crafted!"), Component.empty()));
            MacePowers.removeRecipes();
        } else if (event.getRecipe().getResult().getPersistentDataContainer().has(new NamespacedKey(MacePowers.getPlugin(), "arachnids_treasure"))) {
            MacePowers.broadcastTitle(Title.title(Component.text("Arachnid's Treasure Has Been Crafted!"), Component.empty()));
            MacePowers.removeRecipes();
        } else if (event.getRecipe().getResult().getPersistentDataContainer().has(new NamespacedKey(MacePowers.getPlugin(), "kings_mace"))) {
            MacePowers.broadcastTitle(Title.title(Component.text("King's Mace Has Been Crafted!"), Component.empty()));
            MacePowers.removeRecipes();
        } else if (event.getRecipe().getResult().getPersistentDataContainer().has(new NamespacedKey(MacePowers.getPlugin(), "god_mace"))) {
            MacePowers.broadcastTitle(Title.title(Component.text("GOD Mace Has Been Crafted!"), Component.empty()));
            MacePowers.removeRecipes();
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getPlayer();
        Player killer = victim.getKiller();
        if (killer != null) {
            boolean added = false;
            ItemStack item = killer.getInventory().getItemInMainHand();
            if (item.getType() == Material.MACE && item.hasItemMeta()) {
                if (item.getPersistentDataContainer().has(new NamespacedKey(MacePowers.getPlugin(), "starwrought"))) {
                    addKillCount(1);
                    added = true;
                } else if (item.getPersistentDataContainer().has(new NamespacedKey(MacePowers.getPlugin(), "arachnids_treasure"))) {
                    addKillCount(2);
                    added = true;
                } else if (item.getPersistentDataContainer().has(new NamespacedKey(MacePowers.getPlugin(), "kings_mace"))) {
                    addKillCount(3);
                    added = true;
                } else if (item.getPersistentDataContainer().has(new NamespacedKey(MacePowers.getPlugin(), "god_mace"))) {
                    addKillCount(4);
                    added = true;
                }
            }
            if (!added) {
                addKillCount(5);
            }
        }
    }

    private void addKillCount(int mace) {
        DataManager.getInstance().setKillCount(mace, DataManager.getInstance().getKillCount(mace) + 1);
    }
}
