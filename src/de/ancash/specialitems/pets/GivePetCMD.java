package de.ancash.specialitems.pets;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.ancash.specialitems.Files;
import de.tr7zw.nbtapi.NBTItem;
import net.md_5.bungee.api.ChatColor;

public class GivePetCMD implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if(p.hasPermission(Files.cfg.getString("commands.pet.permission"))) {
				if(args.length != 2) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.pet.usage")));
					return true;
				}
				if(p.getInventory().firstEmpty() != -1) {
					String petName = "";
					
					for(int i = 0; i<args[0].replace("-", " ").split(" ").length; i++) {
						petName = petName + " " + StringUtils.capitalize(args[0].replace("-", " ").split(" ")[i]);
					}
					if(Files.petsConfig.getString("pets."+args[0].replace("-", "").toLowerCase()+".texture") == null) {
						p.sendMessage("§cCouldn'f find texture for "+StringUtils.capitalize(args[1])+" "+petName);;
						return true;
					}
					if(!args[1].equalsIgnoreCase("common") && !args[1].equalsIgnoreCase("uncommon") && !args[1].equalsIgnoreCase("rare") && !args[1].equalsIgnoreCase("epic") && !args[1].equalsIgnoreCase("legendary")) {
						p.sendMessage("§cUnknown rarity");
						return true;
					}
					if(Files.petsConfig.getStringList("pets."+args[0].replace("-", "").toLowerCase()+"."+args[1].toLowerCase()+".lore").size() == 0) {
						p.sendMessage("§cCouldn'f find lore for "+StringUtils.capitalize(args[1])+" "+petName);
						return true;
					}
					ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (byte)3);
					
					is = setDisplayName(is, petName, args[1].toLowerCase());
					ItemMeta im = is.getItemMeta();
					ArrayList<String> lore = new ArrayList<String>();
					for(String str : Files.petsConfig.getStringList("pets."+args[0].replace("-", "").toLowerCase()+"."+args[1].toLowerCase()+".lore")) {
						lore.add(ChatColor.translateAlternateColorCodes('&', str));
					}
					im = is.getItemMeta();
					im.setLore(lore);
					is.setItemMeta(im);
					is = PetMethods.preparePet(is, p);
					is = PetMethods.setTexture(is, Files.petsConfig.getString("pets."+args[0].replace("-", "").toLowerCase()+".texture"));
					
					NBTItem nbtitem = new NBTItem(is);
					//nbtitem.setString("uuid", uuid.toString());
					nbtitem.setString("pet", args[0].replace("-", "").toLowerCase());
					nbtitem.setString("rarity", args[1].toLowerCase());
					nbtitem.setDouble("xp", (double) 0);
					nbtitem.setString("texture", Files.petsConfig.getString("pets."+args[0].replace("-", "").toLowerCase()+".texture"));
					
					p.getInventory().addItem(nbtitem.getItem());
					
					return true;
					
				} else
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("inventory_full")));
					return true;
			} else
				p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("no_permission")));
				return true;
		} else
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("no_player")));
		return false;
	}
	
	private ItemStack setDisplayName(ItemStack is, String name, String rarity) {
		ItemMeta im = is.getItemMeta();
		rarity = rarity.toLowerCase();
		String prefix = "";
		//name = name.replace("§1", "").replace("§2", "").replace("§3", "").replace("§4", "").replace("§5", "").replace("§6", "").replace("§7", "").replace("§8", "").replace("§9", "").replace("§a", "").replace("§b", "").replace("§e", "").replace("§f", "").replace("§r", "").replace("§c", "");
		switch (rarity) {
		case "common":
			prefix = "§f";
			break;
		case "uncommon":
			prefix = "§a";
			break;
		case "rare":
			prefix = "§9";
			break;
		case "epic":
			prefix = "§5";
			break;
		case "legendary":
			prefix = "§6";
			break;
		default:
			break;
		}
		im.setDisplayName("§7[Lvl 1] "+ prefix + name);
		if(name.equalsIgnoreCase("enderdragon")) {
			im.setDisplayName("§7[Lvl 1] "+ prefix + "Ender Dragon");
		}
		is.setItemMeta(im);
		return is;
	}
	
}
