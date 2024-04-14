package com.hytile.game.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.hytile.game.GameTimerManager;

public class PrepareTask extends BukkitRunnable {
    private final GameTimerManager gameTimerManager;

    public PrepareTask(GameTimerManager gameTimerManager) {
        this.gameTimerManager = gameTimerManager;
    }

    @Override
    public void run() {
        int onlinePlayers = Bukkit.getOnlinePlayers().size();
        if (onlinePlayers >= 8) {
            gameTimerManager.setQueueTimer(onlinePlayers); // Trigger setQueueTimer if there are 8 or more players
        } else {
            gameTimerManager.startQueueTimer(); // Otherwise, start the queue timer as usual
        }
    }
}
