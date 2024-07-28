package io.taraxacum.finaltech.core.interfaces;

import org.bukkit.Location;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.4
 */
public interface SimpleLocationMachine extends LocationMachine {

    @Override
    default Location[] getLocations(@Nonnull Location sourceLocation) {
        return new Location[]{this.getLocation(sourceLocation)};
    }

    Location getLocation(@Nonnull Location sourceLocation);
}
