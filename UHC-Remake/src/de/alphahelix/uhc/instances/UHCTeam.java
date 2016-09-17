package de.alphahelix.uhc.instances;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.alphahelix.uhc.UHC;

public class UHCTeam {

	private String name, prefix;
	private ArrayList<Player> players;
	private byte colorData;
	private int maxPlayer;
	private int invSlot;
	private boolean isColoredName;

	public UHCTeam(String name, String prefix, byte colorData, int maxPlayer, int invSlot, boolean isColoredName){//Erstellt ein Team mit dem Teamnamen, Farbkrzel, TeamIcon Farb byte, Maximale Playeranzahl, den TeamIcon Inventarslot, und ob die namen von den Spielern farbig sein sollen 
		this.name = name;
		this.prefix = prefix.replace("&", "§");
		this.colorData = colorData;
		this.maxPlayer = maxPlayer;
		this.invSlot = invSlot;
		this.isColoredName = isColoredName;
		this.players = new ArrayList<Player>();
		UHC.getInstance().getRegister().getTeamManagerUtil().addTeam(this);
	}

	public byte getColorData() {
		return colorData;
	}

	public boolean getColoredName() {
		return isColoredName;
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
			UHC.getInstance().getRegister().getScoreboardUtil().updateTeam(p, this);
			p.sendMessage(UHC.getInstance().getPrefix() + UHC.getInstance().getRegister().getMessageFile().getColorString("Picked team").replace("[team]", prefix + name));
		} else {
			p.sendMessage(UHC.getInstance().getPrefix() + UHC.getInstance().getRegister().getMessageFile().getColorString("Team is full"));
		}
	}

	public void removeTeam(Player p) {
		if (isInTeam(p))
			players.remove(p);
	}

	public ArrayList<String> getPlayerName() {
		ArrayList<String> playerName = new ArrayList<String>();
		for (Player p : players) {
			playerName.add(p.getName());
		}
		return playerName;
	}

	public ArrayList<String> getColoredPlayerName() {
		ArrayList<String> playerName = new ArrayList<String>();
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

	public void setColoredName(Player p) {
		p.setDisplayName(prefix + "[" + name + "] " + p.getName() + " r");
		p.setPlayerListName(prefix + p.getName());
	}
}
