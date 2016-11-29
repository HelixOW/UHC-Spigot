package de.alphahelix.uhc.files;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;
import de.popokaka.alphalibary.UUID.UUIDFetcher;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.logging.Level;

public class PlayerFile extends EasyFile {

	public PlayerFile(UHC uhc) {
		super("players.uhc", uhc);
		register(this);
	}

	public boolean containsPlayer(Player p) {
		return configContains("Player."+ UUIDFetcher.getUUID(p.getName()).toString()+".name");
	}
	
	public void addPlayer(Player p) {
		try {
			String uuid = UUIDFetcher.getUUID(p.getName()).toString();
			setDefault("Players."+uuid+".name", p.getName());
			setDefault("Players."+uuid+".kills", 0);
			setDefault("Players."+uuid+".deaths", 0);
			setDefault("Players."+uuid+".coins", 0);
			setDefault("Players."+uuid+".kits", " ");
			
			setDefault("Players."+uuid+".crates.normal", 0);
			setDefault("Players."+uuid+".crates.unccommon", 0);
			setDefault("Players."+uuid+".crates.rare", 0);
			setDefault("Players."+uuid+".crates.superrare", 0);
			setDefault("Players."+uuid+".crates.epic", 0);
			setDefault("Players."+uuid+".crates.legendary", 0);
			
			setDefault("Players."+uuid+".count", getConfigurationSection("Players").getKeys(false).size());
			setDefault("Players."+uuid+".points", 0);
			if(!Objects.equals(getString("Players." + uuid + ".name"), p.getName())) {
				set("Players."+uuid+".name", p.getName());
				save();
			}
		} catch (Exception e) {
			getLog().log(Level.SEVERE, "Can't add given player.", e.getMessage());
		}
	}

	@Override
	public void addValues() {
	}
}
