package io.taraxacum.finaltech.core.menu.machine;

import io.taraxacum.common.util.JavaUtil;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.libs.plugin.dto.ItemWrapper;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.2
 */
public class MultiFrameMachineMenu extends AbstractMachineMenu {
    public static final int[] MACHINE_SLOT = new int[]{4, 13, 22, 31, 40, 49};
    public static final int[][][] WORK_INPUT_SLOT = new int[][][]{
            new int[][]{new int[]{0, 1, 2}, new int[]{0, 1, 2, 9, 10, 11}, new int[]{0, 1, 2, 9, 10, 11, 18, 19, 20}, new int[]{0, 1, 2, 9, 10, 11, 18, 19, 20, 27, 28, 29}, new int[]{0, 1, 2, 9, 10, 11, 18, 19, 20, 27, 28, 29, 36, 37, 38}, new int[]{0, 1, 2, 9, 10, 11, 18, 19, 20, 27, 28, 29, 36, 37, 38, 45, 46, 47}},
            new int[][]{new int[]{0, 1, 2, 9, 10, 11}, new int[]{9, 10, 11}, new int[]{9, 10, 11, 18, 19, 20}, new int[]{9, 10, 11, 18, 19, 20, 27, 28, 29}, new int[]{9, 10, 11, 18, 19, 20, 27, 28, 29, 36, 37, 38}, new int[]{9, 10, 11, 18, 19, 20, 27, 28, 29, 36, 37, 38, 45, 46, 47}},
            new int[][]{new int[]{0, 1, 2, 9, 10, 11, 18, 19, 20}, new int[]{9, 10, 11, 18, 19, 20}, new int[]{18, 19, 20}, new int[]{18, 19, 20, 27, 28, 29}, new int[]{18, 19, 20, 27, 28, 29, 36, 37, 38}, new int[]{18, 19, 20, 27, 28, 29, 36, 37, 38, 45, 46, 47}},
            new int[][]{new int[]{0, 1, 2, 9, 10, 11, 18, 19, 20, 27, 28, 29}, new int[]{9, 10, 11, 18, 19, 20, 27, 28, 29}, new int[]{18, 19, 20, 27, 28, 29}, new int[]{27, 28, 29}, new int[]{27, 28, 29, 36, 37, 38}, new int[]{27, 28, 29, 36, 37, 38, 45, 46, 47}},
            new int[][]{new int[]{0, 1, 2, 9, 10, 11, 18, 19, 20, 27, 28, 29, 36, 37, 38}, new int[]{9, 10, 11, 18, 19, 20, 27, 28, 29, 36, 37, 38}, new int[]{18, 19, 20, 27, 28, 29, 36, 37, 38}, new int[]{27, 28, 29, 36, 37, 38}, new int[]{36, 37, 38}, new int[]{36, 37, 38, 45, 46, 47}},
            new int[][]{new int[]{0, 1, 2, 9, 10, 11, 18, 19, 20, 27, 28, 29, 36, 37, 38, 45, 46, 47}, new int[]{9, 10, 11, 18, 19, 20, 27, 28, 29, 36, 37, 38, 45, 46, 47}, new int[]{18, 19, 20, 27, 28, 29, 36, 37, 38, 45, 46, 47}, new int[]{27, 28, 29, 36, 37, 38, 45, 46, 47}, new int[]{36, 37, 38, 45, 46, 47}, new int[]{45, 46, 47}}
    };
    public static final int[][][] WORK_OUTPUT_SLOT = new int[][][]{
            new int[][]{new int[]{6, 7, 8}, new int[]{6, 7, 8, 15, 16, 17}, new int[]{6, 7, 8, 15, 16, 17, 24, 25, 26}, new int[]{6, 7, 8, 15, 16, 17, 24, 25, 26, 33, 34, 35}, new int[]{6, 7, 8, 15, 16, 17, 24, 25, 26, 33, 34, 35, 42, 43, 44}, new int[]{6, 7, 8, 15, 16, 17, 24, 25, 26, 33, 34, 35, 42, 43, 44, 51, 52, 53}},
            new int[][]{new int[]{6, 7, 8, 15, 16, 17}, new int[]{15, 16, 17}, new int[]{15, 16, 17, 24, 25, 26}, new int[]{15, 16, 17, 24, 25, 26, 33, 34, 35}, new int[]{15, 16, 17, 24, 25, 26, 33, 34, 35, 42, 43, 44}, new int[]{15, 16, 17, 24, 25, 26, 33, 34, 35, 42, 43, 44, 51, 52, 53}},
            new int[][]{new int[]{6, 7, 8, 15, 16, 17, 24, 25, 26}, new int[]{15, 16, 17, 24, 25, 26}, new int[]{24, 25, 26}, new int[]{24, 25, 26, 33, 34, 35}, new int[]{24, 25, 26, 33, 34, 35, 42, 43, 44}, new int[]{24, 25, 26, 33, 34, 35, 42, 43, 44, 51, 52, 53}},
            new int[][]{new int[]{6, 7, 8, 15, 16, 17, 24, 25, 26, 33, 34, 35}, new int[]{15, 16, 17, 24, 25, 26, 33, 34, 35}, new int[]{24, 25, 26, 33, 34, 35}, new int[]{33, 34, 35}, new int[]{33, 34, 35, 42, 43, 44}, new int[]{33, 34, 35, 42, 43, 44, 51, 52, 53}},
            new int[][]{new int[]{6, 7, 8, 15, 16, 17, 24, 25, 26, 33, 34, 35, 42, 43, 44}, new int[]{15, 16, 17, 24, 25, 26, 33, 34, 35, 42, 43, 44}, new int[]{24, 25, 26, 33, 34, 35, 42, 43, 44}, new int[]{33, 34, 35, 42, 43, 44}, new int[]{42, 43, 44}, new int[]{42, 43, 44, 51, 52, 53}},
            new int[][]{new int[]{6, 7, 8, 15, 16, 17, 24, 25, 26, 33, 34, 35, 42, 43, 44, 51, 52, 53}, new int[]{15, 16, 17, 24, 25, 26, 33, 34, 35, 42, 43, 44, 51, 52, 53}, new int[]{24, 25, 26, 33, 34, 35, 42, 43, 44, 51, 52, 53}, new int[]{33, 34, 35, 42, 43, 44, 51, 52, 53}, new int[]{42, 43, 44, 51, 52, 53}, new int[]{51, 52, 53}}
    };
    private static final int[] BORDER = new int[0];
    private static final int[] INPUT_BORDER = new int[]{3, 12, 21, 30, 39, 48};
    private static final int[] OUTPUT_BORDER = new int[]{5, 14, 23, 32, 41, 50};
    private static final int[] INPUT_SLOT = new int[]{0, 1, 2, 9, 10, 11, 18, 19, 20, 27, 28, 29, 36, 37, 38, 45, 46, 47};
    private static final int[] OUTPUT_SLOT = new int[]{6, 7, 8, 15, 16, 17, 24, 25, 26, 33, 34, 35, 42, 43, 44, 51, 52, 53};

