package io.taraxacum.finaltech.core.item.machine.range.point.face;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.VoidMenu;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class FuelAccelerator extends AbstractFaceMachine implements RecipeItem {
    private final Map<Location, BukkitTask> locationBukkitTaskMap = new HashMap<>();

    public FuelAccelerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        return new VoidMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Location location = block.getLocation();
        BukkitTask task = locationBukkitTaskMap.get(location);
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }

        int time = Slimefun.getTickerTask().getTickRate();
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
        BukkitTask bukkitTask = javaPlugin.getServer().getScheduler().runTaskTimer(javaPlugin, new Runnable() {
            private int count = 0;

            @Override
            public void run() {
                if (count++ < time) {
                    FuelAccelerator.this.pointFunction(block, 1, location -> {
                        BlockState blockState = PaperLib.getBlockState(location.getBlock(), false).getState();
                        if (blockState instanceof Furnace furnace) {
                            if (((Furnace) blockState).getCookTime() > 0 && furnace.getCookTime() < furnace.getCookTimeTotal() - 1) {
                                furnace.setCookTime((short) (((Furnace) blockState).getCookTimeTotal()));
//                                furnace.setCookTimeTotal(1);
                            }
                        }
                        return 0;
                    });
                }
            }
        }, 1L, 1L);
        locationBukkitTaskMap.put(location, bukkitTask);

        javaPlugin.getServer().getScheduler().runTaskLater(javaPlugin, bukkitTask::cancel, time);
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Nonnull
    @Override
    protected BlockFace getBlockFace() {
        return BlockFace.UP;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this);
    }
}
