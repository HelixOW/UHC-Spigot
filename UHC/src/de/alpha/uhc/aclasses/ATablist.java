package de.alpha.uhc.aclasses;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.Registery;
import de.popokaka.alphalibary.nms.SimpleTablist;

public class ATablist {
	
	private Core pl;
	private Registery r;
	
	public ATablist(Core c) {
		this.pl = c;
		this.r = pl.getRegistery();
	}

    private  String header;
    private  String footer;

    public  void setHeader(String header) {
        this.header = header;
    }

    public  void setFooter(String footer) {
        this.footer = footer;
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

            header = r.getMessageFile().getMSGFile().getColorString("Tablist.Top");
            footer = r.getMessageFile().getMSGFile().getColorString("Tablist.Bottom");
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

            header = r.getMessageFile().getMSGFile().getColorString("Tablist.Top");
            footer = r.getMessageFile().getMSGFile().getColorString("Tablist.Bottom");
        }
    }
}
