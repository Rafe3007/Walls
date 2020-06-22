package com.Thebatz.Walls.Countdowns;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.Thebatz.Walls.Main;
import com.Thebatz.Walls.Maps;

public class Celebrate extends BukkitRunnable{
	
	private Maps map;
	private int seconds;
	
	public Celebrate(Maps map) {
		this.map = map;
		this.seconds = 10;
	}
	
	public void begin() {
			this.runTaskTimer(Main.getInstance(), 0L, 20); // every minute
	}
	
	@Override
	public void run() {
		if(seconds == 0) {
			cancel();
			map.reset();
			return;
		}
			
		for(UUID uuid : map.getPlayers()) {
			Location winners = Bukkit.getPlayer(uuid).getLocation();
			if(!Bukkit.getPlayer(uuid).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
				for(int i = 0; i <360; i+=5){
					Location loc = winners;
					loc.setZ(loc.getZ() + Math.cos(i)*5);
					loc.setX(loc.getX() + Math.sin(i)*5);
					loc.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, loc, 1);
				}
			}
		}
		
		seconds--;
	}

}
