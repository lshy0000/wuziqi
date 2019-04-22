package com.lshy.game;

/**
 * 二人对弈棋类选手
 */
public abstract class TwoAbstractRole implements TurnRole {
    TwoQI game;
    TwoAbstractRole another;

     boolean isXianshou;

    public void setXianshou(boolean xianshou) {
        isXianshou = xianshou;
    }

    public boolean isXianshou() {
        return isXianshou;
    }

    @Override
    public TwoAbstractRole nextRole() {
        return another;
    }

    public void setGame(TwoQI game) {
        this.game = game;
    }

    public void setAnother(TwoAbstractRole another) {
        this.another = another;
    }

    public TwoAbstractRole(TwoQI game) {
        this.game = game;
    }

    public TwoQI getGame() {
        return game;
    }

    @Override
    public Object getLiuChengHandler() {
        return game.getLiuchangHandle();
    }

}
