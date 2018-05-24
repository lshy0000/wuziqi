package com.lshy.shudu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by lshy on 2018-5-22.
 */

public class Shu {
    List<Line> lines = new ArrayList<>();
    List<Integer> keneng;
    int x;
    int y;
    int postion;
    int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Shu(int x, int y, int value) {
        this.value = value;
        this.x = x;
        this.y = y;
        if (value > 0) {
            keneng = Collections.EMPTY_LIST;
        } else
            keneng = new ArrayList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        postion = x * 9 + y;
    }

    public int getPosition() {
        return postion;
    }

}
