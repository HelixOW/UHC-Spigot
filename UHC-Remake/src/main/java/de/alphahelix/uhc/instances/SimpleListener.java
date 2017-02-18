package de.alphahelix.uhc.instances;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.register.UHCFileRegister;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public abstract class SimpleListener implements Listener {

    private UHC uhc;
    private Logger log;

    public SimpleListener(UHC uhc) {
        setUhc(uhc);
        Bukkit.getPluginManager().registerEvents(this, getUhc());
        setLog(getUhc().getLog());
    }

    public UHC getUhc() {
        return uhc;
    }

    private void setUhc(UHC uhc) {
        this.uhc = uhc;
    }

    public Logger getLog() {
        return log;
    }

    private void setLog(Logger log) {
        this.log = log;
    }

    public boolean scenarioCheck(Scenarios s) {
        return !(GState.isState(GState.LOBBY) || GState.isState(GState.END)) && UHCFileRegister.getScenarioFile().isEnabled(Scenarios.getRawScenarioName(s)) && Scenarios.isScenario(s);
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
