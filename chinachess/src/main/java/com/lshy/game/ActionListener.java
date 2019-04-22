package com.lshy.game;

/**
 *   游戏角色操作游戏时 回调
 * @param <T>
 */
public interface ActionListener<T extends Action> {
    void OnRoleDoAction(T action);
}
