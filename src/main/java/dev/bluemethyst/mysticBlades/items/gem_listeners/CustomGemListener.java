package dev.bluemethyst.mysticBlades.items.gem_listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class CustomGemListener implements Listener {
    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item != null && item.hasItemMeta() && "Sapphire".equals(item.getItemMeta().getDisplayName())) {
            // Add custom behavior here
            event.getPlayer().sendMessage("You used the gem");
        }
    }
    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        ItemStack newItem = event.getPlayer().getInventory().getItem(event.getNewSlot());
        ItemStack previousItem = event.getPlayer().getInventory().getItem(event.getPreviousSlot());

        if (newItem != null && newItem.hasItemMeta() && "Sapphire".equals(newItem.getItemMeta().getDisplayName())) {

        }

        if (previousItem != null && previousItem.hasItemMeta() && "Sapphire".equals(previousItem.getItemMeta().getDisplayName())) {

        }
    }
}
