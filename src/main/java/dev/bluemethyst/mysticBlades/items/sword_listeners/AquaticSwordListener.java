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

public class AquaticSwordListener implements Listener {

    private CooldownManager cooldownManager = new CooldownManager();
    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (item != null && item.hasItemMeta() && "Aquatic Sword".equals(item.getItemMeta().getDisplayName()) && event.getPlayer().isSneaking()) {

            if (cooldownManager.isOnCooldown(playerId, item)) {
                long timeLeft = (cooldownManager.getCooldownEnd(playerId, item) - System.currentTimeMillis()) / 1000;
                player.sendMessage("Your Aquatic Sword is on cooldown! Time left: " + timeLeft + " seconds.");
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 300, 5));

            cooldownManager.setCooldown(playerId, item, 60);
            player.sendMessage("Aquatic Sword activated! Cooldown started.");
        }
    }
    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        ItemStack newItem = event.getPlayer().getInventory().getItem(event.getNewSlot());
        ItemStack previousItem = event.getPlayer().getInventory().getItem(event.getPreviousSlot());

        if (newItem != null && newItem.hasItemMeta() && "Aquatic Sword".equals(newItem.getItemMeta().getDisplayName())) {
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, PotionEffect.INFINITE_DURATION, 1));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, PotionEffect.INFINITE_DURATION, 2));
        }

        if (previousItem != null && previousItem.hasItemMeta() && "Aquatic Sword".equals(previousItem.getItemMeta().getDisplayName())) {
            event.getPlayer().removePotionEffect(PotionEffectType.CONDUIT_POWER);
            event.getPlayer().removePotionEffect(PotionEffectType.DOLPHINS_GRACE);
        }
    }
}