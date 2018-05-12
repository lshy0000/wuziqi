package com.lshy.wuziqi;

/**
 * 记录整条走法树
 */
public class Zoufa {
    public Zoufa() {
    }

    Zoufa parent;

    public static Zoufa getRoot(Zoufa zoufa) {
        if (zoufa == null) {
            return null;
        }
        if (zoufa.parent==null) {
            return zoufa;
        }
        zoufa.parent.setPingu(zoufa.getPingu());
        return getRoot(zoufa.parent);
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