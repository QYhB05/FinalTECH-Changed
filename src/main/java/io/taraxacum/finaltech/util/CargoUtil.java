package io.taraxacum.finaltech.util;

import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.core.dto.CargoDTO;
import io.taraxacum.finaltech.core.dto.SimpleCargoDTO;
import io.taraxacum.finaltech.core.helper.*;
import io.taraxacum.libs.plugin.dto.InvWithSlots;
import io.taraxacum.libs.plugin.dto.ItemWrapper;
import io.taraxacum.libs.plugin.dto.ServerRunnableLockFactory;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author Final_ROOT
 * @since 1.0
 */
// TODO: abstract as lib
public class CargoUtil {
    private static final int SEARCH_MAP_LIMIT = 3;
    private static final Future<Integer> ZERO_FUTURE = new FutureTask<>(() -> 0);

    /**
     * Do cargo action.
     * inputBlock should not be same with outputBlock
     *
     * @param cargoMode #{@link CargoMode}
     */
    public static Future<Integer> doCargo(@Nonnull CargoDTO cargoDTO, @Nonnull String cargoMode) {
        return switch (cargoMode) {
            case CargoMode.VALUE_INPUT_MAIN -> CargoUtil.doCargoInputMain(cargoDTO);
            case CargoMode.VALUE_OUTPUT_MAIN -> CargoUtil.doCargoOutputMain(cargoDTO);
            case CargoMode.VALUE_STRONG_SYMMETRY -> CargoUtil.doCargoStrongSymmetry(cargoDTO);
            case CargoMode.VALUE_WEAK_SYMMETRY -> CargoUtil.doCargoWeakSymmetry(cargoDTO);
            default -> ZERO_FUTURE;
        };
    }

    public static Future<Integer> doCargoStrongSymmetry(@Nonnull CargoDTO cargoDTO) {
        if (cargoDTO.getJavaPlugin().getServer().isPrimaryThread()) {
            InvWithSlots inputMap = CargoUtil.getInvWithSlots(cargoDTO.getInputBlock(), cargoDTO.getInputSize(), cargoDTO.getInputOrder());
            if (inputMap == null) {
                return ZERO_FUTURE;
            }
            InvWithSlots outputMap = CargoUtil.getInvWithSlots(cargoDTO.getOutputBlock(), cargoDTO.getOutputSize(), cargoDTO.getOutputOrder());
            if (outputMap == null) {
                return ZERO_FUTURE;
            }
            FutureTask<Integer> futureTask = new FutureTask<>(() -> CargoUtil.doSimpleCargoStrongSymmetry(new SimpleCargoDTO(cargoDTO, inputMap, outputMap)));
            // Hard to ensure data security in asynchronous threads. So just run it.
            futureTask.run();
            return futureTask;
        } else {
            cargoDTO.getJavaPlugin().getServer().getScheduler().runTask(cargoDTO.getJavaPlugin(), () -> {
                InvWithSlots inputMap = CargoUtil.getInvWithSlots(cargoDTO.getInputBlock(), cargoDTO.getInputSize(), cargoDTO.getInputOrder());
                if (inputMap == null) {
                    return;
                }
                InvWithSlots outputMap = CargoUtil.getInvWithSlots(cargoDTO.getOutputBlock(), cargoDTO.getOutputSize(), cargoDTO.getOutputOrder());
                if (outputMap == null) {
                    return;
                }
                ServerRunnableLockFactory.getInstance(cargoDTO.getJavaPlugin(), Location.class).waitThenRun(() -> CargoUtil.doSimpleCargoStrongSymmetry(new SimpleCargoDTO(cargoDTO, inputMap, outputMap)), cargoDTO.getInputBlock().getLocation(), cargoDTO.getOutputBlock().getLocation());
            });
            return ZERO_FUTURE;
        }
    }

