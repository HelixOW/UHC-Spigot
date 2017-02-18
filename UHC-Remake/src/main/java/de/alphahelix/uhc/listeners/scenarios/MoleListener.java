package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MoleListener extends SimpleListener {

    public MoleListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!scenarioCheck(Scenarios.MOLE))
            return;

        for (Player p : makeArray(UHCRegister.getPlayerUtil().getSurvivors())) {
            p.getInventory().addItem(new ItemStack(Material.IRON_SPADE));
            p.getInventory().addItem(new ItemStack(Material.IRON_PICKAXE));
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 2));
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.isCancelled())
            return;
        if (!scenarioCheck(Scenarios.MOLE))
            return;

        if (e.getTo().getBlockY() < 40) {
            if (e.getPlayer().hasPotionEffect(PotionEffectType.BLINDNESS))
                e.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
        }
    }
}
