package com.Thebatz.Walls.Countdowns;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import com.Thebatz.Walls.Config;
import com.Thebatz.Walls.GameState;
import com.Thebatz.Walls.Main;
import com.Thebatz.Walls.Maps;

public class Countdown extends BukkitRunnable{
	
	// Lobby Countdown
	private Maps map;
	private int seconds;
	
	public Countdown(Maps map) {
		this.map = map;
		this.seconds = Config.getLobbyCountdown();
	}
	
	public void begin() {
		map.setState(GameState.COUNTDOWN);
		this.runTaskTimer(Main.getInstance(), 0L, 20);
	}

	@Override
	public void run() {
		if(seconds == 0) {
			cancel();
			map.start();
			return;
		}
		
		if (seconds % 30 == 0 || seconds <= 10) {
			if(seconds <= 5) {
				map.playSound(Sound.BLOCK_STONE_BUTTON_CLICK_ON, 10, 1);
			}
			if(seconds == 1) {
				map.sendMessage(ChatColor.AQUA + "Game will start in 1 second.");
			} else {
				map.sendMessage(ChatColor.AQUA + "Game will start in " + seconds + " seconds.");
			}
		}
		
		if (map.getPlayers().size() < Config.getMinPlayers()) {
			cancel();
			map.setState(GameState.RECRUITING);
			map.sendMessage(ChatColor.RED + "There are too few players. Countdown stopped.");
			return;
		}
		
		seconds--;
	}

}
