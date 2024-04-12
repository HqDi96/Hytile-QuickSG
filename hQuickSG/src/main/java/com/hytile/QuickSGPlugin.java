package com.hytile;

import org.bukkit.plugin.java.JavaPlugin;

public class QuickSGPlugin extends JavaPlugin{
	
    private Initializer initializer;
    
	@Override
	public void onEnable() {
        initializer = new Initializer(this);
        initializer.initialize(this);
	}
 
	@Override
	public void onDisable() {
		
	}
}
