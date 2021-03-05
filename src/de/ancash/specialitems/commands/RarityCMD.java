package de.ancash.specialitems.commands;

import java.util.ArrayList;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.ancash.specialitems.Files;
import de.ancash.specialitems.SpecialItems;

public class RarityCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if(p.hasPermission(Files.cfg.getString("commands.strength.permission"))) {
				
				if(args.length == 1) {
					
					if(p.getItemInHand().getType() != Material.AIR) {
					
						if(args[0].equalsIgnoreCase("common") || args[0].equalsIgnoreCase("uncommon") || args[0].equalsIgnoreCase("rare") || args[0].equalsIgnoreCase("epic") || args[0].equalsIgnoreCase("legendary")) {
							ItemStack is = p.getItemInHand();
							ItemMeta im = is.getItemMeta();
							String rarity = "";
							String rarity_color = "";
							if(args[0].equalsIgnoreCase("common")) {
								rarity = SpecialItems.COMMON;
								rarity_color = "§l§f";
							} else
								if(args[0].equalsIgnoreCase("uncommon")) {
									rarity = SpecialItems.UNCOMMON;
									rarity_color = "§l§a";
								} else
									if(args[0].equalsIgnoreCase("rare")) {
										rarity = SpecialItems.RARE;
										rarity_color = "§l§9";
									} else
										if(args[0].equalsIgnoreCase("epic")) {
											rarity = SpecialItems.EPIC;
											rarity_color = "§l§5";
										} else
											if(args[0].equalsIgnoreCase("legendary")) {
												rarity = SpecialItems.LEGENDARY;
												rarity_color = "§l§6";
											} else {
												p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.setrarity.usage")));
												return true;
											}
							String displayname = is.getType().name().replace("_", " ");
							if(im.getDisplayName() != null) {
								displayname = im.getDisplayName();
							}
							displayname = displayname.toLowerCase();
							displayname = displayname.replace("§f", "");
							displayname = displayname.replace("§a", "");
							displayname = displayname.replace("§9", "");
							displayname = displayname.replace("§5", "");
							displayname = displayname.replace("§6", "");
							displayname = displayname.replace("§l", "");
							displayname = WordUtils.capitalize(displayname);
							im.setDisplayName(rarity_color+displayname);
							is.setItemMeta(im);
							if(im.getLore() == null || !im.getLore().contains(rarity)) {
								is = removeRarityLore(is);
								is = addRarityLore(is, rarity);
							} else
								p.sendMessage("§aThe item in your hand is already "+rarity);
							return true;
						}
					} else
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.setrarity.usage")));
						return true;
				} else
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.setrarity.usage")));
					return true;
			} else
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("no_permission")));
				return true;
		}else
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("no_player")));
		return false;
	}

	public static ItemStack removeRarityLore(ItemStack is) {
		
		ItemMeta im = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		ArrayList<String> new_lore = new ArrayList<String>();
		if(im.getLore() != null) {
			lore = (ArrayList<String>) im.getLore();
			for(String str : lore) {
				if(!str.equals(SpecialItems.COMMON) && !str.equals(SpecialItems.UNCOMMON) && !str.equals(SpecialItems.RARE) && !str.equals(SpecialItems.EPIC) && !str.equals(SpecialItems.LEGENDARY) ) {
					if(!str.equals("         ")) {
						new_lore.add(str);
					}
				} 
			}
			im.setLore(new_lore);
			is.setItemMeta(im);
			return is;
		} else
			return is;
		
	}
	public static ItemStack addRarityLore(ItemStack is, String rarity) {
		ArrayList<String> lore = new ArrayList<String>();
		ArrayList<String> new_lore = new ArrayList<String>();
		ItemMeta im = is.getItemMeta();
		if(im.getLore() != null) {
			lore = (ArrayList<String>) im.getLore();
			for(String str : lore) {
				new_lore.add(str);
			}
			new_lore.add("         ");
			new_lore.add(rarity);
			im.setLore(new_lore);
			is.setItemMeta(im);
			return is;

		} else
			new_lore.add("         ");
			new_lore.add(rarity);
			im.setLore(new_lore);
			is.setItemMeta(im);
			return is;
	}
}
