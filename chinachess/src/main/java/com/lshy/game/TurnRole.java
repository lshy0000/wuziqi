package com.lshy.game;

import android.os.Handler;
import android.os.Message;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 回合选手，选手可以拿到操作流程句柄。
 */
public interface TurnRole extends Role {

    Object getLiuChengHandler();

    Action getMyAction();

    <T extends TurnRole> T nextRole();

    /**
     *  激活后 获取当前决策，并操作游戏。
     */
    default void doAction() {
        Object handler = getLiuChengHandler();
        Class handlerclass = handler.getClass();
        try {
//            Message message = getLiuChengHandler().obtainMessage();
//            message.obj = getMyAction();
//            getLiuChengHandler().sendMessage(message);
            Method method = handlerclass.getMethod("obtainMessage");
            Object message = method.invoke(handler);
            Class messageclass = message.getClass();
            Field field = messageclass.getDeclaredField("obj");
            field.setAccessible(true);
            field.set(message, getMyAction());
            Method method1 = handlerclass.getMethod("sendMessage", messageclass);
            method1.invoke(handler, message);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }
}
