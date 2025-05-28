package me.jakes.macePowers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class GenericListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        MacePowers.checkAndDiscoverRecipes(event.getPlayer());
    }
}
