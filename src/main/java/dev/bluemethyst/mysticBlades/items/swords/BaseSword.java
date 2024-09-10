package dev.bluemethyst.mysticBlades.items.swords;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Map;

public abstract class BaseSword {
    protected ItemStack item;

    public BaseSword(Material material, String displayName, Map<Enchantment, Integer> enchantments) {
        item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(displayName);
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                meta.addEnchant(entry.getKey(), entry.getValue(), true);
            }
            item.setItemMeta(meta);
        }
    }

    public ItemStack getItem() {
        return item;
    }
}
