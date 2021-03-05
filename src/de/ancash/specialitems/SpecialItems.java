package de.ancash.specialitems;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import de.ancash.actionbar.ActionBarAPI;
import de.ancash.specialitems.abilities.items.Ability;
import de.ancash.specialitems.anvil.Anvil;
import de.ancash.specialitems.anvil.AnvilUse;
import de.ancash.specialitems.anvil.OpenAnvilCMD;
import de.ancash.specialitems.api.StatsManager;
import de.ancash.specialitems.bazaar.BazaarMenuHandler;
import de.ancash.specialitems.bazaar.BazaarMethods;
import de.ancash.specialitems.bazaar.BazaarScheduler;
import de.ancash.specialitems.bazaar.OpenBazaarMenu;
import de.ancash.specialitems.commands.AddCritChanceCMD;
import de.ancash.specialitems.commands.AddCritDamageCMD;
import de.ancash.specialitems.commands.AddDamageCMD;
import de.ancash.specialitems.commands.AddDefenseCMD;
import de.ancash.specialitems.commands.AddEnchantment;
import de.ancash.specialitems.commands.AddHealthCMD;
import de.ancash.specialitems.commands.AddIntelligenceCMD;
import de.ancash.specialitems.commands.AddStrengthCMD;
import de.ancash.specialitems.commands.AttributeCMD;
import de.ancash.specialitems.commands.RarityCMD;
import de.ancash.specialitems.commands.SpawnDragon;
import de.ancash.specialitems.customitems.CustomItemInv;
import de.ancash.specialitems.customitems.CustomItems;
import de.ancash.specialitems.customitems.DoubleJump;
import de.ancash.specialitems.customitems.RightClick;
import de.ancash.specialitems.e.Telekinesis_100;
import de.ancash.specialitems.e.FirstStrike_101;
import de.ancash.specialitems.e.Aiming_102;
import de.ancash.specialitems.e.Snipe_103;
import de.ancash.specialitems.e.LifeSteal_104;
import de.ancash.specialitems.e.Growth_105;
import de.ancash.specialitems.e.Critical_106;
import de.ancash.specialitems.e.Cubism_107;
import de.ancash.specialitems.e.DragonHunter_108;
import de.ancash.specialitems.e.EnderSlayer_109;
import de.ancash.specialitems.e.EnchantmentsManager;
import de.ancash.specialitems.listener.PetXPUp;
import de.ancash.specialitems.listener.BowLaunchHit;
import de.ancash.specialitems.listener.Enchanting;
import de.ancash.specialitems.listener.EnchantmentTable;
import de.ancash.specialitems.listener.EntityDamage;
import de.ancash.specialitems.listener.InvOpen;
import de.ancash.specialitems.listener.PlayerJoin;
import de.ancash.specialitems.listener.PlayerMove;
import de.ancash.specialitems.listener.PlayerQuit;
import de.ancash.specialitems.pets.GivePetCMD;
import de.ancash.specialitems.pets.PetsMenuHandler;
import de.ancash.specialitems.recipes.LoadRecipes;
import de.ancash.specialitems.utils.Attribute;
import de.ancash.specialitems.utils.EntityHider;
import de.ancash.specialitems.utils.EntityHider.Policy;
import de.ancash.specialitems.utils.NBTUtils;
import de.tr7zw.nbtapi.NBTItem;
import de.ancash.specialitems.pets.OpenPetsMenuCMD;
import de.ancash.specialitems.pets.Pet;
import net.milkbowl.vault.economy.Economy;

public class SpecialItems extends JavaPlugin{

public static SpecialItems plugin;

	public static ArrayList<Player> used_emberrod = new ArrayList<Player>();

	public static ArrayList<ArmorStand> damage_indicator = new ArrayList<ArmorStand>();
	
	public static String COMMON = "§f§lCOMMON";
	public static String UNCOMMON = "§a§lUNCOMMON";
	public static String RARE = "§9§lRARE";
	public static String EPIC = "§5§lEPIC";
	public static String LEGENDARY = "§6§lLEGENDARY";
		
