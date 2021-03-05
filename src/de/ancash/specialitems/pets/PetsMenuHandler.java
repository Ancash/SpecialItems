package de.ancash.specialitems.pets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.EulerAngle;

import de.ancash.specialitems.Files;
import de.ancash.specialitems.SpecialItems;
import de.tr7zw.nbtapi.NBTItem;

public class PetsMenuHandler implements Listener {
		
	@EventHandler
	public void onPlayerAddPet(PlayerInteractEvent e) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, FileNotFoundException, IOException, InvalidConfigurationException {
				
		Player p = e.getPlayer();
		if(p.getItemInHand() != null && p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasLore() && p.getItemInHand().getItemMeta().getLore().contains("               ")) {
			ItemStack is = p.getItemInHand().clone();
			if(is.getAmount() != 1) {
				p.getItemInHand().setAmount(1);
				return;
			}
			if(new NBTItem(is).getString("pet") == null) {
				return;
			}
			
			e.setCancelled(true);
			
			File playerData = new File("plugins/SpecialItems/playerData/"+p.getUniqueId().toString() + "/data.yml");
			FileConfiguration pD = YamlConfiguration.loadConfiguration(playerData);
			try {
				pD.load(playerData);
			} catch (IOException | InvalidConfigurationException e1) {
				e1.printStackTrace();
			}
			boolean addedPet = false;
			for(int i = 1; i < 29; i++) {
				if(pD.getItemStack("pets."+i) != null) {
					continue;
				} else {					
					String texture = PetMethods.getTexure(is);
					NBTItem nbtItem = new NBTItem(is);
					String type = nbtItem.getString("pet");
					String rarity = nbtItem.getString("rarity");
					double xp = nbtItem.getDouble("xp");
					
					SkullMeta sm = (SkullMeta) is.getItemMeta();
					sm.setOwner(p.getDisplayName());
					sm.setLore(null);
					is.setItemMeta(sm);
					
					nbtItem.setDouble("xp", xp);
					nbtItem.setString("pet", type);
					pD.set("pets."+i, is);
					pD.set(i+".texture", texture);
					pD.set(i+".type", type);
					pD.set(i+".rarity", rarity);
					xp = nbtItem.getInteger("xp");
					pD.set(i+".xp", xp);
					try {
						pD.save(playerData);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					addedPet = true;
					break;
				}
				
			}
			if(addedPet == true) {
				p.sendMessage("§aAdded"+is.getItemMeta().getDisplayName().split("] ")[1] +" §ato your Pets");
				p.getInventory().setItemInHand(null);
			} else {
				p.sendMessage("§cYou have too many pets!");
			}
		}
	}
	@SuppressWarnings("unused")
	@EventHandler
	public void onInvClick(InventoryClickEvent e) throws IOException, InvalidConfigurationException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		if(e.getAction().equals(InventoryAction.NOTHING) 
				|| e.getAction().equals(InventoryAction.DROP_ALL_CURSOR)
				|| e.getAction().equals(InventoryAction.DROP_ALL_SLOT)
				|| e.getAction().equals(InventoryAction.DROP_ONE_CURSOR)
				|| e.getAction().equals(InventoryAction.DROP_ONE_SLOT)) {
			e.setCancelled(true);
			return;
		}
		if(e.getClickedInventory() == null) {
			return;
		}
		if(e.getView().getTitle().equals("Pets")) {
			e.setCancelled(true);
			if(e.getSlot() == 49 && e.getAction().equals(InventoryAction.PICKUP_ALL)) {
				e.getWhoClicked().closeInventory();
				return;
			}
			if(!e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && !e.getCurrentItem().getType().equals(Material.BARRIER) && e.getCurrentItem() != null) {
				Player p = (Player) e.getWhoClicked();
				if(e.getSlot() == 50 ) {
					ItemStack is = e.getCurrentItem();
					if(is.getData().toString().contains("8")) {
						ItemStack convertPetToItem = new ItemStack(Material.INK_SACK,1 ,(byte)5);
						ItemMeta cPTI = convertPetToItem.getItemMeta();
						cPTI.setDisplayName("§cConvert Pet to Item");
						convertPetToItem.setItemMeta(cPTI);
						e.getInventory().setItem(50, convertPetToItem);
						return;
					}
					if(is.getData().toString().contains("5")) {
						ItemStack convertPetToItem = new ItemStack(Material.INK_SACK,1 ,(byte)8);
						ItemMeta cPTI = convertPetToItem.getItemMeta();
						cPTI.setDisplayName("§cConvert Pet to Item");
						convertPetToItem.setItemMeta(cPTI);
						e.getInventory().setItem(50, convertPetToItem);
						return;
					}
					e.setCancelled(true);
					return;
				}
				ItemStack is = null;
				if(e.getInventory().getItem(50).getData().toString().contains("5")) {
					ItemStack currentItem = e.getInventory().getItem(e.getSlot()).clone();
					File playerData = new File("plugins/SpecialItems/playerData/"+p.getUniqueId().toString() + "/data.yml");
					FileConfiguration pD = YamlConfiguration.loadConfiguration(playerData);
					
					String texture = "";
					String type = "";
					String rarity = "";
					double xp = 0;
					ArrayList<String> lore =new ArrayList<String>();
					
					int i = e.getSlot()-9;
					
					texture = pD.getString(i+".texture");
					xp = pD.getDouble(i+".xp");
					is = pD.getItemStack("pets."+i).clone();
					type = pD.getString(i+".type");
					rarity = pD.getString(i+".rarity");
					pD.set("pets."+i, "");
					pD.save(playerData);
					e.getInventory().setItem(e.getSlot(), null);
					for(String str : Files.petsConfig.getStringList("pets."+pD.getString(i+".type")+"."+pD.getString(i+".rarity")+".lore")) {
						lore.add(str);
					}		
					if(is == null) {
						return;	
					}
					SkullMeta sm = (SkullMeta) is.getItemMeta();
					sm.setOwner(p.getDisplayName());
					is.setItemMeta(sm);
					
					ItemMeta im = is.getItemMeta();
					im.setLore(lore);
					is.setItemMeta(im);
					
					is = PetMethods.preparePet(is, p);
					is = PetMethods.setTexture(is, texture);
					
					NBTItem nbtitem = new NBTItem(is);
					nbtitem.setString("pet", type);
					nbtitem.setString("rarity", rarity);
					nbtitem.setDouble("xp", xp);
					nbtitem.setString("texture", texture);
					
					p.getInventory().addItem(PetMethods.removeLore(currentItem, "               ", "              "));
					
					if(SpecialItems.equipedPets.containsKey(p.getUniqueId())) {
						SpecialItems.equipedPets.get(p.getUniqueId()).remove();
						SpecialItems.equipedPets.remove(p.getUniqueId());
						SpecialItems.petsName.get(p.getUniqueId()).remove();
						SpecialItems.petsName.remove(p.getUniqueId());
					}
					return;
				}
				if(e.getCurrentItem().getItemMeta().hasLore() && e.getCurrentItem().getItemMeta().getLore().contains("§cClick to despawn")) {
					ItemStack despawn = e.getCurrentItem().clone();
					e.setCurrentItem(despawn);
					SpecialItems.equipedPets.get(p.getUniqueId()).remove();
					SpecialItems.equipedPets.remove(p.getUniqueId());
					SpecialItems.petsName.get(p.getUniqueId()).remove();
					SpecialItems.petsName.remove(p.getUniqueId());
					return;
				}
				for(UUID uuid : SpecialItems.equipedPets.keySet()) {
					if(new NBTItem(e.getCurrentItem()).getString("uuid").equals(new NBTItem(SpecialItems.equipedPets.get(uuid).getItemInHand()).getString("uuid"))) {
						SpecialItems.equipedPets.get(p.getUniqueId()).remove();
						SpecialItems.equipedPets.remove(p.getUniqueId());
						SpecialItems.petsName.get(p.getUniqueId()).remove();
						SpecialItems.petsName.remove(p.getUniqueId());
						return;
					}
				}
				if(SpecialItems.equipedPets.containsKey(p.getUniqueId())) {
					SpecialItems.equipedPets.get(p.getUniqueId()).remove();
					SpecialItems.equipedPets.remove(p.getUniqueId());
					SpecialItems.petsName.get(p.getUniqueId()).remove();
					SpecialItems.petsName.remove(p.getUniqueId());
				}
				ItemStack petItem;
				if(is == null) {
					petItem = e.getCurrentItem();
				} else {
					petItem = is.clone();
				}
				if(!(new NBTItem(petItem).hasKey("pet"))) return;
				ArmorStand pet = (ArmorStand) p.getWorld().spawnEntity(p.getLocation().add(0, 1, 0), EntityType.ARMOR_STAND);
				String displayname = petItem.getItemMeta().getDisplayName().split("] ")[0].replace(" ", "").replace("Lvl", "Lv").replace("§7[", "§8[§7")+"§8]";
				displayname = displayname +String.valueOf(petItem.getItemMeta().getDisplayName().split("] ")[1].charAt(0)) + String.valueOf(petItem.getItemMeta().getDisplayName().split("] ")[1].charAt(1))
				+ " " + p.getDisplayName() + "'s ";
				for(int i = 3; i<petItem.getItemMeta().getDisplayName().replace("-", " ").split(" ").length; i++) {
					displayname = displayname + " " + StringUtils.capitalize(petItem.getItemMeta().getDisplayName().replace("-", " ").split(" ")[i]);
				}
				displayname = displayname.replace("§4", "").replace("§r", "");
				pet.setVisible(false);
				pet.setCustomName(displayname);
				pet.setCustomNameVisible(false);
				pet.setGravity(false);
				pet.setItemInHand(e.getCurrentItem());
				pet.setMarker(true);
				pet.setArms(true);
				EulerAngle ea = new EulerAngle(0, 0, 0);
				pet.setRightArmPose(ea);
				pet.getLocation().setYaw(pet.getLocation().getYaw()-100);
				
				ArmorStand petName = (ArmorStand) pet.getWorld().spawnEntity(pet.getLocation().add(0, 1, 0), EntityType.ARMOR_STAND);
				petName.setVisible(false);
				petName.setCustomName(" "+pet.getCustomName()+" ");
				petName.setCustomNameVisible(true);
				petName.setGravity(false);
				petName.setMarker(true);
				SpecialItems.petsName.put(p.getUniqueId(), petName);
				SpecialItems.equipedPets.put(p.getUniqueId(), pet);
				ItemStack despawn = e.getCurrentItem().clone();
				/*despawn = PetMethods.removeLore(despawn, "               ", "              ");
				despawn = PetMethods.addLore(despawn, "               ", "§cClick to despawn", "              ");*/
				e.setCurrentItem(despawn);
				Pet petInstance = new Pet();
				petInstance.setPlayer(p);
				petInstance.setPetAS(pet);
				petInstance.setNameTagAS(petName);
				petInstance.setLocs(new ArrayList<Location>());
				petInstance.setLoc(pet.getLocation());
				Pet.pets.put(p.getUniqueId(), petInstance);
			}
			return;
		}
	}
}
