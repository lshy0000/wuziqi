package com.lshy.game;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.List;

/**
 * 回合游戏基类，所有的游戏都可以看成回合游戏
 * <p>
 * 回合游戏规定局面 ，回合等,局面可行决策, 回合游戏的流程为 ，游戏初始化-> 游戏开始-> 激活当前游戏角色-> 角色决策-> 局面改变 ->激活下次角色或者结束游戏。
 */
public abstract class TurnBaseGame<M extends TurnRole> implements Game<M> {

    public abstract <T extends JuMian> T getJuMian();

    Object liuchangHandle;

    public Object getLiuchangHandle() {
        return liuchangHandle;
    }

    /**
     * 该局面的当前回合角色的所有可行决策,不能为空，为空时，请设置投降
     */
    public abstract List<? extends Action> getAllActions(M role);

    /**
     * 转换回合
     */
    public void zhuanH() {
        getJuMian().setCurrentJumianRole(getJuMian().getCurrentJuMianRole().nextRole());
    }

    /**
     * 激活角色
     */
    public abstract void activityRole(M role);

    @Override
    public void gameLiucheng(ActionListener listener) {
        new Thread(() -> {
            Looper.prepare();
            liuchangHandle = new Handler(Looper.myLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.obj instanceof Action) {
                        Action action = (Action) msg.obj;
                        action.changeJuMian();
                        if (listener != null) {
                            listener.OnRoleDoAction(action);
                        }
                    }
                    if (getState() != Game_State_Doing_Wait) {
                        gameEnd();
                        ((Handler) liuchangHandle).getLooper().quit();
                    } else {
                        zhuanH();
                        activityRole((M) getJuMian().getCurrentJuMianRole());

                    }
                }
            };
            gameInit();
            gameStart();
            Looper.loop();
        }).start();
    }

    @Override
    public void End() {
        if (liuchangHandle != null) {
            ((Handler) liuchangHandle).getLooper().quit();
        }
    }
}
