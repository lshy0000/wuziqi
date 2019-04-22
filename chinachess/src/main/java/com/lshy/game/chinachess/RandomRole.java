package com.lshy.game.chinachess;

import com.lshy.game.TwoAbstractRole;
import com.lshy.game.TwoQI;

import java.util.List;

/**
 * 一名随机决策的象棋选手。
 */
public class RandomRole extends TwoAbstractRole {

    private String id;

    public RandomRole(TwoQI game) {
        super(game);
        this.id = "Random" + ((int) (Math.random() * 1000));
    }

    @Override
    public CCAction getMyAction() {
        List<CCAction> a = getGame().getAllActions(this);
        int k = a.size();
        int t = (int) (Math.random() * k);
        int m = t % k;
//        System.out.println(getId() + ":" + m+" all:"+k);
        return a.get(m);
    }


    @Override
    public String getId() {
        return id;
    }
}
