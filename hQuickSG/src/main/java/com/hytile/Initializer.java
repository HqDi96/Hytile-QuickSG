package com.hytile;

import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import com.hytile.events.XEvents;
import com.hytile.game.GameTimerManager;
import com.hytile.game.utils.GamePlayerUtils;
import com.hytile.game.utils.XgameEvent;
import com.hytile.handlers.GuiHandlers;
import com.hytile.utils.ColorUtils;

import lombok.Getter;
import lombok.Setter;

public class Initializer {
	
	@Getter @Setter
    private final JavaPlugin plugin;
	private GameTimerManager gameTimerManager;
	public static String PREFIX = ColorUtils.translatedMessage("&8▏ &e&LQuick&f&LSG &8» ");
	private GuiHandlers guiHandlers;
	private GamePlayerUtils gamePlayerUtils;
	private XgameEvent xgameEvent;
	private XEvents xEvents;

    public Initializer(JavaPlugin plugin) {
        this.plugin = plugin;
        this.gameTimerManager = new GameTimerManager();
        this.guiHandlers = new GuiHandlers();
        this.gamePlayerUtils = new GamePlayerUtils(gameTimerManager);
        this.xgameEvent = new XgameEvent(gameTimerManager, plugin);
    }

    public void initialize(JavaPlugin plugin) {
      registerListener();
      registerTimer();
    }
    
    
    public void registerListener() {
        if (guiHandlers != null) {
            Bukkit.getPluginManager().registerEvents(guiHandlers, plugin);
        }
        if (gamePlayerUtils != null) {
            Bukkit.getPluginManager().registerEvents(gamePlayerUtils, plugin);
        }
        if (xgameEvent != null) {
            Bukkit.getPluginManager().registerEvents(xgameEvent, plugin);
        }
        if (xEvents != null) {
            Bukkit.getPluginManager().registerEvents(xEvents, plugin);
        }
        
        
    }
    public void registerTimer() {
        this.gameTimerManager = new GameTimerManager();
        Bukkit.getServer().getServicesManager().register(GameTimerManager.class, gameTimerManager, plugin, ServicePriority.Normal);
    }

}