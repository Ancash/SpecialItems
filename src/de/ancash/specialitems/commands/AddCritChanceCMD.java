package de.ancash.specialitems.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.ancash.specialitems.Files;
import de.tr7zw.nbtapi.NBTItem;

public class AddCritChanceCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if(p.hasPermission(Files.cfg.getString("commands.crit_chance.permission"))) {
				
				if(args.length == 1) {
					
					String s = args[0];
					int crit_chance = 0;
					crit_chance = Integer.parseInt(s);
					if(p.getItemInHand().getType() == Material.AIR) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.crit_chance.usage")));
						return true;
					}
					ItemStack is = p.getItemInHand();
					ItemMeta im = is.getItemMeta();
					if(p.getItemInHand().getItemMeta().getLore() == null) {
						ArrayList<String> lore = new ArrayList<>();
						lore.add("§7"+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.crit_chance.name"))+": §c+"+crit_chance+"%");
						im.setLore(lore);
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.crit_chance.crit_chance").replace("[crit_chance]", args[0])));
						is.setItemMeta(im);
						NBTItem nbt = new NBTItem(is);
						nbt.setInteger("baseCritChance", crit_chance);
						p.setItemInHand(nbt.getItem());
						return true;
					} 
					ArrayList<String> new_lore = new ArrayList<>();
					for(String str : p.getItemInHand().getItemMeta().getLore()) {
						if(!str.contains("§7"+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.crit_chance.name"))+": §c+")) {
							new_lore.add(str);
						} else
							new_lore.add("§7"+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.crit_chance.name"))+": §c+"+crit_chance+"%");
					}
					String str = "§7"+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.crit_chance.name"))+": §c+"+crit_chance+"%";
					if(!new_lore.contains(str)) {
						new_lore.add(str);
					}
					im.setLore(new_lore);
					is.setItemMeta(im);
					NBTItem nbt = new NBTItem(is);
					nbt.setInteger("baseCritChance", crit_chance);
					p.setItemInHand(nbt.getItem());
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.crit_chance.crit_chance").replace("[crit_chance]", args[0])));
					return true;
				} else
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.crit_chance.usage")));
					return true;
			} else
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("no_permission")));
				return true;
		} else
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("no_player")));
		return false;
	}
}
