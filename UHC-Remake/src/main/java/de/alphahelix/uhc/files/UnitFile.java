package de.alphahelix.uhc.files;

import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.uhc.UHC;

public class UnitFile extends SimpleFile<UHC> {

    public UnitFile(UHC uhc) {
        super("units.uhc", uhc);
    }

    @Override
    public void addValues() {
        setDefault("Seconds", "sec");
        setDefault("Minutes", "min");
        setDefault("Hours", "h");
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
}
