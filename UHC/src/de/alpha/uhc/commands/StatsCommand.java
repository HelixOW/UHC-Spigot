package de.alpha.uhc.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.alpha.uhc.Core;
import de.alpha.uhc.utils.Stats;
import de.popokaka.alphalibary.command.SimpleCommand;

public class StatsCommand extends SimpleCommand<Core>{
	
	private static boolean us;
	private static String er;
	
	public static boolean inUs() {
		return us;
	}

	public static void setUs(boolean a) {
		us = a;
	}

	public static String getEr() {
		return er;
	}

	public static void setEr(String a) {
		StatsCommand.er = a;
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
		System.out.println(us);
		if(inUs() == true) {
			Player p = (Player) cs;
			new Stats(p).sendStats();
		} else {
			cs.sendMessage(Core.getPrefix()+ getEr());
		}
		return false;
	}

	@Override
	public List<String> tabComplete(CommandSender cs, String label, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
