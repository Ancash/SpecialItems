package de.ancash.specialitems.anvil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import de.ancash.specialitems.SpecialItems;
import de.ancash.specialitems.listener.Enchanting;
import de.tr7zw.nbtapi.NBTItem;


@SuppressWarnings("unused")
public class AnvilUse implements Listener {

	@EventHandler
	public void onInvClose(InventoryCloseEvent e) {
		if(e.getView().getTitle().equals("Anvil")) {
			if(e.getInventory().getItem(29) != null) {
				if(e.getPlayer().getInventory().firstEmpty() != -1) {
					e.getPlayer().getInventory().addItem(e.getInventory().getItem(29).clone());
					e.getInventory().setItem(29, null);
				} else {
					e.getPlayer().getLocation().getWorld().dropItem(e.getPlayer().getLocation(), e.getInventory().getItem(29).clone());
					e.getInventory().setItem(29, null);
				}
			}
			if(e.getInventory().getItem(33) != null) {
				if(e.getPlayer().getInventory().firstEmpty() != -1) {
					e.getPlayer().getInventory().addItem(e.getInventory().getItem(33).clone());
					e.getInventory().setItem(33, null);
				} else {
					e.getPlayer().getLocation().getWorld().dropItem(e.getPlayer().getLocation(), e.getInventory().getItem(33).clone());
					e.getInventory().setItem(33, null);
				}
			}
		}
	}
	
	
	@EventHandler
	public void onInvOpen(InventoryOpenEvent e) {
		
		if(e.getInventory().getType().equals(InventoryType.ANVIL)) {
			
			e.setCancelled(true);
			
			Inventory anvil = Bukkit.createInventory(null, 9*6, "Anvil");
			
			for(int i = 0; i<9*6; i++) {
				anvil.setItem(i, Anvil.getAnvInv().getItem(i));
			}
			
			e.getPlayer().openInventory(anvil);
		}	
	}
	
