package de.alpha.uhc.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.alpha.uhc.Core;
import de.alpha.uhc.utils.Stats;
import de.popokaka.alphalibary.command.SimpleCommand;

public class StatsCommand extends SimpleCommand<Core>{
	
	private static boolean use;
	private static String err;
	
	public static boolean inUse() {
		return use;
	}

	public static void setUse(boolean use) {
		StatsCommand.use = use;
	}

	public static String getErr() {
		return err;
	}

	public static void setErr(String err) {
		StatsCommand.err = err;
	}

	public static boolean isUse() {
		return use;
	}

	public StatsCommand(Core plugin, String command, String description, String[] aliases) {
		super(plugin, command, description, aliases);
	}

	@Override
	public boolean execute(CommandSender cs, String label, String[] args) {
		if(cs instanceof Player) {
			cs.sendMessage(Core.getPrefix() + UHCCommand.getNoplayer());
			return false;
		}
		if(inUse()) {
			Player p = (Player) cs;
			new Stats(p).sendStats();
		} else {
			cs.sendMessage(Core.getPrefix()+ getErr());
		}
		return false;
	}

	@Override
	public List<String> tabComplete(CommandSender cs, String label, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
