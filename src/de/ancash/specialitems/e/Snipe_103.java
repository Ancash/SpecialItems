package de.ancash.specialitems.e;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class Snipe_103 extends Enchantment{
	public Snipe_103(int id) {
		super(id);
	}

	public int getID() {
		return 103;
	}
	
	@Override
	public boolean canEnchantItem(ItemStack arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		// TODO Auto-generated method stub
		return EnchantmentTarget.BOW;
	}

	@Override
	public int getMaxLevel() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Snipe";
	}

	@Override
	public int getStartLevel() {
		// TODO Auto-generated method stub
		return 1;
	}
}
