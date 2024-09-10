package dev.bluemethyst.mysticBlades.commands;

import dev.bluemethyst.mysticBlades.items.swords.BaseSword;
import dev.bluemethyst.mysticBlades.utils.SwordsUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GiveCustomSwordCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return false;
        }

        Player player = (Player) sender;
        List<Class<? extends BaseSword>> swordClasses = SwordsUtils.getSwords();
        // Check if the player provided a sword name as an argument
        if (args.length == 0) {
            for (Class<?> swordClass : swordClasses) {
                try {
                    Object swordInstance = swordClass.getConstructor().newInstance();
                    ItemStack item = ((BaseSword) swordInstance).getItem();
                    player.getInventory().addItem(item);
                    player.sendMessage("You have been given all swords.");
                } catch (Exception e) {
                    player.sendMessage("Error " + e.getMessage());
                }
            }
        }

        String swordName = args[0];
        // Try to find the sword by name
        for (Class<? extends BaseSword> swordClass : swordClasses) {
            if (swordClass.getSimpleName().equalsIgnoreCase(swordName)) {
                try {
                    Object swordInstance = swordClass.getConstructor().newInstance();
                    ItemStack item = ((BaseSword) swordInstance).getItem();
                    player.getInventory().addItem(item);
                    player.sendMessage("You have been given a " + swordName);
                } catch (Exception e) {
                    player.sendMessage("Error: " + e.getMessage());
                }
                return true;
            }
        }

        // If the sword was not found
        player.sendMessage("Sword not found. Available swords: " + getAvailableSwordNames(swordClasses));
        return false;
    }

    // Helper method to return available sword names
    private String getAvailableSwordNames(List<Class<? extends BaseSword>> swordClasses) {
        StringBuilder swordNames = new StringBuilder();
        for (Class<? extends BaseSword> swordClass : swordClasses) {
            swordNames.append(swordClass.getSimpleName()).append(", ");
        }
        return swordNames.length() > 0 ? swordNames.substring(0, swordNames.length() - 2) : "none";
    }
}
