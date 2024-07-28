package io.taraxacum.finaltech.core.helper;

import io.taraxacum.libs.slimefun.dto.BlockStorageHelper;

/**
 * @author Final_ROOT
 * @since 2.4
 */
public class RouteShow {
    public static final String KEY = "rs";

    public static final String VALUE_TRUE = "t";
    public static final String VALUE_FALSE = "f";

    public static final BlockStorageHelper HELPER = BlockStorageHelper.newInstanceOrGet(BlockStorageHelper.ID_CARGO, KEY, VALUE_FALSE, VALUE_TRUE);
}
