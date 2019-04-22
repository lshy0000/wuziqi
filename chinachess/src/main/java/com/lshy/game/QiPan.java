package com.lshy.game;

import com.lshy.game.chinachess.CCQiZi;

import java.util.List;

/**
 * 棋盘 标记类。
 */
public interface QiPan {
    List<? extends QiZi> getAllQiZi();

    void init();

    void dozhuofa(CCQiZi ccQiZi, int[] nextpostion);

    void undozhuofa(int i);

}
