package com.Thebatz.Walls;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Manager {
	
	private static Location lobby;
	private static ArrayList<Maps> maps;
	
	public Manager() {
		maps = new ArrayList<>();
		
		for(int i = 0; i <= (Config.getMapAmount() - 1); i++) {
			maps.add(new Maps(i));
		}
		
		if(initiateFiles.getMapYaml().contains("Lobby")) {
			float pitch = (float) initiateFiles.getMapYaml().getDouble("Lobby.pitch");
			float yaw = (float) initiateFiles.getMapYaml().getDouble("Lobby.yaw");
			
			lobby = new Location(
				Bukkit.getWorld(initiateFiles.getMapYaml().getString("Lobby.world")),
				initiateFiles.getMapYaml().getDouble("Lobby.x"),
				initiateFiles.getMapYaml().getDouble("Lobby.y"),
				initiateFiles.getMapYaml().getDouble("Lobby.z")
			);
			lobby.setPitch(pitch);
			lobby.setYaw(yaw);
		} else {
			System.out.println("[Walls] Data file not found. Creating file...");
		}
		
	}
	
	// Lobby 
	public static void setLobbySpawn(Location spawn) { lobby = spawn; }
	public static Location getLobbySpawn() { return lobby; }
	
	// maps
	public static List<Maps> getMaps() { return maps; }
	
	public static boolean isPlaying(Player player) {
		for(Maps map : maps) {
			if(map.getPlayers().contains(player.getUniqueId())) {
				return true;
			}
		}
		
		return false;
	}
	
	public static Maps getMap(Player player) {
		for (Maps map : maps) {
			if(map.getPlayers().contains(player.getUniqueId())) {
				return map;
			}
		}
		
		return null;
	}
	
	public static Maps getMap(int id) {
		for(Maps map : maps) {
			if (map.getID() == id) {
				return map;
			}
		}
		
		return null;
	}
	
	public static boolean isRecruiting(int id) { return getMap(id).getState() == GameState.RECRUITING; }
	
}
