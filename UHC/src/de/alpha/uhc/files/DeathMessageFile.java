package de.alpha.uhc.files;

import de.alpha.uhc.Core;
import de.alpha.uhc.Registery;
import de.popokaka.alphalibary.file.SimpleFile;

public class DeathMessageFile {
	
	private Core pl;
	private Registery r;
	
	public DeathMessageFile(Core c) {
		this.pl = c;
		this.r = pl.getRegistery();
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
        r.getCustomDeathListener().setBlockExplode(file.getColorString("Block Explosion"));
        r.getCustomDeathListener().setContact(file.getColorString("Contact with Cactus"));
        r.getCustomDeathListener().setDrown(file.getColorString("Drowning"));
        r.getCustomDeathListener().setPvp(file.getColorString("PvP"));
        r.getCustomDeathListener().setEntityExplode(file.getColorString("Entity Explosion"));
        r.getCustomDeathListener().setFall(file.getColorString("Fall"));
        r.getCustomDeathListener().setAnvil(file.getColorString("Falling Block"));
        r.getCustomDeathListener().setFire(file.getColorString("Fire"));
        r.getCustomDeathListener().setLava(file.getColorString("Lava"));
        r.getCustomDeathListener().setLight(file.getColorString("Lava"));
        r.getCustomDeathListener().setPotion(file.getColorString("Potion"));
        r.getCustomDeathListener().setArrow(file.getColorString("Arrow"));
        r.getCustomDeathListener().setHunger(file.getColorString("Starvation"));
        r.getCustomDeathListener().setBurried(file.getColorString("Suffocation"));
        r.getCustomDeathListener().setKill(file.getColorString("Suicide"));
        r.getCustomDeathListener().setThorns(file.getColorString("Thorns"));
        r.getCustomDeathListener().setVoidd(file.getColorString("Void"));
        r.getCustomDeathListener().setWither(file.getColorString("Wither"));
    }

}
