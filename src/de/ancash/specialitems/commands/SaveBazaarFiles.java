package de.ancash.specialitems.commands;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;

import de.ancash.specialitems.bazaar.BazaarScheduler;

public class SaveBazaarFiles implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {

		if(sender.isOp()) {
			try {
				BazaarScheduler.saveBazaarFiles();
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}

}
