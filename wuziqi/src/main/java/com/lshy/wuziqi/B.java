package com.lshy.wuziqi;

/**
 * Created by lshy on 2018-5-9.
 */

public class B {
    private static final B ourInstance = new B();

    public static B getInstance() {
        return ourInstance;
    }

    private B() {
    }

    int[] a = new int[]{9, 4, 6, 7, 1, 5, 8, 4};
    int i = 0;

    public int getValue() {
//        return a[i++];
       return  ((int) (Math.random() * 10 + 1));
    }

}
