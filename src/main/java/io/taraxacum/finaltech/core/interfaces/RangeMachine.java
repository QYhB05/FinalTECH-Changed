package io.taraxacum.finaltech.core.interfaces;

import org.bukkit.Location;

import java.util.function.Function;

public interface RangeMachine {
    interface RangeFunction extends Function<Location, Integer> {
        @Override
        Integer apply(Location location);
    }
}
