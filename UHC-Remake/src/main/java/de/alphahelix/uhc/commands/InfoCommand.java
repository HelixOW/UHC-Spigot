package de.alphahelix.uhc.commands;

import de.alphahelix.alphalibary.command.SimpleCommand;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.register.UHCFileRegister;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class InfoCommand extends SimpleCommand<UHC> {

    public InfoCommand(UHC plugin) {
        super(plugin, "informations", "Get informations about the current scenario", "scenario", "infos");
    }

    @Override
    public boolean execute(CommandSender cs, String label, String[] args) {

        if (getPlugin().isScenarios())
            cs.sendMessage(getPlugin().getPrefix() + UHCFileRegister.getScenarioFile().getCustomScenarioName(Scenarios.getScenario()) + ChatColor.DARK_GRAY + ": " + UHCFileRegister.getScenarioHelpFile().getScenarioDescriptionAsOneString(Scenarios.getScenario()));
        else {
            cs.sendMessage(getPlugin().getPrefix()
                    + UHCFileRegister.getMessageFile().getScenariomode());
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender cs, String label, String[] args) {
        return null;
    }
}
