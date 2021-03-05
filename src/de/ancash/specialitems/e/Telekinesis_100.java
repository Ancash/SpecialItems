package de.ancash.specialitems.e;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import de.ancash.specialitems.Files;
import de.ancash.specialitems.SpecialItems;

public class Telekinesis_100 extends Enchantment implements Listener{
	
	public static ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public Telekinesis_100(int id) {
		super(id);
	}
	
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		
		Player p = e.getPlayer();
		if(p.getItemInHand() == null) {
			return;
		}
		if(!p.getItemInHand().hasItemMeta()) {
			return;
		}
		Block b = e.getBlock();
		ArrayList<String> plugin_names = new ArrayList<String>();
		for(Plugin pl : Bukkit.getServer().getPluginManager().getPlugins()) {
			plugin_names.add(pl.getName());
		}
		if(plugin_names.contains("WorldGuard")) {
			Plugin pl = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
			if(pl != null && pl instanceof WorldGuardPlugin) {
				if(!((WorldGuardPlugin) pl).canBuild(p, b)) {
					return;
				}
			}
		}
		if(p.getItemInHand().getType() == Material.DIAMOND_SWORD ||
				p.getItemInHand().getType() == Material.GOLD_SWORD ||
				p.getItemInHand().getType() == Material.IRON_SWORD ||
				p.getItemInHand().getType() == Material.STONE_SWORD ||
				p.getItemInHand().getType() == Material.WOOD_SWORD) {
			return;
		}
		if(p.getItemInHand().getItemMeta().hasEnchant(SpecialItems.ench_telekinesis)) {
			for(ItemStack is : e.getBlock().getDrops(p.getItemInHand())) {
				if(p.getInventory().firstEmpty() != -1) {
					p.getInventory().addItem(is);
					e.getBlock().setType(Material.AIR);
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		if(e.getEntity() instanceof Player) {
			return;
		}
		if(e.getDrops().size() != 0) {
			ArrayList<ItemStack> drops = (ArrayList<ItemStack>) e.getDrops();
			LivingEntity le = e.getEntity().getKiller();
			if(le instanceof Player) {
				Player p = (Player) le;
				if(p.getItemInHand() != null && p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasEnchant(SpecialItems.ench_telekinesis)) {
					boolean inv_full = false;
					for(ItemStack is : drops) {
						if(p.getInventory().firstEmpty() != -1) {
							p.getInventory().addItem(is);
						} else {
							e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), is);
							inv_full = true;
						}
					}
					if(inv_full) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("inventory_full")));
						p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1, 1);
					}
					p.giveExp(e.getDroppedExp());
					e.setDroppedExp(0);
					e.getDrops().clear();
				}
			}
		}
	}
	
	public int getID() {
		return 100;
	}
	
	@Override
	public boolean canEnchantItem(ItemStack arg0) {
		return true;
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxLevel() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Telekinesis";
	}

	@Override
	public int getStartLevel() {
		// TODO Auto-generated method stub
		return 1;
	}
}
