package de.alphahelix.uhc.timers;

import de.alphahelix.alphaapi.nms.SimpleActionBar;
import de.alphahelix.alphaapi.utils.Util;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.enums.Sounds;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import de.alphahelix.uhc.util.PlayerUtil;
import de.alphahelix.uhc.util.ScoreboardUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RestartTimer extends AbstractTimer {

    @Override
    public void startTimer() {
        if (!GState.isState(GState.END)) return;
        if (isRunning()) return;

        resetTime();
        UHCRegister.getGraceTimer().stopTimer();
        UHCRegister.getWarmUpTimer().stopTimer();
        UHCRegister.getDeathmatchTimer().stopTimer();
        UHCRegister.getStartDeathMatchTimer().stopTimer();

        setSecondTimer(() -> {
            if (getSeconds() > 0) {
                setSeconds(getSeconds() - 1);

                if (PlayerUtil.getAll().size() > 0) {
                    for (Player p : Util.makePlayerArray(PlayerUtil.getAll())) {
                        ScoreboardUtil.updateTime(p);

                        if (getSeconds() % 10 == 0 && getSeconds() > 10 && getSeconds() != 0) {
                            p.sendMessage(UHC.getPrefix() + UHCFileRegister.getMessageFile().getTimeLeftInfo(getSeconds(), UHCFileRegister.getUnitFile().getSeconds()));
                            SimpleActionBar.send(p, UHC.getPrefix() + UHCFileRegister.getMessageFile().getTimeLeftInfo(getSeconds(), UHCFileRegister.getUnitFile().getSeconds()));
                            p.playSound(p.getLocation(), Sounds.NOTE_BASS.bukkitSound(), 1F, 0F);
                            continue;
                        }

                        if (getSeconds() < 10 && getSeconds() != 0) {
                            p.sendMessage(UHC.getPrefix() + UHCFileRegister.getMessageFile().getTimeLeftInfo(getSeconds(), UHCFileRegister.getUnitFile().getSeconds()));

                            SimpleActionBar.send(p, UHC.getPrefix() + UHCFileRegister.getMessageFile().getTimeLeftInfo(getSeconds(), UHCFileRegister.getUnitFile().getSeconds()));
                            p.playSound(p.getLocation(), Sounds.NOTE_BASS.bukkitSound(), 1F, 0F);
                        }

                        if (getSeconds() == 0) {
                            p.sendMessage(UHC.getPrefix()
                                    + UHCFileRegister.getMessageFile().getEnd());

                            SimpleActionBar.send(p, UHC.getPrefix()
                                    + UHCFileRegister.getMessageFile().getEnd());
                        }
                    }

                    if (getSeconds() == 0) {
                        stopTimer();

                        Bukkit.reload();
                    }
                }
            }
        });
    }
}
