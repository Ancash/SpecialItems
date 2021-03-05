package de.ancash.specialitems.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SpawnDragon implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(args.length == 1) {
			if(sender instanceof Player) {
				
				double health = Double.parseDouble(args[0]);
				
				Player p = (Player) sender;
				EnderDragon ed = (EnderDragon) p.getWorld().spawnEntity(p.getLocation(), EntityType.ENDER_DRAGON);
				ed.setMaxHealth(health);
				ed.setHealth(health);
				ed.setCustomName("Superior Dragon");
				
			}
		}
		
		return false;
	}

}
