package io.taraxacum.libs.plugin.dto;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class KeyValueStringOrderHelper extends KeyValueStringHelper {
    protected final LinkedHashMap<Integer, KeyValue> map;
    @Nonnull
    private final String nullMark;

    public KeyValueStringOrderHelper(@Nonnull Collection<String> keySet, @Nonnull Collection<String> valueSet) {
        super(keySet, valueSet);
        this.map = new LinkedHashMap<>();
        this.nullMark = "";
    }

    public KeyValueStringOrderHelper(@Nonnull String[] keys, @Nonnull String[] values) {
        super(keys, values);
        this.map = new LinkedHashMap<>();
        this.nullMark = "";
    }

    public KeyValueStringOrderHelper(@Nonnull Collection<String> keySet, @Nonnull Collection<String> valueSet, @Nonnull String nullMark) {
        super(keySet, valueSet);
        this.map = new LinkedHashMap<>();
        this.nullMark = nullMark;
    }

    public KeyValueStringOrderHelper(@Nonnull String[] keys, @Nonnull String[] values, @Nonnull String nullMark) {
        super(keys, values);
        this.map = new LinkedHashMap<>();
        this.nullMark = nullMark;
    }

    @Override
    public void putEntry(@Nullable String key, @Nullable String value) {
        if (this.validKey(key)) {
            if (value == null) {
                this.deleteEntry(key);
            } else {
                for (Map.Entry<Integer, KeyValue> entry : this.map.entrySet()) {
                    if (entry.getValue().key.equals(key)) {
                        entry.getValue().value = value;
                        return;
                    }
                }
                for (int i = 0; i < this.map.size(); i++) {
                    if (!this.map.containsKey(i) || this.nullMark.equals(this.map.get(i).value)) {
                        this.map.put(i, new KeyValue(key, value));
                        return;
                    }
                }
                this.map.put(this.map.size(), new KeyValue(key, value));
            }
        }
    }

    private void putEntry(int order, @Nullable String key, @Nullable String value) {
        if (this.validKey(key)) {
            if (value == null) {
                this.deleteEntry(key);
            } else {
                this.map.put(order, new KeyValue(key, value));
            }
        }
    }

    @Override
    public void deleteEntry(@Nullable String key) {
        Iterator<Map.Entry<Integer, KeyValue>> iterator = this.map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, KeyValue> entry = iterator.next();
            if (entry.getValue().key.equals(key)) {
                iterator.remove();
                break;
            }
        }
    }

    @Nonnull
    @Override
    public List<String> getAllMatchKey(@Nullable String value) {
        List<Map.Entry<Integer, KeyValue>> keyOrderList = new ArrayList<>();
        for (Map.Entry<Integer, KeyValue> entry : this.map.entrySet()) {
            if (entry.getValue().value.equals(value)) {
                keyOrderList.add(entry);
            }
        }
        keyOrderList.sort(Comparator.comparingInt(Map.Entry::getKey));
        List<String> list = new ArrayList<>();
        for (Map.Entry<Integer, KeyValue> integerKeyValueEntry : keyOrderList) {
            list.add(integerKeyValueEntry.getValue().key);
        }
        return list;
    }

    @Nonnull
    @Override
    public List<String> getAllMatchKey(@Nonnull String... values) {
        List<Map.Entry<Integer, KeyValue>> keyOrderList = new ArrayList<>();
        for (Map.Entry<Integer, KeyValue> entry : this.map.entrySet()) {
            for (String value : values) {
                if (entry.getValue().value.equals(value)) {
                    keyOrderList.add(entry);
                    break;
                }
            }
        }
        keyOrderList.sort(Comparator.comparingInt(Map.Entry::getKey));
        List<String> list = new ArrayList<>();
        for (Map.Entry<Integer, KeyValue> integerKeyValueEntry : keyOrderList) {
            list.add(integerKeyValueEntry.getValue().key);
        }
        return list;
    }

    @Nullable
    @Override
    public String getValue(@Nonnull String key) {
        for (Map.Entry<Integer, KeyValue> entry : this.map.entrySet()) {
            if (entry.getValue().key.equals(key)) {
                return entry.getValue().value;
            }
        }
        return nullMark;
    }

    @Override
    public int getKeyIndex(@Nonnull String key) {
        for (Map.Entry<Integer, KeyValue> entry : this.map.entrySet()) {
            if (entry.getValue().key.equals(key)) {
                return entry.getKey();
            }
        }
        return -1;
    }

    @Nonnull
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Integer, KeyValue> entry : this.map.entrySet()) {
            stringBuilder.append(entry.getKey()).append(":").append(entry.getValue().toString());
            stringBuilder.append("-");
        }
        return stringBuilder.toString();
    }

    @Nonnull
    @Override
    public KeyValueStringHelper parseString(@Nonnull String string) {
        KeyValueStringOrderHelper keyValueStringOrderHelper = new KeyValueStringOrderHelper(this.keySet, this.valueSet, this.nullMark);
        for (String entry : string.split("-")) {
            String[] split = entry.split(":");
            if (split.length == 3 && this.keySet.contains(split[1]) && this.valueSet.contains(split[2])) {
                keyValueStringOrderHelper.putEntry(Integer.parseInt(split[0]), split[1], split[2]);
            }
        }
        return keyValueStringOrderHelper;
    }

    static class KeyValue {
        private String key;
        private String value;

        public KeyValue(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return key + ":" + value;
        }
    }
}
