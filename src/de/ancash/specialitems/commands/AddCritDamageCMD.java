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

public class AddCritDamageCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if(p.hasPermission(Files.cfg.getString("commands.crit_damage.permission"))) {
				
				if(args.length == 1) {
					
					String s = args[0];
					int crit_damage = 0;
					crit_damage = Integer.parseInt(s);
					if(p.getItemInHand().getType() == Material.AIR) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.crit_damage.usage")));
						return true;
					}
					ItemStack is = p.getItemInHand();
					ItemMeta im = is.getItemMeta();
					if(p.getItemInHand().getItemMeta().getLore() == null) {
						ArrayList<String> lore = new ArrayList<>();
						lore.add("§7"+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.crit_damage.name"))+": §c+"+crit_damage+"%");
						im.setLore(lore);
						is.setItemMeta(im);
						NBTItem nbt = new NBTItem(is);
						nbt.setInteger("baseCritDamage", crit_damage);
						p.setItemInHand(nbt.getItem());
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.crit_damage.crit_damage").replace("[crit_damage]", args[0])));
						return true;
					} 
					ArrayList<String> new_lore = new ArrayList<>();
					for(String str : p.getItemInHand().getItemMeta().getLore()) {
						if(!str.contains("§7"+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.crit_damage.name"))+": §c+")) {
							new_lore.add(str);
						} else
							new_lore.add("§7"+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.crit_damage.name"))+": §c+"+crit_damage+"%");
					}
					String str = "§7"+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.crit_damage.name"))+": §c+"+crit_damage+"%";
					if(!new_lore.contains(str)) {
						new_lore.add(str);
					}
					im.setLore(new_lore);
					is.setItemMeta(im);
					NBTItem nbt = new NBTItem(is);
					nbt.setInteger("baseCritDamage", crit_damage);
					p.setItemInHand(nbt.getItem());
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.crit_damage.crit_damage").replace("[crit_damage]", args[0])));
					return true;
				} else
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.crit_damage.usage")));
					return true;
			} else
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("no_permission")));
				return true;
		} else
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("no_player")));
		return false;
	}
}
