package com.Thebatz.Walls;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameListener implements Listener {
	
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
