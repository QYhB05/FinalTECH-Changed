package io.taraxacum.finaltech.core.interfaces;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.4
 */
public interface CubeMachine extends RangeMachine, LocationMachine {
    default int cubeFunction(@Nonnull Block block, int range, @Nonnull RangeFunction function) {
        int count = 0;
        Location location = block.getLocation();
        World world = location.getWorld();
        int minX = location.getBlockX() - range;
        int minY = Math.max(location.getBlockY() - range, world.getMinHeight());
        int minZ = location.getBlockZ() - range;
        int maxX = location.getBlockX() + range;
        int maxY = Math.min(location.getBlockY() + range, world.getMaxHeight());
        int maxZ = location.getBlockZ() + range;
        int result;
        for (int x = minX; x <= maxX; x++) {
            location.setX(x);
            for (int y = minY; y <= maxY; y++) {
                location.setY(y);
                for (int z = minZ; z <= maxZ; z++) {
                    location.setZ(z);
                    result = function.apply(location);
                    if (result == -1) {
                        return count;
                    }
                    count += result;
                }
            }
        }
        return count;
    }
}
