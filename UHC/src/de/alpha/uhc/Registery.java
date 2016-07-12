package de.alpha.uhc;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import de.alpha.uhc.Listener.ChatListener;
import de.alpha.uhc.Listener.CraftListener;
import de.alpha.uhc.Listener.CustomDeathListener;
import de.alpha.uhc.Listener.DeathListener;
import de.alpha.uhc.Listener.GameEndListener;
import de.alpha.uhc.Listener.InGameListener;
import de.alpha.uhc.Listener.LobbyListener;
import de.alpha.uhc.Listener.MiningListener;
import de.alpha.uhc.Listener.MotdListener;
import de.alpha.uhc.Listener.PlayerJoinListener;
import de.alpha.uhc.Listener.RankTriggerListener;
import de.alpha.uhc.Listener.SoupListener;
import de.alpha.uhc.aclasses.ARanking;
import de.alpha.uhc.aclasses.AScoreboard;
import de.alpha.uhc.aclasses.ATablist;
import de.alpha.uhc.aclasses.ATeam;
import de.alpha.uhc.aclasses.AWorld;
import de.alpha.uhc.border.Border;
import de.alpha.uhc.border.BorderManager;
import de.alpha.uhc.commands.CoinsCommand;
import de.alpha.uhc.commands.StartCommand;
import de.alpha.uhc.commands.StatsCommand;
import de.alpha.uhc.commands.UHCCommand;
import de.alpha.uhc.files.ArmorStandFile;
import de.alpha.uhc.files.CommandsFile;
import de.alpha.uhc.files.DeathMessageFile;
import de.alpha.uhc.files.DropFile;
import de.alpha.uhc.files.HologramFileManager;
import de.alpha.uhc.files.MessageFileManager;
import de.alpha.uhc.files.OptionsFileManager;
import de.alpha.uhc.files.PlayerFileManager;
import de.alpha.uhc.files.RankingFile;
import de.alpha.uhc.files.ScoreboardFile;
import de.alpha.uhc.files.SpawnFileManager;
import de.alpha.uhc.files.TeamFile;
import de.alpha.uhc.kits.GUI;
import de.alpha.uhc.kits.KitFileManager;
import de.alpha.uhc.kits.KitManager;
import de.alpha.uhc.timer.Timer;
import de.alpha.uhc.utils.ArmorStandUtil;
import de.alpha.uhc.utils.BlockUtil;
import de.alpha.uhc.utils.HoloUtil;
import de.alpha.uhc.utils.LobbyPasteUtil;
import de.alpha.uhc.utils.MapReset;
import de.alpha.uhc.utils.Regions;
import de.alpha.uhc.utils.Spectator;
import de.alpha.uhc.utils.Stats;

public class Registery {
	
	private Core pl;
	
	private ARanking ar;
	private AScoreboard as;
	private ATablist atab;
	private ATeam ateam;
	private AWorld aw;
	private Border b;
	private BorderManager bm;
	private ArmorStandFile asf;
	private CommandsFile cf;
	private DeathMessageFile dmf;
	private DropFile df;
	private HologramFileManager hfm;
	private MessageFileManager mfm;
	private OptionsFileManager ofm;
	private PlayerFileManager pfm;
	private RankingFile rf;
	private ScoreboardFile sf;
	private SpawnFileManager sfm;
	private TeamFile tf;
	private GUI g;
	private KitFileManager kfm;
	private KitManager km;
	private ChatListener chatl;
	private CraftListener craftl;
	private CustomDeathListener cdll;
	private DeathListener deathl;
	private GameEndListener gel;
	private InGameListener igl;
	private LobbyListener lobbyl;
	private MiningListener miningl;
	private MotdListener motdl;
	private PlayerJoinListener pjl;
	private RankTriggerListener rtl;
	private SoupListener soupl;
	private Timer t;
	private ArmorStandUtil asu;
	private BlockUtil bu;
	private HoloUtil hu;
	private LobbyPasteUtil lpu;
	private MapReset mr;
	private Regions r;
	private Spectator s;
	private Stats ss;
	private UHCCommand uc;
	private CoinsCommand cc;
	private StartCommand stc;
	private StatsCommand ssc;
	
	public Registery(Core c) {
		this.pl = c;
	}
	
	public void registerAll() {
		//Clazzes
		ar = new ARanking(pl);
		as = new AScoreboard(pl);
		atab = new ATablist(pl);
		ateam = new ATeam(pl);
		aw = new AWorld(pl);
		b = new Border(pl);
		bm = new BorderManager(pl);
		asf = new ArmorStandFile(pl);
		cf = new CommandsFile(pl);
		dmf = new DeathMessageFile(pl);
		df = new DropFile(pl);
		hfm = new HologramFileManager(pl);
		mfm = new MessageFileManager(pl);
		ofm = new OptionsFileManager(pl);
		pfm = new PlayerFileManager(pl);
		rf = new RankingFile(pl);
		sf = new ScoreboardFile(pl);
		sfm = new SpawnFileManager(pl);
		tf = new TeamFile(pl);
		g = new GUI(pl);
		kfm = new KitFileManager(pl);
		km = new KitManager(pl);
		chatl = new ChatListener(pl);
		craftl = new CraftListener();
		cdll = new CustomDeathListener(pl);
		deathl = new DeathListener();
		gel = new GameEndListener(pl);
		igl = new InGameListener(pl);
		lobbyl = new LobbyListener(pl);
		miningl = new MiningListener(pl);
		motdl = new MotdListener(pl);
		pjl = new PlayerJoinListener(pl);
		rtl = new RankTriggerListener(pl);
		soupl = new SoupListener(pl);
		t = new Timer(pl);
		asu = new ArmorStandUtil(pl);
		bu = new BlockUtil(pl);
		hu = new HoloUtil(pl);
		if(getAWorld().isLobbyAsSchematic()) {
			lpu = new LobbyPasteUtil(pl);
		}
		mr = new MapReset(pl);
		r = new Regions(pl);
		s = new Spectator(pl);
		ss = new Stats(pl);
		
		// Commands
		registerCommands();
		
		// Events
		registerEvents();
	}
	
