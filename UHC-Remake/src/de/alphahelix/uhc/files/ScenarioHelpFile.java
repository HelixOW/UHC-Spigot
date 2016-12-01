package de.alphahelix.uhc.files;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;

public class ScenarioHelpFile extends EasyFile {

    public ScenarioHelpFile(UHC uhc) {
        super("scenariohelp.uhc", uhc);
    }

    @Override
    public void addValues() {
        setDefault("ScenarioName (please use the real scenario name not your edited ones!)", "Put in your description of the scenario here. See all english descriptions here:[https://github.com/AlphaHelixDev/UHC-Spigot/blob/master/UHC-Remake/scenarios.uhc] To make a new line please add [nl]");
        setDefault("half ores", "&7Only every &csecond &7ore is &cdropped&7! \n &7Only Ores!");
    }

    public String[] getScenarioDescription(Scenarios s) {
        if (isString(Scenarios.getRawScenarioName(s))) {
            return getColorString(Scenarios.getRawScenarioName(s)).split("\n");
        }
        return new String[]{""};
    }

    public String getScenarioDescriptionAsOneString(Scenarios s) {
        if (isSet(Scenarios.getRawScenarioName(s))) {
            return getColorString(Scenarios.getRawScenarioName(s)).replace("\n", "");
        }
        return "";
    }
}
