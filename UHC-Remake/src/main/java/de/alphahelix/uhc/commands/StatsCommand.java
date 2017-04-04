package de.alphahelix.uhc.commands;

import de.alphahelix.alphaapi.command.SimpleCommand;
import de.alphahelix.alphaapi.uuid.UUIDFetcher;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.util.StatsUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class StatsCommand extends SimpleCommand {

    public StatsCommand() {
        super("stats", "Check your or others stats", "records");
    }

    @Override
    public boolean execute(CommandSender cs, String label, String[] args) {
        if (!(cs instanceof Player)) return true;

        if (args.length == 0) {
            StatsUtil.sendStats((Player) cs, UUIDFetcher.getUUID((OfflinePlayer) cs));
            return true;
        }
        if (args.length == 1) {
            if (Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(args[0])) == null) {
                cs.sendMessage(UHC.getPrefix() + UHCFileRegister.getStatsFile().getNoPlayerMessage());
                return true;
            }
            StatsUtil.sendStats((Player) cs, UUIDFetcher.getUUID(args[0]));
            return true;
        } else {
            cs.sendMessage(UHC.getPrefix() + UHCFileRegister.getStatsFile().getErrorMessage());
        }

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender cs, String label, String[] args) {
        return null;
    }
}
