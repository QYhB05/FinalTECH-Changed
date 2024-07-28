package io.taraxacum.finaltech.util;

import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// TODO: abstract as lib
public class LocationUtil {
    private static final NamespacedKey KEY = new NamespacedKey(FinalTechChanged.getInstance(), "location");

    public static double getManhattanDistance(@Nonnull Location location1, @Nonnull Location location2) {
        return Math.abs(location1.getX() - location2.getX()) + Math.abs(location1.getY() - location2.getY()) + Math.abs(location1.getZ() - location2.getZ());
    }

    public static boolean isSameLocation(@Nullable Location location1, @Nullable Location location2) {
        if (location1 == null || location2 == null) {
            return false;
        }
        if (location1 == location2) {
            return true;
        }
        if (location1.getWorld() != location2.getWorld()) {
            return false;
        } else if (location1.getX() != location2.getX()) {
            return false;
        } else if (location1.getY() != location2.getY()) {
            return false;
        } else if (location1.getZ() != location2.getZ()) {
            return false;
        }
        return true;
    }

    @Nonnull
    public static Location fromRandom(@Nonnull Location location, @Nonnull Random random, double x, double y, double z) {
        return new Location(location.getWorld(), location.getX() + random.nextDouble() * x * 2 - x, location.getY() + random.nextDouble() * y * 2 - y, location.getZ() + random.nextDouble() * z * 2 - z, location.getYaw(), location.getPitch());
    }

    @Nonnull
    public static Location fromRandom(@Nonnull Location location, @Nonnull Random random, double r) {
        return LocationUtil.fromRandom(location, random, r, r, r);
    }

    public static Location[] transferToLocation(@Nonnull Block... blocks) {
        Location[] locations = new Location[blocks.length];
        for (int i = 0; i < locations.length; i++) {
            locations[i] = blocks[i].getLocation();
        }
        return locations;
    }

    public static Location[] transferToLocation(@Nonnull List<Block> blockList) {
        Location[] locations = new Location[blockList.size()];
        for (int i = 0; i < locations.length; i++) {
            locations[i] = blockList.get(i).getLocation();
        }
        return locations;
    }

    @Nullable
    public static Location parseLocationInItem(@Nullable ItemStack item) {
        if (ItemStackUtil.isItemNull(item)) {
            return null;
        }
        ItemMeta itemMeta = item.getItemMeta();
        return LocationUtil.parseLocationInItem(itemMeta);
    }

    @Nullable
    public static Location parseLocationInItem(@Nonnull ItemMeta itemMeta) {
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        String locationString = persistentDataContainer.get(KEY, PersistentDataType.STRING);
        if (locationString != null) {
            return LocationUtil.stringToLocation(locationString);
        }
        return null;
    }

    public static boolean saveLocationToItem(@Nullable ItemStack item, @Nonnull Location location) {
        if (ItemStackUtil.isItemNull(item)) {
            return false;
        }
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            return false;
        }
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        persistentDataContainer.set(KEY, PersistentDataType.STRING, LocationUtil.locationToString(location));
        item.setItemMeta(itemMeta);
        return true;
    }

    // TODO
    public static boolean updateLocationItem(@Nullable ItemStack item) {
        if (ItemStackUtil.isItemNull(item)) {
            return false;
        }
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            return false;
        }
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        String locationString = persistentDataContainer.get(KEY, PersistentDataType.STRING);
        if (locationString != null) {
            Location location = LocationUtil.stringToLocation(locationString);
            if (location != null) {
                List<String> loreList = new ArrayList<>();
                loreList.add(TextUtil.colorRandomString(location.getWorld().getName()));
                itemMeta.setLore(loreList);
                item.setItemMeta(itemMeta);
                return true;
            }
        }
        return false;
    }

    @Nonnull
    public static Location getCenterLocation(@Nonnull Block block) {
        Location location = block.getLocation();
        location.setX(location.getBlockX() + 0.5);
        location.setY(location.getBlockY() + 0.5);
        location.setZ(location.getBlockZ() + 0.5);
        return location;
    }

    @Nullable
    public static Location stringToLocation(@Nonnull String locationString) {
        World world = null;
        Double x = null;
        Double y = null;
        Double z = null;
        for (String value : locationString.split(";")) {
            if (value.length() > 2) {
                switch (value.charAt(0)) {
                    case 'w' -> world = Bukkit.getServer().getWorld(value.substring(2));
                    case 'x' -> x = Double.parseDouble(value.substring(2));
                    case 'y' -> y = Double.parseDouble(value.substring(2));
                    case 'z' -> z = Double.parseDouble(value.substring(2));
                }
            }
        }
        if (world != null && x != null && y != null && z != null) {
            return new Location(world, x, y, z);
        }
        return null;
    }

    @Nonnull
    public static String locationToString(@Nonnull Location location) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("w:");
        stringBuilder.append(location.getWorld().getName());
        stringBuilder.append(";");

        stringBuilder.append("x:");
        stringBuilder.append(location.getX());
        stringBuilder.append(";");

        stringBuilder.append("y:");
        stringBuilder.append(location.getY());
        stringBuilder.append(";");

        stringBuilder.append("z:");
        stringBuilder.append(location.getZ());
        stringBuilder.append(";");

        return stringBuilder.toString();
    }
}
