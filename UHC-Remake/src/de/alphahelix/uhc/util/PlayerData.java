package de.alphahelix.uhc.util;

import java.util.ArrayList;

import de.alphahelix.uhc.instances.Kit;

public class PlayerData{
	
	private int kills, deaths, coins, points, rank;
	private ArrayList<String> kits;

	public PlayerData(int k, int d, int c, int p, int r, ArrayList<String> kitz) {
		this.kills = k;
		this.deaths = d;
		this.coins = c;
		this.points = p;
		this.rank = r;
		this.kits = kitz;
	}
	
	public int getKills() {
		return this.kills;
	}
	
	public int getDeaths() {
		return this.deaths;
	}
	
	public int getCoins() {
		return this.coins;
	}
	
	public int getPoints() {
		return this.points;
	}
	
	public int getRank() {
		return this.rank;
	}
	
	public ArrayList<String> getKits() {
		return this.kits;
	}

	public void addKits(Kit... k) {
		for(Kit kit : k) {
			this.kits.add(kit.getName());
		}
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
}
