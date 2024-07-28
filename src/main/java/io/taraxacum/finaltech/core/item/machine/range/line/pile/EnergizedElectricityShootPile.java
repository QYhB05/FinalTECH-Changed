package io.taraxacum.finaltech.core.item.machine.range.line.pile;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.slimefun.dto.LocationInfo;
import io.taraxacum.libs.slimefun.util.EnergyUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class EnergizedElectricityShootPile extends AbstractElectricityShootPile {
    private final int range = ConfigUtil.getOrDefaultItemSetting(16, this, "range");

    public EnergizedElectricityShootPile(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public int getRange() {
        return this.range;
    }

    @Nonnull
    @Override
    protected RangeFunction doFunction(@Nonnull Summary summary) {
        return location -> {
            if (summary.getCapacitorEnergy() <= 0) {
                return -1;
            }
            LocationInfo locationInfo = LocationInfo.get(location);
            if (locationInfo != null && !this.notAllowedId.contains(locationInfo.getId()) && locationInfo.getSlimefunItem() instanceof EnergyNetComponent energyNetComponent && !EnergyNetComponentType.NONE.equals(energyNetComponent.getEnergyComponentType())) {
                int componentCapacity = energyNetComponent.getCapacity();
                if (componentCapacity > 0) {
                    int componentEnergy = Integer.parseInt(EnergyUtil.getCharge(locationInfo.getLocation()));
                    if (componentEnergy < componentCapacity) {
                        int transferEnergy = Math.min(summary.getCapacitorEnergy(), (componentCapacity - componentEnergy));
                        if (transferEnergy <= 1.5 && transferEnergy > 0) {
                            transferEnergy = 1;
                            EnergyUtil.setCharge(location, String.valueOf(componentEnergy + transferEnergy));
                            summary.setCapacitorEnergy(summary.getCapacitorEnergy() - transferEnergy);
                            summary.setEnergyCharge(StringNumberUtil.add(summary.getEnergyCharge(), String.valueOf(transferEnergy)));
                            return 1;
                        }
                        if (transferEnergy > 0) {
                            EnergyUtil.setCharge(location, String.valueOf(componentEnergy + transferEnergy));
                            transferEnergy /= 1.5;
                            summary.setCapacitorEnergy(summary.getCapacitorEnergy() - transferEnergy);
                            summary.setEnergyCharge(StringNumberUtil.add(summary.getEnergyCharge(), String.valueOf(transferEnergy)));
                            return 1;
                        }
                    }
                }
            }
            return 0;
        };
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this,
                String.valueOf(this.range));
    }
}
