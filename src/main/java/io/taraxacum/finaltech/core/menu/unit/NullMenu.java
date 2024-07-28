package io.taraxacum.finaltech.core.menu.unit;

import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

public class NullMenu extends AbstractMachineMenu {
    public NullMenu(@Nonnull AbstractMachine machine) {
        super(machine);
    }

    @Override
    protected int[] getBorder() {
        return new int[0];
    }

    @Override
    protected int[] getInputBorder() {
        return new int[0];
    }

    @Override
    protected int[] getOutputBorder() {
        return new int[0];
    }

    @Override
    public int[] getInputSlot() {
        return new int[0];
    }

    @Override
    public int[] getOutputSlot() {
        return new int[0];
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);
        this.addMenuOpeningHandler(p -> blockMenu.close());
    }

    @Override
    public void init() {
        super.init();
        this.setSize(9);
    }

    @Override
    protected void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {

    }
}
