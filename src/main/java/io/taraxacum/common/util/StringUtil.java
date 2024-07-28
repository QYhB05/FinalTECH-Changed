package io.taraxacum.common.util;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    public static String[] split(@Nonnull String source, @Nonnull String... chars) {
        List<String> result = new ArrayList<>(chars.length + 1);
        for (String value : chars) {
            int i = source.indexOf(value);
            if (i != -1) {
                String previous = source.substring(0, i);
                String next = source.substring(i + value.length());
                result.add(previous);
                source = next;
            }
        }
        result.add(source);
        return result.toArray(new String[0]);
    }

    public static boolean onlyContainsNumber(@Nonnull String source) {
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
