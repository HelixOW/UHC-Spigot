package de.alpha.uhc.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import de.alpha.uhc.Core;
import de.alpha.uhc.Registery;
import de.popokaka.alphalibary.command.SimpleCommand;

public class StartCommand extends SimpleCommand<Core> {
	
    private boolean use;
    private String err;
    private Core pl;
    private Registery r;

    public StartCommand(Core plugin, String[] aliases) {
        super(plugin, "start", "Short the timer to 10 seconds", aliases);
        this.pl = plugin;
        this.r = pl.getRegistery();
    }

    private boolean inUse() {
        return use;
    }

    public void setUse(boolean a) {
        this.use = a;
    }

    private String getErr() {
        return err;
    }

    public void setErr(String a) {
        this.err = a;
    }

    @Override
    public boolean execute(CommandSender cs, String label, String[] args) {
        if (this.inUse()) {
            if (cs.hasPermission("UHC.start")) {
                r.getTimer().changeTime();
            } else {
                cs.sendMessage(pl.getPrefix() + Core.getInstance().getRegistery().getUHCCommand().getNoperms());
            }
        } else {
            cs.sendMessage(pl.getPrefix() + this.getErr());
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender cs, String label, String[] args) {
        return null;
    }

}
