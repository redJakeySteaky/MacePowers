package me.jakes.macePowers.mace;

import me.jakes.macePowers.MacePowers;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

//this class has global(static) cooldown features built-in for a main ability and a dash ability
public abstract class CustomMaceHandler implements Listener {

    private static final HashMap<UUID, Long> playerMessageTimestamp = new HashMap<>();

    protected static final NamespacedKey dashCooldownIdentifier = new NamespacedKey(MacePowers.getPlugin(), "dash_cooldown");
    protected final int dashCooldownSeconds;

    protected static final NamespacedKey abilityCooldownIdentifier = new NamespacedKey(MacePowers.getPlugin(), "ability_cooldown");
    protected final int abilityCooldownSeconds;

    protected final CustomMace customMaceInstance;

    protected CustomMaceHandler(CustomMace customMaceInstance) {
        this.customMaceInstance = customMaceInstance;
        this.abilityCooldownSeconds = customMaceInstance.getAbilityCooldown();
        this.dashCooldownSeconds = customMaceInstance.getDashCooldown();
    }

    protected void startCooldown(Player player, NamespacedKey cooldownType) {
        player.getPersistentDataContainer().set(cooldownType, PersistentDataType.LONG, System.currentTimeMillis());
    }

    protected boolean isNotOnCooldown(Player player, NamespacedKey cooldownType) {
        return getCooldownSeconds(player, cooldownType) <= 0;
    }

    protected void messagePlayerCooldown(Player player, NamespacedKey cooldownType) {
        // purpose of function is to limit player from seeing cooldown message to every 2.5 seconds
        if (!playerMessageTimestamp.containsKey(player.getUniqueId())) {
            playerMessageTimestamp.put(player.getUniqueId(), System.currentTimeMillis());
            player.sendMessage(Component.text("Cooldown: " + getCooldownSeconds(player, cooldownType) + " seconds left!", NamedTextColor.RED));
        } else {
            if (System.currentTimeMillis() - playerMessageTimestamp.get(player.getUniqueId()) >= 500) {
                playerMessageTimestamp.put(player.getUniqueId(), System.currentTimeMillis());
                player.sendMessage(Component.text("Cooldown: " + getCooldownSeconds(player, cooldownType) + " seconds left!", NamedTextColor.RED));
            }
        }
    }

    protected int getCooldownSeconds(Player player, NamespacedKey cooldownType) {
        if (cooldownType == abilityCooldownIdentifier) {
            return getTimeRemaining(player, cooldownType, abilityCooldownSeconds);
        } else if (cooldownType == dashCooldownIdentifier) {
            return getTimeRemaining(player, cooldownType, dashCooldownSeconds);
        }
        return 0;
    }

    protected int getTimeRemaining(Player player, NamespacedKey cooldownType, int totalTime) {
        long timeStarted = player.getPersistentDataContainer().getOrDefault(cooldownType, PersistentDataType.LONG, 0L);

        // current time - time started = time elapsed
        // convert to seconds
        int timeElapsedSeconds = Math.toIntExact((System.currentTimeMillis() - timeStarted) / 1000);

        // cooldown remaining (total cooldown time required - elapsed time)
        return totalTime - timeElapsedSeconds;
    }

    //main ability
    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getPersistentDataContainer().has(customMaceInstance.getIdentifier())) {
            // if right click
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                Player player = event.getPlayer();
                // if not on cooldown
                if (isNotOnCooldown(player, abilityCooldownIdentifier)) {
                    startCooldown(player, abilityCooldownIdentifier);
                    applyAbility(player);
                } else {
                    // if on cooldown
                    messagePlayerCooldown(player, abilityCooldownIdentifier);
                }
            }
        }
    }

    protected abstract void applyAbility(Player player);

    //dash ability
    @EventHandler
    public void onOffhandSwap(PlayerSwapHandItemsEvent event) {
        if (event.getOffHandItem().getPersistentDataContainer().has(customMaceInstance.getIdentifier(), PersistentDataType.BOOLEAN)) {
            event.setCancelled(true);
            Player player = event.getPlayer();

            if (isNotOnCooldown(player, dashCooldownIdentifier)) {
                startCooldown(player, dashCooldownIdentifier);
                applyDash(player);
            } else {
                // if on cooldown
                messagePlayerCooldown(player, dashCooldownIdentifier);
            }
        }
    }

    private void applyDash(Player player) {
        Vector velocity = player.getLocation().getDirection().normalize().multiply(2);

        // min and max y velocities
        if (velocity.getY() < .5) {
            velocity.setY(.5);
        } else if (velocity.getY() > 1.5) {
            velocity.setY(1.5);
        }
        player.setVelocity(velocity);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WIND_CHARGE_WIND_BURST, 1, 1);
        player.getWorld().spawnParticle(Particle.GUST_EMITTER_SMALL, player.getLocation(), 1);
    }
}
