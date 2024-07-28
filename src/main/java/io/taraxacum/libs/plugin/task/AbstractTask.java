package io.taraxacum.libs.plugin.task;

import org.bukkit.plugin.Plugin;

/**
 * @param <T> Target object type
 * @author Final_ROOT
 * @version 2
 * @see TaskTicker
 * @since 2.2
 */
public interface AbstractTask<T> {

    Plugin getPlugin();

    int getPriority();

    boolean isSync();
}
