package de.ancash.specialitems.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.util.Vector;

import de.ancash.specialitems.SpecialItems;
import de.tr7zw.nbtapi.NBTItem;

public class BowLaunchHit implements Listener {

	public ArrayList<UUID> grappling = new ArrayList<UUID>();
	
	public static ArrayList<Arrow> arrows = new ArrayList<Arrow>();
	
	public static ArrayList<Player> shooter = new ArrayList<Player>();
	public static HashMap<Player, Integer> tripleshot = new HashMap<Player, Integer>();	
	
	public Vector rotateVector(Vector vector, double whatAngle) {
        double cos = Math.cos(whatAngle);
        double sin = Math.sin(whatAngle);
        double x = vector.getX() * cos + vector.getZ() * sin;
        double z = vector.getX() * -sin + vector.getZ() * cos;
     
        return vector.setX(x).setZ(z);
    }
	
	@EventHandler
	public void onArrowLaunch(ProjectileLaunchEvent e) {
		if(e.getEntityType().equals(EntityType.ARROW)) {
			Arrow a = (Arrow) e.getEntity();
			if(a.getShooter() instanceof Player) {
				Player p = (Player) a.getShooter();
				a.setKnockbackStrength(0);
				if(a.getCustomName() != null && a.getCustomName().contains("runaan")) {
					return;
				}
				if(shooter.contains(p)) {
					if(tripleshot.containsKey(p) && tripleshot.get(p) > 0) {
						tripleshot.put(p, tripleshot.get(p)-1);
						return;
					} else {
						e.setCancelled(true);
						return;
					}
				}
				shooter.add(p);
				if(p.getItemInHand() != null && new NBTItem(p.getItemInHand()).hasKey("ability") && new NBTItem(p.getItemInHand()).getString("ability").equals("tripleshot")) {
					int aiming = 0;
					if(SpecialItems.ench_aiming != null) {
						aiming = p.getItemInHand().getEnchantmentLevel(SpecialItems.ench_aiming);
					}
					tripleshot.put(p, 2);
					Arrow a1 = p.launchProjectile(Arrow.class);
					
					a1.setCustomName(aiming+" runaan");
					a1.setVelocity(rotateVector(a.getVelocity(), 50));
					
					Arrow a2 = p.launchProjectile(Arrow.class);
					
					a2.setCustomName(aiming+" runaan");
					a2.setVelocity(rotateVector(a.getVelocity(), -50));
					
						

					arrows.add(a1);
					arrows.add(a2);
					

							
					
				}
				if(p.getItemInHand().hasItemMeta() && SpecialItems.ench_aiming != null && p.getItemInHand().getItemMeta().hasEnchant(SpecialItems.ench_aiming)) {
					a.setCustomName(""+p.getItemInHand().getItemMeta().getEnchantLevel(SpecialItems.ench_aiming));
					arrows.add(a);
				}
				removeShooter(p);
			}
		}
	}
	
	private void removeShooter(Player p) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(SpecialItems.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				shooter.remove(p);
			}
		}, 10);
	}

	@EventHandler
	public void onArrowHit(ProjectileHitEvent e) {
		/*if(e.getEntity().getShooter() instanceof Player) {
			Player p = (Player) e.getEntity().getShooter();
			switch (new Random().nextInt(3)) {
			case 0:
				Effects.createBloodHelix(p);
				break;
			case 1:
				Effects.createDampenedRadialWaves(e.getEntity().getLocation());;
				break;
			case 2:
				Effects.createSphere(e.getEntity().getLocation());;
				break;
			default:
				break;
			}
		}*/
		if(e.getEntity() instanceof Arrow) {
			e.getEntity().remove();
		}
	}
	
	// displayname: (Arrow type(normal/explosive)) + strength + damage + crit_chance + crit_damage
	//                            0                      1         2          3             4
}
