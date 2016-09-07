package de.alphahelix.uhc;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import de.alphahelix.uhc.commands.StatsCommand;
import de.alphahelix.uhc.commands.UHCAdminCommands;
import de.alphahelix.uhc.files.KitsFile;
import de.alphahelix.uhc.files.MainOptionsFile;
import de.alphahelix.uhc.files.MainMessageFile;
import de.alphahelix.uhc.files.PlayerFile;
import de.alphahelix.uhc.files.StatsFile;
import de.alphahelix.uhc.files.scenarios.ScenarioFile;
import de.alphahelix.uhc.inventories.KitInventory;
import de.alphahelix.uhc.listeners.ChatListener;
import de.alphahelix.uhc.listeners.EquipListener;
import de.alphahelix.uhc.listeners.KitChooseListener;
import de.alphahelix.uhc.listeners.RegisterListener;
import de.alphahelix.uhc.util.EasyFile;
import de.alphahelix.uhc.util.PlayerUtil;
import de.alphahelix.uhc.util.StatsUtil;
import de.alphahelix.uhc.util.TranslatorUtil;

public class Registery {

	private UHC uhc;
	private ArrayList<Listener> listeners;
	private ArrayList<EasyFile> easyFiles;
	
	private PlayerUtil playerUtil;
	private StatsUtil statsUtil;
	private TranslatorUtil translatorUtil;
	
	private KitInventory kitInventory;
	
	private PlayerFile playerFile;
	private MainOptionsFile mainOptionsFile;
	private KitsFile kitsFile;
	private StatsFile statsFile;
	private MainMessageFile messageFile;
	private ScenarioFile scenarioFile;
	
	private KitChooseListener kitChooseListener;
	private RegisterListener registerListener;
	private EquipListener equipListener;
	private ChatListener chatListener;

	public Registery(UHC uhc) {
		setUhc(uhc);
		setListeners(new ArrayList<Listener>());
		setEasyFiles(new ArrayList<EasyFile>());
	}

	// Registering

	public void registerAll() {
		
		setPlayerFile(new PlayerFile(getUhc()));
		setMainOptionsFile(new MainOptionsFile(getUhc()));
		setKitsFile(new KitsFile(getUhc()));
		setStatsFile(new StatsFile(getUhc()));
		setMessageFile(new MainMessageFile(getUhc()));
		setScenarioFile(new ScenarioFile(getUhc()));
		
		for(EasyFile easyFile : getEasyFiles()) {
			easyFile.register(easyFile);
		}
		
		getUhc().setMySQLMode(getMainOptionsFile().getBoolean("MySQL"));
		getUhc().setBunggeMode(getMainOptionsFile().getBoolean("Bungeecord"));
		getUhc().setLobbyServer(getMainOptionsFile().getString("Bungeecord Fallbackserver"));
		getUhc().setSoup(getMainOptionsFile().getBoolean("Soup"));
		getUhc().setSpawnradius(getMainOptionsFile().getInt("Spawndispersal"));
		getUhc().setStatusMOTD(getMainOptionsFile().getBoolean("Status MOTD"));
		getUhc().setScenarios(getScenarioFile().getBoolean("Scenarios"));
		getUhc().setKits(getKitsFile().getBoolean("Kits"));
		
		setPlayerUtil(new PlayerUtil(getUhc()));
		setStatsUtil(new StatsUtil(getUhc()));
		setTranslatorUtil(new TranslatorUtil(getUhc()));
		
		setKitInventory(new KitInventory(getUhc()));
		
		setKitChooseListener(new KitChooseListener(getUhc()));
		setRegisterListener(new RegisterListener(getUhc()));
		setEquipListener(new EquipListener(getUhc()));
		setChatListener(new ChatListener(getUhc()));
		
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
		new StatsCommand(getUhc(), "stats", "Check your or others stats", "records");
		new UHCAdminCommands(getUhc(), "uhcAdmin", "Mange the server configurations via commands.", "uhcA");
	}

	// Instance

	public UHC getUhc() {
		return uhc;
	}

	private void setUhc(UHC uhc) {
		this.uhc = uhc;
	}
	
	// Listeners
	
	public KitChooseListener getKitChooseListener() {
		return kitChooseListener;
	}

	public void setKitChooseListener(KitChooseListener kitChooseListener) {
		this.kitChooseListener = kitChooseListener;
	}

	public RegisterListener getRegisterListener() {
		return registerListener;
	}

	public void setRegisterListener(RegisterListener registerListener) {
		this.registerListener = registerListener;
	}
	
	public EquipListener getEquipListener() {
		return equipListener;
	}

	public void setEquipListener(EquipListener equipListener) {
		this.equipListener = equipListener;
	}
	
	public ChatListener getChatListener() {
		return chatListener;
	}

	public void setChatListener(ChatListener chatListener) {
		this.chatListener = chatListener;
	}

	public ArrayList<Listener> getListeners() {
		return listeners;
	}

	private void setListeners(ArrayList<Listener> arrayList) {
		this.listeners = arrayList;
	}

	// Utils
	
	public PlayerUtil getPlayerUtil() {
		return playerUtil;
	}
	
	public void setPlayerUtil(PlayerUtil playerUtil) {
		this.playerUtil = playerUtil;
	}
	
	public TranslatorUtil getTranslatorUtil() {
		return translatorUtil;
	}

	public void setTranslatorUtil(TranslatorUtil translatorUtil) {
		this.translatorUtil = translatorUtil;
	}

	public StatsUtil getStatsUtil() {
		return statsUtil;
	}

	public void setStatsUtil(StatsUtil statsUtil) {
		this.statsUtil = statsUtil;
	}
	

	// Files
	
	public PlayerFile getPlayerFile() {
		return playerFile;
	}

	public ArrayList<EasyFile> getEasyFiles() {
		return easyFiles;
	}

	private void setEasyFiles(ArrayList<EasyFile> easyFiles) {
		this.easyFiles = easyFiles;
	}

	private void setPlayerFile(PlayerFile playerFile) {
		this.playerFile = playerFile;
	}

	public MainOptionsFile getMainOptionsFile() {
		return mainOptionsFile;
	}

	private void setMainOptionsFile(MainOptionsFile mainOptionsFile) {
		this.mainOptionsFile = mainOptionsFile;
	}

	public KitsFile getKitsFile() {
		return kitsFile;
	}

	private void setKitsFile(KitsFile kitsFile) {
		this.kitsFile = kitsFile;
	}
	
	public MainMessageFile getMessageFile() {
		return messageFile;
	}

	private void setMessageFile(MainMessageFile messageFile) {
		this.messageFile = messageFile;
	}

	public ScenarioFile getScenarioFile() {
		return scenarioFile;
	}

	public void setScenarioFile(ScenarioFile scenarioFile) {
		this.scenarioFile = scenarioFile;
	}

	public StatsFile getStatsFile() {
		return statsFile;
	}

	private void setStatsFile(StatsFile statsFile) {
		this.statsFile = statsFile;
	}

	// Inventories
	
	public KitInventory getKitInventory() {
		return kitInventory;
	}

	public void setKitInventory(KitInventory kitInventory) {
		this.kitInventory = kitInventory;
	}
	
}
