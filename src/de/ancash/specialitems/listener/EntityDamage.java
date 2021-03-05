package de.ancash.specialitems.listener;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Animals;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Monster;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import de.ancash.specialitems.Files;
import de.ancash.specialitems.PClass;
import de.ancash.specialitems.SpecialItems;
import de.ancash.specialitems.e.EntityData;
import de.ancash.specialitems.pets.PetMethods;
import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtapi.NBTItem;

public class EntityDamage implements Listener {
	
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity().getType().equals(EntityType.DROPPED_ITEM)) {
			return;
		}
		if(e.getEntity() instanceof Player && e.getCause().equals(DamageCause.FALL) ) {
			return;
		}
		if(e.getEntity() instanceof Player) {
			return;
		}
		if(e.getEntityType().equals(EntityType.ARMOR_STAND)) {
			return;
		}
		
		if(e.getCause().equals(DamageCause.ENTITY_ATTACK)) {
			return;
		}
		if(e.getCause().equals(DamageCause.PROJECTILE)) {
			return;
		}
		double dmg = e.getDamage();
		
		dmg = Math.ceil(dmg);
		
		String damage = String.valueOf(dmg);
		
		damage = "§7"+damage.replace(".0", "");
		
		setDamageIndicator(e.getEntity().getLocation(), damage);
	}
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
	public void onDamageByEntity(EntityDamageByEntityEvent e) throws InterruptedException {
		if(e.getCause().equals(DamageCause.THORNS)) {
			return;
		}
		ArrayList<String> pluginnames = new ArrayList<String>();
		for(Plugin pl : Bukkit.getServer().getPluginManager().getPlugins()) {
			pluginnames.add(pl.getName());
		}
		if(pluginnames.contains("Citizens")) {
			boolean isCitizensNPC = e.getEntity().hasMetadata("NPC");
			if(isCitizensNPC) {
				return;
			}
		}
		if(e.getDamager().getCustomName() != null && e.getDamager().getCustomName().equals("§cMercilessSwipe")) {
			double dmg = e.getDamage();
			dmg = Math.ceil(dmg);
			
			String damagestring = String.valueOf(dmg);
			damagestring=damagestring.replace(".0", "");
			
			damagestring = "§7"+damagestring;
			
			setDamageIndicator(e.getEntity().getLocation(), damagestring);
			return;
		}
		if(e.getEntity().getType().equals(EntityType.DROPPED_ITEM)) {
			return;
		}
		
		if(e.getEntity() instanceof NPC) {
			return;
		}
		if(e.getDamager().getType().equals(EntityType.FIREBALL)) {
			Fireball fb = (Fireball) e.getDamager();
			if(fb.getShooter() instanceof Player) {
				e.setDamage(50);
			}
		}
		
		if(e.getCause().equals(DamageCause.VOID) 
				|| e.getCause().equals(DamageCause.DROWNING)
				|| e.getCause().equals(DamageCause.WITHER)
				|| e.getCause().equals(DamageCause.POISON)
				|| e.getEntityType().equals(EntityType.ARMOR_STAND)) {
			return;
		}
		if(!(e.getDamager() instanceof Player)) {
			
			if(e.getDamager().getType().equals(EntityType.ARROW)) {
				Arrow a = (Arrow) e.getDamager();
				if(a.getShooter() instanceof Player) {
					Player p = (Player) a.getShooter();
					PClass pS = PClass.playerStats.get(p.getUniqueId());
					if(pS == null) {
						return;
					}
					if(!e.getEntity().isDead()) {
						pS.setLastAttackedEntity(e.getEntity().getUniqueId());
					} else {
						pS.setLastAttackedEntity(null);
					}
					int critchance = (int) pS.getCritChance();
					int critdamage = (int)pS.getCritDamage();
					int strength = (int) pS.getStrength();
					int damage = 0;
					if(p.getItemInHand() != null && p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().getLore() != null) {
						damage = getDamage((ArrayList<String>) p.getItemInHand().getItemMeta().getLore());
					}
					int cubismlvl = 0;
					int powerlvl = 0;
					int snipelevel = 0;
					int dragonhunterlvl = 0;
					if(p.getItemInHand() != null && p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasEnchant(Enchantment.ARROW_DAMAGE)) {
						powerlvl = p.getItemInHand().getItemMeta().getEnchantLevel(Enchantment.ARROW_DAMAGE);
					}
					if(SpecialItems.ench_aiming != null) {
						if(p.getItemInHand() != null && p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasEnchant(SpecialItems.ench_snipe)) {
							snipelevel = p.getItemInHand().getItemMeta().getEnchantLevel(SpecialItems.ench_snipe);
						}
						if(p.getItemInHand() != null && p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasEnchant(SpecialItems.ench_cubism)) {
							cubismlvl = p.getItemInHand().getItemMeta().getEnchantLevel(SpecialItems.ench_cubism);
						}
						if(p.getItemInHand() != null && p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasEnchant(SpecialItems.ench_dragonhunter)) {
							dragonhunterlvl = p.getItemInHand().getItemMeta().getEnchantLevel(SpecialItems.ench_dragonhunter);
						}
					}
					double weapondamage = e.getDamage();
					if(powerlvl != 0) {
						weapondamage = weapondamage/(1+((1+powerlvl)*0.25));
					}
					weapondamage = weapondamage+damage;
					double finaldamage = ((5+weapondamage+(strength/5))*(1+strength/100));
					double dmgwithoutenchs = finaldamage;
					if(powerlvl != 0) {
						finaldamage = finaldamage+(dmgwithoutenchs*(powerlvl*0.08));
					}
					if(snipelevel != 0) {
						finaldamage = finaldamage+((0.01*Math.sqrt(p.getLocation().distanceSquared(a.getLocation()))/10)*dmgwithoutenchs);
					}
					if(cubismlvl != 0) {
						if(e.getEntity().getType().equals(EntityType.CREEPER) || e.getEntity().getType().equals(EntityType.MAGMA_CUBE) || e.getEntity().getType().equals(EntityType.SLIME)) {
							finaldamage = finaldamage+(dmgwithoutenchs*(cubismlvl*0.1));
						}
					}
					if(e.getEntity().getType().equals(EntityType.ENDER_DRAGON)) {
						if(dragonhunterlvl != 0) {
							finaldamage = finaldamage + (dmgwithoutenchs*(0.08*dragonhunterlvl));
						}
					}
					Random rnd = new Random();
					int critchancernd = rnd.nextInt(100);
					
					boolean activatedcrithit = false;
					
					if(critchancernd <= critchance) {
						finaldamage = finaldamage * (1+critdamage/100);
						activatedcrithit = true;
					} 
					
					if(e.getEntity() instanceof Player) {
						finaldamage = applyDefense(finaldamage, (int) pS.getDefense());
					}
					ItemStack helmet = p.getInventory().getHelmet();
					ItemStack chestplate = p.getInventory().getChestplate();
					ItemStack leggings = p.getInventory().getLeggings();
					ItemStack boots = p.getInventory().getBoots();
					boolean tara_boost = false;
					if(helmet != null && helmet.getItemMeta().getDisplayName().toLowerCase().contains("tarantula helmet")
							&& chestplate != null && chestplate.getItemMeta().getDisplayName().toLowerCase().contains("tarantula chestplate")
							&& leggings != null && leggings.getItemMeta().getDisplayName().toLowerCase().contains("tarantula leggings") 
							&& boots != null && boots.getItemMeta().getDisplayName().toLowerCase().contains("tarantula boots")
							&& pS.getStrikesOnLastAttackedEntity() == 4) {
						pS.setStrikesOnLastAttackedEntity(0);
						finaldamage = finaldamage*2;
						tara_boost = true;
					}
					if(a.getCustomName() != null && a.getCustomName().contains("runaan")) finaldamage = finaldamage*0.4;
	
					if(e.getEntity() instanceof Player) {
						return;
					}
					
					e.setDamage(finaldamage);
					
					double dmg = finaldamage;
					
					dmg = Math.ceil(dmg);
					
					String damagestring = String.valueOf(dmg);
					damagestring=damagestring.replace(".0", "");
					
					if(activatedcrithit == true) {
						damagestring = addCritTexture(damagestring);
					} else {
						damagestring = "§7"+damagestring;
					}
					if(tara_boost == true) {
						damagestring = "§7+"+damagestring;
					}
					setDamageIndicator(e.getEntity().getLocation(), damagestring);
					return;
				}
			}
			if(!(e.getEntity() instanceof Player)) {
				return;
			}
			
			double finaldamage = e.getDamage();
			
			if(e.getEntity() instanceof Player) {
				PClass pS = PClass.playerStats.get(((Player)e.getEntity()).getUniqueId());
				if(pS == null) {
					return;
				}
				double defense = pS.getDefense();
				if(defense != 0) {
					finaldamage = applyDefense(finaldamage, (int) defense);
				}
			}
			e.setDamage(finaldamage);
			finaldamage = e.getDamage();
			finaldamage = Math.ceil(finaldamage);
			
			String damage = String.valueOf(finaldamage);
		
			damage=damage.replace(".0", "");
						
			setDamageIndicator(e.getEntity().getLocation(), damage);
			return;
		}
		if(!(e.getDamager() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getDamager();
		PClass pS = PClass.playerStats.get(p.getUniqueId());
		if(pS == null) {
			return;
		}
		int critchance = (int) PClass.getCritChance(p);
		double critdamage = PClass.getCritDamage(p);
		double strength =  pS.getStrength();
		double finaldamage = 0;
		
		int sharpness = p.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL);
		int BoA = 0;
		int smite = 0;
		int firstStrike = 0;
		int cubism = 0;
		int dragonhunterlvl = 0;
		int enderslayerlvl = 0;
		if(SpecialItems.ench_aiming != null) {
			BoA = p.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS);
			smite = p.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD);
			firstStrike = p.getItemInHand().getEnchantmentLevel(SpecialItems.ench_firstStrike);
			cubism = p.getItemInHand().getEnchantmentLevel(SpecialItems.ench_cubism);
			dragonhunterlvl = p.getItemInHand().getEnchantmentLevel(SpecialItems.ench_dragonhunter);
			enderslayerlvl = p.getItemInHand().getEnchantmentLevel(SpecialItems.ench_enderslayer);
		}
		
		double weapondamage = e.getDamage()-(sharpness*1.25);
		if(e.getEntity().getType().equals(EntityType.SKELETON)
				|| e.getEntity().getType().equals(EntityType.ZOMBIE)
				|| e.getEntity().getType().equals(EntityType.PIG_ZOMBIE)) {
			weapondamage = weapondamage - (smite*2.5);
		}
		if(e.getEntity().getType().equals(EntityType.SPIDER)
				|| e.getEntity().getType().equals(EntityType.CAVE_SPIDER)
				|| e.getEntity().getType().equals(EntityType.SILVERFISH)
				|| e.getEntity().getType().equals(EntityType.ENDERMITE)) {
			weapondamage = weapondamage - (BoA*2.5);
		}
		if(p.getItemInHand().getType() != Material.AIR) {
			if(p.getItemInHand().getItemMeta().getLore() != null) {
				weapondamage = weapondamage+getDamage((ArrayList<String>) p.getItemInHand().getItemMeta().getLore());
				if(SpecialItems.equipedPets.containsKey(p.getUniqueId()) && p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().getDisplayName() != null && p.getItemInHand().getItemMeta().getDisplayName().toLowerCase().contains("aspect of the dragons")) {
					weapondamage = weapondamage + getOneWithTheDragonDamage(p);
				}
			}
		}
		
		if(!EntityData.entity_uuid.contains(e.getEntity().getUniqueId())) {
			EntityData.register_new(e.getEntity());
		}
		
		EntityData.addPlayer(e.getEntity(), p);
		
		Random rnd = new Random();
		int critchancernd = rnd.nextInt(100);
		
		boolean activatedcrithit = false;
			
		finaldamage = ((5+weapondamage+(strength/5))*(1+strength/100));
				
		double damagewithoutenchs = finaldamage;
		
		if(p.getItemInHand() != null && p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasEnchant(Enchantment.DAMAGE_ALL)) {
			finaldamage = finaldamage +damagewithoutenchs*(0.05*sharpness);
		}
		if(p.getItemInHand() != null && p.getItemInHand().hasItemMeta() && SpecialItems.ench_aiming != null && p.getItemInHand().getItemMeta().hasEnchant(SpecialItems.ench_firstStrike)) {
			if(pS.getLastAttackedEntity() == null || !pS.getLastAttackedEntity().equals(e.getEntity().getUniqueId())) {
				finaldamage = finaldamage +damagewithoutenchs*(0.25*firstStrike);
			}
		}
		if(p.getItemInHand() != null && p.getItemInHand().hasItemMeta() && SpecialItems.ench_aiming != null && p.getItemInHand().getItemMeta().hasEnchant(SpecialItems.ench_cubism)) {
			if(e.getEntity().getType().equals(EntityType.CREEPER) || e.getEntity().getType().equals(EntityType.MAGMA_CUBE) || e.getEntity().getType().equals(EntityType.SLIME)) {
				finaldamage = finaldamage +damagewithoutenchs*(0.1*cubism);
			}
		}
		if(e.getEntity().getType().equals(EntityType.SKELETON)
				|| e.getEntity().getType().equals(EntityType.ZOMBIE)
				|| e.getEntity().getType().equals(EntityType.PIG_ZOMBIE)) {
			finaldamage = finaldamage+(damagewithoutenchs*(smite*0.08));
		}
		if(e.getEntity().getType().equals(EntityType.SPIDER)
				|| e.getEntity().getType().equals(EntityType.CAVE_SPIDER)
				|| e.getEntity().getType().equals(EntityType.SILVERFISH)
				|| e.getEntity().getType().equals(EntityType.ENDERMITE)) {
			finaldamage = finaldamage+(addBaneOfArthropods(damagewithoutenchs, BoA));
		}
		if(dragonhunterlvl != 0 && e.getEntity().getType().equals(EntityType.ENDER_DRAGON)) {
			finaldamage = finaldamage + (damagewithoutenchs*(0.08*dragonhunterlvl));
		}
		if(enderslayerlvl != 0) {
			if(e.getEntity().getType().equals(EntityType.ENDER_DRAGON) || e.getEntity().getType().equals(EntityType.ENDERMAN)) {
				finaldamage = finaldamage + (damagewithoutenchs*(0.12*enderslayerlvl));
			}
		}
		if(critchancernd <= critchance) {
			finaldamage = finaldamage * (1+(critdamage/100));
			activatedcrithit = true;
		} 
		
		pS.setLastAttackedEntity(e.getEntity().getUniqueId());
		
		ItemStack helmet = p.getInventory().getHelmet();
		ItemStack chestplate = p.getInventory().getChestplate();
		ItemStack leggings = p.getInventory().getLeggings();
		ItemStack boots = p.getInventory().getBoots();
		boolean taraboost = false;
		if(helmet != null && helmet.hasItemMeta() && helmet.getItemMeta().hasDisplayName() && helmet.getItemMeta().getDisplayName().toLowerCase().contains("tarantula helmet")
				&& chestplate != null && chestplate.hasItemMeta() && chestplate.getItemMeta().hasDisplayName() && chestplate.getItemMeta().getDisplayName().toLowerCase().contains("tarantula chestplate")
				&& leggings != null && leggings.hasItemMeta() && leggings.getItemMeta().hasDisplayName() && leggings.getItemMeta().getDisplayName().toLowerCase().contains("tarantula leggings") 
				&& boots != null && boots.hasItemMeta() && boots.getItemMeta().hasDisplayName() && boots.getItemMeta().getDisplayName().toLowerCase().contains("tarantula boots")
				&& pS.getStrikesOnLastAttackedEntity() >= 4) {
			pS.setStrikesOnLastAttackedEntity(pS.getStrikesOnLastAttackedEntity()-4);
			finaldamage = finaldamage*2;
			taraboost = true;
		} 
		boolean explosion = false;
		if(e.getCause().equals(DamageCause.BLOCK_EXPLOSION) || e.getCause().equals(DamageCause.ENTITY_EXPLOSION)) {
			explosion = true;
		}
		boolean projectile = false;
		if(e.getCause().equals(DamageCause.PROJECTILE)) {
			explosion = true;
		}
		boolean firelava = false;
		if(e.getCause().equals(DamageCause.FIRE) || e.getCause().equals(DamageCause.LAVA) || e.getCause().equals(DamageCause.FIRE_TICK)) {
			explosion = true;
		}
		if(e.getEntity() instanceof Player) {
			double defense = 0;
			defense = PClass.getTotalDefense(p, explosion, projectile, firelava);
			if(e.getEntity() instanceof Player && !e.getCause().equals(DamageCause.FALL) && defense != 0) {
				finaldamage = applyDefense(finaldamage, (int) defense);
			}
		}
		
		if(e.getDamage() == 700) {
			e.setDamage(700);
			e.getEntity().setVelocity(p.getLocation().toVector().subtract(e.getEntity().getLocation().toVector()).multiply(-10));
		} else {
			e.setDamage(DamageModifier.ARMOR, 0);
			e.setDamage(finaldamage);
			if(p.getItemInHand() != null && p.getItemInHand().hasItemMeta() && SpecialItems.ench_aiming != null && p.getItemInHand().getItemMeta().hasEnchant(SpecialItems.ench_lifesteal)) {
				double lifesteal_healed = e.getDamage()*(0.01*p.getItemInHand().getItemMeta().getEnchantLevel(SpecialItems.ench_lifesteal));
				if(p.getHealth() + lifesteal_healed >= p.getMaxHealth()) {
					p.setHealth(p.getMaxHealth());
				} else {
					p.setHealth(p.getHealth()+lifesteal_healed);
				}
			}
		}
		
		if(e.getEntity() instanceof Player) {
			return;
		}
		
		if(SpecialItems.equipedPets.containsKey(p.getUniqueId()) && e.getDamage() != 700) {
			double merciless_swip_chance = Files.petsConfig.getDouble("pets."+new NBTItem(SpecialItems.equipedPets.get(p.getUniqueId()).getItemInHand()).getString("pet")+".upgrade_per_level." + new NBTItem(SpecialItems.equipedPets.get(p.getUniqueId()).getItemInHand()).getString("rarity") + ".merciless_swipe")
					* (PetMethods.getPetLvl(SpecialItems.equipedPets.get(p.getUniqueId()).getItemInHand()));
			if(new Random().nextInt(100) + 1 <= merciless_swip_chance) {
				if(e.getEntity() instanceof Monster) {
					Monster m = (Monster) e.getEntity();
					m.damage(e.getDamage(), SpecialItems.merciless_swipe);
				} else {
					if(e.getEntity() instanceof Animals) {
						Animals m = (Animals) e.getEntity();
						m.damage(e.getDamage(), SpecialItems.merciless_swipe);
					} else {
						if(e.getEntity() instanceof Slime) {
							Slime m = (Slime) e.getEntity();
							m.damage(e.getDamage(), SpecialItems.merciless_swipe);
						}
					}
				}
			}
			
			boolean apex_p = true;
			for(Entity entity : e.getDamager().getNearbyEntities(15, 15, 15)) {
				if(entity instanceof Animals || entity instanceof Monster || entity instanceof Slime) {
					if(!entity.equals(e.getEntity())) {
						apex_p = false;
						break;
					}
				}
			}
			if(apex_p) {
				double apex_predator =  Files.petsConfig.getDouble("pets."+new NBTItem(SpecialItems.equipedPets.get(p.getUniqueId()).getItemInHand()).getString("pet")+".upgrade_per_level." + new NBTItem(SpecialItems.equipedPets.get(p.getUniqueId()).getItemInHand()).getString("rarity") + ".apex_predator") * (PetMethods.getPetLvl(SpecialItems.equipedPets.get(p.getUniqueId()).getItemInHand()));
				e.setDamage(damagewithoutenchs*(apex_predator/100) + e.getDamage());
			}
			
			if(e.getEntityType().equals(EntityType.ENDER_DRAGON) || e.getEntityType().equals(EntityType.ENDERMAN) || e.getEntityType().equals(EntityType.ENDERMITE)) {
				double end_strike =  Files.petsConfig.getDouble("pets."+new NBTItem(SpecialItems.equipedPets.get(p.getUniqueId()).getItemInHand()).getString("pet")+".upgrade_per_level." + new NBTItem(SpecialItems.equipedPets.get(p.getUniqueId()).getItemInHand()).getString("rarity") + ".end_strike") * (PetMethods.getPetLvl(SpecialItems.equipedPets.get(p.getUniqueId()).getItemInHand()));
				e.setDamage(damagewithoutenchs*(end_strike/100) + e.getDamage());
			}
		}
		
		double dmg = e.getDamage();
		
		dmg = Math.ceil(dmg);
		
		String damage = String.valueOf(dmg);
		damage=damage.replace(".0", "");
		
		if(activatedcrithit == true) {
			damage = addCritTexture(damage);
		} else {
			damage = "§7"+damage;
		}
		if(taraboost == true) {
			damage="+"+damage;
		}
		setDamageIndicator(e.getEntity().getLocation(), damage);
	}
	
	private double applyDefense(double damage, int defense) {
		return damage - ((defense/(defense+100))*damage);
	}
	
	private double getOneWithTheDragonDamage(Player p) {
		double dmg = 0;
		ItemStack pet = SpecialItems.equipedPets.get(p.getUniqueId()).getItemInHand();
		dmg = Files.petsConfig.getDouble("pets."+new NBTItem(pet).getString("pet") + ".upgrade_per_level." + new NBTItem(pet).getString("rarity") + ".one_with_the_dragon_damage") * PetMethods.getPetLvl(pet);
		return dmg;
	}
	
	private double addBaneOfArthropods(double damage, int lvl) {
		return damage * (lvl * 0.08);
	}
	
	private void setDamageIndicator(Location loc, String displayname) {
				
		if(!Files.cfg.getBoolean("enable-damage-indicator")) {
			return;
		}
		
		double randomX = Math.random();
		double randomY = Math.random();
		double randomZ = Math.random();
		
		randomX = -0.5+randomX;
		randomY = 0.25+randomY;
		randomZ = -0.5+randomZ;
		
		ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(loc.add(randomX, randomY, randomZ), EntityType.ARMOR_STAND);
		as.setVisible(false);
		as.setGravity(false);
		as.setMarker(true);
		as.setCustomNameVisible(true);
		as.setCustomName(displayname);
		NBTEntity nbtas = new NBTEntity(as);
		nbtas.setBoolean("Invulnerable", true);
		SpecialItems.damage_indicator.add(as);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(SpecialItems.plugin, new Runnable() {
			
			@Override
			public void run() {

				if(!as.isDead()) {
					as.remove();
				}
				if(as.isDead()) {
					SpecialItems.damage_indicator.remove(as);
				}
				
			}
		}, 20L);
	}
	
	private String addCritTexture(String str) {
		String new_string = null;
		
		if(str.length() == 1) {
			new_string = "§f✦§e"+str+"§c✦";
		}
		if(str.length() == 2) {
			new_string = "§f✦"+String.valueOf(str.charAt(0))+"§e"+String.valueOf(str.charAt(1))+"§c✦";
		}
		if(str.length() == 3) {
			new_string = "§f✦"+String.valueOf(str.charAt(0))+"§e"+String.valueOf(str.charAt(1))+"§6"+String.valueOf(str.charAt(2))+"§c✦";
		}
		if(str.length() == 4) {
			new_string = "§f✦"+String.valueOf(str.charAt(0))+"§e"+String.valueOf(str.charAt(1))+"§6"+String.valueOf(str.charAt(2))+"§c"+String.valueOf(str.charAt(3))+"✦";
		}
		if(str.length() == 5) {
			new_string = "§f✦"+String.valueOf(str.charAt(0))+"§e"+String.valueOf(str.charAt(1))+"§6"+String.valueOf(str.charAt(2))+"§c"+String.valueOf(str.charAt(3))+String.valueOf(str.charAt(4))+"§f✦";
		}
		if(str.length() == 6) {
			new_string = "§f✦"+String.valueOf(str.charAt(0))+"§e"+String.valueOf(str.charAt(1))+"§6"+String.valueOf(str.charAt(2))+"§c"+String.valueOf(str.charAt(3))+String.valueOf(str.charAt(4))+String.valueOf(str.charAt(5))+"§f✦";
		}
		if(str.length() == 7) {
			new_string = "§f✦"+String.valueOf(str.charAt(0))+"§e"+String.valueOf(str.charAt(1))+"§6"+String.valueOf(str.charAt(2))+String.valueOf(str.charAt(3))+"§c"+String.valueOf(str.charAt(4))+String.valueOf(str.charAt(5))+String.valueOf(str.charAt(6))+"§f✦";
		}
		return new_string;
	}
	
	private int getDamage(ArrayList<String> lore) {
		int damage = 0;
		if(lore == null) {
			return damage;
		}
		for(String s : lore) {
			if(s.contains("§7"+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.damage.name"))+": §c+")) {
				damage = Integer.parseInt(s.replace("§7"+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.damage.name"))+": §c+", ""));
			}
		}
		return damage;
	}
}
