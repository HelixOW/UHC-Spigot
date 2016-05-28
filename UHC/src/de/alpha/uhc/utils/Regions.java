package de.alpha.uhc.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.alpha.uhc.Core;
import de.alpha.uhc.Registery;

public class Regions implements Listener {
	
	private Core pl;
	private Registery r;
	
	public Regions(Core c) {
		this.pl = c;
		this.r = pl.getRegistery();
	}

    private  ArrayList<Cuboid> regions = new ArrayList<>();
    private  HashMap<Player, Location> pos1 = new HashMap<>();
    private  HashMap<Player, Location> pos2 = new HashMap<>();

    private  String material;
    private  boolean lobby;

    public  String getMaterial() {
        return material;
    }

    public  void setMaterial(String material) {
        this.material = material;
    }

    public  boolean isLobby() {
        return lobby;
    }

    public  void setLobby(boolean lobby) {
        this.lobby = lobby;
    }

    public  void addRegion(Cuboid toAdd) {
        regions.add(toAdd);
    }

    public  boolean getDefined(Player p) {
        return pos1.containsKey(p) && pos2.containsKey(p);
    }

    public  Location getPos1(Player p) {
        if (pos1.containsKey(p)) {
            return pos1.get(p);
        }
        return null;
    }

    public  Location getPos2(Player p) {
        if (pos2.containsKey(p)) {
            return pos2.get(p);
        }
        return null;
    }

    public  boolean isInRegion(Location loc) {

        if (!(r.getSpawnFileManager().getSpawnFile().isConfigurationSection("Lobbyregion"))) return true;

        for (Cuboid c : regions) {

            if (c.contains(loc)) {
                return true;
            }

        }
        return false;

    }

    @EventHandler
    public void onDefine(PlayerInteractEvent e) {

        Player p = e.getPlayer();

        Material m = Material.getMaterial(material.toUpperCase());

        if (p.getInventory().getItemInMainHand() == null) return;
        if (!(p.getInventory().getItemInMainHand().getType().equals(m))) return;
        if (!(p.hasPermission("uhc.admin"))) return;
        if (!lobby) return;

        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            e.setCancelled(true);

            pos1.put(p, e.getClickedBlock().getLocation());
            p.sendMessage(pl.getPrefix() + "§7The §afirst Lobbypoint §7has been set. [1/2]");

        } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            e.setCancelled(true);

            pos2.put(p, e.getClickedBlock().getLocation());
            p.sendMessage(pl.getPrefix() + "§7The §asecond Lobbypoint §7has been set. [2/2]");

        }

        if (pos1.containsKey(p) && pos2.containsKey(p)) {
            p.sendMessage(pl.getPrefix() + "§7You can now create the lobby with /uhc createLobby");

        }


    }
}
