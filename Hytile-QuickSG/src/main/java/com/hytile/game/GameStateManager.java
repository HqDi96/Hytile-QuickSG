package com.hytile.game;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter(AccessLevel.PRIVATE)
public class GameStateManager {

    private static final GameStateManager instance = new GameStateManager();
    private final Map<Player, GameStates> playerStates = new HashMap<>();

    private GameStateManager() {
    }

    public static GameStateManager getInstance() {
        return instance;
    }

    public void setGameState(Player player, GameStates gameStates) {
        playerStates.put(player, gameStates);
    }

    public GameStates getGameState(Player player) {
        return playerStates.getOrDefault(player, GameStates.LOBBY); 
    }
}
