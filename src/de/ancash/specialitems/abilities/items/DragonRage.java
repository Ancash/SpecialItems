package de.ancash.specialitems.abilities.items;

import org.bukkit.Bukkit;
import org.bukkit.Sound;

public class DragonRage {

	private int mana;
	private String identifier;
	private Sound sound;
	
	public DragonRage(String string, int int1) {
		this.mana = int1;
		this.identifier = string;
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		if(version.contains("1_8") || version.contains("1_9") || version.contains("1_10") || version.contains("1_11")) {
			sound = Sound.ENDERDRAGON_GROWL;
		} else {
			sound = Sound.valueOf("ENTITY_ENDER_DRAGON_GROWL");
		}
	}

	public Sound getSound() {
		return this.sound;
	}
	public int getManaCost() {
		return this.mana;
	}
	public String getIdentifier() {
		return this.identifier;
	}
}
