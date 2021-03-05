package de.ancash.specialitems.customitems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Monster;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import de.ancash.specialitems.PClass;
import de.ancash.specialitems.SpecialItems;
import de.ancash.specialitems.abilities.items.Ability;
import de.tr7zw.nbtapi.NBTItem;

public class RightClick implements Listener{
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
	
		Player p = e.getPlayer();
		
		
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				
			if(p.getItemInHand() != null && p.getItemInHand().hasItemMeta() && new NBTItem(p.getItemInHand()).hasKey("ability")) {
				boolean cancelled = false;
				String ability = new NBTItem(p.getItemInHand()).getString("ability").toLowerCase();
				if(ability.equals(Ability.getDragonRage().getIdentifier())) {
					cancelled = dragonRage(p);
				} else if(ability.equals(Ability.getFireblast().getIdentifier())) {
					cancelled = fireblast(p);
				} else if(ability.equals(Ability.getInstantTransmission().getIdentifier())) {
					cancelled = instantTransmission(p);
				}				
				e.setCancelled(cancelled);
			}
		}
	}
		
	private boolean fireblast(Player p) {
		if(SpecialItems.used_emberrod.contains(p)) {
			p.sendMessage("§cYou have to wait to use this ability again!");
			return false;
		}
		PClass pS = PClass.playerStats.get(p.getUniqueId());
		if(!(pS.getCurrentIntelligence() >= Ability.getFireblast().getManaCost())) {
			p.sendMessage("§cYou don't have enough mana to use §6Fire Blast§c!");
			return false;
		} else {
			pS.setCurrentIntelligence(pS.getCurrentIntelligence()-Ability.getFireblast().getManaCost());
			p.sendMessage("§aUsed §6Fire Blast§a! §b-" + Ability.getFireblast().getManaCost() + " Mana");
			SpecialItems.used_emberrod.add(p);
		}
		p.launchProjectile(Fireball.class);
		Bukkit.getScheduler().scheduleSyncDelayedTask(SpecialItems.plugin, new Runnable() {
			
			@Override
			public void run() {
				
				p.launchProjectile(Fireball.class);
				
			}
		}, 10L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(SpecialItems.plugin, new Runnable() {
			
			@Override
			public void run() {
				
				p.launchProjectile(Fireball.class);
				
			}
		}, 20L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(SpecialItems.plugin, new Runnable() {
			
			@Override
			public void run() {
				
				if(SpecialItems.used_emberrod.contains(p)) {
					SpecialItems.used_emberrod.remove(p);
				}
				
			}
		}, 20 * Ability.getFireblast().getCooldown());
		return true;
	}
	
	private boolean instantTransmission(Player p) {
		PClass pS = PClass.playerStats.get(p.getUniqueId());
		if(!(pS.getCurrentIntelligence() >= Ability.getInstantTransmission().getManaCost())) {
			p.sendMessage("§cYou don't have enough mana to use §6Instant Transmission§c!");
			return false;
		}
		if(p.getLocation().getY() < 250) {
			Set<Material> transparent = new HashSet<Material>();
			transparent.add(Material.AIR);
			transparent.add(Material.WATER);
			transparent.add(Material.STATIONARY_WATER);
			transparent.add(Material.LAVA);
			transparent.add(Material.STATIONARY_LAVA);
			pS.setCurrentIntelligence(pS.getCurrentIntelligence()-Ability.getInstantTransmission().getManaCost());
			Block b = null;
			for(Block block : p.getLineOfSight(transparent, Ability.getInstantTransmission().getRange())) {
				if(transparent.contains(block.getType())) continue;
				b = block;
				break;
			}
			if(b == null) {
				b = p.getLineOfSight(transparent, Ability.getInstantTransmission().getRange()).get(p.getLineOfSight(transparent, 8).size()-1);
			} else {
				p.sendMessage("§cThere are blocks in the way!");
			}
			Location loc = new Location(b.getWorld(), b.getX(), b.getY()+1, b.getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
			p.teleport(loc);
			p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
			p.sendMessage("§aUsed §6Instant Transmission§a! §b-50 Mana");
			return true;
		} else
			p.sendMessage("§cYou're to high! Calm down");
		return false;
	}
	
	@SuppressWarnings("deprecation")
	private boolean dragonRage(Player p) {
		PClass pS = PClass.playerStats.get(p.getUniqueId());
		if(!(pS.getCurrentIntelligence() >= Ability.getDragonRage().getManaCost())) {
			p.sendMessage("§cYou don't have enough mana to use §6Dragon Rage§c!");
			return false;
		} else {
			pS.setCurrentIntelligence(pS.getCurrentIntelligence()-Ability.getDragonRage().getManaCost());
			p.sendMessage("§aUsed §6Dragon Rage§a! §b-100 Mana");
		}
		p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
		for(int i = 0; i < 100; i++) {
			p.playEffect(p.getLocation().add(0, 1, 0), Effect.FLAME, 1);
		}
		ArmorStand as = (ArmorStand) p.getTargetBlock((Set<Material>)null, 3).getWorld().spawnEntity(p.getTargetBlock((Set<Material>)null, 3).getLocation(), EntityType.ARMOR_STAND);
		as.setVisible(false);
		ArrayList<String> plugin_names = new ArrayList<String>();
		for(Plugin pl : Bukkit.getServer().getPluginManager().getPlugins()) {
			plugin_names.add(pl.getName());
		}
		for(Entity entity : as.getNearbyEntities(2, 2, 2)) {
			if(!(entity instanceof Player) && !(entity instanceof NPC) ) {
				

				if(plugin_names.contains("Citizens")) {
					boolean isCitizensNPC = entity.hasMetadata("NPC");
					if(isCitizensNPC) {
						continue;
					}
				}
				
				if(entity instanceof Monster){
					Monster m = (Monster) entity;
					m.damage(700, p);
				}
			}
		}
		as.remove();
		return true;
	}
}