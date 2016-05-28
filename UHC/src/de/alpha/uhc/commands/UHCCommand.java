package de.alpha.uhc.commands;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.Registery;
import de.alpha.uhc.utils.Cuboid;
import de.alpha.uhc.utils.Stats;

public class UHCCommand implements CommandExecutor {
	
	private Core pl;
	private Registery r;
	
	public UHCCommand(Core c) {
		this.pl = c;
		this.r = pl.getRegistery();
	}

    private  String noplayer;
    private  String noperms;
    private  String spawnset;
    private  String lobbyset;
    private  boolean teamMode;

    public  String getNoplayer() {
        return noplayer;
    }

    public  void setNoplayer(String noplayer) {
        this.noplayer = noplayer;
    }

    public  String getNoperms() {
        return noperms;
    }

    public  void setNoperms(String noperms) {
        this.noperms = noperms;
    }

    public  void setSpawnset(String spawnset) {
        this.spawnset = spawnset;
    }

    public  void setLobbyset(String lobbyset) {
        this.lobbyset = lobbyset;
    }

    public  boolean isTeamMode() {
        return teamMode;
    }

    public  void setTeamMode(boolean teamMode) {
        this.teamMode = teamMode;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Core.getInstance().getPrefix() + noplayer);
            return true;
        }

        final Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("uhc")) {

            if (args.length == 2) {
                if (GState.isState(GState.LOBBY)) {
                    if (args[0].equalsIgnoreCase("team")) {
                        if (teamMode) {
                        	r.getATeam().addPlayerToTeam(p, args[1]);
                            return true;
                        }
                    }
                }
            }

            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("team") || args[0].equalsIgnoreCase("teams")) {

                    String a = r.getATeam().getAllTeams().replace("[teams]", "" + r.getATeam().getTeamNames());
                    p.sendMessage(Core.getInstance().getPrefix() + a);
                    return true;
                }
                if (args[0].equalsIgnoreCase("stats")) {
                    new Stats(p).sendStats();
                    return true;
                }

                if (p.hasPermission("UHC.start")) {
                    if (args[0].equalsIgnoreCase("start")) {
                    	r.getTimer().changeTime();
                        return true;
                    }
                } else {
                    p.sendMessage(Core.getInstance().getPrefix() + noperms);
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
                    p.sendMessage("§7 /uhc removeTeamJoiner - removes the TeamJoiner at your current Location");
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

                        r.getOptionsFile().addOptions();
                        r.getOptionsFile().loadOptions();

                        r.getMessageFile().addMessages();
                        r.getMessageFile().loadMessages();

                        r.getSpawnFileManager().getSpawnFile();
                        r.getSpawnFileManager().registerRegions();

                        r.getTeamFile().addDefaultTeams();
                        r.getTeamFile().loadTeams();

                        r.getHologramFile().getHologramFile().save();

                        r.getDropFile().addDrops();
                        r.getDropFile().loadDrops();

                        r.getScoreboardFile().addScores();
                        r.getScoreboardFile().loadScores();

                        r.getDeathMessagesFile().addDeathMessages();
                        r.getDeathMessagesFile().loadDeathMessages();

                        r.getCommandsFile().addCommands();
                        r.getCommandsFile().loadCommands();
                        
                        r.getArmorstandFile().getASFile().save();

                        p.sendMessage(Core.getInstance().getPrefix() + "§cAll configs has been reloaded");
                        return true;
                    }

                    if(args[0].equalsIgnoreCase("removeTeamJoiner")) {
                    	r.getArmorStandUtil().removeArmorStand(p.getLocation());
                    	p.sendMessage(Core.getInstance().getPrefix() + "TeamJoiner successfully removed");
                    }
                    
                    if (args[0].equalsIgnoreCase("setSpawn")) {

                    	r.getSpawnFileManager().SetSpawn(p.getLocation().getX(),
                                p.getLocation().getY(),
                                p.getLocation().getZ(),
                                p.getWorld());
                        p.sendMessage(Core.getInstance().getPrefix() + spawnset);
                        return true;

                    }

                    if (args[0].equalsIgnoreCase("setLobby")) {

                    	r.getSpawnFileManager().SetLobby(p.getLocation().getX(),
                                p.getLocation().getY(),
                                p.getLocation().getZ(),
                                p.getLocation().getYaw(),
                                p.getLocation().getPitch(),
                                p.getWorld());
                        p.sendMessage(Core.getInstance().getPrefix() + lobbyset);
                        return true;

                    }

                    if (args[0].equalsIgnoreCase("createLobby")) {
                        if (r.getRegions().getDefined(p)) {

                            r.getRegions().addRegion((new Cuboid(r.getRegions().getPos1(p), r.getRegions().getPos2(p))));
                            r.getSpawnFileManager().addRegion(r.getRegions().getPos1(p), r.getRegions().getPos2(p));

                            p.sendMessage(Core.getInstance().getPrefix() + "§7You have created a lobbyregion.");
                            return true;

                        } else {
                            p.sendMessage(Core.getInstance().getPrefix() + "§7You have to definde 2 lobbypoints first.");
                            return true;
                        }
                    }
                }

                if (args.length == 2) {
                	
                	if (args[0].equalsIgnoreCase("createTeamJoiner")) {
                		pl.getRegistery().getArmorStandUtil().spawn(p.getLocation(), args[1]);
                        p.sendMessage(Core.getInstance().getPrefix() + "§a Setted Teamjoiner for team " + r.getATeam().getTeamColor(args[1]) + args[1]);
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("tpToWorld")) {
                        if (Bukkit.getWorld(args[1]) != null) {
                            p.teleport(Bukkit.getWorld(args[1]).getSpawnLocation().add(0, 200, 0));
                            p.sendMessage(Core.getInstance().getPrefix() + "§7You have been teleported to the world: §a" + args[1]);
                            return true;
                        } else {
                            p.sendMessage(Core.getInstance().getPrefix() + "§7The World §c" + args[1] + " §7do not exists");
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
                                    
                                    pl.getRegistery().getHologramFile().addHoloram(name, p.getLocation(), Double.parseDouble(args[2]));

                                    for (int i = 0; i < pl.getRegistery().getHologramFile().holocount(); i++) {
                                        Core.getInstance().getRegistery().getHoloUtil().createHologram(all, i, Double.parseDouble(args[2]));
                                    }
                                }
                                p.sendMessage(Core.getInstance().getPrefix() + "§7You have created a new Hologram");
                                return true;
                            }
                        }
                    }
                    if (args[0].equalsIgnoreCase("createHologram")) {
                        if (args[1].equalsIgnoreCase("lowerby")) {
                            p.sendMessage(Core.getInstance().getPrefix() + "/uhc createHologram [lowerby deep] <name>");
                            return true;
                        }
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            for (int i = 1; i < args.length; i++) {
                                name = name + args[i] + " ";
                            }

                            pl.getRegistery().getHologramFile().addHoloram(name, p.getLocation(), 0);

                            for (int i = 0; i < pl.getRegistery().getHologramFile().holocount(); i++) {
                                Core.getInstance().getRegistery().getHoloUtil().createHologram(all, i, 0);
                            }
                        }
                        p.sendMessage(Core.getInstance().getPrefix() + "§7You have created a new Hologram");
                        return true;
                    }
                }
                if (args.length >= 6) {
                    if (args[0].equalsIgnoreCase("addKit")) {

                        for (int i = 5; i < args.length; i++) {
                            lore = lore + args[i] + " ";
                        }

                        pl.getRegistery().getKitFile().addKit(args[1], p.getInventory(), args[2], Integer.parseInt(args[3]), lore, Integer.parseInt(args[4]));
                        p.sendMessage(Core.getInstance().getPrefix() + "§7You have set the kit §a" + args[1] + " §7with GUI-block §a" + args[2] + "§7 on GUI-slot §a" + args[3] + "§7 with the price of §a" + args[4] + " §7and the lore §a" + lore);
                        r.getGui().fill();
                        return true;
                    }
                }

            } else {
                p.sendMessage(Core.getInstance().getPrefix() + noperms);
                return true;
            }
        }

        return true;
    }
}
