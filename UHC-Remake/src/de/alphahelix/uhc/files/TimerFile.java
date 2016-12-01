package de.alphahelix.uhc.files;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;

public class TimerFile extends EasyFile {

    public TimerFile(UHC uhc) {
        super("timers.uhc", uhc);
    }

    @Override
    public void addValues() {
        setDefault("Lobby.lenght", 120);
        setDefault("Period of peace.length", 600);
        setDefault("WarmUp.length", 600);
        setDefault("Deathmatch warmup.length", 3600);
        setDefault("Deathmatch.lenght", 30);
        setDefault("End.length", 30);
    }

}
