package de.alpha.uhc.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import de.alpha.uhc.Core;
import de.alpha.uhc.timer.Timer;
import de.popokaka.alphalibary.command.SimpleCommand;

public class StartCommand extends SimpleCommand<Core>{
	
	private static boolean use;
	private static String err;
	
	public static boolean inUse() {
		return use;
	}

	public static void setUse(boolean a) {
		StartCommand.use = a;
	}

	public static String getErr() {
		return err;
	}

	public static void setErr(String a) {
		StartCommand.err = a;
	}

	public StartCommand(Core plugin, String command, String description, String[] aliases) {
		super(plugin, command, description, aliases);
	}

	@Override
	public boolean execute(CommandSender cs, String label, String[] args) {
		if(StartCommand.inUse()) {
			if(cs.hasPermission("UHC.start")) {
				Timer.changeTime();
			} else {
				cs.sendMessage(Core.getPrefix() + UHCCommand.getNoperms());
			}
		} else {
			cs.sendMessage(Core.getPrefix()+ StartCommand.getErr());
		}
		return false;
	}

	@Override
	public List<String> tabComplete(CommandSender cs, String label, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
