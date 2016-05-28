package de.alpha.uhc.aclasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.Registery;
import de.popokaka.alphalibary.item.ItemBuilder;

public class ATeam implements Listener {
	
	private Core pl;
	private Registery r;
	
	public ATeam(Core c) {
		this.pl = c;
		this.r = pl.getRegistery();
	}

    private  String chosen;
    private  String noExist;
    private  String allTeams;
    private  String materialName;
    private  String blockName;
    private  String title;
    private  String full;
    private  final ArrayList<String> teamNames = new ArrayList<>();
    private  final ArrayList<String> teamColors = new ArrayList<>();
    private  final HashMap<String, Integer> teamMax = new HashMap<>();
    private  final HashMap<String, Integer> teamC = new HashMap<>();
    private  final HashMap<Player, String> teams = new HashMap<>();

    public  ArrayList<String> getTeamNames() {
        return teamNames;
    }

    public  ArrayList<String> getTeamColors() {
        return teamColors;
    }

    public  HashMap<String, Integer> getTeamMax() {
        return teamMax;
    }

    public  void setChosen(String chosen) {
        this.chosen = chosen;
    }

    public  String getNoExist() {
        return noExist;
    }

    public  void setNoExist(String noExist) {
    	this.noExist = noExist;
    }

    public  String getAllTeams() {
        return allTeams;
    }

    public  void setAllTeams(String allTeams) {
    	this.allTeams = allTeams;
    }

    public  void setMaterialName(String materialName) {
    	this.materialName = materialName;
    }

    public  void setBlockName(String blockName) {
    	this.blockName = blockName;
    }

    public  void setTitle(String title) {
    	this.title = title;
    }

    public  void setFull(String full) {
    	this.full = full;
    }

    public  void removePlayerFromTeam(Player p) {
        if (teamC.containsKey(getPlayerTeam(p))) {
            teamC.put(getPlayerTeam(p), teamC.get(getPlayerTeam(p)) - 1);
        }
        if (teams.containsKey(p)) {
            teams.remove(p);
        }
    }

