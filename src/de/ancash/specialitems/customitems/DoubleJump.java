package de.ancash.specialitems.customitems;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import de.ancash.specialitems.Files;
import de.ancash.specialitems.PClass;
import de.ancash.specialitems.SpecialItems;

public class DoubleJump implements Listener {
	
	public static ArrayList<UUID> double_jump = new ArrayList<UUID>();
	
	@EventHandler
	public void onDoubleJump(PlayerToggleFlightEvent e) {
		if(!Files.cfg.getBoolean("doublejump")) return;
		e.setCancelled(true);
		Player p = e.getPlayer();
		
		if(p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR)) {
			e.setCancelled(false);
			return;
		} 
		
		PClass pS = PClass.playerStats.get(p.getUniqueId());
		if(pS.getCurrentIntelligence() < 40) {
			e.setCancelled(true);
			p.sendMessage("§cYou don't have enough mana to use §6Double Jump§c!");
			p.setFlying(false);
			return;
		}
		if(!double_jump.contains(p.getUniqueId())) {
			if(p.getLocation().getDirection().getY() >= 0) {
				p.setVelocity(e.getPlayer().getLocation().getDirection().setY(p.getLocation().getDirection().getY()*1.1));
			} else {
				p.setVelocity(e.getPlayer().getLocation().getDirection().setY(1+p.getLocation().getDirection().getY()));
			}
			pS.setCurrentIntelligence(pS.getCurrentIntelligence()-40);
			p.setFlying(true);
			p.setAllowFlight(false);
			p.sendMessage("§aUsed §6Double Jump§a! §b-40 Mana");
			double_jump.add(p.getUniqueId());
			Bukkit.getScheduler().runTaskLater(SpecialItems.plugin, new Runnable() {
			
				@Override
				public void run() {
					
					double_jump.remove(p.getUniqueId());
				
				}
			}, 5L);
		} 
	}
		
}
