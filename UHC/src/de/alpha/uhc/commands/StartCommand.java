package de.alpha.uhc.commands;

import de.alpha.uhc.Core;
import de.alpha.uhc.timer.Timer;
import de.popokaka.alphalibary.command.SimpleCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

public class StartCommand extends SimpleCommand<Core> {

    private static boolean use;
    private static String err;

    public StartCommand(Core plugin, String[] aliases) {
        super(plugin, "start", "Short the timer to 10 seconds", aliases);
    }

    private static boolean inUse() {
        return use;
    }

    public static void setUse(boolean a) {
        StartCommand.use = a;
    }

    private static String getErr() {
        return err;
    }

    public static void setErr(String a) {
        StartCommand.err = a;
    }

    @Override
    public boolean execute(CommandSender cs, String label, String[] args) {
        if (StartCommand.inUse()) {
            if (cs.hasPermission("UHC.start")) {
                Timer.changeTime();
            } else {
                cs.sendMessage(Core.getPrefix() + UHCCommand.getNoperms());
            }
        } else {
            cs.sendMessage(Core.getPrefix() + StartCommand.getErr());
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender cs, String label, String[] args) {
        return null;
    }

}
