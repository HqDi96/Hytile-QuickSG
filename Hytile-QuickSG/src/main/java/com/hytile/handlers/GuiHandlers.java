package com.hytile.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class GuiHandlers implements Listener {




    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            ItemStack item = event.getCurrentItem();
            if (item != null && item.getType() == Material.SLIME_BALL) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/hub");
            } else if (item != null && item.getType() == Material.PAPER) {
                openGui(player);
            }
        }
    }

    private void openGui(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, "VOTING FOR A MAP");

        // Fill the inventory with items here
        ItemStack item = new ItemStack(Material.WOOL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN+"Revolution");
        item.setItemMeta(meta);
        
        for (int i = 0; i < 54; i++) {
            inventory.setItem(i, item); 
        }

        player.openInventory(inventory);
    }
}
