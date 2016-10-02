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
import de.alphahelix.uhc.UHCCrateRarerity;
import de.alphahelix.uhc.instances.Kit;
import de.popokaka.alphalibary.UUID.UUIDFetcher;
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
			p.sendMessage(getPlugin().getPrefix()
					+ getPlugin().getRegister().getMessageFile().getColorString("No Permissions"));
			return true;
		}

		if (args.length == 0) {
			TextComponent msg = new TextComponent(getPlugin().getPrefix() + "§7Please click §chere §7to see the wiki");
			msg.setClickEvent(new ClickEvent(Action.OPEN_URL, "https://github.com/AlphaHelixDev/UHC-Spigot/wiki"));
			p.spigot().sendMessage(msg);
			return true;
		}

		else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("startLobbyTimer")) {
				if (!getPlugin().getRegister().getLobbyTimer().isRunning()) {
					getPlugin().getRegister().getLobbyTimer().startLobbyCountdown();
					p.sendMessage(getPlugin().getPrefix() + "§7The lobbytimer now continues!");
				}

				else {
					p.sendMessage(getPlugin().getPrefix() + "§7The Lobbytimer is already running!");
				}
			}

			else if (args[0].equalsIgnoreCase("stopLobbyTimer")) {
				getPlugin().getRegister().getLobbyTimer().stopTimer();
			}

			else if (args[0].equalsIgnoreCase("build")) {
				if (!getPlugin().getRegister().getLobbyUtil().hasBuildPermission(p)) {
					getPlugin().getRegister().getLobbyUtil().grandBuildPermission(p);
					p.sendMessage(getPlugin().getPrefix() + "§aGranted §7build permission for yourself!");
				}

				else {
					getPlugin().getRegister().getLobbyUtil().revokeBuildPermission(p);
					p.sendMessage(getPlugin().getPrefix() + "§cRevoked §7build permission from yourself!");
				}
			}
		}

		else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("changeWorld")) {
				if (Bukkit.getWorld(args[1]) != null) {
					p.teleport(Bukkit.getWorld(args[1]).getHighestBlockAt(Bukkit.getWorld(args[1]).getSpawnLocation())
							.getLocation());
				}

				else {
					p.sendMessage(getPlugin().getPrefix() + "Can't find world §c" + args[1]);
				}
			}

			else if (args[0].equalsIgnoreCase("loadWorld")) {
				try {
					Bukkit.createWorld(new WorldCreator(args[1]));
					p.sendMessage(getPlugin().getPrefix() + "The world §a" + args[1] + " §7is now loaded.");
				} catch (Exception e) {
					Bukkit.getLogger().log(Level.SEVERE,
							getPlugin().getConsolePrefix() + "Can't load the world " + args[1], e.getMessage());
				}
			}

			else if (args[0].equalsIgnoreCase("build")) {
				if (Bukkit.getPlayer(args[1]) != null) {
					if (!getPlugin().getRegister().getLobbyUtil().hasBuildPermission(Bukkit.getPlayer(args[1]))) {
						getPlugin().getRegister().getLobbyUtil().grandBuildPermission(Bukkit.getPlayer(args[1]));
						p.sendMessage(getPlugin().getPrefix() + "§aGranted §7build permission for " + args[1] + "!");
					}

					else {
						getPlugin().getRegister().getLobbyUtil().revokeBuildPermission(Bukkit.getPlayer(args[1]));
						p.sendMessage(getPlugin().getPrefix() + "§cRevoked §7build permission from " + args[1] + "!");
					}
				}
			}

			else if (args[0].equalsIgnoreCase("giveCrate")) {
				getPlugin().getRegister().getStatsUtil().addCrate(getPlugin().getRegister().getUhcCrateFile()
						.getCrate(UHCCrateRarerity.valueOf(args[1].toUpperCase())), p);
			}
		}

		else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("giveCrate")) {
				if (Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(args[2])) != null) {
					getPlugin().getRegister().getStatsUtil().addCrate(
							getPlugin().getRegister().getUhcCrateFile()
									.getCrate(UHCCrateRarerity.valueOf(args[1].toUpperCase())),
							Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(args[2])));
					p.sendMessage(getPlugin().getPrefix() + "You've given " + args[2] + " a "
							+ UHCCrateRarerity.valueOf(args[1].toUpperCase()).getPrefix() + args[1] + " crates!");
				}
			}
		}

		else if (args.length == 5) {
			if (args[0].equalsIgnoreCase("createKit")) {
				Kit kit = new Kit(args[1], Integer.parseInt(args[4]), p.getInventory(), Integer.parseInt(args[3]),
						new ItemStack(Material.getMaterial(args[2].toUpperCase())));

				p.sendMessage(getPlugin().getPrefix() + "§7You have set the kit §a" + args[1] + " §7with GUI-block §a"
						+ args[2] + "§7 on GUI-slot §a" + args[3] + "§7 and the price of §a" + args[4]);
				kit.registerKit();
				getPlugin().getRegister().getKitInventory().fillInventory();
				return true;
			}
		}

		else {
			TextComponent msg = new TextComponent(getPlugin().getPrefix() + "§7Please click §chere §7to see the wiki");
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
