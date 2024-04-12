package com.hytile.game;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.hytile.Initializer;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;

public class GameTimerManager {
    @Getter
    private final ConcurrentHashMap<String, Long> playerJoinTimes = new ConcurrentHashMap<>();

    @Getter
    @Setter
    private long queueTimer = 240;

    @Getter
    @Setter
    private long deathmatchTimer = 60;

    @Getter
    @Setter
    private long gameTimer = 10 * 60;

    @Getter
    @Setter
    private GameStates currentState = GameStates.LOBBY;

    private ScheduledExecutorService queueTimerExecutor;
    private ScheduledExecutorService gameTimerExecutor;
    private ScheduledExecutorService deathmatchTimerExecutor;

    public void startQueueTimer() {
        // Start the queue timer
        queueTimerExecutor = Executors.newSingleThreadScheduledExecutor();
        queueTimerExecutor.schedule(() -> {
            int onlinePlayers = Bukkit.getOnlinePlayers().size();
            if (onlinePlayers >= 8) {
                // If the queue timer ends and there are still 8 or more players, start the game timer
                setCurrentState(GameStates.INGAME);
                startGameTimer();
            } else {
                // If there are not enough players, broadcast a message and reset the timer
                Bukkit.broadcastMessage(Initializer.PREFIX + ChatColor.RED + "Timer is being reset, not enough players.");
                resetQueueTimer();
            }
        }, queueTimer, TimeUnit.SECONDS);
    }

    private void resetQueueTimer() {
        // Reset the queue timer to 240 seconds
        setQueueTimer(240);
    }

    private void setQueueTimer(int newQueueTimer) {
        // Check if the new queue timer is less than 60 seconds
        if (newQueueTimer < 60) {
            // If the new queue timer is less than 60 seconds, reset it to the default value (240 seconds)
            Bukkit.broadcastMessage(Initializer.PREFIX + ChatColor.RED + "Queue timer cannot be set to less than 60 seconds. Resetting to default (240 seconds).");
            resetQueueTimer();
        } else {
            // If the new queue timer is greater than or equal to 60 seconds, set it to the specified value
            queueTimer = newQueueTimer;
            // If the queue timer is already running, cancel it and start a new one with the new timer value
            if (queueTimerExecutor != null) {
                queueTimerExecutor.shutdownNow();
            }
            startQueueTimer();
        }
    }


	public void startGameTimer() {
        // Start the game timer
        gameTimerExecutor = Executors.newSingleThreadScheduledExecutor();
        gameTimerExecutor.schedule(() -> {
            // If the game timer ends, check if there are enough players left or not
            int remainingPlayers = Bukkit.getOnlinePlayers().size();
            if (remainingPlayers >= 4) {
                // If there are enough players left, transition to DEATHMATCH
                setCurrentState(GameStates.DEATHMATCH);
                startDeathmatchTimer();
            } else {
                // If there are not enough players, reset the queue timer and transition back to LOBBY
                Bukkit.broadcastMessage(Initializer.PREFIX + ChatColor.RED + "Timer is being reset, not enough players.");
                resetQueueTimer();
                setCurrentState(GameStates.LOBBY);
            }
        }, gameTimer, TimeUnit.SECONDS);
    }

    public void startDeathmatchTimer() {
        // Start the deathmatch timer
        deathmatchTimerExecutor = Executors.newSingleThreadScheduledExecutor();
        deathmatchTimerExecutor.schedule(() -> {
            // If the deathmatch timer ends, set the state to LOBBY
            setCurrentState(GameStates.LOBBY);
            stopAllTimers();
        }, deathmatchTimer, TimeUnit.SECONDS);
    }

    public void handlePlayerJoin(List<String> playerNames) {
        // When a player joins, add them to the playerJoinTimes map
        for (String playerName : playerNames) {
            if (!playerJoinTimes.containsKey(playerName)) {
                playerJoinTimes.put(playerName, System.currentTimeMillis());
            }
        }
    }

    public void handlePlayerLeave(String playerName) {
        // When a player leaves, remove them from the playerJoinTimes map
        playerJoinTimes.remove(playerName);
        // Check if the current state is INGAME and there are fewer than 4 players remaining
        if (currentState == GameStates.INGAME && playerJoinTimes.size() < 4) {
            // Set the state to DEATHMATCH immediately
            setCurrentState(GameStates.DEATHMATCH);
            startDeathmatchTimer(); // Start the deathmatch timer
        }
    }

    public void setCurrentState(GameStates state) {
        // Update the current state
        currentState = state;
    }

    public void stopAllTimers() {
        // Stop all running timers
        if (queueTimerExecutor != null) queueTimerExecutor.shutdownNow();
        if (gameTimerExecutor != null) gameTimerExecutor.shutdownNow();
        if (deathmatchTimerExecutor != null) deathmatchTimerExecutor.shutdownNow();
    }

    public int getQueueTimer() {
        return (int) queueTimer;
    }

    public Plugin getPlugin() {
        // Return the plugin instance if needed
        return null;
    }

    public GameStates getCurrentState() {
        return currentState;
    }
}
