package com.lshy.game.test;

import com.lshy.game.Action;
import com.lshy.game.ActionListener;
import com.lshy.game.chinachess.CCAction;
import com.lshy.game.chinachess.CCJuMian;
import com.lshy.game.chinachess.CChessImpl;
import com.lshy.game.TwoQI;
import com.lshy.game.chinachess.CChessQipan;
import com.lshy.game.chinachess.RandomRole;
import com.lshy.game.sousuo.MMRole;
import com.lshy.simplehandler.Handler;
import com.lshy.simplehandler.Looper;
import com.lshy.simplehandler.Message;

import java.sql.SQLOutput;

public class PlayChess {
    public static void main(String[] args) {

        CChessImpl twoQI = new CChessImpl() {
            com.lshy.simplehandler.Handler handler;

            @Override
            public void gameLiucheng(ActionListener listener) {
                final CChessImpl twoQI1 = this;
                new Thread(() -> {
                    Looper.prepare();
                    handler = new Handler(Looper.myLooper()) {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);

                            if (msg.obj instanceof Action) {
                                Action action = (Action) msg.obj;
                                action.changeJuMian();
                                if (listener != null) {
                                    listener.OnRoleDoAction(action);
                                }
                                System.out.println("当前局面");
                                twoQI1.getJuMian().getQiPan().ShowQi();
                            }
                            if (getState() != Game_State_Doing_Wait) {
                                gameEnd();
                                handler.getLooper().quit();
                            } else {
                                zhuanH();
                                activityRole(getJuMian().getCurrentJuMianRole());
                            }
                        }
                    };
                    gameInit();
                    System.out.println(ccJuMian.Pingu(null));
                    System.out.println("初始棋盘：");
                    twoQI1.getJuMian().getQiPan().ShowQi();
                    gameStart();
                    Looper.loop();
                }).start();
            }

            @Override
            public Object getLiuchangHandle() {
                return handler;
            }

            @Override
            public void End() {
                handler.getLooper().quit();
            }
        };
        twoQI.setRole(new MMRole(twoQI, 3), new MMRole(twoQI, 4));
        final CCJuMian ccJuMian = new CCJuMian();
        CChessQipan cChessQipan = new CChessQipan(twoQI, ccJuMian);
        cChessQipan.canju = Canju.getCanjudata(Canju.canju2);
        ccJuMian.setChessQipan(cChessQipan);
        twoQI.setJuMian(ccJuMian);

        twoQI.gameLiucheng(new ActionListener() {
            private long currenttime = System.currentTimeMillis();

            @Override
            public void OnRoleDoAction(Action action) {
                long time = 0;
                time = System.currentTimeMillis() - currenttime;
                currenttime = System.currentTimeMillis();
                if (cChessQipan.isjiangju(((CCAction) action))) {
                    System.out.println("----------------将军");
                }
                System.out.println("玩家" + action.getMyRole().getId() + "用时" + time);
                if (action instanceof CCAction)
                    System.out.println(((CCAction) action).ccQiZi.getName());
            }
        });
    }
}
