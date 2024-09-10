package dev.bluemethyst.mysticBlades.items.sword_listeners;

import dev.bluemethyst.mysticBlades.utils.CooldownManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.UUID;

public class HarmingSwordListener implements Listener {

    private CooldownManager cooldownManager = new CooldownManager();
    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (item != null && item.hasItemMeta() && "Harming Sword".equals(item.getItemMeta().getDisplayName())) {
            Action action = event.getAction();
            // Check for right-click
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                if (cooldownManager.isOnCooldown(playerId, item)) {
                    long timeLeft = (cooldownManager.getCooldownEnd(playerId, item) - System.currentTimeMillis()) / 1000;
                    player.sendMessage("Your Harming Sword is on cooldown! Time left: " + timeLeft + " seconds.");
                    return;
                }

                int numPotions = 8;

                // Get the player's location and direction for initial direction
                Vector playerDirection = player.getLocation().getDirection();
                double angleIncrement = 2 * Math.PI / numPotions; // 360 degrees / number of potions

                // Loop to spawn and shoot potions in different directions
                for (int i = 0; i < numPotions; i++) {
                    // Create the splash potion of harming
                    ThrownPotion splashPotion = player.getWorld().spawn(player.getEyeLocation(), ThrownPotion.class);
                    ItemStack copy = splashPotion.getItem();

                    // Create and apply a HARMING potion meta
                    PotionMeta potionMeta = (PotionMeta) splashPotion.getItem().getItemMeta();
                    potionMeta.setBasePotionType(PotionType.HARMING); // Harming potion
                    copy.setItemMeta(potionMeta);
                    splashPotion.setItem(copy);

                    // Calculate the angle for the current potion
                    double angle = i * angleIncrement;
                    Vector direction = rotateVector(playerDirection, angle);

                    // Set the velocity to the rotated direction with a multiplier for speed
                    splashPotion.setVelocity(direction.multiply(1.5));
                }
                cooldownManager.setCooldown(player.getUniqueId(), item, 10);
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            event.setDamage(0);
            entity.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_DAMAGE, 1, 1));
        }
    }

    private Vector rotateVector(Vector vec, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        // Only rotate on the XZ plane, leave Y unchanged
        double x = vec.getX() * cos - vec.getZ() * sin;
        double z = vec.getX() * sin + vec.getZ() * cos;

        return new Vector(x, vec.getY(), z).normalize(); // Normalize to maintain the same speed
    }
}