    public  void addPlayerToTeam(Player p, String teamToPut) {
        if (teamNames.contains(teamToPut)) {
            if (isFull(teamToPut)) {
                full = full.replace("[team]", getTeamColor(teamToPut) + teamToPut);
                p.sendMessage(Core.getInstance().getPrefix() + full);
                full = r.getMessageFile().getMSGFile().getColorString("Teams.full");
            } else {
                if (teamC.containsKey(teamToPut)) {
                    if (!(teams.containsKey(p))) {
                        teamC.put(teamToPut, teamC.get(teamToPut) + 1);
                    }
                } else {
                    teamC.put(teamToPut, 1);
                    if (teamC.get(teamToPut) > teamMax.get(teamToPut)) {
                        teamC.put(teamToPut, teamC.get(teamToPut) - 1);
                        full = full.replace("[team]", getTeamColor(teamToPut) + teamToPut);
                        p.sendMessage(Core.getInstance().getPrefix() + full);
                        full = r.getMessageFile().getMSGFile().getColorString("Teams.full");
                        return;
                    }
                }

                teams.put(p, teamToPut);
                chosen = chosen.replace("[team]", getTeamColor(teamToPut) + teamToPut);
                p.sendMessage(Core.getInstance().getPrefix() + chosen);
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 10, 0);
                p.spigot().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 0, 0, 5, 5, 5, 50, 100, 5);
                p.setDisplayName(getTeamColor(teamToPut) + p.getName());
                p.setPlayerListName(getTeamColor(teamToPut) + p.getName());
                r.getAScoreboard().updateLobbyTeam(p);
                chosen = r.getMessageFile().getMSGFile().getColorString("Teams.chosen");
            }
        } else {
            String a = noExist.replace("[team]", teamToPut);
            String b = allTeams.replace("[teams]", "" + teamNames);
            p.sendMessage(Core.getInstance().getPrefix() + a + "\n       " + b);
        }
    }

    public  ChatColor getTeamColor(String team) {
        try {
            return ChatColor.valueOf(r.getTeamFile().getTeamColorAsString(team));
        } catch (IllegalArgumentException e) {
            Bukkit.getConsoleSender().sendMessage(Core.getInstance().getPrefix() + "§cThe Team §4" + team + " §cis invalid.");
        }
        return ChatColor.RESET;
    }

    public  Color getTeamItemColor(String team) {
        if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("orange")) {
            return Color.ORANGE;
        }
        if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("light_purple")) {
            return Color.FUCHSIA;
        }
        if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("aqua")) {
            return Color.AQUA;
        }
        if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("yellow")) {
            return Color.YELLOW;
        }
        if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("green")) {
            return Color.LIME;
        }
        if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("red")) {
            return Color.MAROON;
        }
        if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("dark_grey")) {
            return Color.GRAY;
        }
        if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("grey")) {
            return Color.SILVER;
        }
        if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("dark_aqua")) {
            return Color.NAVY;
        }
        if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("dark_purple")) {
            return Color.PURPLE;
        }
        if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("blue")) {
            return Color.BLUE;
        }
        if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("dark_green")) {
            return Color.OLIVE;
        }
        if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("dark_red")) {
            return Color.RED;
        }
        if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("black")) {
            return Color.BLACK;
        }

        return Color.WHITE;
    }

    private  int getTeamColorAsInteger(String team) {
        try {
            if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("orange")) {
                return 1;
            }
            if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("light_purple")) {
                return 2;
            }
            if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("aqua")) {
                return 3;
            }
            if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("yellow")) {
                return 4;
            }
            if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("green")) {
                return 5;
            }
            if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("red")) {
                return 6;
            }
            if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("dark_grey")) {
                return 7;
            }
            if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("grey")) {
                return 8;
            }
            if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("dark_aqua")) {
                return 9;
            }
            if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("dark_purple")) {
                return 10;
            }
            if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("blue")) {
                return 11;
            }
            if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("dark_green")) {
                return 13;
            }
            if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("dark_red")) {
                return 14;
            }
            if (r.getTeamFile().getTeamColorAsString(team).equalsIgnoreCase("black")) {
                return 15;
            }

        } catch (IllegalArgumentException e) {
            Bukkit.getConsoleSender().sendMessage(Core.getInstance().getPrefix() + "§cThe Team §4" + team + " §cis invalid.");
        }

        return 12;
    }

    public  boolean hasTeam(Player p) {
        return teams.containsKey(p);
    }

    public  String getPlayerTeam(Player p) {
        if (teams.containsKey(p)) {
            return teams.get(p);
        }
        return "";
    }

    private  boolean isFull(String team) {
        if (teamC.containsKey(team)) {
            if (Objects.equals(teamC.get(team), teamMax.get(team))) {
                return true;
            }
        }
        return false;
    }

    public  boolean hasSameTeam(Player p, Player other) {
        if (teams.containsKey(p) && teams.containsKey(other)) {
            if (getPlayerTeam(p).equals(getPlayerTeam(other))) {
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {

        if (!(e.getEntity() instanceof Player && e.getDamager() instanceof Player)) return;

        Player p = (Player) e.getEntity();
        Player other = (Player) e.getDamager();

        if (hasSameTeam(p, other)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDestroy(EntityDamageByEntityEvent e) {

        if (e.getEntity() instanceof ArmorStand) e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        if (!(GState.isState(GState.LOBBY))) return;

        Inventory teamInv;
        Player p = e.getPlayer();
        Material m = p.getInventory().getItemInMainHand().getType();
        Material toCompare = Material.getMaterial(materialName.toUpperCase());

        if (m.equals(toCompare)) {

            teamInv = Bukkit.createInventory(null, 36, title);

            for (String name : teamNames) {

                ItemStack item = new ItemBuilder(Material.getMaterial(blockName.toUpperCase()))
                        .setName(getTeamColor(name) + name)
                        .setDamage((short) getTeamColorAsInteger(name))
                        .build();


                teamInv.addItem(item);
            }
            p.openInventory(teamInv);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (!(GState.isState(GState.LOBBY))) return;
        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getTitle() == null) return;
        if (!(e.getClickedInventory().getTitle().equals(title))) return;
        if (!(e.getWhoClicked() instanceof Player)) return;
        if (!(e.getCurrentItem().hasItemMeta())) return;
        if (e.getCurrentItem().getItemMeta().getDisplayName() == null) return;

        Player p = (Player) e.getWhoClicked();
        e.setCancelled(true);

        if (e.getCurrentItem().getType().equals(Material.getMaterial(blockName.toUpperCase()))) {

            int dura = e.getCurrentItem().getDurability();

            for (String names : teamNames) {

                if (dura == getTeamColorAsInteger(names)) {
                    addPlayerToTeam(p, names);
                    p.closeInventory();
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onTeamJoin(PlayerInteractAtEntityEvent e) {
        if (!(e.getRightClicked() instanceof ArmorStand)) return;
        if (!(GState.isState(GState.LOBBY))) return;
        ArmorStand as = (ArmorStand) e.getRightClicked();
        if (!(as.isCustomNameVisible())) return;
        String name = "";
        for (String names : r.getTeamFile().getTeamFile().getConfigurationSection("Teams").getKeys(false)) {
            if (ChatColor.stripColor(as.getCustomName()).equalsIgnoreCase(names)) {
                name = names;
            }
        }

        e.setCancelled(true);
        addPlayerToTeam(e.getPlayer(), name);
    }
}
