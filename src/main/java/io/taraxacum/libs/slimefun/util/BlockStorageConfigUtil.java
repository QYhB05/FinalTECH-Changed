package io.taraxacum.libs.slimefun.util;

import io.taraxacum.finaltech.util.ConstantTableUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.4
 */
public class BlockStorageConfigUtil {
    public static boolean isEmptyConfig(@Nonnull Config config) {
        return config.getString(ConstantTableUtil.CONFIG_ID) == null;
    }
}
