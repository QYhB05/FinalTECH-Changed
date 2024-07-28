package io.taraxacum.finaltech.core.item.machine.tower;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.DigitalItem;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.SimulateClickMachineMenu;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.LocationUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.slimefun.dto.LocationInfo;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Final_ROOT
 * @since 2.2
 */
public class SimulateClickMachine extends AbstractTower implements RecipeItem {
    private final Set<String> notAllowedId = new HashSet<>(ConfigUtil.getItemStringList(this, "not-allowed-id"));
    private final double rangeRate = ConfigUtil.getOrDefaultItemSetting(0.4, this, "range-rate");

    public SimulateClickMachine(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
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

    @Nullable
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new SimulateClickMachineMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Location location = block.getLocation();
        BlockMenu blockMenu = BlockStorage.getInventory(block);

        ItemStack inputItemStack = blockMenu.getItemInSlot(this.getInputSlot()[0]);
        ItemStack outputItemStack = blockMenu.getItemInSlot(this.getOutputSlot()[0]);

        if (ItemStackUtil.isItemNull(inputItemStack) || !ItemStackUtil.isItemNull(outputItemStack)) {
            return;
        }

        int digit = 0;
        SlimefunItem sfItem = SlimefunItem.getByItem(inputItemStack);
        if (sfItem instanceof DigitalItem digitalItem) {
            digit = digitalItem.getDigit();
        }

        if (digit > 0) {
            location.setY(location.getY() - 1);

            LocationInfo locationInfo = LocationInfo.get(location);
            if (locationInfo != null && !this.notAllowedId.contains(locationInfo.getId())) {
                outputItemStack = ItemStackUtil.cloneItem(inputItemStack);
                outputItemStack.setAmount(1);
                inputItemStack.setAmount(inputItemStack.getAmount() - 1);
                blockMenu.pushItem(outputItemStack, this.getOutputSlot()[0]);

                double range = digit * this.rangeRate;

                JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
                javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> {
                    BlockMenu targetBlockMenu = BlockStorage.getInventory(location);
                    if (targetBlockMenu != null) {
                        Block targetBlock = location.getBlock();
                        for (Entity entity : location.getWorld().getNearbyEntities(LocationUtil.getCenterLocation(targetBlock), range, range, range, entity -> entity instanceof Player)) {
                            if (targetBlockMenu.canOpen(targetBlock, (Player) entity)) {
                                targetBlockMenu.open((Player) entity);
                            }
                        }
                    }
                });
            }
        }

    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this,
                String.valueOf(this.rangeRate));
    }
}
