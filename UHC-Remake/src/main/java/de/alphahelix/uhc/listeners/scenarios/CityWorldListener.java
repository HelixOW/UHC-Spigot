package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class CityWorldListener extends SimpleListener {

    public CityWorldListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!scenarioCheck(Scenarios.CITYWORLD))
            return;

        for (Player p : makeArray(UHCRegister.getPlayerUtil().getSurvivors())) {
            for (int slot = 0; slot < 36 + 9; slot++) {
                p.getInventory().setItem(slot, new ItemStack(getRandomMaterial()));
            }
        }
    }

    private Material getRandomMaterial() {
        int index = new Random().nextInt(Material.values().length);
        return Material.values()[index];
    }
}
