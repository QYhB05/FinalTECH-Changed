package io.taraxacum.finaltech.core.patch;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNet;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.interfaces.MenuUpdater;
import io.taraxacum.finaltech.core.menu.unit.StatusMenu;
import io.taraxacum.finaltech.core.networks.AlteredEnergyNet;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.lang.reflect.Field;

/**
 * @author Final_ROOT
 * @since 2.4
 */
public class EnergyRegulatorBlockTicker extends BlockTicker implements MenuUpdater {
    private boolean init = false;

    public synchronized void enable() {
        if (this.init) {
            return;
        }

        SlimefunItem energyRegulator = SlimefunItem.getByItem(SlimefunItems.ENERGY_REGULATOR);
        if (energyRegulator != null) {
            // register blockMenu
            new EnergyRegulatorStaticsMenu(energyRegulator);

            // replace blockTicker
            BlockTicker blockTicker = new BlockTicker() {
                @Override
                public boolean isSynchronized() {
                    return false;
                }

                @Override
                public void tick(Block b, SlimefunItem item, Config data) {
                    EnergyRegulatorBlockTicker.this.tick(b, item, data);
                }

                @Override
                public void uniqueTick() {
                    AlteredEnergyNet.uniqueTick();
                }
            };

            try {
                final Class<SlimefunItem> clazz = SlimefunItem.class;
                Field declaredField = clazz.getDeclaredField("blockTicker");
                declaredField.setAccessible(true);
                declaredField.set(energyRegulator, blockTicker);
                declaredField.setAccessible(false);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            FinalTechChanged.logger().severe("Can not find energy regulator! Why?");
        }

        this.init = true;
    }

    @Override
    public boolean isSynchronized() {
        return false;
    }

    @Override
    public void tick(Block block, SlimefunItem slimefunItem, Config config) {
        Location location = block.getLocation();
        EnergyNet energyNetwork = AlteredEnergyNet.getNetworkFromLocationOrCreate(location);
        if (energyNetwork instanceof AlteredEnergyNet alteredEnergyNet) {
            AlteredEnergyNet.Summary summary = alteredEnergyNet.tick(block, slimefunItem, config);
            BlockMenu blockMenu = BlockStorage.getInventory(location);
            if (blockMenu != null && blockMenu.hasViewer()) {
                this.updateMenu(blockMenu, StatusMenu.STATUS_SLOT, slimefunItem,
                        String.valueOf(summary.getConsumerAmount()),
                        String.valueOf(summary.getConsumerEnergy()),
                        String.valueOf(summary.getConsumerCapacity()),
                        String.valueOf(summary.getGeneratorAmount()),
                        String.valueOf(summary.getGeneratorEnergy()),
                        String.valueOf(summary.getGeneratorCapacity()),
                        String.valueOf(summary.getCapacitorAmount()),
                        String.valueOf(summary.getCapacitorEnergy()),
                        String.valueOf(summary.getCapacitorCapacity()),
                        String.valueOf(summary.getGeneratedEnergy()),
                        String.valueOf(summary.getTransferredEnergy()));
            }
        } else {
            energyNetwork.markDirty(location);
            energyNetwork.tick(block);
        }
    }
}