    public static Future<Integer> doCargoWeakSymmetry(@Nonnull CargoDTO cargoDTO) {
        if (cargoDTO.getJavaPlugin().getServer().isPrimaryThread()) {
            InvWithSlots inputMap = CargoUtil.getInvWithSlots(cargoDTO.getInputBlock(), cargoDTO.getInputSize(), cargoDTO.getInputOrder());
            if (inputMap == null) {
                return ZERO_FUTURE;
            }
            InvWithSlots outputMap = CargoUtil.getInvWithSlots(cargoDTO.getOutputBlock(), cargoDTO.getOutputSize(), cargoDTO.getOutputOrder());
            if (outputMap == null) {
                return ZERO_FUTURE;
            }
            FutureTask<Integer> futureTask = new FutureTask<>(() -> CargoUtil.doSimpleCargoWeakSymmetry(new SimpleCargoDTO(cargoDTO, inputMap, outputMap)));
            // Hard to ensure data security in asynchronous threads. So just run it.
            futureTask.run();
            return futureTask;
        } else {
            cargoDTO.getJavaPlugin().getServer().getScheduler().runTask(cargoDTO.getJavaPlugin(), () -> {
                InvWithSlots inputMap = CargoUtil.getInvWithSlots(cargoDTO.getInputBlock(), cargoDTO.getInputSize(), cargoDTO.getInputOrder());
                if (inputMap == null) {
                    return;
                }
                InvWithSlots outputMap = CargoUtil.getInvWithSlots(cargoDTO.getOutputBlock(), cargoDTO.getOutputSize(), cargoDTO.getOutputOrder());
                if (outputMap == null) {
                    return;
                }
                ServerRunnableLockFactory.getInstance(cargoDTO.getJavaPlugin(), Location.class).waitThenRun(() -> CargoUtil.doSimpleCargoWeakSymmetry(new SimpleCargoDTO(cargoDTO, inputMap, outputMap)), cargoDTO.getInputBlock().getLocation(), cargoDTO.getOutputBlock().getLocation());
            });
            return ZERO_FUTURE;
        }
    }

    public static Future<Integer> doCargoInputMain(@Nonnull CargoDTO cargoDTO) {
        // OutputMap will be null if BlockStorage has inventory in output block.
        //      In this situation, we will get output inventory dynamically.
        // If there is no output inventory, just return 0.

        // Get inventory.
        if (cargoDTO.getJavaPlugin().getServer().isPrimaryThread()) {
            InvWithSlots inputMap;
            InvWithSlots outputMap;
            // Just get inputMap.
            inputMap = CargoUtil.getInvWithSlots(cargoDTO.getInputBlock(), cargoDTO.getInputSize(), cargoDTO.getInputOrder());
            if (BlockStorage.hasInventory(cargoDTO.getOutputBlock())) {
                outputMap = null;
            } else {
                if (cargoDTO.getOutputBlock().getState() instanceof InventoryHolder) {
                    // Output Inventory is vanilla container.
                    outputMap = CargoUtil.getInvWithSlots(cargoDTO.getOutputBlock(), cargoDTO.getOutputSize(), cargoDTO.getOutputOrder());
                    if (outputMap == null) {
                        return ZERO_FUTURE;
                    }
                } else {
                    // Output Inventory not existed.
                    return ZERO_FUTURE;
                }
            }
            FutureTask<Integer> futureTask = new FutureTask<>(() -> CargoUtil.doSimpleCargoInputMain(new SimpleCargoDTO(cargoDTO, inputMap, outputMap)));
            // Hard to ensure data security in asynchronous threads. So just run it.
            futureTask.run();
            return futureTask;
        } else {
            cargoDTO.getJavaPlugin().getServer().getScheduler().runTask(cargoDTO.getJavaPlugin(), () -> {
                InvWithSlots inputMap;
                InvWithSlots outputMap;
                // Just get inputMap.
                inputMap = CargoUtil.getInvWithSlots(cargoDTO.getInputBlock(), cargoDTO.getInputSize(), cargoDTO.getInputOrder());
                if (BlockStorage.hasInventory(cargoDTO.getOutputBlock())) {
                    outputMap = null;
                } else {
                    if (cargoDTO.getOutputBlock().getState() instanceof InventoryHolder) {
                        // Output Inventory is vanilla container.
                        outputMap = CargoUtil.getInvWithSlots(cargoDTO.getOutputBlock(), cargoDTO.getOutputSize(), cargoDTO.getOutputOrder());
                        if (outputMap == null) {
                            return;
                        }
                    } else {
                        // Output Inventory not existed.
                        return;
                    }
                }
                ServerRunnableLockFactory.getInstance(cargoDTO.getJavaPlugin(), Location.class).waitThenRun(() -> CargoUtil.doSimpleCargoInputMain(new SimpleCargoDTO(cargoDTO, inputMap, outputMap)), cargoDTO.getInputBlock().getLocation(), cargoDTO.getOutputBlock().getLocation());
            });
            return ZERO_FUTURE;
        }
    }

