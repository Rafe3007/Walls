package com.Thebatz.Walls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import com.Thebatz.Walls.Countdowns.Countdown;
import com.Thebatz.Walls.Countdowns.PrepPhase;
import com.google.common.collect.TreeMultimap;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;

public class Maps {
	
	private int id;
	private String name;
	private ArrayList<UUID> players;
	private List<Block> wallMaterials;
	private HashMap<UUID, Team> teams;
	private Location waitLobby,teamSpawn1,teamSpawn2;
	private CuboidRegion wall;
	private GameState state;
	private Countdown countdown;
	private PrepPhase prepPhase;
	private Game game;
	
	private Location temp = new Location(Bukkit.getWorld("world"),0,0,0);
	
	// TODO Make checks to see if all config sections are set
	
	public Maps(int id) {
		this.id = id;
		this.name = Config.getMapName(id);
		players = new ArrayList<>();
		teams = new HashMap<>();
		
		if(initiateFiles.getMapYaml().contains("Maps." + id)) {
			float pitch = (float) initiateFiles.getMapYaml().getDouble("Maps." + id + ".lobby.pitch");
			float yaw = (float) initiateFiles.getMapYaml().getDouble("Maps." + id + ".lobby.yaw");
			waitLobby = new Location(
				Bukkit.getWorld(initiateFiles.getMapYaml().getString("Maps." + id + ".lobby.world")),
				initiateFiles.getMapYaml().getDouble("Maps." + id + ".lobby.x"),
				initiateFiles.getMapYaml().getDouble("Maps." + id + ".lobby.y"),
				initiateFiles.getMapYaml().getDouble("Maps." + id + ".lobby.z")
			);
			waitLobby.setPitch(pitch);
			waitLobby.setYaw(yaw);
			
			float pit1 = (float) initiateFiles.getMapYaml().getDouble("Maps." + id + ".Team-Spawn-A.pitch");
			float yaw1 = (float) initiateFiles.getMapYaml().getDouble("Maps." + id + ".Team-Spawn-A.yaw");
			teamSpawn1 = new Location(
				Bukkit.getWorld(initiateFiles.getMapYaml().getString("Maps." + id + ".Team-Spawn-A.world")),
				initiateFiles.getMapYaml().getDouble("Maps." + id + ".Team-Spawn-A.x"),
				initiateFiles.getMapYaml().getDouble("Maps." + id + ".Team-Spawn-A.y"),
				initiateFiles.getMapYaml().getDouble("Maps." + id + ".Team-Spawn-A.z")
			);
			teamSpawn1.setPitch(pit1);
			teamSpawn1.setYaw(yaw1);
			
			float pitch2 = (float) initiateFiles.getMapYaml().getDouble("Maps." + id + ".Team-Spawn-B.pitch");
			float yaw2 = (float) initiateFiles.getMapYaml().getDouble("Maps." + id + ".Team-Spawn-B.yaw");
			teamSpawn2 = new Location(
				Bukkit.getWorld(initiateFiles.getMapYaml().getString("Maps." + id + ".Team-Spawn-B.world")),
				initiateFiles.getMapYaml().getDouble("Maps." + id + ".Team-Spawn-B.x"),
				initiateFiles.getMapYaml().getDouble("Maps." + id + ".Team-Spawn-B.y"),
				initiateFiles.getMapYaml().getDouble("Maps." + id + ".Team-Spawn-B.z")
			);
			teamSpawn2.setPitch(pitch2);
			teamSpawn2.setYaw(yaw2);
		
			BlockVector3 max = createBv3(initiateFiles.getMapYaml().getInt("Maps." + id + ".wall-region.max.x"),
					initiateFiles.getMapYaml().getInt("Maps." + id + ".wall-region.max.y"),
					initiateFiles.getMapYaml().getInt("Maps." + id + ".wall-region.max.z"));
			BlockVector3 min = createBv3(initiateFiles.getMapYaml().getInt("Maps." + id + ".wall-region.min.x"),
					initiateFiles.getMapYaml().getInt("Maps." + id + ".wall-region.min.y"),
					initiateFiles.getMapYaml().getInt("Maps." + id + ".wall-region.min.z"));
			World world = BukkitAdapter.adapt(Bukkit.getWorld(initiateFiles.getMapYaml().getString("Maps." + id + ".wall-region.world")));
			wall = new CuboidRegion(world,max,min);
		} else {
			waitLobby = temp;
			teamSpawn1 = temp;
			teamSpawn2 = temp;
		}
		
		wallMaterials = new ArrayList<Block>();;
			
		state = GameState.RECRUITING;
		countdown= new Countdown(this);
		prepPhase = new PrepPhase(this);
		game = new Game(this);
	}
	
	public void start() {
		tpPlayers();
		game.start();
		prepPhase.begin();
	}
	
