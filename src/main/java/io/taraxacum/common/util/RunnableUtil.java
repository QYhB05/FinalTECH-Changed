package io.taraxacum.common.util;

import java.util.function.Consumer;

public class RunnableUtil {

    public static <T> int tryRun(Consumer<T> consumer, T... objects) {
        return RunnableUtil.tryRun(0, consumer, objects);

    }

    private static <T> int tryRun(int index, Consumer<T> consumer, T... objects) {
        int i = index;
        int error = 0;
        try {
            do {
                consumer.accept(objects[i]);
            } while (++i < objects.length);
        } catch (Exception e) {
            e.printStackTrace();
            error += 1 + RunnableUtil.tryRun(++i, consumer, objects);
        }
        return error;
    }

    public static <T> int tryRun(Consumer<T> exceptionConsumer, Consumer<T> consumer, T... objects) {
        return RunnableUtil.tryRun(0, exceptionConsumer, consumer, objects);
    }

    private static <T> int tryRun(int index, Consumer<T> exceptionConsumer, Consumer<T> consumer, T... objects) {
        int i = index;
        int error = 0;
        try {
            do {
                consumer.accept(objects[i]);
            } while (++i < objects.length);
        } catch (Exception e) {
            e.printStackTrace();
            exceptionConsumer.accept(objects[i]);
            error += 1 + RunnableUtil.tryRun(++i, consumer, objects);
        }
        return error;
    }
}
