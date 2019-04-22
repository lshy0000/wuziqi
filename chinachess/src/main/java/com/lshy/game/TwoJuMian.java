package com.lshy.game;

import java.util.List;

/**
 * 象棋局面 ，描述了象棋当前局面
 */
public abstract class TwoJuMian<T extends QiPan, M extends Action> implements QiJu<TwoAbstractRole> {

    TwoAbstractRole currentrole;
    TwoQI twoQI;
    T chessQipan;
    TwoAbstractRole winner;

    public TwoAbstractRole getWinner() {
        return winner;
    }

    @Override
    public <T extends TwoQI> void setTwoQi(T t) {
        this.twoQI = t;
    }

    public void setWinner(TwoAbstractRole winner) {
        this.winner = winner;
    }

    public T getChessQipan() {
        return chessQipan;
    }

    public void setChessQipan(T chessQipan) {
        this.chessQipan = chessQipan;
    }

    public TwoJuMian() {

    }

    public void init() {
        currentrole = twoQI.role1;
        chessQipan.init();
    }

    @Override
    public int getState() {
        return twoQI.getState();
    }

    @Override
    public int getJuMianState(TwoAbstractRole role) {
        if (getCurrentJuMianRole() == role) {
            return JuMian_State_doing;
        } else {
            return JuMian_State_Wait;
        }
    }

    @Override
    public List<TwoAbstractRole> getAllRoles() {
        return twoQI.getAllRoles();
    }

    @Override
    public void setCurrentJumianRole(TwoAbstractRole role) {
        currentrole = role;
    }

    @Override
    public void recorvery(int i) {
        getQiPan().undozhuofa(i);
    }

    @Override
    public TwoAbstractRole getCurrentJuMianRole() {
        return currentrole;
    }

    @Override
    public T getQiPan() {
        return chessQipan;
    }

    @Override
    public TwoQI getQi() {
        return twoQI;
    }

    public abstract int Pingu(M action);
}
