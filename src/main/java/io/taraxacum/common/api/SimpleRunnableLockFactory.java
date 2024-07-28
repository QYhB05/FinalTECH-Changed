//package io.taraxacum.common.api;
//
//import javax.annotation.Nonnull;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.Callable;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.FutureTask;
//
//public class SimpleRunnableLockFactory<T> implements RunnableLockFactory<T> {
//    private final Map<T, FutureTask<Object>> MAP = new HashMap<>();
//    private final Object lock = new Object();
//
//    private SimpleRunnableLockFactory() {
//
//    }
//
//    @Override
//    @SafeVarargs
//    public final FutureTask<Void> waitThenRun(@Nonnull Runnable runnable, @Nonnull T... objects) {
//        FutureTask<Void> futureTask = new FutureTask<Void>(runnable, );
//        Runnable run = () -> {
//            boolean work = false;
//            while (!work) {
//                for (T object : objects) {
//                    if (SimpleRunnableLockFactory.this.MAP.containsKey(object)) {
//                        FutureTask<Object> task = SimpleRunnableLockFactory.this.MAP.get(object);
//                        try {
//                            task.get();
//                        } catch (InterruptedException | ExecutionException e) {
//                            e.printStackTrace();
//                        }
//                        SimpleRunnableLockFactory.this.MAP.remove(object);
//                    }
//                }
//                work = true;
//                synchronized (SimpleRunnableLockFactory.this.lock) {
//                    for (T object : objects) {
//                        if (SimpleRunnableLockFactory.this.MAP.containsKey(object)) {
//                            work = false;
//                            break;
//                        }
//                    }
//                    if (work) {
//                        for (T object : objects) {
//                            SimpleRunnableLockFactory.this.MAP.put(object, futureTask);
//                        }
//                        new Thread(futureTask).start();
//                    }
//                }
//            }
//        };
//        new Thread(run).start();
//        return futureTask;
//    }
//
//    @Override
//    public <C> FutureTask<C> waitThenRun(@Nonnull Callable<C> callable, @Nonnull T... objects) {
//        return null;
//    }
//
//    public static <T> RunnableLockFactory<T> getInstance() {
//        return new SimpleRunnableLockFactory<>();
//    }
//
//    public static void main(String[] args) {
//        RunnableLockFactory<Object> instance = SimpleRunnableLockFactory.getInstance();
//
//        Long millis = 1000L;
//
//        Runnable runnable1 = () -> {
//            System.out.println("begin" + 1);
//            try {
//                Thread.sleep(millis);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("end" + 1);
//        };
//        Runnable runnable2 = () -> {
//            System.out.println("begin" + 2);
//            try {
//                Thread.sleep(millis);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("end" + 2);
//        };
//        Runnable runnable3 = () -> {
//            System.out.println("begin" + 3);
//            try {
//                Thread.sleep(millis);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("end" + 3);
//        };
//        Runnable runnable4 = () -> {
//            System.out.println("begin" + 4);
//            try {
//                Thread.sleep(millis);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("end" + 4);
//        };
//        Runnable runnable5 = () -> {
//            System.out.println("begin" + 5);
//            try {
//                Thread.sleep(millis);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("end" + 5);
//        };
//
//        Object object1 = new Object();
//        Object object2 = new Object();
//        Object object3 = new Object();
//        Object object4 = new Object();
//        Object object5 = new Object();
//
//        System.out.println("begin");
//
//        instance.waitThenRun(runnable1, object1);
//        instance.waitThenRun(runnable2, object1, object2);
//        instance.waitThenRun(runnable3, object3);
//        instance.waitThenRun(runnable4, object2, object3);
//        instance.waitThenRun(runnable5, object2);
//
//        System.out.println("end");
//    }
//}
