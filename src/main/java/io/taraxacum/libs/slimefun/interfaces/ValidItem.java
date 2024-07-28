package io.taraxacum.libs.slimefun.interfaces;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.4
 */
public interface ValidItem {
    boolean verifyItem(@Nonnull ItemStack itemStack);
}
