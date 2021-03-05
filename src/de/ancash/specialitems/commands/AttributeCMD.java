package de.ancash.specialitems.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.ancash.specialitems.Files;

public class AttributeCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
			if(!(sender instanceof Player)) {
									
					if(args.length == 4) {
						
						Player target = Bukkit.getPlayer(args[1]);
						if(!args[0].equals("add") && !args[0].equals("remove")) {
							System.out.println("no add/remove");
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.attribute.usage")));
							return true;
						}
						if(!target.isOnline()) {
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("player_is_offline").replace("%player%", args[1])));
						}
						if(!args[2].equals("strength") 
								&& !args[2].equals("crit_chance")
								&& !args[2].equals("crit_damage")
								&& !args[2].equals("health")
								&& !args[2].equals("mana")
								&& !args[2].equals("defense")) {
							System.out.println("no attribute");
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.attribute.usage")));
							return true;
						}
						
						File playerData = new File("plugins/SpecialItems/playerData/"+target.getUniqueId().toString()+"/data.yml");
						FileConfiguration pD = YamlConfiguration.loadConfiguration(playerData);
						
						int amount = Integer.parseInt(args[3]);
						
						if(args[0].equals("add")) {
							pD.set("stats.extra_"+args[2], pD.getInt("stats.extra_"+args[2])+amount);
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.attribute.attribute").replace("%player%", target.getDisplayName()).replace("%amount%", ""+pD.getInt("stats.extra_"+args[2])).replace("%attribute%","extra "+ args[2])));						
						} else
							if(args[0].equals("remove")) {
								if(pD.getInt("stats.extra_"+args[2]) - amount < 0) {
									sender.sendMessage("§cStats can't be less than 0");
									return true;
								}
								pD.set("stats.extra_"+args[2], pD.getInt("stats.extra_"+args[2])-amount);
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.attribute.attribute").replace("%player%", target.getDisplayName()).replace("%amount%", ""+pD.getInt("stats.extra_"+args[2])).replace("%attribute%","extra "+ args[2])));						
							} 
						try {
							pD.save(playerData);
						} catch (IOException e) {
							e.printStackTrace();
						}
						return true;
					} else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.attribute.usage")));
						return true;
					}
					

			}
			Player p = (Player) sender;
			if(p.hasPermission(Files.cfg.getString("commands.attribute.permission"))) {
								
				if(args.length == 4) {
					
					Player target = Bukkit.getPlayer(args[1]);
					if(!args[0].equals("add") && !args[0].equals("remove")) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.attribute.usage")));
						return true;
					}
					if(!target.isOnline()) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("player_is_offline").replace("%player%", args[1])));
					}
					if(!args[2].equals("strength") 
							&& !args[2].equals("crit_chance")
							&& !args[2].equals("crit_damage")
							&& !args[2].equals("health")
							&& !args[2].equals("mana")
							&& !args[2].equals("defense")) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.attribute.usage")));
						return true;
					}
					
					File playerData = new File("plugins/SpecialItems/playerData/"+target.getUniqueId().toString()+"/data.yml");
					FileConfiguration pD = YamlConfiguration.loadConfiguration(playerData);
					
					int amount = Integer.parseInt(args[3]);
					
					if(args[0].equals("add")) {
						pD.set("stats.extra_"+args[2], pD.getInt("stats.extra_"+args[2])+amount);
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.attribute.attribute").replace("%player%", target.getDisplayName()).replace("%amount%", ""+pD.getInt("stats.extra_"+args[2])).replace("%attribute%","extra "+ args[2])));						
					} else
						if(args[0].equals("remove")) {
							if(pD.getInt("stats.extra_"+args[2]) - amount < 0) {
								p.sendMessage("§cStats can't be less than 0");
								return true;
							}
							pD.set("stats.extra_"+args[2], pD.getInt("stats.extra_"+args[2])-amount);
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.attribute.attribute").replace("%player%", target.getDisplayName()).replace("%amount%", ""+pD.getInt("stats.extra_"+args[2])).replace("%attribute%","extra "+ args[2])));						
						} 
					try {
						pD.save(playerData);
					} catch (IOException e) {
						e.printStackTrace();
					}
					return true;
				} else {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.attribute.usage")));
					return true;
				}
				
			} else {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("no_permission")));
				return true;
			}
			
		
	}

}
