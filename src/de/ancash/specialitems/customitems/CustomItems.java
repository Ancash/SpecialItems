package de.ancash.specialitems.customitems;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.ancash.specialitems.Files;
import de.ancash.specialitems.utils.ItemStackUtil;

public class CustomItems {

	static Inventory inv;
	
	public static Inventory getInv() {
		return CustomItems.inv;
	}
	
	static ArrayList<ItemStackUtil> itemStackUtils = new ArrayList<ItemStackUtil>();
	
	public static void loadInv() {
		System.out.println("Loading items...");
		
		inv = Bukkit.createInventory(null, 54, "Custom Items");
		int count = 0;
		try {
			for(int i = 0; i<45; i++) {
				if(Files.itemsConfig.getString("items." + i + ".material") == null) continue;
				ItemStackUtil isutil = new ItemStackUtil(Material.getMaterial(Files.itemsConfig.getString("items." + i + ".material")), Files.itemsConfig.getString("items." + i + ".name").replace("&", "§"));
				ArrayList<String> lore = new ArrayList<String>();
				Files.itemsConfig.getStringList("items." + i + ".lore").forEach(str -> lore.add(str.replace("&", "§")));
				isutil.setLore(lore);
				
				
				isutil.setString("ability", Files.itemsConfig.getString("items." + i + ".ability"));
				
				addItem(isutil.getItem());
				count++;
			}
		} catch(NullPointerException | IllegalArgumentException e) {
			System.out.println("Something went wrong while loading Custom Items:");
			e.printStackTrace();
		}
		System.out.println("Loaded " + (count + 1) + " items from file and " + (inv.firstEmpty() - count - 1) + " for dragons");
	}

	private static void addItem(ItemStack is) {
		if(inv.firstEmpty() != -1) {
			inv.addItem(is);
		}
	}

}
