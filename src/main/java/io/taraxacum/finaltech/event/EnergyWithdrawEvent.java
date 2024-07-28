package io.taraxacum.finaltech.core.event;

import io.taraxacum.common.util.StringNumberUtil;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

/**
 * This event may be called in sync.
 * You may call {@link EnergyDepositEvent} later.
 *
 * @author Final_ROOT
 * @since 2.4
 */
public class EnergyWithdrawEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    /**
     * The target location you want to deposit energy
     */
    private final Location location;

    /**
     * How many energy you have got after the event being called.
     * You may call {@link EnergyDepositEvent} later.
     */
    private String energy;

    public EnergyWithdrawEvent(Location location) {
        this.location = location;
        this.energy = StringNumberUtil.ZERO;
    }

    @Nonnull
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Location getLocation() {
        return location;
    }

    public String getEnergy() {
        return energy;
    }

    public void setEnergy(String energy) {
        this.energy = energy;
    }

    @Nonnull
    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }
}
