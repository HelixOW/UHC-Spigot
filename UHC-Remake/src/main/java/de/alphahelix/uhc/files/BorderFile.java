package de.alphahelix.uhc.files;

import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.uhc.UHC;

public class BorderFile extends SimpleFile<UHC> {

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

    public int getDamageInHearts() {
        return getInt("damage (hearts)");
    }

    public int getDelay() {
        return getInt("delay (min)");
    }

    public int getMovingDistance() {
        return getInt("moving distance");
    }

    public int getSize() {
        return getInt("size");
    }

    public boolean doShrinking() {
        return getBoolean("shrinks");
    }
}
