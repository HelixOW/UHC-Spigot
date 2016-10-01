package de.alphahelix.uhc.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.alphahelix.uhc.UHC;
import de.popokaka.alphalibary.UUID.UUIDFetcher;
import de.popokaka.alphalibary.command.SimpleCommand;

public class StatsCommand extends SimpleCommand<UHC> {

	public StatsCommand(UHC plugin, String command, String description, String... aliases) {
		super(plugin, command, description, aliases);
	}

	@Override
	public boolean execute(CommandSender cs, String label, String[] args) {
		if(!(cs instanceof Player)) return true;
		
		if(args.length == 0) {
			getPlugin().getRegister().getStatsUtil().sendStats((Player) cs, (Player) cs);
			return true;
		}
		if(args.length == 1)  {
			if(Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(args[0])) == null) {
				((Player)cs).sendMessage(getPlugin().getPrefix() + getPlugin().getRegister().getStatsFile().getNoPlayerMessage());
				 return true;
			}
			getPlugin().getRegister().getStatsUtil().sendStats((Player) cs, Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(args[0])));
			return true;
		} else {
			((Player)cs).sendMessage(getPlugin().getPrefix() + getPlugin().getRegister().getStatsFile().getErrorMessage());
		}
		
		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender cs, String label, String[] args) {
		ArrayList<String> suggetions = new ArrayList<String>();
		suggetions.add("stats");
		return suggetions;
	}

}
