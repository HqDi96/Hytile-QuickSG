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

    public XgameEvent(GameTimerManager gameTimerManager, Plugin plugin) {
        this.gameTimerManager = gameTimerManager;
        this.plugin = plugin; // Initialize the plugin instance
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
                    gameTimerManager.startGameTimer();
                }
            }, 0, 20);
        } else {
            gameTimerManager.startQueueTimer();
        }
    }

}
