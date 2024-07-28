package io.taraxacum.finaltech.core.item.machine.electric.capacitor.expanded;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.util.ConfigUtil;
import org.bukkit.inventory.ItemStack;

public class CarbonadoExpandedCapacitor extends AbstractExpandedElectricCapacitor {
    private final int capacity = ConfigUtil.getOrDefaultItemSetting(65536, this, "capacity");
    private final int stack = ConfigUtil.getOrDefaultItemSetting(65536, this, "max-stack");

    public CarbonadoExpandedCapacitor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public int getCapacity() {
        return this.capacity * 2;
    }

    @Override
    public int getMaxStack() {
        return this.stack - 2;
    }
}
