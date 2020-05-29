package com.Thebatz.Walls;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Game {
	
	private Maps map;
	private HashMap<UUID, Integer> kills; 
	
	public Game(Maps map) {
		this.map = map;
		this.kills = new HashMap<>();
	}
	
	public void start() {
		map.setState(GameState.LIVE);
		map.sendMessage(ChatColor.AQUA + "The Walls have fallen..." + ChatColor.GRAY + " Last team standing wins");
		
		for(UUID uuid : map.getPlayers()) {
			kills.put(uuid, 0);
			Bukkit.getPlayer(uuid).closeInventory();
		}
	}
	
	// TODO add Team Amounts to end game
	public void addKill(Player player) {
		int k = kills.get(player.getUniqueId()) + 1;
		kills.put(player.getUniqueId(), k);
	}

}
