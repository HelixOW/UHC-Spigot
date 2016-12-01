package de.alphahelix.uhc.files;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;

public class MOTDFile extends EasyFile {

    public MOTDFile(UHC uhc) {
        super("uhc", uhc);
    }

    @Override
    public void addValues() {
        setDefault("Lobby", "&6UHC&8-&6Remake \n &aJoinable&8!");
        setDefault("Period of peace", "&6UHC&8-&6Remake \n &dPeriod of peace&8!");
        setDefault("Warmup", "&6UHC&8-&6Remake \n &3Warmup&8!");
        setDefault("Ingame", "&6UHC&8-&6Remake \n &cIngame&8!");
        setDefault("Deathmatch warmup", "&6UHC&8-&6Remake \n &eDeathmatch warmup&8!");
        setDefault("Deathmatch", "&6UHC&8-&6Remake \n &4Deathmatch&8!");
        setDefault("End", "&6UHC&8-&6Remake \n &5Restart&8!");
    }

}
