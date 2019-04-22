package com.lshy.game.chinachess;

import com.lshy.game.QiJu;
import com.lshy.game.QiPan;
import com.lshy.game.TwoAbstractRole;
import com.lshy.game.TwoJuMian;
import com.lshy.game.TwoQI;

import java.util.List;

/**
 * 象棋局面 ，描述了象棋当前局面, 以及当前action的评分
 */
public class CCJuMian extends TwoJuMian<CChessQipan, CCAction> {

    @Override
    public int Pingu(CCAction action) {
        return getQiPan().getcurrentScore();
    }

}
