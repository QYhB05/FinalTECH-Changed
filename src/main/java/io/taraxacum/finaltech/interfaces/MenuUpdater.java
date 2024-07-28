package io.taraxacum.finaltech.core.interfaces;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * A Slimefun item that will update its menu to show some info.
 *
 * @author Final_ROOT
 * @since 2.0
 */
public interface MenuUpdater {

    default void updateMenu(@Nonnull BlockMenu blockMenu, int slot, @Nonnull SlimefunItem slimefunItem, @Nonnull String... text) {
        ItemStack item = blockMenu.getItemInSlot(slot);
        if (!ItemStackUtil.isItemNull(item)) {
            ItemStackUtil.setLore(item, ConfigUtil.getStatusMenuLore(FinalTechChanged.getLanguageManager(), slimefunItem, text));
        }
    }
}
