package io.taraxacum.common.api;

import javax.annotation.Nonnull;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Used for running #{@link Runnable} or #{@link Callable}.
 *
 * @param <T>
 */
public interface RunnableLockFactory<T> {

    /**
     * @param runnable logic you want to do
     * @param objects  wait for #{@link FutureTask} triggered to the objects be finished, than run the runnable
     */
    FutureTask<Void> waitThenRun(@Nonnull Runnable runnable, @Nonnull T... objects);

    <C> FutureTask<C> waitThenRun(@Nonnull Callable<C> callable, @Nonnull T... objects);
}
