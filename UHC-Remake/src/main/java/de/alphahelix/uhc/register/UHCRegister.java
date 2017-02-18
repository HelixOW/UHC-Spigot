package de.alphahelix.uhc.register;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.commands.*;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.armor.ArmorListener;
import de.alphahelix.uhc.inventories.*;
import de.alphahelix.uhc.listeners.*;
import de.alphahelix.uhc.listeners.scenarios.*;
import de.alphahelix.uhc.timers.*;
import de.alphahelix.uhc.util.*;
import de.alphahelix.uhc.util.schematic.SchematicManagerUtil;
import org.bukkit.Color;
import org.bukkit.scheduler.BukkitRunnable;

public class UHCRegister {

    private static UHC uhc;

    private static PlayerUtil playerUtil;
    private static StatsUtil statsUtil;
    private static TablistUtil tablistUtil;
    private static BorderUtil borderUtil;
    private static ScoreboardUtil scoreboardUtil;
    private static LobbyUtil lobbyUtil;
    private static TeamManagerUtil teamManagerUtil;
    private static WorldUtil worldUtil;
    private static NPCUtil npcUtil;
    private static RankingUtil rankingUtil;
    private static SchematicManagerUtil schematicManagerUtil;

    private static LobbyTimer lobbyTimer;
    private static GraceTimer graceTimer;
    private static WarmUpTimer warmUpTimer;
    private static DeathmatchTimer deathmatchTimer;
    private static StartDeathMatchTimer startDeathmatchTimer;
    private static RestartTimer restartTimer;
    private static BestPvETimer bestPvETimer;
    private static DamageCycleTimer damageCycleTimer;
    private static EntropyTimer entropyTimer;
    private static FalloutTimer falloutTimer;
    private static GoToHellTimer goToHellTimer;
    private static SkyHighTimer skyHighTimer;

    private static KitInventory kitInventory;
    private static ConfirmInventory confirmInventory;
    private static TeamInventory teamInventory;
    private static PreviewInventory previewInventory;
    private static CrateInventory crateInventory;
    private static ScenarioInventory scenarioInventory;
    private static ScenarioAdminInventory scenarioAdminInventory;
    private static AchievementInventory achievementInventory;

    private static CrateOpenInventory crateOpenInventory;

    private static KitChooseListener kitChooseListener;
    private static RegisterListener registerListener;
    private static EquipListener equipListener;
    private static ChatListener chatListener;
    private static TimerListener timerListener;
    private static ConfirmListener confirmListener;
    private static GStateListener GStateListener;
    private static TeamListener teamListener;
    private static SpectatorListener spectatorListener;
    private static GameEndsListener gameEndsListener;
    private static DeathListener deathListener;
    private static SchematicListener schematicListener;
    private static UHCCrateListener uhcCrateListener;
    private static ScenarioListener scenarioListener;
    private static AchievementListener achievementListener;

    static {
        UHCRegister.uhc = UHC.getInstance();

        UHCFileRegister.register();

        setPlayerUtil(new PlayerUtil(getUhc()));
        setStatsUtil(new StatsUtil(getUhc()));
        setTablistUtil(new TablistUtil(getUhc()));
        setBorderUtil(new BorderUtil(getUhc()));
        setScoreboardUtil(new ScoreboardUtil(getUhc()));
        setLobbyUtil(new LobbyUtil(getUhc()));
        setTeamManagerUtil(new TeamManagerUtil(getUhc()));
        setWorldUtil(new WorldUtil(getUhc()));
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

        setCrateOpenInventory(new CrateOpenInventory(getUhc()));

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
    }

    public static KitChooseListener getKitChooseListener() {
        return kitChooseListener;
    }

    private static void setKitChooseListener(KitChooseListener kitChooseListener) {
        UHCRegister.kitChooseListener = kitChooseListener;
    }

    public static RegisterListener getRegisterListener() {
        return registerListener;
    }

    private static void setRegisterListener(RegisterListener registerListener) {
        UHCRegister.registerListener = registerListener;
    }

    public static EquipListener getEquipListener() {
        return equipListener;
    }

    private static void setEquipListener(EquipListener equipListener) {
        UHCRegister.equipListener = equipListener;
    }

    public static ChatListener getChatListener() {
        return chatListener;
    }

    private static void setChatListener(ChatListener chatListener) {
        UHCRegister.chatListener = chatListener;
    }

    public static PlayerUtil getPlayerUtil() {
        return playerUtil;
    }

