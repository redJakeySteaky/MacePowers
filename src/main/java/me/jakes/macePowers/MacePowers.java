package me.jakes.macePowers;

import me.jakes.macePowers.command.CooldownCommand;
import me.jakes.macePowers.command.GiveMaceCommand;
import me.jakes.macePowers.command.StageCommand;
import me.jakes.macePowers.mace.arachnidsTreasure.ArachnidsTreasure;
import me.jakes.macePowers.mace.arachnidsTreasure.ArachnidsTreasureHandler;
import me.jakes.macePowers.mace.godMace.GodMace;
import me.jakes.macePowers.mace.godMace.GodMaceHandler;
import me.jakes.macePowers.mace.kingsMace.KingsMace;
import me.jakes.macePowers.mace.kingsMace.KingsMaceHandler;
import me.jakes.macePowers.mace.starWrought.StarWrought;
import me.jakes.macePowers.mace.starWrought.StarWroughtHandler;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MacePowers extends JavaPlugin {

    @Override
    public void onEnable() {
        DataManager.getInstance().setup();

        getServer().getPluginManager().registerEvents(new StarWroughtHandler(), this);
        getServer().getPluginManager().registerEvents(new ArachnidsTreasureHandler(), this);
        getServer().getPluginManager().registerEvents(new KingsMaceHandler(), this);
        getServer().getPluginManager().registerEvents(new GodMaceHandler(), this);
        getServer().getPluginManager().registerEvents(new GenericListener(), this);

        Objects.requireNonNull(getCommand("givemace")).setExecutor(new GiveMaceCommand());
        Objects.requireNonNull(getCommand("stage")).setExecutor(new StageCommand());
        Objects.requireNonNull(getCommand("cooldown")).setExecutor(new CooldownCommand());

        //initiate stages from data.yml
        for (int stage = 1; stage <= 4; stage++) {
            if (DataManager.getInstance().getStageInitiated(stage)) {
                switch (stage) {
                    case 1:
                        registerStarWroughtRecipe();
                        getLogger().info("Initiating Stage 1");
                        break;
                    case 2:
                        registerArachnidsTreasureRecipe();
                        getLogger().info("Initiating Stage 2");
                        break;
                    case 3:
                        registerKingsMaceRecipe();
                        getLogger().info("Initiating Stage 3");
                        break;
                    case 4:
                        registerGodMaceRecipe();
                        getLogger().info("Initiating Stage 4");
                        break;
                }
            }
        }

        getLogger().info("MacePowers Successfully Loaded!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static MacePowers getPlugin() {
        return JavaPlugin.getPlugin(MacePowers.class);
    }

    public static void registerStarWroughtRecipe() {
        NamespacedKey key = new NamespacedKey(MacePowers.getPlugin(), "starwrought_recipe");
        if (Bukkit.getRecipe(key) != null) {
            return;
        }
        ShapedRecipe recipe = new ShapedRecipe(key, StarWrought.getInstance().getMace());
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

    public static void registerArachnidsTreasureRecipe() {
        NamespacedKey key = new NamespacedKey(MacePowers.getPlugin(), "arachnids_treasure_recipe");
        if (Bukkit.getRecipe(key) != null) {
            return;
        }

        ShapedRecipe recipe = new ShapedRecipe(key, ArachnidsTreasure.getInstance().getMace());
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

    public static void registerKingsMaceRecipe() {
        NamespacedKey key = new NamespacedKey(MacePowers.getPlugin(), "kings_mace_recipe");
        if (Bukkit.getRecipe(key) != null) {
            return;
        }

        ShapedRecipe recipe = new ShapedRecipe(key, KingsMace.getInstance().getMace());
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

    public static void registerGodMaceRecipe() {
        NamespacedKey key = new NamespacedKey(MacePowers.getPlugin(), "god_mace_recipe");
        if (Bukkit.getRecipe(key) != null) {
            return;
        }


        ShapedRecipe recipe = new ShapedRecipe(key, GodMace.getInstance().getMace());

        recipe.shape(
                " A ",
                "SBK",
                "DDD"
        );
        recipe.setIngredient('A', ArachnidsTreasure.getInstance().getMace());
        recipe.setIngredient('S', StarWrought.getInstance().getMace());
        recipe.setIngredient('K', KingsMace.getInstance().getMace());
        recipe.setIngredient('B', Material.BEACON);
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        Bukkit.addRecipe(recipe);
    }

    public static void removeRecipes() {
        NamespacedKey key1 = new NamespacedKey(MacePowers.getPlugin(), "starwrought_recipe");
        NamespacedKey key2 = new NamespacedKey(MacePowers.getPlugin(), "arachnids_treasure_recipe");
        NamespacedKey key3 = new NamespacedKey(MacePowers.getPlugin(), "kings_mace_recipe");
        NamespacedKey key4 = new NamespacedKey(MacePowers.getPlugin(), "god_mace_recipe");

        Bukkit.removeRecipe(key1);
        Bukkit.removeRecipe(key2);
        Bukkit.removeRecipe(key3);
        Bukkit.removeRecipe(key4);
    }

    public static void checkAndDiscoverRecipes(Player p) {
        for (int stage = 1; stage <= 4; stage++) {
            if (DataManager.getInstance().getStageInitiated(stage)) {
                switch (stage) {
                    case 1:
                        p.discoverRecipe(new NamespacedKey(MacePowers.getPlugin(), "starwrought_recipe"));
                        break;
                    case 2:
                        p.discoverRecipe(new NamespacedKey(MacePowers.getPlugin(), "arachnids_treasure_recipe"));
                        break;
                    case 3:
                        p.discoverRecipe(new NamespacedKey(MacePowers.getPlugin(), "kings_mace_recipe"));
                        break;
                    case 4:
                        p.discoverRecipe(new NamespacedKey(MacePowers.getPlugin(), "god_mace_recipe"));
                        break;
                }
            }
        }
    }

    public static void broadcastTitle(Title message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.showTitle(message);
        }
    }
}
