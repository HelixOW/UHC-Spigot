package de.alpha.uhc.files;

import de.alpha.uhc.Core;
import de.alpha.uhc.Listener.CustomDeathListener;
import de.popokaka.alphalibary.file.SimpleFile;

public class DeathMessageFile {
	
	private Core pl;
	
	public DeathMessageFile(Core c) {
		this.pl = c;
	}

    private  final SimpleFile file = getDeathFile();

    private  SimpleFile getDeathFile() {
        return new SimpleFile("plugins/UHC", "deathmessages.yml");
    }

    public  void addDeathMessages() {
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

    public  void loadDeathMessages() {
        CustomDeathListener.setBlockExplode(file.getColorString("Block Explosion"));
        CustomDeathListener.setContact(file.getColorString("Contact with Cactus"));
        CustomDeathListener.setDrown(file.getColorString("Drowning"));
        CustomDeathListener.setPvp(file.getColorString("PvP"));
        CustomDeathListener.setEntityExplode(file.getColorString("Entity Explosion"));
        CustomDeathListener.setFall(file.getColorString("Fall"));
        CustomDeathListener.setAnvil(file.getColorString("Falling Block"));
        CustomDeathListener.setFire(file.getColorString("Fire"));
        CustomDeathListener.setLava(file.getColorString("Lava"));
        CustomDeathListener.setLight(file.getColorString("Lava"));
        CustomDeathListener.setPotion(file.getColorString("Potion"));
        CustomDeathListener.setArrow(file.getColorString("Arrow"));
        CustomDeathListener.setHunger(file.getColorString("Starvation"));
        CustomDeathListener.setBurried(file.getColorString("Suffocation"));
        CustomDeathListener.setKill(file.getColorString("Suicide"));
        CustomDeathListener.setThorns(file.getColorString("Thorns"));
        CustomDeathListener.setVoidd(file.getColorString("Void"));
        CustomDeathListener.setWither(file.getColorString("Wither"));
    }

}
