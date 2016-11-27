package de.alphahelix.uhc.instances;

import de.alphahelix.uhc.Registery;
import de.alphahelix.uhc.UHC;
import de.popokaka.alphalibary.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Logger;

public abstract class SimpleInventory {
	
	private UHC uhc;
	private Registery register;
	private Logger log;
	
	private HashMap<String, Inventory> playerInv = new HashMap<>();
	private Inventory inventory;
	private String title;
	private int size;
	
	public SimpleInventory(UHC uhc, String title, int size) {
		setUhc(uhc);
		setRegister(getUhc().getRegister());
		setLog(getUhc().getLog());
		
		setTitle(title);
		setSize(size);
	}
	
	public void fillInventory() {
		for (int slot = 0; slot < getRawInventory().getSize(); slot++) {
			getRawInventory().setItem(slot,
					new ItemBuilder(Material.STAINED_GLASS_PANE).setName(" ").setDamage((short) 7).build());
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> toList(T... args) {
		ArrayList<T> toReturn = new ArrayList<>();
		Collections.addAll(toReturn, args);
		return toReturn;
	}
	
	public void openInventory(Player p) {
		if(playerInv.containsKey(p.getName())) p.openInventory(playerInv.get(p.getName()));
		playerInv.put(p.getName(), getRawInventory());
		p.openInventory(getRawInventory());
	}
	
	public void createInventory() {
		setInventory(Bukkit.createInventory(null, size, title));
		fillInventory();
	}

	public Inventory getRawInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Inventory getPlayersInventory(Player p) {
		if(!this.playerInv.containsKey(p.getName())) return getRawInventory();
		return this.playerInv.get(p.getName());
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public UHC getUhc() {
		return uhc;
	}

	private void setUhc(UHC uhc) {
		this.uhc = uhc;
	}

	public Registery getRegister() {
		return register;
	}

	private void setRegister(Registery registery) {
		this.register = registery;
	}

	public Logger getLog() {
		return log;
	}

	private void setLog(Logger log) {
		this.log = log;
	}
}
