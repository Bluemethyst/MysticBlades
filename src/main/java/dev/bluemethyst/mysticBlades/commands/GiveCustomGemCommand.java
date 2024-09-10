package dev.bluemethyst.mysticBlades.commands;

import dev.bluemethyst.mysticBlades.items.gems.CustomGem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCustomGemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            CustomGem customGem = new CustomGem();
            player.getInventory().addItem(customGem.getItem());
            player.sendMessage("You have been given the sapphire gem");
            return true;
        }
        sender.sendMessage("This command can only be used by players.");
        return false;
    }
}