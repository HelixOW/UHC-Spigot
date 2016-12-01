package de.alphahelix.alphalibary.utils;

import de.alphahelix.alphalibary.AlphaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.logging.Logger;

public class Util<P extends AlphaPlugin, R> {

    private P pl;
    private R register;

    public Util(P plugin, R register) {
        setPluginInstance(plugin);
        setRegister(register);
    }

    public P getPluginInstance() {
        return pl;
    }

    private void setPluginInstance(P pl) {
        this.pl = pl;
    }

    public Logger getLog() {
        return Bukkit.getLogger();
    }

    public R getRegister() {
        return register;
    }

    public void setRegister(R register) {
        this.register = register;
    }

    public double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public double calcSec(int milisec) {
        return (milisec * 60);
    }

    public double calcMin(int sec) {
        return (sec / (3600 / 60.0));
    }

    public double calcHours(int sec) {
        return sec / 3600;
    }

    public <T> void cooldown(int length, final T key, final List<T> cooldownList) {
        cooldownList.add(key);
        new BukkitRunnable() {
            public void run() {
                cooldownList.remove(key);
            }
        }.runTaskLaterAsynchronously(getPluginInstance(), length);
    }

    @SuppressWarnings("unchecked")
    public <T> T[] makeArray(T... types) {
        return types;
    }
}
