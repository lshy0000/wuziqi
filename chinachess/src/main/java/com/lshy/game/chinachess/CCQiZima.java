package com.lshy.game.chinachess;

import com.lshy.game.TwoAbstractRole;

import java.util.ArrayList;
import java.util.List;

/**
 * 象棋 兵的走棋规则
 */
public class CCQiZima extends CCQiZi {
    public CCQiZima(int[] postion, TwoAbstractRole role, CChessQipan chessQipan) {
        super(postion, role, chessQipan);
    }

    @Override
    public List<int[]> getNextPosion() {
        List<int[]> re = new ArrayList<>();
        if (postion[1] + 1 <= 9 && postion[0] + 2 <= 8 && chessQipan.getQiZiRole(postion[0] + 1, postion[1]) == null) {
            Add(re, new int[]{postion[0] + 2, postion[1] + 1});
        }
        if (postion[1] - 1 >= 0 && postion[0] + 2 <= 8 && chessQipan.getQiZiRole(postion[0] + 1, postion[1]) == null) {
            Add(re, new int[]{postion[0] + 2, postion[1] - 1});
        }
        if (postion[1] + 1 <= 9 && postion[0] - 2 >= 0 && chessQipan.getQiZiRole(postion[0] - 1, postion[1]) == null) {
            Add(re, new int[]{postion[0] - 2, postion[1] + 1});
        }
        if (postion[1] - 1 >= 0 && postion[0] - 2 >= 0 && chessQipan.getQiZiRole(postion[0] - 1, postion[1]) == null) {
            Add(re, new int[]{postion[0] - 2, postion[1] - 1});
        }
        if (postion[1] + 2 <= 9 && postion[0] + 1 <= 8 && chessQipan.getQiZiRole(postion[0], postion[1] + 1) == null) {
            Add(re, new int[]{postion[0] + 1, postion[1] + 2});
        }
        if (postion[1] + 2 <= 9 && postion[0] - 1 >= 0 && chessQipan.getQiZiRole(postion[0], postion[1] + 1) == null) {
            Add(re, new int[]{postion[0] - 1, postion[1] + 2});
        }
        if (postion[1] - 2 >= 0 && postion[0] + 1 <= 8 && chessQipan.getQiZiRole(postion[0], postion[1] - 1) == null) {
            Add(re, new int[]{postion[0] + 1, postion[1] - 2});
        }
        if (postion[1] - 2 >= 0 && postion[0] - 1 >= 0 && chessQipan.getQiZiRole(postion[0], postion[1] - 1) == null) {
            Add(re, new int[]{postion[0] - 1, postion[1] - 2});
        }
        return re;
    }

    @Override
    public int getSocre() {
        return 8;
    }

    public int getbyte() {
        return 4;
    }
}
