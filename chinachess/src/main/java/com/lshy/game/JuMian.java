package com.lshy.game;

import java.util.List;

/**
 * 局面基类 ，局面 表示了，一场回合游戏的当前状态,谁的回合，回合状态等。
 */
public interface JuMian<M extends TurnRole> {

    int JuMian_State_Init = 0; // 我的回合开始
    int JuMian_State_End_Normal = Integer.MAX_VALUE; // 我的回合正常结束
    int JuMian_State_End_Error = Integer.MIN_VALUE;// 我的回合异常结束
    int JuMian_State_Wait = 2; // 我在等待我的回合到来
    int JuMian_State_doing = 1;// 我的回合进行中

    int getState();

    int getJuMianState(M role);

    List<M> getAllRoles();

    M getCurrentJuMianRole();

    void setCurrentJumianRole(M role);

    void recorvery(int i); // 局面回退
}
