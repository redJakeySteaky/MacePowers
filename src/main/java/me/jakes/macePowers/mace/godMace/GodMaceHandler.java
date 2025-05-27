package me.jakes.macePowers.mace.godMace;

import me.jakes.macePowers.MacePowers;
import me.jakes.macePowers.mace.CustomMaceHandler;
import me.jakes.macePowers.mace.arachnidsTreasure.ArachnidsTreasure;
import me.jakes.macePowers.mace.kingsMace.KingsMace;
import me.jakes.macePowers.mace.starWrought.StarWrought;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class GodMaceHandler extends CustomMaceHandler {

    private final NamespacedKey guiCooldownIdentifier = new NamespacedKey(MacePowers.getPlugin(), "god_mace_gui_cooldown");
    private final int guiCooldownSeconds = GodMace.getInstance().getGuiCooldownSeconds();

    public GodMaceHandler() {
        super(GodMace.getInstance());
    }

    private int maceChosen = 0;
    // 0 none
    // 1 star
    // 2 arach
    // 3 king

    @Override
    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getPersistentDataContainer().has(customMaceInstance.getIdentifier())) {
            // if right click
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {

                Player player = event.getPlayer();
                if (player.isSneaking()) {
                    // if not on cooldown
                    if (isNotOnCooldown(player, guiCooldownIdentifier)) {
                        openGUI(player);
                    } else {
                        // if on cooldown
                        messagePlayerCooldown(player, guiCooldownIdentifier);
                    }

                    //check to make sure gui is not open
                } else if (player.getOpenInventory().getType() != InventoryType.CRAFTING) {
                    // if not on cooldown
                    if (isNotOnCooldown(player, abilityCooldownIdentifier)) {
                        applyAbility(player);
                        if(maceChosen == 0) {
                            startCooldown(player, abilityCooldownIdentifier);
                        }
                    } else {
                        // if on cooldown
                        messagePlayerCooldown(player, abilityCooldownIdentifier);
                    }
                }
            }
        }
    }


    @Override
    protected void applyAbility(Player player) {
        switch (maceChosen) {
            case 0:
                break;
        }
    }

    private void openGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 9, Component.text("Choose Mace"));

        // add maces
        gui.setItem(2, StarWrought.getInstance().getMace());
        gui.setItem(4, ArachnidsTreasure.getInstance().getMace());
        gui.setItem(6, KingsMace.getInstance().getMace());

        player.openInventory(gui);
    }

    @Override
    protected int getCooldownSeconds(Player player, NamespacedKey cooldownType) {
        if (cooldownType == abilityCooldownIdentifier) {
            return getTimeRemaining(player, cooldownType, abilityCooldownSeconds);
        } else if (cooldownType == dashCooldownIdentifier) {
            return getTimeRemaining(player, cooldownType, dashCooldownSeconds);
        } else if (cooldownType == guiCooldownIdentifier) {
            return getTimeRemaining(player, cooldownType, guiCooldownSeconds);
        }
        return 0;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        InventoryView view = event.getView();
        if (!Component.text("Choose Mace").equals(view.title())) return;

        event.setCancelled(true); // Prevent item movement

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || clicked.getType() == Material.AIR) return;

        // Handle buttons by slot or item
        switch (event.getSlot()) {
            case 2 -> {
                startCooldown(player, guiCooldownIdentifier);
                player.sendMessage("You chose StarWrought!");
            }
            case 4 -> {
                startCooldown(player, guiCooldownIdentifier);
                player.sendMessage("You chose Arachnid's Treasure!");
            }
            case 6 -> {
                startCooldown(player, guiCooldownIdentifier);
                player.sendMessage("You chose King's Mace!");
            }
            default -> {
                return;
            } // Do nothing
        }

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 1);
        player.closeInventory();
    }
}
