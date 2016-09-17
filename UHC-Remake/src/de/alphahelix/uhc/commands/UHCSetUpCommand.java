package de.alphahelix.uhc.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.alphahelix.uhc.UHC;
import de.popokaka.alphalibary.command.SimpleCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;

public class UHCSetUpCommand extends SimpleCommand<UHC>{

	public UHCSetUpCommand(UHC plugin, String command, String description, String... aliases) {
		super(plugin, command, description, aliases);
	}

	@Override
	public boolean execute(CommandSender cs, String label, String[] args) {
		
		if (!(cs instanceof Player))
			return true;

		Player p = (Player) cs;

		if (!p.hasPermission("uhc.admin")) {
			p.sendMessage((UHC.getInstance().getPrefix() + UHC.getInstance().getRegister().getMessageFile().getColorString("No Permissions")));
			return true;
		}

		if (args.length == 0) {
			TextComponent msg = new TextComponent(
					UHC.getInstance().getPrefix() + "§7Please click §chere §7to see the wiki");
			msg.setClickEvent(new ClickEvent(Action.OPEN_URL, "https://github.com/AlphaHelixDev/UHC-Spigot/wiki"));
			p.spigot().sendMessage(msg);
			return true;
		} else if(args.length == 1) {
			if(args[0].equalsIgnoreCase("setLobby")) {
				UHC.getInstance().getRegister().getLocationsFile().setLobby(p.getLocation());
				p.sendMessage(UHC.getInstance().getPrefix() + "You've set the lobby to §ayour Location§7!");
			} else if(args[0].equalsIgnoreCase("setArena")) {
				UHC.getInstance().getRegister().getLocationsFile().setArena(p.getLocation());
				p.sendMessage(UHC.getInstance().getPrefix() + "You've set the arenaspawn to §ayour Location§7!");
			} else if(args[0].equalsIgnoreCase("setDeathmatch")) {
				UHC.getInstance().getRegister().getLocationsFile().setArena(p.getLocation());
				p.sendMessage(UHC.getInstance().getPrefix() + "You've set the deathmatch spot to §ayour Location§7!");
			} 
		} else {
			TextComponent msg = new TextComponent(
					UHC.getInstance().getPrefix() + "§7Please click §chere §7to see the wiki");
			msg.setClickEvent(new ClickEvent(Action.OPEN_URL, "https://github.com/AlphaHelixDev/UHC-Spigot/wiki"));
			p.spigot().sendMessage(msg);
			return true;
		}
		
		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender cs, String label, String[] args) {
		return null;
	}

}
