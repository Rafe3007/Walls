package com.Thebatz.Walls;

import org.bukkit.Material;
import org.bukkit.ChatColor;

public enum Team {

	BLUE(ChatColor.BLUE + "Blue", Material.BLUE_WOOL, ChatColor.BLUE),
	ORANGE(ChatColor.GOLD + "Orange", Material.ORANGE_WOOL, ChatColor.GOLD);
	
	private String display;
	private Material material;
	private ChatColor color;
	
	
	private Team(String display, Material material, ChatColor color) {
		this.display = display;
		this.material = material;
		this.color = color;
	}
	
	public String getdisplay() { return display; }
	public Material getMaterial() { return material; }
	public ChatColor getColor() { return color; }
}
