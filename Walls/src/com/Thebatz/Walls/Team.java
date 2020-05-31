package com.Thebatz.Walls;

import org.bukkit.Material;
import org.bukkit.ChatColor;

public enum Team {

	RED(ChatColor.RED + "Red", Material.RED_WOOL),
	BLUE(ChatColor.DARK_BLUE + "Blue", Material.BLUE_WOOL),
	LIGHT_BLUE(ChatColor.BLUE + "Light Blue", Material.LIGHT_BLUE_WOOL),
	CYAN(ChatColor.DARK_AQUA + "Cyan", Material.CYAN_WOOL),
	BLACK(ChatColor.BLACK + "Black", Material.BLACK_WOOL),
	BROWN(ChatColor.DARK_RED + "Brown", Material.BROWN_WOOL),
	LIGHT_GRAY(ChatColor.GRAY + "Dark Gray", Material.LIGHT_GRAY_WOOL),
	GREEN(ChatColor.DARK_GREEN + "Green", Material.BLUE_WOOL),
	PURPLE(ChatColor.DARK_PURPLE + "Purple", Material.PURPLE_WOOL),
	DARK_RED(ChatColor.RED + "Dark Red", Material.BLUE_WOOL),
	ORANGE(ChatColor.GOLD + "Orange", Material.ORANGE_WOOL),
	GRAY(ChatColor.DARK_GRAY + "Gray", Material.GRAY_WOOL),
	LIME(ChatColor.GREEN + "Lime", Material.LIME_WOOL),
	PINK(ChatColor.LIGHT_PURPLE + "Light Purple", Material.PINK_WOOL),
	WHITE(ChatColor.WHITE + "White", Material.WHITE_WOOL),
	YELLOW(ChatColor.YELLOW + "Yellow", Material.YELLOW_WOOL);
	
	private String display;
	private Material material;
	
	private Team(String display, Material material) {
		this.display = display;
		this.material = material;
	}
	
	public String getdisplay() { return display; }
	public Material getMaterial() { return material; }
}
