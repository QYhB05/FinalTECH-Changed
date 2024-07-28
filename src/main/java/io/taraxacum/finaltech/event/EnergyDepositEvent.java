package io.taraxacum.finaltech.core.event;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

/**
 * This event may be called in sync.
 *
 * @author Final_ROOT
 * @since 2.4
 */
public class EnergyDepositEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    /**
     * The target location you want to deposit energy
     */
    private final Location location;

    /**
     * How many energy you want to deposit to target location, for example: "1000"
     */
    private String energy;

    public EnergyDepositEvent(Location location, String energy) {
        super(false);
        this.location = location;
        this.energy = energy;
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
