package io.taraxacum.libs.slimefun.util;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import io.taraxacum.libs.plugin.dto.LanguageManager;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
 
public class ResearchUtil {

    @Nullable
    public static Research setSingleResearch(@Nonnull ItemStack itemStack, int cost, boolean forceCost) {
        SlimefunItem slimefunItem = SlimefunItem.getByItem(itemStack);
        if (slimefunItem != null) {
            Research research = new Research(new NamespacedKey(slimefunItem.getAddon().getJavaPlugin(), slimefunItem.getId()), slimefunItem.getId().hashCode(), slimefunItem.getId(), cost);
            research.addItems(slimefunItem);
            research.register();
            if (forceCost) {
                research.setCost(cost);
            }
            return research;
        }
        return null;
    }

    @Nonnull
    public static Research setResearches(@Nonnull JavaPlugin javaPlugin, @Nonnull String key, int id, @Nonnull String defaultName, int defaultCost, boolean forceCost, @Nonnull ItemStack... itemStacks) {
        Research research = new Research(new NamespacedKey(javaPlugin, key), id, defaultName, defaultCost);
        research.addItems(itemStacks).register();
        if (forceCost) {
            research.setCost(defaultCost);
        }
        return research;
    }

    public static Research setResearches(@Nonnull LanguageManager languageManager, @Nonnull String key, int defaultCost, boolean forceCost, @Nonnull ItemStack... itemStacks) {
        Research research = new Research(new NamespacedKey(languageManager.getPlugin(), key), key.hashCode(), languageManager.getString("research", key), defaultCost);
        research.addItems(itemStacks).register();
        if (forceCost) {
            research.setCost(defaultCost);
        }
        return research;
    }

    public static void setResearchBySlimefunItems(@Nonnull SlimefunItemStack slimefunItemStack1, @Nonnull SlimefunItemStack slimefunItemStack2) {
        SlimefunItem slimefunItem1 = SlimefunItem.getByItem(slimefunItemStack1);
        SlimefunItem slimefunItem2 = SlimefunItem.getByItem(slimefunItemStack2);
        if (slimefunItem1 != null && slimefunItem2 != null) {
            slimefunItem1.setResearch(slimefunItem2.getResearch());
        }
    }

    public static void setResearchBySlimefunItemId(@Nonnull SlimefunItemStack slimefunItemStack, @Nonnull String id) {
        SlimefunItem slimefunItem1 = SlimefunItem.getByItem(slimefunItemStack);
        SlimefunItem slimefunItem2 = SlimefunItem.getById(id);
        if (slimefunItem1 != null && slimefunItem2 != null) {
            slimefunItem1.setResearch(slimefunItem2.getResearch());
        }
    }
}
