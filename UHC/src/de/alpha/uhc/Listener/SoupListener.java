package de.alpha.uhc.Listener;

import de.alpha.uhc.GState;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SoupListener implements Listener {

    private static double boost;

    public static void setBoost(double a) {
        boost = a;
    }

    @EventHandler
    public void onSoup(PlayerInteractEvent e) {

        if (GState.isState(GState.INGAME) || GState.isState(GState.DEATHMATCH) || GState.isState(GState.PREDEATHMATCH)) {

            Player p = e.getPlayer();

            if (p.getInventory().getItemInMainHand().getType().equals(Material.MUSHROOM_SOUP)) {//TODO: multi

                if (e.getAction().equals(Action.RIGHT_CLICK_AIR)
                        || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

                    if (p.getHealth() == 20) {
                        return;
                    }

                    p.getInventory().getItemInMainHand().setType(Material.BOWL);//TODO: multi
                    if (p.getHealth() <= 19 - boost) {
                        p.setHealth(p.getHealth() + boost);
                    } else {
                        p.setHealth(20);
                    }

                }
            }

        }

    }

}
