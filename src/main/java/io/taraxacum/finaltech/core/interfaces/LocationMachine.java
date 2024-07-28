package io.taraxacum.finaltech.core.interfaces;

import org.bukkit.Location;

import javax.annotation.Nonnull;

/**
 * A Slimefun machine that will have impact on data at the specified location.
 * And should be token great care in async program.
 *
 * @author Final_ROOT
 * @see io.taraxacum.finaltech.core.item.machine.AbstractMachine
 * @since 2.2
 */
public interface LocationMachine {

    /**
     * The location it will have impact on.
     *
     * @param sourceLocation
     * @return
     */
    Location[] getLocations(@Nonnull Location sourceLocation);
}
