package de.alphahelix.uhc;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;

import de.alphahelix.uhc.commands.StartCommand;
import de.alphahelix.uhc.commands.StatsCommand;
import de.alphahelix.uhc.commands.UHCAdminCommands;
import de.alphahelix.uhc.commands.UHCSetUpCommand;
import de.alphahelix.uhc.files.BorderFile;
import de.alphahelix.uhc.files.ConfirmFile;
import de.alphahelix.uhc.files.CraftingFile;
import de.alphahelix.uhc.files.DeathmessageFile;
import de.alphahelix.uhc.files.DropsFile;
import de.alphahelix.uhc.files.KitsFile;
import de.alphahelix.uhc.files.LocationsFile;
import de.alphahelix.uhc.files.MainMessageFile;
import de.alphahelix.uhc.files.MainOptionsFile;
import de.alphahelix.uhc.files.PlayerFile;
import de.alphahelix.uhc.files.ScenarioFile;
import de.alphahelix.uhc.files.ScoreboardConstructFile;
import de.alphahelix.uhc.files.ScoreboardFile;
import de.alphahelix.uhc.files.SpectatorFile;
import de.alphahelix.uhc.files.StatsFile;
import de.alphahelix.uhc.files.StatusFile;
import de.alphahelix.uhc.files.TablistFile;
import de.alphahelix.uhc.files.TeamFile;
import de.alphahelix.uhc.files.TimerFile;
import de.alphahelix.uhc.files.UnitFile;
import de.alphahelix.uhc.instances.EasyFile;
import de.alphahelix.uhc.instances.UHCTeam;
import de.alphahelix.uhc.inventories.ConfirmInventory;
import de.alphahelix.uhc.inventories.KitInventory;
import de.alphahelix.uhc.inventories.PreviewInventory;
import de.alphahelix.uhc.inventories.TeamInventory;
import de.alphahelix.uhc.listeners.ChatListener;
import de.alphahelix.uhc.listeners.ConfirmListener;
import de.alphahelix.uhc.listeners.DeathListener;
import de.alphahelix.uhc.listeners.EquipListener;
import de.alphahelix.uhc.listeners.GStateListener;
import de.alphahelix.uhc.listeners.GameEndsListener;
import de.alphahelix.uhc.listeners.KitChooseListener;
import de.alphahelix.uhc.listeners.RegisterListener;
import de.alphahelix.uhc.listeners.SpectatorListener;
import de.alphahelix.uhc.listeners.TeamListener;
import de.alphahelix.uhc.listeners.TimerListener;
import de.alphahelix.uhc.listeners.scenarios.ScenarioListener;
import de.alphahelix.uhc.timers.DeathmatchTimer;
import de.alphahelix.uhc.timers.GraceTimer;
import de.alphahelix.uhc.timers.LobbyTimer;
import de.alphahelix.uhc.timers.RestartTimer;
import de.alphahelix.uhc.timers.StartDeathMatchTimer;
import de.alphahelix.uhc.timers.WarmUpTimer;
import de.alphahelix.uhc.util.BorderUtil;
import de.alphahelix.uhc.util.LobbyUtil;
import de.alphahelix.uhc.util.PlayerUtil;
import de.alphahelix.uhc.util.ScoreboardUtil;
import de.alphahelix.uhc.util.StatsUtil;
import de.alphahelix.uhc.util.TablistUtil;
import de.alphahelix.uhc.util.TeamManagerUtil;
import de.alphahelix.uhc.util.WorldUtil;

public class Registery {

	private UHC uhc;
	private ArrayList<Listener> listeners;
	private ArrayList<ScenarioListener> scenListeners;
	private ArrayList<EasyFile> easyFiles;
	
	private PlayerUtil playerUtil;
	private StatsUtil statsUtil;
	private TablistUtil tablistUtil;
	private BorderUtil borderUtil;
	private ScoreboardUtil scoreboardUtil;
	private LobbyUtil lobbyUtil;
	private TeamManagerUtil teamManagerUtil;
	private WorldUtil worldUtil;
	
	private LobbyTimer lobbyTimer;
	private GraceTimer graceTimer;
	private WarmUpTimer warmUpTimer;
	private DeathmatchTimer deathmatchTimer;
	private StartDeathMatchTimer startDeathmatchTimer;
	private RestartTimer restartTimer;
	
	private KitInventory kitInventory;
	private ConfirmInventory confirmInventory;
	private TeamInventory teamInventory;
	private PreviewInventory previewInventory;
	
