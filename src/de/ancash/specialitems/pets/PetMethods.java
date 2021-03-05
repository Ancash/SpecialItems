package de.ancash.specialitems.pets;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import de.ancash.specialitems.Files;
import de.tr7zw.nbtapi.NBTItem;

public class PetMethods {
	
	public static String getTexure(ItemStack pet) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		String texture = null;
		
		SkullMeta sm = (SkullMeta) pet.getItemMeta();
		Field profileField = sm.getClass().getDeclaredField("profile");
		profileField.setAccessible(true);
		GameProfile profile = (GameProfile) profileField.get(sm);
		Collection<Property> textures = profile.getProperties().get("textures");
		for(Property p : textures) {
			texture = p.getValue();
		}
		return texture;
	}
	
	public static ItemStack setTexture(ItemStack pet, String texture) {
		SkullMeta hm = (SkullMeta) pet.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		profile.getProperties().put("textures", new Property("textures", texture));
		try {
			Field field = hm.getClass().getDeclaredField("profile");
			field.setAccessible(true);
			field.set(hm, profile);
		} catch(IllegalArgumentException  | NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
		pet.setItemMeta(hm);
		return pet;
	}
	
	public static double round(final double value, final int frac) {
        return Math.round(Math.pow(10.0, frac) * value) / Math.pow(10.0, frac);
    }
	
	public static ItemStack setProgressBar(ItemStack is, double current, double max) {
		ItemMeta im = is.getItemMeta();
		ArrayList<String> new_lore = new ArrayList<String>();
		if(im.getLore() == null) return is;
		for(String str : im.getLore()) {
			if(str.contains("--------------------")) {
				String string = str.replace("--------------------", "");
				String progressbar = "";
				for(double d = 0.05; d <= 1; d = d +0.05) {
					if(current / max <= d) {
						progressbar = progressbar+"§f-";
					} else {
						progressbar = progressbar+"§a-";
					}
				}
				string = progressbar+string;
				new_lore.add(string);
			} else {
				new_lore.add(str);
			}
		}
		im.setLore(new_lore);
		is.setItemMeta(im);
		return is;
	}
	
	public static ItemStack preparePet(ItemStack is, Player p) {
		
		is = removeLore(is, "               ", "              ");
		is = replaceSingleStringInLoreLore(is, "%lvl%", ""+getCurrentPetLevel(is));
		is = replaceSingleStringInLoreLore(is, "%cur_xp%", ""+new NBTItem(is).getInteger("xp"));
		
		if(getCurrentPetLevel(is)-2 < 100) {
			if(is.getItemMeta().getDisplayName().contains("§f")) {
				is = replaceSingleStringInLoreLore(is, "%progress_percent%", ""+ round(((double)new NBTItem(is).getInteger("xp") / (double)Pet.common_pet_xp_requirements.get(getCurrentPetLevel(is)-2) * 100), 1)  +"%");
			} else {
				if(is.getItemMeta().getDisplayName().contains("§a")) {
					is = replaceSingleStringInLoreLore(is, "%progress_percent%", ""+ round(((double)new NBTItem(is).getInteger("xp") / (double)Pet.uncommon_pet_xp_requirements.get(getCurrentPetLevel(is)-2) * 100), 1)  +"%");
				} else {
					if(is.getItemMeta().getDisplayName().contains("§9")) {
						is = replaceSingleStringInLoreLore(is, "%progress_percent%", ""+ round(((double)new NBTItem(is).getInteger("xp") / (double)Pet.rare_pet_xp_requirements.get(getCurrentPetLevel(is)-2) * 100), 1)  +"%");
					} else {
						if(is.getItemMeta().getDisplayName().contains("§5")) {
							is = replaceSingleStringInLoreLore(is, "%progress_percent%", ""+ round(((double)new NBTItem(is).getInteger("xp") / (double)Pet.epic_pet_xp_requirements.get(getCurrentPetLevel(is)-2) * 100), 1)  +"%");
						} else {
							if(is.getItemMeta().getDisplayName().contains("§6")) {
								is = replaceSingleStringInLoreLore(is, "%progress_percent%", ""+ round(((double)new NBTItem(is).getInteger("xp") / (double)Pet.legendary_pet_xp_requirements.get(getCurrentPetLevel(is)-2) * 100), 1)  +"%");
							}
						}
					}
				}
			}
			
			if(is.getItemMeta().getDisplayName().contains("§f")) {
				is = replaceSingleStringInLoreLore(is, "%req_xp%", ""+Pet.common_pet_xp_requirements.get(getCurrentPetLevel(is)-2));
			} else {
				if(is.getItemMeta().getDisplayName().contains("§a")) {
					is = replaceSingleStringInLoreLore(is, "%req_xp%", ""+Pet.uncommon_pet_xp_requirements.get(getCurrentPetLevel(is)-2));
				} else {
					if(is.getItemMeta().getDisplayName().contains("§9")) {
						is = replaceSingleStringInLoreLore(is, "%req_xp%", ""+Pet.rare_pet_xp_requirements.get(getCurrentPetLevel(is)-2));
					} else {
						if(is.getItemMeta().getDisplayName().contains("§5")) {
							is = replaceSingleStringInLoreLore(is, "%req_xp%", ""+Pet.epic_pet_xp_requirements.get(getCurrentPetLevel(is)-2));
						} else {
							if(is.getItemMeta().getDisplayName().contains("§6")) {
								is = replaceSingleStringInLoreLore(is, "%req_xp%", ""+Pet.legendary_pet_xp_requirements.get(getCurrentPetLevel(is)-2));
							}
						}
					}
				}
			}
			
			if(is.getItemMeta().getDisplayName().contains("§f")) {
				is = setProgressBar(is, new NBTItem(is).getInteger("xp"), Pet.common_pet_xp_requirements.get(getCurrentPetLevel(is)-2));
			} else {
				if(is.getItemMeta().getDisplayName().contains("§a")) {
					is = setProgressBar(is, new NBTItem(is).getInteger("xp"), Pet.uncommon_pet_xp_requirements.get(getCurrentPetLevel(is)-2));
				} else {
					if(is.getItemMeta().getDisplayName().contains("§9")) {
						is = setProgressBar(is, new NBTItem(is).getInteger("xp"), Pet.rare_pet_xp_requirements.get(getCurrentPetLevel(is)-2));
					} else {
						if(is.getItemMeta().getDisplayName().contains("§5")) {
							is = setProgressBar(is, new NBTItem(is).getInteger("xp"), Pet.epic_pet_xp_requirements.get(getCurrentPetLevel(is)-2));
						} else {
							if(is.getItemMeta().getDisplayName().contains("§6")) {
								is = setProgressBar(is, new NBTItem(is).getInteger("xp"), Pet.legendary_pet_xp_requirements.get(getCurrentPetLevel(is)-2));
							}
						}
					}
				}
			}
		}
		is = setStats(is, new NBTItem(is).getString("pet"), new NBTItem(is).getString("rarity"));
		return is;
	}
	
	@SuppressWarnings("serial")
	static ArrayList<String> petPlaceholder = new ArrayList<String>() {{
		add("strength");
		add("crit_chance");
		add("crit_damage");
		add("health");
		add("defense");
		add("intelligence");
		add("merciless_swipe");
		add("apex_predator");
		add("end_strike");
		add("one_with_the_dragon_strength");
		add("one_with_the_dragon_damage");
		add("superior");
		add("xp_boost");
	}};
	
	public static ItemStack setStats(ItemStack is, String petName, String rarity) {
		for(String placeholder : petPlaceholder) {
			is = replaceSingleStringInLoreLore(is, "%"+placeholder+"%", (round(getAbilityValue(petName, rarity, getPetLvl(is), placeholder),1)+"").replace(".0", ""));
		}
		return is;
	}
	
	private static  double getAbilityValue(String petName, String rarity, int lvl, String abilityName) {
		double value = 0;
		value = (Files.petsConfig.getDouble("pets."+petName+".upgrade_per_level."+rarity+"."+abilityName) * lvl) + Files.petsConfig.getInt("pets."+petName+".base."+rarity+"."+abilityName);
		switch (abilityName) {
		case "health":
			value = round(value, 0);
			break;
		case "defense":
			value = round(value, 0);
			break;
		case "intelligence":
			value = round(value, 0);
			break;
		case "strength":
			value = round(value, 0);
			break;
		case "crit_chance":
			value = round(value, 0);
			break;
		case "crit_damage":
			value = round(value, 0);
			break;
		default:
			break;
		}
		return value;
	}
	
	public static int getPetLvl(ItemStack is) {
		int lvl = Integer.valueOf(is.getItemMeta().getDisplayName().split(" ")[1].replace("§8", "").replace("]", ""));
		return lvl;
	}
	
	public static int getCurrentPetLevel(ItemStack is) {
		int lvl = Integer.valueOf(is.getItemMeta().getDisplayName().split(" ")[1].replace("]", "").replace("§8", ""));
		if(lvl >= 100) {
			return 100;
		}
		return Integer.valueOf(is.getItemMeta().getDisplayName().split(" ")[1].replace("]", "").replace("§8", "")) + 1;
	}
	
	public static ItemStack addPetLevel(ItemStack is) {
		if(is.getItemMeta().getDisplayName().contains("100")) {
			return is;
		}
		ItemMeta im = is.getItemMeta();
		String oldLvl = im.getDisplayName().split("] ")[0]+"]";
		
		String name = im.getDisplayName().split("]")[1];
		
		String newLvl = "§8[§7Lvl "+(Integer.valueOf(oldLvl.split(" ")[1].replace("]", "").replace("§8", ""))+1)+"§8]";
		im.setDisplayName(newLvl+name);
		is.setItemMeta(im);
		return is;
	}
	
	public static ItemStack replaceSingleStringInLoreLore(ItemStack is, String string_to_replace, String string_to_place) {
		ItemMeta im = is.getItemMeta();
		ArrayList<String> new_lore = new ArrayList<String>();
		if(im.getLore() == null) return is;
		for(String str : im.getLore()) {
			str = str.replace(string_to_replace, string_to_place);
			new_lore.add(str);
		}
		im.setLore(new_lore);
		is.setItemMeta(im);
		return is;
	}
	
	public static ItemStack addLore(ItemStack is, String start, String lore, String end) {
		ItemMeta im = is.getItemMeta();
		ArrayList<String> new_lore = new ArrayList<String>();
		if(im.getLore() != null) {
			for(String str : im.getLore()) {
				new_lore.add(str);
				if(str.equals(start)) {
					new_lore.add(lore);
					new_lore.add(end);
				}
			}
		} else {
			new_lore.add(start);
			new_lore.add(lore);
			new_lore.add(end);
		}
		im.setLore(new_lore);
		is.setItemMeta(im);
		return is;
	}
	
	public static ItemStack removeLore(ItemStack is, String start, String end) {
		ItemMeta im = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		ArrayList<String> new_lore = new ArrayList<String>();
		if(im.getLore() != null) {
			lore = (ArrayList<String>) im.getLore();
		} else
			return is;
		
		if(lore.contains(start) && lore.contains(end)) {
			for(String str : lore) {
					if(str.equals(start)) {
						new_lore.add(str);
					} else {
						if(str.equals(end)) {
							new_lore.add(end);
						} else {
							if(!new_lore.contains(start)) {
								new_lore.add(str);
							} else {
								if(!new_lore.contains(start) && !new_lore.contains(end)) {
									new_lore.add(str);
								} else {
									if(new_lore.contains(start) && new_lore.contains(end)) {
										new_lore.add(str);
									}
								}
						}
					}
				}
			}
		} else {
			new_lore = lore;
		}
		new_lore.remove(end);
		im.setLore(new_lore);
		is.setItemMeta(im);
		return is;
	}
}
