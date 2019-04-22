package com.lshy.game;

/**
 * 决策类，表示一个决策，决策影响局面。提供恢复到上一步的方法，即执行changJuMian and recorvery 后，局面无变化
 */
public interface Action {

    void changeJuMian();

    <T extends Role> T getMyRole();
}
