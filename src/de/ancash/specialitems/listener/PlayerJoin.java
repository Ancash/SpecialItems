package de.ancash.specialitems.listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.ancash.specialitems.Files;
import de.ancash.specialitems.PClass;
import de.ancash.specialitems.SpecialItems;
import de.ancash.specialitems.bazaar.SellOffer;

public class PlayerJoin implements Listener {
	
	@SuppressWarnings("unused")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) throws IOException, InvalidConfigurationException {
		final Player p = e.getPlayer();
		File playerData = new File("plugins/SpecialItems/playerData/" + p.getUniqueId().toString() + "/data.yml");
		FileConfiguration pD = YamlConfiguration.loadConfiguration(playerData);
		if(!new File("plugins/SpecialItems/playerData/" + p.getUniqueId().toString()).exists()) {
			new File("plugins/SpecialItems/playerData/" + p.getUniqueId().toString()).mkdir();
		}
		if(!playerData.exists()) {
			playerData.createNewFile();
			pD.set("stats.extra_mana", 0);
			pD.set("stats.extra_defense", 0);
			pD.set("stats.extra_health", 0);
			pD.set("stats.extra_strength", 0);
			pD.set("stats.extra_crit_chance", 0);
			pD.set("stats.extra_crit_damage", 0);
			pD.set("skill.mining.level", 0);
			pD.set("skill.farming.level", 0);
			pD.set("skill.foraging.level", 0);
			pD.set("skill.combat.level", 0);
			pD.save(playerData);
			new File("plugins/SpecialItems/playerData/" + p.getUniqueId().toString() + "/sellOffer").mkdir();
			new File("plugins/SpecialItems/playerData/" + p.getUniqueId().toString() + "/buyOrder").mkdir();
		}
		p.setMaxHealth(Files.cfg.getDouble("base_health")+PClass.getHealthWithoutBase(p));
		p.setHealthScale(20+(PClass.getHealthWithoutBase(p)/100));
		p.setHealth(p.getMaxHealth());
				
		PClass pS = new PClass();
		pS.setUUID(p.getUniqueId());
		pS.setPlayer(p);
		pS.setCritChance(PClass.getCritChance(p));
		pS.setCritDamage(PClass.getCritDamage(p));
		pS.setDefense(PClass.getTotalDefense(p,false,false,false));
		pS.setMaxHealth(PClass.getTotalHealth(p));
		pS.setCurrentHealth(pS.getMaxHealth());
		pS.setMaxIntelligence(PClass.getTotalIntelligence(p));
		pS.setCurrentIntelligence(pS.getMaxIntelligence());

		PClass.playerStats.put(p.getUniqueId(), pS);
		
		if(SpecialItems.merciless_swipe != null && !SpecialItems.merciless_swipe.isDead()) {
			SpecialItems.merciless_swipe.remove();
		}
		SpecialItems.merciless_swipe = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
		SpecialItems.merciless_swipe.setVisible(false);
		SpecialItems.merciless_swipe.setMarker(true);
		SpecialItems.merciless_swipe.setCustomName("§cMercilessSwipe");
				
		File sellOffers = new File("plugins/SpecialItems/playerData/" + p.getUniqueId().toString() + "/sellOffer");
		File buyOrders = new File("plugins/SpecialItems/playerData/" + p.getUniqueId().toString() + "/buyOrder");
		ArrayList<SellOffer> sos = new ArrayList<SellOffer>();
		if(sellOffers.listFiles() == null) return;
		for(File f : sellOffers.listFiles()) {
			
			FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
			fc.load(f);
			SellOffer so = new SellOffer();
			so.setUUID(UUID.fromString(fc.getString("uuid")));
			so.setPrice(fc.getDouble("price"));
			so.setAmount(fc.getInt("amount"));
			sos.add(so);
		}		
	}
}
