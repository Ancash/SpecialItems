package de.ancash.specialitems;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.ancash.specialitems.bazaar.BuyOrder;
import de.ancash.specialitems.bazaar.SellOffer;
import de.ancash.specialitems.pets.PetMethods;
import de.ancash.specialitems.utils.NBTUtils;
import de.tr7zw.nbtapi.NBTItem;

public class PClass {
	
	public static HashMap<UUID, PClass> playerStats = new HashMap<UUID, PClass>();
	
	private HashMap<Enchantment, Integer> ench_opt_1;
	private HashMap<Enchantment, Integer> ench_opt_2;
	private HashMap<Enchantment, Integer> ench_opt_3;
	
	private Player p;
	
	private UUID playerUUID;
	
	private double strength;
	private double crit_damage;
	private double crit_chance;
	private double defense;
	private double max_doubleelligence;
	private double max_health;
	private int speed;
	
	private String lastSkill;
	private double gainedXP;
	private Block block;
	
	private double cur_intelligence;
	
	private UUID last_attacked_entity;
	private int strikes_on_last_attacked_entity;
	
	private ArrayList<SellOffer> sellOffer;
	private ArrayList<BuyOrder> buyOrder;
	
	public ArrayList<BuyOrder> getBuyOrder() {
		return this.buyOrder;
	}
	public ArrayList<SellOffer> getSellOffer() {
		return this.sellOffer;
	}
	public void setBuyOrder(ArrayList<SellOffer> sellOffer) {
		this.sellOffer = sellOffer;
	}
	public void setSellOffer(ArrayList<BuyOrder> buyOrder) {
		this.buyOrder = buyOrder;
	}
	
	public String getLastSkill() {
		return lastSkill;
	}
	public double getGainedXP() {
		return gainedXP;
	}
	public Block getBlock() {
		return block;
	}
	
	public void setBlock(Block Block) {
		block = Block;
	}
	public void setLastSkill(String skill) {
		lastSkill = skill;
	}
	public void setGainedXP(double xp) {
		gainedXP = xp;
	}
	
	public HashMap<Enchantment, Integer> getEnchOpt1() {
		return ench_opt_1;
	}
	public HashMap<Enchantment, Integer> getEnchOpt2() {
		return ench_opt_2;
	}
	public HashMap<Enchantment, Integer> getEnchOpt3() {
		return ench_opt_3;
	}
	public void setEnchOpt1(HashMap<Enchantment, Integer> opt1) {
		ench_opt_1 = opt1;
	}
	public void setEnchOpt2(HashMap<Enchantment, Integer> opt2) {
		ench_opt_2 = opt2;
	}
	public void setEnchOpt3(HashMap<Enchantment, Integer> opt3) {
		ench_opt_3 = opt3;
	}
	
	public int getStrikesOnLastAttackedEntity() {
		return strikes_on_last_attacked_entity;
	}
	public void setStrikesOnLastAttackedEntity(int strikes) {
		strikes_on_last_attacked_entity  = strikes;
	}
	public void setLastAttackedEntity(UUID entityuuid) {
		if(!entityuuid.equals(last_attacked_entity)) {
			last_attacked_entity = entityuuid;
			strikes_on_last_attacked_entity = 1;
		} else {
			strikes_on_last_attacked_entity++;
		}
	}
	public UUID getLastAttackedEntity() {
		return last_attacked_entity;
	}
	public void setPlayer(Player player) {
		p = player;
	}
	public Player getPlayer() {
		return p;
	}
	public void setUUID(UUID uuid) {
		playerUUID = uuid;
	}
	public UUID getUUID() {
		return playerUUID;
	}
	public void setStrength(double value) {
		strength = value;
	}
	public double getStrength() {
		return strength;
	}
	public void setCritDamage(double value) {
		crit_damage = value;
	}
	public double getCritDamage() {
		return crit_damage;
	}
	public void setCritChance(double value) {
		crit_chance = value;
	}
	public double getCritChance() {
		return crit_chance;
	}
	public void setSpeed(int value) {
		speed = value;
	}
	public int getSpeed() {
		return speed;
	}
	public void setDefense(double value) {
		defense = value;
	}
	public double getDefense() {
		return defense;
	}
	public void setMaxIntelligence(double value) {
		max_doubleelligence = value;
	}
	public double getMaxIntelligence() {
		return max_doubleelligence;
	}
	public void setMaxHealth(double value) {
		max_health = value;
		p.setMaxHealth(value);
	}
	public double getMaxHealth() {
		return max_health;
	}
	public void setCurrentHealth(double value) {
		if(value < p.getMaxHealth()) {
			p.setHealth(value);
		}
	}
	public double getCurrentHealth() {
		return p.getHealth();
	}
	public void setCurrentIntelligence(double value) {
		cur_intelligence = value;
	}
	public double getCurrentIntelligence() {
		return cur_intelligence;
	}
	
