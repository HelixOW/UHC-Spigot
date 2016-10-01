package de.alphahelix.uhc.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.popokaka.alphalibary.command.SimpleCommand;

public class InfoCommand extends SimpleCommand<UHC>{

	public InfoCommand(UHC plugin, String command, String description, String... aliases) {
		super(plugin, command, description, aliases);
	}

	@Override
	public boolean execute(CommandSender cs, String label, String[] args) {
		
		if(getPlugin().isScenarios())
			cs.sendMessage(getPlugin().getPrefix() + getPlugin().getRegister().getScenarioFile().getCustomScenarioName(Scenarios.getScenario()) + "§8: " + getPlugin().getRegister().getScenarioHelpFile().getScenarioDescriptionAsOneString(Scenarios.getScenario()));
		else {
			cs.sendMessage(getPlugin().getPrefix()
					+ getPlugin().getRegister().getMainOptionsFile().getColorString("Warnings.Scenario Mode"));
		}
		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender cs, String label, String[] args) {
		ArrayList<String> tr = new ArrayList<>();
		
		tr.add("informations");
		return tr;
	}
}
