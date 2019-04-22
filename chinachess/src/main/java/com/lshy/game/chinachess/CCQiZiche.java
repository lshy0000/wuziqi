package com.lshy.game.chinachess;

import com.lshy.game.TwoAbstractRole;

import java.util.ArrayList;
import java.util.List;

/**
 * 象棋 兵的走棋规则
 */
public class CCQiZiche extends CCQiZi {
    public CCQiZiche(int[] postion, TwoAbstractRole role, CChessQipan chessQipan) {
        super(postion, role, chessQipan);
    }

    @Override
    public List<int[]> getNextPosion() {
        List<int[]> re = new ArrayList<>();
// 四个方向移动
        for (int i = postion[0]+1; i <= 8; i++) {
            if (chessQipan.getQiZiRole(i, postion[1]) == null) {
                Add(re, new int[]{i, postion[1]});
            } else {
                Add(re, new int[]{i, postion[1]});
                break;
            }
        }
        for (int i = postion[0]-1; i >= 0; i--) {
            if (chessQipan.getQiZiRole(i, postion[1]) == null) {
                Add(re, new int[]{i, postion[1]});
            } else {
                Add(re, new int[]{i, postion[1]});
                break;
            }
        }
        for (int i = postion[1]+1; i <= 9; i++) {
            if (chessQipan.getQiZiRole(postion[0], i) == null) {
                Add(re, new int[]{postion[0], i});
            } else {
                Add(re, new int[]{postion[0], i});
                break;
            }
        }
        for (int i = postion[1]-1; i >= 0; i--) {
            if (chessQipan.getQiZiRole(postion[0], i) == null) {
                Add(re, new int[]{postion[0], i});
            } else {
                Add(re, new int[]{postion[0], i});
                break;
            }
        }
        return re;
    }

    @Override
    public int getSocre() {
        return 16;
    }

    public int getbyte() {
        return 5;
    }
}