	public int getSkillLevel(String skill) {
		File playerData = new File("plugins/SpecialItems/playerData/" + p.getUniqueId().toString() + "/data.yml");
		FileConfiguration pD = YamlConfiguration.loadConfiguration(playerData);
		
		try {
			pD.load(playerData);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return pD.getInt("skill."+skill+".level");
	}
	public static double  getHealthWithoutBase(Player p) {
		File playerData = new File("plugins/SpecialItems/playerData/" + p.getUniqueId().toString() + "/data.yml");
		FileConfiguration pD = YamlConfiguration.loadConfiguration(playerData);
		try {
			pD.load(playerData);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		double health = pD.getDouble("stats.extra_health");
		ItemStack[] armor = p.getInventory().getArmorContents();
		
		for(ItemStack is : armor) {
			if(is != null && is.hasItemMeta() && is.getItemMeta().hasLore()) {
				health = health + NBTUtils.getDouble(is, "baseHealth") + NBTUtils.getDouble(is, "reforgeHealth");
			}
		}
		return health;
	}
	
	public static double  getTotalDefense(Player p, boolean explosion, boolean projectile, boolean firelava) {
		File playerData = new File("plugins/SpecialItems/playerData/" + p.getUniqueId().toString() + "/data.yml");
		FileConfiguration pD = YamlConfiguration.loadConfiguration(playerData);
		
		try {
			pD.load(playerData);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		double defense = Files.cfg.getDouble("base_defense") + pD.getDouble("stats.extra_defense");
		ItemStack[] armor = p.getInventory().getArmorContents();
		
		for(ItemStack is : armor) {
			if(is != null && is.hasItemMeta() && is.getItemMeta().hasLore()) {
				defense = defense + NBTUtils.getDouble(is, "baseDefense") + NBTUtils.getDouble(is, "reforgeDefense");
			}
			if(is != null && is.hasItemMeta() && is.getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
				defense = defense + 3*is.getItemMeta().getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
			}
			if(is != null && is.hasItemMeta() && is.getItemMeta().hasEnchant(Enchantment.PROTECTION_EXPLOSIONS) && explosion) {
				defense = defense + 4*is.getItemMeta().getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS);
			}
			if(is != null && is.hasItemMeta() && is.getItemMeta().hasEnchant(Enchantment.PROTECTION_PROJECTILE) && projectile) {
				defense = defense + 4*is.getItemMeta().getEnchantLevel(Enchantment.PROTECTION_PROJECTILE);
			}
			if(is != null && is.hasItemMeta() && is.getItemMeta().hasEnchant(Enchantment.PROTECTION_FIRE) && firelava) {
				defense = defense + 4*is.getItemMeta().getEnchantLevel(Enchantment.PROTECTION_FIRE);
			}
		}
		if(SpecialItems.equipedPets.containsKey(p.getUniqueId())) {
			ItemStack pet = SpecialItems.equipedPets.get(p.getUniqueId()).getItemInHand();
			defense = defense + (Files.petsConfig.getDouble("pets." + new NBTItem(pet).getString("pet") + ".base." + new NBTItem(pet).getString("rarity") + ".defense"));
			defense = defense + (Files.petsConfig.getDouble("pets." + new NBTItem(pet).getString("pet") + ".upgrade_per_level." + new NBTItem(pet).getString("rarity") + ".defense") * PetMethods.getPetLvl(pet));
			
			defense = defense + ((Files.petsConfig.getDouble("pets."+new NBTItem(pet).getString("pet") + ".upgrade_per_level." + new NBTItem(pet).getString("rarity") + ".superior") * PetMethods.getPetLvl(pet))/100 * defense);
		}
		
		return defense;
	}
	public static double  getTotalHealth(Player p) {
		
		File playerData = new File("plugins/SpecialItems/playerData/" + p.getUniqueId().toString() + "/data.yml");
		FileConfiguration pD = YamlConfiguration.loadConfiguration(playerData);
		
		try {
			pD.load(playerData);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		double health = Files.cfg.getDouble("base_health") + pD.getDouble("stats.extra_health");
		ItemStack[] armor = p.getInventory().getArmorContents();
		
		for(ItemStack is : armor) {
			if(is != null && is.hasItemMeta() && SpecialItems.ench_growth != null && is.getItemMeta().hasEnchant(SpecialItems.ench_growth)) {
				health = health+is.getItemMeta().getEnchantLevel(SpecialItems.ench_growth)*15;
			}
			if(is != null && is.hasItemMeta() && is.getItemMeta().hasLore()) {
				health = health + NBTUtils.getDouble(is, "baseHealth") + NBTUtils.getDouble(is, "reforgeHealth");
			}
		}
		if(SpecialItems.equipedPets.containsKey(p.getUniqueId())) {
			ItemStack pet = SpecialItems.equipedPets.get(p.getUniqueId()).getItemInHand();
			health = health + (Files.petsConfig.getDouble("pets." + new NBTItem(pet).getString("pet") + ".base." + new NBTItem(pet).getString("rarity") + ".health"));
			health = health + (Files.petsConfig.getDouble("pets." + new NBTItem(pet).getString("pet") + ".upgrade_per_level." + new NBTItem(pet).getString("rarity") + ".health") * PetMethods.getPetLvl(pet));
			
			health = health + ((Files.petsConfig.getDouble("pets."+new NBTItem(pet).getString("pet") + ".upgrade_per_level." + new NBTItem(pet).getString("rarity") + ".superior") * PetMethods.getPetLvl(pet))/100 * health);
		}
		return health;
	}
	public static double  getTotalIntelligence(Player p) {
		
		File playerData = new File("plugins/SpecialItems/playerData/" + p.getUniqueId().toString() + "/data.yml");
		FileConfiguration pD = YamlConfiguration.loadConfiguration(playerData);
		
		try {
			pD.load(playerData);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		double intelligence = Files.cfg.getDouble("base_mana") + pD.getDouble("stats.extra_mana");
		ItemStack[] armor = p.getInventory().getArmorContents();
		
		for(ItemStack is : armor) {
			if(is != null && is.hasItemMeta() && is.getItemMeta().hasLore()) {
				intelligence = intelligence + NBTUtils.getDouble(is, "baseIntelligence") + NBTUtils.getDouble(is, "reforgeIntelligence");	
			}
		}
		ItemStack iih = p.getItemInHand();
		if(iih != null && iih.hasItemMeta() && iih.getItemMeta().hasLore()) {
			intelligence = intelligence + NBTUtils.getDouble(iih, "baseIntelligence") + NBTUtils.getDouble(iih, "reforgeIntelligence");
		}
		if(SpecialItems.equipedPets.containsKey(p.getUniqueId())) {
			ItemStack pet = SpecialItems.equipedPets.get(p.getUniqueId()).getItemInHand();
			intelligence = intelligence + (Files.petsConfig.getDouble("pets." + new NBTItem(pet).getString("pet") + ".base." + new NBTItem(pet).getString("rarity") + ".intelligence"));
			intelligence = intelligence + (Files.petsConfig.getDouble("pets." + new NBTItem(pet).getString("pet") + ".upgrade_per_level." + new NBTItem(pet).getString("rarity") + ".intelligence") * PetMethods.getPetLvl(pet));
			
			intelligence = intelligence + ((Files.petsConfig.getDouble("pets."+new NBTItem(pet).getString("pet") + ".upgrade_per_level." + new NBTItem(pet).getString("rarity") + ".superior") * PetMethods.getPetLvl(pet))/100 * intelligence);
		}
		return intelligence;
	}

	public static double  getStrength(Player p) {
		
		File playerData = new File("plugins/SpecialItems/playerData/" + p.getUniqueId().toString() + "/data.yml");
		FileConfiguration pD = YamlConfiguration.loadConfiguration(playerData);
		
		try {
			pD.load(playerData);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		double strength = Files.cfg.getDouble("base_strength") + pD.getDouble("stats.extra_strength");
		ItemStack[] armor = p.getInventory().getArmorContents();
		
		for(ItemStack is : armor) {
			if(is != null && is.hasItemMeta() && is.getItemMeta().hasLore()) {
				strength = strength + NBTUtils.getDouble(is, "baseStrength") + NBTUtils.getDouble(is, "reforgeStrength");
			}
		}
		ItemStack iih = p.getItemInHand();
		if(iih != null && iih.hasItemMeta() && iih.getItemMeta().hasLore()) {
			strength = strength + NBTUtils.getDouble(iih, "baseStrength") + NBTUtils.getDouble(iih, "reforgeStrength");
		}
		if(SpecialItems.equipedPets.containsKey(p.getUniqueId())) {
			ItemStack pet = SpecialItems.equipedPets.get(p.getUniqueId()).getItemInHand();
			strength = strength + (Files.petsConfig.getDouble("pets." + new NBTItem(pet).getString("pet") + ".base." + new NBTItem(pet).getString("rarity") + ".strength"));
			strength = strength + (Files.petsConfig.getDouble("pets." + new NBTItem(pet).getString("pet") + ".upgrade_per_level." + new NBTItem(pet).getString("rarity") + ".strength") * PetMethods.getPetLvl(pet));
			strength = strength + Files.petsConfig.getDouble("pets."+new NBTItem(pet).getString("pet") + ".upgrade_per_level." + new NBTItem(pet).getString("rarity") + ".one_with_the_dragon_strength") * PetMethods.getPetLvl(pet);
			
			
			
			strength = strength + ((Files.petsConfig.getDouble("pets."+new NBTItem(pet).getString("pet") + ".upgrade_per_level." + new NBTItem(pet).getString("rarity") + ".superior") * PetMethods.getPetLvl(pet) / 100 * strength));
			
		}
		return strength;
	}
	public static double  getCritDamage(Player p) {
		File playerData = new File("plugins/SpecialItems/playerData/" + p.getUniqueId().toString() + "/data.yml");
		FileConfiguration pD = YamlConfiguration.loadConfiguration(playerData);
		
		try {
			pD.load(playerData);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		double crit_damage = Files.cfg.getDouble("base_crit_damage") + pD.getDouble("stats.extra_crit_damage");
		ItemStack[] armor = p.getInventory().getArmorContents();
		
		for(ItemStack is : armor) {
			if(is != null && is.hasItemMeta() && is.getItemMeta().hasLore()) {
				crit_damage = crit_damage + NBTUtils.getDouble(is, "baseDamage") + NBTUtils.getDouble(is, "reforgeCritDamage");
			}
		}
		ItemStack iih = p.getItemInHand();
		if(iih != null && iih.hasItemMeta() && iih.getItemMeta().hasLore()) {
			crit_damage = crit_damage + NBTUtils.getDouble(iih, "baseCritDamage") + NBTUtils.getDouble(iih, "reforgeCritDamage");
		}
		if(iih != null && iih.hasItemMeta() && SpecialItems.ench_critical != null && iih.getItemMeta().hasEnchant(SpecialItems.ench_critical)) {
			crit_damage = crit_damage + 10 * iih.getItemMeta().getEnchantLevel(SpecialItems.ench_critical);
		}
		if(SpecialItems.equipedPets.containsKey(p.getUniqueId())) {
			ItemStack pet = SpecialItems.equipedPets.get(p.getUniqueId()).getItemInHand();
			crit_damage = crit_damage + (Files.petsConfig.getDouble("pets." + new NBTItem(pet).getString("pet") + ".base." + new NBTItem(pet).getString("rarity") + ".crit_damage"));
			crit_damage = crit_damage + (Files.petsConfig.getDouble("pets." + new NBTItem(pet).getString("pet") + ".upgrade_per_level." + new NBTItem(pet).getString("rarity") + ".crit_damage") * PetMethods.getPetLvl(pet));
			
			crit_damage = crit_damage + ((Files.petsConfig.getDouble("pets."+new NBTItem(pet).getString("pet") + ".upgrade_per_level." + new NBTItem(pet).getString("rarity") + ".superior") * PetMethods.getPetLvl(pet))/100 * crit_damage);
		}
		return crit_damage;
	}
	public static double  getCritChance(Player p) {
		File playerData = new File("plugins/SpecialItems/playerData/" + p.getUniqueId().toString() + "/data.yml");
		FileConfiguration pD = YamlConfiguration.loadConfiguration(playerData);
		
		try {
			pD.load(playerData);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
		double crit_chance = Files.cfg.getDouble("base_crit_chance") + pD.getDouble("stats.extra_crit_chance");
		ItemStack[] armor = p.getInventory().getArmorContents();
		
		for(ItemStack is : armor) {
			if(is != null && is.hasItemMeta() && is.getItemMeta().hasLore()) {
				crit_chance = crit_chance + NBTUtils.getDouble(is, "baseCritChance") + NBTUtils.getDouble(is, "reforgeCritChance");
			}
		}
		ItemStack iih = p.getItemInHand();
		if(iih != null && iih.hasItemMeta() && iih.getItemMeta().hasLore()) {
			ArrayList<String> lore = (ArrayList<String>) iih.getItemMeta().getLore();
			for(String str : lore) {
				if(str.contains("§7"+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.crit_chance.name"))+": §c+")) {
					crit_chance = crit_chance + NBTUtils.getDouble(iih, "baseCritChance") + NBTUtils.getDouble(iih, "reforgeCritChance");
				}
			}
		}
		if(SpecialItems.equipedPets.containsKey(p.getUniqueId())) {
			ItemStack pet = SpecialItems.equipedPets.get(p.getUniqueId()).getItemInHand();
			crit_chance = crit_chance + (Files.petsConfig.getDouble("pets." + new NBTItem(pet).getString("pet") + ".base." + new NBTItem(pet).getString("rarity") + ".crit_chance"));
			crit_chance = crit_chance + (Files.petsConfig.getDouble("pets." + new NBTItem(pet).getString("pet") + ".upgrade_per_level." + new NBTItem(pet).getString("rarity") + ".crit_chance") * PetMethods.getPetLvl(pet));
			
			crit_chance = crit_chance + ((Files.petsConfig.getDouble("pets."+new NBTItem(pet).getString("pet") + ".upgrade_per_level." + new NBTItem(pet).getString("rarity") + ".superior") * PetMethods.getPetLvl(pet))/100 * crit_chance);
		}
		return crit_chance;
	}
}
