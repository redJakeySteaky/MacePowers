package me.jakes.macePowers.mace.arachnidsTreasure;

import me.jakes.macePowers.MacePowers;
import me.jakes.macePowers.mace.CustomMace;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.NamespacedKey;

public class ArachnidsTreasure extends CustomMace {

    private static final ArachnidsTreasure instance = new ArachnidsTreasure();

    protected ArachnidsTreasure() {
        super(Component.text("Arachnid's Treasure", NamedTextColor.GRAY, TextDecoration.BOLD), new NamespacedKey(MacePowers.getPlugin(), "arachnids_treasure"), 180, 10);
    }

    public static ArachnidsTreasure getInstance() {
        return instance;
    }
}
