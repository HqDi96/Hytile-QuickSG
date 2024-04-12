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

import com.hytile.utils.ColorUtils;

public class GuiHandlers implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
	    if (event.getWhoClicked() instanceof Player) {
	        Player player = (Player) event.getWhoClicked();
	        ItemStack item = event.getCurrentItem();
	        if (item != null && item.getType() == Material.SLIME_BALL) {
	            // Dispatch the /hub command when the player right-clicks the slime
	            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/hub");
	        } else if (item != null && item.getType() == Material.PAPER) {
	            // Open the GUI when the player right-clicks the paper
	            openGui(player);
	        }
	    }
	}

	private void openGui(Player player) {
	    Inventory inventory = Bukkit.createInventory(null, 54, ColorUtils.translatedMessage("&aVOTING FOR A MAP"));
	    // Add items to the GUI
	    ItemStack item = new ItemStack(Material.STONE);
	    ItemMeta meta = item.getItemMeta();
	    meta.setDisplayName("Item 1");
	    item.setItemMeta(meta);
	    inventory.setItem(0, item);

	    player.openInventory(inventory);
	}

}