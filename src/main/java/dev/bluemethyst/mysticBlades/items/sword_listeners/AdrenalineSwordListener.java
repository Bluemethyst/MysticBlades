package dev.bluemethyst.mysticBlades.items.sword_listeners;

import dev.bluemethyst.mysticBlades.utils.CooldownManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.UUID;

public class AdrenalineSwordListener implements Listener {

    private CooldownManager cooldownManager = new CooldownManager();
    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (item != null && item.hasItemMeta() && "Adrenaline Sword".equals(item.getItemMeta().getDisplayName()) && event.getPlayer().isSneaking()) {

            if (cooldownManager.isOnCooldown(playerId, item)) {
                long timeLeft = (cooldownManager.getCooldownEnd(playerId, item) - System.currentTimeMillis()) / 1000;
                player.sendMessage("Your Adrenaline Sword is on cooldown! Time left: " + timeLeft + " seconds.");
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 300, 10));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 3));

            cooldownManager.setCooldown(playerId, item, 60);
            player.sendMessage("Adrenaline Sword activated! Cooldown started.");
        }
    }
    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        ItemStack newItem = event.getPlayer().getInventory().getItem(event.getNewSlot());
        ItemStack previousItem = event.getPlayer().getInventory().getItem(event.getPreviousSlot());

        if (newItem != null && newItem.hasItemMeta() && "Adrenaline Sword".equals(newItem.getItemMeta().getDisplayName())) {
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, PotionEffect.INFINITE_DURATION, 1));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HASTE, PotionEffect.INFINITE_DURATION, 2));
        }

        if (previousItem != null && previousItem.hasItemMeta() && "Adrenaline Sword".equals(previousItem.getItemMeta().getDisplayName())) {
            event.getPlayer().removePotionEffect(PotionEffectType.SPEED);
            event.getPlayer().removePotionEffect(PotionEffectType.HASTE);
        }
    }
}