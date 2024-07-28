package io.taraxacum.finaltech.core.menu.clicker;

import io.taraxacum.finaltech.core.item.machine.clicker.AbstractClickerMachine;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.2
 */
public abstract class AbstractClickerMenu extends AbstractMachineMenu {
    protected final AbstractClickerMachine clickerMachine;

    public AbstractClickerMenu(@Nonnull AbstractClickerMachine slimefunItem) {
        super(slimefunItem);
        this.clickerMachine = slimefunItem;
    }

    @Override
    public void newInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        super.newInstance(blockMenu, block);

        blockMenu.addMenuOpeningHandler(p -> {
            Location location = block.getLocation();
            Integer count = this.clickerMachine.getLocationCountMap().getOrDefault(location, 0);
            if (count < this.clickerMachine.getCountThreshold()) {
                this.clickerMachine.getLocationCountMap().put(location, ++count);
            } else {
                p.closeInventory();
                // TODO: waring info in console
                return;
            }

            if (p.getPlayer() != null) {
                AbstractClickerMenu.this.doFunction(blockMenu, block, p.getPlayer());
            }
        });
    }

    abstract protected void doFunction(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull Player player);
}
