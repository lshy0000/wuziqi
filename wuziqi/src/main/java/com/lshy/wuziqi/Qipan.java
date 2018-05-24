package com.lshy.wuziqi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by lshy on 2018-5-10.
 */

public class Qipan implements Qi<Position> {
    private int lenght;
    private int[][] qipanArr;
    private int[][] allLine;

    public Qipan(int lenght) {
        this.lenght = lenght;
        qipanArr = new int[lenght][lenght];
        init();
    }

    private void init() {
        allLine = new int[qipanArr.length * 6 - 2][qipanArr.length];
        for (int i = 0; i < qipanArr.length; i++) {
            allLine[i] = new int[qipanArr.length];
        }
        for (int i = qipanArr.length; i < qipanArr.length + qipanArr.length; i++) {
            allLine[i] = new int[qipanArr.length];
        }
        for (int i = qipanArr.length * 2; i < (qipanArr.length * 2 - 1) + qipanArr.length * 2; i++) {
            allLine[i] = new int[qipanArr.length - Math.abs(i - qipanArr.length * 3 + 1)];
        }
        for (int i = qipanArr.length * 4 - 1; i < (qipanArr.length * 2 - 1) + qipanArr.length * 4 - 1; i++) {
            allLine[i] = new int[qipanArr.length - Math.abs(i - qipanArr.length * 5 + 2)];
        }
    }

    //残局
    public void setQipanArr(int[][] qipanArr) {
        if (qipanArr.length > 1 && qipanArr[0].length == qipanArr.length) {
            this.qipanArr = qipanArr;
            fromCanjuinit();
        }
    }

    //来自残局初始化
    private void fromCanjuinit() {
        allLine = new int[qipanArr.length * 6 - 2][qipanArr.length];

        for (int i = 0; i < qipanArr.length; i++) {
            allLine[i] = new int[qipanArr.length];
            for (int j = 0; j < allLine[i].length; j++) {//行
                allLine[i][j] = qipanArr[i][j];
            }
        }
        for (int i = qipanArr.length; i < qipanArr.length + qipanArr.length; i++) {
            allLine[i] = new int[qipanArr.length];
            for (int j = 0; j < allLine[i].length; j++) {
                allLine[i][j] = qipanArr[j][i - qipanArr.length];
            }
        }
        for (int i = qipanArr.length * 2; i < (qipanArr.length * 2 - 1) + qipanArr.length * 2; i++) {
            int ping = i - qipanArr.length * 3 + 1;
            allLine[i] = new int[qipanArr.length - Math.abs(ping)];
            for (int j = 0; j < allLine[i].length; j++) {
                if (ping < 0) {
                    allLine[i][j] = qipanArr[0 + j][-ping + j];
                } else {
                    allLine[i][j] = qipanArr[ping + j][0 + j];
                }
            }
        }
        for (int i = qipanArr.length * 4 - 1; i < (qipanArr.length * 2 - 1) + qipanArr.length * 4 - 1; i++) {
            int ping = i - qipanArr.length * 5 + 2;
            allLine[i] = new int[qipanArr.length - Math.abs(ping)];
            for (int j = 0; j < allLine[i].length; j++) {
                if (ping < 0) {
                    allLine[i][j] = qipanArr[0 + j][+ping - j + qipanArr.length - 1];
                } else {
                    allLine[i][j] = qipanArr[ping + j][qipanArr.length - 1 - j];
                }
            }
        }
    }


    //获取棋盘
    public int[][] getQipanArr() {
        return qipanArr;
    }

    //获取当前位置的四条线
    public int[][] getPointLine(Position p) {
        int[][] re = new int[4][];
        re[0] = allLine[p.x];
        re[1] = allLine[p.y + qipanArr.length];
        re[2] = allLine[p.x - p.y + qipanArr.length * 3 - 1];
        re[3] = allLine[p.y + p.x + qipanArr.length * 4 - 1];
        return re;
    }

    //获取当前位置的四条线的复制
    public int[][] getPointLinecopy(Position p) {
        int[][] re = new int[4][];
        System.arraycopy(allLine[p.x], 0, re[0], 0, allLine[p.x].length);
        System.arraycopy(allLine[p.y + qipanArr.length], 0, re[1], 0, allLine[p.y + qipanArr.length].length);
        System.arraycopy(allLine[p.x - p.y + qipanArr.length * 3 - 1], 0, re[2], 0, allLine[p.x - p.y + qipanArr.length * 3 - 1].length);
        System.arraycopy(allLine[p.y + p.x + qipanArr.length * 4 - 1], 0, re[3], 0, allLine[p.y + p.x + qipanArr.length * 4 - 1].length);
        return re;
    }

