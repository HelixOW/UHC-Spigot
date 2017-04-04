package de.alphahelix.alphaapi;

import de.alphahelix.alphaapi.fakeapi.FakeAPI;
import de.alphahelix.alphaapi.utils.GameProfileBuilder;
import org.bukkit.plugin.java.JavaPlugin;

public class AlphaAPI extends JavaPlugin {

    private static AlphaAPI instance;
    private static GameProfileBuilder.GameProfileFile gameProfileFile;

    public static AlphaAPI getInstance() {
        return instance;
    }

    public static GameProfileBuilder.GameProfileFile getGameProfileFile() {
        return gameProfileFile;
    }

    @Override
    public void onLoad() {
        FakeAPI.load();
    }

    @Override
    public void onEnable() {
        instance = this;
        FakeAPI.enable();


        gameProfileFile = new GameProfileBuilder.GameProfileFile();
    }

    @Override
    public void onDisable() {
        FakeAPI.disable();
    }
}
