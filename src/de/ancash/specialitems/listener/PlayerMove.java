package de.ancash.specialitems.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.ancash.specialitems.pets.Pet;

public class PlayerMove implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		if(Pet.pets.containsKey(p.getUniqueId())) {
			Pet pet = Pet.pets.get(p.getUniqueId());
			pet.addLocation(p.getLocation());
		}
		
	}
	
}
