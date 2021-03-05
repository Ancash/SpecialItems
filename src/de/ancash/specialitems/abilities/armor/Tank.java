package de.ancash.specialitems.abilities.armor;

import java.util.HashMap;

import de.ancash.specialitems.utils.Attribute;

public class Tank implements FullSetBonus{

	private final static int health = 40;
	private final static int defense = 80;
	private final static int intelligence = 0;
	private final static int strength = 0;
	private final static int crit_chance = 0;
	private final static int crit_damage = 0;
	private static HashMap<Attribute, Integer> all = new HashMap<Attribute, Integer>();
	
	static {
		all.put(Attribute.HEALTH, health);
		all.put(Attribute.DEFENSE, defense);
		all.put(Attribute.INTELLIGENCE, intelligence);
		all.put(Attribute.STRENGTH, strength);
		all.put(Attribute.CRIT_CHANCE, crit_chance);
		all.put(Attribute.CRIT_DAMAGE, crit_damage);
	}
	
	@Override
	public BonusType getType() {
		return BonusType.STATS;
	}
	
	@Override
	public String getName() {
		return "Tank";
	}

	@Override
	public HashMap<Attribute, Integer> getBoosts() {
		return all;
	}

	@Override
	public int getPercentage() {
		return 0;
	}
	
	@Override
	public boolean isPercentage() {
		return false;
	}

	@Override
	public HashMap<Attribute, Double> getPercentageBoost() {
		return null;
	}
}
