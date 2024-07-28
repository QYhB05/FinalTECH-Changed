package io.taraxacum.finaltech.core.item.unusable.trophy;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.core.interfaces.UnCopiableItem;
import io.taraxacum.finaltech.core.item.unusable.UnusableSlimefunItem;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.2
 */
public class QYhB05 extends UnusableSlimefunItem implements UnCopiableItem {
    public QYhB05(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }
}
