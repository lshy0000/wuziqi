package com.lshy.game.chinachess;

import com.lshy.game.QiZi;
import com.lshy.game.TwoAbstractRole;

import java.util.List;

/**
 * 象棋棋子
 */
public abstract class CCQiZi implements QiZi {

    int[] postion;
    TwoAbstractRole role;
    CChessQipan chessQipan;

    public CCQiZi(int[] postion, TwoAbstractRole role, CChessQipan chessQipan) {
        this.postion = postion;
        this.role = role;
        this.chessQipan = chessQipan;
        name = CChessQipan.chessstr[getbyte() - 1];
    }

    @Override
    public TwoAbstractRole getMyRole() {
        return role;
    }

    public int[] getPosion() {
        return postion;
    }

    public abstract List<int[]> getNextPosion(); // 下一步可行位置

    public abstract int getSocre(); // 棋力

    protected abstract int getbyte();// 存储码

    String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void Add(List<int[]> re, int[] postion) {
        if (role != chessQipan.getQiZiRole(postion)) {
//            if(chessQipan.getQiZiRole(postion)!=null){
//                chessQipan.ShowQi();
//                System.out.println("将要吃子"+role.getId()+role.isXianshou()+chessQipan.getQiZiRole(postion).getId()+chessQipan.getQiZiRole(postion).isXianshou());
//                System.out.println(getName()+""+ postion[0]+""+postion[1]+""+chessQipan.getQiZi(postion[0],postion[1]).getName());
//            }
            re.add(postion);
        }


    }

    public int getbyte(TwoAbstractRole role) {
        if (role.isXianshou()) {
            return getbyte();
        } else {
            return -getbyte();
        }
    }
}
