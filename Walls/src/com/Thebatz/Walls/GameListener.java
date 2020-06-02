package com.Thebatz.Walls;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

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
			
			e.setCancelled(true);
			player.closeInventory();
		}
	}
	
	// TODO Change chat to team colors
//	@EventHandler
//	public void onKill(PlayerDeathEvent e) {
//		Player player = e.getEntity();
//		if(player.getKiller() instanceof Player) {
//			if(Manager.isPlaying(player) && Manager.getMap(player).getState().equals(GameState.LIVE)) {
//				String killer = player.getKiller().getName();
//				String killed = player.getName();
//				Manager.getMap(player).sendMessage(ChatColor.RED + killed + " Was kill by " + killer);
//				Manager.getMap(player).getGame().addKill(player);
//			}
//		}
//	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		if(Manager.isPlaying(e.getPlayer()) && Manager.getMap(e.getPlayer()).getState() == GameState.LIVE) { 
			Location respawn;
			Manager.getMap(e.getPlayer()).spectatorOn(e.getPlayer());
			if(Manager.getMap(e.getPlayer()).isOnTeam(e.getPlayer(), Team.BLUE)) {
				respawn = Manager.getMap(e.getPlayer()).getTeamspawn1();
				e.setRespawnLocation(respawn);
			} else if(Manager.getMap(e.getPlayer()).isOnTeam(e.getPlayer(), Team.ORANGE)) {
				respawn = Manager.getMap(e.getPlayer()).getTeamspawn2();
				e.setRespawnLocation(respawn);
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
