package io.taraxacum.finaltech.core.menu.manual;

import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;
 
public abstract class AbstractManualMachineMenu extends AbstractMachineMenu {
    public AbstractManualMachineMenu(@Nonnull AbstractMachine machine) {
        super(machine);
    }

    @Override
    public abstract void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location);
}
