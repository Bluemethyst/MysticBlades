package dev.bluemethyst.mysticBlades.items.sword_listeners;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import dev.bluemethyst.mysticBlades.utils.CooldownManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class InvisibilitySwordListener implements Listener {

    private CooldownManager cooldownManager = new CooldownManager();

    private final Cache<UUID, String> hiddenEquipmentCache = CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.SECONDS)
            .removalListener(notification -> {
                UUID toShowPlayeUUID = (UUID) notification.getKey();
                Player toShowPlayer = Bukkit.getPlayer(toShowPlayeUUID);
                ArrayList<ItemStack> bukkitEquipment = new ArrayList<>() {{
                    add(toShowPlayer.getEquipment().getHelmet());
                    add(toShowPlayer.getEquipment().getChestplate());
                    add(toShowPlayer.getEquipment().getLeggings());
                    add(toShowPlayer.getEquipment().getBoots());
                    add(toShowPlayer.getEquipment().getItemInOffHand());
                    add(toShowPlayer.getEquipment().getItemInMainHand());
                }};
            })
            .build();

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (item != null && item.hasItemMeta() && "Invisibility Sword".equals(item.getItemMeta().getDisplayName()) && event.getPlayer().isSneaking()) {

            if (cooldownManager.isOnCooldown(playerId, item)) {
                long timeLeft = (cooldownManager.getCooldownEnd(playerId, item) - System.currentTimeMillis()) / 1000;
                player.sendMessage("Your Invisibility Sword is on cooldown! Time left: " + timeLeft + " seconds.");
                return;
            }

            hiddenEquipmentCache.put(playerId, "");

            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 300, 10));


            Map<EquipmentSlot, ItemStack> emptyEquipment = new HashMap<>() {{
                put(EquipmentSlot.HAND, new ItemStack(Material.AIR));
                put(EquipmentSlot.OFF_HAND, new ItemStack(Material.AIR));
                put(EquipmentSlot.HEAD, new ItemStack(Material.AIR));
                put(EquipmentSlot.CHEST, new ItemStack(Material.AIR));
                put(EquipmentSlot.LEGS, new ItemStack(Material.AIR));
                put(EquipmentSlot.FEET, new ItemStack(Material.AIR));
            }};
            for (Player loopedPlayer : Bukkit.getOnlinePlayers()) {
                if (loopedPlayer == player) {
                    return;
                }
                loopedPlayer.sendEquipmentChange(player, emptyEquipment);
            }

            cooldownManager.setCooldown(playerId, item, 60);
            player.sendMessage("Invisibility Sword activated! Cooldown started.");
        }
    }
    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        ItemStack newItem = event.getPlayer().getInventory().getItem(event.getNewSlot());
        ItemStack previousItem = event.getPlayer().getInventory().getItem(event.getPreviousSlot());

        if (newItem != null && newItem.hasItemMeta() && "Invisibility Sword".equals(newItem.getItemMeta().getDisplayName())) {
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, PotionEffect.INFINITE_DURATION, 1));
        }

        if (previousItem != null && previousItem.hasItemMeta() && "Invisibility Sword".equals(previousItem.getItemMeta().getDisplayName())) {
            event.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
        }
    }
}