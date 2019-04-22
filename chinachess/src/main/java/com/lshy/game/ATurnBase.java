package com.lshy.game;

// 游戏试探类，表示试探策略，
public abstract class ATurnBase extends TurnBaseGame {

    // 仅供参考
    public void shitanliucheng() {
        activityRole(getJuMian().getCurrentJuMianRole());
        getJuMian().getCurrentJuMianRole().getMyAction().changeJuMian();

        while (getState() == Game_State_Doing_Wait) {
            activityRole(getJuMian().getCurrentJuMianRole().nextRole());
            Action action = getJuMian().getCurrentJuMianRole().getMyAction();
            action.changeJuMian();
        }
    }
}
