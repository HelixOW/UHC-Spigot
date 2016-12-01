package de.alphahelix.alphalibary;

import org.bukkit.plugin.java.JavaPlugin;

public class AlphaPlugin extends JavaPlugin {

	/*
	 * R stands for the Register Clazz where all Listeners, Commands and Files will be initialized.
	 */

    private AlphaPlugin plugin;
    private String prefix;

    @Override
    public void onEnable() {
        setPluginInstance(this);
    }

    /**
     * @return the plugin
     */
    public AlphaPlugin getPluginInstance() {
        return this.plugin;
    }

    /**
     * @param plugin the plugin to set
     */
    private void setPluginInstance(AlphaPlugin plugin) {
        this.plugin = plugin;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
