package com.Thebatz.Walls.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Thebatz.Walls.Config;

public class TestCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		
		Player player = (Player) sender;
		
		if(cmd.getName().equals("test")) {
			player.sendMessage(ChatColor.AQUA + "--------=[Walls]=--------");
			player.sendMessage(ChatColor.DARK_AQUA + "Min Players: " + Config.getMinPlayers());
			player.sendMessage(ChatColor.DARK_AQUA + "Max Players: " + Config.getMaxPlayers());
			player.sendMessage(ChatColor.DARK_AQUA + "Lobby Countdown: " + Config.getLobbyCountdown());
			player.sendMessage(ChatColor.DARK_AQUA + "Prep Phase: " + Config.getPrepTime());
			player.sendMessage(ChatColor.DARK_AQUA + "# of Maps: " + Config.getMapAmount());
			player.sendMessage(ChatColor.DARK_AQUA + "Battle Time: " + Config.getBattleTime());
			player.sendMessage(ChatColor.DARK_AQUA + "Battle Time: " + Config.getBattleTime());
		}
		
		return false;
	}
	

}
