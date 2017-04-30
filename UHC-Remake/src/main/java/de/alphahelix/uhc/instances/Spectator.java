package de.alphahelix.uhc.instances;

import de.alphahelix.alphalibary.utils.MinecraftVersion;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Spectator {

    public Spectator(final Player p) {
        Location l = p.getLocation();
        p.spigot().respawn();
        p.teleport(l);
        PlayerUtil.clearUp(p);
        p.setCanPickupItems(false);
        p.setVelocity(p.getVelocity().setY(20D));
        p.setTotalExperience(0);
        p.setGameMode(GameMode.ADVENTURE);
        p.setPlayerListName("§7[§4X§7] §c" + p.getPlayerListName());
        p.setAllowFlight(true);
        p.setFlying(true);
        try {
            if (MinecraftVersion.getServer() != MinecraftVersion.EIGHT) {
                Class.forName("org.bukkit.entity.Entity").getMethod("setInvulnerable", boolean.class).invoke(p, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        new BukkitRunnable() {
            public void run() {
                equipSpecStuff(p);
            }
        }.runTaskLater(UHC.getInstance(), 20);
        for (String ig : PlayerUtil.getSurvivors()) {
            Bukkit.getPlayer(ig).hidePlayer(p);
        }
    }

    public Spectator(final Player p, Location l) {
        p.teleport(l);
        PlayerUtil.clearUp(p);
        p.setCanPickupItems(false);
        p.setVelocity(p.getVelocity().setY(20D));
        p.setTotalExperience(0);
        p.setGameMode(GameMode.ADVENTURE);
        p.setPlayerListName("§7[§4X§7] §c" + p.getDisplayName());
        p.setAllowFlight(true);
        p.setFlying(true);
        new BukkitRunnable() {
            public void run() {
                equipSpecStuff(p);
            }
        }.runTaskLater(UHC.getInstance(), 20);
        for (String ig : PlayerUtil.getSurvivors()) {
            Bukkit.getPlayer(ig).hidePlayer(p);
        }
    }

    private void equipSpecStuff(Player p) {
        p.getInventory().setItem(UHCFileRegister.getSpectatorFile().getItem().getSlot(),
                UHCFileRegister.getSpectatorFile().getItem().getItemStack());
    }
}
