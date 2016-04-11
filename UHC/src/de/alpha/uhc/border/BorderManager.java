package de.alpha.uhc.border;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.alpha.uhc.Core;
import de.alpha.uhc.aclasses.AScoreboard;
import de.popokaka.alphalibary.nms.SimpleTitle;

public class BorderManager {
	
	public static boolean moveable;
	public static String moved;
	
	private int size = Border.size;
	public static int moving;
	public static int time;

	public void set() {
		
		if(moveable == false) {
			return;
		}
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				Bukkit.broadcastMessage(Core.getPrefix() + moved);
				size = Border.size - moving;
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
