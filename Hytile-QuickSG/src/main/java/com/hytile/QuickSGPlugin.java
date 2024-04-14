package com.hytile;

import org.bukkit.plugin.java.JavaPlugin;

public class QuickSGPlugin extends JavaPlugin{
	
    private Initializer initializer;
    
    /*
     *  Made by @ HaDi.
     *  For HytileMC Network.
     */
    
	@Override
	public void onEnable() {
        initializer = new Initializer(this);
        initializer.initialize(this);
	}
 
	@Override
	public void onDisable() {
	    Initializer initializer = Initializer.getInstance();
	    if (initializer != null) {
	        initializer.pluginDisable(this);
	   }
   }
}
