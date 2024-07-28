package io.taraxacum.finaltech.util;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.taraxacum.finaltech.core.helper.IgnorePermission;
import io.taraxacum.libs.plugin.util.PlayerUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.UUID;

public class PermissionUtil {
    public static boolean checkOfflinePermission(@Nonnull ItemStack itemStack, @Nonnull Location... targetLocations) {
        if (!itemStack.hasItemMeta()) {
            return false;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        String uuid = PlayerUtil.parseIdInItem(itemMeta);
        if (uuid == null) {
            return false;
        }
        Boolean ignorePermission = PlayerUtil.parseIgnorePermissionInItem(itemMeta);
        Player player = Bukkit.getPlayer(UUID.fromString(uuid));
        if (player != null && player.isOnline()) {
            for (Location targetLocation : targetLocations) {
                if (!PermissionUtil.checkPermission(player, targetLocation, Interaction.INTERACT_BLOCK)) {
                    if (ignorePermission) {
                        PlayerUtil.updateIgnorePermissionInItem(itemMeta, false);
                        itemStack.setItemMeta(itemMeta);
                    }
                    return false;
                }
            }
            if (!ignorePermission) {
                PlayerUtil.updateIgnorePermissionInItem(itemMeta, true);
                itemStack.setItemMeta(itemMeta);
            }
            return true;
        } else {
            return ignorePermission;
        }
    }

    public static boolean checkOfflinePermission(@Nonnull Location sourceLocation, @Nonnull Config config, @Nonnull Location... targetLocations) {
        String uuid = config.getString(ConstantTableUtil.CONFIG_UUID);
        if (uuid == null) {
            return false;
        }
        Player player = Bukkit.getPlayer(UUID.fromString(uuid));
        if (player != null && player.isOnline()) {
            for (Location targetLocation : targetLocations) {
                if (!PermissionUtil.checkPermission(player, targetLocation, Interaction.INTERACT_BLOCK)) {
                    IgnorePermission.HELPER.setOrClearValue(sourceLocation, IgnorePermission.VALUE_FALSE);
                    return false;
                }
            }
            IgnorePermission.HELPER.setOrClearValue(sourceLocation, IgnorePermission.VALUE_TRUE);
            return true;
        } else return IgnorePermission.VALUE_TRUE.equals(IgnorePermission.HELPER.getOrDefaultValue(config));
    }

    public static boolean checkOfflinePermission(@Nonnull Location sourceLocation, @Nonnull Location... targetLocations) {
        return PermissionUtil.checkOfflinePermission(sourceLocation, BlockStorage.getLocationInfo(sourceLocation), targetLocations);
    }

    public static boolean checkPermission(@Nonnull String uuid, @Nonnull Block block, @Nonnull Interaction... interactions) {
        Player player = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getPlayer();
        if (player == null || player.isBanned()) {
            return false;
        }
        return PermissionUtil.checkPermission(player, block.getLocation(), interactions);
    }

    public static boolean checkPermission(@Nonnull String uuid, @Nonnull Entity entity, @Nonnull Interaction... interactions) {
        Player player = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getPlayer();
        if (player == null || player.isBanned()) {
            return false;
        }
        return PermissionUtil.checkPermission(player, entity.getLocation(), interactions);
    }

    public static boolean checkPermission(@Nonnull String uuid, @Nonnull Location location, @Nonnull Interaction... interactions) {
        Player player = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getPlayer();
        if (player == null || player.isBanned()) {
            return false;
        }
        return PermissionUtil.checkPermission(player, location, interactions);
    }

    public static boolean checkPermission(@Nonnull Player player, @Nonnull Location location, @Nonnull Interaction... interactions) {
        for (Interaction interaction : interactions) {
            if (!Slimefun.getProtectionManager().hasPermission(player, location, interaction)) {
                return false;
            }
        }
        return true;
    }
}
