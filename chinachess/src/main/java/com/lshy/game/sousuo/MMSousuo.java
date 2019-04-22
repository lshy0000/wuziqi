package com.lshy.game.sousuo;

import com.lshy.game.Action;
import com.lshy.game.Game;
import com.lshy.game.QiPan;
import com.lshy.game.Role;
import com.lshy.game.TwoAbstractRole;
import com.lshy.game.TwoJuMian;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 极大极小值搜索
 */
public class MMSousuo {
    public static int MAXDEEP = 3;
    public static final int WIN = Integer.MAX_VALUE;
    public static final int FAIL = Integer.MIN_VALUE;

    public static <T extends QiPan, M extends Action> LinkAction sousuo(TwoAbstractRole role, LinkAction action, TwoJuMian<T, M> qiju, int maxdeep, int al, int bt, boolean ismax, int currentdeep) {
        if (action != null) {
            if (qiju.getQi().getState() != Game.Game_State_Doing_Wait) {
                action.setScorel((qiju.getWinner().isXianshou() ? WIN : FAIL) / (currentdeep + 1));
                return action;
            }
            if (maxdeep == 0) {
                action.setScorel(qiju.Pingu(((M) action.t)));
                return action;
            }
        } else {
            action = new LinkAction(null);
        }
        List<Action> actions = qiju.getQi().getAllActions(role);
        if (actions.size() == 0) {
            action.setScorel(qiju.Pingu(((M) action.t)));
            return action;
        }
        if (ismax) {
//            bt=Integer.MIN_VALUE;
            for (Action action1 : actions) {
                action1.changeJuMian();
                LinkAction action2 = new LinkAction(action1);
                action2.setParent(action);
                if (currentdeep == 0) {
                    action.add(action2);
                }
                int k = sousuo(role.nextRole(), action2, qiju, maxdeep - 1, al, bt, !ismax, currentdeep + 1).getScorel();

                if (k > bt) {
                    bt = k;
                    action.zuihaojiedian=action2;
                }
                action.setScorel(bt);
                qiju.recorvery(1);
                if (k > al) {
                    break;
                }
            }
            return action;
        } else {
//            al=Integer.MAX_VALUE;
            for (Action action1 : actions) {
                action1.changeJuMian();
                LinkAction action2 = new LinkAction(action1);
                action2.setParent(action);
                if (currentdeep == 0) {
                    action.add(action2);
                }
                int k = sousuo(role.nextRole(), action2, qiju, maxdeep - 1, al, bt, !ismax, currentdeep + 1).getScorel();
                if (k < al) {
                    al = k;
                    action.zuihaojiedian=action2;
                }
                action.setScorel(al);
                qiju.recorvery(1);
                if (k < bt) {
                    break;
                }
            }
            return action;
        }
    }

}
