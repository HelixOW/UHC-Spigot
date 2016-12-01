package de.alphahelix.uhc.instances;

import de.alphahelix.uhc.GState;
import de.alphahelix.uhc.Registery;
import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public abstract class SimpleListener implements Listener {

    private UHC uhc;
    private Registery register;
    private Logger log;

    public SimpleListener(UHC uhc) {
        setUhc(uhc);
        setRegister(getUhc().getRegister());
        getRegister().getListeners().add(this);
        setLog(getUhc().getLog());
    }

    public UHC getUhc() {
        return uhc;
    }

    private void setUhc(UHC uhc) {
        this.uhc = uhc;
    }

    public Registery getRegister() {
        return register;
    }

    private void setRegister(Registery register) {
        this.register = register;
    }

    public Logger getLog() {
        return log;
    }

    private void setLog(Logger log) {
        this.log = log;
    }

    public boolean scenarioCheck(Scenarios s) {
        if (!(GState.isState(GState.LOBBY) || GState.isState(GState.END))) {
            return getRegister().getScenarioFile().isEnabled(Scenarios.getRawScenarioName(s)) && Scenarios.isScenario(s);
        }
        return false;
    }

    public <T> void cooldown(int length, final T key, final List<T> cooldownList) {
        cooldownList.add(key);
        new BukkitRunnable() {
            public void run() {
                cooldownList.remove(key);
            }
        }.runTaskLaterAsynchronously(getUhc(), length);
    }

    public Player[] makeArray(List<String> pNames) {
        List<Player> players = new LinkedList<>();
        for (String s : pNames) {
            if (Bukkit.getPlayerExact(s) == null)
                continue;
            players.add(Bukkit.getPlayerExact(s));
        }
        return players.toArray(new Player[players.size()]);
    }

    @SuppressWarnings("unchecked")
    public <T> T[] makeArray(T... types) {
        return types;
    }
}
