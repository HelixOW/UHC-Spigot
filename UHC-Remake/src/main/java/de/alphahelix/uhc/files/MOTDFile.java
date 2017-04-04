package de.alphahelix.uhc.files;

import de.alphahelix.alphaapi.file.SimpleFile;
import de.alphahelix.uhc.enums.GState;

public class MOTDFile extends SimpleFile {

    public MOTDFile() {
        super("motd.uhc");
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

    public String getMOTD(GState state) {
        switch (state) {
            case LOBBY:
                return getColorString("Lobby");
            case PERIOD_OF_PEACE:
                return getColorString("Period of peace");
            case WARMUP:
                return getColorString("Warmup");
            case IN_GAME:
                return getColorString("Ingame");
            case DEATHMATCH_WARMUP:
                return getColorString("Deathmatch warmup");
            case DEATHMATCH:
                return getColorString("Deathmatch");
            case END:
                return getColorString("End");
            default:
                return "";
        }
    }
}
