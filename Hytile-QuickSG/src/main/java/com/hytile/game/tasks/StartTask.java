package com.hytile.game.tasks;

import com.hytile.game.GameStates;
import com.hytile.game.GameTimerManager;
import org.bukkit.scheduler.BukkitRunnable;

public class StartTask extends BukkitRunnable {
    private final GameTimerManager gameTimerManager;

    public StartTask(GameTimerManager gameTimerManager) {
        this.gameTimerManager = gameTimerManager;
    }

    @Override
    public void run() {
        if (gameTimerManager.getCurrentState() == GameStates.DEATHMATCH) {
            gameTimerManager.startDeathmatchTimer();
        }
    }
}
