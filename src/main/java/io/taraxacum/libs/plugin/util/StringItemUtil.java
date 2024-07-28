package io.taraxacum.libs.plugin.util;

import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.libs.plugin.dto.ItemWrapper;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * We should make suer that
 * one item will have "item" key and "amount" key in the same time
 * or one item will not have only one of them.
 *
 * @author Final_ROOT
 * @since 1.0
 */
public class StringItemUtil {
    public static final NamespacedKey ITEM_KEY = new NamespacedKey(FinalTechChanged.getPlugin(FinalTechChanged.class), "item");
    public static final NamespacedKey AMOUNT_KEY = new NamespacedKey(FinalTechChanged.getPlugin(FinalTechChanged.class), "amount");

    /**
     * get and push item to the inventory
     *
     * @param slots ths slot of the inventory that the item will push it's stored items
     * @return the amount it pushed in real
     */
    public static int pullItemFromCard(@Nonnull ItemStack cardItem, @Nonnull Inventory inventory, @Nonnull int[] slots) {
        if (!cardItem.hasItemMeta() || (cardItem.getAmount() != 1)) {
            return 0;
        }
        ItemMeta itemMeta = cardItem.getItemMeta();
        int count = StringItemUtil.pullItemFromCard(itemMeta, inventory, slots);
        if (count > 0) {
            cardItem.setItemMeta(itemMeta);
        }
        return count;
    }

    /**
     * Set itemMete after using this to save data
     */
    public static int pullItemFromCard(@Nonnull ItemMeta cardItemMeta, @Nonnull Inventory inventory, @Nonnull int[] slots) {
        PersistentDataContainer persistentDataContainer = cardItemMeta.getPersistentDataContainer();
        if (persistentDataContainer.has(ITEM_KEY, PersistentDataType.STRING)) {
            String itemString = persistentDataContainer.get(ITEM_KEY, PersistentDataType.STRING);
            ItemWrapper stringItem = new ItemWrapper(ItemStackUtil.stringToItemStack(itemString));
            return StringItemUtil.pullItemFromCard(cardItemMeta, stringItem, inventory, slots);
        }
        return 0;
    }

    public static int pullItemFromCard(@Nonnull ItemMeta cardItemMeta, @Nonnull ItemWrapper stringItem, @Nonnull Inventory inventory, @Nonnull int[] slots) {
        PersistentDataContainer persistentDataContainer = cardItemMeta.getPersistentDataContainer();
        String amount = persistentDataContainer.get(AMOUNT_KEY, PersistentDataType.STRING);
        int maxStackSize = stringItem.getItemStack().getMaxStackSize();
        int validAmount = StringNumberUtil.compare(amount, String.valueOf(maxStackSize * slots.length)) >= 1 ? maxStackSize * slots.length : Integer.parseInt(amount);
        if (validAmount == 0) {
            persistentDataContainer.remove(ITEM_KEY);
            persistentDataContainer.remove(AMOUNT_KEY);
            return 0;
        }

        amount = StringNumberUtil.sub(amount, String.valueOf(validAmount));
        ItemStack targetItem;
        int count = 0;
        for (int slot : slots) {
            targetItem = inventory.getItem(slot);
            if (ItemStackUtil.isItemNull(targetItem)) {
                int itemAmount = Math.min(validAmount, maxStackSize);
                if (itemAmount > 0) {
                    inventory.setItem(slot, ItemStackUtil.cloneItem(stringItem.getItemStack(), itemAmount));
                    count += validAmount;
                    validAmount -= itemAmount;
                    if (validAmount == 0) {
                        break;
                    }
                }
            }
        }

        amount = StringNumberUtil.add(amount, String.valueOf(validAmount));
        if (StringNumberUtil.ZERO.equals(amount)) {
            persistentDataContainer.remove(ITEM_KEY);
            persistentDataContainer.remove(AMOUNT_KEY);
        } else {
            persistentDataContainer.set(AMOUNT_KEY, PersistentDataType.STRING, amount);
        }
        return count;
    }

