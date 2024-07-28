package io.taraxacum.finaltech.core.menu.machine;

import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

public class LogicComparatorMenu extends AbstractMachineMenu {
    private static final int[] BORDER = new int[]{5, 14, 23};
    private static final int[] INPUT_BORDER = new int[]{0, 1, 2, 3, 4, 9, 11, 13, 18, 19, 20, 21, 22};
    private static final int[] OUTPUT_BORDER = new int[]{6, 7, 8, 15, 17, 24, 25, 26};
    private static final int[] INPUT_SLOT = new int[]{10, 12};
    private static final int[] OUTPUT_SLOT = new int[]{16};

    public LogicComparatorMenu(@Nonnull AbstractMachine machine) {
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
    protected void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {

    }
}