    private static void setPlayerUtil(PlayerUtil playerUtil) {
        UHCRegister.playerUtil = playerUtil;
    }

    public static StatsUtil getStatsUtil() {
        return statsUtil;
    }

    private static void setStatsUtil(StatsUtil statsUtil) {
        UHCRegister.statsUtil = statsUtil;
    }

    public static KitInventory getKitInventory() {
        return kitInventory;
    }

    private static void setKitInventory(KitInventory kitInventory) {
        UHCRegister.kitInventory = kitInventory;
    }

    public static LobbyTimer getLobbyTimer() {
        return lobbyTimer;
    }

    private static void setLobbyTimer(LobbyTimer lobbyTimer) {
        UHCRegister.lobbyTimer = lobbyTimer;
    }

    public static TimerListener getTimerListener() {
        return timerListener;
    }

    private static void setTimerListener(TimerListener timerListener) {
        UHCRegister.timerListener = timerListener;
    }

    public static TablistUtil getTablistUtil() {
        return tablistUtil;
    }

    private static void setTablistUtil(TablistUtil tablistUtil) {
        UHCRegister.tablistUtil = tablistUtil;
    }

    public static BorderUtil getBorderUtil() {
        return borderUtil;
    }

    private static void setBorderUtil(BorderUtil borderUtil) {
        UHCRegister.borderUtil = borderUtil;
    }

    public static ScoreboardUtil getScoreboardUtil() {
        return scoreboardUtil;
    }

    private static void setScoreboardUtil(ScoreboardUtil scoreboardUtil) {
        UHCRegister.scoreboardUtil = scoreboardUtil;
    }

    public static ConfirmInventory getConfirmInventory() {
        return confirmInventory;
    }

    private static void setConfirmInventory(ConfirmInventory confirmInventory) {
        UHCRegister.confirmInventory = confirmInventory;
    }

    public static ConfirmListener getConfirmListener() {
        return confirmListener;
    }

    private static void setConfirmListener(ConfirmListener confirmListener) {
        UHCRegister.confirmListener = confirmListener;
    }

    public static LobbyUtil getLobbyUtil() {
        return lobbyUtil;
    }

    private static void setLobbyUtil(LobbyUtil lobbyUtil) {
        UHCRegister.lobbyUtil = lobbyUtil;
    }

    public static GStateListener getGStateListener() {
        return GStateListener;
    }

    private static void setGStateListener(de.alphahelix.uhc.listeners.GStateListener GStateListener) {
        UHCRegister.GStateListener = GStateListener;
    }

    public static TeamManagerUtil getTeamManagerUtil() {
        return teamManagerUtil;
    }

    private static void setTeamManagerUtil(TeamManagerUtil teamManagerUtil) {
        UHCRegister.teamManagerUtil = teamManagerUtil;
    }

    public static TeamInventory getTeamInventory() {
        return teamInventory;
    }

    private static void setTeamInventory(TeamInventory teamInventory) {
        UHCRegister.teamInventory = teamInventory;
    }

    public static TeamListener getTeamListener() {
        return teamListener;
    }

    private static void setTeamListener(TeamListener teamListener) {
        UHCRegister.teamListener = teamListener;
    }

    public static GraceTimer getGraceTimer() {
        return graceTimer;
    }

    private static void setGraceTimer(GraceTimer graceTimer) {
        UHCRegister.graceTimer = graceTimer;
    }

    public static WarmUpTimer getWarmUpTimer() {
        return warmUpTimer;
    }

    private static void setWarmUpTimer(WarmUpTimer warmUpTimer) {
        UHCRegister.warmUpTimer = warmUpTimer;
    }

    public static DeathmatchTimer getDeathmatchTimer() {
        return deathmatchTimer;
    }

    private static void setDeathmatchTimer(DeathmatchTimer deathmatchTimer) {
        UHCRegister.deathmatchTimer = deathmatchTimer;
    }

    public static StartDeathMatchTimer getStartDeathmatchTimer() {
        return startDeathmatchTimer;
    }

    private static void setStartDeathmatchTimer(StartDeathMatchTimer startDeathmatchTimer) {
        UHCRegister.startDeathmatchTimer = startDeathmatchTimer;
    }

    public static SpectatorListener getSpectatorListener() {
        return spectatorListener;
    }

    private static void setSpectatorListener(SpectatorListener spectatorListener) {
        UHCRegister.spectatorListener = spectatorListener;
    }

    public static GameEndsListener getGameEndsListener() {
        return gameEndsListener;
    }