	private void registerCommands() {
		uc = new UHCCommand(pl);
		pl.getCommand("uhc").setExecutor(uc);
		cc = new CoinsCommand(pl, new String[]{});
		stc = new StartCommand(pl, new String[]{});
		ssc = new StatsCommand(pl, new String[]{});
	}
	
	private void registerEvents() {
    	PluginManager p = Bukkit.getPluginManager();
    	
    	p.registerEvents(this.getPlayerJoinListener(), pl);
    	p.registerEvents(this.getInGameListener(), pl);
    	p.registerEvents(this.getMiningListener(), pl);
    	p.registerEvents(this.getDeathListener(), pl);
    	p.registerEvents(this.getLobbyListener(), pl);
    	p.registerEvents(this.getCraftListener(), pl);
    	p.registerEvents(this.getChatListener(), pl);
    	p.registerEvents(this.getSoupListener(), pl);
    	p.registerEvents(this.getCustomDeathListener(), pl);
    	p.registerEvents(this.getMapReset(), pl);
    	p.registerEvents(this.getSpectator(), pl);
    	p.registerEvents(this.getRegions(), pl);
    	p.registerEvents(this.getATeam(), pl);
    	p.registerEvents(this.getMotdListener(), pl);
    	p.registerEvents(this.getGameEndListener(), pl);
    	p.registerEvents(this.getRankTriggerListener(), pl);
    }
	
	//Getters
    
	public ARanking getARanking() {
		return ar;
	}
	
    public AScoreboard getAScoreboard() {
    	return as;
    }
    
    public ATablist getATablist() {
    	return atab;
    }
    
    public ATeam getATeam() {
    	return ateam;
	}
    
    public AWorld getAWorld() {
    	return aw;
    }
    
    public Border getBorder() {
    	return b;
    }
    
    public BorderManager getBorderManager() {
    	return bm;
    }
    
    public ArmorStandFile getArmorstandFile() {
    	return asf;
    }
    
    public CommandsFile getCommandsFile() {
    	return cf;
    }
    
    public DeathMessageFile getDeathMessagesFile() {
    	return dmf;
    }
    
    public DropFile getDropFile() {
    	return df;
    }
    
    public HologramFileManager getHologramFile() {
    	return hfm;
    }
    
    public MessageFileManager getMessageFile() {
    	return mfm;
    }
    
    public OptionsFileManager getOptionsFile() {
    	return ofm;
    }
    
    public PlayerFileManager getPlayerFile() {
    	return pfm;
    }
    
    public RankingFile getRankingFile() {
    	return rf;
    }
    
    public ScoreboardFile getScoreboardFile() {
    	return sf;
    }
    
    public SpawnFileManager getSpawnFileManager() {
    	return sfm;
    }
    
    public TeamFile getTeamFile() {
    	return tf;
    }
    
    public GUI getGui() {
    	return g;
    }
    
    public KitFileManager getKitFile() {
    	return kfm;
    }
    
    public KitManager getKitManager() {
    	return km;
    }
    
    public ChatListener getChatListener() {
    	return chatl;
    }
    
    public CraftListener getCraftListener() {
    	return craftl;
    }
    
    public CustomDeathListener getCustomDeathListener() {
    	return cdll;
    }
    
    public DeathListener getDeathListener() {
    	return deathl;
    }
    
    public GameEndListener getGameEndListener() {
    	return gel;
    }

    public InGameListener getInGameListener() {
    	return igl;
    }
    
    public LobbyListener getLobbyListener() {
    	return lobbyl;
    }
    
    public MiningListener getMiningListener() {
    	return miningl;
    }
    
    public MotdListener getMotdListener() {
    	return motdl;
    }
    
    public PlayerJoinListener getPlayerJoinListener() {
    	return pjl;
    }
    
    public RankTriggerListener getRankTriggerListener() {
    	return rtl;
    }
    
    public SoupListener getSoupListener() {
    	return soupl;
    }
    
    public Timer getTimer() {
    	return t;
    }
    
    public ArmorStandUtil getArmorStandUtil() {
    	return asu;
    }
    
    public BlockUtil getBlockUtil() {
    	return bu;
    }
    
    public HoloUtil getHoloUtil() {
    	return hu;
    }
    
    public LobbyPasteUtil getLobbyPasteUtil() {
    	return lpu;
    }
    
    public MapReset getMapReset() {
    	return mr;
    }
    
    public Regions getRegions() {
    	return r;
    }
    
    public Spectator getSpectator() {
    	return s;
    }
    
    public Stats getStats() {
    	return ss;
    }
    
    public UHCCommand getUHCCommand() {
    	return uc;
    }
    
    public CoinsCommand getCoinsCommand() {
    	return cc;
    }
    
    public StartCommand getStartCommand() {
    	return stc;
    }
    
    public StatsCommand getStatsCommand() {
    	return ssc;
    }
}
