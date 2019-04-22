package com.lshy.simplehandler;

/**
 * ---------------------
 * 作者：weixin_34088583
 * 来源：CSDN
 * 原文：https://blog.csdn.net/weixin_34088583/article/details/87466772
 * 版权声明：本文为博主原创文章，转载请附上博文链接！
 */
public class Looper {
    private static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();
    private static Looper sMainLooper;

    final MessageQueue mQueue = new MessageQueue();

    public static Looper getMainLooper() {
        return sMainLooper;
    }

    public static void prepareMainLooper() {
        prepare();
        synchronized (Looper.class) {
            if (sMainLooper != null) {
                throw new IllegalStateException("The main Looper has already been prepared.");
            }
            sMainLooper = myLooper();
        }
    }

    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    public static void prepare() {
        running = true;
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper());
    }

    static boolean running = true;

    public static void loop() {
        Looper looper = myLooper();
        for (; running; ) {
            Message msg = looper.mQueue.next();
            if (msg != null) {
                msg.target.dispatchMessage(msg);
            }
        }
    }

    public static void quit() {
        running = false;
    }
}
