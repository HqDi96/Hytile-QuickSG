package com.hytile.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.hytile.Initializer;
import com.hytile.game.GameStateManager;
import com.hytile.game.GameStates;
import com.hytile.utils.ColorUtils;

import java.util.HashMap;

public class FixCommand implements CommandExecutor {

    protected Plugin plugin;
    private final HashMap<Player, Long> cooldowns = new HashMap<>();
    private final int cooldownTimeInSeconds = 3;

    public FixCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by players.");
            return true;
        }

        Player player = (Player) sender;

        if (!checkCooldown(player)) {
            player.sendMessage(Initializer.PREFIX + ColorUtils.translatedMessage("&c&LOops! &cThis command is on cooldown. Please wait."));
            return true;
        }

        GameStates gameState = GameStateManager.getInstance().getGameState(player);
        if (gameState != GameStates.DEATHMATCH && gameState != GameStates.INGAME) {
            player.sendMessage(Initializer.PREFIX + ColorUtils.translatedMessage("&c&LOops! &cYou cannot execute this command while in a queue session."));
            return true;
        }

        // Check if the player has a minimum distance of 3 blocks from the ceiling
        Location playerLocation = player.getLocation();
        Location ceilingLocation = playerLocation.clone().add(0, 3, 0);
        if (ceilingLocation.getBlock().getType() != Material.AIR) {
            player.sendMessage(Initializer.PREFIX + ColorUtils.translatedMessage("&c&LOops! &cYou must have at least 3 blocks of space above you to use this command."));
            return true;
        }

        // Teleport the player to a block above them
        Block blockAbove = playerLocation.getBlock().getRelative(0, 1, 0);
        player.teleport(blockAbove.getLocation());

        // Hide the player for 1 second
        player.getWorld().getPlayers().forEach(otherPlayer -> {
            if (!otherPlayer.equals(player)) {
                otherPlayer.hidePlayer(player);
            }
        });
        player.sendMessage(Initializer.PREFIX + ColorUtils.translatedMessage("&b&lSwosh! &aYou have been fixed. Continue playing!"));

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            player.getWorld().getPlayers().forEach(otherPlayer -> {
                if (!otherPlayer.equals(player)) {
                    otherPlayer.showPlayer(player);
                    player.showPlayer(otherPlayer);
                }
            });
        }, 20L); // 20 ticks = 1 second

        // Set cooldown for the player
        setCooldown(player);
        return true;
    }

    private void setCooldown(Player player) {
        cooldowns.put(player, System.currentTimeMillis() + (cooldownTimeInSeconds * 1000));
    }

    private boolean checkCooldown(Player player) {
        if (cooldowns.containsKey(player)) {
            long cooldownEnd = cooldowns.get(player);
            return cooldownEnd <= System.currentTimeMillis(); // Cooldown time has elapsed
        }
        return true; // No cooldown or cooldown time has elapsed
    }
}
