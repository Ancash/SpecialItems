package de.ancash.specialitems.recipes;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import de.ancash.specialitems.Files;

public class LoadRecipes {

	public LoadRecipes() {
		if(Bukkit.getPluginManager().getPlugin("CraftingInjector") != null) {
			FileConfiguration recipes = Files.recipes;
			int t = 0;
			for(String key : recipes.getKeys(false)) {
				t++;
				HashMap<Integer, ItemStack> ings = new HashMap<Integer, ItemStack>();
				for(int i = 0;  i<9; i++) {
					ings.put(i + 1, recipes.getItemStack(key + ".ingredients." + i));
				}
				ItemStack result = recipes.getItemStack(key + ".result");
				new de.ancash.craftinginjector.IRecipe(result, ings, recipes.getString(key + ".name"));
			}
			System.out.println("Loaded " + t + " recipes from file");
			/*ArrayList<ItemStack> dd = new ArrayList<ItemStack>();
			dd.add(new ItemStack(Material.DIAMOND));
			dd.add(new ItemStack(Material.EMERALD));
			new de.ancash.craftinginjector.IRecipe(new ItemStack(Material.DIAMOND_AXE), dd, "axe");*/
		}
	}
	
}
