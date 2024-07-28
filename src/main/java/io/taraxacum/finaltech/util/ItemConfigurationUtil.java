package io.taraxacum.finaltech.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.core.helper.IgnorePermission;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.libs.plugin.dto.ConfigFileManager;
import io.taraxacum.libs.slimefun.dto.LocationInfo;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.*;

public class ItemConfigurationUtil {
    private static final NamespacedKey KEY_CONFIG = new NamespacedKey(FinalTechChanged.getInstance(), "_FINALTECH_CONFIGURATION");
    private static final NamespacedKey KEY_ID = new NamespacedKey(FinalTechChanged.getInstance(), "_FINALTECH_BLOCK_STORAGE_ID");
    private static final Gson GSON = new GsonBuilder().enableComplexMapKeySerialization().create();
    private static final Type TYPE = new TypeToken<Map<String, String>>() {
    }.getType();
    private static boolean init = false;
    private static Set<String> allowedItemId = new HashSet<>();
    // key: itemId
    // value: group
    private static Map<String, String> itemGroupMap = new HashMap<>();
    // keu: group
    // value: set of itemId
    private static Map<String, Set<String>> groupItemMap = new HashMap<>();
    private static Map<String, Set<String>> itemForbidKeyMap = new HashMap<>();

    private static void init() {
        Set<String> allowedItemId = new HashSet<>();
        Map<String, String> itemGroupMap = new HashMap<>();
        Map<String, Set<String>> itemForbidKeyMap = new HashMap<>();
        Map<String, Set<String>> groupItemMap = new HashMap<>();

        ConfigFileManager itemManager = FinalTechChanged.getItemManager();
        List<String> groupList = itemManager.getStringList(FinalTechItems.MACHINE_CONFIGURATOR.getId(), "item-config");

        for (String group : groupList) {
            List<String> itemIdList = itemManager.getStringList(FinalTechItems.MACHINE_CONFIGURATOR.getId(), "item-config", group);

            for (String itemId : itemIdList) {
                allowedItemId.add(itemId);
                itemGroupMap.put(itemId, group);
                if (groupItemMap.containsKey(group)) {
                    Set<String> itemIdSet = groupItemMap.get(group);
                    itemIdSet.add(itemId);
                } else {
                    Set<String> itemIdSet = new HashSet<>();
                    itemIdSet.add(itemId);
                    groupItemMap.put(group, itemIdSet);
                }

                List<String> keyList = itemManager.getStringList(FinalTechItems.MACHINE_CONFIGURATOR.getId(), "item-config", group, itemId);
                Set<String> keySet = new HashSet<>(keyList);

                keySet.add(ConstantTableUtil.CONFIG_ID);
                keySet.add(ConstantTableUtil.CONFIG_CHARGE);
                keySet.add(ConstantTableUtil.CONFIG_UUID);
                keySet.add(IgnorePermission.KEY);

                itemForbidKeyMap.put(itemId, keySet);
            }
        }

        ItemConfigurationUtil.allowedItemId = allowedItemId;
        ItemConfigurationUtil.itemGroupMap = itemGroupMap;
        ItemConfigurationUtil.groupItemMap = groupItemMap;
        ItemConfigurationUtil.itemForbidKeyMap = itemForbidKeyMap;

        init = true;
    }

    public static Set<String> getAllowedItemId() {
        if (!init) {
            init();
        }

        return ItemConfigurationUtil.allowedItemId;
    }

    public static Map<String, Set<String>> getGroupItemMap() {
        if (!init) {
            init();
        }

        return ItemConfigurationUtil.groupItemMap;
    }

    public static boolean isAllowItemId(@Nonnull String itemId) {
        if (!init) {
            init();
        }

        return ItemConfigurationUtil.allowedItemId.contains(itemId);
    }

    public static boolean isSameGroup(@Nullable String itemId1, @Nullable String itemId2) {
        if (!init) {
            init();
        }

        String group1 = ItemConfigurationUtil.itemGroupMap.get(itemId1);
        String group2 = ItemConfigurationUtil.itemGroupMap.get(itemId2);
        return group1 != null && group1.equals(group2);
    }

