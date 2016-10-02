package de.alphahelix.uhc;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;

import de.alphahelix.uhc.commands.InfoCommand;
import de.alphahelix.uhc.commands.SchematicCommand;
import de.alphahelix.uhc.commands.StartCommand;
import de.alphahelix.uhc.commands.StatsCommand;
import de.alphahelix.uhc.commands.UHCAdminCommands;
import de.alphahelix.uhc.commands.UHCSetUpCommand;
import de.alphahelix.uhc.events.armor.ArmorListener;
import de.alphahelix.uhc.files.BorderFile;
import de.alphahelix.uhc.files.ConfirmFile;
import de.alphahelix.uhc.files.CraftingFile;
import de.alphahelix.uhc.files.DeathmessageFile;
import de.alphahelix.uhc.files.DropsFile;
import de.alphahelix.uhc.files.HologramFile;
import de.alphahelix.uhc.files.KitsFile;
import de.alphahelix.uhc.files.LocationsFile;
import de.alphahelix.uhc.files.MOTDFile;
import de.alphahelix.uhc.files.MainMessageFile;
import de.alphahelix.uhc.files.MainOptionsFile;
import de.alphahelix.uhc.files.PlayerFile;
import de.alphahelix.uhc.files.ScenarioFile;
import de.alphahelix.uhc.files.ScenarioHelpFile;
import de.alphahelix.uhc.files.ScoreboardConstructFile;
import de.alphahelix.uhc.files.ScoreboardFile;
import de.alphahelix.uhc.files.SpectatorFile;
import de.alphahelix.uhc.files.StatsFile;
import de.alphahelix.uhc.files.StatusFile;
import de.alphahelix.uhc.files.TablistFile;
import de.alphahelix.uhc.files.TeamFile;
import de.alphahelix.uhc.files.TimerFile;
import de.alphahelix.uhc.files.UHCCrateFile;
import de.alphahelix.uhc.files.UnitFile;
import de.alphahelix.uhc.instances.EasyFile;
import de.alphahelix.uhc.inventories.ConfirmInventory;
import de.alphahelix.uhc.inventories.CrateInventory;
import de.alphahelix.uhc.inventories.KitInventory;
import de.alphahelix.uhc.inventories.PreviewInventory;
import de.alphahelix.uhc.inventories.TeamInventory;
import de.alphahelix.uhc.inventories.crates.EpicCrateInventory;
import de.alphahelix.uhc.inventories.crates.LegendaryCrateListener;
import de.alphahelix.uhc.inventories.crates.NormalCrateInventory;
import de.alphahelix.uhc.inventories.crates.RareCrateInventory;
import de.alphahelix.uhc.inventories.crates.SuperrareCrateInventory;
import de.alphahelix.uhc.inventories.crates.UnCommonCrateInventory;
import de.alphahelix.uhc.listeners.ChatListener;
import de.alphahelix.uhc.listeners.ConfirmListener;
import de.alphahelix.uhc.listeners.DeathListener;
import de.alphahelix.uhc.listeners.EquipListener;
import de.alphahelix.uhc.listeners.GStateListener;
import de.alphahelix.uhc.listeners.GameEndsListener;
import de.alphahelix.uhc.listeners.KitChooseListener;
import de.alphahelix.uhc.listeners.RegisterListener;
import de.alphahelix.uhc.listeners.SchematicListener;
import de.alphahelix.uhc.listeners.SpectatorListener;
import de.alphahelix.uhc.listeners.TeamListener;
import de.alphahelix.uhc.listeners.TimerListener;
import de.alphahelix.uhc.listeners.UHCCrateListener;
import de.alphahelix.uhc.listeners.scenarios.AppleFamineListener;
import de.alphahelix.uhc.listeners.scenarios.ArmorVHealthListener;
import de.alphahelix.uhc.listeners.scenarios.ArrowListener;
import de.alphahelix.uhc.listeners.scenarios.BackPackListener;
import de.alphahelix.uhc.listeners.scenarios.BareBonesListener;
import de.alphahelix.uhc.listeners.scenarios.BenchBlitzListener;
import de.alphahelix.uhc.listeners.scenarios.BestPvEListener;
import de.alphahelix.uhc.listeners.scenarios.BiomeParanoiaListener;
import de.alphahelix.uhc.listeners.scenarios.BlitzListener;
import de.alphahelix.uhc.listeners.scenarios.BlockRushListener;
import de.alphahelix.uhc.listeners.scenarios.BlockedListener;
import de.alphahelix.uhc.listeners.scenarios.BloodyDiamondsListener;
import de.alphahelix.uhc.listeners.scenarios.BloodyLapisListener;
import de.alphahelix.uhc.listeners.scenarios.BombersListener;
import de.alphahelix.uhc.listeners.scenarios.BowfighterListener;
import de.alphahelix.uhc.listeners.scenarios.CaptainsListener;
import de.alphahelix.uhc.listeners.scenarios.CatsEyesListener;
import de.alphahelix.uhc.listeners.scenarios.CertainCircumstancesListener;
import de.alphahelix.uhc.listeners.scenarios.ChickenListener;
import de.alphahelix.uhc.listeners.scenarios.CityWorldListener;
import de.alphahelix.uhc.listeners.scenarios.CivilisationListener;
import de.alphahelix.uhc.listeners.scenarios.CompensationListener;
import de.alphahelix.uhc.listeners.scenarios.DamageDogersListener;
import de.alphahelix.uhc.listeners.scenarios.DiamondlessListener;
import de.alphahelix.uhc.listeners.scenarios.DimensonalInversionListener;
import de.alphahelix.uhc.listeners.scenarios.DoubleOrNothingListener;
import de.alphahelix.uhc.listeners.scenarios.DungeonMazeListener;
import de.alphahelix.uhc.listeners.scenarios.EightLeggedFreaksListener;
import de.alphahelix.uhc.listeners.scenarios.EnderDanceListener;
import de.alphahelix.uhc.listeners.scenarios.EnderDragonRushListener;
import de.alphahelix.uhc.listeners.scenarios.EntropyListener;
import de.alphahelix.uhc.listeners.scenarios.ErraticPvPListener;
import de.alphahelix.uhc.listeners.scenarios.EveryRoseListener;
import de.alphahelix.uhc.listeners.scenarios.FalloutListener;
import de.alphahelix.uhc.listeners.scenarios.FlowerPowerListener;
import de.alphahelix.uhc.listeners.scenarios.FoodNeophobiaListener;
import de.alphahelix.uhc.listeners.scenarios.GoToHellListener;
import de.alphahelix.uhc.listeners.scenarios.GoldenFleeceListener;
import de.alphahelix.uhc.listeners.scenarios.GoneFishingListener;
import de.alphahelix.uhc.listeners.scenarios.GunsNRosesListener;
import de.alphahelix.uhc.listeners.scenarios.HalfOreListener;
import de.alphahelix.uhc.listeners.scenarios.HashtagBowListener;
import de.alphahelix.uhc.listeners.scenarios.HealthDonorListener;
import de.alphahelix.uhc.listeners.scenarios.InventorsListener;
import de.alphahelix.uhc.listeners.scenarios.ItemHuntListener;
import de.alphahelix.uhc.listeners.scenarios.JackpotListener;
import de.alphahelix.uhc.listeners.scenarios.KingsListener;
import de.alphahelix.uhc.listeners.scenarios.LightsOutListener;
import de.alphahelix.uhc.listeners.scenarios.LiveWithRegretListener;
import de.alphahelix.uhc.listeners.scenarios.LongshotListener;
import de.alphahelix.uhc.listeners.scenarios.LootchestListener;
import de.alphahelix.uhc.listeners.scenarios.LucyInTheSkyWithDiamondsListener;
import de.alphahelix.uhc.listeners.scenarios.MoleListener;
import de.alphahelix.uhc.listeners.scenarios.MonsterIncListener;
import de.alphahelix.uhc.listeners.scenarios.NightmareModeListener;
import de.alphahelix.uhc.listeners.scenarios.NineSlotsListener;
import de.alphahelix.uhc.listeners.scenarios.NoFurnaceListener;
import de.alphahelix.uhc.listeners.scenarios.NoGoingBackListener;
import de.alphahelix.uhc.listeners.scenarios.NoNetherListener;
import de.alphahelix.uhc.listeners.scenarios.NoSprintListener;
import de.alphahelix.uhc.listeners.scenarios.NotShinyEnoughListener;
import de.alphahelix.uhc.listeners.scenarios.OneHealListener;
import de.alphahelix.uhc.listeners.scenarios.PopeyeListener;
import de.alphahelix.uhc.listeners.scenarios.PotentialPermanentListener;
import de.alphahelix.uhc.listeners.scenarios.PotionSwapListener;
import de.alphahelix.uhc.listeners.scenarios.PuppyPowerListener;
import de.alphahelix.uhc.listeners.scenarios.PvCListener;
import de.alphahelix.uhc.listeners.scenarios.PyrophobiaListener;
import de.alphahelix.uhc.listeners.scenarios.PyrotechnicsListener;
import de.alphahelix.uhc.listeners.scenarios.RandomStarterItemsListener;
import de.alphahelix.uhc.listeners.scenarios.RiskyRetrievalListener;
import de.alphahelix.uhc.listeners.scenarios.SelectOresListener;
import de.alphahelix.uhc.listeners.scenarios.SharedHealthListener;
import de.alphahelix.uhc.listeners.scenarios.SheepLoversListener;
import de.alphahelix.uhc.listeners.scenarios.SkyHighListener;
import de.alphahelix.uhc.listeners.scenarios.SwitcherooListener;
import de.alphahelix.uhc.listeners.scenarios.TeamInventoryListener;
import de.alphahelix.uhc.listeners.scenarios.TheHobbitListener;
import de.alphahelix.uhc.listeners.scenarios.TimberListener;
import de.alphahelix.uhc.listeners.scenarios.TreeDropsListener;
import de.alphahelix.uhc.listeners.scenarios.TripleOresListener;
import de.alphahelix.uhc.listeners.scenarios.UltraParanoidListener;
import de.alphahelix.uhc.listeners.scenarios.VeinMinerListener;
import de.alphahelix.uhc.listeners.scenarios.XtrAppleListener;
import de.alphahelix.uhc.timers.BestPvETimer;
import de.alphahelix.uhc.timers.DamageCycleTimer;
import de.alphahelix.uhc.timers.DeathmatchTimer;
import de.alphahelix.uhc.timers.EntropyTimer;
import de.alphahelix.uhc.timers.FalloutTimer;
import de.alphahelix.uhc.timers.GoToHellTimer;
import de.alphahelix.uhc.timers.GraceTimer;
import de.alphahelix.uhc.timers.LobbyTimer;
import de.alphahelix.uhc.timers.RestartTimer;
import de.alphahelix.uhc.timers.SkyHighTimer;
import de.alphahelix.uhc.timers.SoulBrothersListener;
import de.alphahelix.uhc.timers.StartDeathMatchTimer;
import de.alphahelix.uhc.timers.WarmUpTimer;
import de.alphahelix.uhc.util.BiomeUtil;
import de.alphahelix.uhc.util.BorderUtil;
import de.alphahelix.uhc.util.HologramUtil;
import de.alphahelix.uhc.util.LobbyUtil;
import de.alphahelix.uhc.util.NPCUtil;
import de.alphahelix.uhc.util.PlayerUtil;
import de.alphahelix.uhc.util.RankingUtil;
import de.alphahelix.uhc.util.ScoreboardUtil;
import de.alphahelix.uhc.util.StatsUtil;
import de.alphahelix.uhc.util.TablistUtil;
import de.alphahelix.uhc.util.TeamManagerUtil;
import de.alphahelix.uhc.util.WorldUtil;
import de.alphahelix.uhc.util.schematic.SchematicManagerUtil;