	private ItemStack addDoNotMoveTag(ItemStack is) {
		NBTItem nbt = new NBTItem(is);
		nbt.setString("donotmove", "lol");
		return nbt.getItem();
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInvInteract(InventoryClickEvent e) throws InterruptedException {
				
		ItemStack error = Anvil.error.clone();
		
		ItemStack anv = Anvil.anvil.clone();
		
		//Player p = (Player) e.getWhoClicked();
		if(e.getView().getTitle().equals("Anvil") && e.getInventory().getSize() >= 54) {
			
			if(e.getAction().equals(InventoryAction.NOTHING) 
					|| e.getAction().equals(InventoryAction.DROP_ALL_CURSOR)
					|| e.getAction().equals(InventoryAction.DROP_ALL_SLOT)
					|| e.getAction().equals(InventoryAction.DROP_ONE_CURSOR)
					|| e.getAction().equals(InventoryAction.DROP_ONE_SLOT)
					|| e.getAction().equals(InventoryAction.SWAP_WITH_CURSOR)) {
				e.setCancelled(true);
				return;
			}
			e.setCancelled(true);
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
			if(e.getAction().equals(InventoryAction.PICKUP_ALL) 
					|| e.getAction().equals(InventoryAction.PICKUP_ONE)
					|| e.getAction().equals(InventoryAction.PICKUP_HALF)
					|| e.getAction().equals(InventoryAction.PICKUP_SOME)) {
				if(e.getSlot() == 29|| e.getSlot() == 33) {
					e.setCancelled(false);
				}
			}
			
			if(e.getSlot() == 22 && e.getInventory().getItem(29) != null && e.getInventory().getItem(33) != null && !e.getInventory().getItem(13).getType().equals(Material.BARRIER)) {
				
				Player p = (Player) e.getWhoClicked();
				if(p.getLevel() < getXPCost(combineItems(e.getInventory().getItem(29).clone(), e.getInventory().getItem(33)), e.getInventory().getItem(33))) {
					p.playSound(p.getLocation(), Anvil.notenoughxp, 20, 0);
						
					
					return;
				}
				
				p.setLevel(p.getLevel()-getXPCost(combineItems(e.getInventory().getItem(29).clone(), e.getInventory().getItem(33)), e.getInventory().getItem(33)));
				p.playSound(e.getWhoClicked().getLocation(), Anvil.use, 1, 1);
				
				e.getInventory().setItem(29, null);
				e.getInventory().setItem(33, null);
				e.getInventory().setItem(22, anv);
				
			}
			if(e.getSlot() == 13 && !e.getInventory().getItem(13).getType().equals(Material.BARRIER) && ((e.getInventory().getItem(29) != null && e.getInventory().getItem(33) != null))) {
				e.setCancelled(true);
				Bukkit.getScheduler().runTaskLater(SpecialItems.plugin, new Runnable() {
					
					@Override
					public void run() {
						
						e.getInventory().setItem(13, error);
						e.getInventory().setItem(22, anv);
						
					}
				}, 0);
			}
			
			checkItems(e);
			return;
		}
	}
	
	private void checkItems(InventoryClickEvent e) {
		
		Bukkit.getScheduler().runTaskLater(SpecialItems.plugin, new Runnable() {
			
			@Override
			public void run() {

				ArrayList<Material> swords = new ArrayList<Material>();
				swords.add(Material.DIAMOND_SWORD);
				swords.add(Material.GOLD_SWORD);
				swords.add(Material.IRON_SWORD);
				swords.add(Material.STONE_SWORD);
				swords.add(Material.WOOD_SWORD);
				
				ArrayList<Material> armors = new ArrayList<Material>();
				armors.add(Material.CHAINMAIL_BOOTS);
				armors.add(Material.DIAMOND_BOOTS);
				armors.add(Material.GOLD_BOOTS);
				armors.add(Material.IRON_BOOTS);
				armors.add(Material.LEATHER_BOOTS);
				
				armors.add(Material.CHAINMAIL_LEGGINGS);
				armors.add(Material.DIAMOND_LEGGINGS);
				armors.add(Material.GOLD_LEGGINGS);
				armors.add(Material.IRON_LEGGINGS);
				armors.add(Material.LEATHER_LEGGINGS);
				
				armors.add(Material.CHAINMAIL_CHESTPLATE);
				armors.add(Material.DIAMOND_CHESTPLATE);
				armors.add(Material.GOLD_CHESTPLATE);
				armors.add(Material.IRON_CHESTPLATE);
				armors.add(Material.LEATHER_CHESTPLATE);
				
				armors.add(Material.CHAINMAIL_HELMET);
				armors.add(Material.DIAMOND_HELMET);
				armors.add(Material.GOLD_HELMET);
				armors.add(Material.IRON_HELMET);
				armors.add(Material.LEATHER_HELMET);
				
				ArrayList<Material> hoes = new ArrayList<Material>();
				hoes.add(Material.DIAMOND_HOE);
				hoes.add(Material.GOLD_HOE);
				hoes.add(Material.IRON_HOE);
				hoes.add(Material.STONE_HOE);
				hoes.add(Material.WOOD_HOE);
				
				ArrayList<Material> pickaxes = new ArrayList<Material>();
				pickaxes.add(Material.DIAMOND_PICKAXE);
				pickaxes.add(Material.GOLD_PICKAXE);
				pickaxes.add(Material.IRON_PICKAXE);
				pickaxes.add(Material.STONE_PICKAXE);
				pickaxes.add(Material.WOOD_PICKAXE);
				
				ArrayList<Material> axes = new ArrayList<Material>();
				axes.add(Material.DIAMOND_AXE);
				axes.add(Material.GOLD_AXE);
				axes.add(Material.IRON_AXE);
				axes.add(Material.STONE_AXE);
				axes.add(Material.WOOD_AXE);
				
				ArrayList<Material> shovels = new ArrayList<Material>();
				shovels.add(Material.DIAMOND_SPADE);
				shovels.add(Material.GOLD_SPADE);
				shovels.add(Material.IRON_SPADE);
				shovels.add(Material.STONE_SPADE);
				shovels.add(Material.WOOD_SPADE);
				
				ItemStack sacgreen = Anvil.sacgreen.clone();
				
				ItemStack sacred = Anvil.red.clone();
				
				ItemStack upgreen = Anvil.upgreen.clone();
				
				ItemStack upred = Anvil.upred.clone();
				
				ItemStack anv = Anvil.anvil.clone();
				
				ItemStack error = Anvil.error.clone();
				
				ItemStack left = e.getInventory().getItem(29);
				ItemStack right = e.getInventory().getItem(33);
				
				if(e.getAction().equals(InventoryAction.PICKUP_ALL)
						|| e.getAction().equals(InventoryAction.PICKUP_HALF)
						|| e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
					if(e.getSlot() == 29) {
						e.getInventory().setItem(11, upred);
						e.getInventory().setItem(12, upred);
						e.getInventory().setItem(20, upred);
					}
					if(e.getSlot() == 33) {
						e.getInventory().setItem(14, sacred);
						e.getInventory().setItem(15, sacred);
						e.getInventory().setItem(24, sacred);
					}
				}
				
				if(right == null && left != null) {
					e.getInventory().setItem(11, upgreen);
					e.getInventory().setItem(12, upgreen);
					e.getInventory().setItem(20, upgreen);
					e.getInventory().setItem(14, sacred);
					e.getInventory().setItem(15, sacred);
					e.getInventory().setItem(24, sacred);
					e.getInventory().setItem(13, error);
					return;
				}
				if(right != null && left == null) {
					e.getInventory().setItem(11, upred);
					e.getInventory().setItem(12, upred);
					e.getInventory().setItem(20, upred);
					e.getInventory().setItem(14, sacgreen);
					e.getInventory().setItem(15, sacgreen);
					e.getInventory().setItem(24, sacgreen);
					e.getInventory().setItem(13, error);
					return;
				}
				
				if(right == null && left == null) {
					e.getInventory().setItem(11, upred);
					e.getInventory().setItem(12, upred);
					e.getInventory().setItem(20, upred);
					e.getInventory().setItem(14, sacred);
					e.getInventory().setItem(15, sacred);
					e.getInventory().setItem(24, sacred);
					return;
				}
				if((!right.hasItemMeta() && !left.hasItemMeta()) ||
						(!right.getItemMeta().hasEnchants() && left.getItemMeta().hasEnchants() && !right.getType().equals(Material.ENCHANTED_BOOK) && !left.getType().equals(Material.ENCHANTED_BOOK))) {
					e.getInventory().setItem(11, upred);
					e.getInventory().setItem(12, upred);
					e.getInventory().setItem(20, upred);
					e.getInventory().setItem(14, sacred);
					e.getInventory().setItem(15, sacred);
					e.getInventory().setItem(24, sacred);
					e.getInventory().setItem(13, error);
					return;
				}
				if(left.getType().equals(Material.ENCHANTED_BOOK) && !right.getType().equals(Material.ENCHANTED_BOOK) && left != null && right != null) {
					e.getInventory().setItem(11, upred);
					e.getInventory().setItem(12, upred);
					e.getInventory().setItem(20, upred);
					e.getInventory().setItem(14, sacred);
					e.getInventory().setItem(15, sacred);
					e.getInventory().setItem(24, sacred);
					e.getInventory().setItem(13, error);
					return;
				}
				if(!left.getType().equals(Material.ENCHANTED_BOOK) && right.getType().equals(Material.ENCHANTED_BOOK)) {
					
					if(swords.contains(left.getType())) {
						e.getInventory().setItem(11, upgreen);
						e.getInventory().setItem(12, upgreen);
						e.getInventory().setItem(20, upgreen);
						e.getInventory().setItem(14, sacgreen);
						e.getInventory().setItem(15, sacgreen);
						e.getInventory().setItem(24, sacgreen);
						e.getInventory().setItem(13, addAnvilUse(combineItems(left.clone(), right)));
						e.getInventory().setItem(22, xpCost(left, right));
						return;
					}
					if(armors.contains(left.getType())) {
						e.getInventory().setItem(11, upgreen);
						e.getInventory().setItem(12, upgreen);
						e.getInventory().setItem(20, upgreen);
						e.getInventory().setItem(14, sacgreen);
						e.getInventory().setItem(15, sacgreen);
						e.getInventory().setItem(24, sacgreen);
						e.getInventory().setItem(13, addAnvilUse(combineItems(left.clone(), right)));
						e.getInventory().setItem(22, xpCost(left, right));
						return;
					}
					if(shovels.contains(left.getType())) {
						e.getInventory().setItem(11, upgreen);
						e.getInventory().setItem(12, upgreen);
						e.getInventory().setItem(20, upgreen);
						e.getInventory().setItem(14, sacgreen);
						e.getInventory().setItem(15, sacgreen);
						e.getInventory().setItem(24, sacgreen);
						e.getInventory().setItem(13, addAnvilUse(combineItems(left.clone(), right)));
						e.getInventory().setItem(22, xpCost(left, right));
						return;
					}
					if(axes.contains(left.getType())) {
						e.getInventory().setItem(11, upgreen);
						e.getInventory().setItem(12, upgreen);
						e.getInventory().setItem(20, upgreen);
						e.getInventory().setItem(14, sacgreen);
						e.getInventory().setItem(15, sacgreen);
						e.getInventory().setItem(24, sacgreen);
						e.getInventory().setItem(13, addAnvilUse(combineItems(left.clone(), right)));
						e.getInventory().setItem(22, xpCost(left, right));
						return;
					}
					if(pickaxes.contains(left.getType())) {
						e.getInventory().setItem(11, upgreen);
						e.getInventory().setItem(12, upgreen);
						e.getInventory().setItem(20, upgreen);
						e.getInventory().setItem(14, sacgreen);
						e.getInventory().setItem(15, sacgreen);
						e.getInventory().setItem(24, sacgreen);
						e.getInventory().setItem(13, addAnvilUse(combineItems(left.clone(), right)));
						e.getInventory().setItem(22, xpCost(left, right));
						return;
					}
					if(hoes.contains(left.getType())) {
						e.getInventory().setItem(11, upgreen);
						e.getInventory().setItem(12, upgreen);
						e.getInventory().setItem(20, upgreen);
						e.getInventory().setItem(14, sacgreen);
						e.getInventory().setItem(15, sacgreen);
						e.getInventory().setItem(24, sacgreen);
						e.getInventory().setItem(13, addAnvilUse(combineItems(left.clone(), right)));
						e.getInventory().setItem(22, xpCost(left, right));
						return;
					}
					if(left.getType().equals(Material.BOW) ) {
						e.getInventory().setItem(11, upgreen);
						e.getInventory().setItem(12, upgreen);
						e.getInventory().setItem(20, upgreen);
						e.getInventory().setItem(14, sacgreen);
						e.getInventory().setItem(15, sacgreen);
						e.getInventory().setItem(24, sacgreen);
						e.getInventory().setItem(13, addAnvilUse(combineItems(left.clone(), right)));
						e.getInventory().setItem(22, xpCost(left, right));
						return;
					}
					
					e.getInventory().setItem(13, error);
					return;
				}
				if(left.getType().equals(right.getType())) {
					
					if(swords.contains(left.getType()) 
							|| armors.contains(left.getType())
							|| left.getType().equals(Material.BOW) 
							|| hoes.contains(left.getType()) 
							|| pickaxes.contains(left.getType())
							|| axes.contains(left.getType()) || 
							shovels.contains(left.getType())) {
						e.getInventory().setItem(11, upgreen);
						e.getInventory().setItem(12, upgreen);
						e.getInventory().setItem(20, upgreen);
						e.getInventory().setItem(14, sacgreen);
						e.getInventory().setItem(15, sacgreen);
						e.getInventory().setItem(24, sacgreen);
						e.getInventory().setItem(13, addAnvilUse(combineItems(left.clone(), right)));
						e.getInventory().setItem(22, xpCost(left, right));
						return;
					}
					
					if(left.getType().equals(Material.ENCHANTED_BOOK) || right.getType().equals(Material.ENCHANTED_BOOK)) {
						if(left.getType().equals(Material.ENCHANTED_BOOK) && right.getType().equals(Material.ENCHANTED_BOOK)) {
							e.getInventory().setItem(11, upgreen);
							e.getInventory().setItem(12, upgreen);
							e.getInventory().setItem(20, upgreen);
							e.getInventory().setItem(14, sacgreen);
							e.getInventory().setItem(15, sacgreen);
							e.getInventory().setItem(24, sacgreen);
							e.getInventory().setItem(13, addAnvilUse(combineItems(left.clone(), right)));
							e.getInventory().setItem(22, xpCost(left, right));
							return;
						}
						e.getInventory().setItem(11, upred);
						e.getInventory().setItem(12, upred);
						e.getInventory().setItem(20, upred);
						e.getInventory().setItem(14, sacred);
						e.getInventory().setItem(15, sacred);
						e.getInventory().setItem(24, sacred);
						e.getInventory().setItem(13, error);
						return;
					}
					e.getInventory().setItem(11, upred);
					e.getInventory().setItem(12, upred);
					e.getInventory().setItem(20, upred);
					e.getInventory().setItem(14, sacred);
					e.getInventory().setItem(15, sacred);
					e.getInventory().setItem(24, sacred);
					e.getInventory().setItem(13, error);
					return;
				}
				e.getInventory().setItem(11, upred);
				e.getInventory().setItem(12, upred);
				e.getInventory().setItem(20, upred);
				e.getInventory().setItem(14, sacred);
				e.getInventory().setItem(15, sacred);
				e.getInventory().setItem(24, sacred);
				
			}
		}, 0);
	}
	private ItemStack xpCost(ItemStack is1, ItemStack is2) {

		ItemStack anv = new ItemStack(Material.ANVIL);
		ItemMeta anvmeta = anv.getItemMeta();
		anvmeta.setDisplayName("§aCombine Items");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Combine the items in the slots");
		lore.add("§7to the left and right below.");
		lore.add(" ");
		lore.add("§7Cost:");
		lore.add("§3"+getXPCost(is1, is2) + " Exp Levels");
		lore.add("  ");
		lore.add("§eClick to combine");
		anvmeta.setLore(lore);
		anv.setItemMeta(anvmeta);
		anv = addDoNotMoveTag(anv);
		return anv;
	}
	
	private ItemStack addAnvilUse(ItemStack is) {
		ArrayList<String> lore = new ArrayList<String>();
		ArrayList<String> new_lore = new ArrayList<String>();
		if(is.hasItemMeta() && is.getItemMeta().hasLore()) {
			
			lore = (ArrayList<String>) is.getItemMeta().getLore();
			
			for(String str : lore) {
				
				if(!str.contains("§7Anvil Uses")) {
					new_lore.add(str);
					continue;
				}
				new_lore.add("§7Anvil Uses: §c"+(Integer.parseInt(str.replace("§7Anvil Uses: §c", ""))+1));
			}
			ItemMeta im = is.getItemMeta();
			boolean au = false;
			for(String str : new_lore) {
				if(str.contains("§7Anvil Uses: §c")) {
					au = true;
				}
			}
			if(au == false) {
				new_lore.add("      ");
				new_lore.add("§7Anvil Uses: §c1");
			}
			im.setLore(new_lore);
			is.setItemMeta(im);
		} else {
			lore.add("      ");
			lore.add("§7Anvil Uses: §c1");
			ItemMeta im = is.getItemMeta();
			im.setLore(lore);
			is.setItemMeta(im);
		}
		return is;
	}
	
	ArrayList<Enchantment> shovel_enchs = new ArrayList<Enchantment>();
	ArrayList<Enchantment> pickaxe_enchs = new ArrayList<Enchantment>();
	ArrayList<Enchantment> axe_enchs = new ArrayList<Enchantment>();
	ArrayList<Enchantment> hoe_enchs = new ArrayList<Enchantment>();
	ArrayList<Enchantment> armor_enchs = new ArrayList<Enchantment>();
	ArrayList<Enchantment> bow_enchs = new ArrayList<Enchantment>();
	ArrayList<Enchantment> sword_enchs = new ArrayList<Enchantment>();

	
	
	private ItemStack combineItems(ItemStack is1, ItemStack is2) {
		
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
		
		bow_enchs.add(Enchantment.ARROW_KNOCKBACK);
		bow_enchs.add(Enchantment.ARROW_DAMAGE);
		bow_enchs.add(Enchantment.ARROW_FIRE);
		bow_enchs.add(Enchantment.ARROW_INFINITE);
		bow_enchs.add(SpecialItems.ench_aiming);
		bow_enchs.add(SpecialItems.ench_cubism);
		bow_enchs.add(SpecialItems.ench_dragonhunter);
		bow_enchs.add(SpecialItems.ench_snipe);
		bow_enchs.add(SpecialItems.ench_telekinesis);
		
		armor_enchs.add(Enchantment.PROTECTION_ENVIRONMENTAL);
		armor_enchs.add(Enchantment.PROTECTION_EXPLOSIONS);
		armor_enchs.add(Enchantment.PROTECTION_FALL);
		armor_enchs.add(Enchantment.PROTECTION_FIRE);
		armor_enchs.add(Enchantment.PROTECTION_PROJECTILE);
		armor_enchs.add(Enchantment.THORNS);
		armor_enchs.add(Enchantment.DEPTH_STRIDER);
		armor_enchs.add(Enchantment.OXYGEN);
		armor_enchs.add(Enchantment.WATER_WORKER);
		armor_enchs.add(SpecialItems.ench_growth);
		
		hoe_enchs.add(SpecialItems.ench_telekinesis);
		
		axe_enchs.add(SpecialItems.ench_telekinesis);
		axe_enchs.add(Enchantment.SILK_TOUCH);
		axe_enchs.add(Enchantment.DIG_SPEED);
		
		pickaxe_enchs.add(SpecialItems.ench_telekinesis);
		pickaxe_enchs.add(Enchantment.SILK_TOUCH);
		pickaxe_enchs.add(Enchantment.LOOT_BONUS_BLOCKS);
		pickaxe_enchs.add(Enchantment.DIG_SPEED);
		
		shovel_enchs.add(SpecialItems.ench_telekinesis);
		shovel_enchs.add(Enchantment.SILK_TOUCH);
		shovel_enchs.add(Enchantment.DIG_SPEED);
		
		ArrayList<Material> swords = new ArrayList<Material>();
		swords.add(Material.DIAMOND_SWORD);
		swords.add(Material.GOLD_SWORD);
		swords.add(Material.IRON_SWORD);
		swords.add(Material.STONE_SWORD);
		swords.add(Material.WOOD_SWORD);
		
		ArrayList<Material> armors = new ArrayList<Material>();
		armors.add(Material.CHAINMAIL_BOOTS);
		armors.add(Material.DIAMOND_BOOTS);
		armors.add(Material.GOLD_BOOTS);
		armors.add(Material.IRON_BOOTS);
		armors.add(Material.LEATHER_BOOTS);
		
		armors.add(Material.CHAINMAIL_LEGGINGS);
		armors.add(Material.DIAMOND_LEGGINGS);
		armors.add(Material.GOLD_LEGGINGS);
		armors.add(Material.IRON_LEGGINGS);
		armors.add(Material.LEATHER_LEGGINGS);
		
		armors.add(Material.CHAINMAIL_CHESTPLATE);
		armors.add(Material.DIAMOND_CHESTPLATE);
		armors.add(Material.GOLD_CHESTPLATE);
		armors.add(Material.IRON_CHESTPLATE);
		armors.add(Material.LEATHER_CHESTPLATE);
		
		armors.add(Material.CHAINMAIL_HELMET);
		armors.add(Material.DIAMOND_HELMET);
		armors.add(Material.GOLD_HELMET);
		armors.add(Material.IRON_HELMET);
		armors.add(Material.LEATHER_HELMET);
		
		ArrayList<Material> hoes = new ArrayList<Material>();
		hoes.add(Material.DIAMOND_HOE);
		hoes.add(Material.GOLD_HOE);
		hoes.add(Material.IRON_HOE);
		hoes.add(Material.STONE_HOE);
		hoes.add(Material.WOOD_HOE);
		
		ArrayList<Material> pickaxes = new ArrayList<Material>();
		pickaxes.add(Material.DIAMOND_PICKAXE);
		pickaxes.add(Material.GOLD_PICKAXE);
		pickaxes.add(Material.IRON_PICKAXE);
		pickaxes.add(Material.STONE_PICKAXE);
		pickaxes.add(Material.WOOD_PICKAXE);
		
		ArrayList<Material> axes = new ArrayList<Material>();
		axes.add(Material.DIAMOND_AXE);
		axes.add(Material.GOLD_AXE);
		axes.add(Material.IRON_AXE);
		axes.add(Material.STONE_AXE);
		axes.add(Material.WOOD_AXE);
		
		ArrayList<Material> shovels = new ArrayList<Material>();
		shovels.add(Material.DIAMOND_SPADE);
		shovels.add(Material.GOLD_SPADE);
		shovels.add(Material.IRON_SPADE);
		shovels.add(Material.STONE_SPADE);
		shovels.add(Material.WOOD_SPADE);
		
		if(is2.getType().equals(Material.ENCHANTED_BOOK)) {
			EnchantmentStorageMeta enchmeta = (EnchantmentStorageMeta) is2.getItemMeta();
			if(swords.contains(is1.getType())) {
				is1 = applyEnchs(is1.clone(), enchmeta.getStoredEnchants(), sword_enchs);
				return is1;
			}
			if(is1.getType().equals(Material.BOW)) {
				is1 = applyEnchs(is1.clone(), enchmeta.getStoredEnchants(), bow_enchs);
				return is1;
			}
			if(is1.getType().equals(Material.ENCHANTED_BOOK)) {
				is1 = combineBooks(is1.clone(), enchmeta.getStoredEnchants());
				return is1;
			}
			if(armors.contains(is1.getType())) {
				is1 = applyEnchs(is1.clone(), enchmeta.getStoredEnchants(), armor_enchs);
				return is1;
			}
			if(hoes.contains(is1.getType())) {
				is1 = applyEnchs(is1.clone(), is2.getEnchantments(), hoe_enchs);
				return is1;
			}
			if(axes.contains(is1.getType())) {
				is1 = applyEnchs(is1.clone(), is2.getEnchantments(), axe_enchs);
				return is1;
			}
			if(pickaxes.contains(is1.getType())) {
				is1 = applyEnchs(is1.clone(), is2.getEnchantments(), pickaxe_enchs);
				return is1;
			}
			if(shovels.contains(is1.getType())) {
				is1 = applyEnchs(is1.clone(), is2.getEnchantments(), shovel_enchs);
				return is1;
			}
		}
		if(axes.contains(is1.getType())) {
			is1 = applyEnchs(is1.clone(), is2.getEnchantments(), axe_enchs);
			return is1;
		}
		if(pickaxes.contains(is1.getType())) {
			is1 = applyEnchs(is1.clone(), is2.getEnchantments(), pickaxe_enchs);
			return is1;
		}
		if(shovels.contains(is1.getType())) {
			is1 = applyEnchs(is1.clone(), is2.getEnchantments(), shovel_enchs);
			return is1;
		}
		if(hoes.contains(is1.getType())) {
			is1 = applyEnchs(is1.clone(), is2.getEnchantments(), hoe_enchs);
			return is1;
		}
		if(swords.contains(is1.getType()) && is1.getType().equals(is2.getType())) {
			is1 = applyEnchs(is1.clone(), is2.getEnchantments(), sword_enchs);
			return is1;
		}
		if(armors.contains(is1.getType())) {
			is1 = applyEnchs(is1.clone(), is2.getEnchantments(), armor_enchs);
			return is1;
		}
		if(is1.getType().equals(Material.BOW) && is2.getType().equals(Material.BOW)) {
			is1 = applyEnchs(is1, is2.getEnchantments(), bow_enchs);
			return is1;
		}
		return is1;
	}

	private ItemStack applyEnchs(ItemStack is, Map<Enchantment,Integer> enchs, ArrayList<Enchantment> possibleEnchantments) {
		for(Enchantment ench : enchs.keySet()) {
			boolean conflicts = false;
			if(possibleEnchantments.contains(ench)) {
					
					if(is.getItemMeta().getEnchantLevel(ench) < enchs.get(ench)) {
						for(Enchantment conf : is.getEnchantments().keySet()) {
							if((conf.conflictsWith(ench) && !conf.equals(ench))  ||  (!ench.canEnchantItem(is))) {
								conflicts = true;
							}
						}
						if(!conflicts) {
							is.addUnsafeEnchantment(ench, enchs.get(ench));
						}
						continue;
					} 
					if(is.getEnchantmentLevel(ench) == enchs.get(ench) && (is.getEnchantmentLevel(ench)+1) <= ench.getMaxLevel()) {
						for(Enchantment conf : is.getEnchantments().keySet()) {
							if((conf.conflictsWith(ench) && !conf.equals(ench))  ||  !ench.canEnchantItem(is)) {
								conflicts = true;
							}
						}
						if(conflicts == false) {
							is.addUnsafeEnchantment(ench, enchs.get(ench)+1);
						}
						continue;
					} else {
						if(ench.getName().contains("PROTECTION")) {
							for(Enchantment conf : is.getEnchantments().keySet()) {
								if((conf.conflictsWith(ench) && !conf.equals(ench))  ||  !ench.canEnchantItem(is)) {
									conflicts = true;
								}
							}
							if(conflicts == false) {
								if(is.getEnchantmentLevel(ench)+1 <= ench.getMaxLevel()+1){
									is.addUnsafeEnchantment(ench, enchs.get(ench)+1);
								}
							}
						}
					}
			}
		}
		return Enchanting.addEnchantingLore(Enchanting.removeEnchantingLore(is, false));
	}
	
	private ItemStack combineBooks(ItemStack is, Map<Enchantment,Integer> enchs) {
		EnchantmentStorageMeta enchmeta = (EnchantmentStorageMeta) is.getItemMeta();
		for(Enchantment ench : enchs.keySet()) {
			if(enchmeta.getStoredEnchantLevel(ench) < enchs.get(ench)) {
				enchmeta.addStoredEnchant(ench, enchs.get(ench), true);
				continue;
			} 
			if(enchmeta.getStoredEnchantLevel(ench) == enchs.get(ench) && (enchmeta.getStoredEnchantLevel(ench)+1) <= ench.getMaxLevel()) {
				enchmeta.addStoredEnchant(ench, enchs.get(ench)+1, true);
				continue;
			} else {
				if(ench.getName().contains("PROTECTION")) {
					if(enchmeta.getStoredEnchantLevel(ench) == enchs.get(ench) && (enchmeta.getStoredEnchantLevel(ench)+1) <= ench.getMaxLevel()+1) {
						enchmeta.addStoredEnchant(ench, enchs.get(ench)+1, true);
						continue;
					}
				}
			}
			
		}
		enchmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		enchmeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		enchmeta.setDisplayName("§9Enchanted Book");
		is.setItemMeta(enchmeta);
		for(Enchantment ench : enchmeta.getStoredEnchants().keySet()) {
			is.addUnsafeEnchantment(ench, enchmeta.getStoredEnchants().get(ench));
		}
		is = Enchanting.addEnchantingLore(Enchanting.removeEnchantingLore(is, false));
		return is;
	}
	
	private int getXPCost(ItemStack is1, ItemStack is2) {
		double xp = 0;
		int highest_anvil_uses = 0;
		if(is1.hasItemMeta() && is1.getItemMeta().hasLore()) {
			for(String str : is1.getItemMeta().getLore()) {
				if(str.contains("§7Anvil Uses:")) {
					if (highest_anvil_uses < Integer.parseInt(str.replace("§7Anvil Uses: §c", ""))) {
						highest_anvil_uses = Integer.parseInt(str.replace("§7Anvil Uses: §c", ""));
					}
				}
			}
		}
		
		if(is2.hasItemMeta() && is2.getItemMeta().hasLore()) {
			for(String str : is2.getItemMeta().getLore()) {
				if(str.contains("§7Anvil Uses:")) {
					if (highest_anvil_uses < Integer.parseInt(str.replace("§7Anvil Uses: §c", ""))) {
						highest_anvil_uses = Integer.parseInt(str.replace("§7Anvil Uses: §c", ""));
					}
				}
			}
		}
		xp = xp+(3*Math.pow(2, highest_anvil_uses+1)-2);
		Map<Enchantment, Integer> enchs = null;
		if(is2.getType().equals(Material.ENCHANTED_BOOK)) {
			enchs = ((EnchantmentStorageMeta)is2.getItemMeta()).getStoredEnchants();
		} else {
			enchs = is2.getEnchantments();
		}
		for(Enchantment ench : enchs.keySet()) {
			if(ench.getName().equals("DAMAGE_ARTHROPODS")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*1.5;
			}
			if(ench.getName().equals("Cleave")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*1.5;
			}
			if(ench.getName().equals("Critical")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*1.5;
			}
			if(ench.getName().equals("Cubism")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*3;
			}
			if(ench.getName().equals("Dragon Hunter")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*3;
			}
			if(ench.getName().equals("Ender Slayer")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*3;
			}
			if(ench.getName().equals("Execute")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*1.5;
			}
			if(ench.getName().equals("Experience")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*3;
			}
			if(ench.getName().equals("FIRE_ASPECT")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*3;
			}
			if(ench.getName().equals("First Strike")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*1.5;
			}
			if(ench.getName().equals("Giant Killer")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*1.5;
			}
			if(ench.getName().equals("Impaling")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*3;
			}
			if(ench.getName().equals("KNOCKBACK")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*1.5;
			}
			if(ench.getName().equals("Lethalty")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*1.5;
			}
			if(ench.getName().equals("Life Steal")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*1.5;
			}
			if(ench.getName().equals("LOOT_BONUS_MOBS")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*3;
			}
			if(ench.getName().equals("Luck")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*1.5;
			}
			if(ench.getName().equals("Scavenger")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*3;
			}
			if(ench.getName().equals("DAMAGE_ALL")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*1.5;
			}
			if(ench.getName().equals("DAMAGE_UNDEAD")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*1.5;
			}
			if(ench.getName().equals("Telekinesis")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*1.5;
			}
			if(ench.getName().equals("Thunderlord")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*6;
			}
			if(ench.getName().equals("Vampirism")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*1.5;
			}
			if(ench.getName().equals("Venomous")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*1.5;
			}
			
			//bow enchs
			if(ench.getName().equals("Aiming")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*1.5;
			}
			if(ench.getName().equals("Snipe")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*1.5;
			}
			if(ench.getName().equals("Venomous")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*1.5;
			}
			if(ench.getName().equals("Power")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*1.5;
			}
			if(ench.getName().equals("Punch")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*3;
			}
			if(ench.getName().equals("Flame")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*3;
			}
			
			//armor enchs
			if(ench.getName().contains("PROTECTION")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*1.5;
			}
			if(ench.getName().equals("Growth")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*0.5;
			}
			if(ench.getName().equals("Telekinesis")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*0.5;
			}
			
			//tools
			if(ench.getName().equals("DIG_SPEED")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*0.5;
			}
			if(ench.getName().equals("SILK_TOUCH")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*1;
			}
			if(ench.getName().equals("LOOT_BONUS_BLOCKS")) {
				xp = xp + (is2.getEnchantmentLevel(ench))*1.5;
			}
			
		}
		
		return (int) xp;
	}
}