    public static Future<Integer> doCargoOutputMain(@Nonnull CargoDTO cargoDTO) {
        // InputMap will be null if BlockStorage has inventory in input block.
        //      In this situation, we will get input inventory dynamically.
        // If there is no input inventory, just return 0.

        // Get inventory.
        if (cargoDTO.getJavaPlugin().getServer().isPrimaryThread()) {
            InvWithSlots inputMap;
            InvWithSlots outputMap;
            // Just get outputMap.
            outputMap = CargoUtil.getInvWithSlots(cargoDTO.getOutputBlock(), cargoDTO.getOutputSize(), cargoDTO.getOutputOrder());
            if (BlockStorage.hasInventory(cargoDTO.getInputBlock())) {
                inputMap = null;
            } else {
                if (cargoDTO.getInputBlock().getState() instanceof InventoryHolder) {
                    // Input Inventory is vanilla container.
                    inputMap = CargoUtil.getInvWithSlots(cargoDTO.getInputBlock(), cargoDTO.getInputSize(), cargoDTO.getInputOrder());
                    if (inputMap == null) {
                        return ZERO_FUTURE;
                    }
                } else {
                    // Input Inventory not existed.
                    return ZERO_FUTURE;
                }
            }
            FutureTask<Integer> futureTask = new FutureTask<>(() -> CargoUtil.doSimpleCargoOutputMain(new SimpleCargoDTO(cargoDTO, inputMap, outputMap)));
            // Hard to ensure data security in asynchronous threads. So just run it.
            futureTask.run();
            return futureTask;
        } else {
            cargoDTO.getJavaPlugin().getServer().getScheduler().runTask(cargoDTO.getJavaPlugin(), () -> {
                InvWithSlots inputMap;
                InvWithSlots outputMap;
                // Just get outputMap.
                outputMap = CargoUtil.getInvWithSlots(cargoDTO.getOutputBlock(), cargoDTO.getOutputSize(), cargoDTO.getOutputOrder());
                if (BlockStorage.hasInventory(cargoDTO.getInputBlock())) {
                    inputMap = null;
                } else {
                    if (cargoDTO.getInputBlock().getState() instanceof InventoryHolder) {
                        // Input Inventory is vanilla container.
                        inputMap = CargoUtil.getInvWithSlots(cargoDTO.getInputBlock(), cargoDTO.getInputSize(), cargoDTO.getInputOrder());
                        if (inputMap == null) {
                            return;
                        }
                    } else {
                        // Input Inventory not existed.
                        return;
                    }
                }
                ServerRunnableLockFactory.getInstance(cargoDTO.getJavaPlugin(), Location.class).waitThenRun(() -> CargoUtil.doSimpleCargoInputMain(new SimpleCargoDTO(cargoDTO, inputMap, outputMap)), cargoDTO.getInputBlock().getLocation(), cargoDTO.getOutputBlock().getLocation());
            });
            return ZERO_FUTURE;
        }
    }

    /**
     * @param cargoMode Check whether params could be null depend on it.#{@link CargoMode#VALUE_INPUT_MAIN} #{@link CargoMode#VALUE_OUTPUT_MAIN} #{@link CargoMode#VALUE_STRONG_SYMMETRY} #{@link CargoMode#VALUE_WEAK_SYMMETRY}
     */
    public static int doSimpleCargo(@Nonnull SimpleCargoDTO simpleCargoDTO, @Nonnull String cargoMode) {
        return switch (cargoMode) {
            case CargoMode.VALUE_INPUT_MAIN -> CargoUtil.doSimpleCargoInputMain(simpleCargoDTO);
            case CargoMode.VALUE_OUTPUT_MAIN -> CargoUtil.doSimpleCargoOutputMain(simpleCargoDTO);
            case CargoMode.VALUE_STRONG_SYMMETRY -> CargoUtil.doSimpleCargoStrongSymmetry(simpleCargoDTO);
            case CargoMode.VALUE_WEAK_SYMMETRY -> CargoUtil.doSimpleCargoWeakSymmetry(simpleCargoDTO);
            default -> 0;
        };
    }

