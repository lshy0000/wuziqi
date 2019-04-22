package com.lshy.game.chinachess;

import com.lshy.game.Action;
import com.lshy.game.Game;
import com.lshy.game.TwoAbstractRole;
import com.lshy.game.TwoQI;

/**
 * 象棋中的决策 即走法
 */
public class CCAction implements Action {
    CCJuMian ccJuMian;
    public CCQiZi ccQiZi;
    public int[] nextpostion;
    TwoAbstractRole role;

    public static CCAction getTouXiangAction(CChessImpl game) {
        return new CCAction(game.getJuMian()) {
            @Override
            public void changeJuMian() {
                System.out.println("无子可走 或投降");
                game.getJuMian().setWinner(game.getJuMian().getCurrentJuMianRole().nextRole());
                game.changeState(Game.Game_State_End_Normal);
            }
        };
    }

    public CCAction(CCJuMian ccJuMian, CCQiZi ccQiZi, int[] nextpostion, TwoAbstractRole role) {
        this.ccJuMian = ccJuMian;
        this.ccQiZi = ccQiZi;
        this.nextpostion = nextpostion;
        this.role = role;
    }

    public CCAction(CCJuMian juMian) {
        this.ccJuMian = ccJuMian;
    }

    @Override
    public void changeJuMian() {
        ccJuMian.getQiPan().dozhuofa(ccQiZi, nextpostion);
//        if (Math.random() > 0.99) {
//            ccJuMian.getQi().changeState(Game.Game_State_End_Normal);
//        }
    }


    @Override
    public TwoAbstractRole getMyRole() {
        return role;
    }
}
