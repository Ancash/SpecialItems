package de.ancash.specialitems.listener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import de.ancash.specialitems.Files;
import de.ancash.specialitems.SpecialItems;

public class Enchanting implements Listener {
	
	/*@EventHandler
	public void onEnchant(EnchantItemEvent e) {
		e.setCancelled(false);	
		
		ItemStack is = e.getItem().clone();
		e.getInventory().setItem(0, new ItemStack(Material.AIR));
					
		for(Enchantment ench : e.getEnchantsToAdd().keySet()) {
			is.addEnchantment(ench, e.getEnchantsToAdd().get(ench));
		}
		
		is = addEnchantingLore(is);
		e.getEnchanter().getInventory().addItem(is);
	}
*/
	public static ItemStack addEnchantingLore(ItemStack is) {
		ItemMeta im = is.getItemMeta();
		
		if(im.hasEnchants() && im.getLore() != null && im.getLore().contains("           ") || im.hasEnchants() && im.getLore() == null) {
			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		} 
		ArrayList<String> lore = new ArrayList<String>();
		ArrayList<String> new_lore = new ArrayList<String>();
		if(im.getLore() != null) {
			lore = (ArrayList<String>) im.getLore();
		}
		if(is.hasItemMeta() && !is.getItemMeta().hasEnchants() && !is.getType().equals(Material.ENCHANTED_BOOK)) {
			return is;
		}
		ArrayList<Enchantment> enchs = new ArrayList<Enchantment>();
		ArrayList<Integer> levels = new ArrayList<Integer>();
		ArrayList<String> names = new ArrayList<String>();
		int total_enchs = 0;
		for(Enchantment ench : im.getEnchants().keySet()) {
			enchs.add(ench);
			levels.add(im.getEnchants().get(ench));
			total_enchs++;
		}
		if(enchs.size() == 0 && is.getType().equals(Material.ENCHANTED_BOOK)) {
			EnchantmentStorageMeta enchmeta = (EnchantmentStorageMeta) is.getItemMeta();
			for(Enchantment ench : enchmeta.getStoredEnchants().keySet()) {
				enchs.add(ench);
				levels.add(enchmeta.getStoredEnchantLevel(ench));
			}
		}
		int cnt = 0;
		if(im.getLore() != null) {
			for(String str : lore) {
				if(str.equals("           ")) {
					new_lore.add(str);
					cnt = 0;
					for(Enchantment ench : enchs) {
						im.addEnchant(ench, levels.get(cnt), true);
												
						if(ench.getName().equals("WATER_WORKER")) {
							names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.aqua_affinity.name"))+" "+translateEnchLevel(levels.get(cnt)));
						}
						if(ench.getName().equals("DAMAGE_ARTHROPODS")) {
							names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.bane_of_arthropods.name"))+" "+translateEnchLevel(levels.get(cnt)));
						}
						if(ench.getName().equals("PROTECTION_EXPLOSIONS")) {
							names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.blast_protection.name"))+" "+translateEnchLevel(levels.get(cnt)));
						}
						if(ench.getName().equals("DEPTH_STRIDER")) {
							names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.depth_strider.name"))+" "+translateEnchLevel(levels.get(cnt)));
						}
						if(ench.getName().equals("DIG_SPEED")) {
							names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.efficiency.name"))+" "+translateEnchLevel(levels.get(cnt)));
						}
						if(ench.getName().equals("PROTECTION_FALL")) {
							names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.feather_falling.name"))+" "+translateEnchLevel(levels.get(cnt)));
						}
						if(ench.getName().equals("FIRE_ASPECT")) {
							names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fire_aspect.name"))+" "+translateEnchLevel(levels.get(cnt)));
						}
						if(ench.getName().equals("PROTECTION_FIRE")) {
							names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fire_protection.name"))+" "+translateEnchLevel(levels.get(cnt)));
						}
						if(ench.getName().equals("ARROW_FIRE")) {
							names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.flame.name"))+" "+translateEnchLevel(levels.get(cnt)));
						}
						if(ench.getName().equals("LOOT_BONUS_BLOCKS")) {
							names.add("§9Fortune "+translateEnchLevel(levels.get(cnt)));
						}
						if(ench.getName().equals("ARROW_INFINITE")) {
							names.add("§9Infinity "+translateEnchLevel(levels.get(cnt)));
						}
						if(ench.getName().equals("KNOCKBACK")) {
							names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.knockback.name"))+" "+translateEnchLevel(levels.get(cnt)));
						}
						if(ench.getName().equals("LOOT_BONUS_MOBS")) {
							names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.looting.name"))+" "+translateEnchLevel(levels.get(cnt)));
						}
						if(ench.getName().equals("LUCK")) {
							names.add("§9Luck of the Sea "+translateEnchLevel(levels.get(cnt)));
						}
						if(ench.getName().equals("LURE")) {
							names.add("§9Lure "+translateEnchLevel(levels.get(cnt)));
						}
						if(ench.getName().equals("ARROW_DAMAGE")) {
							names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.power.name"))+" "+translateEnchLevel(levels.get(cnt)));
						}
						if(ench.getName().equals("PROTECTION_PROJECTILE")) {
							names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.projectile_protection.name"))+" "+translateEnchLevel(levels.get(cnt)));
						}
						if(ench.getName().equals("PROTECTION_ENVIRONMENTAL")) {
							names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.protection.name"))+" "+translateEnchLevel(levels.get(cnt)));
						}
						if(ench.getName().equals("ARROW_KNOCKBACK")) {
							names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.punch.name"))+" "+translateEnchLevel(levels.get(cnt)));
						}
						if(ench.getName().equals("OXYGEN")) {
							names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.respiration.name"))+" "+translateEnchLevel(levels.get(cnt)));
						}
						if(ench.getName().equals("DAMAGE_ALL")) {
							names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.sharpness.name"))+" "+translateEnchLevel(levels.get(cnt)));
						}
						if(ench.getName().equals("SILK_TOUCH")) {
							names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.silk_touch.name"))+" "+translateEnchLevel(levels.get(cnt)));
						}
						if(ench.getName().equals("DAMAGE_UNDEAD")) {
							names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.smite.name"))+" "+translateEnchLevel(levels.get(cnt)));
						}
						if(ench.getName().equals("THORNS")) {
							names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.thorns.name"))+" "+translateEnchLevel(levels.get(cnt)));
						}
						if(ench.getName().equals("DURABILITY")) {
							names.add("§9Unbreaking "+translateEnchLevel(levels.get(cnt)));
						}
						if(SpecialItems.ench_aiming != null) {
							if(ench.getName().equals("First Strike")) {
								names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.first_strike.name"))+" "+translateEnchLevel(im.getEnchantLevel(SpecialItems.ench_firstStrike)));
							}
							if(ench.getName().equals("Telekinesis")) {
								names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.telekinesis.name"))+" "+translateEnchLevel(im.getEnchantLevel(SpecialItems.ench_telekinesis)));
							}
							if(ench.getName().equals("Aiming")) {
								names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.aiming.name"))+" "+translateEnchLevel(im.getEnchantLevel(SpecialItems.ench_aiming)));
							}
							if(ench.getName().equals("Snipe")) {
								names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.snipe.name"))+" "+translateEnchLevel(im.getEnchantLevel(SpecialItems.ench_snipe)));
							}
							if(ench.getName().equals("Life Steal")) {
								names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.life_steal.name"))+" "+translateEnchLevel(im.getEnchantLevel(SpecialItems.ench_lifesteal)));
							}
							if(ench.getName().equals("Growth")) {
								names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.growth.name"))+" "+translateEnchLevel(im.getEnchantLevel(SpecialItems.ench_growth)));
							}
							if(ench.getName().equals("Critical")) {
								names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.critical.name"))+" "+translateEnchLevel(im.getEnchantLevel(SpecialItems.ench_critical)));
							}
							if(ench.getName().equals("Cubism")) {
								names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.cubism.name"))+" "+translateEnchLevel(im.getEnchantLevel(SpecialItems.ench_cubism)));
							}
							if(ench.getName().equals("Dragon Hunter")) {
								names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.dragon_hunter.name"))+" "+translateEnchLevel(im.getEnchantLevel(SpecialItems.ench_dragonhunter)));
							}
							if(ench.getName().equals("Ender Slayer")) {
								names.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.ender_slayer.name"))+" "+translateEnchLevel(im.getEnchantLevel(SpecialItems.ench_enderslayer)));
							}
						}
						cnt++;
					}
					
					names.sort(String::compareToIgnoreCase);
					for(String name : names) {
						new_lore.add(name);
						if(total_enchs <= 5) {
							if(SpecialItems.ench_aiming != null) {
								if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.aiming.name"))+" "+translateEnchLevel(im.getEnchantLevel(SpecialItems.ench_aiming)))) {
									if(Files.cfg.getString("enchantments.aiming.description.line_1") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.aiming.description.line_1").replace("%%%", ""+((2*im.getEnchants().get(SpecialItems.ench_aiming))))));
										if(Files.cfg.getString("enchantments.aiming.description.line_2") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.aiming.description.line_2").replace("%%%", ""+((2*im.getEnchants().get(SpecialItems.ench_aiming))))));
											if(Files.cfg.getString("enchantments.aiming.description.line_3") != null) {											
												new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.aiming.description.line_3").replace("%%%", ""+((2*im.getEnchants().get(SpecialItems.ench_aiming))))));
											}
										}
									}
								}
								if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.critical.name")))) {
									if(Files.cfg.getString("enchantments.critical.description.line_1") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.critical.description.line_1").replace("%%%", ""+((10*im.getEnchants().get(SpecialItems.ench_critical))+"%"))));
										if(Files.cfg.getString("enchantments.critical.description.line_2") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.critical.description.line_2").replace("%%%", ""+((10*im.getEnchants().get(SpecialItems.ench_critical))+"%"))));
											if(Files.cfg.getString("enchantments.critical.description.line_3") != null) {											
												new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.critical.description.line_3").replace("%%%", ""+((10*im.getEnchants().get(SpecialItems.ench_critical))+"%"))));
											}
										}
									}
								}
								if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.cubism.name")))) {
									if(Files.cfg.getString("enchantments.cubism.description.line_1") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.cubism.description.line_1").replace("%%%", ""+((10*im.getEnchants().get(SpecialItems.ench_cubism))+"%"))));
										if(Files.cfg.getString("enchantments.cubism.description.line_2") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.cubism.description.line_2").replace("%%%", ""+((10*im.getEnchants().get(SpecialItems.ench_cubism))+"%"))));
											if(Files.cfg.getString("enchantments.cubism.description.line_3") != null) {											
												new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.cubism.description.line_3").replace("%%%", ""+((10*im.getEnchants().get(SpecialItems.ench_cubism))+"%"))));
											}
										}
									}
								}
								if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.dragon_hunter.name")))) {
									if(Files.cfg.getString("enchantments.dragon_hunter.description.line_1") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.dragon_hunter.description.line_1").replace("%%%", ""+((8*im.getEnchants().get(SpecialItems.ench_dragonhunter))+"%"))));
										if(Files.cfg.getString("enchantments.dragon_hunter.description.line_2") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.dragon_hunter.description.line_2").replace("%%%", ""+((8*im.getEnchants().get(SpecialItems.ench_dragonhunter))+"%"))));
											if(Files.cfg.getString("enchantments.dragon_hunter.description.line_3") != null) {											
												new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.dragon_hunter.description.line_3").replace("%%%", ""+((8*im.getEnchants().get(SpecialItems.ench_dragonhunter))+"%"))));
											}
										}
									}
								}
								if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.ender_slayer.name")))) {
									if(Files.cfg.getString("enchantments.ender_slayer.description.line_1") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.ender_slayer.description.line_1").replace("%%%", ""+((12*im.getEnchants().get(SpecialItems.ench_enderslayer))+"%"))));
										if(Files.cfg.getString("enchantments.ender_slayer.description.line_2") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.ender_slayer.description.line_2").replace("%%%", ""+((12*im.getEnchants().get(SpecialItems.ench_enderslayer))+"%"))));
											if(Files.cfg.getString("enchantments.ender_slayer.description.line_3") != null) {											
												new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.ender_slayer.description.line_3").replace("%%%", ""+((12*im.getEnchants().get(SpecialItems.ench_enderslayer))+"%"))));
											}
										}
									}
								}
								if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.first_strike.name")))) {
									if(Files.cfg.getString("enchantments.first_strike.description.line_1") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.first_strike.description.line_1").replace("%%%", ""+((25*im.getEnchants().get(SpecialItems.ench_firstStrike)+"%")))));
										if(Files.cfg.getString("enchantments.first_strike.description.line_2") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.first_strike.description.line_2").replace("%%%", ""+((25*im.getEnchants().get(SpecialItems.ench_firstStrike)+"%")))));
											if(Files.cfg.getString("enchantments.first_strike.description.line_3") != null) {
												new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.first_strike.description.line_3").replace("%%%", ""+((25*im.getEnchants().get(SpecialItems.ench_firstStrike)+"%")))));											
											}
										}
									}
								}
								if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.growth.name")))) {
									if(Files.cfg.getString("enchantments.growth.description.line_1") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.growth.description.line_1").replace("%%%", ""+(15*im.getEnchantLevel(SpecialItems.ench_growth)))));
										if(Files.cfg.getString("enchantments.growth.description.line_2") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.growth.description.line_2").replace("%%%", ""+(15*im.getEnchantLevel(SpecialItems.ench_growth)))));
											if(Files.cfg.getString("enchantments.growth.description.line_3") != null) {
												new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.growth.description.line_3").replace("%%%", ""+(15*im.getEnchantLevel(SpecialItems.ench_growth)))));											
											}
										}
									}
								}
								if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.life_steal.name")))) {
									if(Files.cfg.getString("enchantments.life_steal.description.line_1") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.life_steal.description.line_1").replace("%%%", ""+(((double) 0.1*im.getEnchants().get(SpecialItems.ench_lifesteal)+"%")).replace("0000000000000004", ""))));
										if(Files.cfg.getString("enchantments.life_steal.description.line_2") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.life_steal.description.line_2").replace("%%%", ""+(((double) 0.1*im.getEnchants().get(SpecialItems.ench_lifesteal)+"%")).replace("0000000000000004", ""))));
											if(Files.cfg.getString("enchantments.life_steal.description.line_3") != null) {											
												new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.life_steal.description.line_3").replace("%%%", ""+(((double) 0.1*im.getEnchants().get(SpecialItems.ench_lifesteal)+"%")).replace("0000000000000004", ""))));
											}
										}
									}
								}
								if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.snipe.name")))) {
									if(Files.cfg.getString("enchantments.snipe.description.line_1") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.snipe.description.line_1").replace("%%%", ""+((1*im.getEnchants().get(SpecialItems.ench_snipe)+"%")))));
										if(Files.cfg.getString("enchantments.snipe.description.line_2") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.snipe.description.line_2").replace("%%%", ""+((1*im.getEnchants().get(SpecialItems.ench_snipe)+"%")))));
											if(Files.cfg.getString("enchantments.snipe.description.line_3") != null) {											
												new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.snipe.description.line_3").replace("%%%", ""+((1*im.getEnchants().get(SpecialItems.ench_snipe)+"%")))));
											}
										}
									}
								}
							}
							if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.aqua_affinity.name")))) {
								if(Files.cfg.getString("enchantments.aqua_affinity.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.aqua_affinity.description.line_1")));
									if(Files.cfg.getString("enchantments.aqua_affinity.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.aqua_affinity.description.line_2")));
										if(Files.cfg.getString("enchantments.aqua_affinity.description.line_3") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.aqua_affinity.description.line_3")));											
										}
									}
								}
							}
							if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.bane_of_arthropods.name")))) {
								if(Files.cfg.getString("enchantments.bane_of_arthropods.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.bane_of_arthropods.description.line_1").replace("%%%", ""+(8*im.getEnchantLevel(Enchantment.DAMAGE_ARTHROPODS)+"%"))));
									if(Files.cfg.getString("enchantments.bane_of_arthropods.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.bane_of_arthropods.description.line_2").replace("%%%", ""+(8*im.getEnchantLevel(Enchantment.DAMAGE_ARTHROPODS)+"%"))));
										if(Files.cfg.getString("enchantments.bane_of_arthropods.description.line_3") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.bane_of_arthropods.description.line_3").replace("%%%", ""+(8*im.getEnchantLevel(Enchantment.DAMAGE_ARTHROPODS)+"%"))));											
										}
									}
								}
							}
							if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.blast_protection.name")))) {
								if(Files.cfg.getString("enchantments.blast_protection.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.blast_protection.description.line_1").replace("%%%", ""+(4*im.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS)))));
									if(Files.cfg.getString("enchantments.blast_protection.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.blast_protection.description.line_2").replace("%%%", ""+(4*im.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS)))));
										if(Files.cfg.getString("enchantments.blast_protection.description.line_3") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.blast_protection.description.line_3").replace("%%%", ""+(4*im.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS)))));											
										}
									}
								}
							}
							if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.depth_strider.name")))) {
								if(Files.cfg.getString("enchantments.depth_strider.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.depth_strider.description.line_1").replace("%%%", ""+(33*im.getEnchantLevel(Enchantment.DEPTH_STRIDER)+"%"))));
									if(Files.cfg.getString("enchantments.depth_strider.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.depth_strider.description.line_2").replace("%%%", ""+(33*im.getEnchantLevel(Enchantment.DEPTH_STRIDER)+"%"))));
										if(Files.cfg.getString("enchantments.depth_strider.description.line_3") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.depth_strider.description.line_3").replace("%%%", ""+(33*im.getEnchantLevel(Enchantment.DEPTH_STRIDER)+"%"))));											
										}
									}
								}
							}
							if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.efficiency.name")))) {
								if(Files.cfg.getString("enchantments.efficiency.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.efficiency.description.line_1")));
									if(Files.cfg.getString("enchantments.efficiency.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.efficiency.description.line_2")));
										if(Files.cfg.getString("enchantments.efficiency.description.line_3") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.efficiency.description.line_3")));											
										}
									}
								}
							}
							if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.feather_falling.name")))) {
								if(Files.cfg.getString("enchantments.feather_falling.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.feather_falling.description.line_1").replace("%%%", ""+((5*im.getEnchants().get(Enchantment.PROTECTION_FALL)+"%")))).replace("%%b", ""+1*im.getEnchants().get(Enchantment.PROTECTION_FALL)));
									if(Files.cfg.getString("enchantments.feather_falling.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.feather_falling.description.line_2").replace("%%%", ""+((5*im.getEnchants().get(Enchantment.PROTECTION_FALL)+"%")))).replace("%%b", ""+1*im.getEnchants().get(Enchantment.PROTECTION_FALL)));
										if(Files.cfg.getString("enchantments.feather_falling.description.line_3") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.feather_falling.description.line_3").replace("%%%", ""+((5*im.getEnchants().get(Enchantment.PROTECTION_FALL)+"%")))).replace("%%b", ""+1*im.getEnchants().get(Enchantment.PROTECTION_FALL)));											
										}
									}
								}
							}
							if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fire_aspect.name")))) {
								if(Files.cfg.getString("enchantments.fire_aspect.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fire_aspect.description.line_1").replace("%%%", ""+(2+im.getEnchants().get(Enchantment.FIRE_ASPECT))).replace("%%d", ""+(10+5*im.getEnchantLevel(Enchantment.FIRE_ASPECT)))));
									if(Files.cfg.getString("enchantments.fire_aspect.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fire_aspect.description.line_2").replace("%%%", ""+(2+im.getEnchants().get(Enchantment.FIRE_ASPECT))).replace("%%d", ""+(10+5*im.getEnchantLevel(Enchantment.FIRE_ASPECT)))));
										if(Files.cfg.getString("enchantments.fire_aspect.description.line_3") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fire_aspect.description.line_3").replace("%%%", ""+(2+im.getEnchants().get(Enchantment.FIRE_ASPECT))).replace("%%d", ""+(10+5*im.getEnchantLevel(Enchantment.FIRE_ASPECT)))));											
										}
									}
								}
							}
							if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fire_protection.name")))) {
								if(Files.cfg.getString("enchantments.fire_protection.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fire_protection.description.line_1").replace("%%%", ""+(4*im.getEnchants().get(Enchantment.PROTECTION_FIRE)))));
									if(Files.cfg.getString("enchantments.fire_protection.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fire_protection.description.line_2").replace("%%%", ""+(4*im.getEnchants().get(Enchantment.PROTECTION_FIRE)))));
										if(Files.cfg.getString("enchantments.fire_protection.description.line_3") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fire_protection.description.line_3").replace("%%%", ""+(4*im.getEnchants().get(Enchantment.PROTECTION_FIRE)))));											
										}
									}
								}
							}
							if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.flame.name")))) {
								if(Files.cfg.getString("enchantments.flame.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.flame.description.line_1")));
									if(Files.cfg.getString("enchantments.flame.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.flame.description.line_2")));
										if(Files.cfg.getString("enchantments.flame.description.line_3") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.flame.description.line_3")));											
										}
									}
								}
							}
							if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fortune.name")))) {
								if(Files.cfg.getString("enchantments.fortune.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fortune.description.line_1").replace("%%%", ""+(10*im.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS))+"%")));
									if(Files.cfg.getString("enchantments.fortune.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fortune.description.line_2").replace("%%%", ""+(10*im.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS))+"%")));
										if(Files.cfg.getString("enchantments.fortune.description.line_3") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fortune.description.line_3").replace("%%%", ""+(10*im.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS))+"%")));											
										}
									}
								}
							}
							if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.knockback.name")))) {
								if(Files.cfg.getString("enchantments.knockback.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.knockback.description.line_1").replace("%%%", ""+(3*im.getEnchantLevel(Enchantment.KNOCKBACK)))));
									if(Files.cfg.getString("enchantments.knockback.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.knockback.description.line_2").replace("%%%", ""+(3*im.getEnchantLevel(Enchantment.KNOCKBACK)))));
										if(Files.cfg.getString("enchantments.knockback.description.line_3") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.knockback.description.line_3").replace("%%%", ""+(3*im.getEnchantLevel(Enchantment.KNOCKBACK)))));											
										}
									}
								}
							}
							if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.looting.name")))) {
								if(Files.cfg.getString("enchantments.looting.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.looting.description.line_1").replace("%%%", ""+(15*im.getEnchantLevel(Enchantment.LOOT_BONUS_MOBS))+"%")));
									if(Files.cfg.getString("enchantments.looting.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.looting.description.line_2").replace("%%%", ""+(15*im.getEnchantLevel(Enchantment.LOOT_BONUS_MOBS))+"%")));
										if(Files.cfg.getString("enchantments.looting.description.line_3") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.looting.description.line_3").replace("%%%", ""+(15*im.getEnchantLevel(Enchantment.LOOT_BONUS_MOBS))+"%")));											
										}
									}
								}
							}
							if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.power.name")))) {
								if(Files.cfg.getString("enchantments.power.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.power.description.line_1").replace("%%%", ""+(8*im.getEnchantLevel(Enchantment.ARROW_DAMAGE)))));
									if(Files.cfg.getString("enchantments.power.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.power.description.line_2").replace("%%%", ""+(8*im.getEnchantLevel(Enchantment.ARROW_DAMAGE)))));
										if(Files.cfg.getString("enchantments.power.description.line_3") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.power.description.line_3").replace("%%%", ""+(8*im.getEnchantLevel(Enchantment.ARROW_DAMAGE)))));											
										}
									}
								}
							}
							if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.projectile_protection.name")))) {
								if(Files.cfg.getString("enchantments.projectile_protection.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.projectile_protection.description.line_1").replace("%%%", ""+(4*im.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE)))));
									if(Files.cfg.getString("enchantments.projectile_protection.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.projectile_protection.description.line_2").replace("%%%", ""+(4*im.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE)))));
										if(Files.cfg.getString("enchantments.projectile_protection.description.line_3") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.projectile_protection.description.line_3").replace("%%%", ""+(4*im.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE)))));											
										}
									}
								}
							}
							if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.protection.name")))) {
								if(Files.cfg.getString("enchantments.protection.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.protection.description.line_1").replace("%%%", ""+(3*im.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL)))));
									if(Files.cfg.getString("enchantments.protection.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.protection.description.line_2").replace("%%%", ""+(3*im.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL)))));
										if(Files.cfg.getString("enchantments.protection.description.line_3") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.protection.description.line_3").replace("%%%", ""+(3*im.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL)))));											
										}
									}
								}
							}
							if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.punch.name")))) {
								if(Files.cfg.getString("enchantments.punch.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.punch.description.line_1").replace("%%%", ""+(3*im.getEnchantLevel(Enchantment.ARROW_KNOCKBACK)))));
									if(Files.cfg.getString("enchantments.punch.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.punch.description.line_2").replace("%%%", ""+(3*im.getEnchantLevel(Enchantment.ARROW_KNOCKBACK)))));
										if(Files.cfg.getString("enchantments.punch.description.line_3") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.punch.description.line_3").replace("%%%", ""+(3*im.getEnchantLevel(Enchantment.ARROW_KNOCKBACK)))));											
										}
									}
								}
							}
							if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.respiration.name")))) {
								if(Files.cfg.getString("enchantments.respiration.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.respiration.description.line_1").replace("%%%", ""+(15*im.getEnchantLevel(Enchantment.OXYGEN)))));
									if(Files.cfg.getString("enchantments.respiration.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.respiration.description.line_2").replace("%%%", ""+(15*im.getEnchantLevel(Enchantment.OXYGEN)))));
										if(Files.cfg.getString("enchantments.respiration.description.line_3") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.respiration.description.line_3").replace("%%%", ""+(15*im.getEnchantLevel(Enchantment.OXYGEN)))));											
										}
									}
								}
							}
							if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.sharpness.name")))) {
								if(Files.cfg.getString("enchantments.sharpness.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.sharpness.description.line_1").replace("%%%", ""+((5*im.getEnchants().get(Enchantment.DAMAGE_ALL)+"%")))));
									if(Files.cfg.getString("enchantments.sharpness.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.sharpness.description.line_2").replace("%%%", ""+((5*im.getEnchants().get(Enchantment.DAMAGE_ALL)+"%")))));
										if(Files.cfg.getString("enchantments.sharpness.description.line_3") != null) {											
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.sharpness.description.line_3").replace("%%%", ""+((5*im.getEnchants().get(Enchantment.DAMAGE_ALL)+"%")))));
										}
									}
								}
							}
							if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.silk_touch.name")))) {
								if(Files.cfg.getString("enchantments.silk_touch.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.silk_touch.description.line_1")));
									if(Files.cfg.getString("enchantments.silk_touch.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.silk_touch.description.line_2")));
										if(Files.cfg.getString("enchantments.silk_touch.description.line_3") != null) {											
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.silk_touch.description.line_3")));
										}
									}
								}
							}
							if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.smite.name")))) {
								if(Files.cfg.getString("enchantments.smite.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.smite.description.line_1").replace("%%%", ""+((8*im.getEnchants().get(Enchantment.DAMAGE_UNDEAD)+"%")))));
									if(Files.cfg.getString("enchantments.smite.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.smite.description.line_2").replace("%%%", ""+((8*im.getEnchants().get(Enchantment.DAMAGE_UNDEAD)+"%")))));
										if(Files.cfg.getString("enchantments.smite.description.line_3") != null) {											
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.smite.description.line_3").replace("%%%", ""+((8*im.getEnchants().get(Enchantment.DAMAGE_UNDEAD)+"%")))));
										}
									}
								}
							}
							if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.telekinesis.name")))) {
								if(Files.cfg.getString("enchantments.telekinesis.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.telekinesis.description.line_1")));
									if(Files.cfg.getString("enchantments.telekinesis.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.telekinesis.description.line_2")));
										if(Files.cfg.getString("enchantments.telekinesis.description.line_3") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.telekinesis.description.line_3")));
										}
									}
								}
							}
							if(name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.thorns.name")))) {
								if(Files.cfg.getString("enchantments.thorns.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.thorns.description.line_1").replace("%%%", ""+(3*im.getEnchantLevel(Enchantment.THORNS))+"%")));
									if(Files.cfg.getString("enchantments.thorns.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.thorns.description.line_2").replace("%%%", ""+(3*im.getEnchantLevel(Enchantment.THORNS))+"%")));
										if(Files.cfg.getString("enchantments.thorns.description.line_3") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.thorns.description.line_3").replace("%%%", ""+(3*im.getEnchantLevel(Enchantment.THORNS))+"%")));
										}
									}
								}
							}
						}
					}
					if(im.hasEnchants()) {
						new_lore.add("          ");
					}
				} else {
					new_lore.add(str);
				}
			}
		} else {
			for(Enchantment ench : enchs) {
				im.addEnchant(ench, levels.get(cnt), true);
				
				if(ench.getName().equals("WATER_WORKER")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.aqua_affinity.name"))+" "+translateEnchLevel(levels.get(cnt)));
				}
				if(ench.getName().equals("DAMAGE_ARTHROPODS")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.bane_of_arthropods.name"))+" "+translateEnchLevel(levels.get(cnt)));
				}
				if(ench.getName().equals("PROTECTION_EXPLOSIONS")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.blast_protection.name"))+" "+translateEnchLevel(levels.get(cnt)));
				}
				if(ench.getName().equals("DEPTH_STRIDER")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.depth_strider.name"))+" "+translateEnchLevel(levels.get(cnt)));
				}
				if(ench.getName().equals("DIG_SPEED")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.efficiency.name"))+" "+translateEnchLevel(levels.get(cnt)));
				}
				if(ench.getName().equals("PROTECTION_FALL")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.feather_falling.name"))+" "+translateEnchLevel(levels.get(cnt)));
				}
				if(ench.getName().equals("FIRE_ASPECT")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fire_aspect.name"))+" "+translateEnchLevel(levels.get(cnt)));
				}
				if(ench.getName().equals("PROTECTION_FIRE")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fire_protection.name"))+" "+translateEnchLevel(levels.get(cnt)));
				}
				if(ench.getName().equals("ARROW_FIRE")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.flame.name"))+" "+translateEnchLevel(levels.get(cnt)));
				}
				if(ench.getName().equals("LOOT_BONUS_BLOCKS")) {
					lore.add("§9Fortune "+translateEnchLevel(levels.get(cnt)));
				}
				if(ench.getName().equals("ARROW_INFINITE")) {
					lore.add("§9Infinity "+translateEnchLevel(levels.get(cnt)));
				}
				if(ench.getName().equals("KNOCKBACK")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.knockback.name"))+" "+translateEnchLevel(levels.get(cnt)));
				}
				if(ench.getName().equals("LOOT_BONUS_MOBS")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.looting.name"))+" "+translateEnchLevel(levels.get(cnt)));
				}
				if(ench.getName().equals("LUCK")) {
					lore.add("§9Luck of the Sea "+translateEnchLevel(levels.get(cnt)));
				}
				if(ench.getName().equals("LURE")) {
					lore.add("§9Lure "+translateEnchLevel(levels.get(cnt)));
				}
				if(ench.getName().equals("ARROW_DAMAGE")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.power.name"))+" "+translateEnchLevel(levels.get(cnt)));
				}
				if(ench.getName().equals("PROTECTION_PROJECTILE")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.projectile_protection.name"))+" "+translateEnchLevel(levels.get(cnt)));
				}
				if(ench.getName().equals("PROTECTION_ENVIRONMENTAL")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.protection.name"))+" "+translateEnchLevel(levels.get(cnt)));
				}
				if(ench.getName().equals("ARROW_KNOCKBACK")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.punch.name"))+" "+translateEnchLevel(levels.get(cnt)));
				}
				if(ench.getName().equals("OXYGEN")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.respiration.name"))+" "+translateEnchLevel(levels.get(cnt)));
				}
				if(ench.getName().equals("DAMAGE_ALL")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.sharpness.name"))+" "+translateEnchLevel(levels.get(cnt)));
				}
				if(ench.getName().equals("SILK_TOUCH")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.silk_touch.name"))+" "+translateEnchLevel(levels.get(cnt)));
				}
				if(ench.getName().equals("DAMAGE_UNDEAD")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.smite.name"))+" "+translateEnchLevel(levels.get(cnt)));
				}
				if(ench.getName().equals("THORNS")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.thorns.name"))+" "+translateEnchLevel(levels.get(cnt)));
				}
				if(ench.getName().equals("DURABILITY")) {
					lore.add("§9Unbreaking "+translateEnchLevel(levels.get(cnt)));
				}
				if(SpecialItems.ench_aiming != null) {
					if(ench.getName().equals("First Strike")) {
						lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.first_strike.name"))+" "+translateEnchLevel(im.getEnchantLevel(SpecialItems.ench_firstStrike)));
					}
					if(ench.getName().equals("Telekinesis")) {
						lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.telekinesis.name"))+" "+translateEnchLevel(im.getEnchantLevel(SpecialItems.ench_telekinesis)));
					}
					if(ench.getName().equals("Aiming")) {
						lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.aiming.name"))+" "+translateEnchLevel(im.getEnchantLevel(SpecialItems.ench_aiming)));
					}
					if(ench.getName().equals("Snipe")) {
						lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.snipe.name"))+" "+translateEnchLevel(im.getEnchantLevel(SpecialItems.ench_snipe)));
					}
					if(ench.getName().equals("Life Steal")) {
						lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.life_steal.name"))+" "+translateEnchLevel(im.getEnchantLevel(SpecialItems.ench_lifesteal)));
					}
					if(ench.getName().equals("Growth")) {
						lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.growth.name"))+" "+translateEnchLevel(im.getEnchantLevel(SpecialItems.ench_growth)));
					}
					if(ench.getName().equals("Critical")) {
						lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.critical.name"))+" "+translateEnchLevel(im.getEnchantLevel(SpecialItems.ench_critical)));
					}
					if(ench.getName().equals("Cubism")) {
						lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.cubism.name"))+" "+translateEnchLevel(im.getEnchantLevel(SpecialItems.ench_cubism)));
					}
					if(ench.getName().equals("Dragon Hunter")) {
						lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.dragon_hunter.name"))+" "+translateEnchLevel(im.getEnchantLevel(SpecialItems.ench_dragonhunter)));
					}
					if(ench.getName().equals("Ender Slayer")) {
						lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.ender_slayer.name"))+" "+translateEnchLevel(im.getEnchantLevel(SpecialItems.ench_enderslayer)));
					}
				}
				cnt++;
			}
			lore.sort(String::compareToIgnoreCase);
			new_lore.add("           ");
			if(total_enchs <= 5) {
				for(String str : lore) {
					new_lore.add(str);
					if(total_enchs <= 5) {
						if(SpecialItems.ench_aiming != null) {
							if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.aiming.name"))+" "+translateEnchLevel(im.getEnchantLevel(SpecialItems.ench_aiming)))) {
								if(Files.cfg.getString("enchantments.aiming.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.aiming.description.line_1").replace("%%%", ""+((2*im.getEnchants().get(SpecialItems.ench_aiming))))));
									if(Files.cfg.getString("enchantments.aiming.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.aiming.description.line_2").replace("%%%", ""+((2*im.getEnchants().get(SpecialItems.ench_aiming))))));
										if(Files.cfg.getString("enchantments.aiming.description.line_3") != null) {											
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.aiming.description.line_3").replace("%%%", ""+((2*im.getEnchants().get(SpecialItems.ench_aiming))))));
										}
									}
								}
							}
							if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.critical.name")))) {
								if(Files.cfg.getString("enchantments.critical.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.critical.description.line_1").replace("%%%", ""+((10*im.getEnchants().get(SpecialItems.ench_critical))+"%"))));
									if(Files.cfg.getString("enchantments.critical.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.critical.description.line_2").replace("%%%", ""+((10*im.getEnchants().get(SpecialItems.ench_critical))+"%"))));
										if(Files.cfg.getString("enchantments.critical.description.line_3") != null) {											
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.critical.description.line_3").replace("%%%", ""+((10*im.getEnchants().get(SpecialItems.ench_critical))+"%"))));
										}
									}
								}
							}
							if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.cubism.name")))) {
								if(Files.cfg.getString("enchantments.cubism.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.cubism.description.line_1").replace("%%%", ""+((10*im.getEnchants().get(SpecialItems.ench_cubism))+"%"))));
									if(Files.cfg.getString("enchantments.cubism.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.cubism.description.line_2").replace("%%%", ""+((10*im.getEnchants().get(SpecialItems.ench_cubism))+"%"))));
										if(Files.cfg.getString("enchantments.cubism.description.line_3") != null) {											
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.cubism.description.line_3").replace("%%%", ""+((10*im.getEnchants().get(SpecialItems.ench_cubism))+"%"))));
										}
									}
								}
							}
							if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.dragon_hunter.name")))) {
								if(Files.cfg.getString("enchantments.dragon_hunter.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.dragon_hunter.description.line_1").replace("%%%", ""+((8*im.getEnchants().get(SpecialItems.ench_dragonhunter))+"%"))));
									if(Files.cfg.getString("enchantments.dragon_hunter.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.dragon_hunter.description.line_2").replace("%%%", ""+((8*im.getEnchants().get(SpecialItems.ench_dragonhunter))+"%"))));
										if(Files.cfg.getString("enchantments.dragon_hunter.description.line_3") != null) {											
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.dragon_hunter.description.line_3").replace("%%%", ""+((8*im.getEnchants().get(SpecialItems.ench_dragonhunter))+"%"))));
										}
									}
								}
							}
							if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.ender_slayer.name")))) {
								if(Files.cfg.getString("enchantments.ender_slayer.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.ender_slayer.description.line_1").replace("%%%", ""+((12*im.getEnchants().get(SpecialItems.ench_enderslayer))+"%"))));
									if(Files.cfg.getString("enchantments.ender_slayer.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.ender_slayer.description.line_2").replace("%%%", ""+((12*im.getEnchants().get(SpecialItems.ench_enderslayer))+"%"))));
										if(Files.cfg.getString("enchantments.ender_slayer.description.line_3") != null) {											
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.ender_slayer.description.line_3").replace("%%%", ""+((12*im.getEnchants().get(SpecialItems.ench_enderslayer))+"%"))));
										}
									}
								}
							}
							if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.first_strike.name")))) {
								if(Files.cfg.getString("enchantments.first_strike.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.first_strike.description.line_1").replace("%%%", ""+((25*im.getEnchants().get(SpecialItems.ench_firstStrike)+"%")))));
									if(Files.cfg.getString("enchantments.first_strike.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.first_strike.description.line_2").replace("%%%", ""+((25*im.getEnchants().get(SpecialItems.ench_firstStrike)+"%")))));
										if(Files.cfg.getString("enchantments.first_strike.description.line_3") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.first_strike.description.line_3").replace("%%%", ""+((25*im.getEnchants().get(SpecialItems.ench_firstStrike)+"%")))));											
										}
									}
								}
							}
							if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.growth.name")))) {
								if(Files.cfg.getString("enchantments.growth.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.growth.description.line_1").replace("%%%", ""+(15*im.getEnchantLevel(SpecialItems.ench_growth)))));
									if(Files.cfg.getString("enchantments.growth.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.growth.description.line_2").replace("%%%", ""+(15*im.getEnchantLevel(SpecialItems.ench_growth)))));
										if(Files.cfg.getString("enchantments.growth.description.line_3") != null) {
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.growth.description.line_3").replace("%%%", ""+(15*im.getEnchantLevel(SpecialItems.ench_growth)))));											
										}
									}
								}
							}
							if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.life_steal.name")))) {
								if(Files.cfg.getString("enchantments.life_steal.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.life_steal.description.line_1").replace("%%%", ""+(((double) 0.1*im.getEnchants().get(SpecialItems.ench_lifesteal)+"%")).replace("0000000000000004", ""))));
									if(Files.cfg.getString("enchantments.life_steal.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.life_steal.description.line_2").replace("%%%", ""+(((double) 0.1*im.getEnchants().get(SpecialItems.ench_lifesteal)+"%")).replace("0000000000000004", ""))));
										if(Files.cfg.getString("enchantments.life_steal.description.line_3") != null) {											
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.life_steal.description.line_3").replace("%%%", ""+(((double) 0.1*im.getEnchants().get(SpecialItems.ench_lifesteal)+"%")).replace("0000000000000004", ""))));
										}
									}
								}
							}
							if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.snipe.name")))) {
								if(Files.cfg.getString("enchantments.snipe.description.line_1") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.snipe.description.line_1").replace("%%%", ""+((1*im.getEnchants().get(SpecialItems.ench_snipe)+"%")))));
									if(Files.cfg.getString("enchantments.snipe.description.line_2") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.snipe.description.line_2").replace("%%%", ""+((1*im.getEnchants().get(SpecialItems.ench_snipe)+"%")))));
										if(Files.cfg.getString("enchantments.snipe.description.line_3") != null) {											
											new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.snipe.description.line_3").replace("%%%", ""+((1*im.getEnchants().get(SpecialItems.ench_snipe)+"%")))));
										}
									}
								}
							}
						}
						if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.aqua_affinity.name")))) {
							if(Files.cfg.getString("enchantments.aqua_affinity.description.line_1") != null) {
								new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.aqua_affinity.description.line_1")));
								if(Files.cfg.getString("enchantments.aqua_affinity.description.line_2") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.aqua_affinity.description.line_2")));
									if(Files.cfg.getString("enchantments.aqua_affinity.description.line_3") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.aqua_affinity.description.line_3")));											
									}
								}
							}
						}
						if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.bane_of_arthropods.name")))) {
							if(Files.cfg.getString("enchantments.bane_of_arthropods.description.line_1") != null) {
								new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.bane_of_arthropods.description.line_1").replace("%%%", ""+(8*im.getEnchantLevel(Enchantment.DAMAGE_ARTHROPODS)+"%"))));
								if(Files.cfg.getString("enchantments.bane_of_arthropods.description.line_2") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.bane_of_arthropods.description.line_2").replace("%%%", ""+(8*im.getEnchantLevel(Enchantment.DAMAGE_ARTHROPODS)+"%"))));
									if(Files.cfg.getString("enchantments.bane_of_arthropods.description.line_3") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.bane_of_arthropods.description.line_3").replace("%%%", ""+(8*im.getEnchantLevel(Enchantment.DAMAGE_ARTHROPODS)+"%"))));											
									}
								}
							}
						}
						if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.blast_protection.name")))) {
							if(Files.cfg.getString("enchantments.blast_protection.description.line_1") != null) {
								new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.blast_protection.description.line_1").replace("%%%", ""+(4*im.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS)))));
								if(Files.cfg.getString("enchantments.blast_protection.description.line_2") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.blast_protection.description.line_2").replace("%%%", ""+(4*im.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS)))));
									if(Files.cfg.getString("enchantments.blast_protection.description.line_3") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.blast_protection.description.line_3").replace("%%%", ""+(4*im.getEnchantLevel(Enchantment.PROTECTION_EXPLOSIONS)))));											
									}
								}
							}
						}
						if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.depth_strider.name")))) {
							if(Files.cfg.getString("enchantments.depth_strider.description.line_1") != null) {
								new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.depth_strider.description.line_1").replace("%%%", ""+(33*im.getEnchantLevel(Enchantment.DEPTH_STRIDER)+"%"))));
								if(Files.cfg.getString("enchantments.depth_strider.description.line_2") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.depth_strider.description.line_2").replace("%%%", ""+(33*im.getEnchantLevel(Enchantment.DEPTH_STRIDER)+"%"))));
									if(Files.cfg.getString("enchantments.depth_strider.description.line_3") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.depth_strider.description.line_3").replace("%%%", ""+(33*im.getEnchantLevel(Enchantment.DEPTH_STRIDER)+"%"))));											
									}
								}
							}
						}
						if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.efficiency.name")))) {
							if(Files.cfg.getString("enchantments.efficiency.description.line_1") != null) {
								new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.efficiency.description.line_1")));
								if(Files.cfg.getString("enchantments.efficiency.description.line_2") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.efficiency.description.line_2")));
									if(Files.cfg.getString("enchantments.efficiency.description.line_3") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.efficiency.description.line_3")));											
									}
								}
							}
						}
						if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.feather_falling.name")))) {
							if(Files.cfg.getString("enchantments.feather_falling.description.line_1") != null) {
								new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.feather_falling.description.line_1").replace("%%%", ""+((5*im.getEnchants().get(Enchantment.PROTECTION_FALL)+"%")))).replace("%%b", ""+1*im.getEnchants().get(Enchantment.PROTECTION_FALL)));
								if(Files.cfg.getString("enchantments.feather_falling.description.line_2") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.feather_falling.description.line_2").replace("%%%", ""+((5*im.getEnchants().get(Enchantment.PROTECTION_FALL)+"%")))).replace("%%b", ""+1*im.getEnchants().get(Enchantment.PROTECTION_FALL)));
									if(Files.cfg.getString("enchantments.feather_falling.description.line_3") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.feather_falling.description.line_3").replace("%%%", ""+((5*im.getEnchants().get(Enchantment.PROTECTION_FALL)+"%")))).replace("%%b", ""+1*im.getEnchants().get(Enchantment.PROTECTION_FALL)));											
									}
								}
							}
						}
						if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fire_aspect.name")))) {
							if(Files.cfg.getString("enchantments.fire_aspect.description.line_1") != null) {
								new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fire_aspect.description.line_1").replace("%%%", ""+(2+im.getEnchants().get(Enchantment.FIRE_ASPECT))).replace("%%d", ""+(10+5*im.getEnchantLevel(Enchantment.FIRE_ASPECT)))));
								if(Files.cfg.getString("enchantments.fire_aspect.description.line_2") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fire_aspect.description.line_2").replace("%%%", ""+(2+im.getEnchants().get(Enchantment.FIRE_ASPECT))).replace("%%d", ""+(10+5*im.getEnchantLevel(Enchantment.FIRE_ASPECT)))));
									if(Files.cfg.getString("enchantments.fire_aspect.description.line_3") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fire_aspect.description.line_3").replace("%%%", ""+(2+im.getEnchants().get(Enchantment.FIRE_ASPECT))).replace("%%d", ""+(10+5*im.getEnchantLevel(Enchantment.FIRE_ASPECT)))));											
									}
								}
							}
						}
						if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fire_protection.name")))) {
							if(Files.cfg.getString("enchantments.fire_protection.description.line_1") != null) {
								new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fire_protection.description.line_1").replace("%%%", ""+(4*im.getEnchants().get(Enchantment.PROTECTION_FIRE)))));
								if(Files.cfg.getString("enchantments.fire_protection.description.line_2") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fire_protection.description.line_2").replace("%%%", ""+(4*im.getEnchants().get(Enchantment.PROTECTION_FIRE)))));
									if(Files.cfg.getString("enchantments.fire_protection.description.line_3") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fire_protection.description.line_3").replace("%%%", ""+(4*im.getEnchants().get(Enchantment.PROTECTION_FIRE)))));											
									}
								}
							}
						}
						if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.flame.name")))) {
							if(Files.cfg.getString("enchantments.flame.description.line_1") != null) {
								new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.flame.description.line_1")));
								if(Files.cfg.getString("enchantments.flame.description.line_2") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.flame.description.line_2")));
									if(Files.cfg.getString("enchantments.flame.description.line_3") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.flame.description.line_3")));											
									}
								}
							}
						}
						if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fortune.name")))) {
							if(Files.cfg.getString("enchantments.fortune.description.line_1") != null) {
								new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fortune.description.line_1").replace("%%%", ""+(10*im.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS))+"%")));
								if(Files.cfg.getString("enchantments.fortune.description.line_2") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fortune.description.line_2").replace("%%%", ""+(10*im.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS))+"%")));
									if(Files.cfg.getString("enchantments.fortune.description.line_3") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fortune.description.line_3").replace("%%%", ""+(10*im.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS))+"%")));											
									}
								}
							}
						}
						if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.knockback.name")))) {
							if(Files.cfg.getString("enchantments.knockback.description.line_1") != null) {
								new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.knockback.description.line_1").replace("%%%", ""+(3*im.getEnchantLevel(Enchantment.KNOCKBACK)))));
								if(Files.cfg.getString("enchantments.knockback.description.line_2") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.knockback.description.line_2").replace("%%%", ""+(3*im.getEnchantLevel(Enchantment.KNOCKBACK)))));
									if(Files.cfg.getString("enchantments.knockback.description.line_3") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.knockback.description.line_3").replace("%%%", ""+(3*im.getEnchantLevel(Enchantment.KNOCKBACK)))));											
									}
								}
							}
						}
						if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.looting.name")))) {
							if(Files.cfg.getString("enchantments.looting.description.line_1") != null) {
								new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.looting.description.line_1").replace("%%%", ""+(15*im.getEnchantLevel(Enchantment.LOOT_BONUS_MOBS))+"%")));
								if(Files.cfg.getString("enchantments.looting.description.line_2") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.looting.description.line_2").replace("%%%", ""+(15*im.getEnchantLevel(Enchantment.LOOT_BONUS_MOBS))+"%")));
									if(Files.cfg.getString("enchantments.looting.description.line_3") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.looting.description.line_3").replace("%%%", ""+(15*im.getEnchantLevel(Enchantment.LOOT_BONUS_MOBS))+"%")));											
									}
								}
							}
						}
						if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.power.name")))) {
							if(Files.cfg.getString("enchantments.power.description.line_1") != null) {
								new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.power.description.line_1").replace("%%%", ""+(8*im.getEnchantLevel(Enchantment.ARROW_DAMAGE)))));
								if(Files.cfg.getString("enchantments.power.description.line_2") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.power.description.line_2").replace("%%%", ""+(8*im.getEnchantLevel(Enchantment.ARROW_DAMAGE)))));
									if(Files.cfg.getString("enchantments.power.description.line_3") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.power.description.line_3").replace("%%%", ""+(8*im.getEnchantLevel(Enchantment.ARROW_DAMAGE)))));											
									}
								}
							}
						}
						if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.projectile_protection.name")))) {
							if(Files.cfg.getString("enchantments.projectile_protection.description.line_1") != null) {
								new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.projectile_protection.description.line_1").replace("%%%", ""+(4*im.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE)))));
								if(Files.cfg.getString("enchantments.projectile_protection.description.line_2") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.projectile_protection.description.line_2").replace("%%%", ""+(4*im.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE)))));
									if(Files.cfg.getString("enchantments.projectile_protection.description.line_3") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.projectile_protection.description.line_3").replace("%%%", ""+(4*im.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE)))));											
									}
								}
							}
						}
						if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.protection.name")))) {
							if(Files.cfg.getString("enchantments.protection.description.line_1") != null) {
								new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.protection.description.line_1").replace("%%%", ""+(3*im.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL)))));
								if(Files.cfg.getString("enchantments.protection.description.line_2") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.protection.description.line_2").replace("%%%", ""+(3*im.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL)))));
									if(Files.cfg.getString("enchantments.protection.description.line_3") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.protection.description.line_3").replace("%%%", ""+(3*im.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL)))));											
									}
								}
							}
						}
						if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.punch.name")))) {
							if(Files.cfg.getString("enchantments.punch.description.line_1") != null) {
								new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.punch.description.line_1").replace("%%%", ""+(3*im.getEnchantLevel(Enchantment.ARROW_KNOCKBACK)))));
								if(Files.cfg.getString("enchantments.punch.description.line_2") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.punch.description.line_2").replace("%%%", ""+(3*im.getEnchantLevel(Enchantment.ARROW_KNOCKBACK)))));
									if(Files.cfg.getString("enchantments.punch.description.line_3") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.punch.description.line_3").replace("%%%", ""+(3*im.getEnchantLevel(Enchantment.ARROW_KNOCKBACK)))));											
									}
								}
							}
						}
						if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.respiration.name")))) {
							if(Files.cfg.getString("enchantments.respiration.description.line_1") != null) {
								new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.respiration.description.line_1").replace("%%%", ""+(15*im.getEnchantLevel(Enchantment.OXYGEN)))));
								if(Files.cfg.getString("enchantments.respiration.description.line_2") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.respiration.description.line_2").replace("%%%", ""+(15*im.getEnchantLevel(Enchantment.OXYGEN)))));
									if(Files.cfg.getString("enchantments.respiration.description.line_3") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.respiration.description.line_3").replace("%%%", ""+(15*im.getEnchantLevel(Enchantment.OXYGEN)))));											
									}
								}
							}
						}
						if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.sharpness.name")))) {
							if(Files.cfg.getString("enchantments.sharpness.description.line_1") != null) {
								new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.sharpness.description.line_1").replace("%%%", ""+((5*im.getEnchants().get(Enchantment.DAMAGE_ALL)+"%")))));
								if(Files.cfg.getString("enchantments.sharpness.description.line_2") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.sharpness.description.line_2").replace("%%%", ""+((5*im.getEnchants().get(Enchantment.DAMAGE_ALL)+"%")))));
									if(Files.cfg.getString("enchantments.sharpness.description.line_3") != null) {											
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.sharpness.description.line_3").replace("%%%", ""+((5*im.getEnchants().get(Enchantment.DAMAGE_ALL)+"%")))));
									}
								}
							}
						}
						if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.silk_touch.name")))) {
							if(Files.cfg.getString("enchantments.silk_touch.description.line_1") != null) {
								new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.silk_touch.description.line_1")));
								if(Files.cfg.getString("enchantments.silk_touch.description.line_2") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.silk_touch.description.line_2")));
									if(Files.cfg.getString("enchantments.silk_touch.description.line_3") != null) {											
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.silk_touch.description.line_3")));
									}
								}
							}
						}
						if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.smite.name")))) {
							if(Files.cfg.getString("enchantments.smite.description.line_1") != null) {
								new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.smite.description.line_1").replace("%%%", ""+((8*im.getEnchants().get(Enchantment.DAMAGE_UNDEAD)+"%")))));
								if(Files.cfg.getString("enchantments.smite.description.line_2") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.smite.description.line_2").replace("%%%", ""+((8*im.getEnchants().get(Enchantment.DAMAGE_UNDEAD)+"%")))));
									if(Files.cfg.getString("enchantments.smite.description.line_3") != null) {											
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.smite.description.line_3").replace("%%%", ""+((8*im.getEnchants().get(Enchantment.DAMAGE_UNDEAD)+"%")))));
									}
								}
							}
						}
						if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.telekinesis.name")))) {
							if(Files.cfg.getString("enchantments.telekinesis.description.line_1") != null) {
								new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.telekinesis.description.line_1")));
								if(Files.cfg.getString("enchantments.telekinesis.description.line_2") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.telekinesis.description.line_2")));
									if(Files.cfg.getString("enchantments.telekinesis.description.line_3") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.telekinesis.description.line_3")));
									}
								}
							}
						}
						if(str.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.thorns.name")))) {
							if(Files.cfg.getString("enchantments.thorns.description.line_1") != null) {
								new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.thorns.description.line_1").replace("%%%", ""+(3*im.getEnchantLevel(Enchantment.THORNS))+"%")));
								if(Files.cfg.getString("enchantments.thorns.description.line_2") != null) {
									new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.thorns.description.line_2").replace("%%%", ""+(3*im.getEnchantLevel(Enchantment.THORNS))+"%")));
									if(Files.cfg.getString("enchantments.thorns.description.line_3") != null) {
										new_lore.add(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.thorns.description.line_3").replace("%%%", ""+(3*im.getEnchantLevel(Enchantment.THORNS))+"%")));
									}
								}
							}
						}
					}
				}
			} else {
				for(String str : lore) {
					new_lore.add(str);
				}
			}
			new_lore.add("          ");
		}
		
		im.setLore(new_lore);
		is.setItemMeta(im);
		if(is.getType().equals(Material.ENCHANTED_BOOK)) {
			EnchantmentStorageMeta enchmeta = (EnchantmentStorageMeta) is.getItemMeta();
			for(Enchantment ench : is.getEnchantments().keySet()) {
				enchmeta.addStoredEnchant(ench, is.getEnchantmentLevel(ench), true);
			}
			enchmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			enchmeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			enchmeta.setDisplayName("§9Enchanted Book");
			is.setItemMeta(enchmeta);
		}
		return is;
	}
	
	public static ItemStack removeEnchantingLore(ItemStack is, boolean removeEnchantments) {
		ItemMeta im = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		ArrayList<String> new_lore = new ArrayList<String>();
		if(im.getLore() != null) {
			lore = (ArrayList<String>) im.getLore();
		} else
			return is;
		
		if(lore.contains("           ") && lore.contains("          ")) {
			for(String str : lore) {
					if(str.equals("           ")) {
						new_lore.add(str);
					} else {
						if(str.equals("          ")) {
							new_lore.add("          ");
						} else {
							if(!new_lore.contains("           ")) {
								new_lore.add(str);
							} else {
								if(!new_lore.contains("           ") && !new_lore.contains("          ")) {
									new_lore.add(str);
								} else {
									if(new_lore.contains("           ") && new_lore.contains("          ")) {
										new_lore.add(str);
									}
								}
						}
					}
				}
			}
		} else {
			new_lore = lore;
		}
		new_lore.remove("          ");
		im.setLore(new_lore);
		if(removeEnchantments == true) {
			for(Enchantment ench : im.getEnchants().keySet()) {
				im.removeEnchant(ench);
			}
		}
		is.setItemMeta(im);
		return is;
	}
	public static String translateEnchLevel(int Int) {
        LinkedHashMap<String, Integer> roman_numerals = new LinkedHashMap<String, Integer>();
        roman_numerals.put("M", 1000);
        roman_numerals.put("CM", 900);
        roman_numerals.put("D", 500);
        roman_numerals.put("CD", 400);
        roman_numerals.put("C", 100);
        roman_numerals.put("XC", 90);
        roman_numerals.put("L", 50);
        roman_numerals.put("XL", 40);
        roman_numerals.put("X", 10);
        roman_numerals.put("IX", 9);
        roman_numerals.put("V", 5);
        roman_numerals.put("IV", 4);
        roman_numerals.put("I", 1);
        String res = "";
        for(Map.Entry<String, Integer> entry : roman_numerals.entrySet()){
            int matches = Int/entry.getValue();
            res += repeat(entry.getKey(), matches);
            Int = Int % entry.getValue();
        }
        return res;
	}
	public static String repeat(String s, int n) {
        if(s == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        for(int i = 0; i < n; i++) {
            sb.append(s);
        }
        return sb.toString();
}
}
