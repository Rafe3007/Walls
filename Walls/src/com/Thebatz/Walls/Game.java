package com.Thebatz.Walls;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
		map.sendMessage(ChatColor.GRAY + "You have 10 mintues to prepare to fight");
		
		for(UUID uuid : map.getPlayers()) {
			kills.put(uuid, 0);
			Bukkit.getPlayer(uuid).closeInventory();
		}
	}
	
	public void battle() {
		map.sendMessage(ChatColor.GRAY + "The Walls have fallen... Last team standing wins");
		
		List<Block> wall = map.getWall();
		// For loop iterating wallMat String
		for (Block block : wall) {
			if(block.getType().equals(Material.DIRT)) {
				block.setType(Material.ACACIA_PLANKS);
				map.sendMessage("Found Dirt!!");
			} else {
				map.sendMessage("Didn't find jazz :/ Size: " + wall.size());
			}
			
		}
		
	}
	
	// TODO add Team Amounts to end game
	public void addKill(Player player) {
		int k = kills.get(player.getUniqueId()) + 1;
		kills.put(player.getUniqueId(), k);
	}

}
