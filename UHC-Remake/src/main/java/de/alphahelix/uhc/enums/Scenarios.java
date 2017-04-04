package de.alphahelix.uhc.enums;

import de.alphahelix.uhc.register.UHCFileRegister;
import org.bukkit.ChatColor;

import java.util.*;

public enum Scenarios {

    NONE, HALF_ORES, THREE_TIME_ARROWS, MONSTER_INC, APPLE_FAMINE, ARMOR_V_HEALTH, ASSAULT_AND_BATTERY, BACKPACKS, BALD_CHICKEN, BAREBONES, BENCH_BLITZ, BEST_PVE, BETA_ZOMBIE, BLOODY_LAPIS, BIOME_PARANOIA, BIRDS, BLITZ, BLOCKED, BLOCK_RUSH, BLOODY_DIAMONDS, HASHTAGBOW, BOMBERS, BOWFIGHTERS, CATEYES, CAPTAINS, CERTAIN_CIRCUMSTANCES, CHICKEN, CHUNK_APOCALYPSE, CIVILISATION, CITYWORLD, COMPENSATION, CUT_CLEAN, CRAFTABLE_TELEPORTATION, DAMAGE_CYCLE, DAMAGE_DODGERS, DIAMONDLESS, DIMENSIONAL_INVERSION, DOUBLE_OR_NOTHING, DUNGEON_MAZE, EIGHT_LEGGED_FREAKS, ENDER_DANCE, ENDERDRAGON_RUSH, ENTROPY, ERRATIC_PVP, EVERY_ROSE, FALLOUT, FLOWER_POWER, FOOD_NEOPHOBIA, GOLDEN_FLEECE, GONE_FISHIN, GO_TO_HELL, GUNS_N_ROSES, HEALTH_DONOR, THE_HOBBIT, HORSELESS, INVENTORS, ITEM_HUNT, JACKPOT, KILLSWITCH, KINGS, KINGS_OF_THE_SKY, LIGHTS_OUT, LIVE_WITH_REGRET, LUCYS_IN_THE_SKY_WITH_DIAMONDS, LONGSHOTS, LOOTCRATES, MOLE, MOUNTAINEERING, MINECRAFT, NIGHTMARE_MODE, NINE_SLOTS, NO_FURNACE, NO_GOING_BACK, NO_NETHER, NO_SPRINT, NOT_SHINY_ENOUGH, ONE_HEAL, POPEYE, PUPPY_POWER, PVC, POTENTIAL_PERMANENT, POTION_SWAP, PYROPHOBIA, PYROTECHNICS, RANDOM_STARTER_ITEMS, REAL_TIME, REWARDING_LONGSHOTS, RISKY_RETRIEVAL, SELECT_ORES, SHARED_HEALTH, SHEEP_LOVERS, SKY_HIGH, SOUL_BROTHERS, SWITCHEROO, TEAM_INVENTORY, TIMBER, TIME_BOMB, TRIPLE_ORES, TREE_DROPS, ULTRA_PARANOID, URBAN, VAST_TRACK_O_MOUNTAIN, VEIN_MINER, WEB_CAGE, XTR_APPLE, ZOMBIES;

    private static final List<Scenarios> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();
    private static Scenarios played;

    public static boolean isScenario(Scenarios toCompare) {
        return toCompare == played;
    }

    public static boolean isPlayedAndEnabled(Scenarios toCompare) {
        if (UHCFileRegister.getScenarioFile().isEnabled(Scenarios.getRawScenarioName(toCompare))) {
            if (Scenarios.isScenario(toCompare)) {
                return true;
            }
        }
        return false;
    }

    public static Scenarios getScenario() {
        return played;
    }

    public static void setPlayedScenario(Scenarios s) {
        played = s;
    }

    public static Scenarios getRandomScenario() {
        if (UHCFileRegister.getScenarioFile().isVoting())
            return NONE;
        played = VALUES.get(RANDOM.nextInt(SIZE));
        while (!UHCFileRegister.getScenarioFile().isEnabled(getRawScenarioName(played))) {
            played = VALUES.get(RANDOM.nextInt(SIZE));
        }
        return played;
    }

    public static String getRawScenarioName(Scenarios s) {
        if (s == null) return ChatColor.GRAY + "-";
        return s.name().replace("_", " ").toLowerCase();
    }

    public static Scenarios getScenarioByRawName(String name) {
        return valueOf(name.replace(" ", "_").toUpperCase());
    }

    public static Scenarios[] getScenariosToChoose() {
        ArrayList<Scenarios> tr = new ArrayList<>();
        for (int i = 0; i < Scenarios.values().length; i++) {
            if (tr.size() < 4) {
                Scenarios tA = Scenarios.values()[RANDOM.nextInt(Scenarios.values().length)];
                if (UHCFileRegister.getScenarioFile().isEnabled(Scenarios.getRawScenarioName(tA)))
                    if (!tr.contains(tA))
                        tr.add(tA);
            }
        }
        return tr.toArray(new Scenarios[tr.size()]);
    }
}
