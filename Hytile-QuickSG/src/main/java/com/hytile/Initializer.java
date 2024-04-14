package com.hytile;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.hytile.commands.FixCommand;
import com.hytile.commands.ForceStartCommand;
import com.hytile.events.XEvents;
import com.hytile.game.GameTimerManager;
import com.hytile.game.tasks.GameTask;
import com.hytile.game.tasks.PrepareTask;
import com.hytile.game.tasks.StartTask;
import com.hytile.game.utils.GamePlayerUtils;
import com.hytile.game.utils.XgameEvent;
import com.hytile.handlers.GuiHandlers;
import com.hytile.utils.ColorUtils;

import dev.mqzen.boards.BoardManager;
import lombok.Getter;
import lombok.Setter;

public class Initializer {
	
	@Getter
	@Setter
    private final JavaPlugin plugin;
	@Getter
	@Setter
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
        this.xEvents = new XEvents(gameTimerManager);
    }

    public void initialize(JavaPlugin plugin) {
      // LISTENER REGISTRATION
      registerListener();
      // TIMER REGISTRATION
      registerTimer();
      // COMMAND REGISTRIATION
      registerCommand();
      // SCOREBOARD REGISTRATION
      registerBoard();
    }
    
    public void pluginDisable (JavaPlugin plugin) {
    	

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
        // Create instances of the tasks
        PrepareTask prepareTask = new PrepareTask(gameTimerManager);
        GameTask gameTask = new GameTask(gameTimerManager);
        StartTask startTask = new StartTask(gameTimerManager);
        
        // Get the scheduler from the Bukkit plugin instance
        BukkitScheduler scheduler = plugin.getServer().getScheduler();

        // Schedule the tasks to run at specific intervals
        prepareTask.runTask(plugin);
        gameTask.runTaskLater(plugin, 20); 
        startTask.runTaskLater(plugin, 40); 

        Bukkit.getServer().getServicesManager().register(GameTimerManager.class, gameTimerManager, plugin, ServicePriority.Normal);
    }
    
    
    // COMPLICATED WAY OF REGISTRATION BUT THE BEST WAY TO AVOID NULLPOINTEREXECPTION AND SHIT
    public void registerCommand() {
        PluginCommand fixCommand = plugin.getCommand("fix");
        if (fixCommand != null) {
            fixCommand.setExecutor(new FixCommand(plugin));
        } else {
            plugin.getLogger().warning("Failed to register the 'fix' command. Command not found.");
        }
        
        PluginCommand forceStart = plugin.getCommand("forcestart");
        if (forceStart != null) {
        	forceStart.setExecutor(new ForceStartCommand());
        } else {
            plugin.getLogger().warning("Failed to register the 'forcestart' command. Command not found.");
        }
    }
    
    public void registerBoard() {
        BoardManager.load(plugin);
        BoardManager.getInstance().setUpdateInterval(4L);
        BoardManager.getInstance().startBoardUpdaters();
    }


    public Command getCommand(String label) {
        return plugin.getCommand(label);
    }

	public static Initializer getInstance() {
		return null;
	}
}
