package de.alphahelix.uhc.files;

import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.alphalibary.item.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;

public class TeamFile extends SimpleFile {

    public TeamFile() {
        super("plugins/UHC-Remake","teams.uhc");
    }

    public void addValues() {
        setDefault("Enabled", true);
        setDefault("GUI.Name", "&bTeams");
        setMaterial("GUI.Content material", Material.WOOL);

        setInventoryItem("Item", new ItemBuilder(Material.BED).setName("&bTeams").build(), 2);

        setDefault("Teamchat symbol", "@");
        addTeam("Preview", "&7", 2, 1, 0, true, Color.YELLOW, null);
    }

    public boolean isEnabled() {
        return getBoolean("Enabled");
    }

    public String getInventoryName() {
        return getColorString("GUI.Name");
    }

    public Material getContentMaterial() {
        return getMaterial("GUI.Content material");
    }

    public InventoryItem getItem() {
        return getInventoryItem("Item");
    }

    public String getTeamchatSymbol() {
        return getString("Teamchat symbol");
    }

    public void setTeamMemberNPC(String teamName, int pos, Location loc) {
        setLocation("Teams." + teamName + ".locations." + pos, loc);
    }

    public Location getTeamMemberNPC(String teamName, int pos) {
        return getLocation("Teams." + teamName + ".locations." + pos, true);
    }

    public void addTeam(String name, String prefix, int maxPlayer, int data, int slot, boolean colored, Color c,
                        ArrayList<Location> armorStands) {
        setDefault("Teams." + name + ".name", name);
        setDefault("Teams." + name + ".prefix", prefix);
        setDefault("Teams." + name + ".data", data);
        setDefault("Teams." + name + ".max Players", maxPlayer);
        setDefault("Teams." + name + ".slot", slot);
        setDefault("Teams." + name + ".colored Name", colored);

        setDefault("Teams." + name + ".color.red", c.getRed());
        setDefault("Teams." + name + ".color.green", c.getGreen());
        setDefault("Teams." + name + ".color.blue", c.getBlue());
        if (armorStands != null)
            if (armorStands.size() == maxPlayer)
                for (int max = 0; max < maxPlayer; max++)
                    setLocation("Teams." + name + ".locations." + max, armorStands.get(max));
    }
}
