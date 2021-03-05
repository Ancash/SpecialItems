package de.ancash.specialitems.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import de.ancash.specialitems.Files;

public class AddDamageCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if(p.hasPermission(Files.cfg.getString("commands.damage.permission"))) {
				
				if(args.length == 1) {

					String s = args[0];
					int damage = 0;
					damage = Integer.parseInt(s);
					if(p.getItemInHand().getType() == Material.AIR) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.damage.usage")));
						return true;
					}
					if(p.getItemInHand().getItemMeta().getLore() == null) {
						ItemMeta im = p.getItemInHand().getItemMeta();
						ArrayList<String> lore = new ArrayList<>();
						lore.add("§7"+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.damage.name"))+": §c+"+damage);
						im.setLore(lore);
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.damage.damage").replace("[damage]", args[0])));
						p.getItemInHand().setItemMeta(im);
						return true;
					} 
					ArrayList<String> new_lore = new ArrayList<>();
					ItemMeta im = p.getItemInHand().getItemMeta();
					for(String str : p.getItemInHand().getItemMeta().getLore()) {
						if(!str.contains("§7"+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.damage.name"))+": §c+")) {
							new_lore.add(str);
						} else
							new_lore.add("§7"+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.damage.name"))+": §c+"+damage);
					}
					String dmg = "§7"+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.damage.name"))+": §c+"+damage;
					if(!new_lore.contains(dmg)) {
						new_lore.add(dmg);
					}
					im.setLore(new_lore);
					p.getItemInHand().setItemMeta(im);
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.damage.damage").replace("[damage]", args[0])));
					return true;
				} else
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.damage.usage")));
			} else
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("no_permission")));
				return true;
		} else
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("no_player")));
		return false;
	}
}
