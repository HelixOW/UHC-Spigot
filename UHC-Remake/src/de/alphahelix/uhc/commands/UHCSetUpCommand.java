package de.alphahelix.uhc.commands;

import de.alphahelix.alphalibary.command.SimpleCommand;
import de.alphahelix.uhc.Registery;
import de.alphahelix.uhc.UHC;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class UHCSetUpCommand extends SimpleCommand<UHC, Registery> {

    public UHCSetUpCommand(UHC plugin, Registery r, String command, String description, String... aliases) {
        super(plugin, r, command, description, aliases);
    }

    @Override
    public boolean execute(CommandSender cs, String label, String[] args) {

        if (!(cs instanceof Player))
            return true;

        Player p = (Player) cs;

        if (!p.hasPermission("uhc.admin")) {
            p.sendMessage((getPlugin().getPrefix()
                    + getPlugin().getRegister().getMessageFile().getColorString("No Permissions")));
            return true;
        } else if (args.length == 0) {
            TextComponent msg = new TextComponent(getPlugin().getPrefix() + "§7Please click §chere §7to see the wiki");
            msg.setClickEvent(new ClickEvent(Action.OPEN_URL, "https://github.com/AlphaHelixDev/UHC-Spigot/wiki"));
            p.spigot().sendMessage(msg);
            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("setLobby")) {
                getPlugin().getRegister().getLocationsFile().setLobby(p.getLocation());
                p.sendMessage(getPlugin().getPrefix() + "You've set the lobby to §ayour Location§7!");
                return true;
            } else if (args[0].equalsIgnoreCase("setArena")) {
                getPlugin().getRegister().getLocationsFile().setArena(p.getLocation());
                p.sendMessage(getPlugin().getPrefix() + "You've set the arenaspawn to §ayour Location§7!");
                return true;
            } else if (args[0].equalsIgnoreCase("setDeathmatch")) {
                getPlugin().getRegister().getLocationsFile().setDeathmatch(p.getLocation());
                p.sendMessage(getPlugin().getPrefix() + "You've set the deathmatch spot to §ayour Location§7!");
                return true;
            } else if (args[0].equalsIgnoreCase("setNether")) {
                getPlugin().getRegister().getLocationsFile().setNetherArena(p.getLocation());
                p.sendMessage(getPlugin().getPrefix() + "You've set the nether spot to §ayour Location§7!");
                return true;
            } else if (args[0].equalsIgnoreCase("setStatsNPC")) {
                getPlugin().getRegister().getLocationsFile().addNPC(p.getLocation());
                p.sendMessage(getPlugin().getPrefix() + "You've set the position of a NPC to show the stats at your location!");
                return true;
            } else if (args[0].equalsIgnoreCase("manageScenarios")) {
                getPlugin().getRegister().getScenarioAdminInventory().fillInventory(p);
                return true;
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("removeArmorstand")) {
                getPlugin().getRegister().getNpcUtil().removeArmorStand(p.getLocation(), args[1]);
                p.sendMessage(getPlugin().getPrefix() + "You've successfully removed the Armorstand![" + args[1] + "]");
                return true;
            } else if (args[0].equalsIgnoreCase("createArmorstand")) {
                getPlugin().getRegister().getNpcUtil().spawnArmorStand(p.getLocation(), args[1]);
                p.sendMessage(getPlugin().getPrefix() + "You've created a new Armorstand to join teams");
                return true;
            } else if (args[0].equalsIgnoreCase("createRankingArmorstand")) {
                getPlugin().getRegister().getNpcUtil().spawnRankingArmorStand(p.getLocation(),
                        Integer.parseInt(args[1]));
                p.sendMessage(getPlugin().getPrefix() + "You've created the NPC for place " + args[1]);
                return true;
            } else if (args[0].equalsIgnoreCase("removeRankingArmorstand")) {
                getPlugin().getRegister().getNpcUtil().removeRankingArmorStand(p.getLocation(),
                        Integer.parseInt(args[1]));
                p.sendMessage(getPlugin().getPrefix() + "You've successfully removed the Armorstand! For place : "
                        + args[1] + "");
                return true;
            }
        } else if (args[0].equalsIgnoreCase("createHologram")) {
            String name = "";
            if (args[1].equalsIgnoreCase("lower")) {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    for (int i = 3; i < args.length; i++) {
                        name = name + args[i] + " ";
                    }

                    getPlugin().getRegister().getHologramFile().addHologram(name, p.getLocation(),
                            Double.parseDouble(args[1]));

                    for (int i = 0; i < getPlugin().getRegister().getHologramFile().getHologramcount(); i++) {
                        getPlugin().getRegister().getHologramUtil().createHologram(all, i, Double.parseDouble(args[1]));
                    }
                }
                p.sendMessage(getPlugin().getPrefix() + "§7You have created a new Hologram");
                return true;
            } else {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    for (int i = 1; i < args.length; i++) {
                        name = name + args[i] + " ";
                    }

                    getPlugin().getRegister().getHologramFile().addHologram(name, p.getLocation(), 0);

                    for (int i = 0; i < getPlugin().getRegister().getHologramFile().getHologramcount(); i++) {
                        getPlugin().getRegister().getHologramUtil().createHologram(all, i, 0);
                    }
                }
                p.sendMessage(getPlugin().getPrefix() + "§7You have created a new Hologram");
                return true;
            }
        }

        TextComponent msg = new TextComponent(getPlugin().getPrefix() + "§7Please click §chere §7to see the wiki");
        msg.setClickEvent(new ClickEvent(Action.OPEN_URL, "https://github.com/AlphaHelixDev/UHC-Spigot/wiki"));
        p.spigot().sendMessage(msg);
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender cs, String label, String[] args) {
        return null;
    }

}
