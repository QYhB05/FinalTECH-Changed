package io.taraxacum.finaltech.core.listener;

import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.core.event.EnergyDepositEvent;
import io.taraxacum.finaltech.core.event.EnergyWithdrawEvent;
import io.taraxacum.finaltech.core.item.machine.electric.capacitor.expanded.AbstractExpandedElectricCapacitor;
import io.taraxacum.libs.slimefun.dto.LocationInfo;
import io.taraxacum.libs.slimefun.util.EnergyUtil;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Final_ROOT
 * @since 2.4
 */
public class ExpandedElectricCapacitorEnergyListener implements Listener {

    @EventHandler
    public void onEnergyDeposit(EnergyDepositEvent energyDepositEvent) {
        Location location = energyDepositEvent.getLocation();
        LocationInfo locationInfo = LocationInfo.get(location);
        if (locationInfo != null && locationInfo.getSlimefunItem() instanceof AbstractExpandedElectricCapacitor expandedElectricCapacitor) {
            int energy = Integer.parseInt(EnergyUtil.getCharge(locationInfo.getLocation()));
            int stack = Integer.parseInt(BlockStorage.getLocationInfo(locationInfo.getLocation(), "s"));

            long nowEnergy = expandedElectricCapacitor.calEnergy(energy, stack);
            long availableEnergy = expandedElectricCapacitor.getMaxEnergy() - nowEnergy;

            if (availableEnergy > 0) {
                String depositEnergy = energyDepositEvent.getEnergy();
                String transferEnergy = StringNumberUtil.min(depositEnergy, String.valueOf(availableEnergy));

                energyDepositEvent.setEnergy(StringNumberUtil.sub(depositEnergy, transferEnergy));
                nowEnergy += Long.parseLong(transferEnergy);
                expandedElectricCapacitor.setEnergy(location, nowEnergy);
            }
        }
    }

    @EventHandler
    public void onEnergyWithdraw(EnergyWithdrawEvent energyWithdrawEvent) {
        Location location = energyWithdrawEvent.getLocation();
        LocationInfo locationInfo = LocationInfo.get(location);
        if (locationInfo != null && locationInfo.getSlimefunItem() instanceof AbstractExpandedElectricCapacitor expandedElectricCapacitor) {
            int energy = Integer.parseInt(EnergyUtil.getCharge(locationInfo.getLocation()));
            int stack = Integer.parseInt(BlockStorage.getLocationInfo(locationInfo.getLocation(), "s"));


            long nowEnergy = expandedElectricCapacitor.calEnergy(energy, stack);
            expandedElectricCapacitor.setEnergy(location, 0);

            energyWithdrawEvent.setEnergy(StringNumberUtil.add(energyWithdrawEvent.getEnergy(), String.valueOf(nowEnergy)));
        }
    }
}
