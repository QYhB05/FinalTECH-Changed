package io.taraxacum.finaltech.core.item.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.dto.SimpleCargoDTO;
import io.taraxacum.finaltech.core.helper.CargoFilter;
import io.taraxacum.finaltech.core.helper.CargoLimit;
import io.taraxacum.finaltech.core.helper.SlotSearchOrder;
import io.taraxacum.finaltech.core.helper.SlotSearchSize;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.MultiFrameMachineMenu;
import io.taraxacum.finaltech.util.CargoUtil;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.dto.AdvancedMachineRecipe;
import io.taraxacum.libs.plugin.dto.InvWithSlots;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.slimefun.dto.AdvancedCraft;
import io.taraxacum.libs.slimefun.dto.MachineRecipeFactory;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.2
 */
public class MultiFrameMachine extends AbstractMachine implements RecipeItem {
    private final String[] offsetKeys = new String[]{"offset1", "offset2", "offset3", "offset4", "offset5", "offset6"};
    private final List<String> allowedIdList = ConfigUtil.getItemStringList(this, "allowed-id");

    public MultiFrameMachine(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
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
        return new MultiFrameMachineMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        Inventory inventory = blockMenu.toInventory();

        int point = 0;
        int offset;
        List<AdvancedMachineRecipe> availableRecipe;

        int machineSlot;
        ItemStack machineItem;
        SlimefunItem machineSfItem;
        AdvancedCraft advancedCraft;

        List<int[]> inputs = new ArrayList<>();
        List<int[]> outputs = new ArrayList<>();

        for (int i = 0; i < MultiFrameMachineMenu.MACHINE_SLOT.length; i++) {
            machineSlot = MultiFrameMachineMenu.MACHINE_SLOT[i];
            machineItem = inventory.getItem(machineSlot);
            if (!ItemStackUtil.isItemNull(machineItem)) {
                machineSfItem = SlimefunItem.getByItem(machineItem);
                if (machineSfItem != null && this.allowedIdList.contains(machineSfItem.getId())) {
                    MachineUtil.stockSlots(inventory, MultiFrameMachineMenu.WORK_INPUT_SLOT[point][i]);

                    if (MachineUtil.slotCount(inventory, MultiFrameMachineMenu.WORK_OUTPUT_SLOT[point][i]) < MultiFrameMachineMenu.WORK_OUTPUT_SLOT[point][i].length) {
                        offset = config.contains(this.offsetKeys[i]) ? Integer.parseInt(config.getString(this.offsetKeys[i])) : 0;
                        availableRecipe = MachineRecipeFactory.getInstance().getAdvancedRecipe(machineSfItem.getId());
                        advancedCraft = AdvancedCraft.craftAsc(inventory, MultiFrameMachineMenu.WORK_INPUT_SLOT[point][i], availableRecipe, machineItem.getAmount(), offset);
                        if (advancedCraft != null) {
                            advancedCraft.setMatchCount(Math.min(advancedCraft.getMatchCount(), MachineUtil.calMaxMatch(inventory, MultiFrameMachineMenu.WORK_OUTPUT_SLOT[point][i], advancedCraft.getOutputItemList())));
                            if (advancedCraft.getMatchCount() > 0) {
                                advancedCraft.consumeItem(inventory);
                                for (ItemStack itemStack : advancedCraft.calMachineRecipe(0).getOutput()) {
                                    blockMenu.pushItem(ItemStackUtil.cloneItem(itemStack), MultiFrameMachineMenu.WORK_OUTPUT_SLOT[point][i]);
                                }
                                config.setValue(this.offsetKeys[i], String.valueOf(advancedCraft.getOffset()));
                            }
                        }
                    }

                    MachineUtil.stockSlots(inventory, MultiFrameMachineMenu.WORK_OUTPUT_SLOT[point][i]);

                    inputs.add(MultiFrameMachineMenu.WORK_INPUT_SLOT[point][i]);
                    outputs.add(MultiFrameMachineMenu.WORK_OUTPUT_SLOT[point][i]);
                    point = i + 1;
                }
            }
        }

        if (inputs.size() > 1) {
            SimpleCargoDTO simpleCargoDTO = new SimpleCargoDTO();
            simpleCargoDTO.setInputBlock(block);
            simpleCargoDTO.setInputSize(SlotSearchSize.VALUE_OUTPUTS_ONLY);
            simpleCargoDTO.setInputOrder(SlotSearchOrder.VALUE_ASCENT);
            simpleCargoDTO.setOutputBlock(block);
            simpleCargoDTO.setOutputSize(SlotSearchSize.VALUE_INPUTS_ONLY);
            simpleCargoDTO.setOutputOrder(SlotSearchOrder.VALUE_ASCENT);
            simpleCargoDTO.setCargoNumber(3456);
            simpleCargoDTO.setCargoLimit(CargoLimit.VALUE_ALL);
            simpleCargoDTO.setCargoFilter(CargoFilter.VALUE_BLACK);
            simpleCargoDTO.setFilterInv(blockMenu.toInventory());
            simpleCargoDTO.setFilterSlots(new int[0]);

            for (int i = 0; i < inputs.size() - 1; i++) {
                simpleCargoDTO.setInputMap(new InvWithSlots(blockMenu.toInventory(), outputs.get(i)));
                simpleCargoDTO.setOutputMap(new InvWithSlots(blockMenu.toInventory(), inputs.get((i + 1) % outputs.size())));
                CargoUtil.doSimpleCargoWeakSymmetry(simpleCargoDTO);
            }
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipeWithBorder(FinalTechChanged.getLanguageManager(), this);

        for (String id : this.allowedIdList) {
            SlimefunItem slimefunItem = SlimefunItem.getById(id);
            if (slimefunItem != null) {
                this.registerDescriptiveRecipe(slimefunItem.getRecipeOutput());
            }
        }
    }
}
