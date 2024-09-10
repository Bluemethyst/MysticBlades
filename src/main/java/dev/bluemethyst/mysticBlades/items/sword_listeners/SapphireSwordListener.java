package dev.bluemethyst.mysticBlades.items.sword_listeners;

import dev.bluemethyst.mysticBlades.utils.CooldownManager;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.UUID;

public class SapphireSwordListener implements Listener {

    private CooldownManager cooldownManager = new CooldownManager();
    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (item != null && item.hasItemMeta() && "Sapphire Sword".equals(item.getItemMeta().getDisplayName())) {
            Action action = event.getAction();
            // Check for right-click
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                if (cooldownManager.isOnCooldown(playerId, item)) {
                    long timeLeft = (cooldownManager.getCooldownEnd(playerId, item) - System.currentTimeMillis()) / 1000;
                    player.sendMessage("Your Sapphire Sword is on cooldown! Time left: " + timeLeft + " seconds.");
                    return;
                }
                // Get the player and the direction they are looking
                Vector direction = player.getLocation().getDirection();

                // Launch a fireball in the direction the player is looking
                Fireball fireball = player.launchProjectile(Fireball.class);
                fireball.setVelocity(direction.multiply(2));
                cooldownManager.setCooldown(player.getUniqueId(), item, 10);
            }
        }
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        ItemStack newItem = event.getPlayer().getInventory().getItem(event.getNewSlot());
        ItemStack previousItem = event.getPlayer().getInventory().getItem(event.getPreviousSlot());

        if (newItem != null && newItem.hasItemMeta() && "Sapphire Sword".equals(newItem.getItemMeta().getDisplayName())) {
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, PotionEffect.INFINITE_DURATION, 1));
        }

        if (previousItem != null && previousItem.hasItemMeta() && "Sapphire Sword".equals(previousItem.getItemMeta().getDisplayName())) {
            event.getPlayer().removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        }
    }

}