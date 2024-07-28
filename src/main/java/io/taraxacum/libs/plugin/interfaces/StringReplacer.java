package io.taraxacum.libs.plugin.interfaces;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public interface StringReplacer {
    void addFunction(@Nonnull Function<String, String>... function);

    @Nonnull
    List<Function<String, String>> getFunction();

    @Nonnull
    default String getResult(@Nonnull String source) {
        for (Function<String, String> function : this.getFunction()) {
            source = function.apply(source);
        }
        return source;
    }

    @Nonnull
    default List<String> getResult(@Nonnull List<String> sourceList) {
        List<String> resultList = new ArrayList<>(sourceList.size());
        for (String source : sourceList) {
            for (Function<String, String> function : this.getFunction()) {
                source = function.apply(source);
            }
            resultList.add(source);
        }
        return resultList;
    }
}
