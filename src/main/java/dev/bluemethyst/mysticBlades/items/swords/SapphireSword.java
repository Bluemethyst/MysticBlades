package dev.bluemethyst.mysticBlades.items.swords;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import java.util.HashMap;
import java.util.Map;

public class SapphireSword extends BaseSword {

    public SapphireSword() {
        super(Material.DIAMOND_SWORD, "Sapphire Sword", createEnchantments());
    }

    private static Map<Enchantment, Integer> createEnchantments() {
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        enchantments.put(Enchantment.SHARPNESS, 10);
        enchantments.put(Enchantment.UNBREAKING, 5);
        return enchantments;
    }
}
