package me.jakes.macePowers.mace.godMace;

import me.jakes.macePowers.MacePowers;
import me.jakes.macePowers.mace.CustomMace;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.NamespacedKey;

public class GodMace extends CustomMace {
    private static final GodMace instance = new GodMace();

    protected GodMace() {
        super(Component.text("GOD Mace", NamedTextColor.GRAY, TextDecoration.BOLD), new NamespacedKey(MacePowers.getPlugin(), "god_mace"), 180, 10);
    }

    public static GodMace getInstance() {
        return instance;
    }

    public int getGuiCooldownSeconds() {
        return 300;
    }
}
