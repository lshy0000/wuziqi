package com.lshy.game.chinachess;

import com.lshy.game.Game;
import com.lshy.game.QiPan;
import com.lshy.game.TwoAbstractRole;
import com.lshy.game.TwoQI;

import java.util.ArrayList;
import java.util.List;

public class CChessQipan implements QiPan {


    static final int Mode_Standard = 0; // 标准模式
    private int[][] data; // 棋盘数据 二维表示
    public int[][] canju;
    List<CCQiZi> qiZis;
    CCQiZi[][] data2;
    TwoQI twoQI;
    CCJuMian ccJuMian;

    public TwoQI getTwoQI() {
        return twoQI;
    }

    public void setTwoQI(TwoQI twoQI) {
        this.twoQI = twoQI;
    }

    public CCJuMian getCcJuMian() {
        return ccJuMian;
    }

    public void setCcJuMian(CCJuMian ccJuMian) {
        this.ccJuMian = ccJuMian;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public HistoryZhuofa getHistoryZhuofa() {
        return historyZhuofa;
    }

    public void setHistoryZhuofa(HistoryZhuofa historyZhuofa) {
        this.historyZhuofa = historyZhuofa;
    }

    public List<CCQiZi> getGrave() {
        return Grave;
    }

    public void setGrave(List<CCQiZi> grave) {
        Grave = grave;
    }

    public CCQiZishuai shuai;
    public CCQiZishuai jiang;
    /**
     * 开启探测
     */
    public static boolean istance;

    public CChessQipan(TwoQI twoQI, CCJuMian ccJuMian) {
        this.twoQI = twoQI;
        this.ccJuMian = ccJuMian;
        mode = Mode_Standard;
    }

    public int getcurrentScore() {
        int re = 0;
        for (CCQiZi qiZi : qiZis) {
            re += (qiZi.getSocre() * (qiZi.getMyRole().isXianshou() ? 1 : -1));
        }
        return re;
    }


    public void ShowQi() {//  棋盘数据 二维表示
        for (int i = 0; i < data2[0].length; i++) {
            for (int j = 0; j < data2.length; j++) {
                System.out.print(data2[j][i] == null ? "------" : data2[j][i].getMyRole().isXianshou() ? " 红" + data2[j][i].getName() + " " : " 白" + data2[j][i].getName() + " ");
            }
            System.out.println();
        }
//        for (int i = 0; i < data[0].length; i++) {
//            for (int j = 0; j < data.length; j++) {
//                System.out.print(data[j][i]);
//            }
//            System.out.println();
//        }
    }

    int mode;// 棋盘模式

    public int[][] getData() {
        return data;
    }

    public void setData(int[][] data) {
        this.data = data;
    }

    @Override
    public List<CCQiZi> getAllQiZi() {
        return qiZis;
    }

    public static final String[] chessstr = new String[]{"帅", "士", "相", "马", "车", "炮", "兵"};

    public static final int[][] initdata = new int[][]{
            {5, 4, 3, 2, 1, 2, 3, 4, 5},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 6, 0, 0, 0, 0, 0, 6, 0},
            {7, 0, 7, 0, 7, 0, 7, 0, 7},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {-7, 0, -7, 0, -7, 0, -7, 0, -7},
            {0, -6, 0, 0, 0, 0, 0, -6, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {-5, -4, -3, -2, -1, -2, -3, -4, -5},
    };

    public static int[][] zhuanzhi(int[][] initdata) {
        int[][] initdata2 = new int[initdata[0].length][initdata.length];
        for (int i = 0; i < initdata2.length; i++) {
            for (int j = 0; j < initdata2[0].length; j++) {
                initdata2[i][j] = initdata[j][i];
            }
        }
        return initdata2;
    }

    public void init() {
        if (mode == Mode_Standard) {
            qiZis = new ArrayList<>();
            data = zhuanzhi(initdata);
            if (canju != null) {
                data = canju;
            }
            initdata();
        }
    }

    public TwoAbstractRole getQiZiRole(int x, int y) {
        return data2[x][y] == null ? null : data2[x][y].getMyRole();
    }

    public CCQiZi getQiZi(int x, int y) {
        return data2[x][y];
    }

    public TwoAbstractRole getQiZiRole(int[] position) {
        TwoAbstractRole role = null;
//        try {
        role = data2[position[0]][position[1]] == null ? null : data2[position[0]][position[1]].getMyRole();
//        } catch (Exception e) {
//            System.out.println(position[0] + "  " + position[1]);
//        }
        return role;
    }

    //根据 data ,初始化棋盘
    private void initdata() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                CCQiZi qiZi = (creatQZByCode(data[i][j], i, j));
                if (qiZi != null) {
                    qiZis.add(qiZi);
                }
            }
        }
        data2 = new CCQiZi[data.length][data[0].length];
        for (CCQiZi qiZi : qiZis) {
            data2[qiZi.postion[0]][qiZi.postion[1]] = qiZi;
        }
    }

    HistoryZhuofa historyZhuofa = new HistoryZhuofa();

    public CCQiZi creatQZByCode(int code, int i, int j) {
        CCQiZi qiZi = null;
        if (code == 1) {
            shuai = new CCQiZishuai(new int[]{i, j}, twoQI.role1, this);
            qiZi = shuai;
        } else if (code == 2) {
            qiZi = (new CCQiZishi(new int[]{i, j}, twoQI.role1, this));
        } else if (code == 3) {
            qiZi = (new CCQiZixiang(new int[]{i, j}, twoQI.role1, this));
        } else if (code == 4) {
            qiZi = (new CCQiZima(new int[]{i, j}, twoQI.role1, this));
        } else if (code == 5) {
            qiZi = (new CCQiZiche(new int[]{i, j}, twoQI.role1, this));
        } else if (code == 6) {
            qiZi = (new CCQiZipao(new int[]{i, j}, twoQI.role1, this));
        } else if (code == 7) {
            qiZi = (new CCQiZibing(new int[]{i, j}, twoQI.role1, this));
        } else if (code == -1) {
            jiang = new CCQiZishuai(new int[]{i, j}, twoQI.role2, this);
            jiang.setName("将");
            qiZi = (jiang);
        } else if (code == -2) {
            qiZi = (new CCQiZishi(new int[]{i, j}, twoQI.role2, this));
        } else if (code == -3) {
            qiZi = (new CCQiZixiang(new int[]{i, j}, twoQI.role2, this));
        } else if (code == -4) {
            qiZi = (new CCQiZima(new int[]{i, j}, twoQI.role2, this));
        } else if (code == -5) {
            qiZi = (new CCQiZiche(new int[]{i, j}, twoQI.role2, this));
        } else if (code == -6) {
            qiZi = (new CCQiZipao(new int[]{i, j}, twoQI.role2, this));
        } else if (code == -7) {
            qiZi = (new CCQiZibing(new int[]{i, j}, twoQI.role2, this));
        }
        return qiZi;
    }

    public boolean checkAction(CCAction ccAction) {
        istance = true;
        ccAction.changeJuMian();
        if (twoQI.getState() != Game.Game_State_Doing_Wait) { // 获胜行为 合格，
            istance = true;
            ccJuMian.recorvery(1);
            return true;
        }
        List<CCAction> ccActions = twoQI.getAllActions(ccAction.getMyRole().nextRole());
        for (CCAction action : ccActions) {
            action.changeJuMian();
            if (twoQI.getState() != Game.Game_State_Doing_Wait && ccAction.getMyRole().nextRole() == ccJuMian.getWinner()) { // 游戏结束即为将军
                // 敌方获胜 行为非法
                ccJuMian.recorvery(2);
                istance = false;
                return false;
            }
            ccJuMian.recorvery(1);
        }
        istance = false;
        return true;
    }


    public boolean isjiangju(CCAction ccAction) {
        istance = true;
        if (twoQI.getState() != Game.Game_State_Doing_Wait) { // 游戏结束不是将军
            istance = false;
            return false;
        }
        List<CCAction> ccActions = twoQI.getAllActions(ccAction.role);
        for (CCAction action : ccActions) {
            action.changeJuMian();
            if (twoQI.getState() != Game.Game_State_Doing_Wait && ccAction.getMyRole() == ccJuMian.getWinner()) { // 游戏结束即为将军
                ccJuMian.recorvery(1);
                istance = false;
                return true;
            }
            ccJuMian.recorvery(1);
        }
        istance = false;
        return false;
    }

    @Override
    public void dozhuofa(CCQiZi ccQiZi, int[] nextpostion) {
        dozhuofa(ccQiZi, nextpostion, true);
    }

    public void dozhuofa(CCQiZi ccQiZi, int[] nextpostion, boolean savehist) {
        if (ccQiZi == null || getQiZi(ccQiZi.postion[0], ccQiZi.postion[1]) == null) {
            ShowQi();
            System.out.println(ccQiZi);
        }
        if (ccQiZi.getbyte(ccQiZi.getMyRole()) != getQiZi(ccQiZi.postion[0], ccQiZi.postion[1]).getbyte(getQiZi(ccQiZi.postion[0], ccQiZi.postion[1]).getMyRole())) {
            ShowQi();
            throw new UnsupportedOperationException("子位置错误");
        }
        if (savehist)
            historyZhuofa.put(ccQiZi, nextpostion, data2[nextpostion[0]][nextpostion[1]]);
        data2[ccQiZi.postion[0]][ccQiZi.postion[1]] = null;
        if (data2[nextpostion[0]][nextpostion[1]] != null) { //  吃子操作。
//            ShowQi();
            adddie(data2[nextpostion[0]][nextpostion[1]]);
            qiZis.remove(data2[nextpostion[0]][nextpostion[1]]);
            if (data2[nextpostion[0]][nextpostion[1]].getbyte() == 1) {
                // 将帅被吃，游戏结束
                if (!istance)
                    System.out.println("将帅被" + ccQiZi.getName() + "吃" + " 胜者：" + (ccQiZi.getMyRole().isXianshou() ? "红" : "白"));
                ccJuMian.getQi().changeState(Game.Game_State_End_Normal);
                ccJuMian.setWinner(ccQiZi.getMyRole());
            } else {
                if (!istance)
                    System.out.println(chessstr[data2[nextpostion[0]][nextpostion[1]].getbyte() - 1] + "被" + ccQiZi.getName() + "吃" + " 吃子者：" + (ccQiZi.getMyRole().isXianshou() ? "红" : "白"));
            }
        }
        data2[nextpostion[0]][nextpostion[1]] = ccQiZi;
        data[ccQiZi.postion[0]][ccQiZi.postion[1]] = 0;
        data[nextpostion[0]][nextpostion[1]] = ccQiZi.getbyte(ccQiZi.getMyRole());
        ccQiZi.postion = nextpostion;
    }

    List<CCQiZi> Grave = new ArrayList<>();

    private void adddie(CCQiZi ccQiZi) {
        Grave.add(ccQiZi);
    }

    private CCQiZi fromdead(int i, int i1, int i2) {
        CCQiZi ccQiZi = null;
        for (CCQiZi qiZi : Grave) {
            if (qiZi.getbyte(qiZi.getMyRole()) == i) {
                ccQiZi = qiZi;
                ccQiZi.postion = new int[]{i1, i2};
            }
        }
        if (ccQiZi != null) {
            Grave.remove(ccQiZi);
            return ccQiZi;
        } else {
            throw new UnsupportedOperationException("坟墓出错" + i + i1 + i2 + Grave.size());
        }
    }

    @Override
    public void undozhuofa(int i) {
        for (int j = 0; j < i; j++) {
            HistoryZhuofa.Zhuofa zhuofa = historyZhuofa.pop();
            if (getQiZi(zhuofa.p[1], zhuofa.p[2]) != null || getQiZi(zhuofa.p[3], zhuofa.p[4]) == null) {
                throw new UnsupportedOperationException("历史堆错误");
            }
//            dozhuofa(getQiZi(zhuofa.p[3], zhuofa.p[4]), new int[]{zhuofa.p[1], zhuofa.p[2]}, false);
            CCQiZi ccQiZi = getQiZi(zhuofa.p[3], zhuofa.p[4]);
            int[] nextpostion = new int[]{zhuofa.p[1], zhuofa.p[2]};
            data2[ccQiZi.postion[0]][ccQiZi.postion[1]] = null;
            data2[nextpostion[0]][nextpostion[1]] = ccQiZi;
            data[ccQiZi.postion[0]][ccQiZi.postion[1]] = 0;
            data[nextpostion[0]][nextpostion[1]] = ccQiZi.getbyte(ccQiZi.getMyRole());
            ccQiZi.postion = nextpostion;
            if (zhuofa.p[5] != 0) {
                ccJuMian.getQi().changeState(Game.Game_State_Doing_Wait);
                ccJuMian.setWinner(null);
                CCQiZi ccQiZi2 = fromdead(zhuofa.p[5], zhuofa.p[3], zhuofa.p[4]);
                if (ccQiZi2 != null) {
                    for (CCQiZi qiZi : qiZis) {
                        if (qiZi == ccQiZi2) {
                            throw new UnsupportedOperationException("活堆错误");
                        }
                    }
                    qiZis.add(ccQiZi2);
                    data2[zhuofa.p[3]][zhuofa.p[4]] = ccQiZi2;
                    data[zhuofa.p[3]][zhuofa.p[4]] = ccQiZi2.getbyte(ccQiZi2.getMyRole());
                }
            }
        }
//        System.out.println("回退后棋盘");
//        ShowQi();

    }


}
