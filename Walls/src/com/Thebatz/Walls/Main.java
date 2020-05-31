package com.Thebatz.Walls;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import org.bukkit.plugin.java.JavaPlugin;

import com.Thebatz.Walls.Commands.TestCommand;
import com.Thebatz.Walls.Commands.WallsCommand;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class Main extends JavaPlugin{
	
	private static Main instance;
	
	@Override 
	public void onEnable() {
		if(getAPI() != null) {
			System.out.println("[Walls] Worldedit found");
		} else {
			System.out.println(ChatColor.RED + "[Walls] Worldedit not found. Plugin disabled");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		
		Main.instance = this;
		new Config(this);
		
		try {
			new initiateFiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		new Manager();
		
		getCommand("test").setExecutor(new TestCommand());
		getCommand("walls").setExecutor(new WallsCommand());
		
		Bukkit.getPluginManager().registerEvents(new GameListener(), this);
	}
	
	public static Main getInstance() { return instance; }
	public WorldEditPlugin getAPI() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
		if(plugin instanceof WorldEditPlugin) {
			return (WorldEditPlugin) plugin;
		} else {
			return null;
		}
	} 

}
