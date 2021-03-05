package de.ancash.specialitems.customitems;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.ancash.specialitems.Files;
import net.md_5.bungee.api.ChatColor;

public class CustomItemInv implements CommandExecutor, Listener{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if(p.hasPermission(Files.cfg.getString("commands.ci.permission"))) {
				if(args.length != 0) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.ci.usage")));
					return true;
				}
				p.openInventory(CustomItems.getInv());
			} else
				p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("no_permission")));
				return true;
		} else
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("no_player")));
		return false;
	}

	@EventHandler
	public void ciClick(InventoryClickEvent e) {
		if(e.getView().getTitle().equals("Custom Items")) {
			e.setCancelled(true);
			if(e.getClickedInventory() != null && e.getClickedInventory().equals(e.getInventory())) {
				if(e.getCurrentItem() != null) {
					e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
				}
			}
			
		}

	}
}
