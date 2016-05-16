package de.alpha.uhc.border;

import de.alpha.uhc.Core;
import de.alpha.uhc.aclasses.AScoreboard;
import de.popokaka.alphalibary.nms.SimpleTitle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BorderManager {

    private static boolean moveable;
    private static String moved;
    private static int moving;
    private static int time;
    private int size = Border.getSize();

    public static void setMoveable(boolean moveable) {
        BorderManager.moveable = moveable;
    }

    public static void setMoved(String moved) {
        BorderManager.moved = moved;
    }

    public static void setMoving(int moving) {
        BorderManager.moving = moving;
    }

    public static void setTime(int time) {
        BorderManager.time = time;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void set() {

        if (!moveable) {
            return;
        }

        new BukkitRunnable() {

            @Override
            public void run() {

                Bukkit.broadcastMessage(Core.getPrefix() + moved);
                size = Border.getSize() - moving;
                Border.changesize(size);
                for (Player all : Bukkit.getOnlinePlayers()) {
                    AScoreboard.updateInGameBorder(all);
                    SimpleTitle.sendTitle(all, " ", moved, 1, 2, 1);
                }

            }
        }.runTaskTimer(Core.getInstance(), 0, time);
        //
    }

}
