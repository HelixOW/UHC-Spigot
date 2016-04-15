package de.alpha.uhc.Listener;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.aclasses.AScoreboard;
import de.alpha.uhc.aclasses.ATeam;
import de.alpha.uhc.files.MessageFileManager;
import de.popokaka.alphalibary.nms.SimpleTitle;

public class InGameListener implements Listener {
	
	private static String ntrack;
	private static String track;
	private static String trackteam;
	
	private static int size;
	
	
	private ArrayList<Player> ig = new ArrayList<Player>();
	
	public static  String getNtrack() {
		return ntrack;
	}

	public static  void setNtrack(String ntrack) {
		InGameListener.ntrack = ntrack;
	}

	public static  String getTrack() {
		return track;
	}

	public static  void setTrack(String track) {
		InGameListener.track = track;
	}

	public static  String getTrackteam() {
		return trackteam;
	}

	public static  void setTrackteam(String trackteam) {
		InGameListener.trackteam = trackteam;
	}

	public static  int getSize() {
		return size;
	}

	public static  void setSize(int size) {
		InGameListener.size = size;
	}

	public  ArrayList<Player> getIg() {
		return ig;
	}

	public  void setIg(ArrayList<Player> ig) {
		this.ig = ig;
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		
		if(!(GState.isState(GState.INGAME) || GState.isState(GState.GRACE) || GState.isState(GState.DEATHMATCH) || GState.isState(GState.PREDEATHMATCH) || GState.isState(GState.PREGAME))) return;
		if(Core.getSpecs().contains(e.getPlayer())) return;
		AScoreboard.updateInGameCenter(e.getPlayer());
		
	}
	
	@EventHandler
	public void onFirstHit(EntityDamageByEntityEvent e) {
		
		if(!(e.getEntity() instanceof Player)) return;
		if(!(e.getDamager() instanceof Player)) return;
		if(GState.isState(GState.PREDEATHMATCH) || GState.isState(GState.PREGAME)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDMG(EntityDamageEvent e) {
		
		if(!(e.getEntity() instanceof Player)) return;
		if(GState.isState(GState.GRACE)) {
			e.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
		
		if(e.getMaterial().equals(Material.COMPASS)) {
			
			Player target = getNearest(p);
			if(target == null || Core.getSpecs().contains(target)) {
				p.sendMessage(Core.getPrefix() + ntrack);
				return;
			}
			
			if(ATeam.hasTeam(p) == true && ATeam.hasTeam(target) == true) {
				if(ATeam.hasSameTeam(p, target)) {
					trackteam = trackteam.replace("[Player]", target.getDisplayName());
					
					int blocks = (int) p.getLocation().distance(getNearest(p).getLocation());
							
					trackteam = trackteam.replace("[distance]", Integer.toString(blocks));
							
					p.sendMessage(Core.getPrefix() + trackteam);
					SimpleTitle.sendTitle(p, " ", trackteam, 1, 2, 1);
					p.setCompassTarget(getNearest(p).getLocation());
						
					trackteam = MessageFileManager.getMSGFile().getColorString("Compass.TeamPlayerInRange");
					return;
				}
			}
			
				track = track.replace("[Player]", target.getDisplayName());
						
				int blocks = (int) p.getLocation().distance(getNearest(p).getLocation());
						
				track = track.replace("[distance]", Integer.toString(blocks));
						
				p.sendMessage(Core.getPrefix() + track);
				SimpleTitle.sendTitle(p, " ", track, 1, 2, 1);
				p.setCompassTarget(getNearest(p).getLocation());
					
				track = MessageFileManager.getMSGFile().getColorString("Compass.PlayerInRange");
		}
	}
	
	private Player getNearest(Player p) {
		
		double distance = Double.MAX_VALUE;
		Player target = null;
			
		for(Entity entity : p.getNearbyEntities(size, size, size)) {
			if(entity instanceof Player) {
				if(!(Core.getSpecs().contains(entity))) {
					
					double dis = p.getLocation().distance(entity.getLocation());
						
					if(dis < distance) {
						distance = dis;
						target = (Player) entity;
					}
				}
			}
		}
		return target;
	}
	
}
