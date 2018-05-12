package com.lshy.wuziqi;


import java.util.Collection;

public class Sousuo {

    private static boolean M;
    private static boolean T = true;
    private static long cu;
    private static long cucu;
    private static long cuar[];
    private static long ttc = 20000;
    private static int levals = 5;
    private static int leval;

    public static <T extends Zoufa> T fenxi(Qi<T> qi) {
        MAX = Integer.MAX_VALUE;
        MIN = Integer.MIN_VALUE;
        T re = null;
        Qi qir = qi.copy();
        leval = levals;
        cuar = new long[leval];
        cucu = System.currentTimeMillis();
        boolean b = true;
        while (b) {
            try {
                re = (T) Zoufa.getRoot(c(qir, null, leval, MAX, MIN));
                b = false;
            } catch (RuntimeException e) {
                leval -= 2;
                System.out.println("超时导致level 降级");
                cucu = System.currentTimeMillis();
            }
        }
        re.print();
        return re;
    }

    public static <T extends Zoufa> T fenxi2(Qi<T> qi) {
        MAX = Integer.MAX_VALUE;
        MIN = Integer.MIN_VALUE;
        T re = null;
        Qi qir = qi.rever();
//        qir.print();
        leval = levals;
        cuar = new long[leval];
        cucu = System.currentTimeMillis();
        boolean b = true;
        while (b) {
            try {
                re = (T) Zoufa.getRoot(c(qir, null, leval, MAX, MIN));
                b = false;
            } catch (RuntimeException e) {
                leval--;
                System.out.println("超时导致level 降级");
                cucu = System.currentTimeMillis();
            }
        }
        re.print();
        return re;
    }

    static int MAX = Integer.MIN_VALUE;
    static int MIN = Integer.MAX_VALUE;

//    public static Zoufa c(Qi qi, Zoufa p, int k, int alf, int beit) throws RuntimeException {
//        if (p != null) {
//            pppppps("尝试落子 " + "alf:" + alf + "  beit:" + beit);
//            qi.luoZi(p, k % 2 == 0 ? 1 : -1);
//            if (M) p.print();
//        }
//        if (k == 0) {
//            if (p == null) {
//                return null;
//            }
//            int pp = qi.pingGu();
//            if (M) qi.print();
//            qi.unluoZi(p);
//            p.setPingu(pp);
//            if (M) p.print();
//            pppppps("到达树的最底层 " + "alf:" + alf + "  beit:" + beit + " 节点值：" + pp);
//            return p;
//        }
//        cu = Long.valueOf(System.currentTimeMillis());
//        Collection<Zoufa> gen = qi.gen();
//        pppppps("当前节点位置 " + "层数：" + k + "alf:" + alf + "  beit:" + beit + " 节点数目：" + gen.size());
////        tttt("着法生成用时" + (System.currentTimeMillis() - cu));
//        for (Zoufa zoufa : gen) {
//            zoufa.parent = p;
//        }
//        Zoufa re = null;
//        if (k % 2 == (leval - 1) % 2) {
//            Zoufa min = new Zoufa(Integer.MAX_VALUE);
//            cuar[k - 1] = System.currentTimeMillis();
//            for (Zoufa zoufa : gen) {
//                if (k == 1) {
//                    cu = System.currentTimeMillis();
//                }
//                Zoufa t = c(qi, zoufa, k - 1, alf, beit);
//                if (t.getPingu() < min.getPingu()) {
//                    min = t;
//                }
//                pppppps("循环查找最小值： " + "层数：" + k + "alf:" + alf + "  beit:" + beit + " 当前最小值：" + min.getPingu() + "本处节点值：" + t.getPingu());
//                if (min.getPingu() < beit) {
//                    pppppps("循环查找最小值的过程中发现超过beit下界 " + "层数：" + k + "alf:" + alf + "  beit:" + beit + " 当前最小值：" + min.getPingu() + "本处节点值：" + t.getPingu());
//                    break;
//                }
//                if (min.getPingu() < alf) {
//                    alf = min.getPingu();
//                    pppppps("根据当前最小值修改下一层的alf上界" + "层数：" + k + "alf:" + alf + "  beit:" + beit + " 当前最小值：" + min.getPingu() + "本处节点值：" + t.getPingu());
//                }
//                if (System.currentTimeMillis() > cucu + ttc) {
//                    throw new RuntimeException("算法超时");
//                }
//
//            }
//            if (k > 3)
//                tttt("第i层循环迭代分别总用时" + k + "  " + (System.currentTimeMillis() - cuar[k - 1]));
//            re = min;
//        } else {
//            Zoufa max = new Zoufa(Integer.MIN_VALUE);
//            cuar[k - 1] = Long.valueOf(System.currentTimeMillis());
//            for (Zoufa zoufa : gen) {
//                Zoufa t = c(qi, zoufa, k - 1, alf, beit);
//                if (t.getPingu() > max.getPingu()) {
//                    max = t;
//                }
//                pppppps("循环查找最大值： " + "层数：" + k + "alf:" + alf + "  beit:" + beit + " 当前最大值：" + max.getPingu() + "本处节点值：" + t.getPingu());
//                if (max.getPingu() > alf) {
//                    pppppps("循环查找最大值的过程中发现超过alf上界 " + "层数：" + k + "alf:" + alf + "  beit:" + beit + " 当前最大值：" + max.getPingu() + "本处节点值：" + t.getPingu());
//                    break;
//                }
//                if (max.getPingu() > beit) {
//                    beit = max.getPingu();
//                    pppppps("根据当前最大值修改下一层的beit下界" + "层数：" + k + "alf:" + alf + "  beit:" + beit + " 当前最大值：" + max.getPingu() + "本处节点值：" + t.getPingu());
//                }
//                if (System.currentTimeMillis() > cucu + ttc) {
//                    throw new RuntimeException("算法超时");
//                }
//            }
//            if (k > 3)
//                tttt("第i层循环迭代分别总用时" + k + "  " + (System.currentTimeMillis() - cuar[k - 1]));
//            re = max;
//        }
//        if (p != null) {
//            qi.unluoZi(p);
//        }
//        if (M) re.print();
//        pppppps("该节点 " + "alf:" + alf + "  beit:" + beit + " 节点值：" + re.getPingu());
//        return re;
//    }