public class Registery {

	private UHC uhc;
	private ArrayList<Listener> listeners;
	private ArrayList<EasyFile> easyFiles;

	private PlayerUtil playerUtil;
	private StatsUtil statsUtil;
	private TablistUtil tablistUtil;
	private BorderUtil borderUtil;
	private ScoreboardUtil scoreboardUtil;
	private LobbyUtil lobbyUtil;
	private TeamManagerUtil teamManagerUtil;
	private WorldUtil worldUtil;
	private HologramUtil hologramUtil;
	private NPCUtil npcUtil;
	private RankingUtil rankingUtil;
	private SchematicManagerUtil schematicManagerUtil;

	private LobbyTimer lobbyTimer;
	private GraceTimer graceTimer;
	private WarmUpTimer warmUpTimer;
	private DeathmatchTimer deathmatchTimer;
	private StartDeathMatchTimer startDeathmatchTimer;
	private RestartTimer restartTimer;
	private BestPvETimer bestPvETimer;
	private DamageCycleTimer damageCycleTimer;
	private EntropyTimer entropyTimer;
	private FalloutTimer falloutTimer;
	private GoToHellTimer goToHellTimer;
	private SkyHighTimer skyHighTimer;

	private KitInventory kitInventory;
	private ConfirmInventory confirmInventory;
	private TeamInventory teamInventory;
	private PreviewInventory previewInventory;
	private CrateInventory crateInventory;
	
