package de.alpha.uhc.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.alpha.uhc.Core;

public class CustomDeathListener implements Listener {
	
	public static String blockExplode;
	public static String contact;
	public static String drown;
	public static String pvp;
	public static String entityExplode;
	public static String fall;
	public static String anvil;
	public static String fire;
	public static String lava;
	public static String light;
	public static String potion;
	public static String arrow;
	public static String hunger;
	public static String burried;
	public static String kill;
	public static String thorns;
	public static String voidd;
	public static String wither;
	
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		
		Player p = e.getEntity();
		DamageCause cause = p.getLastDamageCause().getCause();
		
		if(cause.equals(DamageCause.BLOCK_EXPLOSION)) {
			String a = blockExplode.replace("[player]", p.getDisplayName());
			e.setDeathMessage(Core.getPrefix() + a);
		}
		if(cause.equals(DamageCause.CONTACT)) {
			String a = contact.replace("[player]", p.getDisplayName());
			e.setDeathMessage(Core.getPrefix() + a);
		}
		if(cause.equals(DamageCause.DROWNING)) {
			String a = drown.replace("[player]", p.getDisplayName());
			e.setDeathMessage(Core.getPrefix() + a);
		}
		if(cause.equals(DamageCause.ENTITY_ATTACK)) {
			if(p.getKiller() instanceof Player) {
				String a = pvp.replace("[player]", p.getDisplayName()).replace("[killer]", p.getKiller().getDisplayName());
				e.setDeathMessage(Core.getPrefix() + a);
			} else {
				String a = pvp.replace("[player]", p.getDisplayName()).replace("[killer]", "a Mob");
				e.setDeathMessage(Core.getPrefix() + a);
			}
		}
		if(cause.equals(DamageCause.ENTITY_EXPLOSION)) {
			String a = entityExplode.replace("[player]", p.getDisplayName());
			e.setDeathMessage(Core.getPrefix() + a);
		}
		if(cause.equals(DamageCause.FALL)) {
			String a = fall.replace("[player]", p.getDisplayName());
			e.setDeathMessage(Core.getPrefix() + a);
		}
		if(cause.equals(DamageCause.FALLING_BLOCK)) {
			String a = anvil.replace("[player]", p.getDisplayName());
			e.setDeathMessage(Core.getPrefix() + a);
		}
		if(cause.equals(DamageCause.FIRE)) {
			String a = fire.replace("[player]", p.getDisplayName());
			e.setDeathMessage(Core.getPrefix() + a);
		}
		if(cause.equals(DamageCause.FIRE_TICK)) {
			String a = fire.replace("[player]", p.getDisplayName());
			e.setDeathMessage(Core.getPrefix() + a);
		}
		if(cause.equals(DamageCause.LAVA)) {
			String a = lava.replace("[player]", p.getDisplayName());
			e.setDeathMessage(Core.getPrefix() + a);
		}
		if(cause.equals(DamageCause.LIGHTNING)) {
			String a = light.replace("[player]", p.getDisplayName());
			e.setDeathMessage(Core.getPrefix() + a);
		}
		if(cause.equals(DamageCause.MAGIC)) {
			String a = potion.replace("[player]", p.getDisplayName());
			e.setDeathMessage(Core.getPrefix() + a);
		}
		if(cause.equals(DamageCause.POISON)) {
			String a = potion.replace("[player]", p.getDisplayName());
			e.setDeathMessage(Core.getPrefix() + a);
		}
		if(cause.equals(DamageCause.PROJECTILE)) {
			if(p.getKiller() instanceof Player) {
				String a = arrow.replace("[player]", p.getDisplayName()).replace("[killer]", p.getKiller().getDisplayName());
				e.setDeathMessage(Core.getPrefix() + a);
			} else {
				String a = arrow.replace("[player]", p.getDisplayName()).replace("[killer]", "a Mob");
				e.setDeathMessage(Core.getPrefix() + a);
			}
		}
		if(cause.equals(DamageCause.STARVATION)) {
			String a = hunger.replace("[player]", p.getDisplayName());
			e.setDeathMessage(Core.getPrefix() + a);
		}
		if(cause.equals(DamageCause.SUFFOCATION)) {
			String a = burried.replace("[player]", p.getDisplayName());
			e.setDeathMessage(Core.getPrefix() + a);
		}
		if(cause.equals(DamageCause.SUICIDE)) {
			String a = kill.replace("[player]", p.getDisplayName());
			e.setDeathMessage(Core.getPrefix() + a);
		}
		if(cause.equals(DamageCause.THORNS)) {
			String a = thorns.replace("[player]", p.getDisplayName());
			e.setDeathMessage(Core.getPrefix() + a);
		}
		if(cause.equals(DamageCause.VOID)) {
			String a = voidd.replace("[player]", p.getDisplayName());
			e.setDeathMessage(Core.getPrefix() + a);
		}
		if(cause.equals(DamageCause.WITHER)) {
			String a = wither.replace("[player]", p.getDisplayName());
			e.setDeathMessage(Core.getPrefix() + a);
		}
	}
}
