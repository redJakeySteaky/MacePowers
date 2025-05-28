package me.jakes.macePowers.mace;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class CustomMace {

    private final NamespacedKey identifier;

    private final ItemStack itemStack;

    private final int dashCooldown;
    private final int abilityCooldown;

    public int getDashCooldown() {
        return dashCooldown;
    }

    public int getAbilityCooldown() {
        return abilityCooldown;
    }

    protected CustomMace(Component name, NamespacedKey identifier, int abilityCooldown, int dashCooldown) {
        this.identifier = identifier;
        itemStack = new ItemStack(Material.MACE);
        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(name);
        meta.getPersistentDataContainer().set(identifier, PersistentDataType.BOOLEAN, true);
        itemStack.setItemMeta(meta);

        this.dashCooldown = dashCooldown;
        this.abilityCooldown = abilityCooldown;
    }

    public NamespacedKey getIdentifier() {
        return identifier;
    }

    public ItemStack getMace() {
        return itemStack;
    }
}
