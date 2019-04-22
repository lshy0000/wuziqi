package com.lshy.game.chinachess;

import com.lshy.game.TwoAbstractRole;

import java.util.ArrayList;
import java.util.List;

/**
 * 象棋 炮的走棋规则
 */
public class CCQiZipao extends CCQiZi {
    public CCQiZipao(int[] postion, TwoAbstractRole role, CChessQipan chessQipan) {
        super(postion, role, chessQipan);
    }

    @Override
    public List<int[]> getNextPosion() {
        List<int[]> re = new ArrayList<>();
        for (int i = postion[0] + 1; i <= 8; i++) {
            if (chessQipan.getQiZiRole(i, postion[1]) == null) {
                Add(re, new int[]{i, postion[1]});
            } else {
                for (int j = i + 1; j <= 8; j++) {
                    if (chessQipan.getQiZiRole(j, postion[1]) != null) {
                        if (chessQipan.getQiZiRole(j, postion[1]) != role)
                            Add(re, new int[]{j, postion[1]});
                        break;
                    }
                }
                break;
            }
        }
        for (int i = postion[0] - 1; i >= 0; i--) {
            if (chessQipan.getQiZiRole(i, postion[1]) == null) {
                Add(re, new int[]{i, postion[1]});
            } else {
                for (int j = i - 1; j >= 0; j--) {
                    if (chessQipan.getQiZiRole(j, postion[1]) != null) {
                        if (chessQipan.getQiZiRole(j, postion[1]) != role)
                            Add(re, new int[]{j, postion[1]});
                        break;
                    }
                }
                break;
            }
        }
        for (int i = postion[1] + 1; i <= 9; i++) {
            if (chessQipan.getQiZiRole(postion[0], i) == null) {
                Add(re, new int[]{postion[0], i});
            } else {
                for (int j = i + 1; j <= 9; j++) {
                    if (chessQipan.getQiZiRole(postion[0], j) != null) {
                        if (chessQipan.getQiZiRole(postion[0], j) != role)
                            Add(re, new int[]{postion[0], j});
                        break;
                    }
                }
                break;
            }
        }
        for (int i = postion[1] - 1; i >= 0; i--) {
            if (chessQipan.getQiZiRole(postion[0], i) == null) {
                Add(re, new int[]{postion[0], i});
            } else {
                for (int j = i - 1; j >= 0; j--) {
                    if (chessQipan.getQiZiRole(postion[0], j) != null) {
                        if (chessQipan.getQiZiRole(postion[0], j) != role)
                            Add(re, new int[]{postion[0], j});
                        break;
                    }
                }
                break;
            }
        }
        return re;
    }

    @Override
    public int getSocre() {
        return 8;
    }

    public int getbyte() {
        return 6;
    }
}
