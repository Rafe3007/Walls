package com.Thebatz.Walls;

import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

import com.Thebatz.Walls.Commands.TestCommand;
import com.Thebatz.Walls.Commands.WallsCommand;

public class Main extends JavaPlugin{
	
	private static Main instance;
	
	@Override 
	public void onEnable() {
		Main.instance = this;
		new Config(this);
		
		try {
			new initiateFiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		new Manager();
		
		getCommand("test").setExecutor(new TestCommand());
		getCommand("walls").setExecutor(new WallsCommand());
		
		
	}
	
	public static Main getInstance() { return instance; }

}