    public MultiFrameMachineMenu(@Nonnull AbstractMachine machine) {
        super(machine);
    }

    @Override
    protected int[] getBorder() {
        return BORDER;
    }

    @Override
    protected int[] getInputBorder() {
        return INPUT_BORDER;
    }

    @Override
    protected int[] getOutputBorder() {
        return OUTPUT_BORDER;
    }

    @Override
    public int[] getInputSlot() {
        return INPUT_SLOT;
    }

    @Override
    public int[] getOutputSlot() {
        return OUTPUT_SLOT;
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    protected void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {

    }

    @Override
    public int[] getSlotsAccessedByItemTransport(@Nullable ItemTransportFlow itemTransportFlow) {
        return super.getSlotsAccessedByItemTransport(itemTransportFlow);
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, @Nullable ItemTransportFlow flow, ItemStack item) {
        if (flow == null) {
            return new int[0];
        }

        if (item == null) {
            return flow == ItemTransportFlow.INSERT ? INPUT_SLOT : OUTPUT_SLOT;
        }

        List<Integer> result = new ArrayList<>();
        int[][][] workSlot = flow == ItemTransportFlow.INSERT ? WORK_INPUT_SLOT : WORK_OUTPUT_SLOT;

        int point = 0;
        ItemWrapper itemWrapper = new ItemWrapper(item);

        for (int i = 0; i < MACHINE_SLOT.length; i++) {
            ItemStack machineItem = menu.getItemInSlot(MACHINE_SLOT[i]);
            if (!ItemStackUtil.isItemNull(machineItem)) {
                boolean add = true;
                for (int slot : workSlot[point][i]) {
                    ItemStack existedItem = menu.getItemInSlot(slot);
                    if (ItemStackUtil.isItemSimilar(existedItem, itemWrapper)) {
                        add = false;
                    }
                }
                if (add) {
                    for (int slot : workSlot[point][i]) {
                        result.add(slot);
                    }
                }

                point = i + 1;
            }
        }

        return JavaUtil.toArray(result);
    }
}
