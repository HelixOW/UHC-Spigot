package de.alphahelix.uhc;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import de.alphahelix.uhc.util.EasyFile;
import de.alphahelix.uhc.util.PlayerUtil;

public class Registery {

	private UHC uhc;
	private ArrayList<Listener> listeners;
	private ArrayList<EasyFile> easyFiles;
	
	private PlayerUtil playerUtil;

	public Registery(UHC uhc) {
		setUhc(uhc);
		setListeners(new ArrayList<Listener>());
		setEasyFiles(new ArrayList<EasyFile>());
	}

	// Registering

	public void registerAll() {

		for (EasyFile easyFile : getEasyFiles()) {
			EasyFile.register(easyFile);
		}
		
		playerUtil = new PlayerUtil(uhc);

		registerCommands();
		registerEvents();
	}

	private void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();
		for (Listener listener : getListeners()) {
			pm.registerEvents(listener, getUhc());
		}
	}

	private void registerCommands() {

	}

	// Instance

	public UHC getUhc() {
		return uhc;
	}

	private void setUhc(UHC uhc) {
		this.uhc = uhc;
	}

	// Listeners

	public ArrayList<Listener> getListeners() {
		return listeners;
	}

	private void setListeners(ArrayList<Listener> arrayList) {
		this.listeners = arrayList;
	}

	public void addListener(Listener listener) {
		if (getListeners().contains(listener))
			return;
		getListeners().add(listener);
	}

	// EasyFiles

	public ArrayList<EasyFile> getEasyFiles() {
		return easyFiles;
	}

	private void setEasyFiles(ArrayList<EasyFile> arrayList) {
		this.easyFiles = arrayList;
	}

	public void addEasyFile(EasyFile file) {
		if (getEasyFiles().contains(file))
			return;
		getEasyFiles().add(file);
	}

	// Utils
	
	public PlayerUtil getPlayerUtil() {
		return playerUtil;
	}
	
}
