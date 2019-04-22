package com.lshy.game.test;

import com.lshy.game.TwoQI;
import com.lshy.game.chinachess.CCJuMian;
import com.lshy.game.chinachess.CChessImpl;
import com.lshy.game.chinachess.CChessQipan;
import com.lshy.game.sousuo.MMRole;

import static com.lshy.game.chinachess.CChessQipan.chessstr;
import static com.lshy.game.chinachess.CChessQipan.initdata;
import static com.lshy.game.chinachess.CChessQipan.zhuanzhi;

public class Canju {

    public static void main(String[] args) {
        //TODO...
        TwoQI twoQI = new CChessImpl();
        twoQI.setRole(new MMRole(twoQI, 4), new MMRole(twoQI, 3));
        CCJuMian ccJuMian = new CCJuMian();
        CChessQipan cChessQipan = new CChessQipan(twoQI, ccJuMian);
        ccJuMian.setChessQipan(cChessQipan);
        twoQI.setJuMian(ccJuMian);
        cChessQipan.canju = getCanjudata(canju);
        cChessQipan.init();
        cChessQipan.ShowQi();
    }

    public static String canju =
            "------------------ 红帅 ------------ 白炮 ------------\n" +
                    "------------------------ 红士 ------------------------\n" +
                    "------------------------------------------ 白车 ------\n" +
                    "------------------------ 白车 ------ 红兵 ------ 白兵 \n" +
                    "------------------------------------------------------\n" +
                    " 白兵 ------------------------------------------------\n" +
                    "------------ 白兵 ------ 白兵 ------ 白兵 ------------\n" +
                    " 白相 ------ 白马  白士  白相 ------------------------\n" +
                    "------------------------------ 白马 ------------------\n" +
                    "------------------ 白士  白将 ------------------------";

    static String canju2="------------------------------------------------------\n" +
            "------------ 白车 ------ 红帅 ------------------------\n" +
            "------------------------------------------------------\n" +
            "------------------------------------------------ 白兵 \n" +
            "------------------------------------------------------\n" +
            " 白兵 ------------------------------------------------\n" +
            "------------ 白兵 ------ 白兵  白炮  红兵 ------------\n" +
            " 白相 ------ 白马  白士 ------------------------------\n" +
            "------------------------------ 白马 ------------------\n" +
            "------------------ 白士  白将 ------------------------";
    public static int[][] getCanjudata(String str) {
        String[] strings = str.split("\n");
        int[][] a = new int[strings.length][];
        for (int p = 0; p < strings.length; p++) {
            int k = 0;
            a[p] = new int[9];
            String string = strings[p];
            for (int i = 0; i < string.length(); k++) {
                if (string.charAt(i) == '-') {
                    i = i + 6;
                    a[p][k] = 0;
                } else {
                    a[p][k] = getCode(string.substring(i + 1, i + 3));
                    i = i + 4;
                }
            }
        }
       return zhuanzhi(a);

    }

    private static int getCode(String substring) {
        int fuhao = substring.contains("红") ? 1 : -1;
        int code = 0;
        if (substring.contains("将")) {
            code = 1;
        }
        for (int i = 0; i < chessstr.length; i++) {
            if (substring.contains(chessstr[i])) {
                code = i + 1;
                break;
            }
        }
        return code * fuhao;
    }

}
