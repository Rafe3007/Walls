package com.Thebatz.Walls.Commands;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Thebatz.Walls.Config;
import com.Thebatz.Walls.Main;
import com.Thebatz.Walls.Manager;
import com.Thebatz.Walls.Maps;
import com.Thebatz.Walls.initiateFiles;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;

public class WallsCommand implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(args.length == 1 && args[0].equalsIgnoreCase("help")) {
				player.sendMessage(ChatColor.AQUA + "------------------");
				if(player.hasPermission("walls.admin")) {
					player.sendMessage(ChatColor.DARK_AQUA + "/walls setspawn" + ChatColor.AQUA + " Sets the Lobby spawn");
					player.sendMessage(ChatColor.DARK_AQUA + "/walls setlobby [id]" + ChatColor.AQUA + " Set a map's waiting lobby");
					player.sendMessage(ChatColor.DARK_AQUA + "/walls setSpawnA [id]" + ChatColor.AQUA + " Set spawn location of team A 'set team spawn a");
					player.sendMessage(ChatColor.DARK_AQUA + "/walls setSpawnB [id]" + ChatColor.AQUA + " Set spawn location of team B 'set team spawn b");
					player.sendMessage(ChatColor.DARK_AQUA + "/walls info [id]" + ChatColor.AQUA + " Information about a walls map");
					player.sendMessage(ChatColor.DARK_AQUA + "/walls reload" + ChatColor.AQUA + " Reload config file");
				} 
				
