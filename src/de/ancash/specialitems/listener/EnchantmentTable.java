package de.ancash.specialitems.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import de.ancash.specialitems.Files;
import de.ancash.specialitems.PClass;
import de.ancash.specialitems.SpecialItems;
import de.tr7zw.nbtapi.NBTItem;

public class EnchantmentTable implements Listener {

	@EventHandler
	public void onInvClose(InventoryCloseEvent e) {
		if(e.getView().getTitle().equals("Enchant Item") && e.getInventory().getItem(13) != null) {
			if(e.getPlayer().getInventory().firstEmpty() != -1) {
			e.getPlayer().getInventory().addItem(e.getInventory().getItem(13));
			} else {
				e.getPlayer().getLocation().getWorld().dropItem(e.getPlayer().getLocation(), e.getInventory().getItem(13));
			}
		}
	}
	
	private ItemStack addDoNotMoveTag(ItemStack is) {
		NBTItem nbt = new NBTItem(is);
		nbt.setString("donotmove", "lol");
		return nbt.getItem();
	}
	
	@EventHandler
	public void onEnchantmenttableOpen(InventoryOpenEvent e) {
		if(e.getInventory().getType().equals(InventoryType.ENCHANTING)) {
						
			
			
			e.setCancelled(true);
			
			Inventory enchantment = Bukkit.createInventory(null, 9*6, "Enchant Item");
			
			ItemStack gray = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)15);
			ItemMeta stained_glass_meta = gray.getItemMeta();
			stained_glass_meta.setDisplayName(" ");
			gray.setItemMeta(stained_glass_meta);
			gray = addDoNotMoveTag(gray);
			//4 = yellow
			//5 = green
			//7 = gray
			for(int slot = 0; slot < 54; slot++) {
				enchantment.setItem(slot, gray);
			}
			
			enchantment.setItem(13, null);
			
			ItemStack close = new ItemStack(Material.BARRIER);
			ItemMeta closemeta = close.getItemMeta();
			closemeta.setDisplayName("§cClose");
			close.setItemMeta(closemeta);
			enchantment.setItem(49, close);
	
			ItemStack yellow = new ItemStack(Material.STAINED_GLASS_PANE, 1,(byte) 4);
			yellow.setItemMeta(stained_glass_meta);
			yellow = addDoNotMoveTag(yellow);
			enchantment.setItem(0, yellow);
			enchantment.setItem(8, yellow);
			enchantment.setItem(45, yellow);
			enchantment.setItem(53, yellow);
			
			ItemStack red = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
			red.setItemMeta(stained_glass_meta);
			red = addDoNotMoveTag(red);
			enchantment.setItem(2, red);
			enchantment.setItem(3, red);
			enchantment.setItem(4, red);
			enchantment.setItem(5, red);
			enchantment.setItem(6, red);
			enchantment.setItem(12, red);
			enchantment.setItem(14, red);
						
