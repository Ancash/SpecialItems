package de.ancash.specialitems.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

public class InvOpen implements Listener {

	@EventHandler
	public void onInvOpen(InventoryOpenEvent e) {
		for(int slot = 0; slot <= e.getPlayer().getInventory().getSize() ; slot++) {
			
			if(e.getPlayer().getInventory().getItem(slot) != null) {
				ItemStack is = e.getPlayer().getInventory().getItem(slot);
				if(is.getItemMeta().hasEnchants() || is.getType().equals(Material.ENCHANTED_BOOK))  {
					is = Enchanting.removeEnchantingLore(is, false);
					is = Enchanting.addEnchantingLore(is);
					e.getPlayer().getInventory().setItem(slot, is);
				}
			}	
		}
	}
}
