package com.Thebatz.Walls;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.regions.CuboidRegion;

public class initiateFiles {

	private static File data;
	private static YamlConfiguration editData;
	
	public initiateFiles() throws IOException {
		initiateFiles.data = new File(Bukkit.getServer().getPluginManager().getPlugin("Walls").getDataFolder(), "Data.yml");
		if(!data.exists()) {
			data.createNewFile();
		}
		
		editData = YamlConfiguration.loadConfiguration(data);
	}
	
	public static YamlConfiguration getMapYaml() { return editData; }
	public static File getData() { return data; }
	public static void reload() { editData = YamlConfiguration.loadConfiguration(data); }
	public static void writeLobbyLocation(Player player, int id) { 
		int map = Manager.getMap(id).getID();
		editData.set("Maps." + map + ".lobby.world", player.getWorld().getName());
		editData.set("Maps." + map + ".lobby.x", player.getLocation().getX());
		editData.set("Maps." + map + ".lobby.y", player.getLocation().getY());
		editData.set("Maps." + map + ".lobby.z", player.getLocation().getZ());
		editData.set("Maps." + map + ".lobby.pitch", player.getLocation().getPitch());
		editData.set("Maps." + map + ".lobby.yaw", player.getLocation().getYaw());
		try {
			editData.save(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void writeTSA(Player player, int id) {
		int map = Manager.getMap(id).getID();
		editData.set("Maps." + map + ".Team-Spawn-A.world", player.getWorld().getName());
		editData.set("Maps." + map + ".Team-Spawn-A.x", player.getLocation().getX());
		editData.set("Maps." + map + ".Team-Spawn-A.y", player.getLocation().getY());
		editData.set("Maps." + map + ".Team-Spawn-A.z", player.getLocation().getZ());
		editData.set("Maps." + map + ".Team-Spawn-A.pitch", player.getLocation().getPitch());
		editData.set("Maps." + map + ".Team-Spawn-A.yaw", player.getLocation().getYaw());
		try {
			editData.save(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void writeTSB(Player player, int id) {
		int map = Manager.getMap(id).getID();
		editData.set("Maps." + map + ".Team-Spawn-B.world", player.getWorld().getName());
		editData.set("Maps." + map + ".Team-Spawn-B.x", player.getLocation().getX());
		editData.set("Maps." + map + ".Team-Spawn-B.y", player.getLocation().getY());
		editData.set("Maps." + map + ".Team-Spawn-B.z", player.getLocation().getZ());
		editData.set("Maps." + map + ".Team-Spawn-B.pitch", player.getLocation().getPitch());
		editData.set("Maps." + map + ".Team-Spawn-B.yaw", player.getLocation().getYaw());
		try {
			editData.save(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void writeWallData(CuboidRegion region, int id) {
		editData.set("Maps." + id + ".wall-region.world", region.getWorld().getName());
		editData.set("Maps." + id + ".wall-region.max.x", region.getMaximumPoint().getBlockX());
		editData.set("Maps." + id + ".wall-region.max.y", region.getMaximumPoint().getBlockY());
		editData.set("Maps." + id + ".wall-region.max.z", region.getMaximumPoint().getBlockZ());
		editData.set("Maps." + id + ".wall-region.min.x", region.getMinimumPoint().getBlockX());
		editData.set("Maps." + id + ".wall-region.min.y", region.getMaximumPoint().getBlockY());
		editData.set("Maps." + id + ".wall-region.min.z", region.getMaximumPoint().getBlockZ());
		try {
			editData.save(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
