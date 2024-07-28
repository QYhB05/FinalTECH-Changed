package io.taraxacum.finaltech.core.item.machine.range.cube;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.MenuUpdater;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusL2Menu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.BlockTickerUtil;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import io.taraxacum.libs.slimefun.dto.LocationInfo;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Function;
 
public class MatrixAccelerator extends AbstractCubeMachine implements RecipeItem, MenuUpdater {
    private final int range = ConfigUtil.getOrDefaultItemSetting(1, this, "range");
    private final Set<String> notAllowedId = new HashSet<>(ConfigUtil.getItemStringList(this, "not-allowed-id"));
    private final boolean safeMode = ConfigUtil.getOrDefaultItemSetting(true, this, "safe-mode");
    // System.nanoTime
    // 1,000,000ns = 1ms
    private final int syncThreshold = ConfigUtil.getOrDefaultItemSetting(300000, this, "threshold-sync");
    private final int asyncThreshold = ConfigUtil.getOrDefaultItemSetting(1600000, this, "threshold-async");

    public MatrixAccelerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.notAllowedId.add(this.getId());
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
        return new StatusL2Menu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Location blockLocation = block.getLocation();
        BlockMenu blockMenu = BlockStorage.getInventory(blockLocation);
        boolean hasViewer = blockMenu.hasViewer();

        int accelerate = 0;
        int range = this.range;

        String machineId = null;

        // parse item
        ItemStack matchItem = blockMenu.getItemInSlot(this.getInputSlot()[0]);
        if (FinalTechItems.ITEM_PHONY.verifyItem(matchItem)) {
            int amount = matchItem.getAmount();
            for (int i = 2, j = amount; j > 0; j /= i) {
                accelerate++;
            }
            for (int i = 2, j = amount; j > 0; j /= i++) {
                range++;
            }
        } else {
            SlimefunItem machineItem = SlimefunItem.getByItem(matchItem);
            if (machineItem == null || this.notAllowedId.contains(machineItem.getId()) || machineItem.getBlockTicker() == null) {
                if (hasViewer) {
                    this.updateMenu(blockMenu, StatusL2Menu.STATUS_SLOT, this,
                            "0", "0", "0");
                }
                return;
            }
            machineId = machineItem.getId();
            accelerate = matchItem.getAmount();
        }

        // search around block
        Map<Integer, List<LocationInfo>> locationInfoMap = new HashMap<>(range * 3);

        final String finalMachineId = machineId;
        Function<String, Boolean> availableIdFunction = machineId == null ? id -> !MatrixAccelerator.this.notAllowedId.contains(id) : finalMachineId::equals;
        int count = this.cubeFunction(block, range, location -> {
            LocationInfo locationInfo = LocationInfo.get(location);
            if (locationInfo != null && availableIdFunction.apply(locationInfo.getId()) && locationInfo.getSlimefunItem().getBlockTicker() != null) {
                int distance = Math.abs(location.getBlockX() - blockLocation.getBlockX()) + Math.abs(location.getBlockY() - blockLocation.getBlockY()) + Math.abs(location.getBlockZ() - blockLocation.getBlockZ());
                locationInfoMap.computeIfAbsent(distance, d -> new ArrayList<>(d * d * 4 + 2)).add(locationInfo);
                locationInfo.cloneLocation();
                return 1;
            }
            return 0;
        });

        if (count == 0) {
            if (hasViewer) {
                this.updateMenu(blockMenu, StatusL2Menu.STATUS_SLOT, this,
                        "0", "0", "0");
            }
            return;
        }


        int accelerateTimeCount = 0;
        int accelerateMachineCount = 0;

        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
        final int finalAccelerate = machineId != null ? accelerate /= count : accelerate;
        List<LocationInfo> locationInfoList;
        for (int distance = 1; distance <= range * 3; distance++) {
            locationInfoList = locationInfoMap.get(distance);
            if (locationInfoList != null) {
                Collections.shuffle(locationInfoList);
                for (LocationInfo locationInfo : locationInfoList) {
                    BlockTicker blockTicker = locationInfo.getSlimefunItem().getBlockTicker();
                    if (blockTicker.isSynchronized()) {
                        javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> {
                            for (int i = 0; i < finalAccelerate; i++) {
                                long testTime = JavaUtil.testTime(() -> blockTicker.tick(locationInfo.getLocation().getBlock(), locationInfo.getSlimefunItem(), locationInfo.getConfig()));
                                if (this.safeMode && testTime > MatrixAccelerator.this.syncThreshold) {
                                    FinalTechChanged.logger().warning(this.getId() + " cost " + testTime + "ns to run blockTicker for " + locationInfo.getId());
                                    MatrixAccelerator.this.notAllowedId.add(locationInfo.getId());
                                    break;
                                }
                            }
                        });
                    } else if (!this.safeMode || FinalTechChanged.isAsyncSlimefunItem(locationInfo.getId()) == FinalTechChanged.isAsyncSlimefunItem(this.getId())) {
                        BlockTickerUtil.runTask(FinalTechChanged.getLocationRunnableFactory(), FinalTechChanged.isAsyncSlimefunItem(locationInfo.getId()), () -> {
                            for (int i = 0; i < finalAccelerate; i++) {
                                long testTime = JavaUtil.testTime(() -> blockTicker.tick(locationInfo.getLocation().getBlock(), locationInfo.getSlimefunItem(), locationInfo.getConfig()));
                                if (this.safeMode && testTime > MatrixAccelerator.this.asyncThreshold) {
                                    FinalTechChanged.logger().warning(this.getId() + " cost " + testTime + "ns to run blockTicker for " + locationInfo.getId());
                                    MatrixAccelerator.this.notAllowedId.add(locationInfo.getId());
                                    break;
                                }
                            }
                        }, locationInfo.getLocation());
                    }

                    if (hasViewer) {
                        javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, locationInfo.getLocation().getBlock()));
                    }
                    accelerateTimeCount += accelerate;
                    accelerateMachineCount++;
                }
            }
        }

        if (hasViewer) {
            this.updateMenu(blockMenu, StatusL2Menu.STATUS_SLOT, this,
                    String.valueOf(range),
                    String.valueOf(accelerateTimeCount),
                    String.valueOf(accelerateMachineCount));
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this,
                String.valueOf(this.range));
    }

    @Override
    public Location[] getLocations(@Nonnull Location sourceLocation) {
        // TODO
        return new Location[0];
    }
}
