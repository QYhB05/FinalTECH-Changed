//package io.taraxacum.finaltech.api.factory;
//
//import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
//import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
//import io.taraxacum.finaltech.FinalTechChanged;
//import io.taraxacum.finaltech.util.ItemStackUtil;
//import org.bukkit.inventory.ItemStack;
//
//import javax.annotation.Nonnull;
//import java.util.*;
//
//public class ItemCodeTable {
//    private Map<String, String> itemCodeMap = new HashMap<>(Slimefun.getRegistry().getAllSlimefunItems().size());
//    private Random random = new Random(FinalTechChanged.getConfigManager().getOrDefault((long)(new Random().nextDouble() * Long.MAX_VALUE), "random", "item-code", "value"));
//
//    private static volatile ItemCodeTable instance;
//
//    private ItemCodeTable() {
//
//    }
//
//    public static void init() {
//
//    }
//
//    public String calCode(@Nonnull ItemStack itemStack) {
//        SlimefunItem slimefunItem = SlimefunItem.getByItem(itemStack);
//        if (slimefunItem != null && ItemStackUtil.isItemSimilar(itemStack, slimefunItem.getItem())) {
//            return this.getOrCalCode(slimefunItem);
//        }
//
//    }
//
//    public String getOrCalCode(@Nonnull String id) {
//        if (this.itemCodeMap.containsKey(id)) {
//            return this.itemCodeMap.get(id);
//        }
//        SlimefunItem slimefunItem = SlimefunItem.getById(id);
//        if (slimefunItem != null) {
//            return this.getOrCalCode(slimefunItem);
//        }
//    }
//
//    public String getOrCalCode(@Nonnull SlimefunItem slimefunItem) {
//        if (SlimefunItem.getById(slimefunItem.getId()) != null && ItemStackUtil.isItemSimilar(slimefunItem.getItem(), SlimefunItem.getById(slimefunItem.getId()).getItem())) {
//            if (this.itemCodeMap.containsKey(slimefunItem.getId())) {
//                return this.itemCodeMap.get(slimefunItem.getId());
//            } else {
//
//            }
//        } else {
//            return this.calCode(slimefunItem.getItem());
//        }
//    }
//
//    @Nonnull
//    public static ItemCodeTable getInstance() {
//        if (instance == null) {
//            synchronized (ItemCodeTable.class) {
//                if (instance == null) {
//                    instance = new ItemCodeTable();
//                }
//            }
//        }
//        return instance;
//    }
//}
