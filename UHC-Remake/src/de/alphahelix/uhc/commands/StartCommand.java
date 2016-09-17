package de.alphahelix.uhc.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.alphahelix.uhc.UHC;
import de.popokaka.alphalibary.command.SimpleCommand;

public class StartCommand extends SimpleCommand<UHC> {

	public StartCommand(UHC plugin, String command, String description, String... aliases) {
		super(plugin, command, description, aliases);
	}

	@Override
	public boolean execute(CommandSender cs, String label, String[] args) {
		if (!(cs instanceof Player))
			return true;

		Player p = (Player) cs;
		
		if (!p.hasPermission("uhc.start")) {
			p.sendMessage(UHC.getInstance().getPrefix() + UHC.getInstance().getRegister().getMessageFile().getColorString("No Permissions"));
			return true;
		}

		if (args.length == 0) {
			UHC.getInstance().getRegister().getLobbyTimer().changeTime(10);
		} else if (args.length == 1) {
			UHC.getInstance().getRegister().getLobbyTimer().changeTime(Integer.parseInt(args[0]));
		} else {
			p.sendMessage(UHC.getInstance().getPrefix() + UHC.getInstance().getRegister().getMessageFile().getColorString("Command not found"));
		}

		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender cs, String label, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
