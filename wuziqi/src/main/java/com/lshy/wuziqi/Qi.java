package com.lshy.wuziqi;

/**
 * Created by lshy on 2018-5-10.
 */

import java.util.Collection;

/**
 * @param <T> 走法。
 */
public interface Qi<T extends Zoufa> {
    /**
     * @return
     * @param p
     */
    public Collection<T> gen(T p);//着法生成

    public void print();

    public boolean Jieshu();

    /**
     * 落子操作
     *
     * @param position
     * @param i        //1 or -1 敌方或友方使用此走法
     */
    public void luoZi(T position, int i);

    /**
     * 落子操作回置
     *
     * @param position //1 or -1 敌方或友方退回此走法
     */
    public void unluoZi(T position);

    //评估函数
    public int pingGu(T zoufa);

    Qi rever();


    Qi copy();
}
