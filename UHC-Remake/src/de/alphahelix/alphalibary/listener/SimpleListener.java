package de.alphahelix.alphalibary.listener;

import de.alphahelix.alphalibary.AlphaPlugin;
import de.alphahelix.alphalibary.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class SimpleListener<P extends AlphaPlugin, R> extends Util<P, R> implements Listener {

    public SimpleListener(P plugin, R register) {
        super(plugin, register);
        Bukkit.getPluginManager().registerEvents(this, getPluginInstance());
    }
}
