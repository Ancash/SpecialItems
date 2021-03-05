package de.ancash.specialitems.e;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class FirstStrike_101 extends Enchantment{
	
	public static ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public FirstStrike_101(int id) {
		super(id);
	}

	public int getID() {
		return 101;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		// TODO Auto-generated method stub
		return EnchantmentTarget.WEAPON;
	}

	@Override
	public int getMaxLevel() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "First Strike";
	}

	@Override
	public int getStartLevel() {
		// TODO Auto-generated method stub
		return 1;
	}
}
