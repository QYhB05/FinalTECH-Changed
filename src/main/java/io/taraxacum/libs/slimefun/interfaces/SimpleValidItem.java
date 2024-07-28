package io.taraxacum.libs.slimefun.interfaces;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public interface SimpleValidItem extends ValidItem {
    @Nonnull
    ItemStack getValidItem();
}
