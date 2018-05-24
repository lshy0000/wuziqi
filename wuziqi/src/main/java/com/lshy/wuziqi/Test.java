package com.lshy.wuziqi;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by lshy on 2018-5-10.
 */

public class Test {

    static String string = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,\n" +
            "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,\n" +
            "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,\n" +
            "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,\n" +
            "0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,\n" +
            "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,\n" +
            "0,0,0,0,0,1,*,0,0,0,0,0,0,0,0,\n" +
            "0,0,0,0,0,*,*,*,0,0,0,0,0,0,0,\n" +
            "0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,\n" +
            "0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,\n" +
            "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,\n" +
            "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,\n" +
            "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,\n" +
            "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,\n" +
            "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,";


    public static void main(String[] arg) {
//        System.out.println("中文测试");
        Qipan a = new Qipan(15);
//        String[] ac = string.split(",\n");
//        int[][] fdsaf = new int[15][15];
//        for (int i = 0; i < ac.length; i++) {
//            String[] pppsdsa = ac[i].split(",");
//            for (int i1 = 0; i1 < pppsdsa.length; i1++) {
//                if ("1".equals(pppsdsa[i1])) {
//                    fdsaf[i][i1] = 1;
//                } else if ("*".equals(pppsdsa[i1])) {
//                    fdsaf[i][i1] = -1;
//                }
//            }
//        }
//        a.setQipanArr(fdsaf);
//        System.out.println(a.Jieshu(new Position(14,12)));


//        a.setQipanArr(new int[15][15]);
//        a.luoZi(new Position(7, 7), -1);
//        a.luoZi(new Position(7, 8), -1);
//        a.luoZi(new Position(7, 9), -1);
//        a.luoZi(new Position(7, 10), -1);
//        a.gen();
//        a.luoZi(((Position) Sousuo.fenxi(a)), 1);
//        a.print();
//        a.luoZi(((Position) myClass.fenxi(a)), 1);
//        a.luoZi(((Position) myClass.fenxi2(a)), -1);
//        a.luoZi(((Position) myClass.fenxi(a)), 1);
//        a.luoZi(((Position) myClass.fenxi2(a)), -1);
//        a.luoZi(((Position) myClass.fenxi(a)), 1);
//        a.luoZi(((Position) myClass.fenxi2(a)), -1);
//        a.luoZi(((Position) myClass.fenxi(a)), 1);
//        a.luoZi(((Position) myClass.fenxi2(a)), -1);
        for (int i = 0; !a.Jieshu(); i++) {
            if (i % 2 != 0) {
                a.luoZi(((Position) Sousuo.fenxi2(a)), -1);
            } else {
                a.luoZi(((Position) Sousuo.fenxi(a)), 1);
            }
            a.print();
        }
        a.print();

//        System.out.println(((Qipan) a).pingGu2());
//        System.out.println( Qipan.getScores(new int[]{0, 0, 1, -1, 1}));
    }

    public static void print(int[][] magicSquare) {
        for (int i = 0; i < magicSquare.length; i++) {
            for (int j = 0; j < magicSquare[0].length; j++) {
                System.out.print((magicSquare[i][j] == -1 ? "*" : magicSquare[i][j]) + ",");
            }
            System.out.println();
        }
    }

    public static void print(int[] magicSquare) {
        System.out.println(Arrays.toString(magicSquare));
    }

    public static void print(Position position) {
        System.out.println(" " + position.x + " " + position.y);
    }

    public static void print(Collection<Position> positions) {
        for (Position position : positions) {
            System.out.println(" " + position.x + " " + position.y);
        }
    }

    public static void print(Position[] position) {
        for (int i = 0; i < position.length; i++)
            System.out.print(" " + position[i].x + " " + position[i].y + ",");
        System.out.println();
    }
}
