package me.jakes.macePowers.mace.kingsMace;

import me.jakes.macePowers.MacePowers;
import me.jakes.macePowers.mace.CustomMace;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.NamespacedKey;

public class KingsMace extends CustomMace {
    private static final KingsMace instance = new KingsMace();

    protected KingsMace() {
        super(Component.text("King's Mace", NamedTextColor.GRAY, TextDecoration.BOLD), new NamespacedKey(MacePowers.getPlugin(), "kings_mace"), 180, 10);
    }

    public static KingsMace getInstance() {
        return instance;
    }
}
