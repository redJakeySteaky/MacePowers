package me.jakes.macePowers;

import me.jakes.macePowers.mace.arachnidsTreasure.ArachnidsTreasure;
import me.jakes.macePowers.mace.arachnidsTreasure.ArachnidsTreasureHandler;
import me.jakes.macePowers.command.GiveCommand;
import me.jakes.macePowers.mace.godMace.GodMaceHandler;
import me.jakes.macePowers.mace.kingsMace.KingsMace;
import me.jakes.macePowers.mace.kingsMace.KingsMaceHandler;
import me.jakes.macePowers.mace.starWrought.StarWrought;
import me.jakes.macePowers.mace.starWrought.StarWroughtHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MacePowers extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new StarWroughtHandler(), this);
        getServer().getPluginManager().registerEvents(new ArachnidsTreasureHandler(), this);
        getServer().getPluginManager().registerEvents(new KingsMaceHandler(), this);
        getServer().getPluginManager().registerEvents(new GodMaceHandler(), this);

        registerStarWroughtRecipe();
        registerArachnidsTreasureRecipe();
        registerKingsMaceRecipe();

        Objects.requireNonNull(getCommand("macepowers")).setExecutor(new GiveCommand());
        Objects.requireNonNull(getCommand("macepowers")).setExecutor(new GiveCommand());

        getLogger().info("MacePowers Successfully Loaded!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static MacePowers getPlugin() {
        return JavaPlugin.getPlugin(MacePowers.class);
    }

    private void registerStarWroughtRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(MacePowers.getPlugin(), "starwrought_recipe"), StarWrought.getInstance().getMace());
        recipe.shape(
                "KFK",
                "RCR",
                "KFK"
        );
        recipe.setIngredient('K', Material.OMINOUS_TRIAL_KEY);
        recipe.setIngredient('F', Material.FEATHER);
        recipe.setIngredient('R', Material.RABBIT_FOOT);
        recipe.setIngredient('C', Material.HEAVY_CORE);
        Bukkit.addRecipe(recipe);
    }

    private void registerArachnidsTreasureRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(MacePowers.getPlugin(), "arachnids_treasure_recipe"), ArachnidsTreasure.getInstance().getMace());
        recipe.shape(
                "KWO",
                "BCB",
                "OWK"
        );
        recipe.setIngredient('K', Material.OMINOUS_TRIAL_KEY);
        recipe.setIngredient('W', Material.COBWEB);
        recipe.setIngredient('B', Material.BREEZE_ROD);
        recipe.setIngredient('O', Material.OMINOUS_BOTTLE);
        recipe.setIngredient('C', Material.HEAVY_CORE);
        Bukkit.addRecipe(recipe);
    }

    private void registerKingsMaceRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(MacePowers.getPlugin(), "kings_mace_recipe"), KingsMace.getInstance().getMace());
        recipe.shape(
                "GTG",
                "KCK",
                "GTG"
        );
        recipe.setIngredient('K', Material.OMINOUS_TRIAL_KEY);
        recipe.setIngredient('G', Material.GOLD_BLOCK);
        recipe.setIngredient('T', Material.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE);
        recipe.setIngredient('C', Material.HEAVY_CORE);
        Bukkit.addRecipe(recipe);
    }
}
