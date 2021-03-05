package de.ancash.specialitems.utils;

import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;

public class NBTUtils {

	public static ItemStack setString(ItemStack is, String key, String value) {
		NBTItem nbt = new NBTItem(is);
		nbt.setString(key, value);
		return nbt.getItem();
	}
	
	public static ItemStack setDouble(ItemStack is, String key, double value) {
		NBTItem nbt = new NBTItem(is);
		nbt.setDouble(key, value);
		return nbt.getItem();
	}
	
	public static ItemStack setInt(ItemStack is, String key, int value) {
		NBTItem nbt = new NBTItem(is);
		nbt.setInteger(key, value);
		return nbt.getItem();
	}
	
	
	
	public static String getString(ItemStack is, String key) {
		return new NBTItem(is).getString(key);
	}
	
	public static double getDouble(ItemStack is, String key) {
		return new NBTItem(is).getDouble(key);
	}
	
	public static int getInt(ItemStack is, String key) {
		return new NBTItem(is).getInteger(key);
	}
}