    //获取棋盘上所有的线
    public int[][] getAllLine() {
        return allLine;
    }


    @Override
    public Collection<Position> gen(Position p) {
        //杀局启发，不能在落子。
        if (p != null && Jieshu(p)) {
            return Collections.EMPTY_LIST;
        }
        HashSet<Position> a = new HashSet<>();
        for (int i = 0; i < qipanArr.length; i++) {
            for (int j = 0; j < qipanArr[0].length; j++) {
                if (qipanArr[i][j] != 0) {
                    add(a, i + 2, j);
                    add(a, i - 2, j);
                    add(a, i, j + 2);
                    add(a, i, j - 2);

                    add(a, i + 2, j + 2);
                    add(a, i - 2, j - 2);
                    add(a, i + 2, j - 2);
                    add(a, i - 2, j + 2);

                    add(a, i + 1, j + 2);
                    add(a, i - 1, j - 2);
                    add(a, i + 1, j - 2);
                    add(a, i - 1, j + 2);

                    add(a, i + 2, j + 1);
                    add(a, i - 2, j - 1);
                    add(a, i + 2, j - 1);
                    add(a, i - 2, j + 1);

                    add(a, i - 1, j - 1);
                    add(a, i - 1, j);
                    add(a, i - 1, j + 1);
                    add(a, i, j - 1);
                    add(a, i, j + 1);
                    add(a, i + 1, j - 1);
                    add(a, i + 1, j);
                    add(a, i + 1, j + 1);
                }
            }
        }
        if (a.size() == 0) {
            return Arrays.asList(new Position(((int) (Math.random() * 5 + 5)), ((int) (Math.random() * 5 + 5))));
        }

        //点分启发
        //计算焦点分数，得到的位置根据焦点分数筛选和排序 着法生成，
        boolean isx = Isxianshou();
        for (Position position : a) {
            getJDScore(position, isx);
        }
        List<Position> re = new ArrayList<>();
        Iterator<Position> iter = a.iterator();
        while (iter.hasNext()) {
            re.add(iter.next());
        }
        Collections.sort(re);
//        for (Position position : re) {
//            position.print();
//        }
//        System.out.println(re.size()+"----------********");

        //截断点分过低的点
        List<Position> c = re.subList(0, re.size() > 15 ? 15 : re.size());
//        print();
//        for (Position position : c) {
//            position.print();
//        }
//        System.out.println(c.size()+"----------********");
        return c;
    }

    //判断先后手
    private boolean Isxianshou() {
        int sun = 0;
        for (int i = 0; i < qipanArr.length; i++) {
            for (int j = 0; j < qipanArr[0].length; j++) {
                sun = qipanArr[i][j];
            }
        }
        return (sun <= 0);
    }

    private void getJDScore(Position position, boolean isxianshou) {
        if (isxianshou) {
            int qian = getScores(position);
            luoZi(position, 1);
            int hou = getScores(position);
            unluoZi(position);
            position.socre = hou - qian;
        } else {
            int qian = getScores(position);
            luoZi(position, -1);
            int hou = getScores(position);
            unluoZi(position);
            position.socre = qian - hou;
        }
    }

    private int getScores(Position position) {
        int[][] a = getPointLine(position);
        int sun = 0;
        for (int[] ints : a) {
            sun += getScore(ints);
        }
        return sun;
    }

    @Override
    public void print() {
        Test.print(qipanArr);
        System.out.println();
    }

    @Override
    public boolean Jieshu() {
        for (int i = 0; i < allLine.length; i++) {
            if (Jieshu(allLine[i])) {
                return true;
            }
        }
        return false;
    }