    public static int doSimpleCargoStrongSymmetry(@Nonnull SimpleCargoDTO simpleCargoDTO) {
        Inventory inputInv = simpleCargoDTO.getInputMap().getInventory();
        int[] inputSlots = simpleCargoDTO.getInputMap().getSlots();
        Inventory outputInv = simpleCargoDTO.getOutputMap().getInventory();
        int[] outputSlots = simpleCargoDTO.getOutputMap().getSlots();

        List<ItemWrapper> filterItemList = MachineUtil.getItemList(simpleCargoDTO.getFilterInv(), simpleCargoDTO.getFilterSlots());

        boolean nonnull = CargoLimit.VALUE_NONNULL.equals(simpleCargoDTO.getCargoLimit());
        boolean stack = !nonnull && CargoLimit.VALUE_STACK.equals(simpleCargoDTO.getCargoLimit());
        boolean first = !nonnull && !stack && CargoLimit.VALUE_FIRST.equals(simpleCargoDTO.getCargoLimit());
        boolean typeLimit = !nonnull && !first && CargoLimit.typeLimit(simpleCargoDTO.getCargoLimit());

        int number = 0;
        int cargoNumber = simpleCargoDTO.getCargoNumber();
        ItemWrapper typeItem = null;
        ItemWrapper inputItemWrapper = new ItemWrapper();

        for (int i = 0, length = Math.min(inputSlots.length, outputSlots.length); i < length; i++) {
            ItemStack inputItem = inputInv.getItem(inputSlots[i]);
            if (ItemStackUtil.isItemNull(inputItem)) {
                continue;
            }
            inputItemWrapper.newWrap(inputItem);
            if (!CargoUtil.isMatch(inputItemWrapper, filterItemList, simpleCargoDTO.getCargoFilter())) {
                continue;
            }
            if (typeItem != null && !ItemStackUtil.isItemSimilar(inputItemWrapper, typeItem)) {
                continue;
            }

            ItemStack outputItem = outputInv.getItem(outputSlots[i]);
            int count;
            if (ItemStackUtil.isItemNull(outputItem)) {
                if (!nonnull) {
                    // Move input item to output slot.
                    if (typeItem == null && typeLimit) {
                        typeItem = new ItemWrapper(ItemStackUtil.cloneItem(inputItem), inputItemWrapper.getItemMeta());
                    }
                    count = Math.min(inputItem.getAmount(), cargoNumber);
                    outputItem = inputItem.clone();
                    outputItem.setAmount(count);
                    outputInv.setItem(outputSlots[i], outputItem);
                    outputItem = outputInv.getItem(outputSlots[i]);
                    inputItem.setAmount(inputItem.getAmount() - count);
                } else {
                    continue;
                }
            } else {
                count = ItemStackUtil.stack(inputItemWrapper, outputItem, cargoNumber);
                if (count == 0) {
                    continue;
                }
                if (typeItem == null && typeLimit) {
                    typeItem = new ItemWrapper(ItemStackUtil.cloneItem(inputItem), inputItemWrapper.getItemMeta());
                }
            }

            cargoNumber -= count;
            number += count;
            if (cargoNumber == 0) {
                break;
            }
            if (stack) {
                cargoNumber = Math.min(cargoNumber, outputItem.getMaxStackSize() - outputItem.getAmount());
            }
            if (first) {
                break;
            }
        }
        return number;
    }

    public static int doSimpleCargoWeakSymmetry(@Nonnull SimpleCargoDTO simpleCargoDTO) {
        Inventory inputInv = simpleCargoDTO.getInputMap().getInventory();
        int[] inputSlots = simpleCargoDTO.getInputMap().getSlots();
        Inventory outputInv = simpleCargoDTO.getOutputMap().getInventory();
        int[] outputSlots = simpleCargoDTO.getOutputMap().getSlots();

        if (inputSlots.length == 0 || outputSlots.length == 0) {
            return 0;
        }

        List<ItemWrapper> filterItemList = MachineUtil.getItemList(simpleCargoDTO.getFilterInv(), simpleCargoDTO.getFilterSlots());

        boolean nonnull = CargoLimit.VALUE_NONNULL.equals(simpleCargoDTO.getCargoLimit());
        boolean stack = !nonnull && CargoLimit.VALUE_STACK.equals(simpleCargoDTO.getCargoLimit());
        boolean first = !nonnull && !stack && CargoLimit.VALUE_FIRST.equals(simpleCargoDTO.getCargoLimit());
        boolean typeLimit = !nonnull && !first && CargoLimit.typeLimit(simpleCargoDTO.getCargoLimit());

        int number = 0;
        int cargoNumber = simpleCargoDTO.getCargoNumber();
        ItemWrapper typeItem = null;
        ItemWrapper inputItemWrapper = new ItemWrapper();

        for (int i = 0, length = Math.max(inputSlots.length, outputSlots.length); i < length; i++) {
            ItemStack inputItem = inputInv.getItem(inputSlots[i % inputSlots.length]);
            if (ItemStackUtil.isItemNull(inputItem)) {
                continue;
            }
            inputItemWrapper.newWrap(inputItem);
            if (!CargoUtil.isMatch(inputItemWrapper, filterItemList, simpleCargoDTO.getCargoFilter())) {
                continue;
            }
            if (typeItem != null && !ItemStackUtil.isItemSimilar(inputItemWrapper, typeItem)) {
                continue;
            }

            ItemStack outputItem = outputInv.getItem(outputSlots[i % outputSlots.length]);
            int count;
            if (ItemStackUtil.isItemNull(outputItem)) {
                if (!nonnull) {
                    // Move input item to output slot.
                    if (typeItem == null && typeLimit) {
                        typeItem = new ItemWrapper(ItemStackUtil.cloneItem(inputItem), inputItemWrapper.getItemMeta());
                    }
                    count = Math.min(inputItem.getAmount(), cargoNumber);
                    outputItem = inputItem.clone();
                    outputItem.setAmount(count);
                    outputInv.setItem(outputSlots[i % outputSlots.length], outputItem);
                    outputItem = outputInv.getItem(outputSlots[i % outputSlots.length]);
                    inputItem.setAmount(inputItem.getAmount() - count);
                } else {
                    continue;
                }
            } else {
                count = ItemStackUtil.stack(inputItemWrapper, outputItem, cargoNumber);
                if (count == 0) {
                    continue;
                }
                if (typeItem == null && typeLimit) {
                    typeItem = new ItemWrapper(ItemStackUtil.cloneItem(inputItem), inputItemWrapper.getItemMeta());
                }
            }

            cargoNumber -= count;
            number += count;
            if (cargoNumber == 0) {
                break;
            }
            if (stack) {
                cargoNumber = Math.min(cargoNumber, outputItem.getMaxStackSize() - outputItem.getAmount());
            }
            if (first) {
                break;
            }
        }
        return number;
    }

