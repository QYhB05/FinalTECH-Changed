package io.taraxacum.finaltech.core.item.machine.manual.craft;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.taraxacum.finaltech.util.RecipeUtil;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class ManualSmeltery extends AbstractManualCraftMachine {
    public ManualSmeltery(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
//        RecipeUtil.registerRecipeByRecipeType(this, RecipeType.SMELTERY);
        RecipeUtil.registerRecipeBySlimefunId(this, SlimefunItems.ELECTRIC_SMELTERY.getItemId());
        RecipeUtil.registerRecipeBySlimefunId(this, SlimefunItems.ELECTRIC_INGOT_FACTORY.getItemId());
    }
}
