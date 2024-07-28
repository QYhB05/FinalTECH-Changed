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
 
public class InfinityMachineChargeCard extends AbstractMachineChargeCard implements RecipeItem {
    private final double energy = ConfigUtil.getOrDefaultItemSetting(16.04, this, "energy");

    public InfinityMachineChargeCard(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected double energy() {
        return this.energy;
    }

    @Override
    protected boolean consume() {
        return false;
    }

    @Override
    protected boolean conditionMatch(@Nonnull Player player) {
        if (player.getLevel() >= 1) {
            player.setLevel(player.getLevel() - 1);
            return true;
        }
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this,
                String.valueOf((int) (Math.floor(energy))),
                String.format("%.2f", (energy - Math.floor(energy)) * 100));
    }
}
