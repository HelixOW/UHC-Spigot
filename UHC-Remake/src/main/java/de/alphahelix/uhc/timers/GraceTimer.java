package de.alphahelix.uhc.timers;

import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.alphalibary.nms.SimpleActionBar;
import de.alphahelix.alphalibary.nms.SimpleTitle;
import de.alphahelix.alphalibary.utils.Util;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.enums.Sounds;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import de.alphahelix.uhc.util.PlayerUtil;
import de.alphahelix.uhc.util.ScoreboardUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class GraceTimer extends AbstractTimer {

    @Override
    public void startTimer() {
        if (!GState.isState(GState.PERIOD_OF_PEACE)) return;
        if (isRunning()) return;

        stopTimer();

        setSecondTimer(() -> {
            if (getSeconds() > 0) {
                setSeconds(getSeconds() - 1);

                for (Player p : Util.makePlayerArray(PlayerUtil.getAll())) {
                    String time = (getMinTime() >= 1 ? UHCFileRegister.getMessageFile().getTimeLeftInfo(Util.round(getMinTime(), 1), UHCFileRegister.getUnitFile().getMinutes()) : UHCFileRegister.getMessageFile().getTimeLeftInfo(Util.round(getSeconds(), 1), UHCFileRegister.getUnitFile().getSeconds()));


                    ScoreboardUtil.updateTime(p);

                    if (getMinTime() % 5 == 0 && getSeconds() > 10 && getSeconds() != 0) {
                        p.sendMessage(UHC.getPrefix() + time);
                        SimpleTitle.sendTitle(p, " ", time, 1, 2, 1);
                        p.playSound(p.getLocation(), Sounds.NOTE_BASS.bukkitSound(), 1F, 0F);
                    }

                    if (getSeconds() < 10 && getSeconds() != 0) {
                        SimpleActionBar.send(p, UHC.getPrefix() + UHCFileRegister.getMessageFile().getTimeLeftInfo(getSeconds(), UHCFileRegister.getUnitFile().getSeconds()));
                        p.playSound(p.getLocation(), Sounds.NOTE_BASS.bukkitSound(), 1F, 0F);
                    }

                    if (getSeconds() == 0) {
                        p.sendMessage(UHC.getPrefix() + UHCFileRegister.getMessageFile().getEnd());

                        SimpleActionBar.send(p, UHC.getPrefix() + UHCFileRegister.getMessageFile().getEnd());

                        for (Player other : Util.makePlayerArray(PlayerUtil.getAll())) {
                            p.hidePlayer(other);
                        }

                        for (Player alive : Util.makePlayerArray(PlayerUtil.getSurvivors())) {
                            p.showPlayer(alive);
                        }

                        if (UHC.isTracker())
                            p.getInventory().addItem(new ItemBuilder(Material.COMPASS).setName(UHC.getTrackerName()).build());
                    }
                }

                if (getSeconds() == 0) {
                    stopTimer();

                    GState.setCurrentState(GState.WARMUP);

                    UHCRegister.getWarmUpTimer().startTimer();
                }
            }
        });
    }
}
