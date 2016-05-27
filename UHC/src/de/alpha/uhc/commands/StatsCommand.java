package de.alpha.uhc.commands;

import de.alpha.uhc.Core;
import de.alpha.uhc.utils.Stats;
import de.popokaka.alphalibary.command.SimpleCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class StatsCommand extends SimpleCommand<Core> {

    private static boolean us;
    private static String er;

    public StatsCommand(Core plugin, String[] aliases) {
        super(plugin, "stats", "See your Statistics of UHC", aliases);
    }

    private static boolean inUs() {
        return us;
    }

    public static void setUs(boolean a) {
        us = a;
    }

    private static String getEr() {
        return er;
    }

    public static void setEr(String a) {
        StatsCommand.er = a;
    }

    @Override
    public boolean execute(CommandSender cs, String label, String[] args) {
        if (!(cs instanceof Player)) {
            cs.sendMessage(Core.getInstance().getPrefix() + UHCCommand.getNoplayer());
            return false;
        }
        if (inUs()) {
            Player p = (Player) cs;
            new Stats(p).sendStats();
        } else {
            cs.sendMessage(Core.getInstance().getPrefix() + getEr());
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender cs, String label, String[] args) {
        return null;
    }

}
