package dev.bluemethyst.mysticBlades.items.swords;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class AdrenalineSword extends BaseSword{
    private ItemStack item;

    public AdrenalineSword() {
        super(Material.DIAMOND_SWORD, "Adrenaline Sword", createEnchantments());
    }

    private static Map<Enchantment, Integer> createEnchantments() {
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        enchantments.put(Enchantment.SHARPNESS, 10);
        return enchantments;
    }
}