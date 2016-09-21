package de.alphahelix.uhc.files;

import java.util.logging.Level;

import org.bukkit.entity.Player;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;
import de.popokaka.alphalibary.UUID.UUIDFetcher;
import de.popokaka.alphalibary.file.SimpleFile;

public class PlayerFile extends EasyFile {

	public PlayerFile(UHC uhc) {
		super("players.uhc", uhc);
		register(this);
	}
	
	public void addPlayer(Player p) {
		try {
			String uuid = UUIDFetcher.getUUID(p.getName()).toString();
			setDefault("Players."+uuid+".name", p.getName());
			setDefault("Players."+uuid+".kills", 0);
			setDefault("Players."+uuid+".deaths", 0);
			setDefault("Players."+uuid+".coins", 0);
			setDefault("Players."+uuid+".kits", "");
			setDefault("Players."+uuid+".count", getConfigurationSection("Players").getKeys(false).size());
			setDefault("Players."+uuid+".points", 0);
			if(getString("Players."+uuid+".name") != p.getName()) {
				set("Players."+uuid+".name", p.getName());
				save();
			}
		} catch (Exception e) {
			getLog().log(Level.SEVERE, "Can't add given player.", e.getMessage());
		}
	}
	
	@Override
	public SimpleFile getFile() {
		return super.getFile();
	}

	@Override
	public void addValues() {
		// TODO Auto-generated method stub
		
	}
	
	
}
