package de.alphahelix.uhc.files;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;

public class StatsFile extends EasyFile {

    public StatsFile(UHC uhc) {
        super("statsMessages.uhc", uhc);
    }

    @Override
    public void addValues() {
        setDefault("Command Errormessage", "&7Please use &c&l/stats <player>");
        setDefault("No Player", "&7The given Player doesn't exists.");
        setDefault("Name", "&6Name &7: &6[player]");
        setDefault("Rank", "&6Rank &7: &6[rank]");
        setDefault("Games", "&6Games &7: &6[games]");
        setDefault("Kills", "&6Kills &7: &6[kills]");
        setDefault("Deaths", "&6Deaths &7: &6[deaths]");
        setDefault("KillDeathRate", "&6KillDeathRate &7: &6[kdr]");
        setDefault("Coins", "&6Coins &7: &6[coins]");
        setDefault("Points", "&6Points &7: &6[points]");
        setDefault("Wins", "&6Wins &7: &6[wins]");
        setDefault("Kits", "&6Kits &7: &6[kits]");
        setDefault("Achievements", "&6Achievements &7: &6[achievements]");
        setDefault("StatsNPC", "&5Stats");
    }

    public String getMessage() {
        return getColorString("Name") + "\n"
                + getColorString("Rank") + "\n"
                + getColorString("Games") + "\n"
                + getColorString("Kills") + "\n"
                + getColorString("Deaths") + "\n"
                + getColorString("KillDeathRate") + "\n"
                + getColorString("Coins") + "\n"
                + getColorString("Points") + "\n"
                + getColorString("Wins") + "\n"
                + getColorString("Kits") + "\n"
                + getColorString("Achievements");
    }

    public String getErrorMessage() {
        return getColorString("Command Errormessage");
    }

    public String getNoPlayerMessage() {
        return getColorString("No Player");
    }
}
