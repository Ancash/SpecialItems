package de.ancash.specialitems.reforgeUtils;

import org.bukkit.inventory.ItemStack;

import de.ancash.specialitems.utils.NBTUtils;

public class StatsUtils {

	public static int getAttribue(ItemStack is, Attribute att) {
		return NBTUtils.getInt(is, att.getName());
	}
	
	public static ItemStack setAttribute(ItemStack is, Attribute att, int value) {
		is = NBTUtils.setInt(is, att.getName(), value);
		return is;
	}

	public static ItemStack addAttribute(ItemStack is, Attribute att, int value) {
		is = NBTUtils.setInt(is, att.getName(), NBTUtils.getInt(is, att.getName()) + value);
		return is;
	}
	
	public static ItemStack subtractAttribute(ItemStack is, Attribute att, int value) {
		is = NBTUtils.setInt(is, att.getName(), NBTUtils.getInt(is, att.getName()) - value);
		return is;
	}
}