    public static int storageItemToCard(@Nonnull ItemStack cardItem, @Nonnull Inventory inventory, @Nonnull int[] slots) {
        if (!cardItem.hasItemMeta()) {
            return 0;
        }
        ItemMeta itemMeta = cardItem.getItemMeta();
        int count = StringItemUtil.storageItemToCard(itemMeta, cardItem.getAmount(), inventory, slots);
        if (count > 0) {
            cardItem.setItemMeta(itemMeta);
        }
        return count;
    }

    /**
     * @param size The amount of the card item
     */
    public static int storageItemToCard(@Nonnull ItemMeta itemMeta, int size, @Nonnull Inventory inventory, @Nonnull int[] slots) {
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        ItemWrapper stringItem = null;
        if (persistentDataContainer.has(ITEM_KEY, PersistentDataType.STRING)) {
            String itemString = persistentDataContainer.get(ITEM_KEY, PersistentDataType.STRING);
            ItemStack item = ItemStackUtil.stringToItemStack(itemString);
            if (item != null) {
                stringItem = new ItemWrapper(item);
            }
        }
        return StringItemUtil.storageItemToCard(itemMeta, stringItem, size, inventory, slots);
    }

    public static int storageItemToCard(@Nonnull ItemMeta cardItemMeta, @Nullable ItemWrapper stringItem, int size, @Nonnull Inventory inventory, @Nonnull int[] slots) {
        PersistentDataContainer persistentDataContainer = cardItemMeta.getPersistentDataContainer();
        String amount = StringNumberUtil.ZERO;
        if (persistentDataContainer.has(AMOUNT_KEY, PersistentDataType.STRING)) {
            amount = persistentDataContainer.get(AMOUNT_KEY, PersistentDataType.STRING);
        } else {
            stringItem = null;
        }
        int totalAmount = 0;
        List<ItemStack> sourceItemList = new ArrayList<>(slots.length);
        ItemStack sourceItem;
        for (int slot : slots) {
            sourceItem = inventory.getItem(slot);
            if (ItemStackUtil.isItemNull(sourceItem)) {
                continue;
            }
            if (stringItem == null || ItemStackUtil.isItemNull(stringItem.getItemStack())) {
                stringItem = new ItemWrapper(ItemStackUtil.cloneItem(sourceItem, 1));
                totalAmount += sourceItem.getAmount();
                sourceItemList.add(sourceItem);
            } else if (ItemStackUtil.isItemSimilar(stringItem, sourceItem)) {
                totalAmount += sourceItem.getAmount();
                sourceItemList.add(sourceItem);
            }
        }

        totalAmount = totalAmount - totalAmount % size;
        int count = totalAmount / size;
        for (ItemStack item : sourceItemList) {
            if (item.getAmount() < totalAmount) {
                totalAmount -= item.getAmount();
                item.setAmount(0);
            } else {
                item.setAmount(item.getAmount() - totalAmount);
                break;
            }
        }

        if (count > 0) {
            if (StringNumberUtil.ZERO.equals(amount)) {
                persistentDataContainer.set(AMOUNT_KEY, PersistentDataType.STRING, String.valueOf(count));
                persistentDataContainer.set(ITEM_KEY, PersistentDataType.STRING, ItemStackUtil.itemStackToString(stringItem.getItemStack()));
            } else {
                persistentDataContainer.set(AMOUNT_KEY, PersistentDataType.STRING, StringNumberUtil.add(amount, String.valueOf(count)));
            }
        } else {
            if (StringNumberUtil.ZERO.equals(amount)) {
                persistentDataContainer.remove(ITEM_KEY);
                persistentDataContainer.remove(AMOUNT_KEY);
            }
        }

        return count;
    }

    @Nullable
    public static ItemStack parseItemInCard(@Nonnull ItemStack cardItem) {
        if (!cardItem.hasItemMeta()) {
            return null;
        }
        ItemMeta itemMeta = cardItem.getItemMeta();
        return StringItemUtil.parseItemInCard(itemMeta);
    }

