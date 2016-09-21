package de.alphahelix.uhc.files;

import java.util.ArrayList;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;

public class ScenarioFile extends EasyFile {

	public ScenarioFile(UHC uhc) {
		super("scenario.uhc", uhc);
	}

	@Override
	public void addValues() {
		setDefault("Scenarios enabled", false);
		setDefault("Scenarios Item", "Paper");
		setDefault("Scenarios Item Name", "&cScenario&7: &c-");
		setDefault("Scenarios Item Slot", 1);
		for (String scenario : getScenarioNames()) {
			setDefault("Scenarios." + scenario + ".enabled", true);
			setDefault("Scenarios." + scenario + ".name", scenario);
		}
	}
	
	public String getCustomScenarioName(Scenarios s) {
		return getString("Scenarios."+Scenarios.getRawScenarioName(s)+".name");
	}
	
	public Scenarios getScenarioByCustomName(String customName) {
		for (String scenario : getScenarioNames()) {
			if(getString("Scenarios." + scenario + ".name").equals(customName))
				return Scenarios.valueOf(scenario);
		}
		return null;
	}

	public boolean isEnabled(String scenario) {
		if (isConfigurationSection("Scenarios." + scenario))
			return getBoolean("Scenarios." + scenario + ".enabled");
		return false;
	}

	private ArrayList<String> getScenarioNames() {
		ArrayList<String> scenarioNames = new ArrayList<>();
		for (Scenarios s : Scenarios.values())
			scenarioNames.add(s.name().replace("_", " ").toLowerCase());
		return scenarioNames;
	}
}
