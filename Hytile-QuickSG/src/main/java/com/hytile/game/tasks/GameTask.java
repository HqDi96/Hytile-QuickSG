package com.hytile.game.tasks;

import com.hytile.game.GameStates;
import com.hytile.game.GameTimerManager;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {
    private final GameTimerManager gameTimerManager;

    public GameTask(GameTimerManager gameTimerManager) {
        this.gameTimerManager = gameTimerManager;
    }

    @Override
    public void run() {
        // Check if the current game state is INGAME
        if (gameTimerManager.getCurrentState() == GameStates.INGAME) {
            // Start the game timer
            gameTimerManager.startGameTimer();
        } else if (gameTimerManager.getCurrentState() == GameStates.LOBBY) {
            // Check if the game is ready to start (e.g., if forcestart command is executed)
            if (gameTimerManager.isGameReady()) {
                // Transition to the prepare round
                gameTimerManager.prepareRound();
                // Schedule the transition to in-game state after prepare round (e.g., 30 seconds)
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        // Transition to the in-game state
                        gameTimerManager.startGame();
                    }
                }.runTaskLater(gameTimerManager.getPlugin(), 20L * 30); // 20 ticks per second, 30 seconds
            }
        }
    }
}
