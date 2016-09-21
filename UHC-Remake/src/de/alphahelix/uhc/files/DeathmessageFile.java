package de.alphahelix.uhc.files;

import java.util.Random;

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;

public class DeathmessageFile extends EasyFile {

	public DeathmessageFile(UHC uhc) {
		super("deathmessage.uhc", uhc);
	}

	@Override
	public void addValues() {
		setDefault("[entity] is a mob", "a mob");
		setDefault("Player.died.reason.tnt", toList("&7[player] stand to near to &ctnt&7!", "&7[player] blew up!"));

		setDefault("Player.died.reason.cactus",
				toList("&7[player] stand to near to a &2cactus&7!", "&7[player] wanted to a hug a &2cactus!"));

		setDefault("Player.died.reason.undefined",
				toList("&7[player] was killed by command!", "&7[player] wanted to hack!"));

		setDefault("Player.died.reason.drowned",
				toList("&7[player] thought he is a fish!", "&7[player] was too long underwater"));

		setDefault("Player.died.reason.entity",
				toList("&7[player] lost the fight with [entity]!", "&7[player] got murded by [entity]!"));

		setDefault("Player.died.reason.fall",
				toList("&7[player] thought he could fly!", "&7[player] thought he is a bird!"));

		setDefault("Player.died.reason.fire", toList("&7[player] burnt away!", "&7[player] is now ash!"));

		setDefault("Player.died.reason.poison",
				toList("&7[player] was poisoned!", "&7[player] was killed with magic!"));

		setDefault("Player.died.reason.starved", toList("&7[player] was too hungry!", "&7[player] starved!"));

		setDefault("Player.died.reason.suffocated",
				toList("&7[player] stucked inside a block!", "&7[player] can't receive help inside a block!"));

		setDefault("Player.died.reason.thorns",
				toList("&7[player] messed with the wrong person!", "&7[player] killed himself!"));

		setDefault("Player.died.reason.wither",
				toList("&7[player] was to weak for wither!", "&7[player] spawned a wither(?!) and can't defeat him!"));
	}

	public String getMessage(DamageCause dg) {
		if (dg == null)
			return "";
		if (dg.equals(DamageCause.BLOCK_EXPLOSION)) {
			return getUhc().getPrefix() + getStringList("Player.died.reason.tnt")
					.get(new Random(getStringList("Player.died.reason.tnt").size())
							.nextInt(getStringList("Player.died.reason.tnt").size()))
					.replace("&", "§");
		}

		else if (dg.equals(DamageCause.CONTACT)) {
			return getUhc().getPrefix() + getStringList("Player.died.reason.cactus")
					.get(new Random(getStringList("Player.died.reason.cactus").size())
							.nextInt(getStringList("Player.died.reason.cactus").size()))
					.replace("&", "§");
		}

		else if (dg.equals(DamageCause.CUSTOM)) {
			return getUhc().getPrefix() + getStringList("Player.died.reason.undefined")
					.get(new Random(getStringList("Player.died.reason.undefined").size())
							.nextInt(getStringList("Player.died.reason.undefined").size()))
					.replace("&", "§");
		}

		else if (dg.equals(DamageCause.DROWNING)) {
			return getUhc().getPrefix() + getStringList("Player.died.reason.drowned")
					.get(new Random(getStringList("Player.died.reason.undefined").size())
							.nextInt(getStringList("Player.died.reason.undefined").size()))
					.replace("&", "§");
		}

		else if (dg.equals(DamageCause.ENTITY_ATTACK) || dg.equals(DamageCause.PROJECTILE)) {
			return getUhc().getPrefix() + getStringList("Player.died.reason.entity")
					.get(new Random(getStringList("Player.died.reason.entity").size())
							.nextInt(getStringList("Player.died.reason.entity").size()))
					.replace("&", "§");
		}

		else if (dg.equals(DamageCause.FALL)) {
			return getUhc().getPrefix() + getStringList("Player.died.reason.fall")
					.get(new Random(getStringList("Player.died.reason.fall").size())
							.nextInt(getStringList("Player.died.reason.fall").size()))
					.replace("&", "§");
		}

		else if (dg.equals(DamageCause.FIRE) || dg.equals(DamageCause.FIRE_TICK) || dg.equals(DamageCause.LAVA)) {
			return getUhc().getPrefix() + getStringList("Player.died.reason.fire")
					.get(new Random(getStringList("Player.died.reason.fire").size())
							.nextInt(getStringList("Player.died.reason.fire").size()))
					.replace("&", "§");
		}

		else if (dg.equals(DamageCause.FIRE)) {
			return getUhc().getPrefix() + getStringList("Player.died.reason.poison")
					.get(new Random(getStringList("Player.died.reason.poison").size())
							.nextInt(getStringList("Player.died.reason.poison").size()))
					.replace("&", "§");
		}

		else if (dg.equals(DamageCause.STARVATION)) {
			return getUhc().getPrefix() + getStringList("Player.died.reason.starved")
					.get(new Random(getStringList("Player.died.reason.starved").size())
							.nextInt(getStringList("Player.died.reason.starved").size()))
					.replace("&", "§");
		}

		else if (dg.equals(DamageCause.SUFFOCATION)) {
			return getUhc().getPrefix() + getStringList("Player.died.reason.suffocated")
					.get(new Random(getStringList("Player.died.reason.suffocated").size())
							.nextInt(getStringList("Player.died.reason.suffocated").size()))
					.replace("&", "§");
		}

		else if (dg.equals(DamageCause.THORNS)) {
			return getUhc().getPrefix() + getStringList("Player.died.reason.thorns")
					.get(new Random(getStringList("Player.died.reason.thorns").size())
							.nextInt(getStringList("Player.died.reason.thorns").size()))
					.replace("&", "§");
		}

		else if (dg.equals(DamageCause.WITHER)) {
			return getUhc().getPrefix() + getStringList("Player.died.reason.wither")
					.get(new Random(getStringList("Player.died.reason.wither").size())
							.nextInt(getStringList("Player.died.reason.wither").size()))
					.replace("&", "§");
		}

		return "";
	}
}
