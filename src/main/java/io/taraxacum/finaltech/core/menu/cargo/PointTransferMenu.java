package io.taraxacum.finaltech.core.menu.cargo;

import io.taraxacum.finaltech.core.helper.BlockSearchMode;
import io.taraxacum.finaltech.core.helper.CargoFilter;
import io.taraxacum.finaltech.core.helper.CargoMode;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;
 
public class PointTransferMenu extends AbstractMachineMenu {
    public static final int[] ITEM_MATCH = new int[]{30, 31, 32, 39, 40, 41, 48, 49, 50};
    private static final int[] BORDER = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 14, 15, 16, 17, 21, 22, 23, 27, 28, 29, 33, 34, 35, 36, 37, 38, 42, 43, 44, 45, 46, 47, 51, 52, 53};
    private static final int[] INPUT_BORDER = new int[]{18, 20};
    private static final int[] OUTPUT_BORDER = new int[]{24, 26};
    private static final int[] INPUT_SLOT = new int[]{30, 31, 32, 39, 40, 41, 48, 49, 50};
    private static final int[] OUTPUT_SLOT = new int[]{30, 31, 32, 39, 40, 41, 48, 49, 50};
    private static final int CARGO_FILTER_SLOT = 12;
    private static final int CARGO_MODE_SLOT = 13;
    private static final int INPUT_BLOCK_SEARCH_MODE_SLOT = 19;
    private static final int OUTPUT_BLOCK_SEARCH_MODE_SLOT = 25;

    public PointTransferMenu(@Nonnull AbstractMachine machine) {
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

        this.addItem(CARGO_FILTER_SLOT, CargoFilter.HELPER.defaultIcon());
        this.addItem(CARGO_MODE_SLOT, CargoMode.HELPER.defaultIcon());

        this.addItem(INPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.POINT_INPUT_HELPER.defaultIcon());

        this.addItem(OUTPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.POINT_OUTPUT_HELPER.defaultIcon());
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        Inventory inventory = blockMenu.toInventory();
        Location location = block.getLocation();

        blockMenu.addMenuClickHandler(CARGO_FILTER_SLOT, CargoFilter.HELPER.getHandler(inventory, location, this.getSlimefunItem(), CARGO_FILTER_SLOT));
        blockMenu.addMenuClickHandler(CARGO_MODE_SLOT, CargoMode.HELPER.getHandler(inventory, location, this.getSlimefunItem(), CARGO_MODE_SLOT));

        blockMenu.addMenuClickHandler(INPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.POINT_INPUT_HELPER.getHandler(inventory, location, this.getSlimefunItem(), INPUT_BLOCK_SEARCH_MODE_SLOT));

        blockMenu.addMenuClickHandler(OUTPUT_BLOCK_SEARCH_MODE_SLOT, BlockSearchMode.POINT_OUTPUT_HELPER.getHandler(inventory, location, this.getSlimefunItem(), OUTPUT_BLOCK_SEARCH_MODE_SLOT));
    }

    @Override
    public void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {
        CargoFilter.HELPER.checkAndUpdateIcon(inventory, location, CARGO_FILTER_SLOT);
        CargoMode.HELPER.checkAndUpdateIcon(inventory, location, CARGO_MODE_SLOT);

        BlockSearchMode.POINT_INPUT_HELPER.checkAndUpdateIcon(inventory, location, INPUT_BLOCK_SEARCH_MODE_SLOT);

        BlockSearchMode.POINT_OUTPUT_HELPER.checkAndUpdateIcon(inventory, location, OUTPUT_BLOCK_SEARCH_MODE_SLOT);
    }
}
