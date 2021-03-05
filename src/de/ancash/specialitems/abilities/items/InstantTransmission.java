package de.ancash.specialitems.abilities.items;

import org.bukkit.Bukkit;
import org.bukkit.Sound;

public class InstantTransmission {
	private int mana;
	private String identifier;
	private int range;
	private Sound sound;
	
	public InstantTransmission(String string, int mana, int range) {
		this.mana = mana;
		this.identifier = string;
		this.range = range;
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		if(version.contains("1_8") || version.contains("1_9") || version.contains("1_10") || version.contains("1_11")) {
			sound = Sound.ENDERMAN_TELEPORT;
		} else {
			sound = Sound.valueOf("ENTITY_ENDERMAN_TELEPORT");
		}
	}

	public Sound getSound() {
		return this.sound;
	}
	public int getRange() {
		return this.range;
	}

	public int getManaCost() {
		return this.mana;
	}
	public String getIdentifier() {
		return this.identifier;
	}
}
