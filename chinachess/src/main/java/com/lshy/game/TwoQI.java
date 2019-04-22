package com.lshy.game;

import com.lshy.game.chinachess.CCAction;
import com.lshy.game.chinachess.CCJuMian;

import java.util.Arrays;
import java.util.List;

/**
 * 二人对弈 棋类游戏。
 */
public abstract class TwoQI<T extends TwoJuMian> extends QI<TwoAbstractRole> {
    int gameState;
    public T ccJuMian;
    public TwoAbstractRole role1;
    public TwoAbstractRole role2;

    public void setJuMian(T ccJuMian) {
        this.ccJuMian = ccJuMian;
        this.ccJuMian.setTwoQi(this);
    }

    @Override
    public void changeState(int i) {
        this.gameState = i;
    }

    @Override
    public int getState() {
        return gameState;
    }

    @Override
    public abstract List<CCAction> getAllActions(TwoAbstractRole role);

    @Override
    public void gameInit() {
        getJuMian().init();
    }

    @Override
    public void gameStart() {
        System.out.println("游戏开始");
        changeState(Game_State_Doing_Wait);
        getJuMian().getCurrentJuMianRole().doAction();
    }

    @Override
    public void gameEnd() {
        System.out.println("游戏结束");
    }


    @Override
    public void setRole(TwoAbstractRole... role) {
        if (role.length < 2) throw new ArrayIndexOutOfBoundsException("error role");
        role1 = (TwoAbstractRole) role[0];
        role2 = (TwoAbstractRole) role[1];
        role1.setAnother(role2);
        role2.setAnother(role1);
        System.out.println("先手玩家：" + role1.getId());
        role1.setXianshou(true);
        System.out.println("后手玩家：" + role2.getId());
    }

    @Override
    public T getJuMian() {
        return ccJuMian;
    }

    @Override
    public abstract void activityRole(TwoAbstractRole role);

    @Override
    public List<TwoAbstractRole> getAllRoles() {
        return Arrays.asList(role1, role2);
    }

}
