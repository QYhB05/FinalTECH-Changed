package io.taraxacum.finaltech.core.interfaces;

import org.bukkit.Location;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;

public interface PointMachine extends RangeMachine {
    default int pointFunction(@Nonnull Block block, int range, @Nonnull PointMachine.RangeFunction function) {
        return function.apply(this.getTargetLocation(block.getLocation(), range));
    }

    @Nonnull
    Location getTargetLocation(@Nonnull Location location, int range);
}
