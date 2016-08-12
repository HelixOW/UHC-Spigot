package de.alphahelix.uhc.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.util.Kit;
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
			p.sendMessage(UHC.getInstance().getRegister().getMessageFile().getColorString("No Permissions"));
			return true;
		}

		if (args.length == 0) {
			TextComponent msg = new TextComponent(UHC.getInstance().getPrefix() + "§7Please click §chere §7to see the wiki");
			msg.setClickEvent(new ClickEvent(Action.OPEN_URL, "https://github.com/AlphaHelixDev/UHC-Spigot/wiki"));
			p.spigot().sendMessage(msg);
			return true;
		}
		if (args.length == 5) {
			if (args[0].equalsIgnoreCase("createKit")) {
				Kit kit = new Kit(args[1], Integer.parseInt(args[4]), p.getInventory(), Integer.parseInt(args[3]), new ItemStack(Material.getMaterial(args[2].toUpperCase())));
				
				p.sendMessage(UHC.getInstance().getPrefix() + "§7You have set the kit §a" + args[1]
						+ " §7with GUI-block §a" + args[2] + "§7 on GUI-slot §a" + args[3] + "§7 and the price of §a"
						+ args[4]);
				kit.registerKit();
				UHC.getInstance().getRegister().getKitInventory().fillInventory();
				return true;
			}
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
