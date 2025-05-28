package me.jakes.macePowers.mace.starWrought;

import me.jakes.macePowers.MacePowers;
import me.jakes.macePowers.mace.CustomMace;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.NamespacedKey;

public class StarWrought extends CustomMace {

    private static final StarWrought instance = new StarWrought();

    protected StarWrought() {
        super(Component.text("StarWrought", NamedTextColor.BLUE, TextDecoration.BOLD), new NamespacedKey(MacePowers.getPlugin(), "starwrought"), 180, 10);
    }

    public static StarWrought getInstance() {
        return instance;
    }

}
