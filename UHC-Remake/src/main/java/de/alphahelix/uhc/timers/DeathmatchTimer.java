package de.alphahelix.uhc.timers;

import de.alphahelix.alphalibary.nms.SimpleActionBar;
import de.alphahelix.alphalibary.nms.SimpleTitle;
import de.alphahelix.alphalibary.utils.Util;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.enums.Sounds;
import de.alphahelix.uhc.events.timers.InGameStartEvent;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import de.alphahelix.uhc.util.PlayerUtil;
import de.alphahelix.uhc.util.ScoreboardUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DeathmatchTimer extends AbstractTimer {

    @Override
    public void startTimer() {
        if (!GState.isState(GState.IN_GAME)) return;
        if (isRunning()) return;

        resetTime();

        Bukkit.getPluginManager().callEvent(new InGameStartEvent());

        setSecondTimer(() -> {
            if (getSeconds() > 0) {
                setSeconds(getSeconds() - 1);

                for (Player p : Util.makePlayerArray(PlayerUtil.getAll())) {
                    String time = (getMinTime() >= 1 ? UHCFileRegister.getMessageFile().getTimeLeftInfo(Util.round(getMinTime(), 1), UHCFileRegister.getUnitFile().getMinutes()) : UHCFileRegister.getMessageFile().getTimeLeftInfo(Util.round(getSeconds(), 1), UHCFileRegister.getUnitFile().getSeconds()));

                    ScoreboardUtil.updateTime(p);

                    if (getMinTime() % 5 == 0 && getSeconds() > 10 && getSeconds() != 0) {
                        p.sendMessage(UHC.getPrefix() + time);
                        SimpleTitle.sendTitle(p, UHC.getPrefix(), time, 1, 2, 1);
                        p.playSound(p.getLocation(), Sounds.NOTE_BASS.bukkitSound(), 1F, 0F);
                    }

                    if (getSeconds() < 10 && getSeconds() != 0) {
                        SimpleActionBar.send(p, UHC.getPrefix() + UHCFileRegister.getMessageFile().getTimeLeftInfo(getSeconds(), UHCFileRegister.getUnitFile().getSeconds()));
                        p.playSound(p.getLocation(), Sounds.NOTE_BASS.bukkitSound(), 1F, 0F);
                    }

                    if (getSeconds() == 0) {
                        p.sendMessage(UHC.getPrefix() + UHCFileRegister.getMessageFile().getEnd());
                        SimpleActionBar.send(p, UHC.getPrefix() + UHCFileRegister.getMessageFile().getEnd());
                        p.teleport(UHCFileRegister.getLocationsFile().getDeathmatch());
                    }
                }

                if (getSeconds() == 0) {
                    stopTimer();

                    GState.setCurrentState(GState.DEATHMATCH_WARMUP);

                    UHCRegister.getStartDeathMatchTimer().startTimer();
                }
            }
        });
    }
}