    @Nullable
    public static ItemStack parseItemInCard(@Nonnull ItemMeta cardItemMeta) {
        PersistentDataContainer persistentDataContainer = cardItemMeta.getPersistentDataContainer();
        String itemString = persistentDataContainer.get(ITEM_KEY, PersistentDataType.STRING);
        if (itemString != null) {
            ItemStack stringItem = ItemStackUtil.stringToItemStack(itemString);
            if (stringItem != null) {
                stringItem.setAmount(1);
                return stringItem;
            }
        }
        return null;
    }

    @Nonnull
    public static String parseAmountInCard(@Nonnull ItemStack cardItem) {
        if (!cardItem.hasItemMeta()) {
            return StringNumberUtil.ZERO;
        }
        ItemMeta itemMeta = cardItem.getItemMeta();
        return StringItemUtil.parseAmountInCard(itemMeta);
    }

    @Nonnull
    public static String parseAmountInCard(@Nonnull ItemMeta cardItemMeta) {
        PersistentDataContainer persistentDataContainer = cardItemMeta.getPersistentDataContainer();
        if (persistentDataContainer.has(AMOUNT_KEY, PersistentDataType.STRING)) {
            return persistentDataContainer.get(AMOUNT_KEY, PersistentDataType.STRING);
        }
        return StringNumberUtil.ZERO;
    }

    public static void setItemInCard(@Nonnull ItemStack cardItem, @Nonnull ItemStack stringItem) {
        if (!cardItem.hasItemMeta()) {
            return;
        }
        ItemMeta itemMeta = cardItem.getItemMeta();
        StringItemUtil.setItemInCard(itemMeta, stringItem);
        cardItem.setItemMeta(itemMeta);
    }

    public static void setItemInCard(@Nonnull ItemMeta itemMeta, @Nonnull ItemStack stringItem) {
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        if (persistentDataContainer.has(AMOUNT_KEY, PersistentDataType.STRING)) {
            persistentDataContainer.set(ITEM_KEY, PersistentDataType.STRING, ItemStackUtil.itemStackToString(stringItem));
        }
    }

    public static void setItemInCard(@Nonnull ItemStack cardItem, @Nonnull ItemStack stringItem, int amount) {
        StringItemUtil.setItemInCard(cardItem, stringItem, String.valueOf(amount));
    }

    public static void setItemInCard(@Nonnull ItemStack cardItem, @Nonnull ItemStack stringItem, @Nonnull String amount) {
        if (!cardItem.hasItemMeta()) {
            return;
        }
        ItemMeta itemMeta = cardItem.getItemMeta();
        StringItemUtil.setItemInCard(itemMeta, stringItem, amount);
        cardItem.setItemMeta(itemMeta);
    }

    public static void setItemInCard(@Nonnull ItemMeta itemMeta, @Nonnull ItemStack stringItem, @Nonnull String amount) {
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        if (StringNumberUtil.compare(amount, StringNumberUtil.ZERO) == 1) {
            persistentDataContainer.set(ITEM_KEY, PersistentDataType.STRING, ItemStackUtil.itemStackToString(stringItem));
            persistentDataContainer.set(AMOUNT_KEY, PersistentDataType.STRING, amount);
        } else {
            persistentDataContainer.remove(ITEM_KEY);
            persistentDataContainer.remove(AMOUNT_KEY);
        }
    }

    public static void setAmountInCard(@Nonnull ItemStack cardItem, @Nonnull String amount) {
        if (!cardItem.hasItemMeta()) {
            return;
        }
        ItemMeta cardItemMeta = cardItem.getItemMeta();
        PersistentDataContainer persistentDataContainer = cardItemMeta.getPersistentDataContainer();
        if (persistentDataContainer.has(ITEM_KEY, PersistentDataType.STRING) && persistentDataContainer.has(AMOUNT_KEY, PersistentDataType.STRING)) {
            if (StringNumberUtil.compare(amount, StringNumberUtil.ZERO) == 1) {
                persistentDataContainer.set(AMOUNT_KEY, PersistentDataType.STRING, amount);
            } else {
                persistentDataContainer.remove(ITEM_KEY);
                persistentDataContainer.remove(AMOUNT_KEY);
            }
        }
        cardItem.setItemMeta(cardItemMeta);
    }
}
