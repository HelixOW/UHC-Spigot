package de.alpha.uhc.border;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.alpha.uhc.Core;
import de.alpha.uhc.aclasses.AScoreboard;
import de.popokaka.alphalibary.nms.SimpleTitle;

public class BorderManager {
	
	private static boolean moveable;
	private static String moved;
	
	private int size = Border.getSize();
	private static int moving;
	private static int time;

	public static synchronized boolean isMoveable() {
		return moveable;
	}

	public static synchronized void setMoveable(boolean moveable) {
		BorderManager.moveable = moveable;
	}

	public static synchronized String getMoved() {
		return moved;
	}

	public static synchronized void setMoved(String moved) {
		BorderManager.moved = moved;
	}

	public synchronized int getSize() {
		return size;
	}

	public synchronized void setSize(int size) {
		this.size = size;
	}

	public static synchronized int getMoving() {
		return moving;
	}

	public static synchronized void setMoving(int moving) {
		BorderManager.moving = moving;
	}

	public static synchronized int getTime() {
		return time;
	}

	public static synchronized void setTime(int time) {
		BorderManager.time = time;
	}

	public void set() {
		
		if(moveable == false) {
			return;
		}
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				Bukkit.broadcastMessage(Core.getPrefix() + moved);
				size = Border.getSize() - moving;
				Border.changesize(size);
				for(Player all : Bukkit.getOnlinePlayers()) {
					AScoreboard.updateInGameBorder(all);
					SimpleTitle.sendTitle(all, " ", moved, 10, 20, 10);
				}
				
			}
		}.runTaskTimer(Core.getInstance(), 0, time);
		//
	}

}
