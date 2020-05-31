package com.Thebatz.Walls;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameListener implements Listener {
	
	@EventHandler
	public void onInventoryclick(InventoryClickEvent e) {
		
		Player player = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem() != null && e.getView().getTitle().contains("Team Selection") && e.getRawSlot() < 54) {
			Team team = Team.valueOf(e.getCurrentItem().getItemMeta().getLocalizedName());
			
			if (!Manager.getMap(player).getTeam(player).equals(team)) {
				player.sendMessage(ChatColor.GRAY + "You are now on " + team.getdisplay() + ChatColor.GRAY + " team!");
				Manager.getMap(player).setTeam(player, team);
			} else {
				player.sendMessage(ChatColor.GRAY + "You are already on " + team.getdisplay() + ChatColor.GRAY + " team!");
			}
			
			e.setCancelled(true); // What does dis do? s
			player.closeInventory();
		}
	}
	
	// TODO Change chat to team colors
	@EventHandler
	public void onKill(PlayerDeathEvent e) {
		Player player = e.getEntity();
		if(player.getKiller() instanceof Player) {
			if(Manager.isPlaying(player) && Manager.getMap(player).getState().equals(GameState.LIVE)) {
				String killer = player.getKiller().getName();
				String killed = player.getName();
				Manager.getMap(player).sendMessage(ChatColor.RED + killed + " Was kill by " + killer);
				Manager.getMap(player).getGame().addKill(player);
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		
		if(Manager.isPlaying(player)) {
			Manager.getMap(player).removePlayer(player);
		}
	}

}
