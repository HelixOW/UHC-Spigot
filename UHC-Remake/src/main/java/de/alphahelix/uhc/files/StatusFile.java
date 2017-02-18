package de.alphahelix.uhc.files;

import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;

public class StatusFile extends SimpleFile<UHC> {

    public StatusFile(UHC uhc) {
        super("status.uhc", uhc);
    }

    @Override
    public void addValues() {
        setDefault("Lobby", "&alobby");
        setDefault("Period of peace", "&dperiod of peace");
        setDefault("Warmup", "&bwarmup");
        setDefault("In game", "&cin game");
        setDefault("Deathmatch warmup", "&3DM - warmup");
        setDefault("Deathmatch", "&4Deathmatch");
        setDefault("End", "&8Restart");
    }

    public String getStatus(GState state) {
        switch (state) {
            case LOBBY:
                return getColorString("Lobby");
            case PERIOD_OF_PEACE:
                return getColorString("Period of peace");
            case WARMUP:
                return getColorString("Warmup");
            case IN_GAME:
                return getColorString("In game");
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
