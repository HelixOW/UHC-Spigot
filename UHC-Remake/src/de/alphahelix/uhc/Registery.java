package de.alphahelix.uhc;

import de.alphahelix.alphalibary.reflection.ReflectionUtil;
import de.alphahelix.uhc.commands.*;
import de.alphahelix.uhc.events.armor.ArmorListener;
import de.alphahelix.uhc.files.*;
import de.alphahelix.uhc.instances.EasyFile;
import de.alphahelix.uhc.inventories.*;
import de.alphahelix.uhc.inventories.crates.*;
import de.alphahelix.uhc.listeners.*;
import de.alphahelix.uhc.listeners.scenarios.*;
import de.alphahelix.uhc.timers.*;
import de.alphahelix.uhc.util.*;
import de.alphahelix.uhc.util.schematic.SchematicManagerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

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
    private ScenarioInventory scenarioInventory;
    private ScenarioAdminInventory scenarioAdminInventory;
    private AchievementInventory achievementInventory;

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
    private LobbyFile lobbyFile;
    private AchievementFile achievementFile;
    private RanksFile ranksFile;

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
    private ScenarioListener scenarioListener;
    private AchievementListener achievementListener;

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
        new StatsCommand(getUhc(), this);
        new UHCAdminCommands(getUhc(), this);
        new UHCSetUpCommand(getUhc(), this);
        new StartCommand(getUhc(), this);
        new InfoCommand(getUhc(), this);
        new SchematicCommand(getUhc(), this);
        new RanksCommand(getUhc(), this);
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
        setLobbyFile(new LobbyFile(getUhc()));
        setAchievementFile(new AchievementFile(getUhc()));
        setRanksFile(new RanksFile(getUhc()));

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
        getUhc().setScenarioVoting(getScenarioFile().getBoolean("Scenario voting"));
        getUhc().setKits(getKitsFile().getBoolean("Kits"));
        getUhc().setTeams(getTeamFile().getBoolean("Teams enabled"));
        getUhc().setTracker(getMainOptionsFile().getBoolean("Tracker.euip"));
        getUhc().setTrackerName(getMainOptionsFile().getColorString("Tracker.name"));
        getUhc().setLobbyAsSchematic(getMainOptionsFile().getBoolean("Lobby.as schematic"));
        getUhc().setCrates(getUhcCrateFile().getBoolean("Crates"));
        getUhc().setLobby(getLobbyFile().getBoolean("Lobby"));
        getUhc().setPregen(getMainOptionsFile().getBoolean("Pregenerate World.enabled"));
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
        setScenarioInventory(new ScenarioInventory(getUhc()));
        setScenarioAdminInventory(new ScenarioAdminInventory(getUhc()));
        setAchievementInventory(new AchievementInventory(getUhc()));

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
        setScenarioListener(new ScenarioListener(getUhc()));
        setAchievementListener(new AchievementListener(getUhc()));

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
        new BirdsListener(getUhc());
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
        new CraftableTeleportationListener(getUhc());
        new CompensationListener(getUhc());
        new DamageDogersListener(getUhc());
        new DamageCycleListener(getUhc());
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
        new HorselessListener(getUhc());
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
        new RealTimeListener(getUhc());
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
        new WebCageListener(getUhc());
        new XtrAppleListener(getUhc());

        registerCommands();
        registerEvents();
        registerTeams();

        getUhcCrateFile().initRarerityContent();
        getAchievementFile().registerAchievements();
        getRanksFile().initRanks();

        getConfirmInventory().fillInventory();
        getTeamInventory().fillInventory();
        if (getUhc().isScenarios() && getUhc().isScenarioVoting()) {
            getScenarioInventory().fillInventory();
        }

        if (getUhc().isScenarios() && !getUhc().isKits()) {
            getUhc().setKits(false);
            if (!getUhc().isScenarioVoting())
                Scenarios.getRandomScenario();
        } else if (getUhc().isKits()) {
            getUhc().setKits(true);
            getUhc().setScenarios(false);
        }

        getUhc().setOneNine(ReflectionUtil.getVersion().contains("1_9")
                || ReflectionUtil.getVersion().contains("1_10")
                || ReflectionUtil.getVersion().contains("1_11"));

        getLocationsFile().initalizeLobbyAndArena();

        new BukkitRunnable() {
            public void run() {
                getWorldUtil().createWorld();
            }
        }.runTaskLater(getUhc(), 5);

        new BukkitRunnable() {
            public void run() {
                getWorldUtil().createNetherWorld();
                if (getUhc().isPregen())
                    getWorldUtil().preGenerateWorld();
                else
                    new BukkitRunnable() {
                        public void run() {
                            new BiomeUtil();
                        }
                    }.runTaskLater(getUhc(), 15);
            }
        }.runTaskLater(getUhc(), 5);
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

    private void setPlayerFile(PlayerFile playerFile) {
        this.playerFile = playerFile;
    }

    public ArrayList<EasyFile> getEasyFiles() {
        return easyFiles;
    }

    private void setEasyFiles(ArrayList<EasyFile> easyFiles) {
        this.easyFiles = easyFiles;
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

    public LobbyFile getLobbyFile() {
        return lobbyFile;
    }

    public void setLobbyFile(LobbyFile lobbyFile) {
        this.lobbyFile = lobbyFile;
    }

    public ScenarioInventory getScenarioInventory() {
        return scenarioInventory;
    }

    public void setScenarioInventory(ScenarioInventory scenarioInventory) {
        this.scenarioInventory = scenarioInventory;
    }

    public ScenarioListener getScenarioListener() {
        return scenarioListener;
    }

    public void setScenarioListener(ScenarioListener scenarioListener) {
        this.scenarioListener = scenarioListener;
    }

    public ScenarioAdminInventory getScenarioAdminInventory() {
        return scenarioAdminInventory;
    }

    public void setScenarioAdminInventory(ScenarioAdminInventory scenarioAdminInventory) {
        this.scenarioAdminInventory = scenarioAdminInventory;
    }

    public AchievementFile getAchievementFile() {
        return achievementFile;
    }

    public void setAchievementFile(AchievementFile achievementFile) {
        this.achievementFile = achievementFile;
    }

    public AchievementListener getAchievementListener() {
        return achievementListener;
    }

    public void setAchievementListener(AchievementListener achievementListener) {
        this.achievementListener = achievementListener;
    }

    public AchievementInventory getAchievementInventory() {
        return achievementInventory;
    }

    public void setAchievementInventory(AchievementInventory achievementInventory) {
        this.achievementInventory = achievementInventory;
    }

    public RanksFile getRanksFile() {
        return ranksFile;
    }

    public void setRanksFile(RanksFile ranksFile) {
        this.ranksFile = ranksFile;
    }
}
