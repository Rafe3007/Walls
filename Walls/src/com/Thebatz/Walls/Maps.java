package com.Thebatz.Walls;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.Thebatz.Walls.Countdowns.Countdown;

public class Maps {
	
	private int id;
	private String name;
	private ArrayList<UUID> players;
//	private HashMap<UUID, Team> teams;
	private Location waitLobby,teamSpawn1,teamSpawn2;
	private GameState state;
	private Countdown countdown;
	private Game game;
	
	private Location temp = new Location(Bukkit.getWorld("world"),400,70,400);
	
	public Maps(int id) {
		this.id = id;
		this.name = Config.getMapName(id);
		players = new ArrayList<>();
//		teams = new HashMap<>();
		
		if(initiateFiles.getMapYaml().contains("Maps." + id)) {
			float pitch = (float) initiateFiles.getMapYaml().getDouble("Maps." + id + "lobby.pitch");
			float yaw = (float) initiateFiles.getMapYaml().getDouble("Maps." + id + "lobby.yaw");
			waitLobby = new Location(
				Bukkit.getWorld(initiateFiles.getMapYaml().getString("lobby.world")),
				initiateFiles.getMapYaml().getDouble("lobby.x"),
				initiateFiles.getMapYaml().getDouble("lobby.y"),
				initiateFiles.getMapYaml().getDouble("lobby.z")
			);
			waitLobby.setPitch(pitch);
			waitLobby.setYaw(yaw);
			
			float pit1 = (float) initiateFiles.getMapYaml().getDouble("Maps." + id + "tsa.pitch");
			float yaw1 = (float) initiateFiles.getMapYaml().getDouble("Maps." + id + "tsa.yaw");
			teamSpawn1 = new Location(
				Bukkit.getWorld(initiateFiles.getMapYaml().getString("tsa.world")),
				initiateFiles.getMapYaml().getDouble("tsa.x"),
				initiateFiles.getMapYaml().getDouble("tsa.y"),
				initiateFiles.getMapYaml().getDouble("tsa.z")
			);
			teamSpawn1.setPitch(pit1);
			teamSpawn1.setYaw(yaw1);
			
			float pitch2 = (float) initiateFiles.getMapYaml().getDouble("Maps." + id + "tsb.pitch");
			float yaw2 = (float) initiateFiles.getMapYaml().getDouble("Maps." + id + "tsb.yaw");
			teamSpawn2 = new Location(
				Bukkit.getWorld(initiateFiles.getMapYaml().getString("lobby.world")),
				initiateFiles.getMapYaml().getDouble("tsb.x"),
				initiateFiles.getMapYaml().getDouble("tsb.y"),
				initiateFiles.getMapYaml().getDouble("tsb.z")
			);
			waitLobby.setPitch(pitch2);
			waitLobby.setYaw(yaw2);
		} else {
			waitLobby = temp;
			teamSpawn1 = temp;
			teamSpawn2 = temp;
		}
			
		state = GameState.RECRUITING;
		countdown= new Countdown(this);
		game = new Game(this);
	}
	
	public void start() {
		game.start();
	}
	
	public void reset() {
		for (UUID uuid : players) {
			Bukkit.getPlayer(uuid).teleport(Manager.getLobbySpawn());
		}
		
		state = GameState.RECRUITING;
		players.clear();
		countdown = new Countdown(this);
		game = new Game(this);
	}
	
	public void sendMessage(String message) {
		for(UUID uuid : players) {
			Bukkit.getPlayer(uuid).sendMessage(message);
		}
	}
	
	public void addPlayer(Player player) {
		players.add(player.getUniqueId());
		player.teleport(waitLobby);
		
		if(players.size() >= Config.getMinPlayers()) {
			countdown.begin();
		}
	}
	
	public void removePlayer(Player player) {
		players.remove(player.getUniqueId());
		player.teleport(Manager.getLobbySpawn());
		
		if (players.size() <= Config.getMinPlayers()) {
			if(state.equals(GameState.COUNTDOWN)) {
				reset();
			}
		}
		
		if(players.size() == 0 && state.equals(GameState.LIVE)) {
			reset();
		}
	}
	
	public int getID() { return id; }
	public List<UUID> getPlayers() { return players; }
	public GameState getState() { return state; } 
	public Game getGame() { return game; }
	public String getName() { return name; }
	public Location getTeamspawn1() { return teamSpawn1; }
	public Location getTeamspawn2() { return teamSpawn2; }
	public Location getMapLobby() { return waitLobby; }
	
	public void setState(GameState state) { this.state = state; }
	public void setTeamspawn1(Location loc) { this.teamSpawn1 = loc; }
	public void setTeamspawn2(Location loc) { this.teamSpawn2 = loc; }
	public void setLobby(Location loc) { this.waitLobby = loc; }
	
	
}
