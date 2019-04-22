package com.lshy.game.chinachess;

import com.lshy.game.TwoAbstractRole;

import java.util.ArrayList;
import java.util.List;

/**
 * 象棋 兵的走棋规则
 */
public class CCQiZishuai extends CCQiZi {
    public CCQiZishuai(int[] postion, TwoAbstractRole role, CChessQipan chessQipan) {
        super(postion, role, chessQipan);
    }

    @Override
    public List<int[]> getNextPosion() {
        List<int[]> re = new ArrayList<>();
        if (role.isXianshou()) {
            if (postion[1] + 1 <= 2) {
                Add(re, new int[]{postion[0], postion[1] + 1});
            }
            if (postion[1] - 1 >= 0) {
                Add(re, new int[]{postion[0], postion[1] - 1});
            }
            if (postion[0] + 1 <= 5) {
                Add(re, new int[]{postion[0] + 1, postion[1]});
            }
            if (postion[0] - 1 >= 3) {
                Add(re, new int[]{postion[0] - 1, postion[1]});
            }
        } else {
            if (postion[1] + 1 <= 9) {
                Add(re, new int[]{postion[0], postion[1] + 1});
            }
            if (postion[1] - 1 >= 7) {
                Add(re, new int[]{postion[0], postion[1] - 1});
            }
            if (postion[0] + 1 <= 5) {
                Add(re, new int[]{postion[0] + 1, postion[1]});
            }
            if (postion[0] - 1 >= 3) {
                Add(re, new int[]{postion[0] - 1, postion[1]});
            }
        }
        boolean hejiu = true;
        if (chessQipan.shuai.postion[0] == chessQipan.jiang.postion[0]) {
            for (int i = chessQipan.shuai.postion[1] + 1; i <= chessQipan.jiang.postion[1] - 1; i = i + 1) {
                if (chessQipan.getQiZiRole(chessQipan.shuai.postion[0], i) != null) {
                    hejiu = false;
                    break;
                }
            }
            if (hejiu) {
                Add(re, role.isXianshou() ? chessQipan.jiang.postion : chessQipan.shuai.postion);
            }
        }
        return re;
    }

    @Override
    public int getSocre() {
        return 500;
    }

    public int getbyte() {
        return 1;
    }
}
