package com.Thebatz.Walls.Countdowns;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import com.Thebatz.Walls.Config;
import com.Thebatz.Walls.Main;
import com.Thebatz.Walls.Maps;

public class BattlePhase extends BukkitRunnable{
	
	private Maps map;
	private int minutes;
	
	public BattlePhase(Maps map) {
		this.map = map;
		this.minutes = Config.getBattleTime();
	}
	
	public void begin() {
			this.runTaskTimer(Main.getInstance(), 0L, 1200); // every minute
	}
	
	@Override
	public void run() {
		if(minutes == 0) {
			cancel();
			map.sendMessage(ChatColor.YELLOW + "Game has ended! It's a draw");
			map.celebrate();
			return;
		}
		
		if (minutes % 5 == 0 || minutes <= 3) {
			if(minutes == 1) {
				map.sendMessage(ChatColor.AQUA + "The game will end in 1 minute.");
			} else {
				map.sendMessage(ChatColor.AQUA + "The game will end in " + minutes + " minutes.");
			}
		}
		
		
		
		minutes--;
	}

}
