package me.jakes.macePowers;

import me.jakes.macePowers.mace.arachnidsTreasure.ArachnidsTreasure;
import me.jakes.macePowers.mace.godMace.GodMace;
import me.jakes.macePowers.mace.kingsMace.KingsMace;
import me.jakes.macePowers.mace.starWrought.StarWrought;
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
            ItemStack item = killer.getInventory().getItemInMainHand();
            if (item.getType() == Material.MACE && item.hasItemMeta()) {
                if (item.getPersistentDataContainer().has(StarWrought.getInstance().getIdentifier())) {
                    addKillCount(killer, 1);
                } else if (item.getPersistentDataContainer().has(ArachnidsTreasure.getInstance().getIdentifier())) {
                    addKillCount(killer, 2);
                } else if (item.getPersistentDataContainer().has(KingsMace.getInstance().getIdentifier())) {
                    addKillCount(killer, 3);
                } else if (item.getPersistentDataContainer().has(GodMace.getInstance().getIdentifier())) {
                    addKillCount(killer, 4);
                }
            }

            //regardless if mace
            // add to total count
            addTotalKill();
            // add to player total count
            addPlayerTotalKill(killer);
        }
    }

    private void addKillCount(Player killer, int mace) {
        addPlayerMaceKill(killer, mace);
        addMaceKillCount(mace);
    }

    private void addPlayerTotalKill(Player killer) {
        DataManager.getInstance().setPlayerTotalKillCount(killer, DataManager.getInstance().getPlayerTotalKillCount(killer) + 1);
    }

    private void addPlayerMaceKill(Player killer, int mace) {
        // add to player mace count
        DataManager.getInstance().setPlayerMaceKillCount(killer, DataManager.getInstance().getPlayerMaceKillCount(killer, mace) + 1, mace);
    }

    private void addMaceKillCount(int mace) {
        DataManager.getInstance().setMaceKillCount(mace, DataManager.getInstance().getMaceKillCount(mace) + 1);
    }

    private void addTotalKill() {
        DataManager.getInstance().setTotalKillCount(DataManager.getInstance().getTotalKillCount() + 1);
    }
}