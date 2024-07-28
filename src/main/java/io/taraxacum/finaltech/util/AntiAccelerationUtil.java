package io.taraxacum.finaltech.util;

import io.taraxacum.finaltech.FinalTechChanged;
import io.taraxacum.finaltech.FinalTechChanged;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;

import javax.annotation.Nonnull;

public class AntiAccelerationUtil {
    public static String KEY = "anti-acceleration";

    /**
     * This method will determine if the given machine is accelerated.
     *
     * @param config The storage info in the machine location
     * @return whether a machine can work
     */
    public static boolean isAccelerated(@Nonnull Config config) {
        String s = config.getString(KEY);
        if (s != null && Integer.parseInt(s) == FinalTechChanged.getSlimefunTickCount()) {
            return true;
        }
        config.setValue(KEY, String.valueOf(FinalTechChanged.getSlimefunTickCount()));
        return false;
    }
}