	private PlayerFile playerFile;
	private MainOptionsFile mainOptionsFile;
	private KitsFile kitsFile;
	private StatsFile statsFile;
	private MainMessageFile messageFile;
	private ScenarioFile scenarioFile;
	private TimerFile timerFile;
	private UnitFile unitFile;
	private TablistFile tablistFile;
	private BorderFile borderFile;
	private ScoreboardFile scoreboardFile;
	private ConfirmFile confirmFile;
	private LocationsFile locationsFile;
	private StatusFile statusFile;
	private ScoreboardConstructFile scoreboardConstructFile;
	private TeamFile teamFile;
	private SpectatorFile spectatorFile;
	private DropsFile dropsFile;
	private DeathmessageFile deathMessageFile;
	private CraftingFile craftingFile;
	
	private KitChooseListener kitChooseListener;
	private RegisterListener registerListener;
	private EquipListener equipListener;
	private ChatListener chatListener;
	private TimerListener timerListener;
	private ConfirmListener confirmListener;
	private GStateListener GStateListener;
	private TeamListener teamListener;
	private SpectatorListener spectatorListener;
	private GameEndsListener gameEndsListener;
	private DeathListener deathListener;
	private ScenarioListener scenarioListener;

	public Registery(UHC uhc) {
		setUhc(uhc);
		setListeners(new ArrayList<Listener>());
		setEasyFiles(new ArrayList<EasyFile>());
		setScenListeners(new ArrayList<ScenarioListener>());
	}

	// Registering

	public void registerAll() {
		
		setPlayerFile(new PlayerFile(getUhc()));
		setMainOptionsFile(new MainOptionsFile(getUhc()));
		setKitsFile(new KitsFile(getUhc()));
		setStatsFile(new StatsFile(getUhc()));
		setMessageFile(new MainMessageFile(getUhc()));
		setScenarioFile(new ScenarioFile(getUhc()));
		setTimerFile(new TimerFile(getUhc()));
		setUnitFile(new UnitFile(getUhc()));
		setTablistFile(new TablistFile(getUhc()));
		setBorderFile(new BorderFile(getUhc()));
		setScoreboardFile(new ScoreboardFile(getUhc()));
		setConfirmFile(new ConfirmFile(getUhc()));
		setLocationsFile(new LocationsFile(getUhc()));
		setStatusFile(new StatusFile(getUhc()));
		setScoreboardConstructFile(new ScoreboardConstructFile(getUhc()));
		setTeamFile(new TeamFile(getUhc()));
		setSpectatorFile(new SpectatorFile(getUhc()));
		setDropsFile(new DropsFile(getUhc()));
		setDeathMessageFile(new DeathmessageFile(getUhc()));
		setCraftingFile(new CraftingFile(getUhc()));
		
		for(EasyFile easyFile : getEasyFiles()) {
			easyFile.register(easyFile);
		}
		
		getUhc().setMySQLMode(getMainOptionsFile().getBoolean("MySQL"));
		getUhc().setBunggeMode(getMainOptionsFile().getBoolean("Bungeecord"));
		getUhc().setLobbyServer(getMainOptionsFile().getString("Bungeecord Fallbackserver"));
		getUhc().setSoup(getMainOptionsFile().getBoolean("Soup"));
		getUhc().setSpawnradius(getMainOptionsFile().getInt("Spawndispersal"));
		getUhc().setStatusMOTD(getMainOptionsFile().getBoolean("Status MOTD"));
		getUhc().setScenarios(getScenarioFile().getBoolean("Scenarios enabled"));
		getUhc().setKits(getKitsFile().getBoolean("Kits"));
		getUhc().setTeams(getTeamFile().getBoolean("Teams enabled"));
		getUhc().setTracker(getMainOptionsFile().getBoolean("Tracker.euip"));
		getUhc().setTrackerName(getMainOptionsFile().getColorString("Tracker.name"));
		
		setPlayerUtil(new PlayerUtil(getUhc()));
		setStatsUtil(new StatsUtil(getUhc()));
		setTablistUtil(new TablistUtil(getUhc()));
		setBorderUtil(new BorderUtil(getUhc()));
		setScoreboardUtil(new ScoreboardUtil(getUhc()));
		setLobbyUtil(new LobbyUtil(getUhc()));
		setTeamManagerUtil(new TeamManagerUtil(getUhc()));
		setWorldUtil(new WorldUtil(getUhc()));
		
		setKitInventory(new KitInventory(getUhc()));
		setConfirmInventory(new ConfirmInventory(getUhc()));
		setTeamInventory(new TeamInventory(getUhc()));
		setPreviewInventory(new PreviewInventory(getUhc()));
		
		setKitChooseListener(new KitChooseListener(getUhc()));
		setRegisterListener(new RegisterListener(getUhc()));
		setEquipListener(new EquipListener(getUhc()));
		setChatListener(new ChatListener(getUhc()));
		setTimerListener(new TimerListener(getUhc()));
		setConfirmListener(new ConfirmListener(getUhc()));
		setGStateListener(new GStateListener(getUhc()));
		setTeamListener(new TeamListener(getUhc()));
		setSpectatorListener(new SpectatorListener(getUhc()));
		setGameEndsListener(new GameEndsListener(getUhc()));
		setDeathListener(new DeathListener(getUhc()));
		setScenarioListener(new ScenarioListener(getUhc()));
		
		
		
		for(ScenarioListener sl : getScenListeners()) {
			sl.register();
		}
		
		setLobbyTimer(new LobbyTimer(getUhc()));
		setGraceTimer(new GraceTimer(getUhc()));
		setWarmUpTimer(new WarmUpTimer(getUhc()));
		setDeathmatchTimer(new DeathmatchTimer(getUhc()));
		setStartDeathmatchTimer(new StartDeathMatchTimer(getUhc()));
		setRestartTimer(new RestartTimer(getUhc()));
		
		registerCommands();
		registerEvents();
		registerTeams();
		
		getConfirmInventory().fillInventory();
		getTeamInventory().fillInventory();
		
		new BukkitRunnable() {
			public void run() {
				getWorldUtil().createWorld();
			}
		}.runTaskLater(getUhc(), 5);
		
		getBorderUtil().changeSize(getBorderFile().getInt("size"));
		
		getUhc().setRestartMessage(getMainOptionsFile().getColorString("Restartmessage"));
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
		new UHCSetUpCommand(getUhc(), "uhcSetup", "Setup all of your options", "uhcS");
		new StartCommand(getUhc(), "start", "Short or strech the lobby time.", "start");
	}
	