	public void battle() {
		game.battle();
	}
	
	public void reset() {
		for (UUID uuid : players) {
			Bukkit.getPlayer(uuid).teleport(Manager.getLobbySpawn());
		}
		
		state = GameState.RECRUITING;
		players.clear();
		teams.clear();
		countdown = new Countdown(this);
		prepPhase = new PrepPhase(this);
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
		
		TreeMultimap<Integer, Team> count = TreeMultimap.create();
		for (Team team : Team.values()) {
			count.put(getTeamCount(team), team);
		}
		
		Team selected = (Team) count.values().toArray()[0];
		setTeam(player, selected);
		
		player.sendMessage(ChatColor.GRAY + "You were placed on the " + selected.getdisplay() + ChatColor.GRAY + " team!");
		
		if(players.size() >= Config.getMinPlayers()) {
			countdown.begin();
		}
	}
	
	public void removePlayer(Player player) {
		players.remove(player.getUniqueId());
		player.teleport(Manager.getLobbySpawn());
		spectatorOff(player);
		
		removeTeam(player);
		
		if (players.size() <= Config.getMinPlayers()) {
			if(state.equals(GameState.COUNTDOWN)) {
				reset();
			}
		}
		
		if(players.size() == 0 && state.equals(GameState.LIVE)) {
			reset();
		}
	}
	
	public void playSound(Sound sound, float volume, float pitch) {
		for (UUID uuid : players) {
			Player player = Bukkit.getPlayer(uuid);
			player.playSound(player.getLocation(), sound, volume, pitch);
		}
	}
	
	public boolean isOnTeam(Player player, Team team) {
		if(players.contains(player.getUniqueId())) {
			if(teams.containsKey(player.getUniqueId())) {
				if(teams.get(player.getUniqueId()) == team) {
					return true; 
				}
			}
		} 
		return false;
	}
	
	public void tpPlayers() {
		Player player;
		for(UUID uuid : players) {
			player = Bukkit.getPlayer(uuid);
			if(isOnTeam(player, Team.BLUE)) {
				player.teleport(teamSpawn1);
			} else if(isOnTeam(player, Team.ORANGE)) {
				player.teleport(teamSpawn2);
			} else {
				removePlayer(player);
				player.sendMessage(ChatColor.RED + "Error: Honestly don't know what went wrong");
			}
		}
	}
	
	public void spectatorOn(Player player) {
		PotionEffect potion = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false);
		player.setGameMode(GameMode.ADVENTURE);
		player.setAllowFlight(true);
		player.setFlying(true);
		player.setInvulnerable(true);
		BukkitScheduler scheduler = Main.getInstance().getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                player.addPotionEffect(potion);
            }
        }, 20L);
	}
	
	public void spectatorOff(Player player) {
		player.setGameMode(GameMode.SURVIVAL);
		player.setFlying(false);
		player.setAllowFlight(false);
		player.setInvulnerable(false);
		player.removePotionEffect(PotionEffectType.INVISIBILITY);
	}
	
	// returns new BlockVector3
	public BlockVector3 createBv3(int x, int y, int z) {
		return BlockVector3.at(x, y, z);
	}
	
	public int getID() { return id; }
	public List<UUID> getPlayers() { return players; }
	public GameState getState() { return state; } 
	public Game getGame() { return game; }
	public String getName() { return name; }
	public Location getTeamspawn1() { return teamSpawn1; }
	public Location getTeamspawn2() { return teamSpawn2; }
	public Location getMapLobby() { return waitLobby; }
	public CuboidRegion getWallRegion() { return wall; }
	public List<Block> getWall() { 
		org.bukkit.World w = Config.getMapWorld(id);
		
		for (BlockVector3 block : wall) {
			Block b = w.getBlockAt(block.getBlockX(), block.getBlockY(), block.getBlockZ());
			wallMaterials.add(b);
		}
	
		return wallMaterials; 
	}
	
	public void setState(GameState state) { this.state = state; }
	public void setTeamspawn1(Location loc) { this.teamSpawn1 = loc; }
	public void setTeamspawn2(Location loc) { this.teamSpawn2 = loc; }
	public void setLobby(Location loc) { this.waitLobby = loc; }
	
	public Team getTeam(Player player) { return teams.get(player.getUniqueId()); }
	public void setTeam(Player player, Team team) {
		removeTeam(player);
		teams.put(player.getUniqueId(), team);
	}
	public void removeTeam(Player player) {
		if(teams.containsKey(player.getUniqueId())) {
			teams.remove(player.getUniqueId());
		}
	}
	public int getTeamCount(Team team) {
		int amount = 0;
		for (Team t : teams.values()) {
			if (t.equals(team)) {
				amount++;
			}
		}
		
		return amount;
	}
	
}
