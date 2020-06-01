package com.Thebatz.Walls;

import org.bukkit.Material;
import org.bukkit.ChatColor;

public enum Team {

	BLUE(ChatColor.BLUE + "Blue", Material.BLUE_WOOL),
	ORANGE(ChatColor.GOLD + "Orange", Material.ORANGE_WOOL);
	
	private String display;
	private Material material;
	
	private Team(String display, Material material) {
		this.display = display;
		this.material = material;
	}
	
	public String getdisplay() { return display; }
	public Material getMaterial() { return material; }
}
