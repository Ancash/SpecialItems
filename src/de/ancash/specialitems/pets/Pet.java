package de.ancash.specialitems.pets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class Pet {

	public static ArrayList<Integer> common_pet_xp_requirements = new ArrayList<Integer>();
	public static ArrayList<Integer> uncommon_pet_xp_requirements = new ArrayList<Integer>();
	public static ArrayList<Integer> rare_pet_xp_requirements = new ArrayList<Integer>();
	public static ArrayList<Integer> epic_pet_xp_requirements = new ArrayList<Integer>();
	public static ArrayList<Integer> legendary_pet_xp_requirements = new ArrayList<Integer>();
	
	public static HashMap<UUID, Pet> pets = new HashMap<UUID, Pet>();
	
	private Player player;
	private ArmorStand nametag;
	private ArmorStand petitem;
	private ArrayList<Location> locs;
	private Location loc;
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	public void setNameTagAS(ArmorStand as) {
		this.nametag = as;
	}
	public void setPetAS(ArmorStand as) {
		this.petitem = as;
	}
	public void addLocation(Location loc) {
		this.locs.add(loc);
	}
	public void setLocs(ArrayList<Location> locs) {
		this.locs = locs;
	}
	public void setLoc(Location loc) {
		this.loc = loc;
	}
	public void removeLoc(int index) {
		if(index < locs.size() && locs.size() != 0) {
			this.locs.remove(index);
		}
	}
	public void removeLoc(Location loc) {
		this.locs.remove(loc);
	}
	public Location getLoc() {
		return this.loc;
	}
	public Player getPlayer() {
		return this.player;
	}
	public ArmorStand getNameTagAS() {
		return this.nametag;
	}
	public ArmorStand getPetItemAS() {
		return this.petitem;
	}
	public ArrayList<Location> getLocs(){
		return this.locs;
	}
}
