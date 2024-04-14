package com.hytile.game.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.concurrent.atomic.AtomicInteger;

import com.hytile.game.GameTimerManager;
import org.bukkit.plugin.Plugin;

public class XgameEvent implements Listener {

    private final GameTimerManager gameTimerManager;
    private final Plugin plugin; // Store the plugin instance
    private boolean shortQueueTimer; // Flag to indicate whether to shorten the queue timer

    public XgameEvent(GameTimerManager gameTimerManager, Plugin plugin) {
        this.gameTimerManager = gameTimerManager;
        this.plugin = plugin; // Initialize the plugin instance
        this.shortQueueTimer = false; // Initialize the flag
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        AtomicInteger queueTimer = new AtomicInteger(gameTimerManager.getQueueTimer());
        if (queueTimer.get() > 0) {
            Bukkit.getScheduler().runTaskTimer(plugin, () -> { // Use the stored plugin instance
                if (queueTimer.get() > 0) {
                    queueTimer.decrementAndGet();
                    player.setLevel(queueTimer.get());
                } else {
                    player.setExp(0);
                    player.setTotalExperience(0);
                    if (shortQueueTimer) {
                        gameTimerManager.setQueueTimer(60); // Set queue timer to 60 seconds
                        gameTimerManager.startGameTimer();
                        shortQueueTimer = false; // Reset the flag
                    } else {
                        gameTimerManager.startGameTimer();
                    }
                }
            }, 0, 20);
        } else {
            gameTimerManager.startQueueTimer();
            // Check if the queue timer needs to be shortened
            if (Bukkit.getOnlinePlayers().size() >= 8) {
                shortQueueTimer = true;
            }
        }
    }

    // Method to reset the 240-second timer in the XP bar
    public void resetTimer(Player player) {
        if (!shortQueueTimer) {
            player.setLevel(240);
        }
    }
}
