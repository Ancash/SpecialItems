package de.ancash.specialitems;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.ancash.specialitems.pets.Pet;
import de.ancash.specialitems.utils.FileUtils;

public class Files {
	
	
	static File configFile = new File("plugins/SpecialItems/config.yml");
	public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(configFile);
	static File petsFile = new File("plugins/SpecialItems/petsConfiguration.yml");
	public static FileConfiguration petsConfig = YamlConfiguration.loadConfiguration(petsFile);
	static File customItemsFile = new File("plugins/SpecialItems/custom-items.yml");
	public static FileConfiguration itemsConfig = YamlConfiguration.loadConfiguration(customItemsFile);
	static File anvilFile = new File("plugins/SpecialItems/anvil.yml");
	public static FileConfiguration anvilConfig = YamlConfiguration.loadConfiguration(anvilFile);
	static File bazaarFile = new File("plugins/SpecialItems/bazaar.yml");
	public static FileConfiguration bazaarConfig = YamlConfiguration.loadConfiguration(bazaarFile);
	static File recipesFile = new File("plugins/SpecialItems/recipes.yml");
	public static FileConfiguration recipes = YamlConfiguration.loadConfiguration(recipesFile);
	static File dragonrageFile = new File("plugins/SpecialItems/abilities/dragonrage.yml");
	public static FileConfiguration dragonrageConfig = YamlConfiguration.loadConfiguration(dragonrageFile);
	static File fireblastFile = new File("plugins/SpecialItems/abilities/fireblast.yml");
	public static FileConfiguration fireblastConfig = YamlConfiguration.loadConfiguration(fireblastFile);
	static File instanttransmissionFile = new File("plugins/SpecialItems/abilities/instanttransmission.yml");
	public static FileConfiguration instanttransmissionConfig = YamlConfiguration.loadConfiguration(instanttransmissionFile);
	static File tripleshotFile = new File("plugins/SpecialItems/abilities/tripleshot.yml");
	public static FileConfiguration tripleshotConfig = YamlConfiguration.loadConfiguration(tripleshotFile);
	
	public static void initFiles() throws FileNotFoundException, IOException, InvalidConfigurationException {
		
		if(!new File("plugins/SpecialItems").exists()) {
			new File("plugins/SpecialItems").mkdir();
		}
		
		if(!configFile.exists()) {
			SpecialItems.getInstance().saveDefaultConfig();
		}
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		
		
		try {
			if(!recipesFile.exists()) loadFile(SpecialItems.getInstance().getResource("recipes.yml"), recipesFile);
			if(!bazaarFile.exists()) loadFile(SpecialItems.getInstance().getResource("bazaar.yml"), bazaarFile);
			if(!petsFile.exists()) loadFile(SpecialItems.getInstance().getResource("petsConfiguration.yml"), petsFile);
			if(!anvilFile.exists()) {
		        if(version.contains("1_8") || version.contains("1_9") || version.contains("1_10") || version.contains("1_11")){
		        	loadFile(SpecialItems.getInstance().getResource("anvil_1_8__1_12.yml"), anvilFile);
		        } else {
		        	loadFile(SpecialItems.getInstance().getResource("anvil_1_13__1_16.yml"), anvilFile);
		        }
			}
			if(!customItemsFile.exists()) {
				if(version.contains("1_8") || version.contains("1_9") || version.contains("1_10") || version.contains("1_11")) {
					loadFile(SpecialItems.getInstance().getResource("custom-items_1_8__1_12.yml"), customItemsFile);				
				} else {
					loadFile(SpecialItems.getInstance().getResource("custom-items_1_13__1_16.yml"), customItemsFile);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		loadAbilities();
		
		File playerData = new File("plugins/SpecialItems/playerData");
		if(!playerData.exists()) {
			playerData.mkdir();
		}
		
		loadFiles();
		
		for(int i = 2; i <= 100; i++) {
			Pet.common_pet_xp_requirements.add((Integer.valueOf(petsConfig.getString("pets.xp_requirements.Lvl"+i).split(" ")[0])));
			Pet.uncommon_pet_xp_requirements.add((Integer.valueOf(petsConfig.getString("pets.xp_requirements.Lvl"+i).split(" ")[1])));
			Pet.rare_pet_xp_requirements.add((Integer.valueOf(petsConfig.getString("pets.xp_requirements.Lvl"+i).split(" ")[2])));
			Pet.epic_pet_xp_requirements.add((Integer.valueOf(petsConfig.getString("pets.xp_requirements.Lvl"+i).split(" ")[3])));
			Pet.legendary_pet_xp_requirements.add((Integer.valueOf(petsConfig.getString("pets.xp_requirements.Lvl"+i).split(" ")[4])));
		}
		
		
	}

	private static void loadFiles() throws FileNotFoundException, IOException, InvalidConfigurationException {
		itemsConfig.load(customItemsFile);
		cfg.load(configFile);
		petsConfig.load(petsFile);
		anvilConfig.load(anvilFile);
		recipes.load(recipesFile);
		
		dragonrageConfig.load(dragonrageFile);
		tripleshotConfig.load(tripleshotFile);
		instanttransmissionConfig.load(instanttransmissionFile);
		fireblastConfig.load(fireblastFile);
	}

	private static void loadAbilities() throws IOException, InvalidConfigurationException {
		if(!dragonrageFile.exists()) {
			loadFile(SpecialItems.getInstance().getResource("de/ancash/specialitems/abilities/dragonrage.yml"), dragonrageFile);
		}
		if(!fireblastFile.exists()) {
			loadFile(SpecialItems.getInstance().getResource("de/ancash/specialitems/abilities/fireblast.yml"), fireblastFile);
		}
		if(!instanttransmissionFile.exists()) {
			loadFile(SpecialItems.getInstance().getResource("de/ancash/specialitems/abilities/instanttransmission.yml"), instanttransmissionFile);
		}
		if(!tripleshotFile.exists()) {
			loadFile(SpecialItems.getInstance().getResource("de/ancash/specialitems/abilities/tripleshot.yml"), tripleshotFile);
		}
	}

	private static void loadFile(InputStream paramInputStream, File paramFile) throws IOException, InvalidConfigurationException {

		if(!paramFile.exists()) {
			FileUtils.copyInputStreamToFile(paramInputStream, paramFile);
		}
		((FileConfiguration)YamlConfiguration.loadConfiguration(paramFile)).load(paramFile);
	}
	
}
