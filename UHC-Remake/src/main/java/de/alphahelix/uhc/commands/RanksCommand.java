package de.alphahelix.uhc.commands;

import de.alphahelix.alphalibary.command.SimpleCommand;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.UHCRank;
import de.alphahelix.uhc.register.UHCFileRegister;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Created by AlphaHelixDev.
 */
public class RanksCommand extends SimpleCommand<UHC> {

    public RanksCommand(UHC plugin) {
        super(plugin, "ranks", "See all ranks!", "rank");
    }

    @Override
    public boolean execute(CommandSender cs, String label, String[] args) {
        cs.sendMessage(UHCFileRegister.getMessageFile().getRanklist(filterEachRank()));
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender cs, String label, String[] args) {
        return null;
    }

    private String filterEachRank() {
        String ranks = "";
        for(UHCRank rank : UHCRank.getRanks()) {
            ranks = ranks + "\n"
                        + "  " + rank.getPrefix() + "\n"
                        + "    " + UHCFileRegister.getStatsFile().getColorString("Kills").replace("[kills]", Long.toString(rank.getMinKills())) + "\n"
                        + "    " + UHCFileRegister.getStatsFile().getColorString("Wins").replace("[wins]", Long.toString(rank.getMinWins())) + "\n"
                        + "    " + UHCFileRegister.getStatsFile().getColorString("Points").replace("[points]", Long.toString(rank.getMinPoints()));
        }
        return ranks;
    }
}
