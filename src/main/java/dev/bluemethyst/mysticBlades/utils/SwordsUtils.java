package dev.bluemethyst.mysticBlades.utils;

import dev.bluemethyst.mysticBlades.items.sword_listeners.*;
import dev.bluemethyst.mysticBlades.items.swords.*;
import dev.bluemethyst.mysticBlades.items.swords.HarmingSword;
import org.bukkit.event.Listener;

import java.util.List;

public class SwordsUtils {
    public static List<Class<? extends BaseSword>> getSwords() {
        return List.of(
                AdrenalineSword.class,
                AquaticSword.class,
                SapphireSword.class,
                DarkMatterSword.class,
                HarmingSword.class,
                InvisibilitySword.class,
                StrengthSword.class,
                ThunderSword.class,
                WealthSword.class
        );
    }

    public static List<Class<? extends Listener>> getSwordListeners() {
        return List.of(
                AdrenalineSwordListener.class,
                AquaticSwordListener.class,
                SapphireSwordListener.class,
                DarkMatterSwordListener.class,
                HarmingSwordListener.class,
                InvisibilitySwordListener.class
        );
    }
}
