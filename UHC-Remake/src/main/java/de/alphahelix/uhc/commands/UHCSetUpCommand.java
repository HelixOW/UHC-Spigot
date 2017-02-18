package de.alphahelix.uhc.commands;

import de.alphahelix.alphalibary.command.SimpleCommand;
import de.alphahelix.alphalibary.fakeapi.utils.ArmorstandUtil;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class UHCSetUpCommand extends SimpleCommand<UHC> {

    public UHCSetUpCommand(UHC plugin) {
        super(plugin, "uhcSetup", "Setup all of your options", "uhcS");
    }

    @Override
    public boolean execute(CommandSender cs, String label, String[] args) {

        if (!(cs instanceof Player))
            return true;

        Player p = (Player) cs;

        if (!p.hasPermission("uhc.admin")) {
            p.sendMessage((getPlugin().getPrefix()
                    + UHCFileRegister.getMessageFile().getNoPermissions()));
            return true;
        } else if (args.length == 0) {
            TextComponent msg = new TextComponent(getPlugin().getPrefix() + ChatColor.GRAY + "Please click " + ChatColor.RED + "here " + ChatColor.GRAY + "to see the wiki");
            msg.setClickEvent(new ClickEvent(Action.OPEN_URL, "https://github.com/AlphaHelixDev/UHC-Spigot/wiki"));
            p.spigot().sendMessage(msg);
            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("setLobby")) {
                UHCFileRegister.getLocationsFile().setLobby(p.getLocation());
                p.sendMessage(getPlugin().getPrefix() + "You've set the lobby to " + ChatColor.GREEN + "your Location" + ChatColor.GRAY + "!");
                return true;
            } else if (args[0].equalsIgnoreCase("setArena")) {
                UHCFileRegister.getLocationsFile().setArena(p.getLocation());
                p.sendMessage(getPlugin().getPrefix() + "You've set the arenaspawn to " + ChatColor.GREEN + "your Location" + ChatColor.GRAY + "!");
                return true;
            } else if (args[0].equalsIgnoreCase("setDeathmatch")) {
                UHCFileRegister.getLocationsFile().setDeathmatch(p.getLocation());
                p.sendMessage(getPlugin().getPrefix() + "You've set the deathmatch spot to " + ChatColor.GREEN + "your Location" + ChatColor.GRAY + "!");
                return true;
            } else if (args[0].equalsIgnoreCase("setNether")) {
                UHCFileRegister.getLocationsFile().setNetherArena(p.getLocation());
                p.sendMessage(getPlugin().getPrefix() + "You've set the nether spot to " + ChatColor.GREEN + "your Location" + ChatColor.GRAY + "!");
                return true;
            } else if (args[0].equalsIgnoreCase("setStatsNPC")) {
                UHCFileRegister.getLocationsFile().addStatsNPC(p.getLocation());
                p.sendMessage(getPlugin().getPrefix() + "You've set the position of a NPC to show the stats at your location!");
                return true;
            } else if (args[0].equalsIgnoreCase("manageScenarios")) {
                UHCRegister.getScenarioAdminInventory().fillInventory(p);
                return true;
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("removeArmorstand")) {
                UHCRegister.getNpcUtil().removeArmorStand(p.getLocation(), args[1]);
                p.sendMessage(getPlugin().getPrefix() + "You've successfully removed the Armorstand![" + args[1] + "]");
                return true;
            } else if (args[0].equalsIgnoreCase("createArmorstand")) {
                UHCRegister.getNpcUtil().spawnArmorStand(p.getLocation(), args[1]);
                p.sendMessage(getPlugin().getPrefix() + "You've created a new Armorstand to join teams");
                return true;
            } else if (args[0].equalsIgnoreCase("createRankingArmorstand")) {
                UHCRegister.getNpcUtil().spawnRankingArmorStand(p.getLocation(),
                        Integer.parseInt(args[1]));
                p.sendMessage(getPlugin().getPrefix() + "You've created the NPC for place " + args[1]);
                return true;
            } else if (args[0].equalsIgnoreCase("removeRankingArmorstand")) {
                UHCRegister.getNpcUtil().removeRankingArmorStand(p.getLocation(),
                        Integer.parseInt(args[1]));
                p.sendMessage(getPlugin().getPrefix() + "You've successfully removed the Armorstand! For place : "
                        + args[1] + "");
                return true;
            }
        } else if (args[0].equalsIgnoreCase("createHologram")) {
            String name = buildString(args, 1);

            for (Player players : Bukkit.getOnlinePlayers()) {
                ArmorstandUtil.spawnArmorstand(players, p.getLocation(), name);
            }

            p.sendMessage(getPlugin().getPrefix() + "§7You have created a new Hologram");
            return true;
        }

        TextComponent msg = new TextComponent(getPlugin().getPrefix() + ChatColor.GRAY + "Please click " + ChatColor.RED + "here " + ChatColor.GRAY + "to see the wiki");
        msg.setClickEvent(new ClickEvent(Action.OPEN_URL, "https://github.com/AlphaHelixDev/UHC-Spigot/wiki"));
        p.spigot().sendMessage(msg);
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender cs, String label, String[] args) {
        return null;
    }

}
