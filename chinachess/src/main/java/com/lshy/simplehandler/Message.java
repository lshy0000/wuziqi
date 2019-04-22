package com.lshy.simplehandler;

/**
 * ---------------------
 * 作者：weixin_34088583
 * 来源：CSDN
 * 原文：https://blog.csdn.net/weixin_34088583/article/details/87466772
 * 版权声明：本文为博主原创文章，转载请附上博文链接！
 */
public class Message {
    public int what;
    public int arg1;
    public int arg2;
    public Object obj;
 
    Handler target;
    Runnable callback;
 
    @Override
    public String toString() {
        return "Message{" +
                "what=" + what +
                ", arg1=" + arg1 +
                ", arg2=" + arg2 +
                ", target=" + target +
                ", callback=" + callback +
                '}';
    }
}
