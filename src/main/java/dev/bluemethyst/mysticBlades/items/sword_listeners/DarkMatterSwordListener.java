package dev.bluemethyst.mysticBlades.items.sword_listeners;

import dev.bluemethyst.mysticBlades.utils.CooldownManager;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.UUID;

public class DarkMatterSwordListener implements Listener {

    private CooldownManager cooldownManager = new CooldownManager();
    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        if (item != null && item.hasItemMeta() && "Dark Matter Sword".equals(item.getItemMeta().getDisplayName()) && event.getPlayer().isSneaking()) {
            Action action = event.getAction();

            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                if (cooldownManager.isOnCooldown(playerId, item)) {
                    long timeLeft = (cooldownManager.getCooldownEnd(playerId, item) - System.currentTimeMillis()) / 1000;
                    player.sendMessage("Your Dark Matter Sword is on cooldown! Time left: " + timeLeft + " seconds.");
                    return;
                }
                // Get the player and the direction they are looking
                Vector direction = player.getLocation().getDirection();

                // Launch a fireball in the direction the player is looking
                DragonFireball fireball = player.launchProjectile(DragonFireball.class);
                fireball.setVelocity(direction.multiply(2));
                cooldownManager.setCooldown(playerId, item, 60);
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
            Player player = (Player) event.getDamager();
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (player.getInventory().getItemInMainHand().hasItemMeta() && "Dark Matter Sword".equals(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName())) {
                Random random = new Random();
                if (random.nextDouble() < 0.1) {
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 1));
                }
            }
        }
    }
}