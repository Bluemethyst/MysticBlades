package dev.bluemethyst.mysticBlades.utils;

import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private Map<UUID, Map<ItemStack, Long>> playerCooldowns = new HashMap<>();

    public boolean isOnCooldown(UUID playerId, ItemStack item) {
        Map<ItemStack, Long> cooldowns = playerCooldowns.get(playerId);
        if (cooldowns != null) {
            Long cooldownEnd = cooldowns.get(item);
            if (cooldownEnd != null) {
                return System.currentTimeMillis() < cooldownEnd;
            }
        }
        return false;
    }

    public void setCooldown(UUID playerId, ItemStack item, int seconds) {
        playerCooldowns.putIfAbsent(playerId, new HashMap<>());
        Map<ItemStack, Long> cooldowns = playerCooldowns.get(playerId);
        cooldowns.put(item, System.currentTimeMillis() + (seconds * 1000));
    }

    public void removeCooldown(UUID playerId, ItemStack item) {
        Map<ItemStack, Long> cooldowns = playerCooldowns.get(playerId);
        if (cooldowns != null) {
            cooldowns.remove(item);
        }
    }

    public void clearPlayerCooldowns(UUID playerId) {
        playerCooldowns.remove(playerId);
    }

    public long getCooldownEnd(UUID playerId, ItemStack item) {
        Map<ItemStack, Long> cooldowns = playerCooldowns.get(playerId);
        if (cooldowns != null) {
            Long cooldownEnd = cooldowns.get(item);
            if (cooldownEnd != null) {
                return cooldownEnd;
            }
        }
        return 0;
    }
}
