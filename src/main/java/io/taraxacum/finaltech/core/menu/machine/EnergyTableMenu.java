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
public class EnergyTableMenu extends AbstractMachineMenu {
    public static final int CARD_SLOT = 4;
    private static final int[] BORDER = new int[]{3, 5};
    private static final int[] INPUT_BORDER = new int[]{0, 2};
    private static final int[] OUTPUT_BORDER = new int[]{6, 8};
    private static final int[] INPUT_SLOT = new int[]{1};
    private static final int[] OUTPUT_SLOT = new int[]{7};

    public EnergyTableMenu(@Nonnull SlimefunItem slimefunItem) {
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
