package de.alpha.uhc.files;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import de.alpha.uhc.Core;
import de.alpha.uhc.Registery;
import de.popokaka.alphalibary.file.SimpleFile;

public class RankingFile {
	
	private Core pl;
	private Registery r;
	
	public RankingFile(Core c) {
		this.pl = c;
		this.r = pl.getRegistery();
	}
	
    private  SimpleFile getRankingFile() {
        return new SimpleFile("plugins/UHC", "ranking.uhc");
    }

    private  final SimpleFile file = getRankingFile();
    
    public void addLines() {
    	file.setDefault("Line 1", "&lRank #[rank]");
    	file.setDefault("Line 2", "&7[player]");
    	file.setDefault("Line 3", " ");
    	file.setDefault("Line 4", "[points] Points");
    	
    	file.setDefault("Stats.player", "§6Player§7: ");
    	file.setDefault("Stats.kills", "§6Kills§7: §a");
    	file.setDefault("Stats.deaths", "§6Deaths§7: §c");
    	file.setDefault("Stats.coins", "§6Coins§7: §c");
    	file.setDefault("Stats.points", "§6Points§7: §c");
    	file.setDefault("Stats.kits", "§6Kits§7: §c");
    }
    
    public void addSign(Location l, int place) {
    	file.setDefault("Locations."+place+" Place.x", l.getBlockX());
    	file.setDefault("Locations."+place+" Place.y", l.getBlockY());
    	file.setDefault("Locations."+place+" Place.z", l.getBlockZ());
    	file.setDefault("Locations."+place+" Place.world", l.getWorld().getName());
    }
    
    public void loadLines() {
    	r.getARanking().setLine1(file.getColorString("Line 1"));
    	r.getARanking().setLine2(file.getColorString("Line 2"));
    	r.getARanking().setLine3(file.getColorString("Line 3"));
    	r.getARanking().setLine4(file.getColorString("Line 4"));
    	
    	r.getStats().setPlayerM(file.getColorString("Stats.player"));
    	r.getStats().setKillsM(file.getColorString("Stats.kills"));
    	r.getStats().setDeathsM(file.getColorString("Stats.deaths"));
    	r.getStats().setCoinsM(file.getColorString("Stats.coins"));
    	r.getStats().setPointsM(file.getColorString("Stats.points"));
    	r.getStats().setKitsM(file.getColorString("Stats.kits"));
    }
    
    public void loadSigns() {
    	if(!(file.isConfigurationSection("Locations"))) return;
    	int x1 = file.getInt("Locations.1 Place.x");
    	int x2 = file.getInt("Locations.2 Place.x");
    	int x3 = file.getInt("Locations.3 Place.x");
    	
    	int y1 = file.getInt("Locations.1 Place.y");
    	int y2 = file.getInt("Locations.2 Place.y");
    	int y3 = file.getInt("Locations.3 Place.y");
    	
    	int z1 = file.getInt("Locations.1 Place.z");
    	int z2 = file.getInt("Locations.2 Place.z");
    	int z3 = file.getInt("Locations.3 Place.z");
    	
    	String world = file.getString("Locations.1 Place.world");
    	
    	r.getARanking().setFirstPlace(new Location(Bukkit.getWorld(world), x1, y1, z1));
    	r.getARanking().setSecondPlace(new Location(Bukkit.getWorld(world), x2, y2, z2));
    	r.getARanking().setThirdPlace(new Location(Bukkit.getWorld(world), x3, y3, z3));
    }
}
