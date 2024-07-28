package io.taraxacum.finaltech.core.item.tool;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ToolUseHandler;
import io.taraxacum.finaltech.core.item.AbstractMySlimefunItem;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public abstract class SuperTool extends AbstractMySlimefunItem {
    public SuperTool(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.addItemHandler((ToolUseHandler) (blockBreakEvent, itemStack, fortune, drops) -> {
            blockBreakEvent.setDropItems(false);
            blockBreakEvent.setExpToDrop(blockBreakEvent.getExpToDrop() + 1);
        });
    }
}
