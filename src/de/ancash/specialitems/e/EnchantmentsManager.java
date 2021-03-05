package de.ancash.specialitems.e;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;

import de.ancash.specialitems.SpecialItems;

public class EnchantmentsManager {
	
	public static void loadEnchs() {
		System.out.println("Loading enchantments...");
		
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		
        if(version.contains("1_8") || version.contains("1_9") || version.contains("1_10") || version.contains("1_11")){

        } else {
        	System.out.println("Couldn't load enchantments for Version " + version);
        	return;
        }
		try {
			try {
				Field f = Enchantment.class.getDeclaredField("acceptingNew");
				f.setAccessible(true);
				f.set(null, true);
			} catch(Exception exception) {
				exception.printStackTrace();
			}
			try {
				SpecialItems.ench_aiming = new Aiming_102(102);
				SpecialItems.ench_critical = new Critical_106(106);
				SpecialItems.ench_cubism = new Cubism_107(107);
				SpecialItems.ench_dragonhunter = new DragonHunter_108(108);
				SpecialItems.ench_enderslayer = new EnderSlayer_109(109);
				SpecialItems.ench_firstStrike = new FirstStrike_101(101);
				SpecialItems.ench_growth = new Growth_105(105);
				SpecialItems.ench_lifesteal = new LifeSteal_104(104);
				SpecialItems.ench_snipe = new Snipe_103(103);
				SpecialItems.ench_telekinesis = new Telekinesis_100(100);
				Bukkit.getPluginManager().registerEvents(SpecialItems.ench_telekinesis, SpecialItems.plugin);
				Enchantment.registerEnchantment(SpecialItems.ench_firstStrike);
				Enchantment.registerEnchantment(SpecialItems.ench_telekinesis);
				Enchantment.registerEnchantment(SpecialItems.ench_aiming);
				Enchantment.registerEnchantment(SpecialItems.ench_snipe);
				Enchantment.registerEnchantment(SpecialItems.ench_lifesteal);
				Enchantment.registerEnchantment(SpecialItems.ench_growth);
				Enchantment.registerEnchantment(SpecialItems.ench_critical);
				Enchantment.registerEnchantment(SpecialItems.ench_cubism);
				Enchantment.registerEnchantment(SpecialItems.ench_dragonhunter);
				Enchantment.registerEnchantment(SpecialItems.ench_enderslayer);
			} catch(IllegalArgumentException e) {
				e.printStackTrace();
			}
		} catch(Exception excep) {
			excep.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "unlikely-arg-type" })
	public static void unload() {

		try {
			Field byIDField = Enchantment.class.getDeclaredField("byId");
			Field byNameField = Enchantment.class.getDeclaredField("byName");
			byIDField.setAccessible(true);
			byNameField.setAccessible(true);
			HashMap<Integer,Enchantment> byId = (HashMap<Integer, Enchantment>) byIDField.get(null);
			HashMap<Integer,Enchantment> byName = (HashMap<Integer, Enchantment>) byNameField.get(null);
			if(byId.containsKey(SpecialItems.ench_enderslayer.getID())) {
				byId.remove(SpecialItems.ench_enderslayer.getID());
			}
			if(byName.containsKey(SpecialItems.ench_enderslayer.getName())) {
				byName.remove(SpecialItems.ench_enderslayer.getName());
			}
			if(byId.containsKey(SpecialItems.ench_dragonhunter.getID())) {
				byId.remove(SpecialItems.ench_dragonhunter.getID());
			}
			if(byName.containsKey(SpecialItems.ench_dragonhunter.getName())) {
				byName.remove(SpecialItems.ench_dragonhunter.getName());
			}
			if(byId.containsKey(SpecialItems.ench_cubism.getID())) {
				byId.remove(SpecialItems.ench_cubism.getID());
			}
			if(byName.containsKey(SpecialItems.ench_cubism.getName())) {
				byName.remove(SpecialItems.ench_cubism.getName());
			}
			if(byId.containsKey(SpecialItems.ench_critical.getID())) {
				byId.remove(SpecialItems.ench_critical.getID());
			}
			if(byName.containsKey(SpecialItems.ench_critical.getName())) {
				byName.remove(SpecialItems.ench_critical.getName());
			}
			if(byId.containsKey(SpecialItems.ench_growth.getID())) {
				byId.remove(SpecialItems.ench_growth.getID());
			}
			if(byName.containsKey(SpecialItems.ench_growth.getName())) {
				byName.remove(SpecialItems.ench_growth.getName());
			}
			if(byId.containsKey(SpecialItems.ench_lifesteal.getID())) {
				byId.remove(SpecialItems.ench_lifesteal.getID());
			}
			if(byName.containsKey(SpecialItems.ench_lifesteal.getName())) {
				byName.remove(SpecialItems.ench_lifesteal.getName());
			}
			if(byId.containsKey(SpecialItems.ench_aiming.getID())) {
				byId.remove(SpecialItems.ench_aiming.getID());
			}
			if(byName.containsKey(SpecialItems.ench_aiming.getName())) {
				byName.remove(SpecialItems.ench_aiming.getName());
			}
			if(byId.containsKey(SpecialItems.ench_telekinesis.getID())) {
				byId.remove(SpecialItems.ench_telekinesis.getID());
			}
			if(byName.containsKey(SpecialItems.ench_telekinesis.getName())) {
				byName.remove(SpecialItems.ench_telekinesis.getName());
			}
			if(byId.containsKey(SpecialItems.ench_firstStrike.getID())) {
				byId.remove(SpecialItems.ench_firstStrike.getID());
			}
			if(byName.containsKey(SpecialItems.ench_firstStrike.getName())) {
				byName.remove(SpecialItems.ench_firstStrike.getName());
			}
			if(byId.containsKey(SpecialItems.ench_snipe.getID())) {
				byId.remove(SpecialItems.ench_snipe.getID());
			}
			if(byName.containsKey(SpecialItems.ench_snipe.getName())) {
				byName.remove(SpecialItems.ench_snipe.getName());
			}
		} catch(Exception ignored) {
		}
		
	}
	
}
