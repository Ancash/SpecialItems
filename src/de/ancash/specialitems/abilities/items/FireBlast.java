package de.ancash.specialitems.abilities.items;

public class FireBlast {
	private int mana;
	private String identifier;
	private int cooldown;
	
	public FireBlast(String string, int int1, int cooldown) {
		this.mana = int1;
		this.identifier = string;
		this.cooldown = cooldown;
	}

	public int getCooldown() {
		return this.cooldown;
	}
	
	public int getManaCost() {
		return this.mana;
	}
	public String getIdentifier() {
		return this.identifier;
	}
}
