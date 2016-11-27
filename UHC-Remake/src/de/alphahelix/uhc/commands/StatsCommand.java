package de.alphahelix.uhc.commands;

import de.alphahelix.uhc.Registery;
import de.alphahelix.uhc.UHC;
import de.popokaka.alphalibary.UUID.UUIDFetcher;
import de.popokaka.alphalibary.command.SimpleCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class StatsCommand extends SimpleCommand<UHC, Registery> {

	public StatsCommand(UHC plugin, Registery r, String command, String description, String... aliases) {
		super(plugin, r, command, description, aliases);
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
				cs.sendMessage(getPlugin().getPrefix() + getPlugin().getRegister().getStatsFile().getNoPlayerMessage());
				 return true;
			}
			getPlugin().getRegister().getStatsUtil().sendStats((Player) cs, Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(args[0])));
			return true;
		} else {
			cs.sendMessage(getPlugin().getPrefix() + getPlugin().getRegister().getStatsFile().getErrorMessage());
		}
		
		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender cs, String label, String[] args) {
		ArrayList<String> suggetions = new ArrayList<>();
		suggetions.add("stats");
		return suggetions;
	}

}
