package io.taraxacum.finaltech.core.menu.machine;

import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class ItemSerializationConstructorMenu extends AbstractMachineMenu {
    public static final int STATUS_SLOT = 40;
    private static final int[] BORDER = new int[]{45, 46, 47, 51, 52, 53};
    private static final int[] INPUT_BORDER = new int[]{36, 37, 38, 39, 40, 41, 42, 43, 44};
    private static final int[] OUTPUT_BORDER = new int[]{48, 50};
    private static final int[] INPUT_SLOT = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35};
    private static final int[] OUTPUT_SLOT = new int[]{49};

    public ItemSerializationConstructorMenu(@Nonnull AbstractMachine abstractMachine) {
        super(abstractMachine);
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
