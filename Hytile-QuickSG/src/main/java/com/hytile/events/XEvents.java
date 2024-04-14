package com.hytile.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.hytile.game.GameStates;
import com.hytile.game.GameTimerManager;

public class XEvents implements Listener {

    private final GameTimerManager gameTimerManager;

    public XEvents(GameTimerManager gameTimerManager) {
        this.gameTimerManager = gameTimerManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (gameTimerManager.getCurrentState() == GameStates.LOBBY) {
            player.setGameMode(GameMode.ADVENTURE);
            player.setFoodLevel(999999999);
            player.setSaturation(1);
            player.setCanPickupItems(false); // Disallow picking up items
        } else {
            player.setGameMode(GameMode.SURVIVAL);
            player.setCanPickupItems(true); // Allow picking up items
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (gameTimerManager.getCurrentState() == GameStates.INGAME || gameTimerManager.getCurrentState() == GameStates.DEATHMATCH) {
                        int foodLevel = player.getFoodLevel();
                        if (foodLevel < 20) {
                            player.setFoodLevel(foodLevel + 1);
                        }
                    }
                }
            }.runTaskTimer(gameTimerManager.getPlugin(), 20 * 20, 20 * 20); // Slow down hunger by a factor of 20
        }
    }

    @EventHandler
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        if (event.getNewGameMode() == GameMode.SURVIVAL) {
            if (gameTimerManager.getCurrentState() == GameStates.LOBBY) {
                player.setFoodLevel(999999999);
                player.setSaturation(1);
                player.setCanPickupItems(false); // Disallow picking up items
            } else {
                player.setCanPickupItems(true); // Allow picking up items
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (gameTimerManager.getCurrentState() == GameStates.INGAME || gameTimerManager.getCurrentState() == GameStates.DEATHMATCH) {
                            int foodLevel = player.getFoodLevel();
                            if (foodLevel < 20) {
                                player.setFoodLevel(foodLevel + 1);
                            }
                        }
                    }
                }.runTaskTimer(gameTimerManager.getPlugin(), 20 * 20, 20 * 20); // Slow down hunger by a factor of 20
            }
        }
    }
}
