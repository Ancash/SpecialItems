package de.ancash.specialitems.anvil;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.ancash.specialitems.Files;
import net.md_5.bungee.api.ChatColor;

public class OpenAnvilCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if(p.hasPermission(Files.cfg.getString("commands.anvil.permission"))) {
				Inventory anvinv = Bukkit.createInventory(null, 9*6, "Anvil");				
				for(int i = 0; i<9*6; i++) {
					anvinv.setItem(i, Anvil.getAnvInv().getItem(i));
				}
				
				p.openInventory(anvinv);
				return true;
				
			} else
				p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("no_permission")));
				return true;
		} else
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("no_player")));
		
		return false;
	}

}
