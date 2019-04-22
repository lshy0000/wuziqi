package com.lshy.game.chinachess;

import com.lshy.game.Action;
import com.lshy.game.TwoAbstractRole;
import com.lshy.game.TwoQI;

import java.util.ArrayList;
import java.util.List;

/**
 * 象棋类 游戏
 */
public class CChessImpl extends TwoQI<CCJuMian> {
    @Override
    public List<CCAction> getAllActions(TwoAbstractRole role) {
        List<CCAction> re = new ArrayList<>();
        List<CCQiZi> qiZis = getJuMian().getQiPan().getAllQiZi();
        for (CCQiZi ccQiZi : qiZis) {
            if (ccQiZi.getMyRole() == role) {
                re.addAll(getAction(ccQiZi));
            }
        }
        if (re.size() == 0)
            re.add(CCAction.getTouXiangAction(this));
        return re;
    }


    @Override
    public void activityRole(TwoAbstractRole role) {
        role.doAction();
    }

    private List<CCAction> getAction(CCQiZi ccQiZi) {
        List<CCAction> re = new ArrayList<>();
        List<int[]> c = ccQiZi.getNextPosion();
        for (int[] ints : c) {
            re.add(new CCAction(ccJuMian, ccQiZi, ints, ccQiZi.role));
        }
        return re;
    }


}
