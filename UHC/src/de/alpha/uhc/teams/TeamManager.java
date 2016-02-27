package de.alpha.uhc.teams;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;

public class TeamManager {

	private static ArrayList<Team> allTeams = new ArrayList<Team>();
	private static HashMap<Player, Team> playerToTeam = new HashMap<Player, Team>();
	
	/**
	 * 
	 * @param t The Team which should be added.
	 */
	public static void addTeam(Team t) {
		if(!allTeams.contains(t)) {
			allTeams.add(t);
		}
	}
	
	/**
	 * 
	 * @param name The String which equals a Teamname.
	 * @return Returns the Team with the specified Teamname name. If no team has this Teamname it returns null.
	 */
	public static Team getTeam(String name) {
		for(Team t : allTeams) {
			if(t.getName().equalsIgnoreCase(name)) {
				return t;
			}
			if(ChatColor.stripColor(t.getName()).equalsIgnoreCase(name)) {
				return t;
			}
		}
		return null;
	}
	
	/**
	 * @return Returns all Teams in a ArrayList.
	 */
	public static ArrayList<Team> getAllTeams() {
		return allTeams;
	}
	
	/**
	 * 
	 * @param p The Player who should be added to the Team t.
	 * @param t The Team where the Player should be added to.
	 */
	public static void addPlayerToTeam(Player p , Team t) {
		if(playerToTeam.containsKey(p)) {
			Team lastTeam = playerToTeam.get(p);
			lastTeam.removePlayer(p);
			p.setDisplayName("§7"+p.getName());
		}
		playerToTeam.put(p, t);
		t.addPlayer(p);
		p.setDisplayName(ChatColor.valueOf(t.getTeamColor().name())+p.getName());
		
	}
	
	/**
	 * 
	 * @param t The Team which should be proven if there is a space left.
	 * @return Returns true if the Team t has space for a Player. Otherwise it returns false.
	 */
	public static boolean hasSpaceForPlayer(Team t) {
		if(t.getAllPlayers().size() >= t.getMaxPlayerSize()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param p The Player who should removed from his last Team.
	 */
	public static void removePlayer(Player p) {
		if(playerToTeam.containsKey(p)) {
			Team lastTeam = playerToTeam.get(p);
			lastTeam.removePlayer(p);
			p.setDisplayName("§7"+p.getName());
		}
	}
	
	/**
	 * 
	 * @param p The Player who should be proven if he has a team.
	 * @return Returns true if the Player has a team. Otherwise it returns false.
	 */
	public static boolean hasTeam(Player p) {
		return playerToTeam.containsKey(p);
	}
	
	/**
	 * 
	 * @param p The Player whose Team should be got.
	 * @return Returs the Team from the Player. If the Team is null or if the Player has no team it returns null.
	 */
	public static Team getTeam(Player p) {
		if(playerToTeam.containsKey(p)) {
			return playerToTeam.get(p);
		}else {
			return null;
		}
	}
	
	
	
	/**
	 * 
	 * @param color The DyeColor which equals the Teams DyeColor
	 * @return Returns the Team with the specified DyeColor color. If no Team has this DyeColor it returns null.
	 */
	public static Team getTeamByColor(DyeColor color) {
		for(Team t : allTeams) {
			if(t.getTeamColor().equals(color)) {
				return t;
			}
		}
		return null;
	}
}
