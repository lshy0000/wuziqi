package com.lshy.game.chinachess;

import java.util.ArrayList;
import java.util.List;

public class HistoryZhuofa {
    List<Zhuofa> hist = new ArrayList<>();

    //队尾添加元素
    public void put(CCQiZi qiZi, int[] postion, CCQiZi chizi) {
        Zhuofa zhuofa = Zhuofa.createinstance(qiZi, postion, chizi);
//        System.out.print("落子");
//        for (int i1 : zhuofa.p) {
//            System.out.print(i1 + ",");
//        }
//        System.out.println();
        hist.add(zhuofa);
    }

    // 队尾 取出并移除元素
    public Zhuofa pop() {
        if (hist.size() == 0) return null;
        Zhuofa zhuofa = hist.get(hist.size() - 1);
//        System.out.print("回退");
//        for (int i1 : zhuofa.p) {
//            System.out.print(i1 + ",");
//        }
//        System.out.println();
        hist.remove(zhuofa);
        return zhuofa;
    }


    public static class Zhuofa {
        public int[] p;//棋子code，棋子x，棋子y，吃子code、

        public static Zhuofa createinstance(CCQiZi qiZi, int[] postion, CCQiZi chizi) {
            Zhuofa zhuofa = new Zhuofa();
            zhuofa.p = new int[]{qiZi.getbyte(qiZi.getMyRole()), qiZi.postion[0], qiZi.postion[1], postion[0], postion[1], chizi == null ? 0 : chizi.getbyte(chizi.getMyRole())};
            return zhuofa;
        }
    }

}
