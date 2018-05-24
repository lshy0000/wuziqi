package com.lshy.pingtu;

/**
 * Created by lshy on 2018-5-23.
 */

public class PingtuTest {
    public static void main(String[] arg) {
        PingStatue c = new PingStatue(new int[]{2, 3, 6, 7, 0, 1, 4, 5, 8});
        System.out.println(c.getValue() + "" + c.isKeda);
        if (c.isKeda) {
            new Pingtu().liucheng(c);
        }
    }
}
