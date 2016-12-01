package de.alphahelix.uhc.files;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;

public class BorderFile extends EasyFile {

    public BorderFile(UHC uhc) {
        super("border.uhc", uhc);
    }

    @Override
    public void addValues() {
        setDefault("damage (hearts)", 2);
        setDefault("delay (min)", 10);
        setDefault("moving distance", 500);
        setDefault("size", 3000);
        setDefault("shrinks", true);
    }

}
