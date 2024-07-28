package io.taraxacum.finaltech.core.item.unusable;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.LogicItem;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.interfaces.UnCopiableItem;
import io.taraxacum.finaltech.util.RecipeUtil;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 * @since 2.4
 */
public class Logic extends UnusableSlimefunItem implements LogicItem, RecipeItem, UnCopiableItem {
    private final boolean logic;

    public Logic(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, boolean logic) {
        super(itemGroup, item, recipeType, recipe);
        this.logic = logic;
    }

    @Override
    public boolean getLogic() {
        return this.logic;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this);
    }
}