	private void registerTeams() {
		if(getTeamFile().getConfigurationSection("Teams") == null) return;
		
		for(String t : getTeamFile().getConfigurationSection("Teams").getKeys(false)) {
			t = "Teams."+t; 
			new UHCTeam(getTeamFile().getColorString(t+".name"),
					getTeamFile().getColorString(t+".prefix"),
					(byte) getTeamFile().getInt(t+".data"),
					getTeamFile().getInt(t+".max Players"),
					getTeamFile().getInt(t+".slot"),
					getTeamFile().getBoolean(t+".name"));
		}
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

	public TimerFile getTimerFile() {
		return timerFile;
	}

	public void setTimerFile(TimerFile timerFile) {
		this.timerFile = timerFile;
	}

	public LobbyTimer getLobbyTimer() {
		return lobbyTimer;
	}

	public void setLobbyTimer(LobbyTimer lobbyTimer) {
		this.lobbyTimer = lobbyTimer;
	}

	public TimerListener getTimerListener() {
		return timerListener;
	}

	public void setTimerListener(TimerListener timerListener) {
		this.timerListener = timerListener;
	}

	public UnitFile getUnitFile() {
		return unitFile;
	}

	public void setUnitFile(UnitFile unitFile) {
		this.unitFile = unitFile;
	}

	public TablistFile getTablistFile() {
		return tablistFile;
	}

	public void setTablistFile(TablistFile tablistFile) {
		this.tablistFile = tablistFile;
	}

	public TablistUtil getTablistUtil() {
		return tablistUtil;
	}

	public void setTablistUtil(TablistUtil tablistUtil) {
		this.tablistUtil = tablistUtil;
	}

	public BorderUtil getBorderUtil() {
		return borderUtil;
	}

	public void setBorderUtil(BorderUtil borderUtil) {
		this.borderUtil = borderUtil;
	}

	public BorderFile getBorderFile() {
		return borderFile;
	}

	public void setBorderFile(BorderFile borderFile) {
		this.borderFile = borderFile;
	}

	public ScoreboardUtil getScoreboardUtil() {
		return scoreboardUtil;
	}

	public void setScoreboardUtil(ScoreboardUtil scoreboardUtil) {
		this.scoreboardUtil = scoreboardUtil;
	}

	public ScoreboardFile getScoreboardFile() {
		return scoreboardFile;
	}

	public void setScoreboardFile(ScoreboardFile scoreboardFile) {
		this.scoreboardFile = scoreboardFile;
	}

	public ConfirmInventory getConfirmInventory() {
		return confirmInventory;
	}

	public void setConfirmInventory(ConfirmInventory confirmInventory) {
		this.confirmInventory = confirmInventory;
	}

	public ConfirmFile getConfirmFile() {
		return confirmFile;
	}

	public void setConfirmFile(ConfirmFile confirmFile) {
		this.confirmFile = confirmFile;
	}

	public ConfirmListener getConfirmListener() {
		return confirmListener;
	}

	public void setConfirmListener(ConfirmListener confirmListener) {
		this.confirmListener = confirmListener;
	}

	public LocationsFile getLocationsFile() {
		return locationsFile;
	}

	public void setLocationsFile(LocationsFile locationsFile) {
		this.locationsFile = locationsFile;
	}

	public LobbyUtil getLobbyUtil() {
		return lobbyUtil;
	}

	public void setLobbyUtil(LobbyUtil lobbyUtil) {
		this.lobbyUtil = lobbyUtil;
	}

	public GStateListener getGStateListener() {
		return GStateListener;
	}

	public void setGStateListener(GStateListener GStateListener) {
		this.GStateListener = GStateListener;
	}

	public StatusFile getStatusFile() {
		return statusFile;
	}

	public void setStatusFile(StatusFile statusFile) {
		this.statusFile = statusFile;
	}

	public ScoreboardConstructFile getScoreboardConstructFile() {
		return scoreboardConstructFile;
	}

	public void setScoreboardConstructFile(ScoreboardConstructFile scoreboardConstructFile) {
		this.scoreboardConstructFile = scoreboardConstructFile;
	}

	public TeamManagerUtil getTeamManagerUtil() {
		return teamManagerUtil;
	}

	public void setTeamManagerUtil(TeamManagerUtil teamManagerUtil) {
		this.teamManagerUtil = teamManagerUtil;
	}

	public TeamFile getTeamFile() {
		return teamFile;
	}

	public void setTeamFile(TeamFile teamFile) {
		this.teamFile = teamFile;
	}

	public TeamInventory getTeamInventory() {
		return teamInventory;
	}

	public void setTeamInventory(TeamInventory teamInventory) {
		this.teamInventory = teamInventory;
	}

	public TeamListener getTeamListener() {
		return teamListener;
	}

	public void setTeamListener(TeamListener teamListener) {
		this.teamListener = teamListener;
	}

	public GraceTimer getGraceTimer() {
		return graceTimer;
	}

	public void setGraceTimer(GraceTimer graceTimer) {
		this.graceTimer = graceTimer;
	}

	public WarmUpTimer getWarmUpTimer() {
		return warmUpTimer;
	}

	public void setWarmUpTimer(WarmUpTimer warmUpTimer) {
		this.warmUpTimer = warmUpTimer;
	}

	public DeathmatchTimer getDeathmatchTimer() {
		return deathmatchTimer;
	}

	public void setDeathmatchTimer(DeathmatchTimer deathmatchTimer) {
		this.deathmatchTimer = deathmatchTimer;
	}

	public StartDeathMatchTimer getStartDeathmatchTimer() {
		return startDeathmatchTimer;
	}

	public void setStartDeathmatchTimer(StartDeathMatchTimer startDeathmatchTimer) {
		this.startDeathmatchTimer = startDeathmatchTimer;
	}

	public SpectatorFile getSpectatorFile() {
		return spectatorFile;
	}

	public void setSpectatorFile(SpectatorFile spectatorFile) {
		this.spectatorFile = spectatorFile;
	}

	public SpectatorListener getSpectatorListener() {
		return spectatorListener;
	}

	public void setSpectatorListener(SpectatorListener spectatorListener) {
		this.spectatorListener = spectatorListener;
	}

	public DropsFile getDropsFile() {
		return dropsFile;
	}

	public void setDropsFile(DropsFile dropsFile) {
		this.dropsFile = dropsFile;
	}

	public GameEndsListener getGameEndsListener() {
		return gameEndsListener;
	}

	public void setGameEndsListener(GameEndsListener gameEndsListener) {
		this.gameEndsListener = gameEndsListener;
	}

	public RestartTimer getRestartTimer() {
		return restartTimer;
	}

	public void setRestartTimer(RestartTimer restartTimer) {
		this.restartTimer = restartTimer;
	}

	public DeathmessageFile getDeathMessageFile() {
		return deathMessageFile;
	}

	public void setDeathMessageFile(DeathmessageFile deathMessageFile) {
		this.deathMessageFile = deathMessageFile;
	}

	public DeathListener getDeathListener() {
		return deathListener;
	}

	public void setDeathListener(DeathListener deathListener) {
		this.deathListener = deathListener;
	}

	public WorldUtil getWorldUtil() {
		return worldUtil;
	}

	public void setWorldUtil(WorldUtil worldUtil) {
		this.worldUtil = worldUtil;
	}

	public CraftingFile getCraftingFile() {
		return craftingFile;
	}

	public void setCraftingFile(CraftingFile craftingFile) {
		this.craftingFile = craftingFile;
	}

	public PreviewInventory getPreviewInventory() {
		return previewInventory;
	}

	public void setPreviewInventory(PreviewInventory previewInventory) {
		this.previewInventory = previewInventory;
	}

	public ScenarioListener getScenarioListener() {
		return scenarioListener;
	}

	public void setScenarioListener(ScenarioListener scenarioListener) {
		this.scenarioListener = scenarioListener;
	}

	public ArrayList<ScenarioListener> getScenListeners() {
		return scenListeners;
	}

	public void setScenListeners(ArrayList<ScenarioListener> scenListeners) {
		this.scenListeners = scenListeners;
	}
}
