package com.Thebatz.Walls.Countdowns;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import com.Thebatz.Walls.Config;
import com.Thebatz.Walls.Main;
import com.Thebatz.Walls.Maps;

public class PrepPhase extends BukkitRunnable{
	
	private Maps map;
	private int minutes;
	
	public PrepPhase(Maps map) {
		this.map = map;
		this.minutes = Config.getPrepTime();
	}
	
	public void begin() {
			this.runTaskTimer(Main.getInstance(), 0L, 1200); // every minute
	}
	
	@Override
	public void run() {
		if(minutes == 0) {
			cancel();
			map.playSound(Sound.BLOCK_BEACON_POWER_SELECT, 10, (float) 1.5);
			map.battle();
			return;
		}
		
		if (minutes % 5 == 0 || minutes <= 3) {
			if(minutes == 1) {
				map.sendMessage(ChatColor.AQUA + "The walls will fall in 1 minute.");
			} else {
				map.sendMessage(ChatColor.AQUA + "The walls will fall in " + minutes + " minutes.");
			}
		}
		
		
		
		minutes--;
	}

}
