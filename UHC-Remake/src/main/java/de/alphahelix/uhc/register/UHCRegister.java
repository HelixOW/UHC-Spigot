package de.alphahelix.uhc.register;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.commands.*;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.armor.ArmorListener;
import de.alphahelix.uhc.inventories.*;
import de.alphahelix.uhc.listeners.*;
import de.alphahelix.uhc.listeners.scenarios.*;
import de.alphahelix.uhc.timers.*;
import de.alphahelix.uhc.util.BorderUtil;
import de.alphahelix.uhc.util.ScoreboardUtil;
import de.alphahelix.uhc.util.TeamManagerUtil;
import de.alphahelix.uhc.util.WorldUtil;
import org.bukkit.Color;
import org.bukkit.scheduler.BukkitRunnable;

public class UHCRegister {

    private static final LobbyTimer LOBBY_TIMER = new LobbyTimer();
    private static final GraceTimer GRACE_TIMER = new GraceTimer();
    private static final WarmUpTimer WARM_UP_TIMER = new WarmUpTimer();
    private static final DeathmatchTimer DEATHMATCH_TIMER = new DeathmatchTimer();
    private static final StartDeathMatchTimer START_DEATH_MATCH_TIMER = new StartDeathMatchTimer();
    private static final RestartTimer RESTART_TIMER = new RestartTimer();
    private static final BestPvETimer BEST_PVE_TIMER = new BestPvETimer();
    private static final DamageCycleTimer DAMAGE_CYCLE_TIMER = new DamageCycleTimer();
    private static final EntropyTimer ENTROPY_TIMER = new EntropyTimer();
    private static final FalloutTimer FALLOUT_TIMER = new FalloutTimer();
    private static final GoToHellTimer GO_TO_HELL_TIMER = new GoToHellTimer();
    private static final SkyHighTimer SKY_HIGH_TIMER = new SkyHighTimer();

    private static final KitInventory KIT_INVENTORY = new KitInventory();
    private static final ConfirmInventory CONFIRM_INVENTORY = new ConfirmInventory();
    private static final TeamInventory TEAM_INVENTORY = new TeamInventory();
    private static final PreviewInventory PREVIEW_INVENTORY = new PreviewInventory();
    private static final CrateInventory CRATE_INVENTORY = new CrateInventory();
    private static final ScenarioInventory SCENARIO_INVENTORY = new ScenarioInventory();
    private static final ScenarioAdminInventory SCENARIO_ADMIN_INVENTORY = new ScenarioAdminInventory();
    private static final AchievementInventory ACHIEVEMENT_INVENTORY = new AchievementInventory();
    private static final CrateOpenInventory CRATE_OPEN_INVENTORY = new CrateOpenInventory();

    private static final KitChooseListener KIT_CHOOSE_LISTENER = new KitChooseListener();
    private static final EquipListener EQUIP_LISTENER = new EquipListener();
    private static final ChatListener CHAT_LISTENER = new ChatListener();
    private static final ConfirmListener CONFIRM_LISTENER = new ConfirmListener();
    private static final GStateListener G_STATE_LISTENER = new GStateListener();
    private static final TeamListener TEAM_LISTENER = new TeamListener();
    private static final SpectatorListener SPECTATOR_LISTENER = new SpectatorListener();
    private static final GameEndsListener GAME_ENDS_LISTENER = new GameEndsListener();
    private static final DeathListener DEATH_LISTENER = new DeathListener();
    private static final SchematicListener SCHEMATIC_LISTENER = new SchematicListener();
    private static final CrateListener CRATE_LISTENER = new CrateListener();
    private static final ScenarioListener SCENARIO_LISTENER = new ScenarioListener();
    private static final AchievementListener ACHIEVEMENT_LISTENER = new AchievementListener();