    private static void setGameEndsListener(GameEndsListener gameEndsListener) {
        UHCRegister.gameEndsListener = gameEndsListener;
    }

    public static RestartTimer getRestartTimer() {
        return restartTimer;
    }

    private static void setRestartTimer(RestartTimer restartTimer) {
        UHCRegister.restartTimer = restartTimer;
    }

    public static DeathListener getDeathListener() {
        return deathListener;
    }

    private static void setDeathListener(DeathListener deathListener) {
        UHCRegister.deathListener = deathListener;
    }

    public static WorldUtil getWorldUtil() {
        return worldUtil;
    }

    private static void setWorldUtil(WorldUtil worldUtil) {
        UHCRegister.worldUtil = worldUtil;
    }

    public static PreviewInventory getPreviewInventory() {
        return previewInventory;
    }

    private static void setPreviewInventory(PreviewInventory previewInventory) {
        UHCRegister.previewInventory = previewInventory;
    }

    public static BestPvETimer getBestPvETimer() {
        return bestPvETimer;
    }

    private static void setBestPvETimer(BestPvETimer bestPvETimer) {
        UHCRegister.bestPvETimer = bestPvETimer;
    }

    public static DamageCycleTimer getDamageCycleTimer() {
        return damageCycleTimer;
    }

    private static void setDamageCycleTimer(DamageCycleTimer damageCycleTimer) {
        UHCRegister.damageCycleTimer = damageCycleTimer;
    }

    public static EntropyTimer getEntropyTimer() {
        return entropyTimer;
    }

    private static void setEntropyTimer(EntropyTimer entropyTimer) {
        UHCRegister.entropyTimer = entropyTimer;
    }

    public static FalloutTimer getFalloutTimer() {
        return falloutTimer;
    }

    private static void setFalloutTimer(FalloutTimer falloutTimer) {
        UHCRegister.falloutTimer = falloutTimer;
    }

    public static GoToHellTimer getGoToHellTimer() {
        return goToHellTimer;
    }

    private static void setGoToHellTimer(GoToHellTimer goToHellTimer) {
        UHCRegister.goToHellTimer = goToHellTimer;
    }

    public static SkyHighTimer getSkyHighTimer() {
        return skyHighTimer;
    }

    private static void setSkyHighTimer(SkyHighTimer skyHighTimer) {
        UHCRegister.skyHighTimer = skyHighTimer;
    }

    public static NPCUtil getNpcUtil() {
        return npcUtil;
    }

    private static void setNpcUtil(NPCUtil npcUtil) {
        UHCRegister.npcUtil = npcUtil;
    }

    public static RankingUtil getRankingUtil() {
        return rankingUtil;
    }

    private static void setRankingUtil(RankingUtil rankingUtil) {
        UHCRegister.rankingUtil = rankingUtil;
    }

    public static SchematicManagerUtil getSchematicManagerUtil() {
        return schematicManagerUtil;
    }

    private static void setSchematicManagerUtil(SchematicManagerUtil schematicManagerUtil) {
        UHCRegister.schematicManagerUtil = schematicManagerUtil;
    }

    public static SchematicListener getSchematicListener() {
        return schematicListener;
    }

    private static void setSchematicListener(SchematicListener schematicListener) {
        UHCRegister.schematicListener = schematicListener;
    }

    public static CrateInventory getCrateInventory() {
        return crateInventory;
    }

    public static void setCrateInventory(CrateInventory crateInventory) {
        UHCRegister.crateInventory = crateInventory;
    }

    public static CrateOpenInventory getCrateOpenInventory() {
        return crateOpenInventory;
    }

    private static void setCrateOpenInventory(CrateOpenInventory crateOpenInventory) {
        UHCRegister.crateOpenInventory = crateOpenInventory;
    }

    public static UHCCrateListener getUhcCrateListener() {
        return uhcCrateListener;
    }

    private static void setUhcCrateListener(UHCCrateListener uhcCrateListener) {
        UHCRegister.uhcCrateListener = uhcCrateListener;
    }

    public static ScenarioInventory getScenarioInventory() {
        return scenarioInventory;
    }

    private static void setScenarioInventory(ScenarioInventory scenarioInventory) {
        UHCRegister.scenarioInventory = scenarioInventory;
    }

    public static ScenarioListener getScenarioListener() {
        return scenarioListener;
    }

    private static void setScenarioListener(ScenarioListener scenarioListener) {
        UHCRegister.scenarioListener = scenarioListener;
    }

    public static ScenarioAdminInventory getScenarioAdminInventory() {
        return scenarioAdminInventory;
    }

