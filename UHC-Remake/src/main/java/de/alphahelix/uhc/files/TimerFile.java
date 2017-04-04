package de.alphahelix.uhc.files;

import de.alphahelix.alphaapi.file.SimpleFile;
import de.alphahelix.uhc.enums.GState;

public class TimerFile extends SimpleFile {

    public TimerFile() {
        super("timers.uhc");
    }

    @Override
    public void addValues() {
        setDefault("Lobby", 120);
        setDefault("Period of peace", 600);
        setDefault("Warmup", 600);
        setDefault("Deathmatch warmup", 3600);
        setDefault("Deathmatch", 30);
        setDefault("End", 30);
    }

    public int getLenght(GState state) {
        switch (state) {
            case LOBBY:
                return getInt("Lobby");
            case PERIOD_OF_PEACE:
                return getInt("Period of peace");
            case WARMUP:
                return getInt("Warmup");
            case IN_GAME:
                return getInt("In game");
            case DEATHMATCH_WARMUP:
                return getInt("Deathmatch warmup");
            case DEATHMATCH:
                return getInt("Deathmatch");
            case END:
                return getInt("End");
            default:
                return 0;
        }
    }
}
