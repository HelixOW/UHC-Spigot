package de.alpha.uhc.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.alpha.uhc.Core;

public class CustomDeathListener implements Listener {
	
	private static String blockExplode;
	private static String contact;
	private static String drown;
	private static String pvp;
	private static String entityExplode;
	private static String fall;
	private static String anvil;
	private static String fire;
	private static String lava;
	private static String light;
	private static String potion;
	private static String arrow;
	private static String hunger;
	private static String burried;
	private static String kill;
	private static String thorns;
	private static String voidd;
	private static String wither;
	
	
	public static void setBlockExplode(String a) {
		blockExplode = a;
	}




	public static void setContact(String a) {
		contact = a;
	}




	public static void setDrown(String a) {
		drown = a;
	}




	public static void setPvp(String a) {
		pvp = a;
	}




	public static void setEntityExplode(String a) {
		entityExplode = a;
	}




	public static void setFall(String a) {
		fall = a;
	}




	public static void setAnvil(String a) {
		anvil = a;
	}




	public static void setFire(String a) {
		fire = a;
	}




	public static void setLava(String a) {
		lava = a;
	}




	public static void setLight(String a) {
		light = a;
	}




	public static void setPotion(String a) {
		potion = a;
	}




	public static void setArrow(String a) {
		arrow = a;
	}




	public static void setHunger(String a) {
		hunger = a;
	}




	public static void setBurried(String a) {
		burried = a;
	}




	public static void setKill(String a) {
		kill = a;
	}




	public static void setThorns(String a) {
		thorns = a;
	}




	public static void setVoidd(String a) {
		voidd = a;
	}




	public static void setWither(String a) {
		wither = a;
	}




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
