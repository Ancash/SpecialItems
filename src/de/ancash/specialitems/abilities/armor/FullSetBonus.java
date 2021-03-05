package de.ancash.specialitems.abilities.armor;

import java.util.HashMap;

import de.ancash.specialitems.utils.Attribute;

public interface FullSetBonus {
	
	public String getName();
	public HashMap<Attribute, Integer> getBoosts();
	public boolean isPercentage();
	public int getPercentage();
	public HashMap<Attribute, Double> getPercentageBoost();
	public BonusType getType(); 
}
