package de.alpha.uhc.Listener;

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
import de.alpha.uhc.Registery;
import de.popokaka.alphalibary.nms.SimpleTitle;

public class InGameListener implements Listener {
	
	private Core pl;
	private Registery r;
	
	public InGameListener(Core c) {
		this.pl = c;
		this.r = pl.getRegistery();
	}

    private  String ntrack;
    private  String track;
    private  String trackteam;

    private  int size;

    public  void setNtrack(String ntrack) {
        this.ntrack = ntrack;
    }

    public  void setTrack(String track) {
    	this.track = track;
    }

    public  void setTrackteam(String trackteam) {
    	this.trackteam = trackteam;
    }

    public  int getSize() {
        return size;
    }

    public  void setSize(int size) {
    	this.size = size;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        if (!(GState.isState(GState.INGAME) || GState.isState(GState.GRACE) || GState.isState(GState.DEATHMATCH) || GState.isState(GState.PREDEATHMATCH) || GState.isState(GState.PREGAME)))
            return;
        if (pl.getSpecs().contains(e.getPlayer())) return;
        if (e.getFrom().getBlock().equals(e.getTo().getBlock())) return;
        r.getAScoreboard().updateInGameCenter(e.getPlayer());

    }

    @EventHandler
    public void onFirstHit(EntityDamageByEntityEvent e) {

        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Player)) return;
        if (GState.isState(GState.PREDEATHMATCH) || GState.isState(GState.PREGAME)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDMG(EntityDamageEvent e) {

        if (!(e.getEntity() instanceof Player)) return;
        if (GState.isState(GState.GRACE)) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {

        Player p = e.getPlayer();

        if (e.getMaterial().equals(Material.COMPASS)) {

            Player target = getNearest(p);
            if (target == null || pl.getSpecs().contains(target)) {
                p.sendMessage(pl.getPrefix() + ntrack);
                return;
            }

            if (r.getATeam().hasTeam(p) && r.getATeam().hasTeam(target)) {
                if (r.getATeam().hasSameTeam(p, target)) {
                    trackteam = trackteam.replace("[Player]", target.getDisplayName());

                    int blocks = (int) p.getLocation().distance(getNearest(p).getLocation());

                    trackteam = trackteam.replace("[distance]", Integer.toString(blocks));

                    p.sendMessage(pl.getPrefix() + trackteam);
                    SimpleTitle.sendTitle(p, " ", trackteam, 1, 2, 1);
                    p.setCompassTarget(getNearest(p).getLocation());

                    trackteam = r.getMessageFile().getMSGFile().getColorString("Compass.TeamPlayerInRange");
                    return;
                }
            }

            track = track.replace("[Player]", target.getDisplayName());

            int blocks = (int) p.getLocation().distance(getNearest(p).getLocation());

            track = track.replace("[distance]", Integer.toString(blocks));

            p.sendMessage(pl.getPrefix() + track);
            SimpleTitle.sendTitle(p, " ", track, 1, 2, 1);
            p.setCompassTarget(getNearest(p).getLocation());

            track = r.getMessageFile().getMSGFile().getColorString("Compass.PlayerInRange");
        }
    }

    private Player getNearest(Player p) {

        double distance = Double.MAX_VALUE;
        Player target = null;

        for (Entity entity : p.getNearbyEntities(size, size, size)) {
            if (entity instanceof Player) {
                if (!(pl.getSpecs().contains(entity))) {

                    double dis = p.getLocation().distance(entity.getLocation());

                    if (dis < distance) {
                        distance = dis;
                        target = (Player) entity;
                    }
                }
            }
        }
        return target;
    }

}
