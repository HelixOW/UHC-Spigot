package de.alpha.uhc.teams;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;

public class Team {

	private ArrayList<Player> allPlayer = new ArrayList<Player>();
	private String name;
	private org.bukkit.scoreboard.Team sTeam = null;
	private int maxSize;
	private DyeColor teamcolor;
	
	public Team(String name , int maxSize, DyeColor teamcolor) {
		//sTeam == ScoreboardTeam
		sTeam = Bukkit.getScoreboardManager().getNewScoreboard().registerNewTeam(name);
		sTeam.setPrefix("" + ChatColor.valueOf(teamcolor.name()));
		this.name = name;
		this.teamcolor = teamcolor;
		this.maxSize = maxSize;
	}
	/**
	 * 
	 * @return Gives back the Team name
	 */
	public String getConfigName() {
		return name;
	}
	
	public String getName() {
		return ChatColor.valueOf(getTeamColor().toString()) + getTeamColor().toString().substring(0, 1).toUpperCase() + getTeamColor().toString().substring(1).toLowerCase();
	}
	/**
	 * Removes the given Player from the Team
	 */
	@SuppressWarnings("deprecation")
	public void removePlayer(Player p) {
		if(allPlayer.contains(p)) {
			allPlayer.remove(p);
		}
		if(sTeam.hasPlayer(p)) {
			sTeam.removePlayer(p);
		}
	}
	/**
	 * Adds the given Player to the Team 
	 */
	@SuppressWarnings("deprecation")
	public void addPlayer(Player p) {
		if(!allPlayer.contains(p)) {
			allPlayer.add(p);
		}
		if(!sTeam.hasPlayer(p)) {
			sTeam.addPlayer(p);
			
		}
		for(Player player : Bukkit.getOnlinePlayers()) {
			player.setScoreboard(sTeam.getScoreboard());
		}
	}
	/**
	 * Return the Minecraft Scoreboard Team 
	 */
	public org.bukkit.scoreboard.Team getScoreboardTeam(){
		return sTeam;
	}
	

	public ArrayList<Player> getAllPlayers(){
		return allPlayer;
	}
	
	public void setTeamColor(DyeColor dyeColor) {
		this.teamcolor = dyeColor;
	}
	
	public DyeColor getTeamColor() {
		return teamcolor;
	}
	
	public ChatColor getChatColor() {
		return ChatColor.valueOf(teamcolor.toString());
	}
	
	public int getMaxPlayerSize() {
		return maxSize;
	}
}
