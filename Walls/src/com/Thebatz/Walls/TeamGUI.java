package com.Thebatz.Walls;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeamGUI {
	
	public TeamGUI(Player player) {
		Inventory gui = Bukkit.createInventory(null,  9, ChatColor.DARK_GRAY + "Team Selection");
		
		for(Team team : Team.values()) {
			ItemStack is = new ItemStack(team.getMaterial());
			ItemMeta isMeta = is.getItemMeta();
			isMeta.setDisplayName(team.getdisplay());
			isMeta.setLocalizedName(team.name());
			is.setItemMeta(isMeta);
			
			gui.addItem(is);
		}
		
		player.openInventory(gui);
	}

}