    public static Zoufa c(Qi qi, Zoufa p, int k, int alf, int beit) throws RuntimeException {
        if (p != null) {
            qi.luoZi(p, k % 2 == 0 ? 1 : -1);
        }
        if (k == 0) {
            if (p == null) {
                return null;
            }
            int pp = qi.pingGu(p);
            qi.unluoZi(p);
            p.setPingu(pp);
            return p;
        }
        Collection<Zoufa> gen = qi.gen();
        for (Zoufa zoufa : gen) {
            zoufa.parent = p;
        }
        if (gen.size() == 0) {//无子节点直接进入评估。
            if (p == null) {
                return null;
            }
            int pp = qi.pingGu(p);
            qi.unluoZi(p);
            p.setPingu(pp);
            return p;
        }
        Zoufa re = null;
        if (k % 2 == (leval - 1) % 2) {
            Zoufa min = new Zoufa(Integer.MAX_VALUE);
            cuar[k - 1] = System.currentTimeMillis();
            for (Zoufa zoufa : gen) {
                Zoufa t = c(qi, zoufa, k - 1, alf, beit);
                if (t.getPingu() < min.getPingu()) {
                    min = t;
                }
                if (min.getPingu() < beit) {
                    break;
                }
                if (min.getPingu() < alf) {
                    alf = min.getPingu();
                }
                if (System.currentTimeMillis() > cucu + ttc) {
                    throw new RuntimeException("算法超时");
                }
            }
            re = min;
        } else {
            Zoufa max = new Zoufa(Integer.MIN_VALUE);
            for (Zoufa zoufa : gen) {
                Zoufa t = c(qi, zoufa, k - 1, alf, beit);
                if (t.getPingu() > max.getPingu()) {
                    max = t;
                }
                if (max.getPingu() > alf) {
                    break;
                }
                if (max.getPingu() > beit) {
                    beit = max.getPingu();
                }
                if (System.currentTimeMillis() > cucu + ttc) {
                    throw new RuntimeException("算法超时");
                }
            }
            re = max;
        }
        if (p != null) {
            qi.unluoZi(p);
        }
        return re;
    }


    private static void pppppps(String s) {
        if (M) {
            System.out.println(s);
        }
    }

    private static void tttt(String s) {
        if (T) {
            System.out.println(s);
        }
    }
}
