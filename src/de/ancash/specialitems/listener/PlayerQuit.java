package de.ancash.specialitems.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.ancash.specialitems.PClass;
import de.ancash.specialitems.SpecialItems;

public class PlayerQuit implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		PClass.playerStats.remove(e.getPlayer().getUniqueId());
		if(SpecialItems.equipedPets.containsKey(e.getPlayer().getUniqueId())) {
			SpecialItems.equipedPets.get(e.getPlayer().getUniqueId()).remove();;
			SpecialItems.petsName.get(e.getPlayer().getUniqueId()).remove();;
			SpecialItems.equipedPets.remove(e.getPlayer().getUniqueId());
			SpecialItems.petsName.remove(e.getPlayer().getUniqueId());
		}
		e.getPlayer().setHealth(1);
		e.getPlayer().resetMaxHealth();
	}
	
}
