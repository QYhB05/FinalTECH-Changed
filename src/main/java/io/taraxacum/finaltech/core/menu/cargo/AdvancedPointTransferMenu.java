package io.taraxacum.finaltech.core.menu.cargo;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.taraxacum.finaltech.core.helper.*;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

public class AdvancedPointTransferMenu extends AbstractMachineMenu {
    public static final int[] ITEM_MATCH = new int[]{30, 31, 32, 39, 40, 41, 48, 49, 50};
    private static final int[] BORDER = new int[]{21, 22, 23, 27, 28, 29, 33, 34, 35, 36, 37, 38, 42, 43, 44, 45, 46, 47, 51, 52, 53};
    private static final int[] INPUT_BORDER = new int[]{0, 2, 9, 11, 18, 20};
    private static final int[] OUTPUT_BORDER = new int[]{6, 8, 15, 17, 24, 26};
    private static final int[] INPUT_SLOT = new int[]{30, 31, 32, 39, 40, 41, 48, 49, 50};
    private static final int[] OUTPUT_SLOT = new int[]{30, 31, 32, 39, 40, 41, 48, 49, 50};
    private static final int CARGO_NUMBER_SUB_SLOT = 3;
    private static final int CARGO_NUMBER_SLOT = 4;
    private static final int CARGO_NUMBER_ADD_SLOT = 5;
    private static final int CARGO_FILTER_SLOT = 12;
    private static final int CARGO_MODE_SLOT = 13;
    private static final int CARGO_LIMIT_SLOT = 14;
    private static final int INPUT_SLOT_SEARCH_SIZE_SLOT = 1;
    private static final int INPUT_SLOT_SEARCH_ORDER_SLOT = 10;
    private static final int INPUT_BLOCK_SEARCH_MODE_SLOT = 19;
    private static final int OUTPUT_SLOT_SEARCH_SIZE_SLOT = 7;
    private static final int OUTPUT_SLOT_SEARCH_ORDER_SLOT = 16;
    private static final int OUTPUT_BLOCK_SEARCH_MODE_SLOT = 25;

