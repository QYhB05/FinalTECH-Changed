package io.taraxacum.libs.plugin.dto;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
 
public class KeyValueStringHelper {
    @Nonnull
    protected final Collection<String> keySet;
    @Nonnull
    protected final Collection<String> valueSet;
    @Nonnull
    private final LinkedHashMap<String, String> map;

    public KeyValueStringHelper(@Nonnull Collection<String> keySet, @Nonnull Collection<String> valueSet) {
        this.keySet = new HashSet<>(keySet.size());
        this.keySet.addAll(keySet);
        this.valueSet = new HashSet<>(valueSet.size());
        this.valueSet.addAll(valueSet);
        this.map = new LinkedHashMap<>();
    }

    public KeyValueStringHelper(@Nonnull String[] keys, @Nonnull String[] values) {
        this.keySet = new HashSet<>(keys.length);
        this.keySet.addAll(Arrays.asList(keys));
        this.valueSet = new HashSet<>(values.length);
        this.valueSet.addAll(Arrays.asList(values));
        this.map = new LinkedHashMap<>();
    }

    public boolean validKey(@Nullable String key) {
        return this.keySet.contains(key);
    }

    public boolean validValue(@Nullable String value) {
        return this.valueSet.contains(value);
    }

    public void putEntry(@Nullable String key, @Nullable String value) {
        if (this.validKey(key)) {
            if (value == null) {
                this.deleteEntry(key);
            } else if (this.validValue(value)) {
                this.map.put(key, value);
            }
        }
    }

    public void deleteEntry(@Nullable String key) {
        this.map.remove(key);
    }

    @Nonnull
    public List<String> getAllMatchKey(@Nullable String value) {
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, String> entry : this.map.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(value)) {
                list.add(entry.getKey());
            }
        }
        return list;
    }

    @Nonnull
    public List<String> getAllMatchKey(@Nonnull String... values) {
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, String> entry : this.map.entrySet()) {
            if (entry.getValue() != null) {
                for (String value : values) {
                    if (entry.getValue().equals(value)) {
                        list.add(entry.getKey());
                        break;
                    }
                }
            }
        }
        return list;
    }

    @Nullable
    public String getValue(@Nonnull String key) {
        return this.map.get(key);
    }

    public int getKeyIndex(@Nonnull String key) {
        Object[] keys = this.map.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            if (keys[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }

    @Nonnull
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : this.map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            stringBuilder.append(key).append(":").append(value);
            stringBuilder.append("-");
        }
        return stringBuilder.toString();
    }

    @Nonnull
    public KeyValueStringHelper parseString(@Nonnull String string) {
        KeyValueStringHelper keyValueStringHelper = new KeyValueStringHelper(this.keySet, this.valueSet);
        for (String entry : string.split("-")) {
            String[] split = entry.split(":");
            if (split.length == 2 && this.keySet.contains(split[0]) && this.valueSet.contains(split[1])) {
                keyValueStringHelper.putEntry(split[0], split[1]);
            }
        }
        return keyValueStringHelper;
    }
}
