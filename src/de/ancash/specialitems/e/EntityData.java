package de.ancash.specialitems.e;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class EntityData {
	
	public static ArrayList<EntityData> eds = new ArrayList<EntityData>();
	
	public static ArrayList<UUID> entity_uuid = new ArrayList<UUID>();
	
	private ArrayList<UUID> player_who_already_hit_this_entity = new ArrayList<UUID>();
	
	private UUID uuid;
	
	void setUUID(UUID uniqueId) {
		uuid = uniqueId;
	}

	public static void register_new(Entity e) {
		if(!entity_uuid.contains(e.getUniqueId())) {
			EntityData ed = new EntityData();
			ed.setUUID(e.getUniqueId());
			eds.add(ed);
		}
	}
	
	public static boolean checkIfAlreadyAttacked(Entity e, Player p) {

		for(EntityData ed : eds) {
			if(ed.uuid.equals(e.getUniqueId())) {
				if(ed.player_who_already_hit_this_entity.contains(p.getUniqueId())) {
					return true;
				} else
					
					return false;
			}
		}
		return true;
	}

	public static void addPlayer(Entity e,Player p) {

		for(EntityData ed : eds) {
			if(ed.getUUID().equals(e.getUniqueId())) {
				if(!ed.player_who_already_hit_this_entity.contains(p.getUniqueId())) {
					ed.player_who_already_hit_this_entity.add(p.getUniqueId());
				}
			}
		}
		
	}
	
	private UUID getUUID() {
		return uuid;
	}	
}
