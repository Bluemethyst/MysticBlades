package dev.bluemethyst.mysticBlades.items.swords;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class WealthSword extends BaseSword {
    public WealthSword() {
        super(Material.DIAMOND_SWORD, "Wealth Sword", createEnchantments());
    }
    private static Map<Enchantment, Integer> createEnchantments() {
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        enchantments.put(Enchantment.SHARPNESS, 10);
        enchantments.put(Enchantment.UNBREAKING, 5);
        return enchantments;
    }
}