package de.alpha.uhc.aclasses;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.files.MessageFileManager;
import de.popokaka.alphalibary.nms.SimpleTablist;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ATablist {
	
	public ATablist(Core c) {
	}

    private  String header;
    private  String footer;

    public  void setHeader(String header) {
        ATablist.header = header;
    }

    public  void setFooter(String footer) {
        ATablist.footer = footer;
    }

    public  void sendStandingLobbyTablist() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            header = header.replace("[player]", all.getDisplayName());
            header = header.replace("[playercount]", Integer.toString(Bukkit.getOnlinePlayers().size()));
            header = header.replace("[gamestatus]", GState.getGStateName());

            footer = footer.replace("[player]", all.getDisplayName());
            footer = footer.replace("[playercount]", Integer.toString(Bukkit.getOnlinePlayers().size()));
            footer = footer.replace("[gamestatus]", GState.getGStateName());

            SimpleTablist.setTablistHeaderFooter(all, null, null);
            SimpleTablist.setTablistHeaderFooter(all, header, footer);

            header = MessageFileManager.getMSGFile().getColorString("Tablist.Top");
            footer = MessageFileManager.getMSGFile().getColorString("Tablist.Bottom");
        }


    }

    public  void sendStandingInGameTablist() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            header = header.replace("[player]", all.getDisplayName());
            header = header.replace("[playercount]", Integer.toString(Core.getInstance().getInGamePlayers().size()));
            header = header.replace("[gamestatus]", GState.getGStateName());

            footer = footer.replace("[player]", all.getDisplayName());
            footer = footer.replace("[playercount]", Integer.toString(Core.getInstance().getInGamePlayers().size()));
            footer = footer.replace("[gamestatus]", GState.getGStateName());

            SimpleTablist.setTablistHeaderFooter(all, null, null);
            SimpleTablist.setTablistHeaderFooter(all, header, footer);

            header = MessageFileManager.getMSGFile().getColorString("Tablist.Top");
            footer = MessageFileManager.getMSGFile().getColorString("Tablist.Bottom");
        }
    }
}
