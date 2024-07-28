package io.taraxacum.finaltech.core.helper;

import io.taraxacum.libs.slimefun.dto.BlockStorageHelper;

import java.util.ArrayList;

public class IgnorePermission {
    public static final String KEY = "ip";

    public static final String VALUE_FALSE = "f";
    public static final String VALUE_TRUE = "t";

    public static final BlockStorageHelper HELPER = BlockStorageHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, new ArrayList<>() {{
        this.add(VALUE_FALSE);
        this.add(VALUE_TRUE);
    }});
}
