package de.alphahelix.uhc.files;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;

public class MainMessageFile extends EasyFile {

	public MainMessageFile(UHC uhc) {
		super("Main-Messages.uhc", uhc);
	}
	
	@Override
	public void addValues() {
		setDefault("Player has joined", "&7+[player]");
		setDefault("Player has left", "&7-[player]");
		setDefault("Scenario Mode", "&7This UHC server is running in &cScenario Mode&7. Due to that the server doesn't allow kits.");
		setDefault("No Permissions", "&7You don't have &cpermissions &7to execute that command!");
		setDefault("Command not found", "&7Can't find the command :C. Try again!");
		setDefault("Kit chosen", "&7You've §asuccessfully chosen &7the kit &8: &a[kit]§7!");
		setDefault("Not enough Coins", "&7You don't have &cenough &7coins to buy &7the kit &8: &a[kit]&7!");
		setDefault("Lobby time left info", "&aGame starts in &7[time] [unit]&8.");
		setDefault("Period of Peace time left info", "&aDamage in &7[time] [unit]&8.");
		setDefault("Period of Peace ended", "&cDanger&7! The damage is now &4on&7!");
		setDefault("WarmUp time left info", "&aPvP is enabled in &7[time] [unit]&8.");
		setDefault("WarmUp ended", "&cAttention&7,&c please&7! PvP is &4now enabled&7!");
		setDefault("Deathmatch time left info", "&aDeathmatch is in &7[time] [unit]&8.");
		setDefault("Deathmatch ended", "&cAttention&7,&c please&7! It's time for the &4deathmatch&7!");
		setDefault("End time left info", "&aServer will stop in &7[time] [unit]&8.");
		setDefault("End ended", "&cRestart&7!");
		setDefault("Winning message", "&7The player &a[player] &7has &awon UHC&7.");
		setDefault("Not enough players", "§7There are §cnot enough §7players online. §cRestarting Timer&7.");
		setDefault("Border has moved", "&7The Border has moved by &c[blocks] blocks&7!");
		setDefault("Picked team", "&7You've picked the team &b[team]&7!");
		setDefault("Team is full", "&7Sorry, but you &ccan't join &7this team!");
	}

}
