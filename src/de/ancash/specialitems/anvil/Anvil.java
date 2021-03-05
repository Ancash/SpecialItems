package de.ancash.specialitems.anvil;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.ancash.specialitems.Files;
import de.tr7zw.nbtapi.NBTItem;

public class Anvil {

	private static Inventory anvinv;
	public static ItemStack anvil;
	public static Sound notenoughxp;
	public static Sound use;
	
	public static ItemStack gray;
	public static ItemStack upred;
	public static ItemStack upgreen;
	public static ItemStack sacred;
	public static ItemStack sacgreen;
	public static ItemStack error;
	public static ItemStack red;
	
	private static ItemStack addDoNotMoveTag(ItemStack is) {
		NBTItem nbt = new NBTItem(is);
		nbt.setString("donotmove", "lol");
		return nbt.getItem();
	}
	
	private static ItemStack setDisplayname(String path, ItemStack is) {
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(Files.anvilConfig.getString("items." + path + ".name").replace("&", "§"));
		is.setItemMeta(im);
		return is;
	}
	private static ItemStack setLore(String path, ItemStack is) {
		ItemMeta im = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		Files.anvilConfig.getStringList("items." + path + ".lore").forEach(str -> lore.add(str.replace("&", "§")));
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
	
	public static Inventory getAnvInv() {
		return anvinv;
	}
	
	private static void load_1_8__1_12() {
		
		notenoughxp = Sound.valueOf(Files.anvilConfig.getString("sounds.notenoughxp"));
		use = Sound.valueOf(Files.anvilConfig.getString("sounds.use"));
		
		anvinv = Bukkit.createInventory(null, 9*6, "Anvil");
		
		gray = new ItemStack(Material.getMaterial(Files.anvilConfig.getString("items.background.material")), 1, (byte)Files.anvilConfig.getInt("items.background.byte"));
		gray = setDisplayname("background", gray);
		gray = addDoNotMoveTag(gray);
		for(int slot = 0; slot < 54; slot++) {
			anvinv.setItem(slot, gray);
		}
		anvinv.setItem(13, null);
		
		ItemStack anv = new ItemStack(Material.getMaterial(Files.anvilConfig.getString("items.anvil.material")));
		anv = setDisplayname("anvil", anv);
		anv = setLore("anvil", anv);
		anv = addDoNotMoveTag(anv);
		anvinv.setItem(22, anv);
		
		anvil = anv;
		
		ItemStack close = new ItemStack(Material.getMaterial(Files.anvilConfig.getString("items.close.material")));
		close = setDisplayname("close", close);
		close = addDoNotMoveTag(close);
		anvinv.setItem(49, close);
		
		upred = new ItemStack(Material.getMaterial(Files.anvilConfig.getString("items.upgrade.material")), 1, (byte)Files.anvilConfig.getInt("items.upgrade.byte"));
		upred = setDisplayname("upgrade", upred);
		upred = setLore("upgrade", upred);
		upred = addDoNotMoveTag(upred);
		anvinv.setItem(11, upred);
		anvinv.setItem(12, upred);
		anvinv.setItem(20, upred);

		upgreen = new ItemStack(Material.getMaterial(Files.anvilConfig.getString("items.green.material")), 1, (byte)Files.anvilConfig.getInt("items.green.byte"));
		upgreen.setItemMeta(upred.getItemMeta());
		upgreen = addDoNotMoveTag(upgreen);
		
		sacred = new ItemStack(Material.getMaterial(Files.anvilConfig.getString("items.sacrifice.material")), 1, (byte)Files.anvilConfig.getInt("items.sacrifice.byte"));
		sacred = setDisplayname("sacrifice", sacred);
		sacred = setLore("sacrifice", sacred);
		sacred = addDoNotMoveTag(sacred);
		anvinv.setItem(14, sacred);
		anvinv.setItem(15, sacred);
		anvinv.setItem(24, sacred);
		
		sacgreen = new ItemStack(Material.getMaterial(Files.anvilConfig.getString("items.green.material")), 1, (byte)Files.anvilConfig.getInt("items.green.byte"));
		sacgreen.setItemMeta(sacred.getItemMeta());
		sacgreen = addDoNotMoveTag(sacgreen);
		
		error = new ItemStack(Material.getMaterial(Files.anvilConfig.getString("items.error.material")));
		error = setDisplayname("error", error);
		error = setLore("error", error);
		error = addDoNotMoveTag(error);
		anvinv.setItem(13, error);
				
		red = new ItemStack(Material.getMaterial(Files.anvilConfig.getString("items.background_red.material")), 1, (byte)Files.anvilConfig.getInt("items.background_red.byte"));
		red = setDisplayname("background_red", red);
		red = addDoNotMoveTag(red);
		anvinv.setItem(45, red);
		anvinv.setItem(46, red);
		anvinv.setItem(47, red);
		anvinv.setItem(48, red);
		anvinv.setItem(50, red);
		anvinv.setItem(51, red);
		anvinv.setItem(52, red);
		anvinv.setItem(53, red);
		
		anvinv.setItem(29, null);
		anvinv.setItem(33, null);
	}
	public static void loadAnvil() {
		
		load_1_8__1_12();
	}
	
}
