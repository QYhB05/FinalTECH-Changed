package io.taraxacum.libs.plugin.task;

import javax.annotation.Nonnull;

/**
 * @param <T> Target object type
 * @author Final_ROOT
 * @see TaskTicker
 * @since 2.2
 */
public interface EndTask<T> extends AbstractTask<T> {

    void endTick(@Nonnull T t);
}
