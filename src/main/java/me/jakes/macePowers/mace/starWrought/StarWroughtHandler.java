package me.jakes.macePowers.mace.starWrought;

import me.jakes.macePowers.mace.CustomMaceHandler;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class StarWroughtHandler extends CustomMaceHandler {

    public StarWroughtHandler() {
        super(StarWrought.getInstance());
    }

    @Override
    protected void applyAbility(Player player) {
        player.setVelocity(new Vector(0, 2, 0));
        player.getWorld().playSound(player.getLocation(), Sound.ITEM_MACE_SMASH_GROUND_HEAVY, 1.5f, .6f);
        player.getWorld().spawnParticle(Particle.GUST_EMITTER_LARGE, player.getLocation(), 2);

        for (Entity entity : player.getNearbyEntities(10, 10, 10)) {
            if (entity instanceof Player nearbyPlayer) {
                nearbyPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 200, 1));
            }
        }
    }
}
