package de.alphahelix.uhc.util;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Util;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.LinkedList;

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
		p.setMaxHealth(20.0);
		p.setHealth(20.0);
		p.setInvulnerable(false);
		p.setLevel(0);
		p.setMaxHealth(20);
		p.setTotalExperience(0);
		for(PotionEffect pe : p.getActivePotionEffects()) {
			p.removePotionEffect(pe.getType());
		}
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
	
	public void addSurvivor(String name) {
		if(!(survivors.contains(name))) survivors.add(name);
	}
	
	public void removeSurvivor(Player p) {
		if(survivors.contains(p.getName())) survivors.remove(p.getName());
	}
	
	public void removeSurvivor(String name) {
		if(survivors.contains(name)) survivors.remove(name);
	}
	
	public boolean isSurivor(Player p) {
		return survivors.contains(p.getName());
	}
	
	public boolean isSurvivor(String name) {
		return survivors.contains(name);
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
	
	public void addDead(String name) {
		if(!(deaths.contains(name))) deaths.add(name);
	}
	
	public void removeDead(String name) {
		if(deaths.contains(name)) deaths.remove(name);
	}
	
	public boolean isDead(Player p) {
		return deaths.contains(p.getName());
	}
	
	public boolean isDead(String name) {
		return deaths.contains(name);
	}
	
	//Other
	
	public int getMinimumPlayerCount() {
		return getRegister().getMainOptionsFile().getInt("Minimum players");
	}
	
	public int getMaximumPlayerCount() {
		return getRegister().getMainOptionsFile().getInt("Maximum players");
	}
}
