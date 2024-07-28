package io.taraxacum.finaltech.core.item.usable.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
 
public class InfinityMachineAccelerateCard extends AbstractMachineAccelerateCard implements RecipeItem {
    private final int times = ConfigUtil.getOrDefaultItemSetting(1, this, "times");

    public InfinityMachineAccelerateCard(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected int times() {
        return this.times;
    }

    @Override
    protected boolean consume() {
        return false;
    }

    @Override
    protected boolean conditionMatch(@Nonnull Player player) {
        if (player.getHealth() > 1) {
            player.setHealth(player.getHealth() - 1);
            return true;
        }
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this,
                String.valueOf(this.times()));
    }
}
