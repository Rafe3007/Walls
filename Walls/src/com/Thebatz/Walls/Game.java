package com.Thebatz.Walls;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.Thebatz.Walls.Countdowns.Celebrate;

public class Game {
	
	private Maps map;
	private HashMap<UUID, Integer> kills; 
	private int blue, orange;
	private Celebrate celebrate;
	
	public Game(Maps map) {
		this.map = map;
		this.kills = new HashMap<>();
		this.blue = 0;
		this.orange = 0;
		this.celebrate = new Celebrate(map);
	}
	
	public void start() {
		map.setState(GameState.LIVE);
		map.sendMessage(ChatColor.GRAY + "You have " + ChatColor.RED + Config.getPrepTime() + ChatColor.GRAY + " mintues to prepare to fight");
		
		for(UUID uuid : map.getPlayers()) {
			kills.put(uuid, 0);
			
			if(map.isOnTeam(Bukkit.getPlayer(uuid), Team.BLUE)) {
				blue++;
			} else {
				orange++;
			}
			
			Bukkit.getPlayer(uuid).closeInventory();
		}
	}
	
	public void battle() {
		map.sendMessage(ChatColor.GRAY + "The Walls have fallen... Last team standing wins");
		List<String> matsList = Config.getWallMaterials(map.getID());
		List<Block> wall = map.getWall();
		// For loop iterating wallMat String
		for(String material : matsList) {
			for (Block block : wall) {
				if(block.getType().equals(Material.valueOf(material))) {
					block.setType(Material.AIR);
				}
			}
		}
		
	}
	
	// TODO add Team Amounts to end game
	public void addKill(Player player) {
		int k = kills.get(player.getUniqueId()) + 1;
		kills.put(player.getUniqueId(), k);
		
		if(map.isOnTeam(player, Team.BLUE)) {
			blue--;
		} else {
			orange--;
		}
		
		if(blue <= 0) {
			map.sendMessage(Team.BLUE.getdisplay() + ChatColor.AQUA + " Team Wins!");
			map.celebrate();
		} else if(orange <= 0) {
			map.sendMessage(Team.ORANGE.getdisplay() + ChatColor.AQUA + " Team Wins!");
			map.celebrate();
		} else {
			map.sendMessage(ChatColor.RED + "" + blue + ChatColor.BLUE + " Remain on Blue team.");
			map.sendMessage(ChatColor.RED + "" + orange + ChatColor.GOLD + " Remain on Orange team.");
		}
	}
	
	public void removeBody(Player player) {
		if(map.getTeam(player) == Team.BLUE) {
			blue--;
		} else if(map.getTeam(player) == Team.ORANGE) {
			orange--;
		}
	}
	
	public int getBlueAmt() { return blue; }
	public int getOrangeAmt() { return orange; }
	public Celebrate getCelebrate() { return celebrate; }
	
	public void subBlueAmt() {
		blue--;
		
		if(blue <= 0) {
			map.sendMessage(Team.ORANGE.getdisplay() + ChatColor.AQUA + " Team Wins!");
			map.celebrate();
		} else {
			map.sendMessage(ChatColor.RED + "" + blue + ChatColor.BLUE + " Remain on Blue team.");
			map.sendMessage(ChatColor.RED + "" + orange + ChatColor.GOLD + " Remain on Orange team.");
		}
	}
	
	public void subOrangeAmt() {
		orange--;
		
		if(orange <= 0) {
			map.sendMessage(Team.BLUE.getdisplay() + ChatColor.AQUA + " Team Wins!");
			map.celebrate();
		} else {
			map.sendMessage(ChatColor.RED + "" + blue + ChatColor.BLUE + " Remain on Blue team.");
			map.sendMessage(ChatColor.RED + "" + orange + ChatColor.GOLD + " Remain on Orange team.");
		}
	}

}
