package io.taraxacum.finaltech.core.menu.cargo;

import io.taraxacum.finaltech.core.helper.BlockSearchMode;
import io.taraxacum.finaltech.core.helper.CargoFilter;
import io.taraxacum.finaltech.core.helper.PositionInfo;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

public class MeshTransferMenu extends AbstractMachineMenu {
    public static final int[] ITEM_MATCH = new int[]{27, 28, 29, 36, 37, 38, 45, 46, 47};
    private static final int[] BORDER = new int[]{3, 4, 12, 13, 21, 22, 30, 31, 39, 40, 48, 49};
    private static final int[] INPUT_BORDER = new int[]{5, 14, 23};
    private static final int[] OUTPUT_BORDER = new int[]{32, 41, 50};
    private static final int[] INPUT_SLOT = new int[]{6, 7, 8, 15, 16, 17, 24, 25, 26};
    private static final int[] OUTPUT_SLOT = new int[]{33, 34, 35, 42, 43, 44, 51, 52, 53};
    private static final int POSITION_NORTH_SLOT = 1;
    private static final int POSITION_EAST_SLOT = 11;
    private static final int POSITION_SOUTH_SLOT = 19;
    private static final int POSITION_WEST_SLOT = 9;
    private static final int POSITION_UP_SLOT = 0;
    private static final int POSITION_DOWN_SLOT = 18;
    private static final int CARGO_FILTER_SLOT = 10;
    private static final int INPUT_BLOCK_SEARCH_MODE_SLOT = 2;
    private static final int OUTPUT_BLOCK_SEARCH_MODE_SLOT = 20;

    public MeshTransferMenu(@Nonnull AbstractMachine machine) {
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

        this.addItem(POSITION_NORTH_SLOT, PositionInfo.NORTH_ICON);
        this.addItem(POSITION_EAST_SLOT, PositionInfo.EAST_ICON);
        this.addItem(POSITION_SOUTH_SLOT, PositionInfo.SOUTH_ICON);
        this.addItem(POSITION_WEST_SLOT, PositionInfo.WEST_ICON);
        this.addItem(POSITION_UP_SLOT, PositionInfo.UP_ICON);
        this.addItem(POSITION_DOWN_SLOT, PositionInfo.DOWN_ICON);

        this.addItem(CARGO_FILTER_SLOT, CargoFilter.HELPER.defaultIcon());
        this.addItem(INPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.MESH_INPUT_HELPER.defaultIcon());
        this.addItem(OUTPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.MESH_OUTPUT_HELPER.defaultIcon());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        Inventory inventory = blockMenu.toInventory();
        Location location = block.getLocation();

        blockMenu.addMenuClickHandler(POSITION_NORTH_SLOT, PositionInfo.NORTH_HELPER.getHandler(inventory, location, this.getSlimefunItem(), POSITION_NORTH_SLOT));
        blockMenu.addMenuClickHandler(POSITION_EAST_SLOT, PositionInfo.EAST_HELPER.getHandler(inventory, location, this.getSlimefunItem(), POSITION_EAST_SLOT));
        blockMenu.addMenuClickHandler(POSITION_SOUTH_SLOT, PositionInfo.SOUTH_HELPER.getHandler(inventory, location, this.getSlimefunItem(), POSITION_SOUTH_SLOT));
        blockMenu.addMenuClickHandler(POSITION_WEST_SLOT, PositionInfo.WEST_HELPER.getHandler(inventory, location, this.getSlimefunItem(), POSITION_WEST_SLOT));
        blockMenu.addMenuClickHandler(POSITION_UP_SLOT, PositionInfo.UP_HELPER.getHandler(inventory, location, this.getSlimefunItem(), POSITION_UP_SLOT));
        blockMenu.addMenuClickHandler(POSITION_DOWN_SLOT, PositionInfo.DOWN_HELPER.getHandler(inventory, location, this.getSlimefunItem(), POSITION_DOWN_SLOT));

        blockMenu.addMenuClickHandler(CARGO_FILTER_SLOT, CargoFilter.HELPER.getHandler(inventory, location, this.getSlimefunItem(), CARGO_FILTER_SLOT));
        blockMenu.addMenuClickHandler(INPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.MESH_INPUT_HELPER.getHandler(inventory, location, this.getSlimefunItem(), INPUT_BLOCK_SEARCH_MODE_SLOT));
        blockMenu.addMenuClickHandler(OUTPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.MESH_OUTPUT_HELPER.getHandler(inventory, location, this.getSlimefunItem(), OUTPUT_BLOCK_SEARCH_MODE_SLOT));
    }

    @Override
    protected void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {
        PositionInfo.NORTH_HELPER.checkAndUpdateIcon(inventory, location, POSITION_NORTH_SLOT);
        PositionInfo.EAST_HELPER.checkAndUpdateIcon(inventory, location, POSITION_EAST_SLOT);
        PositionInfo.SOUTH_HELPER.checkAndUpdateIcon(inventory, location, POSITION_SOUTH_SLOT);
        PositionInfo.WEST_HELPER.checkAndUpdateIcon(inventory, location, POSITION_WEST_SLOT);
        PositionInfo.UP_HELPER.checkAndUpdateIcon(inventory, location, POSITION_UP_SLOT);
        PositionInfo.DOWN_HELPER.checkAndUpdateIcon(inventory, location, POSITION_DOWN_SLOT);

        CargoFilter.HELPER.checkAndUpdateIcon(inventory, location, CARGO_FILTER_SLOT);
        BlockSearchMode.MESH_INPUT_HELPER.checkAndUpdateIcon(inventory, location, INPUT_BLOCK_SEARCH_MODE_SLOT);
        BlockSearchMode.MESH_OUTPUT_HELPER.checkAndUpdateIcon(inventory, location, OUTPUT_BLOCK_SEARCH_MODE_SLOT);
    }
}