    public static Map<String, String> filterByItem(@Nonnull String itemId, @Nonnull Map<String, String> map) {
        if (!init) {
            init();
        }

        Set<String> forbidKeySet = itemForbidKeyMap.get(itemId);

        if (forbidKeySet == null) {
            return new HashMap<>();
        }

        Map<String, String> resultMap = new HashMap<>();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!forbidKeySet.contains(entry.getKey())) {
                resultMap.put(entry.getKey(), entry.getValue());
            }
        }

        return resultMap;
    }
    private static String getValue(Location l, String key){
        return BlockStorage.getLocationInfo(l, key);
    }

    public static boolean saveConfigToItem(@Nonnull ItemStack itemStack, @Nonnull Location location) {
        String itemId = getValue(location, ConstantTableUtil.CONFIG_ID);
        if (!ItemConfigurationUtil.allowedItemId.contains(itemId)) {
            return false;
        }

        Map<String, String> configMap = new HashMap<>();
        for (String key : BlockStorage.getLocationInfo(location).getKeys()) {
            configMap.put(key, getValue(location, key));
        }

        configMap = ItemConfigurationUtil.filterByItem(itemId, configMap);

        ItemConfigurationUtil.setConfigurationToItem(itemStack, configMap);

        ItemConfigurationUtil.setIdToItem(itemStack, itemId);

        return true;
    }

    public static boolean saveConfigToItem(@Nonnull ItemStack itemStack, @Nonnull LocationInfo locationInfo) {
        if (!ItemConfigurationUtil.allowedItemId.contains(locationInfo.getId())) {
            return false;
        }

        Map<String, String> configMap = new HashMap<>();
        for (String key : locationInfo.getConfig().getKeys()) {
            configMap.put(key, locationInfo.getConfig().getString(key));
        }

        configMap = ItemConfigurationUtil.filterByItem(locationInfo.getId(), configMap);

        ItemConfigurationUtil.setConfigurationToItem(itemStack, configMap);

        ItemConfigurationUtil.setIdToItem(itemStack, locationInfo.getId());

        return true;
    }

    public static boolean loadConfigFromItem(@Nonnull ItemStack itemStack, @Nonnull Location location) {
        LocationInfo locationInfo = LocationInfo.get(location);

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return false;
        }
        String itemId = ItemConfigurationUtil.getIdFromItem(itemMeta);

        if (locationInfo == null || itemId == null || !ItemConfigurationUtil.isSameGroup(itemId, locationInfo.getId())) {
            return false;
        }

        Map<String, String> configMap = ItemConfigurationUtil.getConfigurationFromItem(itemMeta);
        configMap = ItemConfigurationUtil.filterByItem(itemId, configMap);

        for (Map.Entry<String, String> entry : configMap.entrySet()) {
            if (BlockStorage.getLocationInfo(location, entry.getKey()) != null) {
                BlockStorage.addBlockInfo(location, entry.getKey(), entry.getValue());
            }
        }

        return true;
    }

    public static boolean loadConfigFromItem(@Nonnull ItemStack itemStack, @Nonnull LocationInfo locationInfo) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        String itemId = ItemConfigurationUtil.getIdFromItem(itemMeta);

        if (!ItemConfigurationUtil.isSameGroup(itemId, locationInfo.getId())) {
            return false;
        }

        Map<String, String> configMap = ItemConfigurationUtil.getConfigurationFromItem(itemMeta);
        configMap = ItemConfigurationUtil.filterByItem(itemId, configMap);

        for (Map.Entry<String, String> entry : configMap.entrySet()) {
            if (locationInfo.getConfig().contains(entry.getKey())) {
                BlockStorage.addBlockInfo(locationInfo.getLocation(), entry.getKey(), entry.getValue());
            }
        }

        return true;
    }

    @Nullable
    public static String getIdFromItem(@Nonnull ItemStack itemStack) {
        if (!itemStack.hasItemMeta()) {
            return null;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        return ItemConfigurationUtil.getIdFromItem(itemMeta);
    }

    @Nullable
    public static String getIdFromItem(@Nonnull ItemMeta itemMeta) {
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        if (persistentDataContainer.has(KEY_ID, PersistentDataType.STRING)) {
            return persistentDataContainer.get(KEY_ID, PersistentDataType.STRING);
        }
        return null;
    }

    public static void setIdToItem(@Nonnull ItemStack itemStack, @Nonnull String id) {
        if (!itemStack.hasItemMeta()) {
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        ItemConfigurationUtil.setIdToItem(itemMeta, id);
        itemStack.setItemMeta(itemMeta);
    }

    public static void setIdToItem(@Nonnull ItemMeta itemMeta, @Nonnull String id) {
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        persistentDataContainer.set(KEY_ID, PersistentDataType.STRING, id);
    }

    @Nonnull
    public static Map<String, String> getConfigurationFromItem(@Nonnull ItemStack itemStack) {
        if (!itemStack.hasItemMeta()) {
            return new HashMap<>();
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        return ItemConfigurationUtil.getConfigurationFromItem(itemMeta);
    }

    @Nonnull
    public static Map<String, String> getConfigurationFromItem(@Nonnull ItemMeta itemMeta) {
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        if (persistentDataContainer.has(KEY_CONFIG, PersistentDataType.STRING)) {
            String s = persistentDataContainer.get(KEY_CONFIG, PersistentDataType.STRING);
            return GSON.fromJson(s, TYPE);
        }
        return new HashMap<>();
    }

    public static void setConfigurationToItem(@Nonnull ItemStack itemStack, @Nonnull Map<String, String> map) {
        if (!itemStack.hasItemMeta()) {
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        ItemConfigurationUtil.setConfigurationToItem(itemMeta, map);
        itemStack.setItemMeta(itemMeta);
    }

    public static void setConfigurationToItem(@Nonnull ItemMeta itemMeta, @Nonnull Map<String, String> map) {
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        persistentDataContainer.set(KEY_CONFIG, PersistentDataType.STRING, GSON.toJson(map));
    }
}
