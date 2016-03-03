package de.alpha.uhc.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.alpha.border.Border;
import de.alpha.uhc.Core;

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
				size = size - moving;
				new Border().changesize(size);
				for(Player all : Bukkit.getOnlinePlayers()) {
					ScoreboardManager.updateBorderScore(all);
					TitleManager.sendTitle(all, 10, 20, 10, " ", moved);
				}
				
			}
		}.runTaskTimer(Core.getInstance(), 0, time);
		//
	}

}
