package de.alphahelix.uhc.register;

import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.files.*;

public class UHCFileRegister {

    private static final UHC UHC_INSTANCE = UHC.getInstance();
    private static final PlayerFile PLAYER_FILE = new PlayerFile(UHC_INSTANCE);
    private static final OptionsFile OPTIONS_FILE = new OptionsFile(UHC_INSTANCE);
    private static final KitsFile KITS_FILE = new KitsFile(UHC_INSTANCE);
    private static final StatsFile STATS_FILE = new StatsFile(UHC_INSTANCE);
    private static final MessageFile MESSAGE_FILE = new MessageFile(UHC_INSTANCE);
    private static final ScenarioFile SCENARIO_FILE = new ScenarioFile(UHC_INSTANCE);
    private static final TimerFile TIMER_FILE = new TimerFile(UHC_INSTANCE);
    private static final UnitFile UNIT_FILE = new UnitFile(UHC_INSTANCE);
    private static final TablistFile TABLIST_FILE = new TablistFile(UHC_INSTANCE);
    private static final BorderFile BORDER_FILE = new BorderFile(UHC_INSTANCE);
    private static final ScoreboardFile SCOREBOARD_FILE = new ScoreboardFile(UHC_INSTANCE);
    private static final ConfirmFile CONFIRM_FILE = new ConfirmFile(UHC_INSTANCE);
    private static final LocationsFile LOCATIONS_FILE = new LocationsFile(UHC_INSTANCE);
    private static final StatusFile STATUS_FILE = new StatusFile(UHC_INSTANCE);
    private static final ScoreboardFile.ScoreboardConstructFile SCOREBOARD_CONSTRUCT_FILE = new ScoreboardFile.ScoreboardConstructFile(UHC_INSTANCE);
    private static final TeamFile TEAM_FILE = new TeamFile(UHC_INSTANCE);
    private static final SpectatorFile SPECTATOR_FILE = new SpectatorFile(UHC_INSTANCE);
    private static final DropsFile DROPS_FILE = new DropsFile(UHC_INSTANCE);
    private static final DeathmessageFile DEATHMESSAGE_FILE = new DeathmessageFile(UHC_INSTANCE);
    private static final CraftingFile CRAFTING_FILE = new CraftingFile(UHC_INSTANCE);
    private static final ScenarioFile.ScenarioHelpFile SCENARIO_HELP_FILE = new ScenarioFile.ScenarioHelpFile(UHC_INSTANCE);
    private static final MOTDFile MOTD_FILE = new MOTDFile(UHC_INSTANCE);
    private static final CrateFile CRATE_FILE = new CrateFile(UHC_INSTANCE);
    private static final LobbyFile LOBBY_FILE = new LobbyFile(UHC_INSTANCE);
    private static final AchievementFile ACHIEVEMENT_FILE = new AchievementFile(UHC_INSTANCE);
    private static final RanksFile RANKS_FILE = new RanksFile(UHC_INSTANCE);
    private static final SimpleFile[] files = new SimpleFile[]{
            PLAYER_FILE, OPTIONS_FILE, KITS_FILE, STATS_FILE, MESSAGE_FILE, SCENARIO_FILE,
            TIMER_FILE, UNIT_FILE, TABLIST_FILE, BORDER_FILE, SCOREBOARD_FILE, CONFIRM_FILE,
            LOCATIONS_FILE, STATUS_FILE, SCOREBOARD_CONSTRUCT_FILE, TEAM_FILE, SPECTATOR_FILE,
            DROPS_FILE, DEATHMESSAGE_FILE, CRAFTING_FILE, SCENARIO_HELP_FILE, MOTD_FILE,
            CRATE_FILE, LOBBY_FILE, ACHIEVEMENT_FILE, RANKS_FILE
    };

    public static PlayerFile getPlayerFile() {
        return PLAYER_FILE;
    }

    public static OptionsFile getOptionsFile() {
        return OPTIONS_FILE;
    }

    public static KitsFile getKitsFile() {
        return KITS_FILE;
    }

    public static StatsFile getStatsFile() {
        return STATS_FILE;
    }

    public static MessageFile getMessageFile() {
        return MESSAGE_FILE;
    }

    public static ScenarioFile getScenarioFile() {
        return SCENARIO_FILE;
    }

    public static TimerFile getTimerFile() {
        return TIMER_FILE;
    }

    public static UnitFile getUnitFile() {
        return UNIT_FILE;
    }

    public static TablistFile getTablistFile() {
        return TABLIST_FILE;
    }

    public static BorderFile getBorderFile() {
        return BORDER_FILE;
    }

    public static ScoreboardFile getScoreboardFile() {
        return SCOREBOARD_FILE;
    }

    public static ConfirmFile getConfirmFile() {
        return CONFIRM_FILE;
    }

    public static LocationsFile getLocationsFile() {
        return LOCATIONS_FILE;
    }

    public static StatusFile getStatusFile() {
        return STATUS_FILE;
    }

    public static ScoreboardFile.ScoreboardConstructFile getScoreboardConstructFile() {
        return SCOREBOARD_CONSTRUCT_FILE;
    }

    public static TeamFile getTeamFile() {
        return TEAM_FILE;
    }

    public static SpectatorFile getSpectatorFile() {
        return SPECTATOR_FILE;
    }

    public static DropsFile getDropsFile() {
        return DROPS_FILE;
    }

    public static DeathmessageFile getDeathmessageFile() {
        return DEATHMESSAGE_FILE;
    }

    public static CraftingFile getCraftingFile() {
        return CRAFTING_FILE;
    }

    public static ScenarioFile.ScenarioHelpFile getScenarioHelpFile() {
        return SCENARIO_HELP_FILE;
    }

    public static MOTDFile getMotdFile() {
        return MOTD_FILE;
    }

    public static CrateFile getCrateFile() {
        return CRATE_FILE;
    }

    public static LobbyFile getLobbyFile() {
        return LOBBY_FILE;
    }

    public static AchievementFile getAchievementFile() {
        return ACHIEVEMENT_FILE;
    }

    public static RanksFile getRanksFile() {
        return RANKS_FILE;
    }

    public static void register() {
        for (SimpleFile file : files) {
            file.addValues();
        }
    }
}
