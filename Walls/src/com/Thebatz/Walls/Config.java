package com.Thebatz.Walls;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class Config {
	
	private static Main main;
	
	public Config (Main main) {
		Config.main  = main;
		
		main.getConfig().options().copyDefaults();
		main.saveDefaultConfig();
	}
	
	// Getters
	public static int getMinPlayers() { return main.getConfig().getInt("min-Players"); }
	public static int getMaxPlayers() { return main.getConfig().getInt("max-Players"); }
	public static int getLobbyCountdown() { return main.getConfig().getInt("lobby-countdown"); }
	public static int getPrepTime() { return main.getConfig().getInt("Prep-phase"); }
	public static int getBattleTime() { return main.getConfig().getInt("Battle-phase"); }
	
	public static World getLobbyWorld() { return Bukkit.getWorld(main.getConfig().getString("lobby-spawn.world")); }
	public static World getMapWorld(int id) { return Bukkit.getWorld(main.getConfig().getString("Maps." + id + ".world")); }
	public static String getMapName(int id) { return main.getConfig().getString("Maps." + id + ".name"); }  
	
	public static int getMapAmount() { return main.getConfig().getConfigurationSection("Maps.").getKeys(false).size(); }
}
