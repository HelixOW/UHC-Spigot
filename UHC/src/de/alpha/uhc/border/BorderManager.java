package de.alpha.uhc.border;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.alpha.uhc.Core;
import de.alpha.uhc.Registery;
import de.popokaka.alphalibary.nms.SimpleTitle;

public class BorderManager extends Border{
	
	private Core pl;
	private Registery r;
	
	public BorderManager(Core c) {
		super(c);
		this.pl = c;
		this.r = pl.getRegistery();
	}

    private  boolean moveable;
    private  String moved;
    private  int moving;
    private  int time;
    private int size = getSize();

    public  void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }

    public  void setMoved(String moved) {
        this.moved = moved;
    }

    public  void setMoving(int moving) {
        this.moving = moving;
    }

    public  void setTime(int time) {
        this.time = time;
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

                Bukkit.broadcastMessage(Core.getInstance().getPrefix() + moved);
                size = r.getBorder().getSize() - moving;
                r.getBorder().changesize(size);
                for (Player all : Bukkit.getOnlinePlayers()) {
                	r.getAScoreboard().updateInGameBorder(all);
                    SimpleTitle.sendTitle(all, " ", moved, 1, 2, 1);
                }

            }
        }.runTaskTimer(Core.getInstance(), 0, time);
        //
    }

}
