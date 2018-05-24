package com.lshy.pingtu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by lshy on 2018-5-23.
 */

public class Pingtu {
//    广度搜索
//    从队列中取一个状态
//    判断是否是终点
//    终点结束
//    非终点,将状态标记为走过加入走过表中,将该状态的邻居状态表列出,判重
//    取出临近状态加入队列
//    A*算法
//    从队列中取出第一个元素
//    判断是否是终点
//    终点结束
//    非终点,将状态标记为走过加入走过表中,将该状态的邻居状态表列出,判重
//    更新f值,f值=估值+已花费代价
//    将临近状态加入队列,更具f值排序


    PriorityBlockingQueue<PingStatue> queen = new PriorityBlockingQueue<PingStatue>(100, new Comparator<PingStatue>() {
        @Override
        public int compare(PingStatue pingStatue, PingStatue t1) {
            return +pingStatue.getValue() - t1.getValue();
        }
    });

    List<PingStatue> fangwen = new ArrayList<>();

    public boolean liucheng(PingStatue chushi) {
        try {
            queen.put(chushi);
            PingStatue ob;
            int ips=0;
            while ((ob = queen.poll()) != null) {
                ips++;
                if (ob.equalIs(PingStatue.OBJ.xu)) {
                    ob.print();
                    System.out.println("搜索次数为："+ips);
                    return true;
                } else {
                    fangwen.add(ob);
                    ob.kuozhuan();
                    for (int[] ints : ob.linjin) {
                        boolean b = false;
                        for (PingStatue pingStatue : fangwen) {
                            if (pingStatue.equalIs(ints)) {
                                b = true;
                            }
                        }
                        if (!b) {
                            PingStatue statue = new PingStatue(ob,ints);
                            queen.put(statue);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
