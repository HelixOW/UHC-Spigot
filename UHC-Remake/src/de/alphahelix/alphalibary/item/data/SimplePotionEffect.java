package de.alphahelix.alphalibary.item.data;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

class SimplePotionEffect {

    private int durationInSec = 0;
    private PotionEffectType potionType = PotionEffectType.SPEED;
    private int amplifier = 0;

    public SimplePotionEffect(int durationInSec, PotionEffectType type, int amplifier) {
        this.durationInSec = durationInSec;
        this.potionType = type;
        this.amplifier = amplifier;
    }

    public PotionEffect createEffect() {
        return new PotionEffect(this.potionType, this.durationInSec * 20, this.amplifier);
    }
}
