package io.taraxacum.finaltech.core.item.machine.template.conversion;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;
import org.bukkit.inventory.ItemStack;

public class LogicToDigitalConversion extends AbstractConversionMachine {
    public LogicToDigitalConversion(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        this.registerRecipe(FinalTechItemStacks.LOGIC_FALSE, FinalTechItemStacks.DIGITAL_ZERO);
        this.registerRecipe(FinalTechItemStacks.LOGIC_TRUE, FinalTechItemStacks.DIGITAL_ONE);
    }
}
