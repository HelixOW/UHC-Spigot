package de.alpha.uhc.utils;

import de.alpha.uhc.Core;
import de.alpha.uhc.files.SpawnFileManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class Regions implements Listener {

    private static ArrayList<Cuboid> regions = new ArrayList<>();
    private static HashMap<Player, Location> pos1 = new HashMap<>();
    private static HashMap<Player, Location> pos2 = new HashMap<>();

    private static String material;
    private static boolean lobby;

    public static String getMaterial() {
        return material;
    }

    public static void setMaterial(String material) {
        Regions.material = material;
    }

    public static boolean isLobby() {
        return lobby;
    }

    public static void setLobby(boolean lobby) {
        Regions.lobby = lobby;
    }

    public static void addRegion(Cuboid toAdd) {
        regions.add(toAdd);
    }

    public static boolean getDefined(Player p) {
        return pos1.containsKey(p) && pos2.containsKey(p);
    }

    public static Location getPos1(Player p) {
        if (pos1.containsKey(p)) {
            return pos1.get(p);
        }
        return null;
    }

    public static Location getPos2(Player p) {
        if (pos2.containsKey(p)) {
            return pos2.get(p);
        }
        return null;
    }

    public static boolean isInRegion(Location loc) {

        if (!(SpawnFileManager.getSpawnFile().isConfigurationSection("Lobbyregion"))) return true;

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

        if (p.getInventory().getItemInMainHand() == null) return;//TODO: multi
        if (!(p.getInventory().getItemInMainHand().getType().equals(m))) return;//TODO: multi
        if (!(p.hasPermission("uhc.admin"))) return;
        if (!lobby) return;

        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            e.setCancelled(true);

            pos1.put(p, e.getClickedBlock().getLocation());
            p.sendMessage(Core.getPrefix() + "§7The §afirst Lobbypoint §7has been set. [1/2]");

        } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            e.setCancelled(true);

            pos2.put(p, e.getClickedBlock().getLocation());
            p.sendMessage(Core.getPrefix() + "§7The §asecond Lobbypoint §7has been set. [2/2]");

        }

        if (pos1.containsKey(p) && pos2.containsKey(p)) {
            p.sendMessage(Core.getPrefix() + "§7You can now create the lobby with /uhc createLobby");

        }


    }
}
