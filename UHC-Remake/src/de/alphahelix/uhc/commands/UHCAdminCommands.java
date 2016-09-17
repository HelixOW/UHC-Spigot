package de.alphahelix.uhc.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Kit;
import de.popokaka.alphalibary.command.SimpleCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class UHCAdminCommands extends SimpleCommand<UHC> {

	public UHCAdminCommands(UHC plugin, String command, String description, String... aliases) {
		super(plugin, command, description, aliases);
	}

	@Override
	public boolean execute(CommandSender cs, String label, String[] args) {
		if (!(cs instanceof Player))
			return true;

		Player p = (Player) cs;

		if (!p.hasPermission("uhc.admin")) {
			p.sendMessage(UHC.getInstance().getPrefix()
					+ UHC.getInstance().getRegister().getMessageFile().getColorString("No Permissions"));
			return true;
		}

		if (args.length == 0) {
			TextComponent msg = new TextComponent(
					UHC.getInstance().getPrefix() + "§7Please click §chere §7to see the wiki");
			msg.setClickEvent(new ClickEvent(Action.OPEN_URL, "https://github.com/AlphaHelixDev/UHC-Spigot/wiki"));
			p.spigot().sendMessage(msg);
			return true;
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("push")) {
				UHC.getInstance().getRegister().getStatsUtil().pushCacheToBackUp();
			} else if (args[0].equalsIgnoreCase("startLobbyTimer")) {
				if (!UHC.getInstance().getRegister().getLobbyTimer().isRunning()) {
					UHC.getInstance().getRegister().getLobbyTimer().startLobbyCountdown();
					p.sendMessage(UHC.getInstance().getPrefix() + "§7The lobbytimer now continues!");
				} else {
					p.sendMessage(UHC.getInstance().getPrefix() + "§7The Lobbytimer is already running!");
				}
			} else if (args[0].equalsIgnoreCase("stopLobbyTimer")) {
				UHC.getInstance().getRegister().getLobbyTimer().stopTimer();
			} else if (args[0].equalsIgnoreCase("build")) {
				if (!UHC.getInstance().getRegister().getLobbyUtil().hasBuildPermission(p)) {
					UHC.getInstance().getRegister().getLobbyUtil().grandBuildPermission(p);
					p.sendMessage(UHC.getInstance().getPrefix() + "§aGranted §7build permission for yourself!");
				} else {
					UHC.getInstance().getRegister().getLobbyUtil().revokeBuildPermission(p);
					p.sendMessage(UHC.getInstance().getPrefix() + "§cRevoked §7build permission from yourself!");
				}
			}
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("changeWorld")) {
				if (Bukkit.getWorld(args[1]) != null) {
					p.teleport(Bukkit.getWorld(args[1]).getHighestBlockAt(Bukkit.getWorld(args[1]).getSpawnLocation())
							.getLocation());
				} else {
					p.sendMessage(UHC.getInstance().getPrefix() + "Can't find world §c" + args[1]);
				}
			} else if (args[0].equalsIgnoreCase("loadWorld")) {
				try {
					Bukkit.createWorld(new WorldCreator(args[1]));
					p.sendMessage(UHC.getInstance().getPrefix() + "The world §a" + args[1] + " §7is now loaded.");
				} catch (Exception e) {
					Bukkit.getLogger().log(Level.SEVERE,
							UHC.getInstance().getConsolePrefix() + "Can't load the world " + args[1], e.getMessage());
				}
			} else if (args[0].equalsIgnoreCase("build")) {
				if (Bukkit.getPlayer(args[1]) != null) {
					if (!UHC.getInstance().getRegister().getLobbyUtil().hasBuildPermission(Bukkit.getPlayer(args[1]))) {
						UHC.getInstance().getRegister().getLobbyUtil().grandBuildPermission(Bukkit.getPlayer(args[1]));
						p.sendMessage(UHC.getInstance().getPrefix() + "§aGranted §7build permission for "+args[1]+"!");
					} else {
						UHC.getInstance().getRegister().getLobbyUtil().revokeBuildPermission(Bukkit.getPlayer(args[1]));
						p.sendMessage(UHC.getInstance().getPrefix() + "§cRevoked §7build permission from "+args[1]+"!");
					}
				}
			}
		} else if (args.length == 5) {
			if (args[0].equalsIgnoreCase("createKit")) {
				Kit kit = new Kit(args[1], Integer.parseInt(args[4]), p.getInventory(), Integer.parseInt(args[3]),
						new ItemStack(Material.getMaterial(args[2].toUpperCase())));

				p.sendMessage(
						UHC.getInstance().getPrefix() + "§7You have set the kit §a" + args[1] + " §7with GUI-block §a"
								+ args[2] + "§7 on GUI-slot §a" + args[3] + "§7 and the price of §a" + args[4]);
				kit.registerKit();
				UHC.getInstance().getRegister().getKitInventory().fillInventory();
				return true;
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
		ArrayList<String> suggetions = new ArrayList<String>();
		suggetions.add("uhcAdmin");
		return suggetions;
	}
}
