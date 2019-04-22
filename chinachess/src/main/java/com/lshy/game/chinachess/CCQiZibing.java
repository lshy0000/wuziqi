package com.lshy.game.chinachess;

import com.lshy.game.TwoAbstractRole;

import java.util.ArrayList;
import java.util.List;

/**
 * 象棋 兵的走棋规则
 */
public class CCQiZibing extends CCQiZi {
    public CCQiZibing(int[] postion, TwoAbstractRole role, CChessQipan chessQipan) {
        super(postion, role, chessQipan);
    }

    @Override
    public List<int[]> getNextPosion() {
        List<int[]> re = new ArrayList<>();
        if (role.isXianshou()) {
            if (postion[1] > 4) {
                if (postion[1] + 1 <= 9) {
                    Add(re, new int[]{postion[0], postion[1] + 1});
                }
                if (postion[0] + 1 <= 8) {
                    Add(re, new int[]{postion[0] + 1, postion[1]});
                }
                if (postion[0] - 1 >= 0) {
                    Add(re, new int[]{postion[0] - 1, postion[1]});
                }
            } else {
                Add(re, new int[]{postion[0], postion[1] + 1});
            }
        } else {
            if (postion[1] > 4) {
                Add(re, new int[]{postion[0], postion[1] - 1});
            } else {
                if (postion[1] - 1 >= 0) {
                    Add(re, new int[]{postion[0], postion[1] - 1});
                }
                if (postion[0] + 1 <= 8) {
                    Add(re, new int[]{postion[0] + 1, postion[1]});
                }
                if (postion[0] - 1 >= 0) {
                    Add(re, new int[]{postion[0] - 1, postion[1]});
                }
            }
        }
        return re;
    }

    @Override
    public int getSocre() {
        return 1;
    }

    public int getbyte() {
        return 7;
    }
}
