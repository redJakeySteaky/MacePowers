package me.jakes.macePowers.mace.kingsMace;

import me.jakes.macePowers.mace.CustomMaceListener;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class KingsMaceListener extends CustomMaceListener {

    public KingsMaceListener() {
        super(KingsMace.getInstance());
    }

    @Override
    protected void applyAbility(Player player) {
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, .5f, 1);
        player.getWorld().spawnParticle(Particle.EXPLOSION_EMITTER, player.getLocation().add(new Vector(0, 1, 0)), 1);
        player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 20 * 60, 4));
        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 20 * 60, 1));
        player.heal(20);
    }
}
