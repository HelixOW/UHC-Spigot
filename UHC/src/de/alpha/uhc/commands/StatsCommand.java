package de.alpha.uhc.commands;

import de.alpha.uhc.Core;
import de.alpha.uhc.Registery;
import de.alpha.uhc.utils.Stats;
import de.popokaka.alphalibary.command.SimpleCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class StatsCommand extends SimpleCommand<Core> {

    private boolean us;
    private String er;
    private Core pl;
    private Registery r;

    public StatsCommand(Core plugin, String[] aliases) {
        super(plugin, "stats", "See your Statistics of UHC", aliases);
        this.pl = plugin;
        this.r = pl.getRegistery();
    }

    private boolean inUs() {
        return us;
    }

    public void setUs(boolean a) {
        us = a;
    }

    private String getEr() {
        return er;
    }

    public void setEr(String a) {
        this.er = a;
    }

    @Override
    public boolean execute(CommandSender cs, String label, String[] args) {
        if (!(cs instanceof Player)) {
            cs.sendMessage(pl.getPrefix() + r.getUHCCommand().getNoplayer());
            return false;
        }
        if (inUs()) {
            Player p = (Player) cs;
            new Stats(p).sendStats();
        } else {
            cs.sendMessage(pl.getPrefix() + getEr());
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender cs, String label, String[] args) {
        return null;
    }

}
