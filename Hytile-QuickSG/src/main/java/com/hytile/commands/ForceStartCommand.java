package com.hytile.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.hytile.Initializer;
import com.hytile.game.GameStateManager;
import com.hytile.game.GameStates;

public class ForceStartCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	    if (!(sender instanceof Player)) {
	        sender.sendMessage("This command can only be executed by players.");
	        return true;
	    }

	    Player player = (Player) sender;

	    if (!player.isOp() && !player.hasPermission("quicksg.forcestart")) {
	        player.sendMessage(Initializer.PREFIX+ ChatColor.RED + "You don't have permission to use this command.");
	        return true;
	    }

	    GameStates gameState = GameStateManager.getInstance().getGameState(player);
	    if (gameState == GameStates.LOBBY || gameState == GameStates.LOBBY) {
	        GameStateManager.getInstance().setGameState(player, GameStates.INGAME);
	        player.sendMessage(Initializer.PREFIX+ChatColor.GREEN + "Force started the game!");
	        // Additional logic to remove the lobby or queue session
	    } else {
	        player.sendMessage(Initializer.PREFIX+ChatColor.RED + "You can only use this command in the lobby or queue session.");
	    }

	    return true;
	}
}
