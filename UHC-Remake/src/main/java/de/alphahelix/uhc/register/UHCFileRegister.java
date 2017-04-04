package de.alphahelix.uhc.register;

import de.alphahelix.alphaapi.file.SimpleFile;
import de.alphahelix.uhc.files.*;

public class UHCFileRegister {

    private static final PlayerStatsFile PLAYER_STATS_FILE = new PlayerStatsFile();
    private static final OptionsFile OPTIONS_FILE = new OptionsFile();
    private static final KitsFile KITS_FILE = new KitsFile();
    private static final StatsFile STATS_FILE = new StatsFile();
    private static final MessageFile MESSAGE_FILE = new MessageFile();
    private static final ScenarioFile SCENARIO_FILE = new ScenarioFile();
    private static final TimerFile TIMER_FILE = new TimerFile();
    private static final UnitFile UNIT_FILE = new UnitFile();
    private static final TablistFile TABLIST_FILE = new TablistFile();
    private static final BorderFile BORDER_FILE = new BorderFile();
    private static final ScoreboardFile SCOREBOARD_FILE = new ScoreboardFile();
    private static final ConfirmFile CONFIRM_FILE = new ConfirmFile();
    private static final LocationsFile LOCATIONS_FILE = new LocationsFile();
    private static final StatusFile STATUS_FILE = new StatusFile();
    private static final ScoreboardFile.ScoreboardConstructFile SCOREBOARD_CONSTRUCT_FILE = new ScoreboardFile.ScoreboardConstructFile();
    private static final TeamFile TEAM_FILE = new TeamFile();
    private static final SpectatorFile SPECTATOR_FILE = new SpectatorFile();
    private static final DropsFile DROPS_FILE = new DropsFile();
    private static final DeathmessageFile DEATHMESSAGE_FILE = new DeathmessageFile();
    private static final CraftingFile CRAFTING_FILE = new CraftingFile();
    private static final ScenarioFile.ScenarioHelpFile SCENARIO_HELP_FILE = new ScenarioFile.ScenarioHelpFile();
    private static final MOTDFile MOTD_FILE = new MOTDFile();
    private static final CrateFile CRATE_FILE = new CrateFile();
    private static final LobbyFile LOBBY_FILE = new LobbyFile();
    private static final AchievementFile ACHIEVEMENT_FILE = new AchievementFile();
    private static final RanksFile RANKS_FILE = new RanksFile();
    private static final SimpleFile[] files = new SimpleFile[]{
            PLAYER_STATS_FILE, OPTIONS_FILE, KITS_FILE, STATS_FILE, MESSAGE_FILE, SCENARIO_FILE,
            TIMER_FILE, UNIT_FILE, TABLIST_FILE, BORDER_FILE, SCOREBOARD_FILE, CONFIRM_FILE,
            LOCATIONS_FILE, STATUS_FILE, SCOREBOARD_CONSTRUCT_FILE, TEAM_FILE, SPECTATOR_FILE,
            DROPS_FILE, DEATHMESSAGE_FILE, CRAFTING_FILE, SCENARIO_HELP_FILE, MOTD_FILE,
            CRATE_FILE, LOBBY_FILE, ACHIEVEMENT_FILE, RANKS_FILE
    };

    public static PlayerStatsFile getPlayerFile() {
        return PLAYER_STATS_FILE;
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