    public static int doSimpleCargoInputMain(@Nonnull SimpleCargoDTO simpleCargoDTO) {
        Inventory inputInv = simpleCargoDTO.getInputMap().getInventory();
        int[] inputSlots = simpleCargoDTO.getInputMap().getSlots();

        List<ItemWrapper> skipItemList = new ArrayList<>(inputSlots.length);
        List<ItemWrapper> filterItemList = MachineUtil.getItemList(simpleCargoDTO.getFilterInv(), simpleCargoDTO.getFilterSlots());

        // If output block is a slimefun machine
        boolean dynamicOutputBlock = simpleCargoDTO.getOutputBlock() != null && simpleCargoDTO.getOutputMap() == null;
        List<ItemWrapper> searchItemList = new ArrayList<>(SEARCH_MAP_LIMIT);
        List<InvWithSlots> searchInvList = new ArrayList<>(SEARCH_MAP_LIMIT);
        boolean newOutputMap = false;
        InvWithSlots outputMap = simpleCargoDTO.getOutputMap();

        boolean nonnull = CargoLimit.VALUE_NONNULL.equals(simpleCargoDTO.getCargoLimit());
        boolean stack = !nonnull && CargoLimit.VALUE_STACK.equals(simpleCargoDTO.getCargoLimit());
        boolean first = !nonnull && !stack && CargoLimit.VALUE_FIRST.equals(simpleCargoDTO.getCargoLimit());
        boolean typeLimit = !nonnull && !first && CargoLimit.typeLimit(simpleCargoDTO.getCargoLimit());

        int number = 0;
        int cargoNumber = simpleCargoDTO.getCargoNumber();
        ItemWrapper typeItem = null;
        ItemWrapper inputItemWrapper = new ItemWrapper();

        for (int inputSlot : inputSlots) {
            ItemStack inputItem = inputInv.getItem(inputSlot);
            if (ItemStackUtil.isItemNull(inputItem)) {
                continue;
            }
            inputItemWrapper.newWrap(inputItem);
            if (typeItem != null && !ItemStackUtil.isItemSimilar(inputItemWrapper, typeItem)) {
                continue;
            }
            if (!CargoUtil.isMatch(inputItemWrapper, filterItemList, simpleCargoDTO.getCargoFilter())) {
                continue;
            }
            if (CargoUtil.isMatch(inputItemWrapper, skipItemList, CargoFilter.VALUE_WHITE)) {
                continue;
            }
            if (dynamicOutputBlock) {
                outputMap = null;
                newOutputMap = false;
                for (int i = 0; i < searchItemList.size(); i++) {
                    if (ItemStackUtil.isItemSimilar(inputItemWrapper, searchItemList.get(i))) {
                        outputMap = searchInvList.get(i);
                        break;
                    }
                }
                if (outputMap == null) {
                    outputMap = CargoUtil.getInvWithSlots(simpleCargoDTO.getOutputBlock(), simpleCargoDTO.getOutputSize(), simpleCargoDTO.getOutputOrder(), inputItem);
                    if (outputMap == null) {
                        continue;
                    }
                    newOutputMap = true;
                }
            }
            Inventory outputInv = outputMap.getInventory();
            int[] outputSlots = outputMap.getSlots();
            boolean work = false;
            for (int outputSlot : outputSlots) {
                ItemStack outputItem = outputInv.getItem(outputSlot);
                if (ItemStackUtil.isItemNull(outputItem)) {
                    if (!nonnull) {
                        if (typeItem == null && typeLimit) {
                            typeItem = new ItemWrapper(ItemStackUtil.cloneItem(inputItem), inputItemWrapper.getItemMeta());
                        }
                        int count = Math.min(inputItem.getAmount(), cargoNumber);
                        outputItem = ItemStackUtil.cloneItem(inputItem);
                        outputItem.setAmount(count);
                        outputInv.setItem(outputSlot, outputItem);
                        inputItem.setAmount(inputItem.getAmount() - count);
                        cargoNumber -= count;
                        number += count;
                        work = true;
                        if (stack) {
                            cargoNumber = Math.min(cargoNumber, outputItem.getMaxStackSize() - outputItem.getAmount());
                            break;
                        }
                        if (inputItem.getAmount() == 0 || cargoNumber == 0) {
                            break;
                        }
                    }
                } else if (outputItem.getAmount() < outputItem.getMaxStackSize() && ItemStackUtil.isItemSimilar(inputItemWrapper, outputItem)) {
                    if (typeItem == null && typeLimit) {
                        typeItem = new ItemWrapper(ItemStackUtil.cloneItem(inputItem), inputItemWrapper.getItemMeta());
                    }
                    int count = ItemStackUtil.stack(inputItemWrapper, outputItem, cargoNumber);
                    cargoNumber -= count;
                    number += count;
                    work = true;
                    if (stack) {
                        cargoNumber = Math.min(cargoNumber, outputItem.getMaxStackSize() - outputItem.getAmount());
                        break;
                    }
                    if (inputItem.getAmount() == 0 || cargoNumber == 0) {
                        break;
                    }
                }
            }
            if (cargoNumber == 0) {
                break;
            }
            if (work) {
                if (first) {
                    break;
                } else if (newOutputMap && searchItemList.size() < SEARCH_MAP_LIMIT) {
                    searchItemList.add(inputItemWrapper);
                    searchInvList.add(outputMap);
                }
            } else {
                skipItemList.add(inputItemWrapper);
            }
        }
        return number;
    }

