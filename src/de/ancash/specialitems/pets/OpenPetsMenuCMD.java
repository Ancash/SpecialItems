package de.ancash.specialitems.pets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.ancash.specialitems.Files;
import de.tr7zw.nbtapi.NBTItem;
import net.md_5.bungee.api.ChatColor;

public class OpenPetsMenuCMD implements CommandExecutor {
		
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if(p.hasPermission(Files.cfg.getString("commands.pets.permission"))) {
				if(args.length != 0) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.pets.usage")));
					return true;
				}
				Inventory petInv = Bukkit.createInventory(null, 6*9, "Pets");
				ItemStack gray = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)7);
				ItemMeta stained_glass_meta = gray.getItemMeta();
				stained_glass_meta.setDisplayName(" ");
				gray.setItemMeta(stained_glass_meta);
				//4 = yellow
				//5 = green
				//7 = gray
				//15 = black
				for(int slot = 0; slot < 54; slot++) {
					petInv.setItem(slot, gray);
				}
				for(int i = 0; i < 7; i++) {
					petInv.setItem(10+i, null);
					petInv.setItem(19+i, null);
					petInv.setItem(28+i, null);
					petInv.setItem(37+i, null);
				}
				ItemStack close = new ItemStack(Material.BARRIER);
				ItemMeta closemeta = close.getItemMeta();
				closemeta.setDisplayName("§cClose");
				close.setItemMeta(closemeta);
				petInv.setItem(49, close);
				
				ItemStack convertPetToItem = new ItemStack(Material.INK_SACK, 1 , (byte) 8);
				ItemMeta cPTI = convertPetToItem.getItemMeta();
				cPTI.setDisplayName("§cConvert Pet to Item");
				convertPetToItem.setItemMeta(cPTI);
				petInv.setItem(50, convertPetToItem);
				
				File playerData = new File("plugins/SpecialItems/playerData/"+p.getUniqueId().toString() + "/data.yml");
				FileConfiguration pD = YamlConfiguration.loadConfiguration(playerData);
				try {
					pD.load(playerData);
				} catch (IOException | InvalidConfigurationException e) {
					e.printStackTrace();
				}
				//pD.set("pets.1", Main.phoenix);
				ArrayList<ItemStack> pets = new ArrayList<ItemStack>();
				for(int i = 1; i<29; i++) {
					if(pD.getItemStack("pets."+i) != null) {
						ItemStack is = pD.getItemStack("pets."+i).clone();
						is = PetMethods.setTexture(is, pD.getString(i+".texture"));
						ItemMeta im = is.getItemMeta();
						ArrayList<String> lore = new ArrayList<String>();
						for(String str : Files.petsConfig.getStringList("pets."+pD.getString(i+".type")+"."+pD.getString(i+".rarity")+".lore")) {
							lore.add(ChatColor.translateAlternateColorCodes('&', str));
						}
						im.setLore(lore);
						is.setItemMeta(im);
						NBTItem nbtitem = new NBTItem(is);
						nbtitem.setDouble("xp", pD.getDouble(i+".xp"));
						nbtitem.setString("pet", pD.getString(i+".type"));
						nbtitem.setString("rarity", pD.getString(i+".rarity"));
						pets.add(nbtitem.getItem());
					}
				}
				for(ItemStack is : pets) {
					is = PetMethods.preparePet(is, p);
					if(petInv.contains(is)) {
						ItemMeta im = is.getItemMeta();
						im.setDisplayName(im.getDisplayName()+"¢");
						is.setItemMeta(im);
						petInv.addItem(is);
						im.setDisplayName(im.getDisplayName().replace("¢", ""));
						is.setItemMeta(im);
					} else {
						petInv.addItem(is.clone());
					}
				}
				
				p.openInventory(petInv);
				return true;
			} else
				p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("no_permission")));
				return true;
		} else
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("no_player")));
		
		return false;
	}
}
