package io.taraxacum.finaltech.core.menu.cargo;

import io.taraxacum.finaltech.core.helper.CargoMode;
import io.taraxacum.finaltech.core.helper.CargoOrder;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;
 
public class LocationTransferMenu extends AbstractMachineMenu {
    public static final int LOCATION_RECORDER_SLOT = 40;
    private static final int[] BORDER = new int[]{27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 41, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};
    private static final int[] INPUT_BORDER = new int[0];
    private static final int[] OUTPUT_BORDER = new int[0];
    private static final int[] INPUT_SLOT = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
    private static final int[] OUTPUT_SLOT = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
    private static final int CARGO_MODE_SLOT = 42;
    private static final int CARGO_ORDER_SLOT = 43;
    private static final int LINE1_SLOT = 51;
    private static final int LINE2_SLOT = 52;
    private static final int LINE3_SLOT = 53;

    public LocationTransferMenu(@Nonnull AbstractMachine machine) {
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

        this.addItem(CARGO_MODE_SLOT, CargoMode.HELPER.defaultIcon());
        this.addItem(CARGO_ORDER_SLOT, CargoOrder.HELPER.defaultIcon());

//        this.addItem(LINE1_SLOT, SlotSearchLine.L1_ICON);
//        this.addItem(LINE2_SLOT, SlotSearchLine.L2_ICON);
//        this.addItem(LINE3_SLOT, SlotSearchLine.L3_ICON);
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        Inventory inventory = blockMenu.toInventory();
        Location location = block.getLocation();

        blockMenu.addMenuClickHandler(CARGO_MODE_SLOT, CargoMode.HELPER.getHandler(inventory, location, this.getSlimefunItem(), CARGO_MODE_SLOT));
        blockMenu.addMenuClickHandler(CARGO_ORDER_SLOT, CargoOrder.HELPER.getHandler(inventory, location, this.getSlimefunItem(), CARGO_ORDER_SLOT));

//        blockMenu.addMenuClickHandler(LINE1_SLOT, SlotSearchLine.L1_HELPER.getHandler(inventory, location, this.getSlimefunItem(), LINE1_SLOT));
//        blockMenu.addMenuClickHandler(LINE2_SLOT, SlotSearchLine.L2_HELPER.getHandler(inventory, location, this.getSlimefunItem(), LINE2_SLOT));
//        blockMenu.addMenuClickHandler(LINE3_SLOT, SlotSearchLine.L3_HELPER.getHandler(inventory, location, this.getSlimefunItem(), LINE3_SLOT));
    }

    @Override
    protected void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {
        CargoMode.HELPER.checkAndUpdateIcon(inventory, location, CARGO_MODE_SLOT);
        CargoOrder.HELPER.checkAndUpdateIcon(inventory, location, CARGO_ORDER_SLOT);

//        SlotSearchLine.L1_HELPER.checkAndUpdateIcon(inventory, location, LINE1_SLOT);
//        SlotSearchLine.L2_HELPER.checkAndUpdateIcon(inventory, location, LINE2_SLOT);
//        SlotSearchLine.L3_HELPER.checkAndUpdateIcon(inventory, location, LINE3_SLOT);
    }
}
