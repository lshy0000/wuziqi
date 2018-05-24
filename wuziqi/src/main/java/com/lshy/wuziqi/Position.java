package com.lshy.wuziqi;

public class Position extends Zoufa implements Comparable {
    public int x;
    public int y;
    public int socre;

    public int getSocre() {
        return socre;
    }

    public void setSocre(int socre) {
        this.socre = socre;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return x * 100 + y;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Position && ((Position) o).y == this.y && ((Position) o).x == this.x);
    }


    @Override
    void print() {
        Position p = this;
        int k = 0;
        while (p != null) {
            System.out.println("当前点：+k:" + k + p.x + "  " + p.y + " score:" + p.socre + " pingu" + p.getPingu());
            k++;
            p = (Position) p.getParent();
        }

    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Position))
            throw new RuntimeException("非正确比较对象");
        return -this.socre + ((Position) o).socre;
    }


}