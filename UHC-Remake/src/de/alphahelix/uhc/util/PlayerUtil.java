package de.alphahelix.uhc.util;

import java.util.LinkedList;

import org.bukkit.entity.Player;

import de.alphahelix.uhc.UHC;

public class PlayerUtil extends Util{

	private LinkedList<String> all;
	private LinkedList<String> survivors;
	private LinkedList<String> deaths;
	
	public PlayerUtil(UHC uhc) {
		super(uhc);
		setAll(new LinkedList<String>());
		setSurvivors(new LinkedList<String>());
		setDeaths(new LinkedList<String>());
	}

	//All Players
	
	public LinkedList<String> getAll() {
		return all;
	}

	private void setAll(LinkedList<String> all) {
		this.all = all;
	}
	
	public void addAll(Player p) {
		if(!(all.contains(p.getName()))) all.add(p.getName());
	}
	
	public void removeAll(Player p) {
		if(all.contains(p.getName())) all.remove(p.getName());
		removeSurvivor(p);
		removeDead(p);
	}
	
	//Living Players

	public LinkedList<String> getSurvivors() {
		return survivors;
	}

	private void setSurvivors(LinkedList<String> survivors) {
		this.survivors = survivors;
	}
	
	public void addSurvivor(Player p) {
		if(!(survivors.contains(p.getName()))) survivors.add(p.getName());
	}
	
	public void removeSurvivor(Player p) {
		if(survivors.contains(p.getName())) survivors.remove(p.getName());
	}
	
	//Death Players | Spectators |

	public LinkedList<String> getDeaths() {
		return deaths;
	}

	private void setDeaths(LinkedList<String> deaths) {
		this.deaths = deaths;
	}
	
	public void addDead(Player p) {
		if(!(deaths.contains(p.getName()))) deaths.add(p.getName());
	}
	
	public void removeDead(Player p) {
		if(deaths.contains(p.getName())) deaths.remove(p.getName());
	}
}
