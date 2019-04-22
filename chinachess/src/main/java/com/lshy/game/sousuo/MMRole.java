package com.lshy.game.sousuo;

import com.lshy.game.Action;
import com.lshy.game.TwoAbstractRole;
import com.lshy.game.TwoQI;
import com.lshy.game.chinachess.CChessQipan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MMRole extends TwoAbstractRole {
    int maxdeep;

    public MMRole(TwoQI game, int maxdeep) {
        super(game);
        this.maxdeep = maxdeep;
    }

    @Override
    public Action getMyAction() {

// 该 玩家的策略是 使用极大极小值 搜索找到 得分最高的action
        CChessQipan.istance = true;
        LinkAction action = MMSousuo.sousuo(this, null, getGame().getJuMian(), maxdeep, Integer.MAX_VALUE, Integer.MIN_VALUE, isXianshou(), 0);
        CChessQipan.istance = false;
        List<LinkAction> result=new ArrayList<>();// 过滤获取所有的最优子节点。
        for (Object o : action.child) {
            if (((LinkAction) o).getnodeInt() == action.zuihaojiedian.getnodeInt() && ((LinkAction) o).getScorel() == action.zuihaojiedian.getScorel()) {
                result.add((LinkAction) o);
            }
        }
        System.out.println("最优子节点数目："+result.size());
        return result.get(((int) (Math.random() * result.size()))).getT();

//        Collections.sort(action.child, new Comparator<LinkAction>() {
//            @Override
//            public int compare(LinkAction o1, LinkAction o2) {
//                return (((Integer) o2.getScorel()).compareTo(o1.getScorel())) * (isXianshou() ? 1 : -1);
//            }
//        });
//        int p = ((LinkAction) action.getChild().get(0)).getScorel();
//        int k = 0;
//        for (Object o : action.child) {
//            if (((LinkAction) o).getnodeInt()==action.zuihaojiedian.getnodeInt()&&((LinkAction) o).getScorel() == p) {
//                k++;
//            } else {
//                break;
//            }
//        }
//        return ((LinkAction) action.getChild().get(((int) (Math.random() * k)))).getT();
////        return  action.zuihaojiedian.getT();
    }

    @Override
    public String getId() {
        return "MAXMIN dp:" + maxdeep;
    }
}
