package de.ancash.specialitems;

import java.util.ArrayList;
import java.util.TimerTask;

import org.bukkit.entity.Animals;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.WaterMob;
import org.bukkit.util.Vector;

import de.ancash.specialitems.listener.BowLaunchHit;


public class ATask extends TimerTask{

	@Override
	public void run() {
		for(int i = BowLaunchHit.arrows.size(); i > 0; i--) {
			Arrow a = BowLaunchHit.arrows.get(i-1);
			if(a.isDead() || a == null || a.getTicksLived() > 400) {
				BowLaunchHit.arrows.remove(a);
				continue;
			} 
			int aiming = 0;
			if(a.getCustomName() != null && a.getCustomName().split(" ").length == 2) {
				aiming = Integer.parseInt(a.getCustomName().split(" ")[0]);
			} else {
				if(a.getCustomName() != null) {
					aiming = Integer.parseInt(a.getCustomName());
				}
			}
			ArrayList<Entity> entities = (ArrayList<Entity>) a.getNearbyEntities(2*aiming, 2*aiming, 2*aiming);
			Entity closest_entity = null;
			double closest_dist = 0;
			for(Entity e : entities) {
				double distance = e.getLocation().distanceSquared(a.getLocation());
				if(!(e instanceof Player) && !(e instanceof ArmorStand) && !e.isDead()) {
					if(e instanceof Animals || e instanceof Monster || e.getType().equals(EntityType.ENDER_DRAGON) || e instanceof WaterMob) {
						if(closest_entity == null || distance < closest_dist) {
							closest_entity = e;
							closest_dist = distance;
						}
					}
				}
			}
			Vector v = a.getVelocity();
			if(closest_entity != null) {
				v = closest_entity.getLocation().add(0, 0.5, 0).toVector().subtract(a.getLocation().toVector());

				double vecX = v.getX();
				if(vecX < 0) {
					vecX = -v.getX();
				}
				double vecY = v.getY();
				if(vecY < 0) {
					vecY = -v.getY();
				}
				double vecZ = v.getZ();
				if(vecZ < 0) {
					vecZ = -v.getZ();
				}
				double totalVec = vecX+vecY+vecZ;
				a.setVelocity(v.multiply(totalVec/100+0.1));
			}
		}
	}

	

	
}
