package de.ancash.specialitems.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.ancash.specialitems.Files;
import de.ancash.specialitems.SpecialItems;
import de.ancash.specialitems.listener.Enchanting;
import net.md_5.bungee.api.ChatColor;

public class AddEnchantment implements CommandExecutor {
    
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
		
			Player p = (Player) sender;
			
			if(p.hasPermission(Files.cfg.getString("commands.addench.permission"))) {
				
				if(args.length == 2) {
					
					if(args[0].equalsIgnoreCase("firststrike")) {
						
						int level = Integer.parseInt(args[1]);
						
						if(p.getItemInHand() != null) {
							
							if(level > 0) {
								p.getItemInHand().addUnsafeEnchantment(SpecialItems.ench_firstStrike, level);
								ItemStack is = p.getItemInHand();
								is = Enchanting.removeEnchantingLore(is, false);
								is = Enchanting.addEnchantingLore(is);
								p.setItemInHand(is);
							} else
								p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("commands.addench.usage")));
								return true;	
						} else
							p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("commands.addench.usage")));
							return true;	
					} else
						if(args[0].equalsIgnoreCase("telekinesis")) {
							
							int level = Integer.parseInt(args[1]);
							
							if(p.getItemInHand() != null) {
								
								if(level > 0) {
									p.getItemInHand().addUnsafeEnchantment(SpecialItems.ench_telekinesis, level);
									ItemStack is = p.getItemInHand();
									is = Enchanting.removeEnchantingLore(is, false);
									is = Enchanting.addEnchantingLore(is);
									p.setItemInHand(is);
								} else
									p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("commands.addench.usage")));
									return true;	
							} else
								p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("commands.addench.usage")));
								return true;	
						} else
							if(args[0].equalsIgnoreCase("aiming")) {
								int level = Integer.parseInt(args[1]);
								
								if(p.getItemInHand() != null) {
									
									if(level > 0) {
										p.getItemInHand().addUnsafeEnchantment(SpecialItems.ench_aiming, level);
										ItemStack is = p.getItemInHand();
										is = Enchanting.removeEnchantingLore(is, false);
										is = Enchanting.addEnchantingLore(is);
										p.setItemInHand(is);
									} else
										p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("commands.addench.usage")));
										return true;	
								} else
									p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("commands.addench.usage")));
									return true;	
							} else 
								if(args[0].equalsIgnoreCase("snipe")) {
									int level = Integer.parseInt(args[1]);
									
									if(p.getItemInHand() != null) {
										
										if(level > 0) {
											p.getItemInHand().addUnsafeEnchantment(SpecialItems.ench_snipe, level);
											ItemStack is = p.getItemInHand();
											is = Enchanting.removeEnchantingLore(is, false);
											is = Enchanting.addEnchantingLore(is);
											p.setItemInHand(is);
										} else
											p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("commands.addench.usage")));
											return true;	
									} else
										p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("commands.addench.usage")));
										return true;	
								} else 
									if(args[0].equalsIgnoreCase("lifesteal")) {
										int level = Integer.parseInt(args[1]);
										
										if(p.getItemInHand() != null) {
											
											if(level > 0) {
												p.getItemInHand().addUnsafeEnchantment(SpecialItems.ench_lifesteal, level);
												ItemStack is = p.getItemInHand();
												is = Enchanting.removeEnchantingLore(is, false);
												is = Enchanting.addEnchantingLore(is);
												p.setItemInHand(is);
											} else
												p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("commands.addench.usage")));
												return true;	
										} else
											p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("commands.addench.usage")));
											return true;	
									} else {
										if(args[0].equalsIgnoreCase("growth")) {
											int level = Integer.parseInt(args[1]);
											
											if(p.getItemInHand() != null) {
												
												if(level > 0) {
													p.getItemInHand().addUnsafeEnchantment(SpecialItems.ench_growth, level);
													ItemStack is = p.getItemInHand();
													is = Enchanting.removeEnchantingLore(is, false);
													is = Enchanting.addEnchantingLore(is);
													p.setItemInHand(is);
												} else
													p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("commands.addench.usage")));
													return true;	
											} else
												p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("commands.addench.usage")));
												return true;	
										} else {
											
											if(args[0].equalsIgnoreCase("critical")) {
												int level = Integer.parseInt(args[1]);
												
												if(p.getItemInHand() != null) {
													
													if(level > 0) {
														p.getItemInHand().addUnsafeEnchantment(SpecialItems.ench_critical, level);
														ItemStack is = p.getItemInHand();
														is = Enchanting.removeEnchantingLore(is, false);
														is = Enchanting.addEnchantingLore(is);
														p.setItemInHand(is);
													} else
														p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("commands.addench.usage")));
														return true;	
												} else
													p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("commands.addench.usage")));
													return true;	
											} else {
												if(args[0].equalsIgnoreCase("cubism")) {
													int level = Integer.parseInt(args[1]);
													
													if(p.getItemInHand() != null) {
														
														if(level > 0) {
															p.getItemInHand().addUnsafeEnchantment(SpecialItems.ench_cubism, level);
															ItemStack is = p.getItemInHand();
															is = Enchanting.removeEnchantingLore(is, false);
															is = Enchanting.addEnchantingLore(is);
															p.setItemInHand(is);
														} else
															p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("commands.addench.usage")));
															return true;	
													} else
														p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("commands.addench.usage")));
														return true;	
												} else {
													if(args[0].equalsIgnoreCase("dragonhunter")) {
														int level = Integer.parseInt(args[1]);
														
														if(p.getItemInHand() != null) {
															
															if(level > 0) {
																p.getItemInHand().addUnsafeEnchantment(SpecialItems.ench_dragonhunter, level);
																ItemStack is = p.getItemInHand();
																is = Enchanting.removeEnchantingLore(is, false);
																is = Enchanting.addEnchantingLore(is);
																p.setItemInHand(is);
															} else
																p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("commands.addench.usage")));
																return true;	
														} else
															p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("commands.addench.usage")));
															return true;	
													} else {
														if(args[0].equalsIgnoreCase("enderslayer")) {
															int level = Integer.parseInt(args[1]);
															
															if(p.getItemInHand() != null) {
																
																if(level > 0) {
																	p.getItemInHand().addUnsafeEnchantment(SpecialItems.ench_enderslayer, level);
																	ItemStack is = p.getItemInHand();
																	is = Enchanting.removeEnchantingLore(is, false);
																	is = Enchanting.addEnchantingLore(is);
																	p.setItemInHand(is);
																} else
																	p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("commands.addench.usage")));
																	return true;	
															} else
																p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("commands.addench.usage")));
																return true;	
														} else {
															p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("commands.addench.usage")));
															return true;
														}
													}
												}
											}
										}
									}
				}else
					p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("commands.addench.usage")));
					return true;
			} else
				p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("no_permission")));
				return true;
		} else
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("no_player")));
		return false;
	}

}
