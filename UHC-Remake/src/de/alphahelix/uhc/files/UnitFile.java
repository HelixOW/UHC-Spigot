package de.alphahelix.uhc.files;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;

public class UnitFile extends EasyFile {

    public UnitFile(UHC uhc) {
        super("units.uhc", uhc);
    }

    @Override
    public void addValues() {
        setDefault("Seconds", "sec");
        setDefault("Minutes", "min");
        setDefault("Hours", "h");
    }

}
