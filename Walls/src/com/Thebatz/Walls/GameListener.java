package com.Thebatz.Walls;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.world.WorldLoadEvent;

public class GameListener implements Listener {
	
	@EventHandler
	public void onWorldLoad(WorldLoadEvent e) {
		
		if(Manager.isMapWorld(e.getWorld())) {
			Manager.getMap(e.getWorld()).setJoinState(true);
		}
		
	}
	
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
	
	@EventHandler
	public void onKill(PlayerDeathEvent e) {
		Player player = (Player) e.getEntity();
		if(player.getKiller() instanceof Player) {
			Player killer = (Player) e.getEntity().getKiller();
			if(Manager.isPlaying(player) && Manager.getMap(player).getState().equals(GameState.LIVE)) {
				String killerr = Manager.getMap(killer).getTeam(killer).getColor() + player.getKiller().getName();
				String killed = Manager.getMap(player).getTeam(player).getColor() + player.getName();
				Manager.getMap(player).sendMessage(killed + ChatColor.RED + " Was killed by " + killerr);
				player.spigot().respawn();
				Manager.getMap(player).getGame().addKill(player);
			}
		} else if(Manager.isPlaying(player) && Manager.getMap(player).getState().equals(GameState.LIVE)) {
			if(Manager.getMap(player).isOnTeam(player, Team.BLUE)) {
				Manager.getMap(player).getGame().subBlueAmt();
			} else if(Manager.getMap(player).isOnTeam(player, Team.ORANGE)) {
				Manager.getMap(player).getGame().subOrangeAmt();
			}
			player.spigot().respawn();
		}
	}
	
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