    public static int doSimpleCargoOutputMain(@Nonnull SimpleCargoDTO simpleCargoDTO) {
        Inventory outputInv = simpleCargoDTO.getOutputMap().getInventory();
        int[] outputSlots = simpleCargoDTO.getOutputMap().getSlots();

        List<ItemWrapper> skipItemList = new ArrayList<>(simpleCargoDTO.getOutputMap().getSlots().length);
        List<ItemWrapper> filterList = MachineUtil.getItemList(simpleCargoDTO.getFilterInv(), simpleCargoDTO.getFilterSlots());

        boolean dynamicInputBlock = simpleCargoDTO.getInputBlock() != null && simpleCargoDTO.getInputMap() == null;
        List<ItemWrapper> searchItemList = new ArrayList<>(SEARCH_MAP_LIMIT);
        List<InvWithSlots> searchInvList = new ArrayList<>(SEARCH_MAP_LIMIT);
        InvWithSlots nullItemInputMap = null;
        boolean newInputMap = false;
        InvWithSlots inputMap = simpleCargoDTO.getInputMap();

        boolean nonnull = CargoLimit.VALUE_NONNULL.equals(simpleCargoDTO.getCargoLimit());
        boolean stack = !nonnull && CargoLimit.VALUE_STACK.equals(simpleCargoDTO.getCargoLimit());
        boolean first = !nonnull && !stack && CargoLimit.VALUE_FIRST.equals(simpleCargoDTO.getCargoLimit());
        boolean typeLimit = !nonnull && !first && CargoLimit.typeLimit(simpleCargoDTO.getCargoLimit());

        int number = 0;
        int cargoNumber = simpleCargoDTO.getCargoNumber();
        ItemWrapper typeItem = null;
        final ItemWrapper finalItemWrapper = new ItemWrapper();
        ItemWrapper outputItemWrapper;

        for (int outputSlot : outputSlots) {
            ItemStack outputItem = outputInv.getItem(outputSlot);
            outputItemWrapper = null;
            if (ItemStackUtil.isItemNull(outputItem)) {
                if (nonnull) {
                    continue;
                }
            } else {
                if (outputItem.getAmount() >= outputItem.getMaxStackSize()) {
                    continue;
                }
                outputItemWrapper = finalItemWrapper;
                outputItemWrapper.newWrap(outputItem);
                if (!CargoUtil.isMatch(outputItemWrapper, filterList, simpleCargoDTO.getCargoFilter())) {
                    continue;
                }
                if (CargoUtil.isMatch(outputItemWrapper, skipItemList, CargoFilter.VALUE_WHITE)) {
                    continue;
                }
                if (typeItem != null && !ItemStackUtil.isItemSimilar(outputItemWrapper, typeItem)) {
                    continue;
                }
            }
            if (dynamicInputBlock) {
                if (outputItemWrapper == null) {
                    if (nullItemInputMap == null) {
                        nullItemInputMap = CargoUtil.getInvWithSlots(simpleCargoDTO.getInputBlock(), simpleCargoDTO.getInputSize(), simpleCargoDTO.getInputOrder());
                    }
                    inputMap = nullItemInputMap;
                    newInputMap = false;
                } else {
                    inputMap = null;
                    newInputMap = false;
                    for (int i = 0; i < searchItemList.size(); i++) {
                        if (ItemStackUtil.isItemSimilar(outputItemWrapper, searchItemList.get(i))) {
                            inputMap = searchInvList.get(i);
                            break;
                        }
                    }
                    if (inputMap == null) {
                        inputMap = CargoUtil.getInvWithSlots(simpleCargoDTO.getInputBlock(), simpleCargoDTO.getInputSize(), simpleCargoDTO.getInputOrder(), outputItem);
                        if (inputMap == null) {
                            continue;
                        }
                        newInputMap = true;
                    }
                }
            }
            Inventory inputInv = inputMap.getInventory();
            int[] inputSlots = inputMap.getSlots();
            boolean work = false;
            for (int inputSlot : inputSlots) {
                ItemStack inputItem = inputInv.getItem(inputSlot);
                if (ItemStackUtil.isItemNull(inputItem)) {
                    continue;
                }
                ItemWrapper inputItemWrapper = new ItemWrapper(inputItem);
                if (outputItemWrapper == null) {
                    if (!CargoUtil.isMatch(inputItemWrapper, filterList, simpleCargoDTO.getCargoFilter())) {
                        continue;
                    }
                    if (typeItem != null && !ItemStackUtil.isItemSimilar(inputItemWrapper, typeItem)) {
                        continue;
                    }
                    if (typeItem == null && typeLimit) {
                        typeItem = new ItemWrapper(inputItem, inputItemWrapper.getItemMeta());
                    }
                    int count = Math.min(inputItem.getAmount(), cargoNumber);
                    outputItem = inputItem.clone();
                    outputItem.setAmount(count);
                    outputInv.setItem(outputSlot, outputItem);
                    outputItem = outputInv.getItem(outputSlot);
                    outputItemWrapper = finalItemWrapper;
                    outputItemWrapper.newWrap(outputItem, inputItemWrapper.getItemMeta());
                    inputItem.setAmount(inputItem.getAmount() - count);
                    cargoNumber -= count;
                    number += count;
                    work = true;
                    if (stack) {
                        cargoNumber = Math.min(cargoNumber, outputItem.getMaxStackSize() - outputItem.getAmount());
                    }
                    if (outputItem.getAmount() >= outputItem.getMaxStackSize() || cargoNumber == 0) {
                        break;
                    }
                } else if (outputItem.getAmount() < outputItem.getMaxStackSize() && ItemStackUtil.isItemSimilar(inputItemWrapper, outputItemWrapper)) {
                    if (typeItem == null && typeLimit) {
                        typeItem = new ItemWrapper(inputItem, inputItemWrapper.getItemMeta());
                    }
                    int count = ItemStackUtil.stack(inputItemWrapper, outputItemWrapper, cargoNumber);
                    cargoNumber -= count;
                    number += count;
                    work = true;
                    if (stack) {
                        cargoNumber = Math.min(cargoNumber, outputItem.getMaxStackSize() - outputItem.getAmount());
                    }
                    if (outputItem.getAmount() >= outputItem.getMaxStackSize() || cargoNumber == 0) {
                        break;
                    }
                }
            }
            if (cargoNumber == 0) {
                break;
            }
            if (work) {
                if (first) {
                    break;
                } else if (newInputMap && searchItemList.size() <= SEARCH_MAP_LIMIT) {
                    searchItemList.add(outputItemWrapper);
                    searchInvList.add(inputMap);
                }
            } else {
                if (outputItemWrapper == null) {
                    break;
                } else {
                    skipItemList.add(outputItemWrapper);
                }
            }
        }
        return number;
    }