    private static void setScenarioAdminInventory(ScenarioAdminInventory scenarioAdminInventory) {
        UHCRegister.scenarioAdminInventory = scenarioAdminInventory;
    }

    public static AchievementListener getAchievementListener() {
        return achievementListener;
    }

    private static void setAchievementListener(AchievementListener achievementListener) {
        UHCRegister.achievementListener = achievementListener;
    }

    public static AchievementInventory getAchievementInventory() {
        return achievementInventory;
    }

    private static void setAchievementInventory(AchievementInventory achievementInventory) {
        UHCRegister.achievementInventory = achievementInventory;
    }

    private static void registerCommands() {
        new StatsCommand(getUhc());
        new UHCAdminCommands(getUhc());
        new UHCSetUpCommand(getUhc());
        new StartCommand(getUhc());
        new InfoCommand(getUhc());
        new SchematicCommand(getUhc());
        new RanksCommand(getUhc());
    }

    private static void registerTeams() {
        if (UHCFileRegister.getTeamFile().getConfigurationSection("Teams") == null)
            return;

        for (String t : UHCFileRegister.getTeamFile().getConfigurationSection("Teams").getKeys(false)) {
            t = "Teams." + t;
            getTeamManagerUtil().registerTeam(
                    UHCFileRegister.getTeamFile().getColorString(t + ".name"),
                    UHCFileRegister.getTeamFile().getColorString(t + ".prefix"),
                    (byte) UHCFileRegister.getTeamFile().getInt(t + ".data"),
                    UHCFileRegister.getTeamFile().getInt(t + ".max Players"),
                    UHCFileRegister.getTeamFile().getInt(t + ".slot"),
                    UHCFileRegister.getTeamFile().getBoolean(t + ".colored Name"),
                    Color.fromRGB(UHCFileRegister.getTeamFile().getInt(t + ".color.red"), UHCFileRegister.getTeamFile().getInt(t + ".color.green"), UHCFileRegister.getTeamFile().getInt(t + ".color.blue")));
        }
    }

    public static void registerAll() {
        getUhc().setPrefix(UHCFileRegister.getOptionsFile().getPrefix());
        getUhc().setMySQLMode(UHCFileRegister.getOptionsFile().isMySQL());
        getUhc().setBunggeMode(UHCFileRegister.getOptionsFile().isBungeecord());
        getUhc().setLobbyServer(UHCFileRegister.getOptionsFile().getBungeecordFallbackserver());
        getUhc().setSoup(UHCFileRegister.getOptionsFile().isSoup());
        getUhc().setDebug(UHCFileRegister.getOptionsFile().isDebug());
        getUhc().setSpawnradius(UHCFileRegister.getOptionsFile().getSpawndispersal());
        getUhc().setStatusMOTD(UHCFileRegister.getOptionsFile().isStatusMOTD());
        getUhc().setScenarios(UHCFileRegister.getScenarioFile().isEnabled());
        getUhc().setScenarioVoting(UHCFileRegister.getScenarioFile().isVoting());
        getUhc().setKits(UHCFileRegister.getKitsFile().isKitmode());
        getUhc().setTeams(UHCFileRegister.getTeamFile().isEnabled());
        getUhc().setTracker(UHCFileRegister.getOptionsFile().isTrackerEquip());
        getUhc().setTrackerName(UHCFileRegister.getOptionsFile().getTrackerItem().getItemStack().getItemMeta().getDisplayName());
        getUhc().setLobbyAsSchematic(UHCFileRegister.getOptionsFile().isLobbyAsSchematic());
        getUhc().setCrates(UHCFileRegister.getCrateFile().isEnabled());
        getUhc().setLobby(UHCFileRegister.getLobbyFile().isLobbymode());
        getUhc().setPregen(UHCFileRegister.getOptionsFile().isPregenerateWorldEnabled());
        getUhc().setRestartMessage(UHCFileRegister.getOptionsFile().getRestart());

        getBorderUtil().changeSize(UHCFileRegister.getBorderFile().getSize());

        registerCommands();
        registerTeams();

        UHCFileRegister.getCrateFile().initCrates();
        UHCFileRegister.getAchievementFile().registerAchievements();
        UHCFileRegister.getRanksFile().initRanks();

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


        new BukkitRunnable() {
            public void run() {
                getWorldUtil().createWorld();
                getWorldUtil().createNetherWorld();
            }
        }.runTaskLater(getUhc(), 5);
    }

    public static UHC getUhc() {
        return uhc;
    }
}
