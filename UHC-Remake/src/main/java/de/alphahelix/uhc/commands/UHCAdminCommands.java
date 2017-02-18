package de.alphahelix.uhc.commands;

import de.alphahelix.alphalibary.command.SimpleCommand;
import de.alphahelix.alphalibary.uuid.UUIDFetcher;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Crate;
import de.alphahelix.uhc.instances.Kit;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class UHCAdminCommands extends SimpleCommand<UHC> {

    public UHCAdminCommands(UHC plugin) {
        super(plugin, "uhcAdmin", "Manage some server configurations via commands.", "uhcA");
    }

    @Override
    public boolean execute(CommandSender cs, String label, String[] args) {
        if (!(cs instanceof Player))
            return true;

        Player p = (Player) cs;

        if (!p.hasPermission("uhc.admin")) {
            p.sendMessage(getPlugin().getPrefix()
                    + UHCFileRegister.getMessageFile().getNoPermissions());
            return true;
        }

        if (args.length == 0) {
            TextComponent msg = new TextComponent(getPlugin().getPrefix() + ChatColor.GRAY + "Please click " + ChatColor.RED + "here " + ChatColor.GRAY + "to see the wiki");
            msg.setClickEvent(new ClickEvent(Action.OPEN_URL, "https://github.com/AlphaHelixDev/UHC-Spigot/wiki"));
            p.spigot().sendMessage(msg);
            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("startLobby")) {
                if (!UHCRegister.getLobbyTimer().isRunning()) {
                    UHCRegister.getLobbyTimer().startLobbyCountdown();
                    p.sendMessage(getPlugin().getPrefix() + ChatColor.GRAY + "The lobbytimer now continues!");
                } else {
                    p.sendMessage(getPlugin().getPrefix() + ChatColor.GRAY + "The Lobbytimer is already running!");
                }
            } else if (args[0].equalsIgnoreCase("stopLobby")) {
                UHCRegister.getLobbyTimer().stopTimer();
            } else if (args[0].equalsIgnoreCase("build")) {
                if (!UHCRegister.getLobbyUtil().hasBuildPermission(p)) {
                    UHCRegister.getLobbyUtil().grandBuildPermission(p);
                    p.sendMessage(getPlugin().getPrefix() + ChatColor.GREEN + "Granted " + ChatColor.GRAY + "build permission for yourself!");
                } else {
                    UHCRegister.getLobbyUtil().revokeBuildPermission(p);
                    p.sendMessage(getPlugin().getPrefix() + ChatColor.RED + "Revoked " + ChatColor.GRAY + "build permission from yourself!");
                }
            } else if (args[0].equalsIgnoreCase("worlds")) {
                p.sendMessage(getPlugin().getPrefix() + "The worlds are: \n" + getWorldnames());
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("changeWorld")) {
                if (Bukkit.getWorld(args[1]) != null) {
                    p.teleport(Bukkit.getWorld(args[1]).getHighestBlockAt(Bukkit.getWorld(args[1]).getSpawnLocation())
                            .getLocation());
                } else {
                    p.sendMessage(getPlugin().getPrefix() + "Can't find world " + ChatColor.RED + args[1]);
                }
            } else if (args[0].equalsIgnoreCase("loadWorld")) {
                try {
                    Bukkit.createWorld(new WorldCreator(args[1]));
                    p.sendMessage(getPlugin().getPrefix() + "The world " + ChatColor.GREEN + args[1] + ChatColor.GRAY + " is now loaded.");
                } catch (Exception e) {
                    Bukkit.getLogger().log(Level.SEVERE,
                            getPlugin().getConsolePrefix() + "Can't load the world " + args[1], e.getMessage());
                }
            } else if (args[0].equalsIgnoreCase("build")) {
                if (Bukkit.getPlayer(args[1]) != null) {
                    if (!UHCRegister.getLobbyUtil().hasBuildPermission(Bukkit.getPlayer(args[1]))) {
                        UHCRegister.getLobbyUtil().grandBuildPermission(Bukkit.getPlayer(args[1]));
                        p.sendMessage(getPlugin().getPrefix() + "§aGranted §7build permission for " + args[1] + "!");
                    } else {
                        UHCRegister.getLobbyUtil().revokeBuildPermission(Bukkit.getPlayer(args[1]));
                        p.sendMessage(getPlugin().getPrefix() + "§cRevoked §7build permission from " + args[1] + "!");
                    }
                }
            } else if (args[0].equalsIgnoreCase("dropCrate")) {
                try {
                    p.sendMessage(getPlugin().getPrefix() + UHCFileRegister.getMessageFile().getCrateDropped(Crate.getCrateByName(args[1])));

                    UHCRegister.getStatsUtil().addCrate(Crate.getCrateByName(args[1]), p);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("dropCrate")) {
                if (Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(args[2])) != null) {
                    try {

                        UHCRegister.getStatsUtil().addCrate(
                                Crate.getCrateByName(args[1]),
                                Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(args[2])));

                        p.sendMessage(getPlugin().getPrefix() + "You've given " + args[2] + " a "
                                + args[1] + " crate!");
                        if (Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(args[2])).isOnline())
                            Bukkit.getPlayer(args[2]).sendMessage(getPlugin().getPrefix() + UHCFileRegister.getMessageFile().getCrateDropped(Crate.getCrateByName(args[1])));
                    } catch (Exception ignore) {
                    }
                }
            }
        } else if (args.length == 4 && !args[0].equalsIgnoreCase("createCrate")) {
            if (args[0].equalsIgnoreCase("createKit")) {
                Kit kit = new Kit(args[1], Double.parseDouble(args[3]), new ItemStack(Material.getMaterial(args[2].toUpperCase())), getInventoryStacks(p.getInventory()));

                p.sendMessage(getPlugin().getPrefix() + ChatColor.GRAY + "You have set the kit " + ChatColor.GREEN + args[1] + ChatColor.GRAY + " with GUI-block " + ChatColor.GREEN
                        + args[2] + ChatColor.GRAY + " and the price of " + ChatColor.GREEN + args[3]);
                kit.registerKit();
                return true;
            }
        } else if (args.length >= 4 && args[0].equalsIgnoreCase("createCrate")) {
            if (args[0].equalsIgnoreCase("createCrate")) {
                Crate crate;

                if (p.getInventory().getItemInHand() != null)
                    crate = new Crate(args[1], Double.parseDouble(args[2]), p.getInventory().getItemInHand(), getKitFromName(makeStringArray(args, 3)));
                else if (args.length >= 4)
                    crate = new Crate(args[1], Double.parseDouble(args[2]), new ItemStack(Material.getMaterial(args[3].toUpperCase())), getKitFromName(makeStringArray(args, 4)));
                else
                    crate = new Crate(args[1], Double.parseDouble(args[2]), new ItemStack(Material.DIRT), getKitFromName(makeStringArray(args, 3)));

                p.sendMessage(getPlugin().getPrefix() + "§7You have created the crate §a" + args[1] + "§7 with a drop chance of §a" + args[2] + "%");
                crate.registerCrate();
                return true;
            }
        } else {
            TextComponent msg = new TextComponent(getPlugin().getPrefix() + ChatColor.GRAY + "Please click " + ChatColor.RED + "here " + ChatColor.GRAY + "to see the wiki");
            msg.setClickEvent(new ClickEvent(Action.OPEN_URL, "https://github.com/AlphaHelixDev/UHC-Spigot/wiki"));
            p.spigot().sendMessage(msg);
            return true;
        }

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender cs, String label, String[] args) {
        ArrayList<String> suggetions = new ArrayList<>();
        suggetions.add("uhcAdmin");
        return suggetions;
    }

    private ArrayList<String> getWorldnames() {
        ArrayList<String> worldNames = new ArrayList<>();
        for (World w : Bukkit.getWorlds()) {
            worldNames.add(w.getName());
        }
        return worldNames;
    }

    private ItemStack[] getInventoryStacks(Inventory inventory) {
        ArrayList<ItemStack> toArray = new ArrayList<>();
        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack != null && itemStack.getType() != Material.AIR) {
                toArray.add(itemStack);
            }
        }
        return toArray.toArray(new ItemStack[toArray.size()]);
    }

    private Kit[] getKitFromName(String... name) {
        ArrayList<Kit> kits = new ArrayList<>();
        for(String kit : name) {
            if(UHCFileRegister.getKitsFile().getKit(kit) != null) {
                kits.add(UHCFileRegister.getKitsFile().getKit(kit));
            }
        }
        return kits.toArray(new Kit[kits.size()]);
    }

    private String[] makeStringArray(String[] args, int start) {
        return buildString(args, start).split(" ");
    }
}
