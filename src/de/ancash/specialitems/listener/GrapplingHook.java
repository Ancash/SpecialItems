package de.ancash.specialitems.listener;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import de.ancash.specialitems.Files;
import de.ancash.specialitems.SpecialItems;
import net.md_5.bungee.api.ChatColor;

public class GrapplingHook implements Listener,CommandExecutor{

	public static ArrayList<GrapplingHook> ghs = new ArrayList<GrapplingHook>();
	public static ArrayList<UUID> player = new ArrayList<UUID>();
	
	private UUID shooter;
	private FishHook fh;
	private boolean isFishing;
	private Location loc;
	
	public UUID getShooter() {
		return shooter;
	}
	public FishHook getFishHook() {
		return fh;
	}
	public Location getLocation() {
		return loc;
	}
	public boolean isFishing() {
		return isFishing;
	}
	public void setFishHook(FishHook fh) {
		this.fh = fh;
	}
	public void setShooter(UUID uuid) {
		this.shooter = uuid;
	}
	public void setIsFishing(boolean fishing) {
		isFishing = fishing;
	}
	public void setLocation(Location loc) {
		this.loc = loc;
	}
	
	@EventHandler
	public void onFishHookLaunch(ProjectileLaunchEvent e) {
		
		
		if(e.getEntityType().equals(EntityType.FISHING_HOOK)) {
			
			FishHook fh = (FishHook) e.getEntity();
			
			if(!(fh.getShooter() instanceof Player)) {
				return;
			}
			
			Player p = (Player) fh.getShooter();
				
			if(p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().getDisplayName().equals("§aGrappling Hook")) {
				GrapplingHook gh = new GrapplingHook();
				gh.setShooter(p.getUniqueId());
				gh.setFishHook(fh);
				gh.setLocation(fh.getLocation());
				
				ghs.add(gh);
			}
		}
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerInteract(PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
		if(p.getItemInHand() != null && p.getItemInHand().getType().equals(Material.FISHING_ROD) && p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().getDisplayName().equals("§aGrappling Hook")) {
			
			if(e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
				for(GrapplingHook gh : ghs) {
					//.add(new Vector(0, -(gh.getFishHook().getLocation().toVector().getY()/15)*9.5, 0)
					if(gh.getShooter().equals(p.getUniqueId()) && !player.contains(gh.getShooter()) && !gh.getFishHook().isDead()) {
						double y = gh.getFishHook().getLocation().toVector().subtract(p.getLocation().toVector()).getY();
						double yvec = gh.getFishHook().getLocation().toVector().getY();
						System.out.println(y);
						System.out.println(yvec);
						p.setVelocity(gh.getLocation().toVector().add(new Vector(0, -(yvec/5), 0)).subtract(p.getLocation().toVector().add(new Vector(0, p.getLocation().toVector().getY()/7.5, 0))).multiply(-1));
						//p.setVelocity(p.getLocation().toVector().subtract(gh.getFishHook().getLocation().toVector()).multiply(-0.2));
						//p.setVelocity(gh.getLocation().toVector().add(new Vector(0, -gh.getLocation().toVector().getY()/8, 0)).subtract(p.getLocation().toVector()).add(new Vector(0, -(gh.getLocation().toVector().getY()/15)*10+3, 0)).multiply((y*2)/(y*3.5)));
						//p.setVelocity(gh.getFishHook().getLocation().toVector().add(new Vector(0, -(gh.getFishHook().getLocation().toVector().getY()/15)*9.5, 0)).subtract(p.getLocation().toVector()).multiply(1));
						ghs.remove(gh);
						player.add(gh.getShooter());
						Bukkit.getScheduler().scheduleAsyncDelayedTask(SpecialItems.plugin, new Runnable() {
							
							@Override
							public void run() {

								player.remove(gh.getShooter());
								
							}
						}, 40L);
						break;
						
					}
				}
				
			}
			
		}
		
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if(p.hasPermission(Files.cfg.getString("commands.grapplinghook.permission"))) {
				if(args.length != 0) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.grapplinghook.usage")));
					return true;
				}
				if(p.getInventory().firstEmpty() != -1) {
					
					//p.getInventory().addItem(SpecialItems.grapplingHook);
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("commands.grapplinghook.grapplinghook")));
					return true;
					
				} else
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("inventory_full")));
					return true;
			} else
				p.sendMessage(ChatColor.translateAlternateColorCodes('&',Files.cfg.getString("no_permission")));
				return true;
		} else
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Files.cfg.getString("no_player")));
		return false;
	}
	
}
