package de.alphahelix.uhc.files;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;

public class TablistFile extends EasyFile {

    public TablistFile(UHC uhc) {
        super("tablist.uhc", uhc);
    }

    @Override
    public void addValues() {
        setDefault("Header", "&b>> &cWelcome [player] &cat UHC &b<< \n &7Players:");
        setDefault("Footer",
                "&b>> &cThere are currently &4[playercount] &cplayers online &b<< \n &b>> &cCurrent Gamestate: &4[gamestatus] &b<<");
    }

}