			ItemStack clay = new ItemStack(Material.INK_SACK,1,  (byte) 8);
			ItemMeta im = clay.getItemMeta();
			im.setDisplayName("§cEnchant Item");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("§7Place an item in the open slot");
			lore.add("§7to enchant it!");
			im.setLore(lore);
			clay.setItemMeta(im);
			clay = addDoNotMoveTag(clay);
			enchantment.setItem(29, clay);
			enchantment.setItem(31, clay);
			enchantment.setItem(33, clay);
			e.getPlayer().openInventory(enchantment);
		}
	}
	
	@EventHandler
	public void onInvInteract(InventoryClickEvent e) {
				
		Player p = (Player) e.getWhoClicked();
		if(e.getView().getTitle().equals("Enchant Item") && e.getInventory().getSize() >= 54) {
			
			if(e.getAction().equals(InventoryAction.NOTHING) 
					|| e.getAction().equals(InventoryAction.DROP_ALL_CURSOR)
					|| e.getAction().equals(InventoryAction.DROP_ALL_SLOT)
					|| e.getAction().equals(InventoryAction.DROP_ONE_CURSOR)
					|| e.getAction().equals(InventoryAction.DROP_ONE_SLOT)
					|| e.getAction().equals(InventoryAction.SWAP_WITH_CURSOR)) {
				e.setCancelled(true);
				return;
			}		
			
			if(!e.getView().getTitle().equals("Enchant Item")) {
				e.setCancelled(false);
				if(e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) && e.getInventory().getItem(13) == null) {
					
					if(e.getCurrentItem().getItemMeta().hasEnchants()) {
						ItemStack clay = new ItemStack(Material.INK_SACK,1,  (byte) 8);
						ItemMeta im = clay.getItemMeta();
						im.setDisplayName("§cAlready Enchanted!");
						ArrayList<String> lore = new ArrayList<String>();
						lore.add("§7This item has already been");
						lore.add("§7enchanted! Use an Anvil to add");
						lore.add("§7more enchantments to it!");
						im.setLore(lore);
						clay.setItemMeta(im);
						clay = addDoNotMoveTag(clay);
						e.getInventory().setItem(29, clay);
						e.getInventory().setItem(31, clay);
						e.getInventory().setItem(33, clay);
						return;
					}
					Bukkit.getScheduler().runTaskLater(SpecialItems.plugin, new Runnable() {
						
						@Override
						public void run() {
							prepareEnchantments(e);
						}
					}, 0);
					return;
				} else {
					Bukkit.getScheduler().runTaskLater(SpecialItems.plugin, new Runnable() {
						
						@Override
						public void run() {
							prepareEnchantments(e);
						}
					}, 0);
					return;
				}
			} else {
				e.setCancelled(true);
			}
			ItemStack is = e.getInventory().getItem(e.getSlot());
			ItemStack currentIs = e.getCurrentItem();
			ItemStack cursorIs = e.getCursor();
			if(currentIs != null && !currentIs.getType().equals(Material.AIR)) {
				if(!(new NBTItem(currentIs).hasKey("donotmove")) && !currentIs.getType().equals(Material.BARRIER)) {
					e.setCancelled(false);
				}
			}
			if(cursorIs != null && !cursorIs.getType().equals(Material.AIR)) {
				if(!(new NBTItem(cursorIs).hasKey("donotmove")) && !cursorIs.getType().equals(Material.BARRIER)) {
					e.setCancelled(false);
				}
			}
			
			if(is != null) {
				if(is.getType().equals(Material.BARRIER) && !e.getAction().equals(InventoryAction.PICKUP_HALF)) {
					p.closeInventory();
					return;
				}
				if(e.getAction().equals(InventoryAction.PICKUP_ALL) 
						|| e.getAction().equals(InventoryAction.PICKUP_ONE)
						|| e.getAction().equals(InventoryAction.PICKUP_HALF)
						|| e.getAction().equals(InventoryAction.PICKUP_SOME)) {
					if(e.getSlot() == 29 || e.getSlot() == 31 || e.getSlot() == 33) {
						if(e.getInventory().getItem(13) != null) {							
							
							if(e.getSlot() == 29 && !e.getInventory().getItem(29).getType().equals(Material.INK_SACK)) {
								if(!e.getInventory().getItem(29).getItemMeta().getLore().contains("§cNot enough levels!")) {
									if(p.getLevel() >= e.getInventory().getItem(29).getAmount()) {
										p.setLevel(p.getLevel()-e.getInventory().getItem(29).getAmount());
										ItemStack is_to_ench = e.getInventory().getItem(13).clone();
										if(!is_to_ench.getType().equals(Material.BOOK)) {
											for(Enchantment ench : e.getInventory().getItem(29).getEnchantments().keySet()) {
												is_to_ench.addUnsafeEnchantment(ench, e.getInventory().getItem(29).getEnchantmentLevel(ench));
											}
										} else {
											is_to_ench.setType(Material.ENCHANTED_BOOK);
											EnchantmentStorageMeta enchmeta = (EnchantmentStorageMeta) is_to_ench.getItemMeta();
											for(Enchantment ench : e.getInventory().getItem(29).getEnchantments().keySet()) {
												enchmeta.addStoredEnchant(ench, e.getInventory().getItem(29).getEnchantmentLevel(ench), true);
											}
											is_to_ench.setItemMeta(enchmeta);
										}
										is_to_ench = Enchanting.addEnchantingLore(is_to_ench);
										e.getInventory().setItem(13, is_to_ench);
										ItemStack clay = new ItemStack(Material.INK_SACK,1,  (byte) 8);
										ItemMeta im = clay.getItemMeta();
										im.setDisplayName("§cAlready Enchanted!");
										ArrayList<String> lore = new ArrayList<String>();
										lore.add("§7This item has already been");
										lore.add("§7enchanted! Use an Anvil to add");
										lore.add("§7more enchantments to it!");
										im.setLore(lore);
										clay.setItemMeta(im);
										clay = addDoNotMoveTag(clay);
										e.getInventory().setItem(29, clay);
										e.getInventory().setItem(31, clay);
										e.getInventory().setItem(33, clay);
										return;
									}
								}
							}
							
							
							if(e.getSlot() == 31 && !e.getInventory().getItem(31).getType().equals(Material.INK_SACK)) {
								if(!e.getInventory().getItem(31).getItemMeta().getLore().contains("§cNot enough levels!")) {
									if(p.getLevel() >= e.getInventory().getItem(31).getAmount()) {
										p.setLevel(p.getLevel()-e.getInventory().getItem(31).getAmount());
										ItemStack is_to_ench = e.getInventory().getItem(13).clone();
										if(!is_to_ench.getType().equals(Material.BOOK)) {
											for(Enchantment ench : e.getInventory().getItem(31).getEnchantments().keySet()) {
												is_to_ench.addUnsafeEnchantment(ench, e.getInventory().getItem(31).getEnchantmentLevel(ench));
											}
										} else {
											is_to_ench.setType(Material.ENCHANTED_BOOK);
											EnchantmentStorageMeta enchmeta = (EnchantmentStorageMeta) is_to_ench.getItemMeta();
											for(Enchantment ench : e.getInventory().getItem(31).getEnchantments().keySet()) {
												enchmeta.addStoredEnchant(ench, e.getInventory().getItem(31).getEnchantmentLevel(ench), true);
											}
											is_to_ench.setItemMeta(enchmeta);
										}
										is_to_ench = Enchanting.addEnchantingLore(is_to_ench);
										e.getInventory().setItem(13, is_to_ench);
										ItemStack clay = new ItemStack(Material.INK_SACK,1,  (byte) 8);
										ItemMeta im = clay.getItemMeta();
										im.setDisplayName("§cAlready Enchanted!");
										ArrayList<String> lore = new ArrayList<String>();
										lore.add("§7This item has already been");
										lore.add("§7enchanted! Use an Anvil to add");
										lore.add("§7more enchantments to it!");
										im.setLore(lore);
										clay.setItemMeta(im);
										clay = addDoNotMoveTag(clay);
										e.getInventory().setItem(29, clay);
										e.getInventory().setItem(31, clay);
										e.getInventory().setItem(33, clay);
										return;
									}
								}
							}
							
							
							if(e.getSlot() == 33 && !e.getInventory().getItem(33).getType().equals(Material.INK_SACK)) {
								if(!e.getInventory().getItem(33).getItemMeta().getLore().contains("§cNot enough levels!")) {
									if(p.getLevel() >= e.getInventory().getItem(33).getAmount()) {
										p.setLevel(p.getLevel()-e.getInventory().getItem(33).getAmount());
										ItemStack is_to_ench = e.getInventory().getItem(13).clone();
										if(!is_to_ench.getType().equals(Material.BOOK)) {
											for(Enchantment ench : e.getInventory().getItem(33).getEnchantments().keySet()) {
												is_to_ench.addUnsafeEnchantment(ench, e.getInventory().getItem(33).getEnchantmentLevel(ench));
											}
										} else {
											is_to_ench.setType(Material.ENCHANTED_BOOK);
											EnchantmentStorageMeta enchmeta = (EnchantmentStorageMeta) is_to_ench.getItemMeta();
											for(Enchantment ench : e.getInventory().getItem(33).getEnchantments().keySet()) {
												enchmeta.addStoredEnchant(ench, e.getInventory().getItem(33).getEnchantmentLevel(ench), true);
											}
											is_to_ench.setItemMeta(enchmeta);
										}
										is_to_ench = Enchanting.addEnchantingLore(is_to_ench);
										e.getInventory().setItem(13, is_to_ench);
										ItemStack clay = new ItemStack(Material.INK_SACK,1,  (byte) 8);
										ItemMeta im = clay.getItemMeta();
										im.setDisplayName("§cAlready Enchanted!");
										ArrayList<String> lore = new ArrayList<String>();
										lore.add("§7This item has already been");
										lore.add("§7enchanted! Use an Anvil to add");
										lore.add("§7more enchantments to it!");
										im.setLore(lore);
										clay.setItemMeta(im);
										clay = addDoNotMoveTag(clay);
										e.getInventory().setItem(29, clay);
										e.getInventory().setItem(31, clay);
										e.getInventory().setItem(33, clay);
										return;
									}
								}
							}
						}
					}
				}
			}
			if(e.getSlot() == 13) {
				e.setCancelled(false);
				
				if(e.getAction().equals(InventoryAction.PLACE_ALL) || e.getAction().equals(InventoryAction.PLACE_ONE) && e.getInventory().getItem(13) == null) {
					
					Bukkit.getScheduler().runTaskLater(SpecialItems.plugin, new Runnable() {
						
						@Override
						public void run() {
							prepareEnchantments(e);
						}
					}, 0);
					return;
				} else {
					Bukkit.getScheduler().runTaskLater(SpecialItems.plugin, new Runnable() {
						
						@Override
						public void run() {

							if(e.getInventory().getItem(13) == null) {
								ItemStack clay = new ItemStack(Material.INK_SACK,1,  (byte) 8);
								ItemMeta im = clay.getItemMeta();
								im.setDisplayName("§cEnchant Item");
								ArrayList<String> lore = new ArrayList<String>();
								im.setLore(lore);
								clay.setItemMeta(im);
								lore.add("§7Place an item in the open slot");
								lore.add("§7to enchant it!");
								im.setLore(lore);
								clay.setItemMeta(im);
								clay = addDoNotMoveTag(clay);
								e.getInventory().setItem(29, clay);
								e.getInventory().setItem(31, clay);
								e.getInventory().setItem(33, clay);
							}
							
						}
					}, 0);
					return;
				}
			}
			return;
		}
	}
	
	public static void loadEnchantmentTableStuff() {
		book_enchs.add(Enchantment.ARROW_DAMAGE);
		book_enchs.add(Enchantment.ARROW_FIRE); 
		book_enchs.add(Enchantment.ARROW_INFINITE); 
		book_enchs.add(Enchantment.ARROW_KNOCKBACK);
		book_enchs.add(Enchantment.DAMAGE_ALL); 
		book_enchs.add(Enchantment.DAMAGE_ARTHROPODS); 
		book_enchs.add(Enchantment.DAMAGE_UNDEAD);
		book_enchs.add(Enchantment.DEPTH_STRIDER);
		book_enchs.add(Enchantment.DIG_SPEED);
		book_enchs.add(Enchantment.FIRE_ASPECT);
		book_enchs.add(Enchantment.KNOCKBACK); 
		book_enchs.add(Enchantment.LOOT_BONUS_BLOCKS);
		book_enchs.add(Enchantment.LOOT_BONUS_MOBS); 
		book_enchs.add(Enchantment.LUCK);
		book_enchs.add(Enchantment.LURE);
		book_enchs.add(Enchantment.OXYGEN);
		book_enchs.add(Enchantment.PROTECTION_ENVIRONMENTAL);
		book_enchs.add(Enchantment.PROTECTION_EXPLOSIONS);
		book_enchs.add(Enchantment.PROTECTION_FALL);
		book_enchs.add(Enchantment.PROTECTION_FIRE);
		book_enchs.add(Enchantment.PROTECTION_PROJECTILE);
		book_enchs.add(Enchantment.SILK_TOUCH);
		book_enchs.add(Enchantment.THORNS);
		book_enchs.add(Enchantment.WATER_WORKER);
		book_enchs.add(SpecialItems.ench_aiming);
		book_enchs.add(SpecialItems.ench_critical);
		book_enchs.add(SpecialItems.ench_cubism);
		book_enchs.add(SpecialItems.ench_enderslayer);
		book_enchs.add(SpecialItems.ench_firstStrike);
		book_enchs.add(SpecialItems.ench_growth);
		book_enchs.add(SpecialItems.ench_lifesteal);
		book_enchs.add(SpecialItems.ench_snipe);
		book_enchs.add(SpecialItems.ench_telekinesis);
		
		hoe_enchs.add(SpecialItems.ench_telekinesis);
		
		shovel_enchs.add(SpecialItems.ench_telekinesis);
		shovel_enchs.add(Enchantment.SILK_TOUCH);
		shovel_enchs.add(Enchantment.DIG_SPEED);
		
		axe_enchs.add(SpecialItems.ench_telekinesis);
		axe_enchs.add(Enchantment.SILK_TOUCH);
		axe_enchs.add(Enchantment.DIG_SPEED);
		
		pickaxe_enchs.add(SpecialItems.ench_telekinesis);
		pickaxe_enchs.add(Enchantment.SILK_TOUCH);
		pickaxe_enchs.add(Enchantment.LOOT_BONUS_BLOCKS);
		pickaxe_enchs.add(Enchantment.DIG_SPEED);
		
		helmet_enchs.add(Enchantment.PROTECTION_ENVIRONMENTAL);
		helmet_enchs.add(Enchantment.PROTECTION_EXPLOSIONS);
		helmet_enchs.add(Enchantment.PROTECTION_FIRE);
		helmet_enchs.add(Enchantment.PROTECTION_PROJECTILE);
		helmet_enchs.add(Enchantment.WATER_WORKER);
		helmet_enchs.add(Enchantment.OXYGEN);
		helmet_enchs.add(Enchantment.THORNS);
		helmet_enchs.add(SpecialItems.ench_growth);
		
		chestplate_enchs.add(Enchantment.PROTECTION_ENVIRONMENTAL);
		chestplate_enchs.add(Enchantment.PROTECTION_EXPLOSIONS);
		chestplate_enchs.add(Enchantment.PROTECTION_FIRE);
		chestplate_enchs.add(Enchantment.PROTECTION_PROJECTILE);
		chestplate_enchs.add(Enchantment.THORNS);
		chestplate_enchs.add(SpecialItems.ench_growth);
		
		leggings_enchs.add(Enchantment.PROTECTION_ENVIRONMENTAL);
		leggings_enchs.add(Enchantment.PROTECTION_EXPLOSIONS);
		leggings_enchs.add(Enchantment.PROTECTION_FIRE);
		leggings_enchs.add(Enchantment.PROTECTION_PROJECTILE);
		leggings_enchs.add(Enchantment.THORNS);
		leggings_enchs.add(SpecialItems.ench_growth);
		
		boots_enchs.add(Enchantment.PROTECTION_ENVIRONMENTAL);
		boots_enchs.add(Enchantment.PROTECTION_EXPLOSIONS);
		boots_enchs.add(Enchantment.PROTECTION_FIRE);
		boots_enchs.add(Enchantment.PROTECTION_PROJECTILE);
		boots_enchs.add(Enchantment.THORNS);
		boots_enchs.add(Enchantment.PROTECTION_FALL);
		boots_enchs.add(Enchantment.DEPTH_STRIDER);
		boots_enchs.add(SpecialItems.ench_growth);
		
		bow_enchs.add(SpecialItems.ench_aiming);
		bow_enchs.add(SpecialItems.ench_snipe);
		bow_enchs.add(SpecialItems.ench_telekinesis);
		bow_enchs.add(SpecialItems.ench_cubism);
		bow_enchs.add(Enchantment.ARROW_DAMAGE);
		bow_enchs.add(Enchantment.ARROW_INFINITE);
		bow_enchs.add(Enchantment.ARROW_KNOCKBACK);
		bow_enchs.add(Enchantment.ARROW_FIRE);
		
		sword_enchs.add(Enchantment.DAMAGE_ALL);
		sword_enchs.add(Enchantment.DAMAGE_ARTHROPODS);
		sword_enchs.add(Enchantment.DAMAGE_UNDEAD);
		sword_enchs.add(Enchantment.FIRE_ASPECT);
		sword_enchs.add(Enchantment.KNOCKBACK);
		sword_enchs.add(Enchantment.LOOT_BONUS_MOBS);
		sword_enchs.add(SpecialItems.ench_firstStrike);
		sword_enchs.add(SpecialItems.ench_lifesteal);
		sword_enchs.add(SpecialItems.ench_telekinesis);
		sword_enchs.add(SpecialItems.ench_critical);
		sword_enchs.add(SpecialItems.ench_cubism);
		sword_enchs.add(SpecialItems.ench_enderslayer);
	}
	
	static ArrayList<Enchantment> book_enchs = new ArrayList<Enchantment>();
	
	static ArrayList<Enchantment> hoe_enchs = new ArrayList<Enchantment>();
	
	static ArrayList<Enchantment> shovel_enchs = new ArrayList<Enchantment>();
	
	static ArrayList<Enchantment> axe_enchs = new ArrayList<Enchantment>();
	
	static ArrayList<Enchantment> pickaxe_enchs = new ArrayList<Enchantment>();
	
	static ArrayList<Enchantment> helmet_enchs = new ArrayList<Enchantment>();
	
	static ArrayList<Enchantment> chestplate_enchs = new ArrayList<Enchantment>();
	
	static ArrayList<Enchantment> leggings_enchs = new ArrayList<Enchantment>();
	
	static ArrayList<Enchantment> boots_enchs = new ArrayList<Enchantment>();
	
	static ArrayList<Enchantment> bow_enchs = new ArrayList<Enchantment>();

	static ArrayList<Enchantment> sword_enchs = new ArrayList<Enchantment>();
	
	private void setEnchantmentBottles(ArrayList<Enchantment> possibleEnchantments, InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		ArrayList<String> ench_name = new ArrayList<String>();
		
		Random rnd = new Random();
		
		HashMap<Enchantment, Integer> enchs_to_add = new HashMap<Enchantment, Integer>();
		
		ItemStack is = e.getInventory().getItem(13).clone();
		
		if(is.getItemMeta().hasEnchants()) {
			return;
		}
		PClass pS = PClass.playerStats.get(p.getUniqueId());
		ItemStack ench1 = new ItemStack(Material.EXP_BOTTLE, rnd.nextInt(9)+8);
		ItemMeta ench1meta = ench1.getItemMeta();
		ench1meta.setDisplayName("§aEnchant Item §8- §6"+ench1.getAmount()+" Levels");
		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add("§7Guarantees at least:");
		
		int total_ench_level = 2;
		
		Collections.shuffle(possibleEnchantments);
		for(Enchantment ench : possibleEnchantments) {
			if(total_ench_level == 0) {
				break;
			}
			if(ench == null) continue;
			for(int i = 0; i < ench.getMaxLevel()-1; i++) {
				if(total_ench_level == 0) {
					break;
				}
				if((rnd.nextInt(100)+1) <= ench1.getAmount()*2+30) {
					if(ench.getName().equals("Cubism") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("Telekinesis") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 1) {
						break;
					}
					if(ench.getName().equals("Aiming") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("Snipe") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 3) {
						break;
					}
					if(ench.getName().equals("ARROW_DAMAGE") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("ARROW_KNOCKBACK") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 2) {
						break;
					}
					if(ench.getName().equals("ARROW_INFINITE") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 1) {
						break;
					}
					if(ench.getName().equals("ARROW_FIRE") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 1) {
						break;
					}
					if(ench.getName().equals("Ender Slayer") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("Critical") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("Telekinesis") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 1) {
						break;
					}
					if(ench.getName().equals("Life Steal") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 3) {
						break;
					}
					if(ench.getName().equals("First Strike") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 4) {
						break;
					}
					if(ench.getName().equals("DAMAGE_ALL") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("DAMAGE_ALL")) {
						if(ench_name.contains("Smite") || ench_name.contains("Bane of Arthropods")) {
							break;
						}
					}
					if(ench.getName().equals("DAMAGE_ARTHROPODS") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("DAMAGE_ARTHROPODS")) {
						if(ench_name.contains("Smite") || ench_name.contains("Sharpness")) {
							break;
						}
					}
					if(ench.getName().equals("DAMAGE_UNDEAD") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("DAMAGE_UNDEAD")) {
						if(ench_name.contains("Sharpness") || ench_name.contains("Bane of Arthropods")) {
							break;
						}
					}
					if(ench.getName().equals("FIRE_ASPECT") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 2) {
						break;
					}
					if(ench.getName().equals("KNOCKBACK") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 2) {
						break;
					}
					if(ench.getName().equals("LOOT_BONUS_MOBS") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 3) {
						break;
					}
					if(ench.getName().equals("PROTECTION_ENVIRONMENTAL") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("PROTECTION_ENVIRONMENTAL")) {
						if(ench_name.contains("Projectile Protection")
								|| ench_name.contains("Fire Protection")
								|| ench_name.contains("Blast Protection")) {
							break;
						}
					}
					if(ench.getName().equals("PROTECTION_PROJECTILE") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("PROTECTION_PROJECTILE")) {
						if(ench_name.contains("Protection")
								|| ench_name.contains("Fire Protection")
								|| ench_name.contains("Blast Protection")) {
							break;
						}
					}
					if(ench.getName().equals("SILK_TOUCH") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 1) {
						break;
					}
					if(ench.getName().equals("LOOT_BONUS_BLOCKS") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 3) {
						break;
					}
					if(ench.getName().equals("SILK_TOUCH")) {
						if(ench_name.contains("Fortune")) {
							break;
						}
					}
					if(ench.getName().equals("LOOT_BONUS_BLOCKS")) {
						if(ench_name.contains("Silk Touch")) {
							break;
						}
					}
					if(ench.getName().equals("PROTECTION_EXPLOSIONS") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("PROTECTION_EXPLOSIONS")) {
						if(ench_name.contains("Protection")
								|| ench_name.contains("Fire Protection")
								|| ench_name.contains("Projectile Protection")) {
							break;
						}
					}
					if(ench.getName().equals("PROTECTION_FIRE") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("PROTECTION_FIRE")) {
						if(ench_name.contains("Protection")
								|| ench_name.contains("Blast Protection")
								|| ench_name.contains("Projectile Protection")) {
							break;
						}
					}
					if(ench.getName().equals("WATER_WORKER") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 1) {
						break;
					}
					if(ench.getName().equals("OXYGEN") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 3) {
						break;
					}
					if(ench.getName().equals("THORNS") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 3) {
						break;
					}
					if(ench.getName().equals("Growth") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("DEPTH_STRIDER") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 3) {
						break;
					}
					if(ench.getName().equals("PROTECTION_FALL") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("DIG_SPEED") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("SILK_TOUCH") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 1) {
						break;
					}
					if(ench.getName().equals("LOOT_BONUS_BLOCKS") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 3) {
						break;
					}
					if(ench.getName().equals("SILK_TOUCH")) {
						if(ench_name.contains("Fortune")) {
							break;
						}
					}
					if(ench.getName().equals("LOOT_BONUS_BLOCKS")) {
						if(ench_name.contains("Silk Touch")) {
							break;
						}
					}
					if(total_ench_level == 0) {
						break;
					}
					if(!enchs_to_add.containsKey(ench)) {
						enchs_to_add.put(ench, 1);
						total_ench_level--;
					} else {
						enchs_to_add.put(ench, enchs_to_add.get(ench)+1);
						total_ench_level--;
					}
					if(ench.getName().equals("Cubism")) {
						ench_name.add("Cubism");
					}
					if(ench.getName().equals("Telekinesis")) {
						ench_name.add("Telekinesis");
					}
					if(ench.getName().equals("Aiming")) {
						ench_name.add("Aiming");
					}
					if(ench.getName().equals("Snipe")) {
						ench_name.add("Snipe");
					}
					if(ench.getName().equals("ARROW_DAMAGE")) {
						ench_name.add("Power");
					}
					if(ench.getName().equals("ARROW_KNOCKBACK")) {
						ench_name.add("Punch");
					}
					if(ench.getName().equals("ARROW_INFINITE")) {
						ench_name.add("Infinity");
					}
					if(ench.getName().equals("ARROW_FIRE")) {
						ench_name.add("Flame");
					}
					if(ench.getName().equals("Ender Slayer")) {
						ench_name.add("Ender Slayer");
					}
					if(ench.getName().equals("Critical")) {
						ench_name.add("Critical");
					}
					if(ench.getName().equals("Life Steal")) {
						ench_name.add("Life Steal");
					}
					if(ench.getName().equals("First Strike")) {
						ench_name.add("First Strike");
					}
					if(ench.getName().equals("DAMAGE_ALL") && !ench_name.contains("Bane of Arthropods") && !ench_name.contains("Smite")) {
						ench_name.add("Sharpness");
					}
					if(ench.getName().equals("DAMAGE_ARTHROPODS") && !ench_name.contains("Smite") && !ench_name.contains("Sharpness")) {
						ench_name.add("Bane of Arthropods");
					}
					if(ench.getName().equals("DAMAGE_UNDEAD") && !ench_name.contains("Bane of Arthropods") && !ench_name.contains("Sharpness")) {
						ench_name.add("Smite");
					}
					if(ench.getName().equals("FIRE_ASPECT")) {
						ench_name.add("Fire Aspect");
					}
					if(ench.getName().equals("KNOCKBACK")) {
						ench_name.add("Knockback");
					}
					if(ench.getName().equals("LOOT_BONUS_MOBS")) {
						ench_name.add("Looting");
					}
					if(ench.getName().equals("PROTECTION_ENVIRONMENTAL")) {
						ench_name.add("Protection");
					}
					if(ench.getName().equals("PROTECTION_EXPLOSIONS")) {
						ench_name.add("Blast Protection");
					}
					if(ench.getName().equals("PROTECTION_PROJECTILE")) {
						ench_name.add("Projectile Protection");
					}
					if(ench.getName().equals("PROTECTION_FIRE")) {
						ench_name.add("Fire Protection");
					}
					if(ench.getName().equals("WATER_WORKER")) {
						ench_name.add("Aqua Affinity");
					}
					if(ench.getName().equals("OXYGEN")) {
						ench_name.add("Respiration");
					}
					if(ench.getName().equals("THORNS")) {
						ench_name.add("Thorns");
					}
					if(ench.getName().equals("Growth")) {
						ench_name.add("Growth");
					}
					if(ench.getName().equals("DEPTH_STRIDER")) {
						ench_name.add("Depth Strider");
					}
					if(ench.getName().equals("PROTECTION_FALL")) {
						ench_name.add("Feather Falling");
					}
					if(ench.getName().equals("DIG_SPEED")) {
						ench_name.add("Efficiency");
					}
					if(ench.getName().equals("SILK_TOUCH")) {
						ench_name.add("Silk Touch");
					}
					if(ench.getName().equals("LOOT_BONUS_BLOCKS")) {
						ench_name.add("Fortune");
					}
				}
			}
		}
		if(ench_name.size() > 1) {
			int random = rnd.nextInt(ench_name.size()-1);
			if(ench_name.get(random) == "Cubism") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.cubism.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_cubism)));
			}
			if(ench_name.get(random) == "Telekinesis") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.telekinesis.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_telekinesis)));
			}
			if(ench_name.get(random) == "Aiming") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.aiming.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_aiming)));
			}
			if(ench_name.get(random) == "Snipe") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.snipe.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_snipe)));
			}
			if(ench_name.get(random) == "Power") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.power.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.ARROW_DAMAGE)));
			}
			if(ench_name.get(random) == "Punch") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.punch.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.ARROW_KNOCKBACK)));
			}
			if(ench_name.get(random) == "Infinity") {
				lore1.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.ARROW_INFINITE)));
			}
			if(ench_name.get(random) == "Flame") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.flame.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.ARROW_FIRE)));
			}
			if(ench_name.get(random) == "Ender Slayer") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.ender_slayer.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_enderslayer)));
			}
			if(ench_name.get(random) == "Critical") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.critical.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_critical)));
			}
			if(ench_name.get(random) == "Life Steal") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.life_steal.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_lifesteal)));
			}
			if(ench_name.get(random) == "First Strike") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.first_strike.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_firstStrike)));
			}
			if(ench_name.get(random) == "Sharpness") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.sharpness.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.DAMAGE_ALL)));
			}
			if(ench_name.get(random) == "Looting") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.looting.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.LOOT_BONUS_MOBS)));
			}
			if(ench_name.get(random) == "Bane of Arthropods") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.bane_of_arthropods.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.DAMAGE_ARTHROPODS)));
			}
			if(ench_name.get(random) == "Smite") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.smite.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.DAMAGE_UNDEAD)));
			}
			if(ench_name.get(random) == "Fire Aspect") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fire_aspect.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.FIRE_ASPECT)));
			}
			if(ench_name.get(random) == "Knockback") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.knockback.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.KNOCKBACK)));
			}
			if(ench_name.get(random) == "Protection") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.protection.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.PROTECTION_ENVIRONMENTAL)));
			}
			if(ench_name.get(random) == "Blast Protection") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.blast_protection.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.PROTECTION_EXPLOSIONS)));
			}
			if(ench_name.get(random) == "Projectile Protection") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.projectile_protection.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.PROTECTION_PROJECTILE)));
			}
			if(ench_name.get(random) == "Fire Protection") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fire_protection.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.PROTECTION_FIRE)));
			}
			if(ench_name.get(random) == "Aqua Affinity") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.aqua_affinity.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.WATER_WORKER)));
			}
			if(ench_name.get(random) == "Respiration") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.respiration.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.OXYGEN)));
			}
			if(ench_name.get(random) == "Thorns") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.thorns.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.THORNS)));
			}
			if(ench_name.get(random) == "Growth") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.growth.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_growth)));
			}
			if(ench_name.get(random) == "Depth Strider") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.depth_strider.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.DEPTH_STRIDER)));
			}
			if(ench_name.get(random) == "Feather Falling") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.feather_falling.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.PROTECTION_FALL)));
			}
			if(ench_name.get(random) == "Efficiency") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.efficiency.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.DIG_SPEED)));
			}
			if(ench_name.get(random) == "Silk Touch") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.silk_touch.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.SILK_TOUCH)));
			}
			if(ench_name.get(random) == "Fortune") {
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fortune.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.LOOT_BONUS_BLOCKS)));
			}
		} else {
			if(ench_name.size() == 1) {
				if(!ench_name.contains(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.telekinesis.ench_table_name")))) {
					enchs_to_add.put(SpecialItems.ench_telekinesis, 1);
					lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.telekinesis.ench_table_name"))+" I");
				} else {
					enchs_to_add.put(SpecialItems.ench_telekinesis, 1);
					lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.telekinesis.ench_table_name"))+" I");
				}
			}
			if(ench_name.size() == 0) {
				enchs_to_add.put(SpecialItems.ench_telekinesis, 1);
				lore1.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.telekinesis.ench_table_name"))+" I");
			}
		}
		ench_name.clear();
		lore1.add(" ");
		if(ench1.getAmount() > p.getLevel()) {
			lore1.add("§cNot enough levels!");
		} else {
			lore1.add("§eClick to enchant!");
		}
		ench1meta.setLore(lore1);
		ench1meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		ench1.setItemMeta(ench1meta);
		ench1.addUnsafeEnchantments(enchs_to_add);
		enchs_to_add.clear();
		ench1 = addDoNotMoveTag(ench1);
		e.getInventory().setItem(29, ench1);
		
		
		
		ItemStack ench2 = new ItemStack(Material.EXP_BOTTLE, rnd.nextInt(9)+32);
		ItemMeta ench2meta = ench2.getItemMeta();
		ench2meta.setDisplayName("§aEnchant Item §8- §6"+ench2.getAmount()+" Levels");
		ArrayList<String> lore2 = new ArrayList<String>();
		lore2.add("§7Guarantees at least:");
		total_ench_level = 4;
		Collections.shuffle(possibleEnchantments);
		for(Enchantment ench : possibleEnchantments) {
			if(total_ench_level == 0) {
				break;
			}
			if(ench == null) continue;
			for(int i = 0; i < ench.getMaxLevel()-1; i++) {
				if(total_ench_level == 0) {
					break;
				}
				if((rnd.nextInt(100)+1) <= ench2.getAmount()*2+30) {
					if(ench.getName().equals("Cubism") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("Telekinesis") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 1) {
						break;
					}
					if(ench.getName().equals("Aiming") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("Snipe") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 3) {
						break;
					}
					if(ench.getName().equals("ARROW_DAMAGE") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("ARROW_KNOCKBACK") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 2) {
						break;
					}
					if(ench.getName().equals("ARROW_INFINITE") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 1) {
						break;
					}
					if(ench.getName().equals("ARROW_FIRE") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 1) {
						break;
					}
					if(ench.getName().equals("Ender Slayer") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("Critical") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("Telekinesis") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 1) {
						break;
					}
					if(ench.getName().equals("Life Steal") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 3) {
						break;
					}
					if(ench.getName().equals("First Strike") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 4) {
						break;
					}
					if(ench.getName().equals("DAMAGE_ALL") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("DAMAGE_ALL")) {
						if(ench_name.contains("Smite") || ench_name.contains("Bane of Arthropods")) {
							break;
						}
					}
					if(ench.getName().equals("DAMAGE_ARTHROPODS") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("DAMAGE_ARTHROPODS")) {
						if(ench_name.contains("Smite") || ench_name.contains("Sharpness")) {
							break;
						}
					}
					if(ench.getName().equals("DAMAGE_UNDEAD") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("DAMAGE_UNDEAD")) {
						if(ench_name.contains("Sharpness") || ench_name.contains("Bane of Arthropods")) {
							break;
						}
					}
					if(ench.getName().equals("FIRE_ASPECT") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 2) {
						break;
					}
					if(ench.getName().equals("KNOCKBACK") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 2) {
						break;
					}
					if(ench.getName().equals("LOOT_BONUS_MOBS") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 3) {
						break;
					}
					if(ench.getName().equals("SILK_TOUCH") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 1) {
						break;
					}
					if(ench.getName().equals("LOOT_BONUS_BLOCKS") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 3) {
						break;
					}
					if(ench.getName().equals("SILK_TOUCH")) {
						if(ench_name.contains("Fortune")) {
							break;
						}
					}
					if(ench.getName().equals("LOOT_BONUS_BLOCKS")) {
						if(ench_name.contains("Silk Touch")) {
							break;
						}
					}
					if(ench.getName().equals("PROTECTION_ENVIRONMENTAL") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("PROTECTION_ENVIRONMENTAL")) {
						if(ench_name.contains("Projectile Protection")
								|| ench_name.contains("Fire Protection")
								|| ench_name.contains("Blast Protection")) {
							break;
						}
					}
					if(ench.getName().equals("PROTECTION_PROJECTILE") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("PROTECTION_PROJECTILE")) {
						if(ench_name.contains("Protection")
								|| ench_name.contains("Fire Protection")
								|| ench_name.contains("Blast Protection")) {
							break;
						}
					}
					if(ench.getName().equals("PROTECTION_EXPLOSIONS") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("PROTECTION_EXPLOSIONS")) {
						if(ench_name.contains("Protection")
								|| ench_name.contains("Fire Protection")
								|| ench_name.contains("Projectile Protection")) {
							break;
						}
					}
					if(ench.getName().equals("PROTECTION_FIRE") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("PROTECTION_FIRE")) {
						if(ench_name.contains("Protection")
								|| ench_name.contains("Blast Protection")
								|| ench_name.contains("Projectile Protection")) {
							break;
						}
					}
					if(ench.getName().equals("WATER_WORKER") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 1) {
						break;
					}
					if(ench.getName().equals("OXYGEN") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 3) {
						break;
					}
					if(ench.getName().equals("THORNS") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 3) {
						break;
					}
					if(ench.getName().equals("Growth") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("DEPTH_STRIDER") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 3) {
						break;
					}
					if(ench.getName().equals("PROTECTION_FALL") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("DIG_SPEED") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(total_ench_level == 0) {
						break;
					}
					if(!enchs_to_add.containsKey(ench)) {
						enchs_to_add.put(ench, 1);
						total_ench_level--;
					} else {
						enchs_to_add.put(ench, enchs_to_add.get(ench)+1);
						total_ench_level--;
					}
					if(ench.getName().equals("Cubism")) {
						ench_name.add("Cubism");
					}
					if(ench.getName().equals("Telekinesis")) {
						ench_name.add("Telekinesis");
					}
					if(ench.getName().equals("Aiming")) {
						ench_name.add("Aiming");
					}
					if(ench.getName().equals("Snipe")) {
						ench_name.add("Snipe");
					}
					if(ench.getName().equals("ARROW_DAMAGE")) {
						ench_name.add("Power");
					}
					if(ench.getName().equals("ARROW_KNOCKBACK")) {
						ench_name.add("Punch");
					}
					if(ench.getName().equals("ARROW_INFINITE")) {
						ench_name.add("Infinity");
					}
					if(ench.getName().equals("ARROW_FIRE")) {
						ench_name.add("Flame");
					}
					if(ench.getName().equals("Ender Slayer")) {
						ench_name.add("Ender Slayer");
					}
					if(ench.getName().equals("Critical")) {
						ench_name.add("Critical");
					}
					if(ench.getName().equals("Life Steal")) {
						ench_name.add("Life Steal");
					}
					if(ench.getName().equals("First Strike")) {
						ench_name.add("First Strike");
					}
					if(ench.getName().equals("DAMAGE_ALL") && !ench_name.contains("Bane of Arthropods") && !ench_name.contains("Smite")) {
						ench_name.add("Sharpness");
					}
					if(ench.getName().equals("DAMAGE_ARTHROPODS") && !ench_name.contains("Smite") && !ench_name.contains("Sharpness")) {
						ench_name.add("Bane of Arthropods");
					}
					if(ench.getName().equals("DAMAGE_UNDEAD") && !ench_name.contains("Bane of Arthropods") && !ench_name.contains("Sharpness")) {
						ench_name.add("Smite");
					}
					if(ench.getName().equals("FIRE_ASPECT")) {
						ench_name.add("Fire Aspect");
					}
					if(ench.getName().equals("KNOCKBACK")) {
						ench_name.add("Knockback");
					}
					if(ench.getName().equals("LOOT_BONUS_MOBS")) {
						ench_name.add("Looting");
					}
					if(ench.getName().equals("PROTECTION_ENVIRONMENTAL")) {
						ench_name.add("Protection");
					}
					if(ench.getName().equals("PROTECTION_EXPLOSIONS")) {
						ench_name.add("Blast Protection");
					}
					if(ench.getName().equals("PROTECTION_PROJECTILE")) {
						ench_name.add("Projectile Protection");
					}
					if(ench.getName().equals("PROTECTION_FIRE")) {
						ench_name.add("Fire Protection");
					}
					if(ench.getName().equals("WATER_WORKER")) {
						ench_name.add("Aqua Affinity");
					}
					if(ench.getName().equals("OXYGEN")) {
						ench_name.add("Respiration");
					}
					if(ench.getName().equals("THORNS")) {
						ench_name.add("Thorns");
					}
					if(ench.getName().equals("Growth")) {
						ench_name.add("Growth");
					}
					if(ench.getName().equals("DEPTH_STRIDER")) {
						ench_name.add("Depth Strider");
					}
					if(ench.getName().equals("PROTECTION_FALL")) {
						ench_name.add("Feather Falling");
					}
					if(ench.getName().equals("DIG_SPEED")) {
						ench_name.add("Efficiency");
					}
					if(ench.getName().equals("SILK_TOUCH")) {
						ench_name.add("Silk Touch");
					}
					if(ench.getName().equals("LOOT_BONUS_BLOCKS")) {
						ench_name.add("Fortune");
					}
				}
			}
		}
		if(ench_name.size() > 1) {
			int random = rnd.nextInt(ench_name.size()-1);
			if(ench_name.get(random) == "Cubism") {
				lore2.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_cubism)));
			}
			if(ench_name.get(random) == "Telekinesis") {
				lore2.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_telekinesis)));
			}
			if(ench_name.get(random) == "Aiming") {
				lore2.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_aiming)));
			}
			if(ench_name.get(random) == "Snipe") {
				lore2.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_snipe)));
			}
			if(ench_name.get(random) == "Power") {
				lore2.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.ARROW_DAMAGE)));
			}
			if(ench_name.get(random) == "Punch") {
				lore1.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.ARROW_KNOCKBACK)));
			}
			if(ench_name.get(random) == "Infinity") {
				lore2.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.ARROW_INFINITE)));
			}
			if(ench_name.get(random) == "Flame") {
				lore2.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.ARROW_FIRE)));
			}
			if(ench_name.get(random) == "Ender Slayer") {
				lore2.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_enderslayer)));
			}
			if(ench_name.get(random) == "Critical") {
				lore2.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_critical)));
			}
			if(ench_name.get(random) == "Telekinesis") {
				lore2.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_telekinesis)));
			}
			if(ench_name.get(random) == "Life Steal") {
				lore2.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_lifesteal)));
			}
			if(ench_name.get(random) == "First Strike") {
				lore2.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_firstStrike)));
			}
			if(ench_name.get(random) == "Sharpness") {
				lore2.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.DAMAGE_ALL)));
			}
			if(ench_name.get(random) == "Looting") {
				lore2.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.LOOT_BONUS_MOBS)));
			}
			if(ench_name.get(random) == "Bane of Arthropods") {
				lore2.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.DAMAGE_ARTHROPODS)));
			}
			if(ench_name.get(random) == "Smite") {
				lore2.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.DAMAGE_UNDEAD)));
			}
			if(ench_name.get(random) == "Fire Aspect") {
				lore2.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.FIRE_ASPECT)));
			}
			if(ench_name.get(random) == "Knockback") {
				lore2.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.KNOCKBACK)));
			}
			if(ench_name.get(random) == "Protection") {
				lore2.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.protection.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.PROTECTION_ENVIRONMENTAL)));
			}
			if(ench_name.get(random) == "Blast Protection") {
				lore2.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.blast_protection.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.PROTECTION_EXPLOSIONS)));
			}
			if(ench_name.get(random) == "Projectile Protection") {
				lore2.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.projectile_protection.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.PROTECTION_PROJECTILE)));
			}
			if(ench_name.get(random) == "Fire Protection") {
				lore2.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fire_protection.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.PROTECTION_FIRE)));
			}
			if(ench_name.get(random) == "Aqua Affinity") {
				lore2.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.aqua_affinity.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.WATER_WORKER)));
			}
			if(ench_name.get(random) == "Respiration") {
				lore2.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.respiration.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.OXYGEN)));
			}
			if(ench_name.get(random) == "Thorns") {
				lore2.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.thorns.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.THORNS)));
			}
			if(ench_name.get(random) == "Growth") {
				lore2.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.growth.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_growth)));
			}
			if(ench_name.get(random) == "Depth Strider") {
				lore2.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.depth_strider.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.DEPTH_STRIDER)));
			}
			if(ench_name.get(random) == "Feather Falling") {
				lore2.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.feather_falling.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.PROTECTION_FALL)));
			}
			if(ench_name.get(random) == "Efficiency") {
				lore2.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.efficiency.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.DIG_SPEED)));
			}
			if(ench_name.get(random) == "Silk Touch") {
				lore2.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.silk_touch.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.SILK_TOUCH)));
			}
			if(ench_name.get(random) == "Fortune") {
				lore2.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fortune.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.LOOT_BONUS_BLOCKS)));
			}
		}
				
		pS.setEnchOpt2(enchs_to_add);
		ench_name.clear();
		lore2.add(" ");
		if(ench2.getAmount() > p.getLevel()) {
			lore2.add("§cNot enough levels!");
		} else {
			lore2.add("§eClick to enchant!");
		}
		ench2meta.setLore(lore2);
		ench2meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		ench2.setItemMeta(ench2meta);
		ench2.addUnsafeEnchantments(enchs_to_add);
		enchs_to_add.clear();
		ench2 = addDoNotMoveTag(ench2);
		e.getInventory().setItem(31, ench2);
		
		
		
		ItemStack ench3 = new ItemStack(Material.EXP_BOTTLE, rnd.nextInt(9)+48);
		ItemMeta ench3meta = ench3.getItemMeta();
		ench3meta.setDisplayName("§aEnchant Item §8- §6"+ench3.getAmount()+" Levels");
		ArrayList<String> lore3 = new ArrayList<String>();
		lore3.add("§7Guarantees at least:");
		total_ench_level = 12;
		Collections.shuffle(possibleEnchantments);
		for(Enchantment ench : possibleEnchantments) {
			if(total_ench_level == 0) {
				break;
			}
			if(ench == null) continue;
			for(int i = 0; i < ench.getMaxLevel()-1; i++) {
				if(total_ench_level == 0) {
					break;
				}
				if((rnd.nextInt(100)+1) <= ench3.getAmount()*2) {
					if(ench.getName().equals("Cubism") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("Telekinesis") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 1) {
						break;
					}
					if(ench.getName().equals("Aiming") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("Snipe") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 3) {
						break;
					}
					if(ench.getName().equals("ARROW_DAMAGE") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("ARROW_KNOCKBACK") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 2) {
						break;
					}
					if(ench.getName().equals("ARROW_INFINITE") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 1) {
						break;
					}
					if(ench.getName().equals("ARROW_FIRE") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 1) {
						break;
					}
					if(ench.getName().equals("Ender Slayer") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("Critical") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("Telekinesis") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 1) {
						break;
					}
					if(ench.getName().equals("Life Steal") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 3) {
						break;
					}
					if(ench.getName().equals("First Strike") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 4) {
						break;
					}
					if(ench.getName().equals("DAMAGE_ALL") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("DAMAGE_ALL")) {
						if(ench_name.contains("Smite") || ench_name.contains("Bane of Arthropods")) {
							break;
						}
					}
					if(ench.getName().equals("DAMAGE_ARTHROPODS") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("DAMAGE_ARTHROPODS")) {
						if(ench_name.contains("Smite") || ench_name.contains("Sharpness")) {
							break;
						}
					}
					if(ench.getName().equals("DAMAGE_UNDEAD") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("DAMAGE_UNDEAD")) {
						if(ench_name.contains("Sharpness") || ench_name.contains("Bane of Arthropods")) {
							break;
						}
					}
					if(ench.getName().equals("FIRE_ASPECT") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 2) {
						break;
					}
					if(ench.getName().equals("KNOCKBACK") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 2) {
						break;
					}
					if(ench.getName().equals("LOOT_BONUS_MOBS") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 3) {
						break;
					}
					if(ench.getName().equals("PROTECTION_ENVIRONMENTAL") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("PROTECTION_ENVIRONMENTAL")) {
						if(ench_name.contains("Projectile Protection")
								|| ench_name.contains("Fire Protection")
								|| ench_name.contains("Blast Protection")) {
							break;
						}
					}
					if(ench.getName().equals("PROTECTION_PROJECTILE") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("PROTECTION_PROJECTILE")) {
						if(ench_name.contains("Protection")
								|| ench_name.contains("Fire Protection")
								|| ench_name.contains("Blast Protection")) {
							break;
						}
					}
					if(ench.getName().equals("PROTECTION_EXPLOSIONS") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("PROTECTION_EXPLOSIONS")) {
						if(ench_name.contains("Protection")
								|| ench_name.contains("Fire Protection")
								|| ench_name.contains("Projectile Protection")) {
							break;
						}
					}
					if(ench.getName().equals("PROTECTION_FIRE") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("PROTECTION_FIRE")) {
						if(ench_name.contains("Protection")
								|| ench_name.contains("Blast Protection")
								|| ench_name.contains("Projectile Protection")) {
							break;
						}
					}
					if(ench.getName().equals("WATER_WORKER") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 1) {
						break;
					}
					if(ench.getName().equals("OXYGEN") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 3) {
						break;
					}
					if(ench.getName().equals("THORNS") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 3) {
						break;
					}
					if(ench.getName().equals("Growth") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("DEPTH_STRIDER") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 3) {
						break;
					}
					if(ench.getName().equals("PROTECTION_FALL") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("DIG_SPEED") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 5) {
						break;
					}
					if(ench.getName().equals("SILK_TOUCH") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 1) {
						break;
					}
					if(ench.getName().equals("LOOT_BONUS_BLOCKS") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 3) {
						break;
					}
					if(ench.getName().equals("SILK_TOUCH") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 1) {
						break;
					}
					if(ench.getName().equals("LOOT_BONUS_BLOCKS") && enchs_to_add.containsKey(ench) && enchs_to_add.get(ench) == 3) {
						break;
					}
					if(ench.getName().equals("SILK_TOUCH")) {
						if(ench_name.contains("Fortune")) {
							break;
						}
					}
					if(ench.getName().equals("LOOT_BONUS_BLOCKS")) {
						if(ench_name.contains("Silk Touch")) {
							break;
						}
					}
					if(total_ench_level == 0) {
						break;
					}
					if(!enchs_to_add.containsKey(ench)) {
						enchs_to_add.put(ench, 1);
						total_ench_level--;
					} else {
						enchs_to_add.put(ench, enchs_to_add.get(ench)+1);
						total_ench_level--;
					}
					if(ench.getName().equals("Cubism")) {
						ench_name.add("Cubism");
					}
					if(ench.getName().equals("Telekinesis")) {
						ench_name.add("Telekinesis");
					}
					if(ench.getName().equals("Aiming")) {
						ench_name.add("Aiming");
					}
					if(ench.getName().equals("Snipe")) {
						ench_name.add("Snipe");
					}
					if(ench.getName().equals("ARROW_DAMAGE")) {
						ench_name.add("Power");
					}
					if(ench.getName().equals("ARROW_KNOCKBACK")) {
						ench_name.add("Punch");
					}
					if(ench.getName().equals("ARROW_INFINITE")) {
						ench_name.add("Infinity");
					}
					if(ench.getName().equals("ARROW_FIRE")) {
						ench_name.add("Flame");
					}
					if(ench.getName().equals("Ender Slayer")) {
						ench_name.add("Ender Slayer");
					}
					if(ench.getName().equals("Critical")) {
						ench_name.add("Critical");
					}
					if(ench.getName().equals("Life Steal")) {
						ench_name.add("Life Steal");
					}
					if(ench.getName().equals("First Strike")) {
						ench_name.add("First Strike");
					}
					if(ench.getName().equals("DAMAGE_ALL") && !ench_name.contains("Bane of Arthropods") && !ench_name.contains("Smite")) {
						ench_name.add("Sharpness");
					}
					if(ench.getName().equals("DAMAGE_ARTHROPODS") && !ench_name.contains("Smite") && !ench_name.contains("Sharpness")) {
						ench_name.add("Bane of Arthropods");
					}
					if(ench.getName().equals("DAMAGE_UNDEAD") && !ench_name.contains("Bane of Arthropods") && !ench_name.contains("Sharpness")) {
						ench_name.add("Smite");
					}
					if(ench.getName().equals("FIRE_ASPECT")) {
						ench_name.add("Fire Aspect");
					}
					if(ench.getName().equals("KNOCKBACK")) {
						ench_name.add("Knockback");
					}
					if(ench.getName().equals("LOOT_BONUS_MOBS")) {
						ench_name.add("Looting");
					}
					if(ench.getName().equals("PROTECTION_ENVIRONMENTAL")) {
						ench_name.add("Protection");
					}
					if(ench.getName().equals("PROTECTION_EXPLOSIONS")) {
						ench_name.add("Blast Protection");
					}
					if(ench.getName().equals("PROTECTION_PROJECTILE")) {
						ench_name.add("Projectile Protection");
					}
					if(ench.getName().equals("PROTECTION_FIRE")) {
						ench_name.add("Fire Protection");
					}
					if(ench.getName().equals("WATER_WORKER")) {
						ench_name.add("Aqua Affinity");
					}
					if(ench.getName().equals("OXYGEN")) {
						ench_name.add("Respiration");
					}
					if(ench.getName().equals("THORNS")) {
						ench_name.add("Thorns");
					}
					if(ench.getName().equals("Growth")) {
						ench_name.add("Growth");
					}
					if(ench.getName().equals("DEPTH_STRIDER")) {
						ench_name.add("Depth Strider");
					}
					if(ench.getName().equals("PROTECTION_FALL")) {
						ench_name.add("Feather Falling");
					}
					if(ench.getName().equals("DIG_SPEED")) {
						ench_name.add("Efficiency");
					}
					if(ench.getName().equals("SILK_TOUCH")) {
						ench_name.add("Silk Touch");
					}
					if(ench.getName().equals("LOOT_BONUS_BLOCKS")) {
						ench_name.add("Fortune");
					}
				}
			}
		}
		if(ench_name.size() > 1) {
			int random = rnd.nextInt(ench_name.size()-1);
			if(ench_name.get(random) == "Cubism") {
				lore3.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_cubism)));
			}
			if(ench_name.get(random) == "Telekinesis") {
				lore3.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_telekinesis)));
			}
			if(ench_name.get(random) == "Aiming") {
				lore3.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_aiming)));
			}
			if(ench_name.get(random) == "Snipe") {
				lore3.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_snipe)));
			}
			if(ench_name.get(random) == "Power") {
				lore3.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.ARROW_DAMAGE)));
			}
			if(ench_name.get(random) == "Punch") {
				lore3.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.ARROW_KNOCKBACK)));
			}
			if(ench_name.get(random) == "Infinity") {
				lore3.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.ARROW_INFINITE)));
			}
			if(ench_name.get(random) == "Flame") {
				lore3.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.ARROW_FIRE)));
			}
			if(ench_name.get(random) == "Ender Slayer") {
				lore3.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_enderslayer)));
			}
			if(ench_name.get(random) == "Critical") {
				lore3.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_critical)));
			}
			if(ench_name.get(random) == "Telekinesis") {
				lore3.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_telekinesis)));
			}
			if(ench_name.get(random) == "Life Steal") {
				lore3.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_lifesteal)));
			}
			if(ench_name.get(random) == "First Strike") {
				lore3.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_firstStrike)));
			}
			if(ench_name.get(random) == "Sharpness") {
				lore3.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.DAMAGE_ALL)));
			}
			if(ench_name.get(random) == "Looting") {
				lore3.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.LOOT_BONUS_MOBS)));
			}
			if(ench_name.get(random) == "Bane of Arthropods") {
				lore3.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.DAMAGE_ARTHROPODS)));
			}
			if(ench_name.get(random) == "Smite") {
				lore3.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.DAMAGE_UNDEAD)));
			}
			if(ench_name.get(random) == "Fire Aspect") {
				lore3.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.FIRE_ASPECT)));
			}
			if(ench_name.get(random) == "Knockback") {
				lore3.add("§7 * "+ench_name.get(random)+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.KNOCKBACK)));
			}
			if(ench_name.get(random) == "Protection") {
				lore3.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.protection.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.PROTECTION_ENVIRONMENTAL)));
			}
			if(ench_name.get(random) == "Blast Protection") {
				lore3.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.blast_protection.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.PROTECTION_EXPLOSIONS)));
			}
			if(ench_name.get(random) == "Projectile Protection") {
				lore3.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.projectile_protection.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.PROTECTION_PROJECTILE)));
			}
			if(ench_name.get(random) == "Fire Protection") {
				lore3.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fire_protection.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.PROTECTION_FIRE)));
			}
			if(ench_name.get(random) == "Aqua Affinity") {
				lore3.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.aqua_affinity.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.WATER_WORKER)));
			}
			if(ench_name.get(random) == "Respiration") {
				lore3.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.respiration.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.OXYGEN)));
			}
			if(ench_name.get(random) == "Thorns") {
				lore3.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.thorns.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.THORNS)));
			}
			if(ench_name.get(random) == "Growth") {
				lore3.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.growth.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(SpecialItems.ench_growth)));
			}
			if(ench_name.get(random) == "Depth Strider") {
				lore3.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.depth_strider.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.DEPTH_STRIDER)));
			}
			if(ench_name.get(random) == "Feather Falling") {
				lore3.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.feather_falling.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.PROTECTION_FALL)));
			}
			if(ench_name.get(random) == "Efficiency") {
				lore3.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.efficiency.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.DIG_SPEED)));
			}
			if(ench_name.get(random) == "Silk Touch") {
				lore3.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.silk_touch.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.SILK_TOUCH)));
			}
			if(ench_name.get(random) == "Fortune") {
				lore3.add("§7 * "+ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("enchantments.fortune.ench_table_name"))+" "+Enchanting.translateEnchLevel(enchs_to_add.get(Enchantment.LOOT_BONUS_BLOCKS)));
			}
		}

		ench_name.clear();
		pS.setEnchOpt3(enchs_to_add);
		lore3.add(" ");
		if(ench3.getAmount() > p.getLevel()) {
			lore3.add("§cNot enough levels!");
		} else {
			lore3.add("§eClick to enchant!");
		}
		ench3meta.setLore(lore3);
		ench3meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		ench3.setItemMeta(ench3meta);
		ench3.addUnsafeEnchantments(enchs_to_add);
		enchs_to_add.clear();
		ench3 = addDoNotMoveTag(ench3);
		e.getInventory().setItem(33, ench3);
	}
	
	
	private void prepareEnchantments(InventoryClickEvent e) {

		if(e.getInventory().getItem(13) != null && e.getInventory().getItem(13).getItemMeta().hasEnchants()) {
			ItemStack clay = new ItemStack(Material.INK_SACK,1,  (byte) 8);
			ItemMeta im = clay.getItemMeta();
			im.setDisplayName("§cAlready Enchanted!");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("§7This item has already been");
			lore.add("§7enchanted! Use an Anvil to add");
			lore.add("§7more enchantments to it!");
			im.setLore(lore);
			clay.setItemMeta(im);
			clay = addDoNotMoveTag(clay);
			e.getInventory().setItem(29, clay);
			e.getInventory().setItem(31, clay);
			e.getInventory().setItem(33, clay);
			return;
		}
		if(e.getInventory().getItem(13) != null) {
			if(e.getInventory().getItem(13).getType().equals(Material.DIAMOND_SWORD)
				|| e.getInventory().getItem(13).getType().equals(Material.GOLD_SWORD)
				|| e.getInventory().getItem(13).getType().equals(Material.IRON_SWORD)
				|| e.getInventory().getItem(13).getType().equals(Material.STONE_SWORD)
				|| e.getInventory().getItem(13).getType().equals(Material.WOOD_SWORD)) {
				setEnchantmentBottles(sword_enchs, e);
				return;
			}
		
			if(e.getInventory().getItem(13).getType().equals(Material.BOW)) {
				setEnchantmentBottles(bow_enchs, e);
				return;
			}
			if(e.getInventory().getItem(13).getType().equals(Material.CHAINMAIL_HELMET)
				|| e.getInventory().getItem(13).getType().equals(Material.GOLD_HELMET)
				|| e.getInventory().getItem(13).getType().equals(Material.DIAMOND_HELMET)
				|| e.getInventory().getItem(13).getType().equals(Material.IRON_HELMET)
				|| e.getInventory().getItem(13).getType().equals(Material.LEATHER_HELMET)) {
				setEnchantmentBottles(helmet_enchs, e);
				return;
			}
		
			if(e.getInventory().getItem(13).getType().equals(Material.CHAINMAIL_CHESTPLATE)
				|| e.getInventory().getItem(13).getType().equals(Material.GOLD_CHESTPLATE)
				|| e.getInventory().getItem(13).getType().equals(Material.DIAMOND_CHESTPLATE)
				|| e.getInventory().getItem(13).getType().equals(Material.IRON_CHESTPLATE)
				|| e.getInventory().getItem(13).getType().equals(Material.LEATHER_CHESTPLATE)
				|| e.getInventory().getItem(13).getType().equals(Material.CHAINMAIL_LEGGINGS)) {
				setEnchantmentBottles(chestplate_enchs, e);
				return;
			}
		
			if(e.getInventory().getItem(13).getType().equals(Material.CHAINMAIL_LEGGINGS)
				|| e.getInventory().getItem(13).getType().equals(Material.GOLD_LEGGINGS)
				|| e.getInventory().getItem(13).getType().equals(Material.DIAMOND_LEGGINGS)
				|| e.getInventory().getItem(13).getType().equals(Material.IRON_LEGGINGS)
				|| e.getInventory().getItem(13).getType().equals(Material.LEATHER_LEGGINGS)) {
				setEnchantmentBottles(leggings_enchs, e);
				return;
			}
		
			if(e.getInventory().getItem(13).getType().equals(Material.CHAINMAIL_BOOTS)
				|| e.getInventory().getItem(13).getType().equals(Material.GOLD_BOOTS)
				|| e.getInventory().getItem(13).getType().equals(Material.DIAMOND_BOOTS)
				|| e.getInventory().getItem(13).getType().equals(Material.IRON_BOOTS)
				|| e.getInventory().getItem(13).getType().equals(Material.LEATHER_BOOTS)) {
				setEnchantmentBottles(boots_enchs, e);
				return;
			}
		
			if(e.getInventory().getItem(13).getType().equals(Material.DIAMOND_PICKAXE)
				|| e.getInventory().getItem(13).getType().equals(Material.GOLD_PICKAXE)
				|| e.getInventory().getItem(13).getType().equals(Material.IRON_PICKAXE)
				|| e.getInventory().getItem(13).getType().equals(Material.STONE_PICKAXE)
				|| e.getInventory().getItem(13).getType().equals(Material.WOOD_PICKAXE)) {
				setEnchantmentBottles(pickaxe_enchs, e);
				return;
			}
		
			if(e.getInventory().getItem(13).getType().equals(Material.DIAMOND_AXE)
				|| e.getInventory().getItem(13).getType().equals(Material.GOLD_AXE)
				|| e.getInventory().getItem(13).getType().equals(Material.IRON_AXE)
				|| e.getInventory().getItem(13).getType().equals(Material.STONE_AXE)
				|| e.getInventory().getItem(13).getType().equals(Material.WOOD_AXE)) {
				setEnchantmentBottles(axe_enchs, e);
				return;
			}
		
			if(e.getInventory().getItem(13).getType().equals(Material.DIAMOND_SPADE)
				|| e.getInventory().getItem(13).getType().equals(Material.GOLD_SPADE)
				|| e.getInventory().getItem(13).getType().equals(Material.IRON_SPADE)
				|| e.getInventory().getItem(13).getType().equals(Material.STONE_SPADE)
				|| e.getInventory().getItem(13).getType().equals(Material.WOOD_SPADE)) {
				setEnchantmentBottles(shovel_enchs, e);
				return;
			}
		
			if(e.getInventory().getItem(13).getType().equals(Material.DIAMOND_HOE)
				|| e.getInventory().getItem(13).getType().equals(Material.GOLD_HOE)
				|| e.getInventory().getItem(13).getType().equals(Material.IRON_HOE)
				|| e.getInventory().getItem(13).getType().equals(Material.STONE_HOE)
				|| e.getInventory().getItem(13).getType().equals(Material.WOOD_HOE)) {
				setEnchantmentBottles(hoe_enchs, e);
				return;
			}
		
			if(e.getInventory().getItem(13).getType().equals(Material.BOOK) && e.getInventory().getItem(13).getAmount() == 1) {
				setEnchantmentBottles(book_enchs, e);
				return;
			}
		}
		ItemStack clay = new ItemStack(Material.INK_SACK,1,  (byte) 8);
		ItemMeta im = clay.getItemMeta();
		im.setDisplayName("§cNot enchantable!");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7This item cannot be enchanted!");
		im.setLore(lore);
		clay.setItemMeta(im);
		clay = addDoNotMoveTag(clay);
		e.getInventory().setItem(29, clay);
		e.getInventory().setItem(31, clay);
		e.getInventory().setItem(33, clay);
	}
}
