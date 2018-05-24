package com.lshy.wuziqi;

/**
 * 记录整条走法树
 */
public class Zoufa {
    public Zoufa() {
    }
    public int getRootInt() {
        Zoufa p = this;
        int k = 0;
        while (p != null) {
            k++;
            p =  p.getParent();
        }
        return k;
    }
    Zoufa parent;

    public Zoufa getParent() {
        return parent;
    }

    public void setParent(Zoufa parent) {
        this.parent = parent;
    }

    public static<T extends Zoufa> T getRoot(T zoufa) {
        if (zoufa == null) {
            return null;
        }
        if (zoufa.parent==null) {
            return zoufa;
        }
        zoufa.parent.setPingu(zoufa.getPingu());
        return getRoot((T) zoufa.parent);
    }

    int pingu;

    void print() {
    }

    public Zoufa(int pingu) {
        this.pingu = pingu;
    }

    public int getPingu() {
        return pingu;
    }

    public void setPingu(int pingu) {
        this.pingu = pingu;
    }


}