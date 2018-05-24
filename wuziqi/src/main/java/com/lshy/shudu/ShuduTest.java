package com.lshy.shudu;

/**
 * Created by lshy on 2018-5-22.
 */

public class ShuduTest {
    static String string = "3,6,0,2,0,0,0,8,1,\n" +
            "8,0,9,0,0,3,0,0,7,\n" +
            "0,0,0,1,0,5,0,6,0,\n" +
            "0,9,4,0,0,0,5,0,2,\n" +
            "0,0,0,0,5,0,0,0,0,\n" +
            "5,0,2,0,0,0,7,4,0,\n" +
            "0,7,0,8,0,4,0,0,0,\n" +
            "2,0,0,7,0,0,1,0,4,\n" +
            "1,4,0,0,0,9,0,7,3";

    static String strig2 = "0,8,0,0,6,0,0,0,0,\n" +
            "4,0,0,0,7,0,0,0,1,\n" +
            "0,0,2,4,0,0,0,0,6,\n" +
            "0,3,9,2,0,0,0,0,0,\n" +
            "0,0,0,6,4,5,0,0,0,\n" +
            "0,0,0,0,0,8,5,7,0,\n" +
            "9,0,0,0,0,6,4,0,0,\n" +
            "7,0,0,0,8,0,0,0,5,\n" +
            "0,0,0,0,9,0,0,1,0";

    public static void main(String[] arg) {
        int[][] fdsaf = new int[9][9];
        String[] ac = strig2.split(",\n");

        for (int i = 0; i < ac.length; i++) {
            String[] pppsdsa = ac[i].split(",");
            for (int i1 = 0; i1 < pppsdsa.length; i1++) {
                fdsaf[i][i1] = Integer.valueOf(pppsdsa[i1]);

            }
        }

        Shudu shudu = null;
        try {
            shudu = new Shudu(fdsaf);
            shudu.liucheng();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
