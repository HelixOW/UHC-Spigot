package de.alpha.uhc.commands;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.alpha.uhc.Core;
import de.alpha.uhc.files.SpawnFileManager;
import de.alpha.uhc.utils.Stats;
import de.alpha.uhc.utils.WorldPopulator;

public class UHCCommand implements CommandExecutor {
	
	public static String noplayer;
	public static String noperms;
	public static String spawnset;
	public static String lobbyset;
	
	private SpawnFileManager sfm = new SpawnFileManager();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(Core.getPrefix() + noplayer);
			return true;
		}
		
		final Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("uhc")) {
			
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("stats")) {
					new Stats(p).sendStats();
					return true;
				}
			}
			
			if(args.length == 0 || args.length > 2) {
				if(p.hasPermission("uhc.admin")) {
					p.sendMessage("§8---===UHC===---");
					p.sendMessage("§7 /uhc setSpawn - Set your Arena");
					p.sendMessage("§7 /uhc setLobby - Set your Lobby, where the Players will wait.");
					p.sendMessage("§7 /uhc createWorld [Name] - create a new random World");
					p.sendMessage("§7 /uhc reload - reload the Server to restart UHC");
					p.sendMessage("§7 /uhc stats - See your stats");
					p.sendMessage("§8---===XXX===---");
					return true;
				} else {
					p.sendMessage("§8---===UHC===---");
					p.sendMessage("§7 /uhc stats - See your Stats");
					p.sendMessage("§8---===XXX===---");
					return true;
				}
			}
			
			if(p.hasPermission("uhc.admin")) {	
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("rl")) {
						new BukkitRunnable() {
							
							@Override
							public void run() {
								Bukkit.spigot().restart();
							}
						}.runTaskLater(Core.getInstance(), 20);
						return true;
					}
					if(args[0].equalsIgnoreCase("setSpawn")) {
						
						sfm.SetSpawn(p.getLocation().getX(),
								p.getLocation().getY(),
								p.getLocation().getZ(),
								p.getWorld());
						p.sendMessage(Core.getPrefix() + spawnset);
						return true;
						
					}
					
					if(args[0].equalsIgnoreCase("setLobby")) {
						
						sfm.SetLobby(p.getLocation().getX(),
								p.getLocation().getY(),
								p.getLocation().getZ(),
								p.getWorld());
						p.sendMessage(Core.getPrefix() + lobbyset);
						return true;
						
					}
				}
				
				if(args.length == 2) {
					
					if(args[0].equalsIgnoreCase("createWorld")) {
						
						Bukkit.createWorld(new WorldCreator(args[1])
						.generateStructures(false)
						.type(WorldType.NORMAL));
						
						Bukkit.getWorld(args[1]).getPopulators().add(new WorldPopulator());
						new BukkitRunnable() {
							
							@Override
							public void run() {
								
								p.teleport(Bukkit.getWorld(args[1]).getSpawnLocation());
								
							}
						}.runTaskLater(Core.getInstance(), 200);
						return true;
					}
				}
				
			} else {
				p.sendMessage(Core.getPrefix() + noperms);
				return true;
			}
		}
		
		return false;
	}
}
