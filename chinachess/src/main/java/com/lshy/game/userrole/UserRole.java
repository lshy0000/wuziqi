package com.lshy.game.userrole;

import com.lshy.game.Action;
import com.lshy.game.TwoAbstractRole;
import com.lshy.game.TwoQI;

/**
 * 用户角色， 用户角色将在 被doaction函数激活后 ，通过OperateAction 获取游戏流程句柄 发送 message来操作游戏。
 */
public class UserRole extends TwoAbstractRole {
    String id;
    boolean isactivity;
    Action myAction;

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsactivity() {
        return isactivity;
    }

    public void setIsactivity(boolean isactivity) {
        this.isactivity = isactivity;
    }


    public void OperateAction(Action action) {
        if (action != null) {
            this.myAction = action;
            super.doAction();
            isactivity = false;
        }
    }

    public UserRole(TwoQI game, String id) {
        super(game);
        this.id = id;
    }

    public UserRole(TwoQI game) {
        super(game);
    }

    @Override
    public Action getMyAction() {
        return myAction;
    }

    @Override
    public void doAction() {
        isactivity = true;
    }

    @Override
    public String getId() {
        return id;
    }
}
