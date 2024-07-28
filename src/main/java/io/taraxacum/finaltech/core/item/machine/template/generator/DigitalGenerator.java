package io.taraxacum.finaltech.core.item.machine.template.generator;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;
import org.bukkit.inventory.ItemStack;

public class DigitalGenerator extends AbstractGeneratorMachine {
    public DigitalGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    void registerRandomOutputRecipes() {
        this.registerRecipe(FinalTechItemStacks.DIGITAL_ONE);
        this.registerRecipe(FinalTechItemStacks.DIGITAL_TWO);
        this.registerRecipe(FinalTechItemStacks.DIGITAL_THREE);
        this.registerRecipe(FinalTechItemStacks.DIGITAL_FOUR);
    }
}
