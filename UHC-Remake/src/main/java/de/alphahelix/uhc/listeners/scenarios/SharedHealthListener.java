package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.instances.SimpleListener;
import de.alphahelix.uhc.register.UHCRegister;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class SharedHealthListener extends SimpleListener {

    public SharedHealthListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.isCancelled()) return;
        if (!scenarioCheck(Scenarios.SHARED_HEALTH)) return;
        if (!(e.getEntity() instanceof Player)) return;

        Player p = (Player) e.getEntity();

        if (UHCRegister.getTeamManagerUtil().isInOneTeam(p) == null) return;
        e.setCancelled(true);
        int count = UHCRegister.getTeamManagerUtil().isInOneTeam(p).getPlayers().size();
        p.damage(e.getDamage() / count);
        for (Player teamMate : UHCRegister.getTeamManagerUtil().isInOneTeam(p).getPlayers()) {
            teamMate.damage(e.getDamage() / count);
        }
    }
}
