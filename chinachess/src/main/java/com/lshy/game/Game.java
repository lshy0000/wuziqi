package com.lshy.game;

import java.util.List;

/**
 * 游戏基类，包含游戏角色，状态。任何游戏应当 包含 ，游戏初始化，开始，游戏流程等。
 */
public interface Game<T extends Role> {
    int Game_State_Init = 0;// 游戏初始化
    int Game_State_End_Normal = Integer.MAX_VALUE;// 游戏正常结束
    int Game_State_End_Error = Integer.MIN_VALUE; // 游戏异常结束
    int Game_State_Doing_Wait = 1; // 游戏进行中

    void changeState(int i);

    int getState();

    List<T> getAllRoles();

    void gameInit();

    void gameStart();

    void gameEnd();

    /**
     * 游戏玩法流程。任何游戏的启动函数，表示一个游戏从开始 到结束的全过程。
     */
    <T extends Action> void gameLiucheng(ActionListener<T> actionListener);

    void setRole(T... role);

    /**
     *  主动结束游戏
     */
    void End();
}
