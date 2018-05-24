package com.lshy.shudu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by lshy on 2018-5-22.
 */

public class Shudu {
    BlockingQueue<Shu> queen;
    int[][] data;
    Line[] lines;
    List<Shu> shus = new ArrayList<>();
    private Shu tance;
    private Shudu parent;

    void set(int[][] data) throws InterruptedException {
        this.data = data;
        queen = new LinkedBlockingQueue();
        shus = new ArrayList<>();
        init();
//        inittoQueen();
    }

    public Shudu() {
    }

    public Shudu(int[][] data) throws InterruptedException {
        set(data);
        inittoQueen();
    }

    private void init() {
        lines = new Line[27];
        for (int i = 0; i < data.length; i++) {//行
            lines[i] = new Line();
            for (int j = 0; j < data[0].length; j++) {
                Shu shu = new Shu(i, j, data[i][j]);
                shus.add(shu);
                lines[i].shus.add(shu);
                shu.lines.add(lines[i]);
            }
        }
        for (int i = 0; i < data.length; i++) {//列
            lines[i + data.length] = new Line();
            for (int j = 0; j < data[0].length; j++) {
                lines[i + data.length].shus.add(shus.get(j * data.length + i));
                shus.get(j * data.length + i).lines.add(lines[i + data.length]);
            }
        }
        for (int i = 0; i < data.length; i++) {//阵
            lines[i + data.length * 2] = new Line();
            for (int j = 0; j < data[0].length; j++) {
                Shu shu = shus.get((i / 3 * 3 + j / 3) * data.length + (i % 3 * 3 + j % 3));
                lines[i + data.length * 2].shus.add(shu);
                shu.lines.add(lines[i + data.length * 2]);
            }
        }
        for (Shu shu : shus) {
            if (shu.value > 0) {
                for (Line line : shu.lines) {
                    line.keneng.remove(Integer.valueOf(shu.value));
                }
            }
        }

    }


    void inittoQueen() throws InterruptedException {
        for (Shu shu : shus) {
            if (shu.value > 0) {
                queen.put(shu);
            }
        }

    }


    boolean dealQueen() throws InterruptedException {
        Shu ob;
        while ((ob = queen.poll()) != null) {
            boolean k = fenxi(ob);
            if (!k) {
                return false;
            }
        }
        return true;

    }

    private boolean fenxi(Shu ob) throws InterruptedException {
        for (Line line : ob.lines) {
            Iterator<Shu> c = line.shus.iterator();
            while (c.hasNext()) {
                Shu shu = c.next();
                if (shu.value == 0) {
                    if (shu.keneng.contains(Integer.valueOf(ob.getValue()))) {
                        shu.keneng.remove(Integer.valueOf(ob.getValue()));
                        if (shu.keneng.size() == 0) return false;
                        if (shu.keneng.size() == 1) {
                            for (Line line2 : shu.lines) {
                                if (!line2.keneng.contains(shu.keneng.get(0))) {
                                    return false;
                                }
                            }
                            shu.value = shu.keneng.get(0);
                            data[shu.x][shu.y] = shu.value;
                            queen.put(shu);
                            for (Line line2 : shu.lines) {
                                line2.keneng.remove(Integer.valueOf(shu.value));
                            }
                        }
                    }
                }
            }
        }
        return true;
    }


    boolean liucheng() throws InterruptedException {
        if (!dealQueen()) {
            if (tance == null) {
                System.out.println("该数独冲突，无解" + "");
            } else {
//                System.out.println("本次探测失败" + "探测位置：" + tance.x + " " + tance.y + "探测值：" + tance.value);
            }
            return false;
        }
        Shu ob = tanCe();
        if (ob == null || ob.keneng == null || ob.keneng.size() < 2) {
            //数独已经解开。
            System.out.println("该数独可能解：" + "");
            print();
            System.out.println("探测深度：" + getRootInt());
            printTance();
            return true;
        }
        boolean re = false;
        System.out.println("分支点" + getRootInt() + "探测位置：" + ob.x + " " + ob.y + "探测可能值：" + gett(ob.keneng));
        for (Integer integer : ob.keneng) {
            Shudu c = copy();
            c.tance = c.shus.get(ob.getPosition());
            c.tance.setValue(integer);
            c.data[c.tance.x][c.tance.y] = c.tance.value;
            c.queen.put(c.tance);
            for (Line line2 : c.tance.lines) {
                line2.keneng.remove(Integer.valueOf(c.tance.value));
            }
            c.parent = this;
            if (c.liucheng()) {
                re = true;
            } else {
                System.out.println("本次为父级探测，探测子项均失败" + "探测深度：" + getRootInt() + "  " + "探测位置：" + c.tance.x + " " + c.tance.y + "探测值：" + c.tance.value);
                printTance();
            }
        }
        return re;
    }

    private String gett(List<Integer> keneng) {
        String re = "";
        for (Integer integer : keneng) {
            re += integer + ",";
        }
        return re;
    }

    public int getRootInt() {
        Shudu p = this;
        int k = 0;
        while (p != null) {
            k++;
            p = p.parent;
        }
        return k;
    }

    public void printTance() {
        Shudu p = this;
        while (p != null) {
            if (p.tance != null) {
                System.out.println("本层探测" + (p.getRootInt() - 1) + "位置：" + p.tance.x + " " + p.tance.y + "探测值：" + p.tance.value);
//                System.out.println("分歧点");
//                p.print();
            }
            p = p.parent;
        }
    }

    private Shudu copy() throws InterruptedException {
        Shudu re = new Shudu();
        re.set(copy(data));
        for (int i = 0; i < shus.size(); i++) {
            if (re.shus.get(i).value == 0) {
                re.shus.get(i).keneng.clear();
                for (Integer integer : shus.get(i).keneng) {
                    re.shus.get(i).keneng.add(integer);
                }
            } else {
                for (Line line : re.shus.get(i).lines) {
                    line.keneng.remove(Integer.valueOf(re.shus.get(i).value));
                }
            }
        }
        return re;
    }

    public static int[][] copy(int[][] qipanArr) {
        int[][] c = new int[qipanArr.length][qipanArr.length];
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[0].length; j++) {
                c[i][j] = qipanArr[i][j];
            }
        }
        return c;
    }

    private Shu tanCe() {
        return Collections.min(shus, new Comparator<Shu>() {
            @Override
            public int compare(Shu shu, Shu t1) {
                int r = 0;
                if (shu.value == 0 && t1.value == 0) {
                    r = (shu.keneng.size() - t1.keneng.size());
                } else {
                    r = shu.value - t1.value;
                }
                return r;
            }
        });
    }

    private void print() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                System.out.print((data[i][j] == -1 ? "*" : data[i][j]) + ",");
            }
            System.out.println();
        }
        System.out.println();
    }

}
