package com.lshy.game;

public interface QiJu<M extends TurnRole> extends JuMian<M> {
    public <T extends QiPan> T getQiPan();

    public <T extends QI> T getQi();

    public M getWinner();

    <T extends TwoQI> void setTwoQi(T t);

}