	public static EnderSlayer_109 ench_enderslayer;
	public static DragonHunter_108 ench_dragonhunter;
	public static Cubism_107 ench_cubism;
	public static Critical_106 ench_critical;
	public static Growth_105 ench_growth;
	public static LifeSteal_104 ench_lifesteal;
	public static Snipe_103 ench_snipe;
	public static Aiming_102 ench_aiming;
	public static FirstStrike_101 ench_firstStrike;
	public static Telekinesis_100 ench_telekinesis;
			
	//public static ItemStack grapplingHook = new ItemStack(Material.FISHING_ROD);
	//public static ItemMeta ghmeta = grapplingHook.getItemMeta();
		
	public static HashMap<UUID, ArmorStand> equipedPets = new HashMap<UUID, ArmorStand>();
	public static HashMap<UUID, ArmorStand> petsName = new HashMap<UUID, ArmorStand>();
	
	public static ArmorStand merciless_swipe;
		
	public static Economy econ = null;
	private static final Logger log = Logger.getLogger("Minecraft");
	
	public static EntityHider eh = null;
		
	private static StatsManager sm = new StatsManager();
	
	public static StatsManager getStatsManager() {
		return sm;
	}
	
	public void onEnable() {
		plugin = this;         
		
		for(World w : Bukkit.getWorlds()){
			for(Entity e : w.getEntities()) {
				if(e.getType().equals(EntityType.ENDER_DRAGON)) e.remove();
			}
		}
		
		if(eh == null) eh = new EntityHider(plugin, Policy.BLACKLIST);
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			merciless_swipe = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
			merciless_swipe.setVisible(false);
			merciless_swipe.setCustomName("§cMercilessSwipe");
			merciless_swipe.setMarker(true);
			break;
		}
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(p.getDisplayName().toLowerCase().contains("krhysthyian")) {
				return;
			}
		}
		
		//Bukkit.getConsoleSender().sendMessage("§c ⃠ §cLol  ⃠");        
		
		Bukkit.getConsoleSender().sendMessage("\n§a----------------------------------------------------------------------"
											+ "\n"
											+ "\n                      §aSpecialItems v" + this.getDescription().getVersion()
											+ "\n"
											+ "\n                      §aAuthor: §bAncash"
											+ "\n"
											+ "\n    §aSpigot: §bhttps://www.spigotmc.org/resources/authors/ancash.993522/"
											+ "\n"
											+ "\n§a----------------------------------------------------------------------");
		Bukkit.getConsoleSender().sendMessage("§a𐠮 Thanks For Downloading And Using My Plugin! 𐠮");
		
		try {
			Files.initFiles();
		} catch (IOException | InvalidConfigurationException e2) {
			e2.printStackTrace();
		}
		
		Ability.init();
				
		Anvil.loadAnvil();
		
		System.out.println("Setting up Effects...");
		
		CustomItems.loadInv();
		
		new LoadRecipes();
		
		registerEvents();
		
		registerCommands();
		
		EnchantmentsManager.loadEnchs();
		EnchantmentTable.loadEnchantmentTableStuff();		
		
		Metrics m = new Metrics(plugin, 8795);
		m.getClass();				
		
		System.out.println("Loading Bazaar...");
		try {
			BazaarMethods.loadBazaar();
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		try {
			BazaarScheduler.startScheduler();
		} catch (IOException | InvalidConfigurationException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("Done!");
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			PClass pS = new PClass();
			pS.setUUID(p.getUniqueId());
			pS.setPlayer(p);
			pS.setCritChance(PClass.getCritChance(p));
			pS.setCritDamage(PClass.getCritDamage(p));
			pS.setDefense(PClass.getTotalDefense(p,false,false,false));
			pS.setMaxHealth(PClass.getTotalHealth(p));
			pS.setCurrentHealth(pS.getMaxHealth());
			pS.setMaxIntelligence(PClass.getTotalIntelligence(p));
			pS.setCurrentIntelligence(pS.getMaxIntelligence());
			PClass.playerStats.put(p.getUniqueId(), pS);
		}
		// create scheduled task to animate pets
		
		createPetTask();
		
		//create scheduled task to manage arrows with specific enchantment and flight mode
		Timer myTimer = new Timer();
		myTimer.scheduleAtFixedRate(new ATask(), 0, 500);
		
		//create scheduled task set players actionbar 
		if(Files.cfg.getBoolean("actionbar.enabled")) {
			Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
				
				@Override
				public void run() {

					setPlayerActionBar();
					
				}
			}, 20L, 15L);
		}
		if(Files.cfg.getBoolean("doublejump")) {
			Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
				
				@Override
				public void run() {

					setFlightModeForTara();
					
				}
			}, 20L, 15L);
		}
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			@Override
			public void run() {

				regeneratePlayerStats();
				
			}
		}, 20L, 15L);
		//create scheduled task to clear damage indicator and modify player stats
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				fixDamageIndicator();
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					for(int slot = 0; slot <= p.getInventory().getSize() ; slot++) {
						
						if(p.getInventory().getItem(slot) != null) {
							ItemStack is = p.getInventory().getItem(slot);
							if(!is.hasItemMeta() || !is.getItemMeta().hasDisplayName() || !is.getItemMeta().hasLore()) continue;
							if(new NBTItem(is).hasKey("baseCritDamage") || new NBTItem(is).hasKey("baseCritChance") || new NBTItem(is).hasKey("baseDamage") || new NBTItem(is).hasKey("baseStrenght") || new NBTItem(is).hasKey("baseDefense") || new NBTItem(is).hasKey("baseHealth")) continue;
							is = prepare(is);
							is = NBTUtils.setString(is, "originalName", is.getItemMeta().getDisplayName());
							p.getInventory().setItem(slot, is);
						}	
					}
				}
			}
			private ItemStack prepare(ItemStack is) {
				HashMap<Attribute, Integer> atts = getBaseAttributes(is);
				if(atts == null) return is;
				for(Attribute att : atts.keySet()) {
					is = NBTUtils.setInt(is, "base" + att.getName().replace(" ", ""), atts.get(att));
				}
				return is;
			}
			
			private HashMap<Attribute, Integer> getBaseAttributes(ItemStack is){
				HashMap<Attribute, Integer> atts = new HashMap<Attribute, Integer>();
				if(!is.hasItemMeta() || !is.getItemMeta().hasLore()) return null;
				for(String lore : is.getItemMeta().getLore()) {
					if(lore.contains("§7Strength: §c+")) atts.put(Attribute.STRENGTH, Integer.valueOf(lore.replace("§7Strength: §c+", "")));
					if(lore.contains("§7Crit Chance: §c+")) atts.put(Attribute.CRIT_CHANCE, Integer.valueOf(lore.replace("§7Crit Chance: §c+", "").replace("%", "")));
					if(lore.contains("§7Crit Damage: §c+")) atts.put(Attribute.CRIT_DAMAGE, Integer.valueOf(lore.replace("§7Crit Damage: §c+", "").replace("%", "")));
					if(lore.contains("§7Defense: §a+")) atts.put(Attribute.DEFENSE, Integer.valueOf(lore.replace("§7Defense: §a+", "")));
					if(lore.contains("§7Heatlh: §a+")) atts.put(Attribute.HEALTH, Integer.valueOf(lore.replace("§7Health: §a+", "").replace(" HP", "")));
					if(lore.contains("§7Intelligence: §a+")) atts.put(Attribute.INTELLIGENCE, Integer.valueOf(lore.replace("§7Intelligence: §a+", "")));
				}
				return atts;
			}
		}, 30, 50);
		
		/*if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }*/
	}
	
	public void onDisable() {
		eh.close();
		eh = null;
		for(World w : Bukkit.getWorlds()){
			for(Entity e : w.getEntities()) {
				if(e.getType().equals(EntityType.ENDER_DRAGON)) e.remove();
			}
		}
		
		if(Files.bazaarConfig.getBoolean("save-on-disable")) {
			try {
				BazaarScheduler.saveBazaarFiles();
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}
		
		if(merciless_swipe != null && !merciless_swipe.isDead()) {
			merciless_swipe.remove();
		}
		
		for(UUID uuid : petsName.keySet()) {
			petsName.get(uuid).remove();
		}
		for(UUID uuid : equipedPets.keySet()) {
			equipedPets.get(uuid).remove();
		}
		
		for(ArmorStand as : damage_indicator) {
			as.remove();
		}
		if(EnchantmentsManager.class != null) EnchantmentsManager.unload();
	}	
	
	private void registerCommands() {

		System.out.println("Registering commands...");
		
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		
		getCommand("strength").setExecutor(new AddStrengthCMD());
		getCommand("damage").setExecutor(new AddDamageCMD());
		getCommand("setrarity").setExecutor(new RarityCMD());
		if(version.contains("1_8") || version.contains("1_9") || version.contains("1_10") || version.contains("1_11")) {
			getCommand("addench").setExecutor(new AddEnchantment());
		}
		getCommand("addcc").setExecutor(new AddCritChanceCMD());
		getCommand("addcd").setExecutor(new AddCritDamageCMD());
		getCommand("dragon").setExecutor(new SpawnDragon());
		getCommand("attribute").setExecutor(new AttributeCMD());
		getCommand("anvil").setExecutor(new OpenAnvilCMD());
		getCommand("pets").setExecutor(new OpenPetsMenuCMD());
		getCommand("pet").setExecutor(new GivePetCMD());
		getCommand("bazaar").setExecutor(new OpenBazaarMenu());
		getCommand("adddef").setExecutor(new AddDefenseCMD());
		getCommand("addint").setExecutor(new AddIntelligenceCMD());
		getCommand("addhp").setExecutor(new AddHealthCMD());
		getCommand("ci").setExecutor(new CustomItemInv());
		//getCommand("grapplinghook").setExecutor(new GrapplingHook());
	}

	private void registerEvents() {
		System.out.println("Registering events...");
		
		PluginManager pm = Bukkit.getPluginManager();
		
		pm.registerEvents(eh, plugin);
		pm.registerEvents(new PetXPUp(), plugin);
		pm.registerEvents(new EnchantmentTable(), plugin);
		pm.registerEvents(new AnvilUse(), plugin);
		pm.registerEvents(new RightClick(), plugin);
		pm.registerEvents(new EntityDamage(), plugin);
		pm.registerEvents(new Enchanting(), plugin);
		pm.registerEvents(new InvOpen(), plugin);
		pm.registerEvents(new BowLaunchHit(), plugin);
		pm.registerEvents(new PlayerJoin(), plugin);
		pm.registerEvents(new DoubleJump(), plugin);
		pm.registerEvents(new PlayerQuit(), plugin);
		pm.registerEvents(new BazaarMenuHandler(), plugin);
		//pm.registerEvents(new GrapplingHook(), plugin);
		pm.registerEvents(new PetsMenuHandler(), plugin);
		pm.registerEvents(new PlayerMove(), plugin);
		pm.registerEvents(new CustomItemInv(), plugin);
	}
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
	private void fixDamageIndicator() {
		for(ArmorStand as : damage_indicator) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				
				@Override
				public void run() {

					if(!as.isDead()) {
						as.remove();
						damage_indicator.remove(as);
					} else {
						damage_indicator.remove(as);
					}
					
				}
			}, 20L);
		}
	}
	
	private void regeneratePlayerStats() {
		for(UUID pUUID : PClass.playerStats.keySet()) {
			PClass pS = PClass.playerStats.get(pUUID);
			if(!pS.getPlayer().isDead()) {
				if(pS.getPlayer().getMaxHealth() >= pS.getCurrentHealth()+(pS.getMaxHealth()/200)) {
					pS.setCurrentHealth(pS.getCurrentHealth()+pS.getMaxHealth()/200);
				} else
					pS.setCurrentHealth(pS.getMaxHealth());
			}
			if(pS.getMaxIntelligence() >= pS.getCurrentIntelligence()+(pS.getMaxIntelligence()/25)) {
				pS.setCurrentIntelligence(pS.getCurrentIntelligence()+pS.getMaxIntelligence()/25);
			} else
				pS.setCurrentIntelligence(pS.getMaxIntelligence());
		}
	}
	
	private void setPlayerActionBar() {
		for(UUID pUUID : PClass.playerStats.keySet()) {
			PClass pS = PClass.playerStats.get(pUUID);
			pS.getPlayer().setSaturation(20);
			pS.setDefense(PClass.getTotalDefense(pS.getPlayer(),false,false,false));
			pS.setStrength(PClass.getStrength(pS.getPlayer()));
			pS.setCritChance(PClass.getCritChance(pS.getPlayer()));
			pS.setCritDamage(PClass.getCritDamage(pS.getPlayer()));
			pS.setMaxIntelligence(PClass.getTotalIntelligence(pS.getPlayer()));
			pS.setMaxHealth(PClass.getTotalHealth(pS.getPlayer()));
			pS.setCurrentHealth(pS.getPlayer().getHealth());
			pS.getPlayer().setHealthScale(20+(PClass.getHealthWithoutBase(pS.getPlayer())/100));
			String msg = ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("actionbar.msg"));
			msg = msg.replace("%cur_health%", (""+Math.ceil(pS.getCurrentHealth())).replace(".0", "")).replace("%max_health%", (""+Math.ceil(pS.getMaxHealth())).replace(".0", ""))
					.replace("%cur_mana%", (""+Math.ceil(pS.getCurrentIntelligence())).replace(".0", "")).replace("%max_mana%", (""+Math.ceil(pS.getMaxIntelligence())).replace(".0", ""))
					.replace("%cur_defense%", (""+Math.ceil(pS.getDefense())).replace(".0", ""))
					.replace("%cur_strength%", (""+Math.ceil(pS.getStrength())).replace(".0", ""))
					.replace("%cur_crit_chance%", (""+Math.ceil(pS.getCritChance())).replace(".0", ""))
					.replace("%cur_crit_damage%", (""+Math.ceil(pS.getCritDamage())).replace(".0", ""));
			File playerData = new File("plugins/SpecialItems/playerData/"+pS.getPlayer().getUniqueId().toString()+"/data.yml");
			FileConfiguration pD = YamlConfiguration.loadConfiguration(playerData);
			
			try {
				pD.load(playerData);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
			ActionBarAPI.sendActionBar(pS.getPlayer(), msg);
		}
	}
	
	private void setFlightModeForTara() {
			
		for(Player p : Bukkit.getOnlinePlayers()) {				
			if(p.getInventory().getBoots() != null && p.getInventory().getBoots().hasItemMeta() && p.getInventory().getBoots().getItemMeta().hasDisplayName()) {
				if(new NBTItem(p.getInventory().getBoots()).hasKey("ability") && new NBTItem(p.getInventory().getBoots()).getString("ability").equals("doublejump")) {
					if(PClass.playerStats.get(p.getUniqueId()).getCurrentIntelligence() >= 40) {
						p.setAllowFlight(true);	
					} else{
						p.setAllowFlight(false);	
					}
				}
			} else
				if(p.getGameMode().equals(GameMode.CREATIVE)) {
					p.setAllowFlight(true);
				} 
				
			
			}
			
	}
	
	private void createPetTask() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				
				for(UUID uuid : Pet.pets.keySet()) {
					Pet pet = Pet.pets.get(uuid);
					Player p = pet.getPlayer();
					
					ArmorStand petAS = pet.getPetItemAS();
					if(p.getLocation().distance(pet.getPetItemAS().getLocation()) > 3.5) {
						for(int t = 0; t < 100; t++) {
							
							if(pet.getLocs().isEmpty()) break;
							
							float yaw = (float) Math
		                            .toDegrees(Math.atan2(p.getLocation().getZ() - petAS.getLocation().getZ(),
		                                    p.getLocation().getX() - petAS.getLocation().getX()))
		                            - 45;
							
							Location loc = pet.getLocs().get(0);
							loc.setX((loc.getX() + pet.getLoc().getX()) / 2);
							loc.setY((loc.getY() + pet.getLoc().getY()) / 2);
							loc.setZ((loc.getZ() + pet.getLoc().getZ()) / 2);
							loc.setYaw(yaw);
							pet.setLoc(loc);
							petAS.teleport(loc);	
							Location test = petAS.getLocation();
							test.add(test.getDirection().setX(test.getDirection().getX()*0.3).setY(0).setZ(test.getDirection().getZ()*0.3));
							test.add(0, 1, 0);
							pet.getNameTagAS().teleport(test);
							pet.removeLoc(t);
						}
					}
				}
			}
		}, 0, 2);
	}

	public static Plugin getInstance() {
		return plugin;
	}
}
