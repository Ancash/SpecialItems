package de.ancash.specialitems.utils;

import org.bukkit.Bukkit;

public class NMS {
	
	public static Class<?> getNMSClass(String name) {
		try {
			return Class.forName("net.minecraft.server." + getVersion() + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Class<?> getCraftBukkitClass(String name) {
		try {
			return Class.forName("org.bukkit.craftbukkit." + getVersion() + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static String getVersion() {
    	return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	}
}
