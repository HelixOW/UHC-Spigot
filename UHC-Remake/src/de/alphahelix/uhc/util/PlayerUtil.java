package de.alphahelix.uhc.util;

import java.util.LinkedList;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Util;

public class PlayerUtil extends Util{

	private LinkedList<String> all;
	private LinkedList<String> survivors;
	private LinkedList<String> deaths;
	
	public PlayerUtil(UHC uhc) {
		super(uhc);
		setAll(new LinkedList<String>());
		setSurvivors(new LinkedList<String>());
		setDeads(new LinkedList<String>());
	}
	
	public void clearUp(Player p) {
		p.getInventory().clear();
		p.getEnderChest().clear();
		p.getEquipment().clear();
		
		p.setAllowFlight(false);
		p.setFlySpeed((float) 0.1);
		p.getInventory().setArmorContents(null);
		p.setBedSpawnLocation(null);
		p.setExp(0);
		p.setFireTicks(0);
		p.setFlying(false);
		p.setFoodLevel(20);
		p.setGameMode(GameMode.SURVIVAL);
		p.setGlowing(false);
		p.setHealth(20.0);
		p.setInvulnerable(false);
		p.setLevel(0);
		p.setMaxHealth(20);
		p.setTotalExperience(0);
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
	
	public boolean isSurivor(Player p) {
		if(survivors.contains(p.getName())) return true;
		return false;
	}
	
	//Death Players | Spectators |

	public LinkedList<String> getDeads() {
		return deaths;
	}

	private void setDeads(LinkedList<String> deaths) {
		this.deaths = deaths;
	}
	
	public void addDead(Player p) {
		if(!(deaths.contains(p.getName()))) deaths.add(p.getName());
	}
	
	public void removeDead(Player p) {
		if(deaths.contains(p.getName())) deaths.remove(p.getName());
	}
	
	public boolean isDead(Player p) {
		if(deaths.contains(p.getName())) return true;
		return false;
	}
	
	//Other
	
	public int getMinimumPlayerCount() {
		return getRegister().getMainOptionsFile().getInt("Minimum players");
	}
	
	public int getMaximumPlayerCount() {
		return getRegister().getMainOptionsFile().getInt("Maximum players");
	}
}
