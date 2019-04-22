package com.lshy.simplehandler;

/**
 * ---------------------
 * 作者：weixin_34088583
 * 来源：CSDN
 * 原文：https://blog.csdn.net/weixin_34088583/article/details/87466772
 * 版权声明：本文为博主原创文章，转载请附上博文链接！
 */
public class Handler {
    final Looper mLooper;
    final MessageQueue mQueue;
    final Callback mCallback;
 
    public Handler() {
        this(Looper.myLooper());
    }
 
    public Handler(Looper looper) {
        this(looper, looper.mQueue, null);
    }
 
    public Handler(Looper looper, Callback callback) {
        this(looper, looper.mQueue, callback);
    }
 
    public Handler(Looper looper, MessageQueue queue, Callback callback) {
        mLooper = looper;
        mQueue = queue;
        mCallback = callback;
    }
 
    public void dispatchMessage(Message msg) {
        if (msg.callback != null) {
            handleCallback(msg);
        } else {
            if (mCallback != null) {
                if (mCallback.handleMessage(msg)) {
                    return;
                }
            }
            handleMessage(msg);
        }
    }
    public Message obtainMessage(){
        return new Message();
    }
 
    public final void sendMessage(Message msg) {
        msg.target = this;
        mQueue.enqueueMessage(msg);
    }
 
    public void handleMessage(Message msg) {
    }

    public Looper getLooper() {
        return mLooper;
    }

    public interface Callback {
        boolean handleMessage(Message msg);
    }
 
    private static void handleCallback(Message message) {
        message.callback.run();
    }
 
    public final void post(Runnable runnable) {
        Message msg = new Message();
        msg.callback = runnable;
        sendMessage(msg);
    }
}
