package de.alpha.uhc.Listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.Registery;
import de.popokaka.alphalibary.UUID.UUIDFetcher;
import de.popokaka.alphalibary.item.ItemBuilder;
import de.popokaka.alphalibary.mysql.MySQLManager;
import de.popokaka.alphalibary.nms.SimpleTitle;

public class PlayerJoinListener implements Listener {
	
	private Core pl;
	private Registery r;
	
	public PlayerJoinListener(Core c) {
		this.pl = c;
		this.r = pl.getRegistery();
	}

    private  String join;
    private  String full;

    private  String title;
    private  String subtitle;

    private  Material teamItem;
    private  String teamName;

    private  Material kitItem;
    private  String kitName;
    private  boolean kitMode;

    private  boolean leaveMode;
    private  Material leaveItem;
    private  String leaveName;

    private  boolean startMode;
    private  Material startItem;
    private  String startName;
    private  int mpc;
    private int apc;

    public  void setJoin(String join) {
    	this.join = join;
    }
    public  void setFull(String full) {
    	this.full = full;
    }
    public  void setTitle(String title) {
    	this.title = title;
    }
    public  void setSubtitle(String subtitle) {
    	this.subtitle = subtitle;
    }
    public  void setTeamItem(Material teamItem) {
    	this.teamItem = teamItem;
    }
    public  void setTeamName(String teamName) {
    	this.teamName = teamName;
    }
    public  Material getKitItem() {
        return kitItem;
    }
    public  void setKitItem(Material kitItem) {
    	this.kitItem = kitItem;
    }
    public  void setKitName(String kitName) {
    	this.kitName = kitName;
    }
    public  void setKitMode(boolean kitMode) {
    	this.kitMode = kitMode;
    }
    public  void setLeaveMode(boolean leaveMode) {
    	this.leaveMode = leaveMode;
    }
    public  void setLeaveItem(Material leaveItem) {
    	this.leaveItem = leaveItem;
    }
    public  void setLeaveName(String leaveName) {
    	this.leaveName = leaveName;
    }
    public  void setStartMode(boolean startMode) {
    	this.startMode = startMode;
    }
    public  void setStartItem(Material startItem) {
    	this.startItem = startItem;
    }
    public  void setStartName(String startName) {
    	this.startName = startName;
    }
    public  void setMpc(int mpc) {
    	this.mpc = mpc;
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {

        e.setJoinMessage(null);
        final Player p = e.getPlayer();

        if (GState.isState(GState.RESTART)) {
            p.kickPlayer(pl.getPrefix() + r.getGameEndListener().getKick());
            return;
        }

        if (GState.isState(GState.INGAME) || GState.isState(GState.GRACE)) {
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);
            if (r.getSpawnFileManager().getSpawn() == null) p.teleport(Bukkit.getWorld("UHC").getSpawnLocation());
            else p.teleport(r.getSpawnFileManager().getSpawn());
            pl.addSpec(p);
            r.getSpectator().setSpec(p);
            r.getAScoreboard().sendAntiFlickerInGameBoard(p);

            r.getATablist().sendStandingInGameTablist();
            for (Player all : pl.getInGamePlayers()) {
                r.getAScoreboard().updateInGameSpectators(all);
            }
            return;
        }

        for (Player all : Bukkit.getOnlinePlayers()) {
            all.showPlayer(p);
        }

        r.getATablist().sendStandingLobbyTablist();

        if (pl.isMySQLActive()) {
            if (MySQLManager.getObjectConditionResult("UUID", UUIDFetcher.getUUID(p.getName()).toString(), "UUID") == null) {
                MySQLManager.exInsertQry(p.getName(), UUIDFetcher.getUUID(p.getName()).toString(), "0", "0", "0", "");
            } else if (MySQLManager.getObjectConditionResult("UUID ", UUIDFetcher.getUUID(p.getName()).toString(),
                    "UUID") != null) {
                MySQLManager.exUpdateQry(UUIDFetcher.getUUID(p.getName()).toString(), "Player", p.getName());
            }
        } else {
            if (!(r.getPlayerFile().getPlayerFile().contains("Player: " + p.getName()))) {
                r.getPlayerFile().addPlayer(p);
            }
        }

        if (Bukkit.getOnlinePlayers().size() == mpc + 1) {
            p.kickPlayer(pl.getPrefix() + full);
        }

        for (Entity kill : p.getWorld().getEntities()) {

            if (!(kill instanceof Player || kill instanceof ArmorStand)) {
                kill.remove();
            }

        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {

            @Override
            public void run() {

                apc = Bukkit.getOnlinePlayers().size();

                join = join.replace("[Player]", p.getDisplayName());
                join = join.replace("[PlayerCount]", "§7[" + apc + "/" + mpc + "]");

                Bukkit.broadcastMessage(pl.getPrefix() + join);

                join = r.getMessageFile().getMSGFile().getColorString("Announcements.Join");

            }
        }, 20);

        p.setLevel(0);
        p.setExp(0);

        p.getInventory().clear();
        p.getInventory().setArmorContents(null);

        pl.getRegistery().getHoloUtil().showHologram(p);

        if (kitMode) {
            if (kitItem == null || kitName == null) {
                Bukkit.getConsoleSender().sendMessage(pl.getPrefix() + "§cYou don't have any Kits in your kits.yml");
            } else {
                p.getInventory().setHeldItemSlot(0);
                p.getInventory().setItemInMainHand(new ItemBuilder(kitItem).setName(kitName).build());

            }
        }
        if (r.getUHCCommand().isTeamMode()) {
            if (teamItem == null || teamName == null) {
                Bukkit.getConsoleSender().sendMessage(pl.getPrefix() + "§cYou don't have any Kits in your kits.yml");
            } else {
                p.getInventory().setItem(1, new ItemBuilder(teamItem).setName(teamName).build());
            }
        }
        if (leaveMode && r.getGameEndListener().isBungeeMode()) {
            p.getInventory().setItem(8, new ItemBuilder(leaveItem).setName(leaveName).build());
        }
        if (startMode && p.hasPermission("uhc.start")) {
            p.getInventory().setItem(4, new ItemBuilder(startItem).setName(startName).build());
        }
        if (r.getSpawnFileManager().getLobby() != null) {
            if (Bukkit.getWorld(r.getSpawnFileManager().getLobbyWorldName()) == null) {
                p.teleport(p.getWorld().getHighestBlockAt(p.getWorld().getSpawnLocation()).getLocation());
            } else {
                p.teleport(r.getSpawnFileManager().getLobby().getWorld().getHighestBlockAt(r.getSpawnFileManager().getLobby()).getLocation());
            }
        }
        pl.addInGamePlayer(p);
        p.setGameMode(GameMode.ADVENTURE);
        p.setHealth(20);
        p.setFoodLevel(20);
        for (PotionEffect pe : p.getActivePotionEffects()) {
            if (p.hasPotionEffect(pe.getType())) {
                p.removePotionEffect(pe.getType());
            }
        }

        if (title.contains("[Player]")) {
            String aa = title.replace("[Player]", p.getDisplayName());
            if (subtitle.contains("[Player]")) {
                String bb = subtitle.replace("[Player]", p.getDisplayName());
                SimpleTitle.sendTitle(p, aa, bb, 3, 4, 3);
            } else {
                SimpleTitle.sendTitle(p, aa, subtitle, 3, 4, 3);
            }
        } else {
            SimpleTitle.sendTitle(p, title, subtitle, 3, 4, 3);
        }

        for (Player all : Bukkit.getOnlinePlayers()) {
            r.getAScoreboard().setLobbyScoreboard(all);
        }

        if (Bukkit.getOnlinePlayers().size() == r.getTimer().getPc()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    r.getTimer().startCountdown();
                }
            }.runTaskLater(Core.getInstance(), 20);
        }
    }

    @EventHandler
    public void onLeaveClick(PlayerInteractEvent e) {
        if (!(GState.isState(GState.LOBBY)))
            return;
        if (e.getItem() == null)
            return;
        if (e.getItem().getType().equals(leaveItem)) {
            if (r.getGameEndListener().isBungeeMode()) {
                ByteArrayDataOutput out = ByteStreams.newDataOutput();

                out.writeUTF("Connect");
                out.writeUTF(r.getGameEndListener().getBungeeServer());

                e.getPlayer().sendPluginMessage(Core.getInstance(), "BungeeCord", out.toByteArray());
            }
        } else if (e.getPlayer().hasPermission("uhc.start") && startMode
                && e.getItem().getType().equals(startItem)) {
            r.getTimer().changeTime();
            startMode = false;
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        r.getATeam().removePlayerFromTeam(e.getPlayer());
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    r.getAScoreboard().setLobbyScoreboard(all);
                }
            }
        }.runTaskLater(Core.getInstance(), 5);
    }
}
