package de.alphahelix.uhc;

public enum Scenarios {
	
	HALF_ORES,
	THREE_TIME_ARROWS,
	MONSTER_INC,
	APPLE_FAMINE,
	ARMOR_V_HEALTH,
	ASSASSINS,
	ASSAULT_AND_BATTERY,
	BACKPACKS,
	BALD_CHICKEN,
	BAREBONES,
	BENCH_BLITZ,
	BEST_PVE,
	BETA_ZOMBIE,
	BIG_CRACK,
	BIOME_PARANOIA,
	BLITZ,
	BLOCKED,
	BLOCK_RUSH,
	BLOODY_DIAMONDS,
	HASHTAGBOW,
	BOMBERS,
	BOWFIGHTERS,
	CATEYES,
	CAPTAINS,
	CERTAIN_CIRCUMSTANCES,
	CHICKEN,
	CHUNK_APOCALYPSE,
	CIVILISATION,
	CITY_WORLD,
	COMPENSATION,
	CUT_CLEAN,
	DAMAGE_CYCLE,
	DAMAGE_DODGERS,
	DIAMONDLESS,
	DIMENSIONAL_INVERSION,
	DOUBLE_OR_NOTHING,
	DUNGEON_MAZE,
	EIGHT_LEGGED_FREAKS,
	ENDER_DANCE,
	ENDERDRAGON_RUSH,
	ENTROPY,
	EVERY_ROSE,
	FALLOUT,
	FLOWER_POWER,
	FOOD_NEOPHOBIA,
	GOLDEN_FLEECE,
	GONE_FISHIN,
	GO_TO_HELL,
	GUNS_N_ROSES,
	HEALTH_DONOR,
	THE_HOBBIT,
	INVENTORS,
	ITEM_HUNT,
	JACKPOT,
	KILLSWITCH,
	KINGS,
	KINGS_OF_THE_SKY,
	LIGHTS_OUT,
	LIVE_WITH_REGRET,
	LUCYS_IN_THE_SKY_WITH_DIAMONDS,
	LONGSHOTS,
	LOOTCRATES,
	MOBLE,
	MOUNTAINEERING,
	MYSTERY_TEAMS,
	NIGHTMARE_MODE,
	NINE_SLOTS,
	NO_FURNACE,
	NO_GOING_BACK,
	NO_NETHER,
	NOT_SHINY_ENOUGH,
	ONE_HEAL,
	POPEYE,
	PUPPY_POWER,
	PVC,
	POTENTIAL_PERMANENT,
	POTION_SWAP,
	PYROPHOBIA,
	PYROTECHNICS,
	RANDOM_STARTER_ITEMS,
	REWARDING_LONGSHOTS,
	RISKY_RETRIEVAL,
	SELECT_ORES,
	SELF_DIAGNOSIS,
	SHARED_HEALTH,
	SHEEP_LOVERS,
	SKY_HIGH,
	SOUL_BROTHERS,
	SWITCHEROO,
	TEAM_INVENTORY,
	TIME_BOMB,
	TRIPLE_ORES,
	TREE_DROPS,
	ULTRA_PARANOID,
	URBAN,
	URBAN_SPRAWL,
	VAST_TRACK_O_MOUNTAIN,
	XTR_APPLE,
	ZONBIES;
	
	private static Scenarios played;
//	private static final List<Scenarios> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
//	private static final int SIZE = VALUES.size();
//	private static final Random RANDOM = new Random();
	
	public static boolean isScenario(Scenarios toCompare) {
    	return toCompare == played;
    }
	
	public static Scenarios getScenario() {
		return played;
	}
	
	public static Scenarios getRandomScenario() {
//		played = VALUES.get(RANDOM.nextInt(SIZE));
		played = Scenarios.THREE_TIME_ARROWS;
		return played;
	}
	
	public static String getRawScenarioName(Scenarios s) {
		return s.name().replace("_", " ").toLowerCase();
	}
	
	public static Scenarios getScenarioByRawName(String name) {
		return valueOf(name.replace(" ", "_").toUpperCase());
	}
	
}