    static {
        UHCFileRegister.register();

        new ArmorListener();
        new HalfOreListener();
        new ArrowListener();
        new MonsterIncListener();
        new AppleFamineListener();
        new ArmorVHealthListener();
        new BackPackListener();
        new BareBonesListener();
        new BenchBlitzListener();
        new BestPvEListener();
        new BloodyLapisListener();
        new BiomeParanoiaListener();
        new BirdsListener();
        new BlitzListener();
        new BlockedListener();
        new BlockRushListener();
        new BloodyDiamondsListener();
        new HashtagBowListener();
        new BombersListener();
        new BowfighterListener();
        new CatsEyesListener();
        new CaptainsListener();
        new CertainCircumstancesListener();
        new ChickenListener();
        new CivilisationListener();
        new CityWorldListener();
        new CraftableTeleportationListener();
        new CompensationListener();
        new DamageDogersListener();
        new DamageCycleListener();
        new DiamondlessListener();
        new DimensonalInversionListener();
        new DoubleOrNothingListener();
        new DungeonMazeListener();
        new EightLeggedFreaksListener();
        new EnderDanceListener();
        new EnderDragonRushListener();
        new EntropyListener();
        new ErraticPvPListener();
        new EveryRoseListener();
        new FalloutListener();
        new FlowerPowerListener();
        new FoodNeophobiaListener();
        new GoldenFleeceListener();
        new GoneFishingListener();
        new GoToHellListener();
        new GunsNRosesListener();
        new HealthDonorListener();
        new TheHobbitListener();
        new HorselessListener();
        new InventorsListener();
        new ItemHuntListener();
        new JackpotListener();
        new KingsListener();
        new LightsOutListener();
        new LiveWithRegretListener();
        new LucyInTheSkyWithDiamondsListener();
        new LongshotListener();
        new LootchestListener();
        new MoleListener();
        new NightmareModeListener();
        new NineSlotsListener();
        new NoFurnaceListener();
        new NoGoingBackListener();
        new NoNetherListener();
        new NoSprintListener();
        new NotShinyEnoughListener();
        new OneHealListener();
        new PopeyeListener();
        new PuppyPowerListener();
        new PvCListener();
        new PotentialPermanentListener();
        new PotionSwapListener();
        new PyrophobiaListener();
        new PyrotechnicsListener();
        new RandomStarterItemsListener();
        new RealTimeListener();
        new RiskyRetrievalListener();
        new SelectOresListener();
        new SharedHealthListener();
        new SheepLoversListener();
        new SkyHighListener();
        new SoulBrothersListener();
        new SwitcherooListener();
        new TeamInventoryListener();
        new TimberListener();
        new TripleOresListener();
        new TreeDropsListener();
        new UltraParanoidListener();
        new VeinMinerListener();
        new WebCageListener();
        new XtrAppleListener();
        new KingsOfTheSkyListener();
    }

    private static void registerCommands() {
        new StatsCommand();
        new UHCAdminCommands();
        new UHCSetUpCommand();
        new StartCommand();
        new InfoCommand();
        new SchematicCommand();
        new RanksCommand();
    }

