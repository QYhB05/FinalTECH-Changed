package io.taraxacum.finaltech.core.menu.machine;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.4
 */
public class EnergyInputTableMenu extends AbstractMachineMenu {
    private static final int[] BORDER = new int[0];
    private static final int[] INPUT_BORDER = new int[]{0, 6};
    private static final int[] OUTPUT_BORDER = new int[]{8};
    private static final int[] INPUT_SLOT = new int[]{1, 2, 3, 4, 5};
    private static final int[] OUTPUT_SLOT = new int[]{7};

    public EnergyInputTableMenu(@Nonnull SlimefunItem slimefunItem) {
        super(slimefunItem);
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
