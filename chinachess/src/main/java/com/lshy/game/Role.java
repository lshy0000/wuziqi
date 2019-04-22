package com.lshy.game;

/**
 *  游戏玩家 基类
 */
public interface Role {
    String getId(); // 玩家标识
    <T extends Game> T getGame();
}
