package de.alpha.uhc.commands;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.aclasses.ATeam;
import de.alpha.uhc.files.*;
import de.alpha.uhc.kits.GUI;
import de.alpha.uhc.kits.KitFileManager;
import de.alpha.uhc.timer.Timer;
import de.alpha.uhc.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class UHCCommand implements CommandExecutor {

    private static String noplayer;
    private static String noperms;
    private static String spawnset;
    private static String lobbyset;
    private static boolean teamMode;

    public static String getNoplayer() {
        return noplayer;
    }

    public static void setNoplayer(String noplayer) {
        UHCCommand.noplayer = noplayer;
    }

    public static String getNoperms() {
        return noperms;
    }

    public static void setNoperms(String noperms) {
        UHCCommand.noperms = noperms;
    }

    public static void setSpawnset(String spawnset) {
        UHCCommand.spawnset = spawnset;
    }

    public static void setLobbyset(String lobbyset) {
        UHCCommand.lobbyset = lobbyset;
    }

    public static boolean isTeamMode() {
        return teamMode;
    }

    public static void setTeamMode(boolean teamMode) {
        UHCCommand.teamMode = teamMode;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Core.getPrefix() + noplayer);
            return true;
        }

        final Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("uhc")) {

            if (args.length == 2) {
                if (GState.isState(GState.LOBBY)) {
                    if (args[0].equalsIgnoreCase("team")) {
                        if (teamMode) {
                            ATeam.addPlayerToTeam(p, args[1]);
                            return true;
                        }
                    }

                    if (args[0].equalsIgnoreCase("createTeamJoiner")) {
                        ArmorStandUtil.spawn(p.getLocation(), args[1]);
                        p.sendMessage(Core.getPrefix() + "§a Setted Teamjoiner for team " + ATeam.getTeamColor(args[1]) + args[1]);
                        return true;
                    }
                }
            }

            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("team") || args[0].equalsIgnoreCase("teams")) {

                    String a = ATeam.getAllTeams().replace("[teams]", "" + ATeam.getTeamNames());
                    p.sendMessage(Core.getPrefix() + a);
                    return true;
                }
                if (args[0].equalsIgnoreCase("stats")) {
                    new Stats(p).sendStats();
                    return true;
                }

                if (p.hasPermission("UHC.start")) {
                    if (args[0].equalsIgnoreCase("start")) {
                        Timer.changeTime();
                        return true;
                    }
                } else {
                    p.sendMessage(Core.getPrefix() + noperms);
                    return true;
                }
            }

            if (args.length == 0) {
                if (p.hasPermission("UHC.admin")) {
                    p.sendMessage("§8---===UHC===---");
                    p.sendMessage("§7 /uhc setSpawn - Set your Arena");
                    p.sendMessage("§7 /uhc setLobby - Set your Lobby, where the players will wait.");
                    p.sendMessage("§7 /uhc createLobby - Create a lobbyregion, which Player won't be able to leave");
                    p.sendMessage("§7 /uhc createWorld <name> - create a new random world");
                    p.sendMessage("§7 /uhc createHologram [lowerby deep] <name> - create a hologram with Player stats");
                    p.sendMessage("§7 /uhc restart - reload the server to restart UHC");
                    p.sendMessage("§7 /uhc reload - reload all UHC files");
                    p.sendMessage("§7 /uhc addKit <name> <GUI block> <GUI slot> <price> <itemlore> - adds a kit with your current inventory");
                    p.sendMessage("§7 /uhc tpToWorld <name> - teleport to the World with the name <name>");
                    p.sendMessage("§7 /uhc start - short the countdown to 10 seconds");
                    p.sendMessage("§7 /uhc team [teamname] - See all teams [join this team]");
                    p.sendMessage("§7 /uhc createTeamJoiner [team] - creates a Entity to join the team [team]");
                    p.sendMessage("§7 /uhc stats - see your stats");
                    p.sendMessage("§8---===XXX===---");
                    return true;
                } else {
                    p.sendMessage("§8---===UHC===---");
                    p.sendMessage("§7 /uhc stats - see your stats");
                    p.sendMessage("§7 /uhc team [teamname] - See all teams [join this team]");
                    p.sendMessage("§8---===XXX===---");
                    return true;
                }
            }

            if (p.hasPermission("UHC.admin")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("restart")) {
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                Bukkit.reload();
                            }
                        }.runTaskLater(Core.getInstance(), 20);
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("reload")) {

                        OptionsFileManager.addOptions();
                        OptionsFileManager.loadOptions();

                        MessageFileManager.addMessages();
                        MessageFileManager.loadMessages();

                        SpawnFileManager.getSpawnFile();
                        SpawnFileManager.registerRegions();

                        TeamFile.addDefaultTeams();
                        TeamFile.loadTeams();

                        HologramFileManager.getHologramFile().save();

                        DropFile.addDrops();
                        DropFile.loadDrops();

                        ScoreboardFile.addScores();
                        ScoreboardFile.loadScores();

                        DeathMessageFile.addDeathMessages();
                        DeathMessageFile.loadDeathMessages();

                        CommandsFile.addCommands();
                        CommandsFile.loadCommands();

                        p.sendMessage(Core.getPrefix() + "§cAll configs has been reloaded");
                        return true;
                    }


                    if (args[0].equalsIgnoreCase("setSpawn")) {

                        SpawnFileManager.SetSpawn(p.getLocation().getX(),
                                p.getLocation().getY(),
                                p.getLocation().getZ(),
                                p.getWorld());
                        p.sendMessage(Core.getPrefix() + spawnset);
                        return true;

                    }

                    if (args[0].equalsIgnoreCase("setLobby")) {

                        SpawnFileManager.SetLobby(p.getLocation().getX(),
                                p.getLocation().getY(),
                                p.getLocation().getZ(),
                                p.getLocation().getYaw(),
                                p.getLocation().getPitch(),
                                p.getWorld());
                        p.sendMessage(Core.getPrefix() + lobbyset);
                        return true;

                    }

                    if (args[0].equalsIgnoreCase("createLobby")) {
                        if (Regions.getDefined(p)) {

                            Regions.addRegion((new Cuboid(Regions.getPos1(p), Regions.getPos2(p))));
                            SpawnFileManager.addRegion(Regions.getPos1(p), Regions.getPos2(p));

                            p.sendMessage(Core.getPrefix() + "§7You have created a lobbyregion.");
                            return true;

                        } else {
                            p.sendMessage(Core.getPrefix() + "§7You have to definde 2 lobbypoints first.");
                            return true;
                        }
                    }
                }

                if (args.length == 2) {

                    if (args[0].equalsIgnoreCase("tpToWorld")) {
                        if (Bukkit.getWorld(args[1]) != null) {
                            p.teleport(Bukkit.getWorld(args[1]).getSpawnLocation().add(0, 200, 0));
                            p.sendMessage(Core.getPrefix() + "§7You have been teleported to the world: §a" + args[1]);
                            return true;
                        } else {
                            p.sendMessage(Core.getPrefix() + "§7The World §c" + args[1] + " §7do not exists");
                            return true;
                        }
                    }

                    if (args[0].equalsIgnoreCase("createWorld")) {

                        Bukkit.createWorld(new WorldCreator(args[1])
                                .generateStructures(false)
                                .type(WorldType.NORMAL));

                        new BukkitRunnable() {

                            @Override
                            public void run() {

                                p.teleport(Bukkit.getWorld(args[1]).getSpawnLocation());

                            }
                        }.runTaskLater(Core.getInstance(), 200);
                        return true;
                    }
                }

                String lore = "";
                String name = "";
                if (args.length >= 2) {
                    if (args.length >= 4) {
                        if (args[0].equalsIgnoreCase("createHologram")) {
                            if (args[1].equalsIgnoreCase("lowerby")) {
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    for (int i = 3; i < args.length; i++) {
                                        name = name + args[i] + " ";
                                    }

                                    new HologramFileManager().addHoloram(name, p.getLocation(), Double.parseDouble(args[2]));

                                    for (int i = 0; i < new HologramFileManager().holocount(); i++) {
                                        new HoloUtil().createHologram(all, i, Double.parseDouble(args[2]));
                                    }
                                }
                                p.sendMessage(Core.getPrefix() + "§7You have created a new Hologram");
                                return true;
                            }
                        }
                    }
                    if (args[0].equalsIgnoreCase("createHologram")) {
                        if (args[1].equalsIgnoreCase("lowerby")) {
                            p.sendMessage(Core.getPrefix() + "/uhc createHologram [lowerby deep] <name>");
                            return true;
                        }
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            for (int i = 1; i < args.length; i++) {
                                name = name + args[i] + " ";
                            }

                            new HologramFileManager().addHoloram(name, p.getLocation(), 0);

                            for (int i = 0; i < new HologramFileManager().holocount(); i++) {
                                new HoloUtil().createHologram(all, i, 0);
                            }
                        }
                        p.sendMessage(Core.getPrefix() + "§7You have created a new Hologram");
                        return true;
                    }
                }
                if (args.length >= 6) {
                    if (args[0].equalsIgnoreCase("addKit")) {

                        for (int i = 5; i < args.length; i++) {
                            lore = lore + args[i] + " ";
                        }

                        new KitFileManager().addKit(args[1], p.getInventory(), args[2], Integer.parseInt(args[3]), lore, Integer.parseInt(args[4]));
                        p.sendMessage(Core.getPrefix() + "§7You have set the kit §a" + args[1] + " §7with GUI-block §a" + args[2] + "§7 on GUI-slot §a" + args[3] + "§7 with the price of §a" + args[4] + " §7and the lore §a" + lore);
                        GUI.fill();
                        return true;
                    }
                }

            } else {
                p.sendMessage(Core.getPrefix() + noperms);
                return true;
            }
        }

        return true;
    }
}
