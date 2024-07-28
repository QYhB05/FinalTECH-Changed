package io.taraxacum.finaltech.core.menu.machine;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.taraxacum.finaltech.core.interfaces.DigitalItem;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 * @since 2.2
 */
public class ConfigurationWorkerMenu extends AbstractMachineMenu {
    public static final int DIGITAL_SLOT = 4;
    private static final int[] BORDER = new int[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 13, 16, 17};
    private static final int[] INPUT_BORDER = new int[]{11};
    private static final int[] OUTPUT_BORDER = new int[]{15};
    private static final int[] INPUT_SLOT = new int[]{12};
    private static final int[] OUTPUT_SLOT = new int[]{14};

    public ConfigurationWorkerMenu(@Nonnull AbstractMachine machine) {
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

    @Override
    public int[] getSlotsAccessedByItemTransport(@Nullable ItemTransportFlow itemTransportFlow) {
        return super.getSlotsAccessedByItemTransport(itemTransportFlow);
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, @Nullable ItemTransportFlow flow, ItemStack item) {
        SlimefunItem slimefunItem = SlimefunItem.getByItem(item);
        if (slimefunItem instanceof DigitalItem) {
            return new int[]{DIGITAL_SLOT};
        } else {
            return this.getSlotsAccessedByItemTransport(flow);
        }
    }
}
