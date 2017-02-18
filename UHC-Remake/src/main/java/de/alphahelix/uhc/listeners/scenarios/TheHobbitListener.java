package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TheHobbitListener extends SimpleListener {

    public TheHobbitListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onEnd(LobbyEndEvent e) {
        if (!scenarioCheck(Scenarios.THE_HOBBIT))
            return;

        for (Player p : makeArray(UHCRegister.getPlayerUtil().getSurvivors())) {
            p.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET));
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.isCancelled())
            return;
        if (!scenarioCheck(Scenarios.THE_HOBBIT))
            return;
        if (!e.getAction().name().contains("RIGHT"))
            return;

        if (e.getPlayer().getInventory().getItemInHand().getType().equals(Material.GOLD_NUGGET)) {
            e.getPlayer().getInventory().setItemInHand(new ItemStack(Material.AIR));
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, (20 * 30), 1), true);
        }
    }
}
