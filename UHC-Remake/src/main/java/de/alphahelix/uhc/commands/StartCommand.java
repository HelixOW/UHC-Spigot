package de.alphahelix.uhc.commands;

import de.alphahelix.alphalibary.command.SimpleCommand;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class StartCommand extends SimpleCommand<UHC> {

    public StartCommand(UHC plugin) {
        super(plugin, "start", "Short or strech the lobby time.");
    }

    @Override
    public boolean execute(CommandSender cs, String label, String[] args) {
        if (!(cs instanceof Player))
            return true;

        Player p = (Player) cs;

        if (!(p.hasPermission("uhc.start") || p.hasPermission("uhc.admin"))) {
            p.sendMessage(getPlugin().getPrefix() + UHCFileRegister.getMessageFile().getNoPermissions());
            return true;
        }

        if (args.length == 0) {
            UHCRegister.getLobbyTimer().changeTime(10);
        } else if (args.length == 1) {
            UHCRegister.getLobbyTimer().changeTime(Integer.parseInt(args[0]));
        } else {
            p.sendMessage(getPlugin().getPrefix() + UHCFileRegister.getMessageFile().getCommandNotFound());
        }

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender cs, String label, String[] args) {
        return null;
    }

}
