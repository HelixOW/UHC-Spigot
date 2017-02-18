package de.alphahelix.uhc.files;

import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.alphalibary.reflection.ReflectionUtil;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.register.UHCFileRegister;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class ScenarioFile extends SimpleFile<UHC> {

    private ArrayList<Material> added = new ArrayList<>();

    public ScenarioFile(UHC uhc) {
        super("scenario.uhc", uhc);
    }

    @Override
    public void addValues() {
        setDefault("Enabled", false);
        setDefault("Voting", true);

        setInventoryItem("Item", new ItemBuilder(Material.PAPER).setName("&cScenario&7: &c-").build(), 1);
        setDefault("GUI", "&7Vote for &4Scenarios");

        for (String scenario : getScenarioNames()) {
            setDefault("Scenarios." + scenario + ".enabled", true);
            setDefault("Scenarios." + scenario + ".name", scenario);
            setInventoryItem("Scenarios." + scenario + ".icon",
                    new ItemStack(getRandomMaterial(), 1, (short) 0), 0);
        }
    }

    public boolean isEnabled() {
        return getBoolean("Enabled");
    }

    public boolean isVoting() {
        return getBoolean("Voting");
    }

    public InventoryItem getItem(Scenarios played) {
        if (played == null)
            return getInventoryItem("Item");
        else {
            ItemStack old = getInventoryItem("Item").getItemStack();
            ItemStack is =
                    new ItemBuilder(old)
                            .setName(old.getItemMeta().getDisplayName().replace("-", UHCFileRegister.getScenarioFile()
                                    .getCustomScenarioName(played)))
                            .setLore(UHCFileRegister.getScenarioHelpFile()
                                    .getScenarioDescription(played))
                            .build();

            return new InventoryItem(is, getInventoryItem("Item").getSlot());
        }
    }

    public String getInventoryName() {
        return getColorString("GUI");
    }

    public void toggleScenarioStatus(Scenarios s) {
        override("Scenarios." + Scenarios.getRawScenarioName(s) + ".enabled",
                !getBoolean("Scenarios." + Scenarios.getRawScenarioName(s) + ".enabled"));
    }

    public String getCustomScenarioName(Scenarios s) {
        if (s == null) return "§7-";
        return getString("Scenarios." + Scenarios.getRawScenarioName(s) + ".name");
    }

    public ItemStack getScenarioItem(Scenarios s) {
        return getInventoryItem("Scenarios." + Scenarios.getRawScenarioName(s) + ".icon").getItemStack();
    }

    public Scenarios getScenarioByItem(ItemStack s) {
        return Scenarios
                .valueOf(ChatColor.stripColor(s.getItemMeta().getDisplayName()).toUpperCase().replace(" ", "_"));
    }

    public Scenarios getScenarioByCustomName(String customName) {
        for (String scenario : getScenarioNames()) {
            if (getString("Scenarios." + scenario + ".name").equals(customName))
                return Scenarios.valueOf(scenario.toUpperCase().replace(" ", "_"));
        }
        return null;
    }

    public boolean isEnabled(String scenario) {
        return isConfigurationSection("Scenarios." + scenario) && getBoolean("Scenarios." + scenario + ".enabled");
    }

    private ArrayList<String> getScenarioNames() {
        ArrayList<String> scenarioNames = new ArrayList<>();
        for (Scenarios s : Scenarios.values())
            scenarioNames.add(s.name().replace("_", " ").toLowerCase());
        return scenarioNames;
    }

    private Material getRandomMaterial() {
        int index = new Random().nextInt(Material.values().length);
        if (!ReflectionUtil.getVersion().contains("1_8")) {
            while (Material.values()[index].equals(Material.AIR)
                    || Material.values()[index].equals(Material.BURNING_FURNACE)
                    || Material.values()[index].equals(Material.getMaterial("COMMAND_CHAIN"))
                    || Material.values()[index].equals(Material.getMaterial("COMMAND_REPEATING"))
                    || Material.values()[index].equals(Material.DIODE_BLOCK_OFF)
                    || Material.values()[index].equals(Material.DIODE_BLOCK_ON)
                    || Material.values()[index].equals(Material.GLOWING_REDSTONE_ORE)
                    || Material.values()[index].equals(Material.REDSTONE_COMPARATOR_OFF)
                    || Material.values()[index].equals(Material.REDSTONE_COMPARATOR_ON)
                    || Material.values()[index].equals(Material.REDSTONE_LAMP_ON)
                    || Material.values()[index].equals(Material.REDSTONE_TORCH_OFF)
                    || Material.values()[index].equals(Material.REDSTONE_WIRE)
                    || Material.values()[index].equals(Material.STAINED_GLASS_PANE)
                    || Material.values()[index].equals(Material.PISTON_EXTENSION)
                    || Material.values()[index].equals(Material.PISTON_MOVING_PIECE)
                    || Material.values()[index].equals(Material.STANDING_BANNER)
                    || Material.values()[index].equals(Material.BREWING_STAND)
                    || Material.values()[index].equals(Material.getMaterial("CHORUS_FLOWER"))
                    || Material.values()[index].equals(Material.getMaterial("CHORUS_FRUIT"))
                    || Material.values()[index].equals(Material.getMaterial("CHORUS_FRUIT_POPPED"))
                    || Material.values()[index].equals(Material.getMaterial("CHORUS_PLANT"))
                    || Material.values()[index].equals(Material.THIN_GLASS)
                    || Material.values()[index].equals(Material.PISTON_STICKY_BASE)
                    || Material.values()[index].equals(Material.PISTON_BASE)
                    || Material.values()[index].equals(Material.SOIL)
                    || Material.values()[index].equals(Material.IRON_DOOR_BLOCK)
                    || Material.values()[index].equals(Material.SKULL)
                    || Material.values()[index].equals(Material.HUGE_MUSHROOM_1)
                    || Material.values()[index].equals(Material.HUGE_MUSHROOM_2)
                    || Material.values()[index].equals(Material.STATIONARY_WATER)
                    || Material.values()[index].equals(Material.WATER)
                    || Material.values()[index].equals(Material.STATIONARY_LAVA)
                    || Material.values()[index].equals(Material.LAVA)
                    || added.contains(Material.values()[index]))
                index = new Random().nextInt(Material.values().length);
        } else {
            while (Material.values()[index].equals(Material.AIR)
                    || Material.values()[index].equals(Material.BURNING_FURNACE)
                    || Material.values()[index].equals(Material.DIODE_BLOCK_OFF)
                    || Material.values()[index].equals(Material.DIODE_BLOCK_ON)
                    || Material.values()[index].equals(Material.GLOWING_REDSTONE_ORE)
                    || Material.values()[index].equals(Material.REDSTONE_COMPARATOR_OFF)
                    || Material.values()[index].equals(Material.REDSTONE_COMPARATOR_ON)
                    || Material.values()[index].equals(Material.REDSTONE_LAMP_ON)
                    || Material.values()[index].equals(Material.REDSTONE_TORCH_OFF)
                    || Material.values()[index].equals(Material.REDSTONE_WIRE)
                    || Material.values()[index].equals(Material.STAINED_GLASS_PANE)
                    || Material.values()[index].equals(Material.PISTON_EXTENSION)
                    || Material.values()[index].equals(Material.PISTON_MOVING_PIECE)
                    || Material.values()[index].equals(Material.STANDING_BANNER)
                    || Material.values()[index].equals(Material.BREWING_STAND)
                    || Material.values()[index].equals(Material.THIN_GLASS)
                    || Material.values()[index].equals(Material.PISTON_STICKY_BASE)
                    || Material.values()[index].equals(Material.PISTON_BASE)
                    || Material.values()[index].equals(Material.SOIL)
                    || Material.values()[index].equals(Material.IRON_DOOR_BLOCK)
                    || Material.values()[index].equals(Material.SKULL)
                    || Material.values()[index].equals(Material.HUGE_MUSHROOM_1)
                    || Material.values()[index].equals(Material.HUGE_MUSHROOM_2)
                    || Material.values()[index].equals(Material.STATIONARY_WATER)
                    || Material.values()[index].equals(Material.WATER)
                    || Material.values()[index].equals(Material.STATIONARY_LAVA)
                    || Material.values()[index].equals(Material.LAVA)
                    || added.contains(Material.values()[index]))
                index = new Random().nextInt(Material.values().length);
        }

        added.add(Material.values()[index]);
        return Material.values()[index];
    }

    public static class ScenarioHelpFile extends SimpleFile<UHC> {

        public ScenarioHelpFile(UHC uhc) {
            super("scenariohelp.uhc", uhc);
        }

        @Override
        public void addValues() {
            setDefault("ScenarioName (please use the real scenario name not your edited ones!)", "Put in your description of the scenario here. See all english descriptions here:[https://github.com/AlphaHelixDev/UHC-Spigot/blob/master/UHC-Remake/scenarios.uhc] To make a new line please add [nl]");
            setDefault("half ores", "&7Only every &csecond &7ore is &cdropped&7! \n &7Only Ores!");
        }

        public String[] getScenarioDescription(Scenarios s) {
            if (isString(Scenarios.getRawScenarioName(s))) {
                return getColorString(Scenarios.getRawScenarioName(s)).split("\n");
            }
            return new String[]{""};
        }

        public String getScenarioDescriptionAsOneString(Scenarios s) {
            if (isSet(Scenarios.getRawScenarioName(s))) {
                return getColorString(Scenarios.getRawScenarioName(s)).replace("\n", "");
            }
            return "";
        }
    }
}