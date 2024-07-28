package io.taraxacum.finaltech.core.command;

import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.item.unusable.StorageCard;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * A {@link Command} that will transfer item in player's hand to a {@link StorageCard}.
 *
 * @author Final_ROOT
 * @since 2.0
 */
public class TransformToStorageItem implements CommandExecutor {
    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        if (strings.length != 1) {
            return false;
        }
        if (!(commandSender instanceof Player player)) {
            FinalTechChanged.logger().info("Not support for console");
            return false;
        }
        ItemStack item = player.getItemInHand();
        if (ItemStackUtil.isItemNull(item) || !FinalTechItems.STORAGE_CARD.isTargetItem(item)) {
            return false;
        }
        String amount = strings[0];
        if (!StringNumberUtil.isNumber(amount)) {
            return false;
        }
        ItemStack storageCardItem = FinalTechItems.STORAGE_CARD.getValidItem(item, amount);
        player.setItemInHand(storageCardItem);
        return true;
    }
}