	private NormalCrateInventory normalCrateInventory;
	private UnCommonCrateInventory unCommonCrateInventory;
	private RareCrateInventory rareCrateInventory;
	private SuperrareCrateInventory superrareCrateInventory;
	private EpicCrateInventory epicCrateInventory;
	private LegendaryCrateListener legendaryCrateInventory;

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
	private ScenarioHelpFile scenarioHelpFile;
	private HologramFile hologramFile;
	private MOTDFile mOTDFile;
	private UHCCrateFile uhcCrateFile;

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
	private SchematicListener schematicListener;
	private UHCCrateListener uhcCrateListener;

	public Registery(UHC uhc) {
		setUhc(uhc);
		setListeners(new ArrayList<Listener>());
		setEasyFiles(new ArrayList<EasyFile>());
	}

	// Registering

	private void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();
		for (Listener listener : getListeners()) {
			pm.registerEvents(listener, getUhc());
		}
	}

	private void registerCommands() {
		new StatsCommand(getUhc(), "stats", "Check your or others stats", "records");
		new UHCAdminCommands(getUhc(), "uhcAdmin", "Manage some server configurations via commands.", "uhcA");
		new UHCSetUpCommand(getUhc(), "uhcSetup", "Setup all of your options", "uhcS");
		new StartCommand(getUhc(), "start", "Short or strech the lobby time.", "start");
		new InfoCommand(getUhc(), "informations", "Get informations about the current scenario", "scenario", "infos");
		new SchematicCommand(getUhc(), "schematic", "Create your own uhc schematics", "schem");
	}

	private void registerTeams() {
		if (getTeamFile().getConfigurationSection("Teams") == null)
			return;

		for (String t : getTeamFile().getConfigurationSection("Teams").getKeys(false)) {
			t = "Teams." + t;
			getTeamManagerUtil().registerTeam(getTeamFile().getColorString(t + ".name"),
					getTeamFile().getColorString(t + ".prefix"), (byte) getTeamFile().getInt(t + ".data"),
					getTeamFile().getInt(t + ".max Players"), getTeamFile().getInt(t + ".slot"),
					getTeamFile().getBoolean(t + ".colored Name"), Color.fromRGB(getTeamFile().getInt(t + ".color.red"),
							getTeamFile().getInt(t + ".color.green"), getTeamFile().getInt(t + ".color.blue")));
		}
	}
	
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
		setScenarioHelpFile(new ScenarioHelpFile(getUhc()));
		setHologramFile(new HologramFile(getUhc()));
		setMOTDFile(new MOTDFile(getUhc()));
		setUhcCrateFile(new UHCCrateFile(getUhc()));

		for (EasyFile easyFile : getEasyFiles()) {
			easyFile.register(easyFile);
		}

		getUhc().setMySQLMode(getMainOptionsFile().getBoolean("MySQL"));
		getUhc().setBunggeMode(getMainOptionsFile().getBoolean("Bungeecord"));
		getUhc().setLobbyServer(getMainOptionsFile().getString("Bungeecord Fallbackserver"));
		getUhc().setSoup(getMainOptionsFile().getBoolean("Soup"));
		getUhc().setDebug(getMainOptionsFile().getBoolean("debugging"));
		getUhc().setSpawnradius(getMainOptionsFile().getInt("Spawndispersal"));
		getUhc().setStatusMOTD(getMainOptionsFile().getBoolean("Status MOTD"));
		getUhc().setScenarios(getScenarioFile().getBoolean("Scenarios enabled"));
		getUhc().setKits(getKitsFile().getBoolean("Kits"));
		getUhc().setTeams(getTeamFile().getBoolean("Teams enabled"));
		getUhc().setTracker(getMainOptionsFile().getBoolean("Tracker.euip"));
		getUhc().setTrackerName(getMainOptionsFile().getColorString("Tracker.name"));
		getUhc().setLobbyAsSchematic(getMainOptionsFile().getBoolean("Lobby.as schematic"));
		getUhc().setCrates(getUhcCrateFile().getBoolean("Crates"));
		getUhc().setRestartMessage(getMainOptionsFile().getColorString("Restartmessage"));

		setPlayerUtil(new PlayerUtil(getUhc()));
		setStatsUtil(new StatsUtil(getUhc()));
		setTablistUtil(new TablistUtil(getUhc()));
		setBorderUtil(new BorderUtil(getUhc()));
		setScoreboardUtil(new ScoreboardUtil(getUhc()));
		setLobbyUtil(new LobbyUtil(getUhc()));
		setTeamManagerUtil(new TeamManagerUtil(getUhc()));
		setWorldUtil(new WorldUtil(getUhc()));
		setHologramUtil(new HologramUtil(getUhc()));
		setNpcUtil(new NPCUtil(getUhc()));
		setRankingUtil(new RankingUtil(getUhc()));
		setSchematicManagerUtil(new SchematicManagerUtil(getUhc()));

		setKitInventory(new KitInventory(getUhc()));
		setConfirmInventory(new ConfirmInventory(getUhc()));
		setTeamInventory(new TeamInventory(getUhc()));
		setPreviewInventory(new PreviewInventory(getUhc()));
		setCrateInventory(new CrateInventory(getUhc()));
		
		setNormalCrateInventory(new NormalCrateInventory(getUhc()));
		setUnCommonCrateInventory(new UnCommonCrateInventory(getUhc()));
		setRareCrateInventory(new RareCrateInventory(getUhc()));
		setSuperrareCrateInventory(new SuperrareCrateInventory(getUhc()));
		setEpicCrateInventory(new EpicCrateInventory(getUhc()));
		setLegendaryCrateInventory(new LegendaryCrateListener(getUhc()));

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
		setSchematicListener(new SchematicListener(getUhc()));
		setUhcCrateListener(new UHCCrateListener(getUhc()));

		setLobbyTimer(new LobbyTimer(getUhc()));
		setGraceTimer(new GraceTimer(getUhc()));
		setWarmUpTimer(new WarmUpTimer(getUhc()));
		setDeathmatchTimer(new DeathmatchTimer(getUhc()));
		setStartDeathmatchTimer(new StartDeathMatchTimer(getUhc()));
		setRestartTimer(new RestartTimer(getUhc()));
		setBestPvETimer(new BestPvETimer(getUhc()));
		setDamageCycleTimer(new DamageCycleTimer(getUhc()));
		setEntropyTimer(new EntropyTimer(getUhc()));
		setFalloutTimer(new FalloutTimer(getUhc()));
		setGoToHellTimer(new GoToHellTimer(getUhc()));
		setSkyHighTimer(new SkyHighTimer(getUhc()));

		getBorderUtil().changeSize(getBorderFile().getInt("size"));

		new ArmorListener(getUhc());

		new HalfOreListener(getUhc());
		new ArrowListener(getUhc());
		new MonsterIncListener(getUhc());
		new AppleFamineListener(getUhc());
		new ArmorVHealthListener(getUhc());
		new BackPackListener(getUhc());
		new BareBonesListener(getUhc());
		new BenchBlitzListener(getUhc());
		new BestPvEListener(getUhc());
		new BloodyLapisListener(getUhc());
		new BiomeParanoiaListener(getUhc());
		new BlitzListener(getUhc());
		new BlockedListener(getUhc());
		new BlockRushListener(getUhc());
		new BloodyDiamondsListener(getUhc());
		new HashtagBowListener(getUhc());
		new BombersListener(getUhc());
		new BowfighterListener(getUhc());
		new CatsEyesListener(getUhc());
		new CaptainsListener(getUhc());
		new CertainCircumstancesListener(getUhc());
		new ChickenListener(getUhc());
		new CivilisationListener(getUhc());
		new CityWorldListener(getUhc());
		new CompensationListener(getUhc());
		new DamageDogersListener(getUhc());
		new DiamondlessListener(getUhc());
		new DimensonalInversionListener(getUhc());
		new DoubleOrNothingListener(getUhc());
		new DungeonMazeListener(getUhc());
		new EightLeggedFreaksListener(getUhc());
		new EnderDanceListener(getUhc());
		new EnderDragonRushListener(getUhc());
		new EntropyListener(getUhc());
		new ErraticPvPListener(getUhc());
		new EveryRoseListener(getUhc());
		new FalloutListener(getUhc());
		new FlowerPowerListener(getUhc());
		new FoodNeophobiaListener(getUhc());
		new GoldenFleeceListener(getUhc());
		new GoneFishingListener(getUhc());
		new GoToHellListener(getUhc());
		new GunsNRosesListener(getUhc());
		new HealthDonorListener(getUhc());
		new TheHobbitListener(getUhc());
		new InventorsListener(getUhc());
		new ItemHuntListener(getUhc());
		new JackpotListener(getUhc());
		new KingsListener(getUhc());
		new LightsOutListener(getUhc());
		new LiveWithRegretListener(getUhc());
		new LucyInTheSkyWithDiamondsListener(getUhc());
		new LongshotListener(getUhc());
		new LootchestListener(getUhc());
		new MoleListener(getUhc());
		new NightmareModeListener(getUhc());
		new NineSlotsListener(getUhc());
		new NoFurnaceListener(getUhc());
		new NoGoingBackListener(getUhc());
		new NoNetherListener(getUhc());
		new NoSprintListener(getUhc());
		new NotShinyEnoughListener(getUhc());
		new OneHealListener(getUhc());
		new PopeyeListener(getUhc());
		new PuppyPowerListener(getUhc());
		new PvCListener(getUhc());
		new PotentialPermanentListener(getUhc());
		new PotionSwapListener(getUhc());
		new PyrophobiaListener(getUhc());
		new PyrotechnicsListener(getUhc());
		new RandomStarterItemsListener(getUhc());
		new RiskyRetrievalListener(getUhc());
		new SelectOresListener(getUhc());
		new SharedHealthListener(getUhc());
		new SheepLoversListener(getUhc());
		new SkyHighListener(getUhc());
		new SoulBrothersListener(getUhc());
		new SwitcherooListener(getUhc());
		new TeamInventoryListener(getUhc());
		new TimberListener(getUhc());
		new TripleOresListener(getUhc());
		new TreeDropsListener(getUhc());
		new UltraParanoidListener(getUhc());
		new VeinMinerListener(getUhc());
		new XtrAppleListener(getUhc());

		registerCommands();
		registerEvents();
		registerTeams();
		
		getUhcCrateFile().initRarerityContent();

		getConfirmInventory().fillInventory();
		getTeamInventory().fillInventory();
		
		if (getUhc().isScenarios() && !getUhc().isKits()) {
			getUhc().setKits(false);
			Scenarios.getRandomScenario();
		} else if (getUhc().isKits()) {
			getUhc().setKits(true);
			getUhc().setScenarios(false);
		}

		getLocationsFile().initalizeLobbyAndArena();

		new BukkitRunnable() {
			public void run() {
				getWorldUtil().createWorld();
			}
		}.runTaskLater(getUhc(), 5);

		new BukkitRunnable() {
			public void run() {
				getWorldUtil().createNetherWorld();
			}
		}.runTaskLater(getUhc(), 5);

		new BukkitRunnable() {
			public void run() {
				new BiomeUtil();
			}
		}.runTaskLater(getUhc(), 15);

		new BukkitRunnable() {
			public void run() {
				GState.setCurrentState(GState.LOBBY);
			}
		}.runTaskLaterAsynchronously(getUhc(), 20 * 3);
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

	public ScenarioHelpFile getScenarioHelpFile() {
		return scenarioHelpFile;
	}

	public void setScenarioHelpFile(ScenarioHelpFile scenarioHelpFile) {
		this.scenarioHelpFile = scenarioHelpFile;
	}

	public BestPvETimer getBestPvETimer() {
		return bestPvETimer;
	}

	public void setBestPvETimer(BestPvETimer bestPvETimer) {
		this.bestPvETimer = bestPvETimer;
	}

	public DamageCycleTimer getDamageCycleTimer() {
		return damageCycleTimer;
	}

	public void setDamageCycleTimer(DamageCycleTimer damageCycleTimer) {
		this.damageCycleTimer = damageCycleTimer;
	}

	public EntropyTimer getEntropyTimer() {
		return entropyTimer;
	}

	public void setEntropyTimer(EntropyTimer entropyTimer) {
		this.entropyTimer = entropyTimer;
	}

	public FalloutTimer getFalloutTimer() {
		return falloutTimer;
	}

	public void setFalloutTimer(FalloutTimer falloutTimer) {
		this.falloutTimer = falloutTimer;
	}

	public GoToHellTimer getGoToHellTimer() {
		return goToHellTimer;
	}

	public void setGoToHellTimer(GoToHellTimer goToHellTimer) {
		this.goToHellTimer = goToHellTimer;
	}

	public SkyHighTimer getSkyHighTimer() {
		return skyHighTimer;
	}

	public void setSkyHighTimer(SkyHighTimer skyHighTimer) {
		this.skyHighTimer = skyHighTimer;
	}

	public HologramFile getHologramFile() {
		return hologramFile;
	}

	public void setHologramFile(HologramFile hologramFile) {
		this.hologramFile = hologramFile;
	}

	public HologramUtil getHologramUtil() {
		return hologramUtil;
	}

	public void setHologramUtil(HologramUtil hologramUtil) {
		this.hologramUtil = hologramUtil;
	}

	public NPCUtil getNpcUtil() {
		return npcUtil;
	}

	public void setNpcUtil(NPCUtil npcUtil) {
		this.npcUtil = npcUtil;
	}

	public MOTDFile getMOTDFile() {
		return mOTDFile;
	}

	public void setMOTDFile(MOTDFile mOTDFile) {
		this.mOTDFile = mOTDFile;
	}

	public RankingUtil getRankingUtil() {
		return rankingUtil;
	}

	public void setRankingUtil(RankingUtil rankingUtil) {
		this.rankingUtil = rankingUtil;
	}

	public SchematicManagerUtil getSchematicManagerUtil() {
		return schematicManagerUtil;
	}

	public void setSchematicManagerUtil(SchematicManagerUtil schematicManagerUtil) {
		this.schematicManagerUtil = schematicManagerUtil;
	}

	public SchematicListener getSchematicListener() {
		return schematicListener;
	}

	public void setSchematicListener(SchematicListener schematicListener) {
		this.schematicListener = schematicListener;
	}

	public UHCCrateFile getUhcCrateFile() {
		return uhcCrateFile;
	}

	public void setUhcCrateFile(UHCCrateFile uhcCrateFile) {
		this.uhcCrateFile = uhcCrateFile;
	}

	public CrateInventory getCrateInventory() {
		return crateInventory;
	}

	public void setCrateInventory(CrateInventory crateInventory) {
		this.crateInventory = crateInventory;
	}

	public UHCCrateListener getUhcCrateListener() {
		return uhcCrateListener;
	}

	public void setUhcCrateListener(UHCCrateListener uhcCrateListener) {
		this.uhcCrateListener = uhcCrateListener;
	}

	public NormalCrateInventory getNormalCrateInventory() {
		return normalCrateInventory;
	}

	public void setNormalCrateInventory(NormalCrateInventory normalCrateInventory) {
		this.normalCrateInventory = normalCrateInventory;
	}

	public UnCommonCrateInventory getUnCommonCrateInventory() {
		return unCommonCrateInventory;
	}

	public void setUnCommonCrateInventory(UnCommonCrateInventory unCommonCrateInventory) {
		this.unCommonCrateInventory = unCommonCrateInventory;
	}

	public RareCrateInventory getRareCrateInventory() {
		return rareCrateInventory;
	}

	public void setRareCrateInventory(RareCrateInventory rareCrateInventory) {
		this.rareCrateInventory = rareCrateInventory;
	}

	public SuperrareCrateInventory getSuperrareCrateInventory() {
		return superrareCrateInventory;
	}

	public void setSuperrareCrateInventory(SuperrareCrateInventory superrareCrateInventory) {
		this.superrareCrateInventory = superrareCrateInventory;
	}

	public EpicCrateInventory getEpicCrateInventory() {
		return epicCrateInventory;
	}

	public void setEpicCrateInventory(EpicCrateInventory epicCrateInventory) {
		this.epicCrateInventory = epicCrateInventory;
	}

	public LegendaryCrateListener getLegendaryCrateInventory() {
		return legendaryCrateInventory;
	}

	public void setLegendaryCrateInventory(LegendaryCrateListener legendaryCrateInventory) {
		this.legendaryCrateInventory = legendaryCrateInventory;
	}
}
