package de.alphahelix.uhc.files;

import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class ScenarioFile extends EasyFile {

    private ArrayList<Material> added = new ArrayList<>();

    public ScenarioFile(UHC uhc) {
        super("scenario.uhc", uhc);
    }

    @Override
    public void addValues() {
        setDefault("Scenarios enabled", false);
        setDefault("Scenarios Item", "Paper");
        setDefault("Scenarios Item Name", "&cScenario&7: &c-");
        setDefault("Scenarios Item Slot", 1);
        setDefault("Scenario voting", true);
        setDefault("GUI", "&7Vote for &4Scenarios");
        for (String scenario : getScenarioNames()) {
            setDefault("Scenarios." + scenario + ".enabled", true);
            setDefault("Scenarios." + scenario + ".name", scenario);
            setDefault("Scenarios." + scenario + ".icon",
                    getRandomMaterial().name().toLowerCase().replace("_", " ") + ":0");
        }
    }

    public void toggleScenarioStatus(Scenarios s) {
        set("Scenarios." + Scenarios.getRawScenarioName(s) + ".enabled",
                !getBoolean("Scenarios." + Scenarios.getRawScenarioName(s) + ".enabled"));
        save();
    }

    public String getCustomScenarioName(Scenarios s) {
        if (s == null) return "§7-";
        return getString("Scenarios." + Scenarios.getRawScenarioName(s) + ".name");
    }

    public ItemStack getScenarioItem(Scenarios s) {
        String[] data = getString("Scenarios." + Scenarios.getRawScenarioName(s) + ".icon").split(":");
        Material m = Material.getMaterial(data[0].toUpperCase().replace(" ", "_"));
        ItemBuilder tr = new ItemBuilder(m);

        tr.setName(getCustomScenarioName(s));
        tr.setDamage(Short.parseShort(data[1]));
        tr.setLore(getRegister().getScenarioHelpFile().getScenarioDescription(s));

        return tr.build();
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
        while (Material.values()[index].equals(Material.AIR)
                || Material.values()[index].equals(Material.BURNING_FURNACE)
                || Material.values()[index].equals(Material.COMMAND_CHAIN)
                || Material.values()[index].equals(Material.COMMAND_REPEATING)
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
                || Material.values()[index].equals(Material.CHORUS_FLOWER)
                || Material.values()[index].equals(Material.CHORUS_FRUIT)
                || Material.values()[index].equals(Material.CHORUS_FRUIT_POPPED)
                || Material.values()[index].equals(Material.CHORUS_PLANT)
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
                || Material.values()[index].equals(Material.LAVA) || added.contains(Material.values()[index]))
            index = new Random().nextInt(Material.values().length);

        added.add(Material.values()[index]);
        return Material.values()[index];
    }
}