				if(player.hasPermission("walls.player")) {
					player.sendMessage(ChatColor.DARK_AQUA + "/walls lobby" + ChatColor.AQUA + " Teleport to lobby spawn");
					player.sendMessage(ChatColor.DARK_AQUA + "/walls list" + ChatColor.AQUA + " List available maps");
					player.sendMessage(ChatColor.DARK_AQUA + "/walls join [id]" + ChatColor.AQUA + " Join a walls game");
					player.sendMessage(ChatColor.DARK_AQUA + "/walls leave" + ChatColor.AQUA + " Leave a walls game");
				}
			}
			
			else if(args.length == 1 && args[0].equalsIgnoreCase("setspawn") ) {
				if(player.hasPermission("walls.admin")) {
					Manager.setLobbySpawn(player.getLocation());
					
					initiateFiles.getMapYaml().set("Lobby.world", player.getWorld().getName());
					initiateFiles.getMapYaml().set("Lobby.x", player.getLocation().getX());
					initiateFiles.getMapYaml().set("Lobby.y", player.getLocation().getY());
					initiateFiles.getMapYaml().set("Lobby.z", player.getLocation().getZ());
					initiateFiles.getMapYaml().set("Lobby.pitch", player.getLocation().getPitch());
					initiateFiles.getMapYaml().set("Lobby.yaw", player.getLocation().getYaw());
					try {
						initiateFiles.getMapYaml().save(initiateFiles.getData());
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					player.sendMessage(ChatColor.GREEN + "Lobby Spawn set!");
				} else {
					player.sendMessage(ChatColor.RED + "You don't have permission to use this");
				}
			}
			
			else if(args.length == 2 && args[0].equalsIgnoreCase("setlobby")) {
				if(player.hasPermission("walls.admin")) {
					try {
						int id = Integer.parseInt(args[1]);
						if (id >= 0 && id <= (Config.getMapAmount() - 1)) {
							Manager.getMap(id).setLobby(player.getLocation());
							initiateFiles.writeLobbyLocation(player, id);
							player.sendMessage(ChatColor.GREEN + "Map lobby Successfully set");
						} else {
							player.sendMessage(ChatColor.RED + "Invalid usage. - /walls setlobby [id]");
						}
					} catch(NumberFormatException x) {
						player.sendMessage(ChatColor.RED + "Invalid usage. - /walls setlobby [id]");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You don't have permission to use this");
				}
			}
			
			else if(args.length == 2 && args[0].equalsIgnoreCase("setwall")) {
				if(player.hasPermission("walls.admin")) {
					try {
						int id = Integer.parseInt(args[1]);
						Region selec = null;
						BukkitPlayer worldeditplayer = Main.getInstance().getAPI().wrapPlayer(player);
						try {
							selec = Main.getInstance().getAPI().getSession(player).getSelection(worldeditplayer.getWorld());
						} catch (IncompleteRegionException e) {
							e.printStackTrace();
						}
						if(selec == null) {
							player.sendMessage(ChatColor.RED + "You don't have anything selected!");
						} else {
							CuboidRegion cS = new CuboidRegion(worldeditplayer.getWorld(), selec.getMinimumPoint(), selec.getMaximumPoint());
							initiateFiles.writeWallData(cS, id);
							player.sendMessage(ChatColor.GREEN + "Wall region has been set!");
						}
					} catch(NumberFormatException x) {
						player.sendMessage(ChatColor.RED + "Invalid usage. - /walls setwall [id]");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You don't have permission to use this");
				}
			}
			
			else if(args.length == 2 && args[0].equalsIgnoreCase("setspawnb")) {
				if(player.hasPermission("walls.admin")) {
					try {
						int id = Integer.parseInt(args[1]);
						if (id >= 0 && id <= (Config.getMapAmount() - 1)) {
							Manager.getMap(id).setTeamspawn1(player.getLocation());
							initiateFiles.writeTSA(player, id);
							player.sendMessage(ChatColor.GREEN + "Map Spawn B Successfully set");
						} else {
							player.sendMessage(ChatColor.RED + "Invalid usage. - /walls stsa [id]");
						}
					} catch(NumberFormatException x) {
						player.sendMessage(ChatColor.RED + "Invalid usage. - /walls stsa [id]");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You don't have permission to use this");
				}
			}
			
			else if(args.length == 2 && args[0].equalsIgnoreCase("setspawna")) {
				if(player.hasPermission("walls.admin")) {
					try {
						int id = Integer.parseInt(args[1]);
						if (id >= 0 && id <= (Config.getMapAmount() - 1)) {
							Manager.getMap(id).setTeamspawn2(player.getLocation());
							initiateFiles.writeTSB(player, id);
							player.sendMessage(ChatColor.GREEN + "Map Spawn A Successfully set");
						} else {
							player.sendMessage(ChatColor.RED + "Invalid usage. - /walls stsb [id]");
						}
					} catch(NumberFormatException x) {
						player.sendMessage(ChatColor.RED + "Invalid usage. - /walls stsb [id]");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You don't have permission to use this");
				}
			}
			
		
			
			// TODO Edit Message
			else if(args.length == 2 && args[0].equalsIgnoreCase("info")) {
				if(player.hasPermission("walls.admin")) {
					try {
						player.sendMessage(ChatColor.AQUA + "------[Walls info]------");
						int id = Integer.parseInt(args[1]);
						if (id >= 0 && id <= (Config.getMapAmount() - 1)) {
							player.sendMessage(ChatColor.DARK_AQUA + "Map ID: " + Manager.getMap(id).getID());
							player.sendMessage(ChatColor.DARK_AQUA + "Map name: " + Manager.getMap(id).getName());
							player.sendMessage(ChatColor.DARK_AQUA + "Lobby: world=" + ChatColor.WHITE + Manager.getMap(id).getMapLobby().getWorld().getName() +
								    ChatColor.DARK_AQUA + "  x=" + ChatColor.WHITE + Manager.getMap(id).getMapLobby().getBlockX() + 
									ChatColor.DARK_AQUA + "  y=" + ChatColor.WHITE + Manager.getMap(id).getMapLobby().getBlockY() + 
									ChatColor.DARK_AQUA + "  z=" + ChatColor.WHITE + Manager.getMap(id).getMapLobby().getBlockZ());
							player.sendMessage(ChatColor.DARK_AQUA + "Spawn A: world=" + ChatColor.WHITE + Manager.getMap(id).getTeamspawn1().getWorld().getName() +
									ChatColor.DARK_AQUA + "  x=" + ChatColor.WHITE + Manager.getMap(id).getTeamspawn1().getBlockX() + 
									ChatColor.DARK_AQUA + "  y=" + ChatColor.WHITE + Manager.getMap(id).getTeamspawn1().getBlockY() + 
									ChatColor.DARK_AQUA + "  z=" + ChatColor.WHITE + Manager.getMap(id).getTeamspawn1().getBlockZ());
							player.sendMessage(ChatColor.DARK_AQUA + "Spawn B: world=" + ChatColor.WHITE + Manager.getMap(id).getTeamspawn2().getWorld().getName() +
									ChatColor.DARK_AQUA + "  x=" + ChatColor.WHITE + Manager.getMap(id).getTeamspawn2().getBlockX() + 
									ChatColor.DARK_AQUA + "  y=" + ChatColor.WHITE + Manager.getMap(id).getTeamspawn2().getBlockY() + 
									ChatColor.DARK_AQUA + "  z=" + ChatColor.WHITE + Manager.getMap(id).getTeamspawn2().getBlockZ());
							player.sendMessage("");
						} else {
							player.sendMessage(ChatColor.RED + "Invalid map ID - /walls info [id]");
						}
					} catch(NumberFormatException x) {
						player.sendMessage(ChatColor.RED + "Invalid map ID - /walls info [id]");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You don't have permission to use this");
				}
			}
			
			else if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
				if(player.hasPermission("walls.admin")) {
					Main.getInstance().getConfig().options().copyDefaults();
					Main.getInstance().saveDefaultConfig();
					player.sendMessage(ChatColor.GREEN + "Walls config reloaded!");
				} else {
					player.sendMessage(ChatColor.RED + "You don't have permission to use this");
				}
			}
			
			// -------------------------------------[PLAYER COMMANDS]-----------------------------------------------
			
			else if(args.length == 1 && args[0].equalsIgnoreCase("lobby")) {
				if(player.hasPermission("walls.player")) {
					if(Manager.getLobbySpawn() != null) {
						player.teleport(Manager.getLobbySpawn());
					} else {
						player.sendMessage(ChatColor.RED + "Error: Lobby spawn not set or not valid");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You don't have permission to use this");
				}
			}
			
			else if(args.length == 1 && args[0].equalsIgnoreCase("list")) {
				if(player.hasPermission("walls.player")) {
					player.sendMessage(ChatColor.AQUA + "These are the available Walls Maps:" + ChatColor.GRAY + " [Map] | [ID]");
					
					for(Maps maps : Manager.getMaps()) {
						player.sendMessage(ChatColor.DARK_AQUA + " " + maps.getName() + " | " + maps.getID());
					}
				} else {
					player.sendMessage(ChatColor.RED + "You don't have permission to use this");
				}
			} 
			
			else if(args.length == 1 && args[0].equalsIgnoreCase("leave")) {
				if(player.hasPermission("walls.player")) {
					if(Manager.isPlaying(player)) {
						Manager.getMap(player).removePlayer(player); 
						
						player.sendMessage(ChatColor.GREEN + "You have left the game.");
					} else {
						player.sendMessage(ChatColor.RED + "You are not in an Walls game!");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You don't have permission to use this");
				}
			} 
			
			else if(args.length == 2 && args[0].equalsIgnoreCase("join")) {
				if(player.hasPermission("walls.player")) {
					try {
						int id = Integer.parseInt(args[1]);
						if (id >= 0 && id <= (Config.getMapAmount() - 1)) {
							if (Manager.isRecruiting(id)) {
								Manager.getMap(id).addPlayer(player);
								
								player.sendMessage(ChatColor.GREEN + "You have joined the Walls map " + Manager.getMap(id).getName() + "!");
							} else {
								player.sendMessage(ChatColor.RED + "Cannot join this game right now...");
							}
						} else {
							player.sendMessage(ChatColor.RED + "Invalid map! See '/walls list for valid maps");
						}
					} catch(NumberFormatException x) {
						player.sendMessage(ChatColor.RED + "Invalid map! See '/walls list' for valid maps");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You don't have permission to use this");
				}
			}
			
			
			
			else {
				player.sendMessage(ChatColor.RED + "Invalid command. Type '/Walls Help' for more info");
			}
			
		} else {
			System.out.println(ChatColor.DARK_RED + "[ERROR] " + ChatColor.RED + "You can't use this command from the console");
		}
		
		return false; 
	}

}
