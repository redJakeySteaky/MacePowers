package me.jakes.macePowers.mace.arachnidsTreasure;

import me.jakes.macePowers.mace.CustomMaceHandler;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashMap;
import java.util.UUID;

public class ArachnidsTreasureHandler extends CustomMaceHandler {

    HashMap<UUID, Long> playerCobwebTimeout = new HashMap<>();

    private static final int cobwebTimeoutSeconds = 20;

    private static final int cobwebRadius = 50;

    public ArachnidsTreasureHandler() {
        super(ArachnidsTreasure.getInstance());
    }

    // for determining if players can place cobwebs
    @EventHandler
    public void onCobwebPlace(BlockPlaceEvent event) {
        if (playerCobwebTimeout.isEmpty()) {
            return;
        }

        if (event.getBlock().getType()==Material.COBWEB) {
            // cycle through set removing old players
            for (UUID id : playerCobwebTimeout.keySet()) {
                long timeElapsedSeconds = (System.currentTimeMillis() - playerCobwebTimeout.get(id)) / 1000;
                if (timeElapsedSeconds >= cobwebTimeoutSeconds) {
                    playerCobwebTimeout.remove(id);
                } else if (event.getPlayer().getUniqueId() == id) {
                    cancelCobweb(event);
                }
            }
            checkNearbyEntities(event);
        }
    }

    private void cancelCobweb(BlockPlaceEvent event) {
        event.setCancelled(true);
        event.getBlock().getWorld().spawnParticle(Particle.LARGE_SMOKE, event.getBlock().getLocation(), 1);
        event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.BLOCK_CANDLE_EXTINGUISH, 1, 1);
    }

    private void checkNearbyEntities(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        for (Entity entity : player.getNearbyEntities(cobwebRadius, cobwebRadius, cobwebRadius)) {
            if (entity instanceof Player nearbyPlayer) {
                if (playerCobwebTimeout.containsKey(nearbyPlayer.getUniqueId())) {
                    if (loc.distance(entity.getLocation()) <= cobwebRadius) {
                        cancelCobweb(event);
                    }
                }
            }
        }
    }

    @Override
    protected void applyAbility(Player player) {
        player.getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_THUNDER, 1.5f, .75f);
        clearCobwebs(player);
        playerCobwebTimeout.put(player.getUniqueId(), System.currentTimeMillis());
    }

    private void clearCobwebs(Player player) {

        World world = player.getWorld();
        Location center = player.getLocation();

        world.playSound(center, Sound.BLOCK_FIRE_EXTINGUISH, 1, 1.5f);

        int cx = center.getBlockX();
        int cy = center.getBlockY();
        int cz = center.getBlockZ();

        for (int x = -cobwebRadius; x <= cobwebRadius; x++) {
            for (int y = -cobwebRadius; y <= cobwebRadius; y++) {
                for (int z = -cobwebRadius; z <= cobwebRadius; z++) {
                    Location loc = new Location(world, cx + x, cy + y, cz + z);
                    if (loc.distance(center) <= cobwebRadius && loc.getBlock().getType() == Material.COBWEB) {
                        loc.getBlock().setType(Material.AIR);
                        world.spawnParticle(Particle.LARGE_SMOKE, loc, 1);
                    }
                }
            }
        }
    }
}
