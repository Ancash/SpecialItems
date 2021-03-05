package de.ancash.specialitems.abilities.items;

import de.ancash.specialitems.Files;

public class Ability {

	static DragonRage dr;
	static FireBlast fb ;
	static InstantTransmission it;
	static Tripleshot ts;
	
	public static void init() {
		dr = new DragonRage(Files.dragonrageConfig.getString("name"), Files.dragonrageConfig.getInt("mana"));
		fb = new FireBlast(Files.fireblastConfig.getString("name"), Files.fireblastConfig.getInt("mana"), Files.fireblastConfig.getInt("cooldown"));
		it = new InstantTransmission(Files.instanttransmissionConfig.getString("name"), Files.instanttransmissionConfig.getInt("mana"), Files.instanttransmissionConfig.getInt("range"));
		ts = new Tripleshot(Files.tripleshotConfig.getString("name"));
	}
	
	public static DragonRage getDragonRage() {
		return dr;
	}
	
	public static FireBlast getFireblast() {
		return fb;
	}
	
	public static InstantTransmission getInstantTransmission() {
		return it;
	}
	
	public static Tripleshot getTripleshot() {
		return ts;
	}
	
}
