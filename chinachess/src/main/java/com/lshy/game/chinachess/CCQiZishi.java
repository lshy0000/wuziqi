package com.lshy.game.chinachess;

import com.lshy.game.TwoAbstractRole;

import java.util.ArrayList;
import java.util.List;

/**
 * 象棋 士的走棋规则
 */
public class CCQiZishi extends CCQiZi {
    public static final int[] centeread = new int[]{4, 1};
    public static final int[] centewhite = new int[]{4, 8};
    public static final int[][] jiaoread = new int[][]{{3, 0}, {5, 0}, {3, 2}, {5, 2}};
    public static final int[][] jiaowhite = new int[][]{{3, 7}, {5, 7}, {3, 9}, {5, 9}};

    public CCQiZishi(int[] postion, TwoAbstractRole role, CChessQipan chessQipan) {
        super(postion, role, chessQipan);
    }

    @Override
    public List<int[]> getNextPosion() {
        List<int[]> re = new ArrayList<>();
        if (role.isXianshou()) {
            if (postion[1] == centeread[1]) {
                for (int i = 0; i < jiaoread.length; i++) {
                    Add(re, jiaoread[i]);
                }
            } else {
                Add(re, centeread);
            }
        } else {
            if (postion[1] == centewhite[1]) {
                for (int i = 0; i < jiaowhite.length; i++) {
                    Add(re, jiaowhite[i]);
                }
            } else {
                Add(re, centewhite);
            }
        }
        return re;
    }

    @Override
    public int getSocre() {
        return 3;
    }

    public int getbyte() {
        return 2;
    }
}
