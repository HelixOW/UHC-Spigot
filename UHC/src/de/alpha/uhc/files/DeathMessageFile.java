package de.alpha.uhc.files;

import de.alpha.uhc.Listener.CustomDeathListener;
import de.popokaka.alphalibary.file.SimpleFile;

public class DeathMessageFile {
	
	public static SimpleFile getDeathFile() {
		return new SimpleFile("plugins/UHC", "deathmessages.yml");
	}
	
	private static SimpleFile file = getDeathFile();
	
	public static void addDeathMessages() {
		file.setDefault("Block Explosion", "[player] &7was to near to a block explode and died.");
		file.setDefault("Contact with Cactus", "[player] &7wanted to kuddle with a cactus. Ouch.");
		file.setDefault("Drowning", "[player] &7thought he is a fish and ran out of air.");
		file.setDefault("PvP", "[player] &7 was slained by &c[killer]&7.");
		file.setDefault("Entity Explosion", "[player] &7standed to near to a creeper and died.");
		file.setDefault("Fall", "[player] &7tought he could fly.");
		file.setDefault("Falling Block", "[player] &7was hit by an Anvil.");
		file.setDefault("Fire", "[player] &7burned by fire.");
		file.setDefault("Lava", "[player] &7burned by lava.");
		file.setDefault("Lightning", "[player] &7ran into a lightning bolt and died.");
		file.setDefault("Potion", "[player] &7was hit by a potion and died.");
		file.setDefault("Arrow", "[player] &7was shot by &c[killer].");
		file.setDefault("Starvation", "[player] &7ran out of food and starved.");
		file.setDefault("Suffocation", "[player] &7was burried alive.");
		file.setDefault("Suicide", "[player] &7killed himself.");
		file.setDefault("Thorns", "[player] &7wasn't knowing the others enchantment well enough.");
		file.setDefault("Void", "[player] &7falled into the void.");
		file.setDefault("Wither", "[player] &7was to weak for the Withereffect.");
	}
	
	public static void loadDeathMessages() {
		CustomDeathListener.blockExplode = file.getColorString("Block Explosion");
		CustomDeathListener.contact = file.getColorString("Contact with Cactus");
		CustomDeathListener.drown = file.getColorString("Drowning");
		CustomDeathListener.pvp = file.getColorString("PvP");
		CustomDeathListener.entityExplode = file.getColorString("Entity Explosion");
		CustomDeathListener.fall = file.getColorString("Fall");
		CustomDeathListener.anvil = file.getColorString("Falling Block");
		CustomDeathListener.fire = file.getColorString("Fire");
		CustomDeathListener.lava = file.getColorString("Lava");
		CustomDeathListener.light = file.getColorString("Lightning");
		CustomDeathListener.potion = file.getColorString("Potion");
		CustomDeathListener.arrow = file.getColorString("Arrow");
		CustomDeathListener.hunger = file.getColorString("Starvation");
		CustomDeathListener.burried = file.getColorString("Suffocation");
		CustomDeathListener.kill = file.getColorString("Suicide");
		CustomDeathListener.thorns = file.getColorString("Thorns");
		CustomDeathListener.voidd = file.getColorString("Void");
		CustomDeathListener.wither = file.getColorString("Wither");
	}

}
