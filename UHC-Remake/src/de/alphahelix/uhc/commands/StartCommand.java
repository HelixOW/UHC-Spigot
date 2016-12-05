package de.alphahelix.uhc.commands;

import de.alphahelix.alphalibary.command.SimpleCommand;
import de.alphahelix.uhc.Registery;
import de.alphahelix.uhc.UHC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class StartCommand extends SimpleCommand<UHC, Registery> {

    public StartCommand(UHC plugin, Registery r) {
        super(plugin, r, "start", "Short or strech the lobby time.", "start");
    }

    @Override
    public boolean execute(CommandSender cs, String label, String[] args) {
        if (!(cs instanceof Player))
            return true;

        Player p = (Player) cs;

        if (!(p.hasPermission("uhc.start") || p.hasPermission("uhc.admin"))) {
            p.sendMessage(getPlugin().getPrefix() + getPlugin().getRegister().getMessageFile().getColorString("No Permissions"));
            return true;
        }

        if (args.length == 0) {
            getPlugin().getRegister().getLobbyTimer().changeTime(10);
        } else if (args.length == 1) {
            getPlugin().getRegister().getLobbyTimer().changeTime(Integer.parseInt(args[0]));
        } else {
            p.sendMessage(getPlugin().getPrefix() + getPlugin().getRegister().getMessageFile().getColorString("Command not found"));
        }

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender cs, String label, String[] args) {
        return null;
    }

}
