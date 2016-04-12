package de.alpha.uhc.aclasses;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.files.MessageFileManager;
import de.popokaka.alphalibary.nms.SimpleTablist;

public class ATablist {
	
	private static String header;
	private static String footer;
	
	
	
	public static  void setHeader(String header) {
		ATablist.header = header;
	}

	public static  void setFooter(String footer) {
		ATablist.footer = footer;
	}

	public static void sendStandingLobbyTablist() {
		for(Player all : Bukkit.getOnlinePlayers()) {
			header = header.replace("[player]", all.getDisplayName());
			header = header.replace("[playercount]", Integer.toString(Bukkit.getOnlinePlayers().size()));
			header = header.replace("[gamestatus]", GState.getGStateName());
			
			footer = footer.replace("[player]", all.getDisplayName());
			footer = footer.replace("[playercount]", Integer.toString(Bukkit.getOnlinePlayers().size()));
			footer = footer.replace("[gamestatus]", GState.getGStateName());
			
			SimpleTablist.setTablistHeaderFooter(all, "", "");
			SimpleTablist.setTablistHeaderFooter(all, header, footer);
			
			header = MessageFileManager.getMSGFile().getColorString("Tablist.Top");
			footer = MessageFileManager.getMSGFile().getColorString("Tablist.Bottom");
		}
		
		
	}
	
	public static void sendStandingInGameTablist() {
		for(Player all : Bukkit.getOnlinePlayers()) {
			header = header.replace("[player]", all.getDisplayName());
			header = header.replace("[playercount]", Integer.toString(Core.getInGamePlayers().size()));
			header = header.replace("[gamestatus]", GState.getGStateName());
			
			footer = footer.replace("[player]", all.getDisplayName());
			footer = footer.replace("[playercount]", Integer.toString(Core.getInGamePlayers().size()));
			footer = footer.replace("[gamestatus]", GState.getGStateName());
			
			SimpleTablist.setTablistHeaderFooter(all, "", "");
			SimpleTablist.setTablistHeaderFooter(all, header, footer);
			
			header = MessageFileManager.getMSGFile().getColorString("Tablist.Top");
			footer = MessageFileManager.getMSGFile().getColorString("Tablist.Bottom");
		}
	}
}