    public AdvancedPointTransferMenu(@Nonnull AbstractMachine machine) {
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

        this.addItem(CARGO_NUMBER_SUB_SLOT, CargoNumber.CARGO_NUMBER_SUB_ICON);
        this.addItem(CARGO_NUMBER_SLOT, CargoNumber.CARGO_NUMBER_ICON);
        this.addMenuClickHandler(CARGO_NUMBER_SLOT, ChestMenuUtils.getEmptyClickHandler());
        this.addItem(CARGO_NUMBER_ADD_SLOT, CargoNumber.CARGO_NUMBER_ADD_ICON);
        this.addItem(CARGO_FILTER_SLOT, CargoFilter.HELPER.defaultIcon());
        this.addItem(CARGO_MODE_SLOT, CargoMode.HELPER.defaultIcon());
        this.addItem(CARGO_LIMIT_SLOT, CargoLimit.HELPER.defaultIcon());

        this.addItem(INPUT_SLOT_SEARCH_SIZE_SLOT, SlotSearchSize.INPUT_HELPER.defaultIcon());
        this.addItem(INPUT_SLOT_SEARCH_ORDER_SLOT, SlotSearchOrder.INPUT_HELPER.defaultIcon());
        this.addItem(INPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.POINT_INPUT_HELPER.defaultIcon());

        this.addItem(OUTPUT_SLOT_SEARCH_SIZE_SLOT, SlotSearchSize.OUTPUT_HELPER.defaultIcon());
        this.addItem(OUTPUT_SLOT_SEARCH_ORDER_SLOT, SlotSearchOrder.OUTPUT_HELPER.defaultIcon());
        this.addItem(OUTPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.POINT_OUTPUT_HELPER.defaultIcon());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        Inventory inventory = blockMenu.toInventory();
        Location location = block.getLocation();

        blockMenu.addMenuClickHandler(CARGO_NUMBER_SUB_SLOT, CargoNumber.HELPER.getPreviousHandler(inventory, location, this.getSlimefunItem(), CARGO_NUMBER_SLOT));
        blockMenu.addMenuClickHandler(CARGO_NUMBER_ADD_SLOT, CargoNumber.HELPER.getNextHandler(inventory, location, this.getSlimefunItem(), CARGO_NUMBER_SLOT));
        blockMenu.addMenuClickHandler(CARGO_FILTER_SLOT, CargoFilter.HELPER.getHandler(inventory, location, this.getSlimefunItem(), CARGO_FILTER_SLOT));
        blockMenu.addMenuClickHandler(CARGO_MODE_SLOT, CargoMode.HELPER.getHandler(inventory, location, this.getSlimefunItem(), CARGO_MODE_SLOT));
        blockMenu.addMenuClickHandler(CARGO_LIMIT_SLOT, CargoLimit.HELPER.getHandler(inventory, location, this.getSlimefunItem(), CARGO_LIMIT_SLOT));

        blockMenu.addMenuClickHandler(INPUT_SLOT_SEARCH_SIZE_SLOT, SlotSearchSize.INPUT_HELPER.getHandler(inventory, location, this.getSlimefunItem(), INPUT_SLOT_SEARCH_SIZE_SLOT));
        blockMenu.addMenuClickHandler(INPUT_SLOT_SEARCH_ORDER_SLOT, SlotSearchOrder.INPUT_HELPER.getHandler(inventory, location, this.getSlimefunItem(), INPUT_SLOT_SEARCH_ORDER_SLOT));
        blockMenu.addMenuClickHandler(INPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.POINT_INPUT_HELPER.getHandler(inventory, location, this.getSlimefunItem(), INPUT_BLOCK_SEARCH_MODE_SLOT));

        blockMenu.addMenuClickHandler(OUTPUT_SLOT_SEARCH_SIZE_SLOT, SlotSearchSize.OUTPUT_HELPER.getHandler(inventory, location, this.getSlimefunItem(), OUTPUT_SLOT_SEARCH_SIZE_SLOT));
        blockMenu.addMenuClickHandler(OUTPUT_SLOT_SEARCH_ORDER_SLOT, SlotSearchOrder.OUTPUT_HELPER.getHandler(inventory, location, this.getSlimefunItem(), OUTPUT_SLOT_SEARCH_ORDER_SLOT));
        blockMenu.addMenuClickHandler(OUTPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.POINT_OUTPUT_HELPER.getHandler(inventory, location, this.getSlimefunItem(), OUTPUT_BLOCK_SEARCH_MODE_SLOT));
    }

    @Override
    public void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {
        CargoNumber.HELPER.checkAndUpdateIcon(inventory, location, CARGO_NUMBER_SLOT);
        CargoFilter.HELPER.checkAndUpdateIcon(inventory, location, CARGO_FILTER_SLOT);
        CargoMode.HELPER.checkAndUpdateIcon(inventory, location, CARGO_MODE_SLOT);
        CargoLimit.HELPER.checkAndUpdateIcon(inventory, location, CARGO_LIMIT_SLOT);

        SlotSearchSize.INPUT_HELPER.checkAndUpdateIcon(inventory, location, INPUT_SLOT_SEARCH_SIZE_SLOT);
        SlotSearchOrder.INPUT_HELPER.checkAndUpdateIcon(inventory, location, INPUT_SLOT_SEARCH_ORDER_SLOT);
        BlockSearchMode.POINT_INPUT_HELPER.checkAndUpdateIcon(inventory, location, INPUT_BLOCK_SEARCH_MODE_SLOT);

        SlotSearchSize.OUTPUT_HELPER.checkAndUpdateIcon(inventory, location, OUTPUT_SLOT_SEARCH_SIZE_SLOT);
        SlotSearchOrder.OUTPUT_HELPER.checkAndUpdateIcon(inventory, location, OUTPUT_SLOT_SEARCH_ORDER_SLOT);
        BlockSearchMode.POINT_OUTPUT_HELPER.checkAndUpdateIcon(inventory, location, OUTPUT_BLOCK_SEARCH_MODE_SLOT);
    }
}
