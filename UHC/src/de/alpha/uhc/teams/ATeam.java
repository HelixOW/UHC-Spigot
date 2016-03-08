package de.alpha.uhc.teams;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.alpha.uhc.Core;
import de.alpha.uhc.files.MessageFileManager;
import de.alpha.uhc.files.TeamFile;

public class ATeam implements Listener {
	
	public static String chosen;
	public static String noExist;
	public static String allTeams;
	
	public static ArrayList<String> teamNames = new ArrayList<String>();
	public static ArrayList<String> teamColors = new ArrayList<String>();
	
	private static HashMap<Player, String> teams = new HashMap<Player, String>();
	
	public static void addPlayerToTeam(Player p, String teamToPut) {
		if(teamNames.contains(teamToPut)) {
			teams.put(p, teamToPut);
			chosen = chosen.replace("[team]", getTeamColor(teamToPut)+teamToPut);
			p.sendMessage(Core.getPrefix() + chosen);
			p.setDisplayName(getTeamColor(teamToPut) + p.getName());
			p.setPlayerListName(getTeamColor(teamToPut) + p.getName());
			chosen = MessageFileManager.getMSGFile().getColorString("Teams.chosen");
		} else {
			String a = noExist.replace("[team]", teamToPut);
			String b = allTeams.replace("[teams]", ""+teamNames);
			p.sendMessage(Core.getPrefix() + a + "\n       " + b);
		}
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		
		if(!(e.getEntity() instanceof Player && e.getDamager() instanceof Player)) return;
		
		Player p = (Player) e.getEntity();
		Player other = (Player) e.getDamager();
		
		if(hasSameTeam(p, other)) {
			e.setCancelled(true);
		}
	}
	
	public static ChatColor getTeamColor(String team) {
		try {
			return ChatColor.valueOf(TeamFile.getTeamColorAsString(team));
		} catch (IllegalArgumentException e) {
			Bukkit.getConsoleSender().sendMessage(Core.getPrefix() + "§cThe Team §4" + team + " §cis invalid.");
		}
		return ChatColor.RESET;
	}
	
	public static boolean hasTeam(Player p) {
		if(teams.containsKey(p)) {
			return true;
		}
		return false;
	}
	
	public static String getPlayerTeam(Player p) {
		if(teams.containsKey(p)) {
			return teams.get(p);
		}
		return "";
	}
	
	public static boolean hasSameTeam(Player p, Player other) {
		if(teams.containsKey(p) && teams.containsKey(other)) {
			if(getPlayerTeam(p).equals(getPlayerTeam(other))) {
				return true;
			}
		}
		return false;
	}
}
