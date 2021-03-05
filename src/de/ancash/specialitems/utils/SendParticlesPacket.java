package de.ancash.specialitems.utils;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SendParticlesPacket {

	public static void sendParticles(Player player, Location loc, boolean bol, int am, String part) {
		try {
			Class<?> clazz = NMS.getNMSClass("PacketPlayOutWorldParticles");
			Object cc = clazz.newInstance();
			
			Class<?> enumPparticle = NMS.getNMSClass("EnumParticle");
			
			Object particle = null;
			
			for(Object o : enumPparticle.getEnumConstants()) {
				
				if(o.toString().equals(part.toUpperCase())) {
					particle = o;
					break;
				}
				
			}
			if(particle != null) {
				Reflections.setPrivateField(cc.getClass(), "a", cc, particle);
			} else {
				System.out.println("Something went wrong while sending packets: " + part);
				return;
			}
			Reflections.setPrivateField(cc.getClass(), "j", cc, bol);
			Reflections.setPrivateField(cc.getClass(), "b", cc, (float) loc.getX());
			Reflections.setPrivateField(cc.getClass(), "c", cc, (float) loc.getY());
			Reflections.setPrivateField(cc.getClass(), "d", cc, (float) loc.getZ());
			Reflections.setPrivateField(cc.getClass(), "e", cc, (float) 0);
			Reflections.setPrivateField(cc.getClass(), "f", cc, (float) 0);
			Reflections.setPrivateField(cc.getClass(), "g", cc, (float) 0);
			Reflections.setPrivateField(cc.getClass(), "h", cc, (float) 0);
			Reflections.setPrivateField(cc.getClass(), "i", cc, am);
			
			sendPacket(player, cc);
			
		} catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException e3) {
			e3.printStackTrace();
		}
	}
	
	private static void sendPacket(Player player, Object packet) {
		
		try {	        
	        Object entityPlayer= player.getClass().getMethod("getHandle").invoke(player);
	        Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);

	        playerConnection.getClass().getMethod("sendPacket", NMS.getNMSClass("Packet")).invoke(playerConnection, packet);
		} catch(NullPointerException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		
	}
}
