package com.lshy.simplehandler;

import java.util.LinkedList;
import java.util.Queue;

/**
 * ---------------------
 * 作者：weixin_34088583
 * 来源：CSDN
 * 原文：https://blog.csdn.net/weixin_34088583/article/details/87466772
 * 版权声明：本文为博主原创文章，转载请附上博文链接！
 */
public class MessageQueue {
    private final Queue<Message> mMessages = new LinkedList<>();
 
    public void enqueueMessage(Message msg) {
        synchronized (mMessages) {
            mMessages.add(msg);
            mMessages.notifyAll();
        }
    }
 
    public Message next() {
        synchronized (mMessages) {
            if (!mMessages.isEmpty()) {
                return mMessages.poll();
            }
            try {
                mMessages.wait();
                if (!mMessages.isEmpty()) {
                    return mMessages.poll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return next();
    }
}
