package de.alphahelix.alphalibary.item.data;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;

public class SimpleFireworkEffect {

    private Type type = Type.BALL;
    private Color[] color = new Color[]{Color.WHITE};
    private boolean flicker = false;
    private boolean trail = false;
    private Color[] fades = new Color[]{Color.WHITE};

    public SimpleFireworkEffect(Type type) {
        this.type = type;
    }

    public SimpleFireworkEffect setColor(Color... color) {
        this.color = color;
        return this;
    }

    public SimpleFireworkEffect setFade(Color... fades) {
        this.fades = fades;
        return this;
    }

    public SimpleFireworkEffect setTrail(boolean trail) {
        this.trail = trail;
        return this;
    }

    public SimpleFireworkEffect setFlicker(boolean flicker) {
        this.flicker = flicker;
        return this;
    }

    public FireworkEffect build() {
        return FireworkEffect.builder().flicker(flicker).trail(trail).withColor(color).withFade(fades).with(type).build();
    }
}