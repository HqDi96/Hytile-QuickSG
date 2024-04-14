package com.hytile.game.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.hytile.Initializer;
import com.hytile.game.GameTimerManager;
import com.hytile.utils.ColorUtils;

import net.md_5.bungee.api.ChatColor;

public class GamePlayerUtils implements Listener {

    private GameTimerManager gameTimerManager;

    public GamePlayerUtils(GameTimerManager gameTimerManager) {
        this.gameTimerManager = gameTimerManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // GAME TIMER METHOD
        List<String> playerNames = new ArrayList<>();
        playerNames.add(event.getPlayer().getName());
        gameTimerManager.handlePlayerJoin(playerNames);

        // MESSAGE ON JOIN
        int onlinePlayers = Bukkit.getOnlinePlayers().size();
        int maxPlayers = Bukkit.getMaxPlayers();
        String message = ColorUtils.translatedMessage(Initializer.PREFIX+"&e" + event.getPlayer().getName() + " &ahas joined the server&8 (&a" + onlinePlayers + "/&a" + maxPlayers + "&8)");
        event.setJoinMessage(null);
        Bukkit.broadcastMessage(message);

        // ITEMS WHEN THE PLAYER JOINS
        ItemStack votingPaper = new ItemStack(Material.PAPER);
        ItemMeta votingPaperMeta = votingPaper.getItemMeta();
        votingPaperMeta.setDisplayName(ChatColor.GREEN+"VOTING");
        votingPaper.setItemMeta(votingPaperMeta);

        ItemStack rulesBook = new ItemStack(Material.BOOK);
        ItemMeta rulesBookMeta = rulesBook.getItemMeta();
        rulesBookMeta.setDisplayName(ChatColor.AQUA+"RULES");
        rulesBookMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        rulesBook.setItemMeta(rulesBookMeta);

        ItemStack exitSlime = new ItemStack(Material.SLIME_BALL);
        ItemMeta exitSlimeMeta = exitSlime.getItemMeta();
        exitSlimeMeta.setDisplayName(ChatColor.RED+"EXIT");
        exitSlime.setItemMeta(exitSlimeMeta);

        event.getPlayer().getInventory().setItem(0, votingPaper);
        event.getPlayer().getInventory().setItem(4, rulesBook);
        event.getPlayer().getInventory().setItem(8, exitSlime);
    }
    
    
     @EventHandler
     public void onPlayerLeaveEvent(PlayerQuitEvent event) {
    	 event.setQuitMessage(null);
         int onlinePlayers = Bukkit.getOnlinePlayers().size() - 1;
         int maxPlayers = Bukkit.getMaxPlayers();
         String message = ColorUtils.translatedMessage(Initializer.PREFIX+"&e" + event.getPlayer().getName() + " &ahas left the server&8 (&a" + onlinePlayers + "/&a" + maxPlayers + "&8)");
         Bukkit.broadcastMessage(message);

   }
}