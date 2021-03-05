package de.ancash.specialitems.e;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class LifeSteal_104 extends Enchantment{
	
	public LifeSteal_104(int id) {
		super(id);
	}

	public int getID() {
		return 104;
	}
	
	@Override
	public boolean canEnchantItem(ItemStack is) {
		if(is.getType().equals(Material.DIAMOND_SWORD) 
				|| is.getType().equals(Material.GOLD_SWORD)
				|| is.getType().equals(Material.IRON_SWORD)
				|| is.getType().equals(Material.STONE_SWORD) 
				|| is.getType().equals(Material.WOOD_SWORD)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) {
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.WEAPON;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Life Steal";
	}

	@Override
	public int getStartLevel() {
		return 1;
	}
}
