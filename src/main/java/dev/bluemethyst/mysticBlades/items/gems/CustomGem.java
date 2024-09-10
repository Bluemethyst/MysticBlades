package dev.bluemethyst.mysticBlades.items.gems;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomGem {
    private ItemStack item;

    public CustomGem() {
        item = new ItemStack(Material.DIAMOND);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("Sapphire");
            meta.addEnchant(Enchantment.PROTECTION, 1, true);
            // Add more customizations here (e.g., lore, enchantments)
            item.setItemMeta(meta);
        }
    }

    public ItemStack getItem() {
        return item;
    }
}
