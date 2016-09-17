package de.alphahelix.uhc.files.scenarios;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;

public class ScenarioFile extends EasyFile {

	public ScenarioFile(UHC uhc) {
		super("Scenario.uhc", uhc);
	}
	
	@Override
	public void addValues() {
		setDefault("GUI.Name", "&cScenario - Roulette");
		setDefault("Scenarios", false);
		setDefault("Scenarios Item", "Paper");
		setDefault("Scenarios Item Name", "&cScenario&7: &c-");
		setDefault("Scenarios Item Slot", 1);
	}

}
