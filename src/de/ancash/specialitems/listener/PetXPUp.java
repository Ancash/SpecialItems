package de.ancash.specialitems.listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import de.ancash.specialitems.Files;
import de.ancash.specialitems.PClass;
import de.ancash.specialitems.SpecialItems;
import de.ancash.specialitems.pets.Pet;
import de.ancash.specialitems.pets.PetMethods;
import de.tr7zw.nbtapi.NBTItem;

public class PetXPUp implements Listener {
	
	private ItemStack addXPtoPet(Player p, ItemStack is, String type) throws IOException {
		
		PClass pS = PClass.playerStats.get(p.getUniqueId());
		pS.setLastSkill("mining");
		
		File playerData = new File("plugins/SpecialItems/playerData/"+p.getUniqueId().toString() + "/data.yml");
		FileConfiguration pD = YamlConfiguration.loadConfiguration(playerData);
		try {
			pD.load(playerData);
		} catch (IOException | InvalidConfigurationException e1) {
			e1.printStackTrace();
		}
		
		if(is.hasItemMeta() && is.getItemMeta().hasLore() && is.getItemMeta().getLore().contains("               ")) {
			/*if(!is.getItemMeta().getLore().contains("§8Mining Pet")) {
				gainedXP = gainedXP*0.25;
			}*/
			ItemStack cfgIS = null;
			String texture = null;
			double total_xp = 0;
			NBTItem nbt = new NBTItem(SpecialItems.equipedPets.get(p.getUniqueId()).getItemInHand());
			for(int i = 1; i<29; i++) {
				
				if(SpecialItems.equipedPets.get(p.getUniqueId()).getItemInHand().getItemMeta().getDisplayName().contains("100") || pD.getDouble(i+".xp") != nbt.getDouble("xp") || pD.getItemStack("pets."+i) == null || !nbt.getString("rarity").equals(pD.getString(i+".rarity")) || !nbt.getString("pet").equals(pD.getString(i+".type")) || !nbt.getString("texture").equals(pD.getString(i+".texture"))) {
					continue;
				}
				
				
				cfgIS = pD.getItemStack("pets."+i);
				double gainedXP = Files.petsConfig.getDouble("pets."+pD.getString(i+".type")+".xp-block-mob-fish."+type+".xp"); 
				if(gainedXP == 0) {
					return is;
				}
				gainedXP = gainedXP + (gainedXP * ((Files.petsConfig.getDouble("pets."+pD.getString(i+".type")+".upgrade_per_level."+pD.getString(i+".rarity")+".xp_boost") * PetMethods.getPetLvl(cfgIS))/100));
				total_xp = gainedXP+pD.getDouble(i+".xp");
				texture = pD.getString(i+".texture");
				if(cfgIS.getItemMeta().getDisplayName().contains("100")) {
					return is;
				}
				NBTItem nbtitem = new NBTItem(cfgIS);
				if(PetMethods.getCurrentPetLevel(cfgIS) <= 100 && !cfgIS.getItemMeta().getDisplayName().contains("100")) {
					if(pD.getString(i+".rarity").equals("common") && PetMethods.getCurrentPetLevel(cfgIS)-1 < 100 && total_xp >= Pet.common_pet_xp_requirements.get(PetMethods.getCurrentPetLevel(cfgIS)-2)) {
						total_xp = total_xp-Pet.common_pet_xp_requirements.get(PetMethods.getCurrentPetLevel(cfgIS)-2);
						cfgIS = PetMethods.addPetLevel(cfgIS);
						
					} else {
						if(pD.getString(i+".rarity").equals("uncommon") && PetMethods.getCurrentPetLevel(cfgIS)-1 < 100 && total_xp >= Pet.uncommon_pet_xp_requirements.get(PetMethods.getCurrentPetLevel(cfgIS)-2)) {
							total_xp = total_xp-Pet.uncommon_pet_xp_requirements.get(PetMethods.getCurrentPetLevel(cfgIS)-2);
							cfgIS = PetMethods.addPetLevel(cfgIS);
							
						} else {
							if(pD.getString(i+".rarity").equals("rare") && PetMethods.getCurrentPetLevel(cfgIS)-1 < 100 && total_xp >= Pet.rare_pet_xp_requirements.get(PetMethods.getCurrentPetLevel(cfgIS)-2)) {
								total_xp = total_xp-Pet.rare_pet_xp_requirements.get(PetMethods.getCurrentPetLevel(cfgIS)-2);
								cfgIS = PetMethods.addPetLevel(cfgIS);
						
							} else {
								if(pD.getString(i+".rarity").equals("epic") && PetMethods.getCurrentPetLevel(cfgIS)-1 < 100 && total_xp >= Pet.epic_pet_xp_requirements.get(PetMethods.getCurrentPetLevel(cfgIS)-2)) {
									total_xp = total_xp-Pet.epic_pet_xp_requirements.get(PetMethods.getCurrentPetLevel(cfgIS)-2);
									cfgIS = PetMethods.addPetLevel(cfgIS);
								} else {
									if(pD.getString(i+".rarity").equals("legendary") && PetMethods.getCurrentPetLevel(cfgIS)-1 < 100 && total_xp >= Pet.legendary_pet_xp_requirements.get(PetMethods.getCurrentPetLevel(cfgIS)-2)) {
										total_xp = total_xp-Pet.legendary_pet_xp_requirements.get(PetMethods.getCurrentPetLevel(cfgIS)-2);
										cfgIS = PetMethods.addPetLevel(cfgIS);
									} else {
										
									}
								}
							}
						}
					}
				}
				pD.set(i+".xp", total_xp);
				SkullMeta sm = (SkullMeta) cfgIS.getItemMeta();
				sm.setOwner(p.getDisplayName());
				cfgIS.setItemMeta(sm);
				pD.set("pets."+i, cfgIS);
				nbtitem.setDouble("xp", pD.getDouble(i+".xp"));
				nbtitem.setString("pet", pD.getString(i+".type"));
				nbtitem.setString("rarity", pD.getString(i+".rarity"));
				nbtitem.setString("texture", texture);
				cfgIS = nbtitem.getItem();
				ItemMeta  im = cfgIS.getItemMeta();
				ArrayList<String> lore = new ArrayList<String>();
				for(String str : Files.petsConfig.getStringList("pets."+pD.getString(i+".type")+"."+pD.getString(i+".rarity")+".lore")) {
					lore.add(str);
				}
				im.setLore(lore);
				cfgIS.setItemMeta(im);
				SpecialItems.equipedPets.get(p.getUniqueId()).setItemInHand(PetMethods.preparePet(PetMethods.setTexture(cfgIS, texture), p));
				
				String displayname = cfgIS.getItemMeta().getDisplayName().split("] ")[0].replace(" ", "").replace("Lvl", "Lv").replace("§7[", "§8[§7")+"§8]";
				displayname = displayname +String.valueOf(cfgIS.getItemMeta().getDisplayName().split("] ")[1].charAt(0)) + String.valueOf(cfgIS.getItemMeta().getDisplayName().split("] ")[1].charAt(1))
				+ " " + p.getDisplayName() + "'s ";
				for(int t = 3; t<cfgIS.getItemMeta().getDisplayName().replace("-", " ").split(" ").length; t++) {
					displayname = displayname + " " + StringUtils.capitalize(cfgIS.getItemMeta().getDisplayName().replace("-", " ").split(" ")[t]);
				}
				displayname = displayname.replace("§4", "").replace("§r", "");
				SpecialItems.petsName.get(p.getUniqueId()).setCustomName(displayname);
				pD.save(playerData);
				break;
				
			}
		}
		return is;
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDeathByPlayer(EntityDeathEvent e) throws IOException {
		if(e.getEntity().getKiller() instanceof Player) {
			Player p = e.getEntity().getKiller();
			
			if(SpecialItems.equipedPets.containsKey(p.getUniqueId())) {
				
				addXPtoPet(p, SpecialItems.equipedPets.get(p.getUniqueId()).getItemInHand(), e.getEntity().getType().name());
				
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerCatchFish(PlayerFishEvent e) throws IOException {
		if(e.getCaught() == null) {
			return;
		}
		if(State.CAUGHT_FISH.equals(e.getState())){
			ItemStack fish = ((Item) e.getCaught()).getItemStack();
			String type = "";
			switch (fish.getData().toString()) {
			case "RAW_FISH(0)":
				type = "raw_fish";
				break;
			case "RAW_FISH(1)":
				type = "salmon";
				break;
			case "RAW_FISH(2)":
				type = "clownfish";
				break;
			case "RAW_FISH(3)":
				type = "pufferfish";
				break;
			default:
				break;
			}
			if(type.equals("")) {
				return;
			}
			if(SpecialItems.equipedPets.containsKey(e.getPlayer().getUniqueId())) {
				addXPtoPet(e.getPlayer(), SpecialItems.equipedPets.get(e.getPlayer().getUniqueId()).getItemInHand(), type);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent e) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, IOException, InvalidConfigurationException {
		Player p = e.getPlayer();
		
		if(SpecialItems.equipedPets.containsKey(p.getUniqueId())) {
							
			addXPtoPet(p, SpecialItems.equipedPets.get(p.getUniqueId()).getItemInHand(), e.getBlock().getType().name());
			
		}
		
		/*if(e.getBlock().getType().equals(Material.COAL_ORE)
				|| e.getBlock().getType().equals(Material.DIAMOND_ORE)
				|| e.getBlock().getType().equals(Material.EMERALD_ORE)
				|| e.getBlock().getType().equals(Material.GLOWING_REDSTONE_ORE)
				|| e.getBlock().getType().equals(Material.GOLD_ORE)
				|| e.getBlock().getType().equals(Material.IRON_ORE)
				|| e.getBlock().getType().equals(Material.LAPIS_ORE)
				|| e.getBlock().getType().equals(Material.QUARTZ_ORE)
				|| e.getBlock().getType().equals(Material.REDSTONE_ORE)
				|| e.getBlock().getType().equals(Material.STONE)
				|| e.getBlock().getType().equals(Material.COBBLESTONE)
				|| e.getBlock().getType().equals(Material.ICE)
				|| e.getBlock().getType().equals(Material.PACKED_ICE)
				|| e.getBlock().getType().equals(Material.NETHERRACK)
				|| e.getBlock().getType().equals(Material.SAND)
				|| e.getBlock().getType().equals(Material.GRAVEL)
				|| e.getBlock().getType().equals(Material.ENDER_STONE)
				|| e.getBlock().getType().equals(Material.GLOWSTONE)
				|| e.getBlock().getType().equals(Material.OBSIDIAN)) {
			Player p = e.getPlayer();
			PClass pS = PClass.playerStats.get(p.getUniqueId());
			pS.setLastSkill("mining");
			
			File playerData = new File("plugins/SpecialItems/playerData/"+p.getUniqueId().toString());
			FileConfiguration pD = YamlConfiguration.loadConfiguration(playerData);
			try {
				pD.load(playerData);
			} catch (IOException | InvalidConfigurationException e1) {
				e1.printStackTrace();
			}
			
			double gainedXP = Main.cfg.getDouble("skill.block."+e.getBlock().getType().name()+".xp"); 
			
			
			pD.load(playerData);
			pS.setGainedXP(gainedXP);
			pS.setBlock(e.getBlock());
			pD.set("skill.mining.xp", pD.getInt("skill.mining.xp")+Main.cfg.getDouble("skill.block."+e.getBlock().getType().name()+".xp"));
			if(Main.getXPRequirement(pD.getInt("skill.mining.level")+1) < pD.getDouble("skill.mining.xp")) {
				if(pD.getInt("skill.mining.level") >  50) {
					pS.setBlock(null);
					return;
				}
				pD.set("skill.mining.level", pD.getInt("skill.mining.level")+1);
				pD.set("skill.mining.xp", pD.getDouble("skill.mining.xp")-(Main.getXPRequirement(pD.getInt("skill.mining.level"))));
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
				if(pD.getInt("skill.mining.level") == 1) {
					p.sendMessage("§3▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
					p.sendMessage("  §b§lSKILL LEVEL UP §3Mining §80➜§3"+Enchanting.translateEnchLevel(pD.getInt("skill.mining.level")));
					p.sendMessage(" ");
					p.sendMessage("  §a§lREWARDS");
					p.sendMessage("    §80➜§a"+(4*(pD.getInt("skill.mining.level"))+"% §fchance to get double ores."));
					p.sendMessage("   §eSpelunker I");
					p.sendMessage("   §8+§a1 ❇ Defense");
					p.sendMessage("   §8+§61,000 §7Coins");
					p.sendMessage("§3▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
				} else {
					if(pD.getInt("skill.mining.level") < 15) {
						p.sendMessage("§3▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
						p.sendMessage("  §b§lSKILL LEVEL UP §3Mining §8"+Enchanting.translateEnchLevel(pD.getInt("skill.mining.level")-1)+"➜"+Enchanting.translateEnchLevel(pD.getInt("skill.mining.level")));
						p.sendMessage(" ");
						p.sendMessage("  §a§lREWARDS");
						p.sendMessage("   §eSpelunker I");
						p.sendMessage("    §8"+(4*(pD.getInt("skill.mining.level")-1))+"➜§a"+(4*(pD.getInt("skill.mining.level"))+"% §fchance to get double ores."));
						p.sendMessage("   §8+§a1 ❇ Defense");
						p.sendMessage("   §8+§61,000 §7Coins");
						p.sendMessage("§3▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
					} else {
						if(pD.getInt("skill.mining.level") < 26) {
							p.sendMessage("§3▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
							p.sendMessage("  §b§lSKILL LEVEL UP §3Mining §8"+Enchanting.translateEnchLevel(pD.getInt("skill.mining.level")-1)+"➜"+Enchanting.translateEnchLevel(pD.getInt("skill.mining.level")));
							p.sendMessage(" ");
							p.sendMessage("  §a§lREWARDS");
							p.sendMessage("   §eSpelunker I");
							p.sendMessage("    §8"+(4*(pD.getInt("skill.mining.level")-1))+"➜§a"+(4*(pD.getInt("skill.mining.level"))+"% §fchance to get double ores."));
							p.sendMessage("   §8+§a2 ❇ Defense");
							p.sendMessage("   §8+§61,000 §7Coins");
							p.sendMessage("§3▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
						} else {
							if(pD.getInt("skill.mining.level") < 51) {
								p.sendMessage("§3▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
								p.sendMessage("  §b§lSKILL LEVEL UP §3Mining §8"+Enchanting.translateEnchLevel(pD.getInt("skill.mining.level")-1)+"➜"+Enchanting.translateEnchLevel(pD.getInt("skill.mining.level")));
								p.sendMessage(" ");
								p.sendMessage("  §a§lREWARDS");
								p.sendMessage("   §eSpelunker I");
								p.sendMessage("    §8"+(4*(pD.getInt("skill.mining.level")-1))+"➜§a"+(4*(pD.getInt("skill.mining.level"))+"% §fchance to get double ores."));
								p.sendMessage("   §8+§a2 ❇ Defense");
								p.sendMessage("   §8+§61,000 §7Coins");
								p.sendMessage("§3▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
							} else {
								
							}
						}
					}
				}
				
				//➜
			}
			try {
				pD.save(playerData);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {
				
				@Override
				public void run() {

					if(pS.getBlock() != null && pS.getBlock().equals(e.getBlock())) {
						pS.setBlock(null);
					}
					
				}
			}, 20L);
			
			
		}*/
		
	}
	
}
