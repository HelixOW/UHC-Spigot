package de.alphahelix.uhc.commands;

import de.alphahelix.alphalibary.command.SimpleCommand;
import de.alphahelix.uhc.Registery;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.UHCRank;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Created by AlphaHelixDev.
 */
public class RanksCommand extends SimpleCommand<UHC, Registery> {

    public RanksCommand(UHC plugin, Registery register) {
        super(plugin, register, "ranks", "See all ranks!", "rank");
    }

    @Override
    public boolean execute(CommandSender cs, String label, String[] args) {

        cs.sendMessage(getRegister().getMessageFile().getColorString("Ranklist").replace("[ranks]", filterEachRank()));

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
                        + "    " + getRegister().getStatsFile().getColorString("Kills").replace("[kills]", Long.toString(rank.getMinKills())) + "\n"
                        + "    " + getRegister().getStatsFile().getColorString("Wins").replace("[wins]", Long.toString(rank.getMinWins())) + "\n"
                        + "    " + getRegister().getStatsFile().getColorString("Points").replace("[points]", Long.toString(rank.getMinPoints()));
        }
        return ranks;
    }
}
