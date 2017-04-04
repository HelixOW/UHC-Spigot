package de.alphahelix.uhc.instances;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.util.ScoreboardUtil;
import de.alphahelix.uhc.util.TeamManagerUtil;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class UHCTeam {

    private String name, prefix;
    private ArrayList<Player> players;
    private byte colorData;
    private int maxPlayer;
    private int invSlot;
    private boolean isColoredName;
    private Color color;

    public UHCTeam(String name, String prefix, byte colorData, int maxPlayer, int invSlot, boolean isColoredName, Color color) {
        this.name = name;
        this.prefix = prefix.replace("&", "§");
        this.colorData = colorData;
        this.maxPlayer = maxPlayer;
        this.invSlot = invSlot;
        this.isColoredName = isColoredName;
        this.color = color;
        this.players = new ArrayList<>();
        TeamManagerUtil.addTeam(this);
    }

    public byte getColorData() {
        return colorData;
    }

    public Color getColor() {
        return color;
    }

    public boolean getColoredName() {
        return isColoredName;
    }

    public void setColoredName(Player p) {
        p.setDisplayName(prefix + "[" + name + "] " + p.getName() + " §r");
        p.setPlayerListName(prefix + p.getName());
    }

    public String getName() {
        return name;
    }

    public ArrayList<Player> getPlayers() {

        return players;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getMaxPlayer() {

        return maxPlayer;
    }

    public boolean isInTeam(Player p) {
        return players.contains(p);
    }

    public int getSize() {
        return players.size();
    }

    public int getInvSlot() {
        return invSlot;
    }

    public void setIsColoredName(boolean isColoredName) {
        this.isColoredName = isColoredName;
    }

    public void addToTeam(Player p) {
        if ((getSize() <= maxPlayer) && (!isInTeam(p))) {
            players.add(p);
            if (isColoredName)
                setColoredName(p);
            ScoreboardUtil.updateTeam(p, this);
            p.sendMessage(UHC.getPrefix() + UHCFileRegister.getMessageFile().getPickedTeam(this));
        } else {
            p.sendMessage(UHC.getPrefix()
                    + UHCFileRegister.getMessageFile().getTeamIsFull());
        }
    }

    public void removeTeam(Player p) {
        if (players.contains(p))
            players.remove(p);
    }

    public ArrayList<String> getPlayerName() {
        ArrayList<String> playerName = new ArrayList<>();
        for (Player p : players) {
            playerName.add(p.getName());
        }
        return playerName;
    }

    public ArrayList<String> getColoredPlayerName() {
        ArrayList<String> playerName = new ArrayList<>();
        for (Player p : players) {
            playerName.add(prefix + p.getName());
        }
        return playerName;
    }

    public ItemStack getIcon(Material m) {
        ItemStack is = new ItemStack(m, 1, colorData);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(prefix + name);
        im.setLore(getColoredPlayerName());
        is.setItemMeta(im);
        return is;
    }
}