    public boolean Jieshu(Position p) {
        for (int i = 0; i < getPointLine(p).length; i++) {
            if (Jieshu(getPointLine(p)[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean Jieshu(int[] a) {
        if (a.length > 5) {
            int p = -5;
            int k = 0;
            for (int i = 0; i < a.length; i++) {
                if (p == -5 && a[i] != 0) {
                    p = a[i];
                } else if (a[i] != p) {
                    k = 0;
                    p = a[i] != 0 ? a[i] : -5;
                } else {
                    k++;
                    if (k == 4) return true;
                }
            }
        }
        return false;

    }

    public int Jieshu2(Position p) {
        for (int i = 0; i < getPointLine(p).length; i++) {
            if (Jieshu2(getPointLine(p)[i]) != 0) {
                return Jieshu2(getPointLine(p)[i]);
            }
        }
        return 0;
    }

    public static int Jieshu2(int[] a) {
        if (a.length > 5) {
            int p = -5;
            int k = 0;
            for (int i = 0; i < a.length; i++) {
                if (p == -5 && a[i] != 0) {
                    p = a[i];
                } else if (a[i] != p) {
                    k = 0;
                    p = a[i] != 0 ? a[i] : -5;
                } else {
                    k++;
                    if (k == 4) return p;
                }
            }
        }
        return 0;
    }

    private void add(Collection a, int x, int y) {
        if (qipanArr.length > x && qipanArr.length > y && x > -1 && y > -1) {
            if (qipanArr[x][y] == 0) {
                a.add(new Position(x, y));
            }
        }
    }

    @Override
    public void luoZi(Position position, int i) {
        if (qipanArr[position.x][position.y] != 0)
            throw new NoSuchElementException("全局都是子了");
        qipanArr[position.x][position.y] = i;
        initc(position);
    }

    @Override
    public void unluoZi(Position position) {
        if (qipanArr[position.x][position.y] == 0)
            throw new NoSuchElementException();
        qipanArr[position.x][position.y] = 0;
        initc(position);
    }

    private void initc(Position p) {
        allLine[p.x][p.y] = qipanArr[p.x][p.y];
        allLine[p.y + qipanArr.length][p.x] = qipanArr[p.x][p.y];
        allLine[p.x - p.y + qipanArr.length * 3 - 1][Math.min(p.x, p.y)] = qipanArr[p.x][p.y];
        allLine[p.y + p.x + qipanArr.length * 4 - 1][Math.min(p.x, qipanArr.length - 1 - p.y)] = qipanArr[p.x][p.y];
    }

    static int SSS = 16000;

    @Override
    public int pingGu(Position position) {
        if (position != null && Jieshu(position))//避免戏弄对手。杀局时，评估当为定分。
            return Jieshu2(position) == 1 ? (100000 - position.getRootInt()) : (-160000 + position.getRootInt());

        int re = 0;
        if (position != null) {
            int[][] po = getPointLine(position);
            for (int i = 0; i < po.length; i++) {
                re += getScore(po[i]);
            }
            re += pingGu(((Position) position.parent));
        }
        return re;

    }

    public int[][] rever(int[][] qipanArr) {
        int[][] c = new int[qipanArr.length][qipanArr.length];
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[0].length; j++) {
                c[i][j] = -qipanArr[i][j];
            }
        }
        return c;
    }

    @Override
    public Qipan rever() {
        Qipan re = new Qipan(qipanArr.length);
        re.setQipanArr(rever(this.qipanArr));
        return re;
    }

    @Override
    public Qi copy() {
        Qipan re = new Qipan(qipanArr.length);
        re.setQipanArr(copy(this.qipanArr));
        return re;
    }

    private int[][] copy(int[][] qipanArr) {
        int[][] c = new int[qipanArr.length][qipanArr.length];
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[0].length; j++) {
                c[i][j] = qipanArr[i][j];
            }
        }
        return c;
    }


    public static int getScore(int[] a) {
        if (a.length < 5) return 0;
        int re = 0;
        for (int i = 0; i < a.length - 5; i++) {
            re += getScoreswuzi(new int[]{a[i], a[i + 1], a[i + 2], a[i + 3], a[i + 4]});
        }
        return re;
    }


    public static int[] sss = new int[]{-160000, -9120, -512, -128, -2, 0, 1, 64, 256, 1024, 100000};

    public static int getScoreswuzi(int[] a) {
        int k = 0;
        int p = 0;
        for (int i = 0; i < a.length; i++) {
            if (k == 0) {
                k = a[i];
                p += k;
            } else {
                if (a[i] == k) {
                    p += k;
                } else if (a[i] != 0) {
                    return 0;
                }
            }

        }
        int re = sss[p + 5];
        return re;
    }

    public int pingGu2() {
        int re = 0;
        for (int i = 0; i < allLine.length; i++) {
            int p = getScore(allLine[i]);
            System.out.println("" + p);
            Test.print(allLine[i]);
            System.out.println();
            re += p;
        }
        return re;

    }
}
