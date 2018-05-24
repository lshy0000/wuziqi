package com.lshy.pingtu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lshy on 2018-5-23.
 */

public class PingStatue {
    int[][] sta;
    int[] xu;
    PingStatue parent;//父节点
    List<PingStatue> child;//子节点
    List<int[]> linjin;
    private int value;// f值//估值与真实值越接近，找到最优的速度越快。

    public int getValue() {
        if(value==0){
            jisuan();
        }
        return value;
    }

    private void jisuan() {
        if (parent!=null) {
            va=parent.va+shi;
        }
        value=getGuzhi()+va;
    }

    boolean isKeda;//可达状态
    int va;
    static  int shi=88; //实际系数
    static  int guju=67;//估值距离系数
    static  int xulie=33;//估值逆序数系数。
    //测试发现此种系数，搜索次数最少。

    public static PingStatue OBJ;//初始目标，需要维度。

    public PingStatue(int[][] sta) {
        this.sta = sta;
        xu = new int[sta.length * sta.length];
        for (int i = 0; i < sta.length; i++) {
            for (int j = 0; j < sta[i].length; j++) {
                xu[i * sta[i].length + j] = sta[i][j];
            }
        }

    }

    public PingStatue(PingStatue ob, int[] ints) {
        parent=ob;
        this.xu = ints;
        int k = Double.valueOf(Math.sqrt(xu.length)).intValue();
        sta = new int[k][k];
        for (int i = 0; i < sta.length; i++) {
            sta[i] = new int[k];
            for (int j = 0; j < sta[i].length; j++) {
                sta[i][j] = xu[i * sta[i].length + j];
            }
        }
    }

    public void kuozhuan() {
        int i = 0;
        for (; i < xu.length; i++) {
            if (xu[i] == xu.length - 1) {
                break;
            }
        }
        linjin = new ArrayList<>();

        //上下左右四个位置变换后的数组；
        if (i % sta.length != 0) {
            int[] r = new int[xu.length];
            for (int j = 0; j < xu.length; j++) {
                if (j == i - 1) {
                    r[j] = xu[i];
                } else if (j == i) {
                    r[j] = xu[i - 1];
                } else {
                    r[j] = xu[j];
                }
            }
            if (parent == null || !parent.equalIs(r)) {
                linjin.add(r);
            }
        }
        if (i % sta.length != sta.length - 1) {
            int[] r = new int[xu.length];
            for (int j = 0; j < xu.length; j++) {
                if (j == i + 1) {
                    r[j] = xu[i];
                } else if (j == i) {
                    r[j] = xu[i + 1];
                } else {
                    r[j] = xu[j];
                }
            }
            if (parent == null || !parent.equalIs(r)) {
                linjin.add(r);
            }
        }
        if (i > sta.length - 1) {
            int[] r = new int[xu.length];
            for (int j = 0; j < xu.length; j++) {
                if (j == i - sta.length) {
                    r[j] = xu[i];
                } else if (j == i) {
                    r[j] = xu[i - sta.length];
                } else {
                    r[j] = xu[j];
                }
            }
            if (parent == null || !parent.equalIs(r)) {
                linjin.add(r);
            }
        }
        if (i < xu.length - sta.length) {
            int[] r = new int[xu.length];
            for (int j = 0; j < xu.length; j++) {
                if (j == i + sta.length) {
                    r[j] = xu[i];
                } else if (j == i) {
                    r[j] = xu[i + sta.length];
                } else {
                    r[j] = xu[j];
                }
            }
            if (parent == null || !parent.equalIs(r)) {
                linjin.add(r);
            }
        }


    }

    public PingStatue(int k) {
        sta = new int[k][k];
        xu = new int[k * k];
        for (int i = 0; i < xu.length; i++) {
            if (i == 0) {
                xu[i] = 0;
            } else {
                xu[i] = xu[i - 1] + 1;
            }
        }
        for (int i = 0; i < sta.length; i++) {
            sta[i] = new int[k];
            for (int j = 0; j < sta[i].length; j++) {
                sta[i][j] = xu[i * sta[i].length + j];
            }
        }
    }

    public PingStatue(int[] xu) {
        this.xu = xu;
        int k = Double.valueOf(Math.sqrt(xu.length)).intValue();
        sta = new int[k][k];
        for (int i = 0; i < sta.length; i++) {
            sta[i] = new int[k];
            for (int j = 0; j < sta[i].length; j++) {
                sta[i][j] = xu[i * sta[i].length + j];
            }
        }
    }

    public boolean equalIs(int[][] a) {
        for (int i = 0; i < a.length; i++) {
            if (Arrays.equals(a[i], sta[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean equalIs(int[] a) {
        return Arrays.equals(a, xu);
    }


    public int getGuzhi() {
        if (OBJ == null) {
            OBJ = new PingStatue(sta.length);
        }
        int re = 0;
        for (int i = 0; i < xu.length; i++) {
            re += Math.abs(xu[i] - OBJ.xu[i]);

        }
        re = re*guju + getNixuValue() * xulie;
        return re;
    }


    public int getNixuValue() {
        int re = getNixushu(getNixu());
        isKeda = re % 2 == 0;
        return re;
    }

    public int[] getNixu() {
        int[] re = new int[xu.length - 1];
        for (int j = 0, i = 0; i < xu.length; i++) {
            if (xu[i] != xu.length - 1) {
                re[j++] = xu[i];
            }
        }
        return re;
    }

    public static int getNixushu(int[] a) {
        return Maopao(Arrays.copyOf(a, a.length));
    }

    //冒泡排序
    public static int Maopao(int[] arr) {
        int re = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    re++;
                }
            }
        }
        return re;
    }

    public void print() {
        //倒叙打印
        PingStatue ob = this;
        int l = 0;
        while (ob != null) {
            System.out.println("路径经过节点:" + l);
            for (int i = 0; i < ob.sta.length; i++) {
                for (int j = 0; j < ob.sta[0].length; j++) {
                    System.out.print((ob.sta[i][j]) + ",");
                }
                System.out.println();
            }
            ob = ob.parent;
            l++;
        }
    }
}
