package io.taraxacum.finaltech.core.item.machine.range.point.face;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.LocationMachine;
import io.taraxacum.finaltech.core.interfaces.MenuUpdater;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusMenu;
import io.taraxacum.finaltech.util.BlockTickerUtil;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.slimefun.dto.LocationInfo;
import io.taraxacum.libs.slimefun.util.EnergyUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
 
public class OverloadedChargeBase extends AbstractFaceMachine implements RecipeItem, MenuUpdater, LocationMachine {
    private final Set<String> notAllowedId = new HashSet<>(ConfigUtil.getItemStringList(this, "not-allowed-id"));
    private final double efficiency = ConfigUtil.getOrDefaultItemSetting(0.1, this, "efficiency");
    private final double maxLimit = ConfigUtil.getOrDefaultItemSetting(2, this, "max-limit");

    public OverloadedChargeBase(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_PLACER_DENY;
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StatusMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        this.pointFunction(block, 1, location -> {
            LocationInfo locationInfo = LocationInfo.get(location);
            if (locationInfo != null && !notAllowedId.contains(locationInfo.getId())) {
                BlockTickerUtil.runTask(FinalTechChanged.getLocationRunnableFactory(), FinalTechChanged.isAsyncSlimefunItem(locationInfo.getId()), () -> OverloadedChargeBase.this.doCharge(block, locationInfo), location);
                return 0;
            }
            BlockMenu blockMenu = BlockStorage.getInventory(block);
            if (blockMenu.hasViewer()) {
                this.updateMenu(blockMenu, StatusMenu.STATUS_SLOT, this,
                        "0",
                        "0");
            }
            return 0;
        });
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    private void doCharge(@Nonnull Block block, @Nonnull LocationInfo locationInfo) {
        int storedEnergy = 0;
        int chargeEnergy = 0;

        if (locationInfo.getSlimefunItem() instanceof EnergyNetComponent energyNetComponent && !JavaUtil.matchOnce(energyNetComponent.getEnergyComponentType(), EnergyNetComponentType.CAPACITOR, EnergyNetComponentType.GENERATOR)) {
            int capacity = energyNetComponent.getCapacity();
            int maxValue = capacity < Integer.MAX_VALUE / OverloadedChargeBase.this.maxLimit ? (int) (capacity * OverloadedChargeBase.this.maxLimit) : Integer.MAX_VALUE;
            storedEnergy = Integer.parseInt(EnergyUtil.getCharge(locationInfo.getLocation()));
            chargeEnergy = storedEnergy < maxValue - capacity * OverloadedChargeBase.this.efficiency ? (int) (capacity * OverloadedChargeBase.this.efficiency) : (maxValue - storedEnergy);
            if (chargeEnergy > 0) {
                storedEnergy += chargeEnergy;
                EnergyUtil.setCharge(locationInfo.getConfig(), storedEnergy);
            }
        }

        BlockMenu blockMenu = BlockStorage.getInventory(block);
        if (blockMenu.hasViewer()) {
            this.updateMenu(blockMenu, StatusMenu.STATUS_SLOT, this,
                    String.valueOf(storedEnergy),
                    String.valueOf(chargeEnergy));
        }
    }

    @Nonnull
    @Override
    protected BlockFace getBlockFace() {
        return BlockFace.UP;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this,
                String.valueOf(this.efficiency),
                String.valueOf(this.maxLimit * 100));
    }

    @Override
    public Location[] getLocations(@Nonnull Location sourceLocation) {
        return new Location[]{this.getTargetLocation(sourceLocation, 1)};
    }
}
