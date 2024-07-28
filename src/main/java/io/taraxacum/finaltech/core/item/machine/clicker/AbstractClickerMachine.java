package io.taraxacum.finaltech.core.item.machine.clicker;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * This machine will do function while being clicked by player.
 *
 * @author Final_ROOT
 * @since 2.2
 */
public abstract class AbstractClickerMachine extends AbstractMachine {
    private final Map<Location, Integer> locationCountMap = new HashMap<>();
    private final int countThreshold = ConfigUtil.getOrDefaultItemSetting(Slimefun.getTickerTask().getTickRate() / 2, this, "count-threshold");

    public AbstractClickerMachine(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
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

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {

    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    protected void uniqueTick() {
        super.uniqueTick();

        this.locationCountMap.clear();
    }

    public Map<Location, Integer> getLocationCountMap() {
        return locationCountMap;
    }

    public int getCountThreshold() {
        return countThreshold;
    }
}
