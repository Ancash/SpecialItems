package de.ancash.specialitems.effects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import de.ancash.specialitems.SpecialItems;
import de.ancash.specialitems.pets.Pet;
import de.ancash.specialitems.utils.SendParticlesPacket;

public class Effects{
	
	public static void createParticlesForPet(Pet pet) {
		Location loc = getArmTip(pet.getPetItemAS());
		for(Player p : Bukkit.getOnlinePlayers()) {
            SendParticlesPacket.sendParticles(p, loc, true, 1, "ENCHANTMENT_TABLE");
		}
	}
	
	private static Location getArmTip(ArmorStand as) {
        // Gets shoulder location
        Location asl = as.getLocation().clone();
        asl.setYaw(asl.getYaw() + 90);
        Vector dir = asl.getDirection();
        asl.setX(asl.getX() + 5f / 16f * dir.getX());
        asl.setY(asl.getY() + 22f / 16f);
        asl.setZ(asl.getZ() + 5f / 16f * dir.getZ());
        // Get Hand Location
     
        EulerAngle ea = as.getRightArmPose();
        Vector armDir = getDirection(ea.getY(), ea.getX(), -ea.getZ());
        armDir = rotateAroundAxisY(armDir, Math.toRadians(asl.getYaw()-90f));
        asl.setX(asl.getX() + 10f / 20f * armDir.getX());
        asl.setY(asl.getY() + 10f / 16f * armDir.getY());
        asl.setZ(asl.getZ() + 10f / 20f * armDir.getZ());
        return asl;
    }

    public static Vector getDirection(Double yaw, Double pitch, Double roll) {
        Vector v = new Vector(0, -1, 0);
        v = rotateAroundAxisX(v, pitch);
        v = rotateAroundAxisY(v, yaw);
        v = rotateAroundAxisZ(v, roll);
        return v;
    }
 
    private static Vector rotateAroundAxisX(Vector v, double angle) {
        double y, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        y = v.getY() * cos - v.getZ() * sin;
        z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }

    private static Vector rotateAroundAxisY(Vector v, double angle) {
        angle = -angle;
        double x, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = v.getX() * cos + v.getZ() * sin;
        z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }

    private static Vector rotateAroundAxisZ(Vector v, double angle) {
        double x, y, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = v.getX() * cos - v.getY() * sin;
        y = v.getX() * sin + v.getY() * cos;
        return v.setX(x).setY(y);
    }
	
	public static void createTorpedo(final Player p) {
		new BukkitRunnable() {
			Location loc = p.getLocation().add(0,-2,0);
			Vector dir = loc.getDirection();
			double t = 0;
			
			public void run() {
				
				t += 1;
				double x = dir.getX() * t;
				double y = dir.getY() * t + 1.5;
				double z = dir.getZ() * t;
				loc.add(x, y, z);
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(p.getLocation().distance(loc) < 20) {
						SendParticlesPacket.sendParticles(p, loc, true, 1, "FIREWORKS_SPARK");
					}
				}
				for(Entity e : loc.getChunk().getEntities()) {
					if(e.getLocation().distance(loc) < 2.0) {
						if(!e.equals(p)) {
							e.setFireTicks(20*5);
						}
					}
				}
				if(t > 40) this.cancel();
				
			}
		}.runTaskTimer(SpecialItems.plugin, 0, 0);
	}
	
	public static void createBloodHelix(final Player p) {
		new BukkitRunnable() {
			
			double phi = 0;
			
			public void run() {
				
				double x,y,z;
				phi += Math.PI/16;
				Location loc = p.getLocation();
				for(double t = 0; t <= 2*Math.PI; t += Math.PI/16) {
					for(double i = 0; i <=1; i+=1) {
						x = 0.15*(2*Math.PI-t)*Math.cos(t + phi + i*Math.PI);
						y = 0.5*t;
						z = 0.15*(2*Math.PI-t)*Math.sin(t + phi + i*Math.PI);
						loc.add(x,y,z);
						Location p1 = loc.clone();
						loc.subtract(x, y, z);
												
						for(Player p : Bukkit.getOnlinePlayers()) {
							if(p.getLocation().distance(p1) < 20) {
								SendParticlesPacket.sendParticles(p, p1, true, 1, "REDSTONE");
							}
						}
					}
				}
				if(phi > 10*Math.PI) this.cancel();
			}
		}.runTaskTimer(SpecialItems.plugin, 0, 0);
	}
	
	public static void createSphere(Location loc) {
		
		new BukkitRunnable() {
			
			double phi = 0;
			
			public void run() {
				
				phi += Math.PI/10;
				for(double theta = 0; theta <= 2*Math.PI; theta = theta + Math.PI/32) {
					double r = 1.5;
					double x = r*Math.cos(theta)*Math.sin(phi);
					double y = r*Math.cos(phi) + 1.5;
					double z = r*Math.sin(theta)*Math.sin(phi);
					loc.add(x,y,z);
					Location p1 = loc.clone();
					loc.subtract(x, y, z);

					for(Player p : Bukkit.getOnlinePlayers()) {
						if(p.getLocation().distance(p1) < 20) {
							SendParticlesPacket.sendParticles(p, p1, true, 1, "FLAME");
						}
					}
					if(phi > 4*Math.PI) this.cancel();
				}
				
			}
			
			
		}.runTaskTimer(SpecialItems.plugin, 0, 0);
		
	}
	
	public static void createDampenedRadialWaves(final Location loc) {
		
		new BukkitRunnable() {
			
			double t = Math.PI/4;
			
			public void run() {
				
				t = t + 0.1*Math.PI;
				for (double theta = 0; theta <= 2*Math.PI; theta = theta + Math.PI/32){
					double x = t*Math.cos(theta);
					double y = 2*Math.exp(-0.1*t) * Math.sin(t) + 1.5;
					double z = t*Math.sin(theta);
					loc.add(x,y,z);
					Location p1 = loc.clone();
					loc.subtract(x, y, z);
					
					theta = theta + Math.PI/64;
					 
					x = t*Math.cos(theta);
					y = 2*Math.exp(-0.1*t) * Math.sin(t) + 1.5;
					z = t*Math.sin(theta);
					loc.add(x,y,z);
					Location p2 = loc.clone();
					loc.subtract(x, y, z);
					for(Player p : Bukkit.getOnlinePlayers()) {
						if(p.getLocation().distance(p1) < 20) {
							SendParticlesPacket.sendParticles(p, p1, true, 2, "FIREWORKS_SPARK");
						}
						if(p.getLocation().distance(p2) < 20) {
							SendParticlesPacket.sendParticles(p, p2, true, 1, "SPELL_WITCH");
						}
					}
					if(t > 20) this.cancel();
				}
				
			}
			
			
		}.runTaskTimer(SpecialItems.plugin, 0, 0);
		
	}
}