    public static boolean isMatch(@Nonnull ItemWrapper itemWrapper, @Nonnull List<ItemWrapper> list, @Nonnull String cargoFilter) {
        for (ItemWrapper filterItem : list) {
            if (ItemStackUtil.isItemSimilar(itemWrapper, filterItem)) {
                return CargoFilter.VALUE_WHITE.equals(cargoFilter);
            }
        }
        return CargoFilter.VALUE_BLACK.equals(cargoFilter);
    }

    @Nullable
    public static InvWithSlots getInvWithSlots(@Nonnull Block block, @Nonnull String size, @Nonnull String order) {
        return CargoUtil.getInvWithSlots(block, size, order, ItemStackUtil.AIR);
    }

    @Nullable
    public static InvWithSlots getInvWithSlots(@Nonnull Block block, @Nonnull String size, @Nonnull String order, @Nonnull ItemStack item) {
        Inventory inventory = null;
        int[] slots = null;

        if (BlockStorage.hasInventory(block)) {
            BlockMenu blockMenu = BlockStorage.getInventory(block);
            inventory = blockMenu.toInventory();
            int[] insert;
            int[] withdraw;
            int i;
            switch (size) {
                case SlotSearchSize.VALUE_INPUTS_ONLY:
                    if (ItemStackUtil.isItemNull(item)) {
                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.INSERT);
                        if (slots.length == 0) {
                            slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, ItemStackUtil.AIR);
                        }
                    } else {
                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, item);
                    }
                    break;
                case SlotSearchSize.VALUE_OUTPUTS_ONLY:
                    if (ItemStackUtil.isItemNull(item)) {
                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
                        if (slots.length == 0) {
                            slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, ItemStackUtil.AIR);
                        }
                    } else {
                        slots = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, item);
                    }
                    break;
                case SlotSearchSize.VALUE_INPUTS_AND_OUTPUTS:
                    if (ItemStackUtil.isItemNull(item)) {
                        insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.INSERT);
                        if (insert.length == 0) {
                            insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, ItemStackUtil.AIR);
                        }
                        withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
                        if (withdraw.length == 0) {
                            withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, ItemStackUtil.AIR);
                        }
                    } else {
                        insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, item);
                        withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, item);
                    }
                    slots = new int[insert.length + withdraw.length];
                    i = 0;
                    for (; i < insert.length; i++) {
                        slots[i] = insert[i];
                    }
                    System.arraycopy(withdraw, 0, slots, i, withdraw.length);
                    break;
                case SlotSearchSize.VALUE_OUTPUTS_AND_INPUTS:
                    if (ItemStackUtil.isItemNull(item)) {
                        insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.INSERT);
                        if (insert.length == 0) {
                            insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, ItemStackUtil.AIR);
                        }
                        withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
                        if (withdraw.length == 0) {
                            withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, ItemStackUtil.AIR);
                        }
                    } else {
                        insert = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.INSERT, item);
                        withdraw = blockMenu.getPreset().getSlotsAccessedByItemTransport(blockMenu, ItemTransportFlow.WITHDRAW, item);
                    }
                    slots = new int[insert.length + withdraw.length];
                    i = 0;
                    for (; i < withdraw.length; i++) {
                        slots[i] = withdraw[i];
                    }
                    System.arraycopy(insert, 0, slots, i, insert.length);
                    break;
                default:
                    slots = new int[0];
            }
        } else {
            BlockState blockState = block.getState();
            if (blockState instanceof InventoryHolder inventoryHolder) {
                inventory = inventoryHolder.getInventory();
                slots = new int[inventory.getSize()];
                // TODO:
                for (int i = 0; i < slots.length; i++) {
                    slots[i] = i;
                }
            }
        }

        if (inventory == null || slots == null) {
            return null;
        }
        return CargoUtil.calInvWithSlots(inventory, slots, order);
    }

    @Nullable
    public static Inventory getVanillaInventory(@Nonnull Block block) {
        if (block.getState() instanceof InventoryHolder inventoryHolder) {
            return inventoryHolder.getInventory();
        }
        return null;
    }

    @Nonnull
    public static InvWithSlots calInvWithSlots(@Nonnull Inventory inventory, @Nonnull int[] slots, @Nonnull String order) {
        switch (order) {
            case SlotSearchOrder.VALUE_DESCEND:
                slots = JavaUtil.reserve(slots);
                break;
            case SlotSearchOrder.VALUE_FIRST_ONLY:
                if (slots.length > 0) {
                    slots = new int[]{slots[0]};
                }
                break;
            case SlotSearchOrder.VALUE_LAST_ONLY:
                if (slots.length > 0) {
                    slots = new int[]{slots[slots.length - 1]};
                }
            case SlotSearchOrder.VALUE_RANDOM:
                slots = JavaUtil.shuffle(slots);
            default:
                break;
        }
        return new InvWithSlots(inventory, slots);
    }

    @Nonnull
    public static InvWithSlots calInvWithSlots(@Nonnull Inventory inventory, @Nonnull String order) {
        int[] slots = new int[inventory.getSize()];
        // TODO:
        for (int i = 0; i < slots.length; i++) {
            slots[i] = i;
        }
        return CargoUtil.calInvWithSlots(inventory, slots, order);
    }

    public static boolean hasInventory(@Nonnull Block block) {
        if (BlockStorage.hasInventory(block)) {
            return true;
        }
        return block.getState() instanceof InventoryHolder;
    }
}
