package com.lshy.game.sousuo;

import com.lshy.game.Action;
import com.lshy.game.Role;

import java.util.ArrayList;
import java.util.List;

public class LinkAction<T extends Action> implements Action {
    LinkAction parent;
    T t;
    int scorel;
    List<LinkAction<T>> child;// 获得子节点，其中包含了分数相同的最优子节点，最优节点不是被截断的节点。
    LinkAction<T> zuihaojiedian;  // 最优子节点

    public List<LinkAction<T>> getChild() {
        return child;
    }

    public void setChild(List<LinkAction<T>> child) {
        this.child = child;
    }

    public void add(LinkAction<T> t) {
        if (child == null) {
            child = new ArrayList<>();
        }
        child.add(t);
    }

    public LinkAction(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public int getScorel() {
        return scorel;
    }

    public void setScorel(int scorel) {
        this.scorel = scorel;
    }

    public LinkAction getParent() {
        return parent;
    }

    public void setParent(LinkAction parent) {
        this.parent = parent;
    }

    /**
     * 获取 当前探测的action的深度
     *
     * @return
     */
    public int getRootInt() {
        LinkAction p = this;
        int k = 0;
        while (p != null) {
            k++;
            p = p.getParent();
        }
        return k;
    }
    public int getnodeInt() {
        LinkAction p = this;
        int k = 0;
        while (p != null) {
            k++;
            p = p.zuihaojiedian;
        }
        return k;
    }
    /**
     * 获取 当前探测的action的 根节点
     *
     * @return
     */
    public static <T extends LinkAction> T getRoot(T zoufa) {
        if (zoufa == null) {
            return null;
        }
        if (zoufa.parent == null) {
            return zoufa;
        }
        return getRoot((T) zoufa.parent);
    }

    @Override
    public void changeJuMian() {
        throw new UnsupportedOperationException("sub");
    }

    @Override
    public <T extends Role> T getMyRole() {
        throw new UnsupportedOperationException("sub");
    }
}
