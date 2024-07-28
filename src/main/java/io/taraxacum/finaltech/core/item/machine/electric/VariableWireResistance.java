package io.taraxacum.finaltech.core.item.machine.electric;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.MenuUpdater;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusMenu;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;
import io.taraxacum.finaltech.util.BlockTickerUtil;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.slimefun.util.EnergyUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

public class VariableWireResistance extends AbstractElectricMachine implements RecipeItem, MenuUpdater {
    private final int capacity = ConfigUtil.getOrDefaultItemSetting(65536, this, "capacity");
    private final String capacityString = String.valueOf(this.capacity);

    public VariableWireResistance(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StatusMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Location location = block.getLocation();
        String charge = EnergyUtil.getCharge(location);
        if (this.capacityString.equals(charge)) {
            JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
            Runnable runnable = () -> {
                BlockStorage.deleteLocationInfoUnsafely(location, true);
                BlockStorage.addBlockInfo(location, ConstantTableUtil.CONFIG_ID, FinalTechItemStacks.VARIABLE_WIRE_CAPACITOR.getItemId(), true);
                //BlockStorage.addBlockInfo(location, ConstantTableUtil.CONFIG_CHARGE, String.valueOf(this.getCapacity()));
                ((EnergyNetComponent) FinalTechItemStacks.VARIABLE_WIRE_CAPACITOR).setCharge(location, this.getCapacity());
                Slimefun.getNetworkManager().updateAllNetworks(location);
                javaPlugin.getServer().getScheduler().runTaskLater(javaPlugin, () -> {
                    if (!location.getBlock().getType().isAir() && FinalTechItemStacks.VARIABLE_WIRE_CAPACITOR.getItemId().equals(BlockStorage.getLocationInfo(location, ConstantTableUtil.CONFIG_ID))) {
                        block.setType(FinalTechItemStacks.VARIABLE_WIRE_CAPACITOR.getType());
                    }
                }, 0);
            };

            javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> BlockTickerUtil.runTask(FinalTechChanged.getLocationRunnableFactory(), FinalTechChanged.isAsyncSlimefunItem(this.getId()), runnable, location));
        } else {
            BlockMenu blockMenu = BlockStorage.getInventory(location);
            if (blockMenu.hasViewer()) {
                this.updateMenu(blockMenu, StatusMenu.STATUS_SLOT, this, charge);
            }
        }
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this,
                String.valueOf(this.capacity));
    }
}
