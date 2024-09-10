package dev.bluemethyst.mysticBlades.commands;

import dev.bluemethyst.mysticBlades.items.swords.BaseSword;
import dev.bluemethyst.mysticBlades.utils.SwordsUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class GiveCustomSwordTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            List<Class<? extends BaseSword>> swordClasses = SwordsUtils.getSwords();
            for (Class<? extends BaseSword> swordClass : swordClasses) {
                suggestions.add(swordClass.getSimpleName());
            }
        }
        return suggestions;
    }
}
