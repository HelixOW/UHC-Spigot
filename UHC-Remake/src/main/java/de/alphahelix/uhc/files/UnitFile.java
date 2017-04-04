package de.alphahelix.uhc.files;

import de.alphahelix.alphaapi.file.SimpleFile;

public class UnitFile extends SimpleFile {

    public UnitFile() {
        super("units.uhc");
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
