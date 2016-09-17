package de.alphahelix.uhc.util;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.UHCTeam;
import de.alphahelix.uhc.instances.Util;

public class TeamManagerUtil extends Util {

	private ArrayList<UHCTeam> teams;
	private int maxTeammember = 4;
	private Material m;

	public TeamManagerUtil(UHC pl) {
		super(pl);
		teams = new ArrayList<UHCTeam>();
		m = Material.getMaterial(getRegister().getTeamFile().getString("GUI.Content Material"));
	}

	public Material getIconMaterial() {
		return m;
	}

	public static TeamManagerUtil getTeamManager() {
		return UHC.getInstance().getRegister().getTeamManagerUtil();
	}

	public ArrayList<UHCTeam> getTeams() {
		return teams;
	}

	public int getMaxTeamMemberWhenNotSet() {
		return maxTeammember;
	}

	public Material getTeamItem() {
		return Material.getMaterial(getRegister().getTeamFile().getString("Team.Item").replace(" ", "_").toUpperCase());
	}

	public UHCTeam getTeamByIcon(ItemStack icon, Material m) {
		UHCTeam s = null;
		for (UHCTeam t : teams)
			if (t.getIcon(m).equals(icon))
				s = t;
		return s;
	}

	public UHCTeam getFullestTeam() {
		UHCTeam t = null;
		int i = -1;
		for (UHCTeam h : teams)
			if (i < h.getSize()) {
				i = h.getSize();
				t = h;
			}
		return t;
	}

	public UHCTeam getLowestTeam() {
		UHCTeam t = null;
		int i = Integer.MAX_VALUE;
		for (UHCTeam h : teams)
			if (i > h.getSize()) {
				i = h.getSize();
				t = h;
			}
		return t;
	}

	public UHCTeam getPercentFullestTeam() {
		UHCTeam t = null;
		double i = -1;
		for (UHCTeam h : teams)
			if (i < (h.getSize() / (double) t.getMaxPlayer())) {
				i = h.getSize();
				t = h;
			}
		return t;
	}

	public UHCTeam getPercentLowestTeam() {
		UHCTeam t = null;
		double i = Integer.MAX_VALUE;
		for (UHCTeam h : teams)
			if (i > (h.getSize() / (double) t.getMaxPlayer())) {
				i = h.getSize();
				t = h;
			}
		return t;
	}

	public UHCTeam getTeamByName(String name) {
		for (UHCTeam t : teams)
			if (t.getName().equals(name))
				return t;
		return null;
	}

	public UHCTeam getTeamByPrefix(String prefix) {
		for (UHCTeam t : teams)
			if (t.getPrefix().equals(prefix))
				return t;
		return null;
	}

	public UHCTeam getTeamByData(byte data) {
		for (UHCTeam t : teams)
			if (t.getColorData() == data)
				return t;
		return null;
	}

	public UHCTeam getTeamByInvSlot(int invSlot) {
		for (UHCTeam t : teams)
			if (t.getInvSlot() == invSlot)
				return t;
		return null;
	}

	public UHCTeam getTeamByPlayer(Player p) {
		for (UHCTeam t : teams)
			if (t.getPlayers().contains(p))
				return t;
		return null;
	}

	public int getTeamAmount() {
		return teams.size();
	}

	public void setMaxTeamMemberWhenNonSet(int maxPlayer) {
		this.maxTeammember = maxPlayer;
	}

	public UHCTeam registerTeam(String name, String prefix, byte colorData, int invSlot, boolean isColoredName) {
		UHCTeam created = new UHCTeam(name, prefix, colorData, maxTeammember, invSlot, isColoredName);
		return created;
	}

	public UHCTeam registerTeam(String name, String prefix, byte colorData, int maxPlayer, int invSlot,
			boolean isColoredName) {
		UHCTeam created = new UHCTeam(name, prefix, colorData, maxPlayer, invSlot, isColoredName);
		return created;
	}

	public UHCTeam registerTeam(String name, String prefix, byte colorData, int invSlot) {
		UHCTeam created = new UHCTeam(name, prefix, colorData, maxTeammember, invSlot, true);
		return created;
	}

	public UHCTeam registerTeam(String name, String prefix, byte colorData, int maxPlayer, int invSlot) {
		UHCTeam created = new UHCTeam(name, prefix, colorData, maxPlayer, invSlot, true);
		return created;
	}

	public void addTeam(UHCTeam team) {
		teams.add(team);
	}

	public boolean existTeam(UHCTeam team) {
		return teams.contains(team);
	}

	public UHCTeam isInOneTeam(Player p) {
		for (UHCTeam t : teams)
			if (t.isInTeam(p))
				return t;
		return null;
	}

	public void splitPlayersIntoTeams() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(isInOneTeam(p) == null) {
				getLowestTeam().addToTeam(p);
			}
		}
	}

	public void setIconMaterial(Material m) {
		this.m = m;
	}
}
