package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.util.TeamManagerUtil;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class SharedHealthListener extends SimpleListener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.isCancelled()) return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.SHARED_HEALTH)) return;
        if (!(e.getEntity() instanceof Player)) return;

        Player p = (Player) e.getEntity();

        if (TeamManagerUtil.isInOneTeam(p) == null) return;
        e.setCancelled(true);
        int count = TeamManagerUtil.isInOneTeam(p).getPlayers().size();
        p.damage(e.getDamage() / count);
        for (OfflinePlayer teamMate : TeamManagerUtil.isInOneTeam(p).getPlayers()) {
            if (teamMate.isOnline())
                teamMate.getPlayer().damage(e.getDamage() / count);
        }
    }
}
