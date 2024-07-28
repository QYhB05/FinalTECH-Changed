package io.taraxacum.finaltech.core.item.machine.cargo;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.dto.CargoDTO;
import io.taraxacum.finaltech.core.dto.SimpleCargoDTO;
import io.taraxacum.finaltech.core.helper.*;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.cargo.AdvancedPointTransferMenu;
import io.taraxacum.finaltech.setup.FinalTechItemStacks;
import io.taraxacum.finaltech.util.*;
import io.taraxacum.libs.plugin.dto.InvWithSlots;
import io.taraxacum.libs.plugin.dto.ServerRunnableLockFactory;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class AdvancedPointTransfer extends AbstractCargo implements RecipeItem {
    private final double particleDistance = 0.25;
    private final int particleInterval = 2;
    private final int range = ConfigUtil.getOrDefaultItemSetting(8, this, "range");

    public AdvancedPointTransfer(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
                Block block = blockPlaceEvent.getBlock();
                Location location = block.getLocation();

                IgnorePermission.HELPER.checkOrSetBlockStorage(location);
                BlockStorage.addBlockInfo(location, ConstantTableUtil.CONFIG_UUID, blockPlaceEvent.getPlayer().getUniqueId().toString());

                CargoNumber.HELPER.checkOrSetBlockStorage(location);
                CargoFilter.HELPER.checkOrSetBlockStorage(location);
                CargoMode.HELPER.checkOrSetBlockStorage(location);
                CargoLimit.HELPER.checkOrSetBlockStorage(location);

                SlotSearchSize.INPUT_HELPER.checkOrSetBlockStorage(location);
                SlotSearchOrder.INPUT_HELPER.checkOrSetBlockStorage(location);
                BlockSearchMode.POINT_INPUT_HELPER.checkOrSetBlockStorage(location);

                SlotSearchSize.OUTPUT_HELPER.checkOrSetBlockStorage(location);
                SlotSearchOrder.OUTPUT_HELPER.checkOrSetBlockStorage(location);
                BlockSearchMode.POINT_OUTPUT_HELPER.checkOrSetBlockStorage(location);
            }
        };
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this, AdvancedPointTransferMenu.ITEM_MATCH);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new AdvancedPointTransferMenu(this);
    }

    @Override
    public void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        Location location = block.getLocation();
        JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
        boolean primaryThread = javaPlugin.getServer().isPrimaryThread();
        boolean drawParticle = blockMenu.hasViewer() || RouteShow.VALUE_TRUE.equals(RouteShow.HELPER.getOrDefaultValue(config));

        if (primaryThread) {
            BlockData blockData = block.getState().getBlockData();
            if (!(blockData instanceof Directional)) {
                return;
            }
            BlockFace blockFace = ((Directional) blockData).getFacing();
            Block inputBlock = this.searchBlock(block, BlockSearchMode.POINT_INPUT_HELPER.getOrDefaultValue(config), blockFace.getOppositeFace(), true, drawParticle);
            Block outputBlock = this.searchBlock(block, BlockSearchMode.POINT_OUTPUT_HELPER.getOrDefaultValue(config), blockFace, false, drawParticle);

            if (inputBlock.getLocation().equals(outputBlock.getLocation())) {
                return;
            }

            if (!PermissionUtil.checkOfflinePermission(location, config, LocationUtil.transferToLocation(inputBlock, outputBlock))) {
                return;
            }

            if (drawParticle) {
                javaPlugin.getServer().getScheduler().runTaskLaterAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, inputBlock, outputBlock), Slimefun.getTickerTask().getTickRate());
            }

            String inputSlotSearchSize = SlotSearchSize.INPUT_HELPER.getOrDefaultValue(config);
            String inputSlotSearchOrder = SlotSearchOrder.INPUT_HELPER.getOrDefaultValue(config);

            String outputSlotSearchSize = SlotSearchSize.OUTPUT_HELPER.getOrDefaultValue(config);
            String outputSlotSearchOrder = SlotSearchOrder.OUTPUT_HELPER.getOrDefaultValue(config);

            int cargoNumber = Integer.parseInt(CargoNumber.HELPER.getOrDefaultValue(config));
            String cargoFilter = CargoFilter.HELPER.getOrDefaultValue(config);
            String cargoMode = CargoMode.HELPER.getOrDefaultValue(config);
            String cargoLimit = CargoLimit.HELPER.getOrDefaultValue(config);

            CargoUtil.doCargo(new CargoDTO(javaPlugin, inputBlock, inputSlotSearchSize, inputSlotSearchOrder, outputBlock, outputSlotSearchSize, outputSlotSearchOrder, cargoNumber, cargoLimit, cargoFilter, blockMenu.toInventory(), AdvancedPointTransferMenu.ITEM_MATCH), cargoMode);
        } else {
            javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> {
                BlockData blockData = block.getState().getBlockData();
                if (!(blockData instanceof Directional)) {
                    return;
                }
                BlockFace blockFace = ((Directional) blockData).getFacing();
                Block inputBlock = AdvancedPointTransfer.this.searchBlock(block, BlockSearchMode.POINT_INPUT_HELPER.getOrDefaultValue(config), blockFace.getOppositeFace(), true, drawParticle);
                Block outputBlock = AdvancedPointTransfer.this.searchBlock(block, BlockSearchMode.POINT_OUTPUT_HELPER.getOrDefaultValue(config), blockFace, false, drawParticle);

                if (inputBlock.getLocation().equals(outputBlock.getLocation())) {
                    return;
                }

                Inventory inputInventory = CargoUtil.getVanillaInventory(inputBlock);
                Inventory outputInventory = CargoUtil.getVanillaInventory(outputBlock);

                ServerRunnableLockFactory.getInstance(javaPlugin, Location.class).waitThenRun(() -> {
                    if (!BlockStorage.hasBlockInfo(location)) {
                        return;
                    }

                    if (!PermissionUtil.checkOfflinePermission(location, config, LocationUtil.transferToLocation(inputBlock, outputBlock))) {
                        return;
                    }

                    String inputSize = SlotSearchSize.INPUT_HELPER.getOrDefaultValue(config);
                    String inputOrder = SlotSearchOrder.INPUT_HELPER.getOrDefaultValue(config);

                    String outputSize = SlotSearchSize.OUTPUT_HELPER.getOrDefaultValue(config);
                    String outputOrder = SlotSearchOrder.OUTPUT_HELPER.getOrDefaultValue(config);

                    String cargoMode = CargoMode.HELPER.getOrDefaultValue(config);

                    InvWithSlots inputMap;
                    if (BlockStorage.hasInventory(inputBlock)) {
                        if (CargoMode.VALUE_OUTPUT_MAIN.equals(cargoMode)) {
                            inputMap = null;
                        } else {
                            inputMap = CargoUtil.getInvWithSlots(inputBlock, inputSize, inputOrder);
                        }
                    } else if (inputInventory != null) {
                        inputMap = CargoUtil.calInvWithSlots(inputInventory, inputOrder);
                    } else {
                        return;
                    }

                    InvWithSlots outputMap;
                    if (BlockStorage.hasInventory(outputBlock)) {
                        if (CargoMode.VALUE_INPUT_MAIN.equals(cargoMode)) {
                            outputMap = null;
                        } else {
                            outputMap = CargoUtil.getInvWithSlots(outputBlock, outputSize, outputOrder);
                        }
                    } else if (outputInventory != null) {
                        outputMap = CargoUtil.calInvWithSlots(outputInventory, outputOrder);
                    } else {
                        return;
                    }

                    if (drawParticle) {
                        javaPlugin.getServer().getScheduler().runTaskLaterAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.WAX_OFF, 0, inputBlock, outputBlock), Slimefun.getTickerTask().getTickRate());
                    }

                    int cargoNumber = Integer.parseInt(CargoNumber.HELPER.getOrDefaultValue(config));
                    String cargoFilter = CargoFilter.HELPER.getOrDefaultValue(config);
                    String cargoLimit = CargoLimit.HELPER.getOrDefaultValue(config);

                    CargoUtil.doSimpleCargo(new SimpleCargoDTO(inputMap, inputBlock, inputSize, inputOrder, outputMap, outputBlock, outputSize, outputOrder, cargoNumber, cargoLimit, cargoFilter, blockMenu.toInventory(), AdvancedPointTransferMenu.ITEM_MATCH), cargoMode);
                }, inputBlock.getLocation(), outputBlock.getLocation());
            });
        }
    }

    @Nonnull
    private Block searchBlock(@Nonnull Block begin, @Nonnull String searchMode, @Nonnull BlockFace blockFace, boolean input, boolean drawParticle) {
        List<Location> particleLocationList = new ArrayList<>();
        particleLocationList.add(LocationUtil.getCenterLocation(begin));
        Block result = begin.getRelative(blockFace);
        int count = 1;
        if (BlockSearchMode.VALUE_ZERO.equals(searchMode)) {
            particleLocationList.add(LocationUtil.getCenterLocation(result));
            if (drawParticle && FinalTechChanged.getSlimefunTickCount() % this.particleInterval == 0) {
                JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
                javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawLineByDistance(javaPlugin, Particle.CRIT_MAGIC, this.particleInterval * Slimefun.getTickerTask().getTickRate() * 50L / particleLocationList.size(), this.particleDistance, input ? JavaUtil.reserve(particleLocationList) : particleLocationList));
            }
            return result;
        }
        Set<Location> locationSet = new HashSet<>();
        locationSet.add(begin.getLocation());
        while (true) {
            if (BlockStorage.hasInventory(result) && !result.getType().equals(FinalTechItemStacks.POINT_TRANSFER.getType())) {
                particleLocationList.add(LocationUtil.getCenterLocation(result));
                break;
            }
            if (PaperLib.getBlockState(result, false).getState() instanceof InventoryHolder) {
                particleLocationList.add(LocationUtil.getCenterLocation(result));
                break;
            }
            if (result.getType() == FinalTechItemStacks.POINT_TRANSFER.getType()) {
                particleLocationList.add(LocationUtil.getCenterLocation(result));
                count = 0;
                if (locationSet.contains(result.getLocation())) {
                    break;
                }
                locationSet.add(result.getLocation());
                if (BlockSearchMode.VALUE_INHERIT.equals(searchMode)) {
                    BlockData blockData = result.getState().getBlockData();
                    if (blockData instanceof Directional) {
                        blockFace = ((Directional) blockData).getFacing();
                        if (input) {
                            blockFace = blockFace.getOppositeFace();
                        }
                    }
                }
            }
            result = result.getRelative(blockFace);
            if (count++ > this.range) {
                particleLocationList.add(LocationUtil.getCenterLocation(result));
                break;
            }
        }
        if (drawParticle && FinalTechChanged.getSlimefunTickCount() % this.particleInterval == 0) {
            JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawLineByDistance(javaPlugin, Particle.CRIT_MAGIC, this.particleInterval * Slimefun.getTickerTask().getTickRate() * 50L / particleLocationList.size(), this.particleDistance, input ? JavaUtil.reserve(particleLocationList) : particleLocationList));
        }
        return result;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTechChanged.getLanguageManager(), this,
                String.valueOf(this.range));
    }
}
