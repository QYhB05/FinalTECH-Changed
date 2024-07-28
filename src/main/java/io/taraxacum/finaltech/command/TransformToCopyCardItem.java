package io.taraxacum.finaltech.core.command;

import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.item.unusable.CopyCard;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * A {@link Command} that will transfer item in player's hand to a {@link CopyCard}.
 *
 * @author Final_ROOT
 * @since 2.0
 */
public class TransformToCopyCardItem implements CommandExecutor {
    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        if (commandSender instanceof Player player) {
            ItemStack item = player.getItemInHand();
            if (ItemStackUtil.isItemNull(item) || !FinalTechItems.COPY_CARD.isTargetItem(item)) {
                return false;
            }
            String amount = strings[0];
            if (!StringNumberUtil.isNumber(amount)) {
                FinalTechChanged.logger().info("It seems that u didn't input a valid number");
                return false;
            }
            ItemStack copyCardItem = FinalTechItems.COPY_CARD.getValidItem(item, amount);
            player.setItemInHand(copyCardItem);
            return true;
        } else {
            FinalTechChanged.logger().info("Not support for console");
        }
        return false;
    }
}
