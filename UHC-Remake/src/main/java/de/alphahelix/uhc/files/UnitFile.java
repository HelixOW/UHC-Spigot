package de.alphahelix.uhc.files;

import de.alphahelix.alphalibary.file.SimpleFile;

public class UnitFile extends SimpleFile {

    public UnitFile() {
        super("plugins/UHC-Remake","units.uhc");
    }

    @Override
    public void addValues() {
        setDefault("Seconds", "sec");
        setDefault("Minutes", "min");
        setDefault("Hours", "h");
        setDefault("Days", "d");
    }

    public String getSeconds() {
        return getString("Seconds");
    }

    public String getMinutes() {
        return getString("Minutes");
    }

    public String getHours() {
        return getString("Hours");
    }

    public String getDays() {
        return getString("Days");
    }
}
