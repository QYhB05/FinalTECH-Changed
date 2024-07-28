package io.taraxacum.libs.slimefun.util;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import java.util.Random;
 
public class SfItemUtil {
    private static final NamespacedKey SLIMEFUN_ITEM_KEY = new NamespacedKey(Slimefun.instance(), "slimefun_item");
    private static final NamespacedKey SPECIAL_ITEM_KEY = new NamespacedKey(FinalTechChanged.getInstance(), "ft_item");
    private static final String SPECIAL_ITEM_VALUE = String.valueOf(FinalTechChanged.getConfigManager().getOrDefault(new Random().nextLong(Long.MAX_VALUE), "valid-item-seed"));

    @Nonnull
    public static String getIdFormatName(@Nonnull Class<? extends SlimefunItem> clazz) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("_FINALTECH_");
        boolean append = false;
        for (char c : clazz.getSimpleName().toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                if (append) {
                    stringBuilder.append("_");
                }
                stringBuilder.append(c);
                append = true;
            } else if (c >= 'a' && c <= 'z') {
                stringBuilder.append((char) (c - 32));
            } else {
                if (append) {
                    stringBuilder.append("_");
                    append = false;
                }
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    public static void removeSlimefunId(@Nonnull ItemStack itemStack) {
        if (itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
            if (persistentDataContainer.has(SLIMEFUN_ITEM_KEY, PersistentDataType.STRING)) {
                persistentDataContainer.remove(SLIMEFUN_ITEM_KEY);
                itemStack.setItemMeta(itemMeta);
            }
        }
    }

    public static void setSlimefunItemKey(@Nonnull ItemStack itemStack, @Nonnull String id) {
        ItemStackUtil.setNBT(itemStack, SLIMEFUN_ITEM_KEY, id);
    }

    public static boolean hasSpecialItemKey(@Nonnull ItemStack itemStack) {
        if (!itemStack.hasItemMeta()) {
            return false;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        return persistentDataContainer.has(SPECIAL_ITEM_KEY, PersistentDataType.STRING) && SPECIAL_ITEM_VALUE.equals(persistentDataContainer.get(SPECIAL_ITEM_KEY, PersistentDataType.STRING));
    }

    public static void setSpecialItemKey(@Nonnull ItemStack itemStack) {
        return ;
    }
}
