package me.jakes.macePowers;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class DataManager {
    private final static DataManager instance = new DataManager();

    private File file;
    private YamlConfiguration config;

    private DataManager() {
    }

    public static DataManager getInstance() {
        return instance;
    }

    public void setup() {
        file = new File(MacePowers.getPlugin().getDataFolder(), "data.yml");

        if (!file.exists()) {
            MacePowers.getPlugin().saveResource("data.yml", false);
        }

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(file);
        } catch (Exception e) {
            MacePowers.getPlugin().getLogger().severe("Failed to load data.yml!");
            MacePowers.getPlugin().getLogger().severe(e.getMessage());
        }
    }

    private void save() {
        try {
            config.save(file);
        } catch (Exception e) {
            MacePowers.getPlugin().getLogger().severe("Failed to save data.yml!");
            MacePowers.getPlugin().getLogger().severe(e.getMessage());
        }
    }

    public void saveMaceChosen(Player player, int maceChosen) {
        config.set("players." + player.getUniqueId() + ".maceChosen", maceChosen);
        save();
    }

    public int getMaceChosen(Player player) {
        return config.getInt("players." + player.getUniqueId() + ".maceChosen", 0);
    }

    public void saveStageInitiated(int stage, boolean bool) {
        config.set("stages-initiated." + stage, bool);
        save();
    }

    public boolean getStageInitiated(int stage) {
        return config.getBoolean("stages-initiated." + stage);
    }

    public void setMaceKillCount(int mace, int kills) {
        config.set("killcount." + mace, kills);
        save();
    }

    public int getMaceKillCount(int mace) {
        return config.getInt("killcount." + mace, 0);
    }

    public void setPlayerTotalKillCount(Player player, int kills) {
        config.set("killcount." + player.getUniqueId() + ".total", kills);
        save();
    }

    public int getPlayerTotalKillCount(Player player) {
        return config.getInt("killcount." + player.getUniqueId() + ".total", 0);
    }

    public void setPlayerMaceKillCount(Player player, int kills, int mace) {
        config.set("killcount." + player.getUniqueId() + "." + mace, kills);
        save();
    }

    public int getPlayerMaceKillCount(Player player, int mace) {
        return config.getInt("killcount." + player.getUniqueId() + "." + mace, 0);
    }

    public void setTotalKillCount(int kills) {
        config.set("killcount.total", kills);
        save();
    }

    public int getTotalKillCount() {
        return config.getInt("killcount.total", 0);
    }

}