    private static void registerTeams() {
        if (UHCFileRegister.getTeamFile().getConfigurationSection("Teams") == null)
            return;

        for (String t : UHCFileRegister.getTeamFile().getConfigurationSection("Teams").getKeys(false)) {
            t = "Teams." + t;
            TeamManagerUtil.registerTeam(
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
        UHC.setPrefix(UHCFileRegister.getOptionsFile().getPrefix());
        UHC.setMySQLMode(UHCFileRegister.getOptionsFile().isMySQL());
        UHC.setBunggeMode(UHCFileRegister.getOptionsFile().isBungeecord());
        UHC.setLobbyServer(UHCFileRegister.getOptionsFile().getBungeecordFallbackserver());
        UHC.setDebug(UHCFileRegister.getOptionsFile().isDebug());
        UHC.setSpawnradius(UHCFileRegister.getOptionsFile().getSpawndispersal());
        UHC.setStatusMOTD(UHCFileRegister.getOptionsFile().isStatusMOTD());
        UHC.setScenarios(UHCFileRegister.getScenarioFile().isEnabled());
        UHC.setScenarioVoting(UHCFileRegister.getScenarioFile().isVoting());
        UHC.setKits(UHCFileRegister.getKitsFile().isKitmode());
        UHC.setTeams(UHCFileRegister.getTeamFile().isEnabled());
        UHC.setTracker(UHCFileRegister.getOptionsFile().isTrackerEquip());
        UHC.setTrackerName(UHCFileRegister.getOptionsFile().getTrackerItem().getItemStack().getItemMeta().getDisplayName());
        UHC.setLobbyAsSchematic(UHCFileRegister.getOptionsFile().isLobbyAsSchematic());
        UHC.setCrates(UHCFileRegister.getCrateFile().isEnabled());
        UHC.setLobby(UHCFileRegister.getLobbyFile().isLobbymode());
        UHC.setPregen(UHCFileRegister.getOptionsFile().isPregenerateWorldEnabled());
        UHC.setRestartMessage(UHCFileRegister.getOptionsFile().getRestart());

        BorderUtil.changeSize(UHCFileRegister.getBorderFile().getSize());
        TeamManagerUtil.setIconMaterial(UHCFileRegister.getTeamFile().getContentMaterial());
        ScoreboardUtil.setIden(UHCFileRegister.getScoreboardFile().getLobbyIdentifier());

        registerCommands();
        registerTeams();

        UHCFileRegister.getCrateFile().initCrates();
        UHCFileRegister.getAchievementFile().registerAchievements();
        UHCFileRegister.getRanksFile().initRanks();

        getConfirmInventory().fillInventory();
        getTeamInventory().fillInventory();
        if (UHC.isScenarios() && UHC.isScenarioVoting()) {
            getScenarioInventory().fillInventory();
        }

        if (UHC.isScenarios() && !UHC.isKits()) {
            UHC.setKits(false);
            if (!UHC.isScenarioVoting())
                Scenarios.getRandomScenario();
        } else if (UHC.isKits()) {
            UHC.setKits(true);
            UHC.setScenarios(false);
        }


        new BukkitRunnable() {
            public void run() {
                WorldUtil.createWorld();
            }
        }.runTaskLater(UHC.getInstance(), 5);
    }

    public static LobbyTimer getLobbyTimer() {
        return LOBBY_TIMER;
    }

    public static GraceTimer getGraceTimer() {
        return GRACE_TIMER;
    }

    public static WarmUpTimer getWarmUpTimer() {
        return WARM_UP_TIMER;
    }

    public static DeathmatchTimer getDeathmatchTimer() {
        return DEATHMATCH_TIMER;
    }

    public static StartDeathMatchTimer getStartDeathMatchTimer() {
        return START_DEATH_MATCH_TIMER;
    }

    public static RestartTimer getRestartTimer() {
        return RESTART_TIMER;
    }

    public static BestPvETimer getBestPveTimer() {
        return BEST_PVE_TIMER;
    }

    public static DamageCycleTimer getDamageCycleTimer() {
        return DAMAGE_CYCLE_TIMER;
    }

    public static EntropyTimer getEntropyTimer() {
        return ENTROPY_TIMER;
    }

    public static FalloutTimer getFalloutTimer() {
        return FALLOUT_TIMER;
    }

    public static GoToHellTimer getGoToHellTimer() {
        return GO_TO_HELL_TIMER;
    }

    public static SkyHighTimer getSkyHighTimer() {
        return SKY_HIGH_TIMER;
    }

    public static KitInventory getKitInventory() {
        return KIT_INVENTORY;
    }

    public static ConfirmInventory getConfirmInventory() {
        return CONFIRM_INVENTORY;
    }

    public static TeamInventory getTeamInventory() {
        return TEAM_INVENTORY;
    }

    public static PreviewInventory getPreviewInventory() {
        return PREVIEW_INVENTORY;
    }

    public static CrateInventory getCrateInventory() {
        return CRATE_INVENTORY;
    }

    public static ScenarioInventory getScenarioInventory() {
        return SCENARIO_INVENTORY;
    }

    public static ScenarioAdminInventory getScenarioAdminInventory() {
        return SCENARIO_ADMIN_INVENTORY;
    }

    public static AchievementInventory getAchievementInventory() {
        return ACHIEVEMENT_INVENTORY;
    }

    public static CrateOpenInventory getCrateOpenInventory() {
        return CRATE_OPEN_INVENTORY;
    }

    public static KitChooseListener getKitChooseListener() {
        return KIT_CHOOSE_LISTENER;
    }

    public static EquipListener getEquipListener() {
        return EQUIP_LISTENER;
    }

    public static ChatListener getChatListener() {
        return CHAT_LISTENER;
    }

    public static ConfirmListener getConfirmListener() {
        return CONFIRM_LISTENER;
    }

    public static GStateListener getgStateListener() {
        return G_STATE_LISTENER;
    }

    public static TeamListener getTeamListener() {
        return TEAM_LISTENER;
    }

    public static SpectatorListener getSpectatorListener() {
        return SPECTATOR_LISTENER;
    }

    public static GameEndsListener getGameEndsListener() {
        return GAME_ENDS_LISTENER;
    }

    public static DeathListener getDeathListener() {
        return DEATH_LISTENER;
    }

    public static SchematicListener getSchematicListener() {
        return SCHEMATIC_LISTENER;
    }

    public static CrateListener getCrateListener() {
        return CRATE_LISTENER;
    }

    public static ScenarioListener getScenarioListener() {
        return SCENARIO_LISTENER;
    }

    public static AchievementListener getAchievementListener() {
        return ACHIEVEMENT_LISTENER;
    }
}
