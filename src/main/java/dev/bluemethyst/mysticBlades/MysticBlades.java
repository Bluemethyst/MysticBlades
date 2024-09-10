package dev.bluemethyst.mysticBlades;

import dev.bluemethyst.mysticBlades.commands.GiveCustomGemCommand;
import dev.bluemethyst.mysticBlades.commands.GiveCustomSwordCommand;
import dev.bluemethyst.mysticBlades.commands.GiveCustomSwordTabCompleter;
import dev.bluemethyst.mysticBlades.items.gem_listeners.CustomGemListener;
import dev.bluemethyst.mysticBlades.items.swords.AdrenalineSword;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit. event. Listener;
import java.util.List;
import java.util.logging.Logger;

import static dev.bluemethyst.mysticBlades.utils.SwordsUtils.getSwordListeners;

public final class MysticBlades extends JavaPlugin {
    Logger logger = Logger.getLogger("MysticBlades");

    @Override
    public void onEnable() {
        logger.info("MysticBlades has been enabled!");

        AdrenalineSword adrenalineSword = new AdrenalineSword();
        getCommand("givecustomgem").setExecutor(new GiveCustomGemCommand());
        getCommand("givecustomsword").setExecutor(new GiveCustomSwordCommand());
        getCommand("givecustomsword").setTabCompleter(new GiveCustomSwordTabCompleter());

        List<Class<? extends Listener>> swordListeners = getSwordListeners();
        for (Class<? extends Listener> listener : swordListeners) {
            try {
                Listener listenerInstance = listener.getConstructor().newInstance();
                getServer().getPluginManager().registerEvents(listenerInstance, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        getServer().getPluginManager().registerEvents(new CustomGemListener(), this);

        // Define the custom sword recipe
        ShapedRecipe customSwordRecipe = new ShapedRecipe(new NamespacedKey(this, "adrenaline_sword"), adrenalineSword.getItem());
        customSwordRecipe.shape(" D ", "DDD", " S ");
        customSwordRecipe.setIngredient('D', Material.DIAMOND);
        customSwordRecipe.setIngredient('S', Material.STICK);

        // Register the recipe
        getServer().addRecipe(customSwordRecipe);
    }

    @Override
    public void onDisable() {
        logger.info("MysticBlades has been disabled!");
    }
}
