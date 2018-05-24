package com.lshy.shudu;

/**
 * Created by lshy on 2018-5-22.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 线性规则限制
 */
public class Line {
    List<Shu> shus = new ArrayList<>();
    List<Integer> keneng = new ArrayList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
//
//    public boolean isChongtu() {
//
//        for (Shu shu : shus) {
//            if (shu.value != 0) {
//                if (keneng.contains(shu.value))
//                    keneng.remove(Integer.valueOf(shu.value));
//                else {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
}
