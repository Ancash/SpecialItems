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

public class AddStrengthCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if(p.hasPermission(Files.cfg.getString("commands.strength.permission"))) {
				
				if(args.length == 1) {
					
					String s = args[0];
					int strength = 0;
					strength = Integer.parseInt(s);
					if(p.getItemInHand().getType() == Material.AIR) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.strength.usage")));
						return true;
					}
					ItemStack is = p.getItemInHand();
					ItemMeta im = is.getItemMeta();
					if(p.getItemInHand().getItemMeta().getLore() == null) {
						ArrayList<String> lore = new ArrayList<>();
						lore.add("§7"+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.strength.name"))+": §c+"+strength);
						im.setLore(lore);
						is.setItemMeta(im);
						NBTItem nbt = new NBTItem(is);
						nbt.setInteger("baseStrength", strength);
						p.setItemInHand(nbt.getItem());;
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.strength.strength").replace("[strength]", args[0])));
						return true;
					} 
					ArrayList<String> new_lore = new ArrayList<>();
					for(String str : p.getItemInHand().getItemMeta().getLore()) {
						if(!str.contains("§7"+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.strength.name"))+": §c+")) {
							new_lore.add(str);
						} else
							new_lore.add("§7"+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.strength.name"))+": §c+"+strength);
					}
					String str = "§7"+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.strength.name"))+": §c+"+strength;
					if(!new_lore.contains(str)) {
						new_lore.add(str);
					}
					im.setLore(new_lore);
					is.setItemMeta(im);
					NBTItem nbt = new NBTItem(is);
					nbt.setInteger("baseStrength", strength);
					p.setItemInHand(nbt.getItem());
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.strength.strength").replace("[strength]", args[0])));
					return true;
				} else
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.strength.usage")));
					return true;
			} else
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("no_permission")));
				return true;
		} else
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("no_player")));
		return false;
	}
}
