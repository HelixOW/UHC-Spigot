package de.alphahelix.uhc.files;

import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.uhc.UHC;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import java.util.Random;

public class DeathmessageFile extends SimpleFile<UHC> {

    public DeathmessageFile(UHC uhc) {
        super("deathmessage.uhc", uhc);
    }

    public String getIsAMob() {
        return getColorString("[entity] is a mob");
    }

    @Override
    public void addValues() {
        setDefault("[entity] is a mob", "a mob");
        setArgumentList("Player.died.reason.tnt",
                "&7[player] stand to near to &ctnt&7!", "&7[player] blew up!");

        setArgumentList("Player.died.reason.cactus",
                "&7[player] stand to near to a &2cactus&7!", "&7[player] wanted to a hug a &2cactus!");

        setArgumentList("Player.died.reason.undefined",
                "&7[player] was killed by command!", "&7[player] wanted to hack!");

        setArgumentList("Player.died.reason.drowned",
                "&7[player] thought he is a fish!", "&7[player] was too long underwater");

        setArgumentList("Player.died.reason.entity",
                "&7[player] lost the fight with [entity]!", "&7[player] got murded by [entity]!");

        setArgumentList("Player.died.reason.fall",
                "&7[player] thought he could fly!", "&7[player] thought he is a bird!");

        setArgumentList("Player.died.reason.fire", "&7[player] burnt away!", "&7[player] is now ash!");

        setArgumentList("Player.died.reason.poison",
                "&7[player] was poisoned!", "&7[player] was killed with magic!");

        setArgumentList("Player.died.reason.starved", "&7[player] was too hungry!", "&7[player] starved!");

        setArgumentList("Player.died.reason.suffocated",
                "&7[player] stucked inside a block!", "&7[player] can't receive help inside a block!");

        setArgumentList("Player.died.reason.thorns",
                "&7[player] messed with the wrong person!", "&7[player] killed himself!");

        setArgumentList("Player.died.reason.wither",
                "&7[player] was to weak for wither!", "&7[player] spawned a wither(?!) and can't defeat him!");
    }

    public String getMessage(DamageCause dg) {
        if (dg == null)
            return getPluginInstance().getPrefix() + getStringList("Player.died.reason.undefined")
                    .get(new Random(getStringList("Player.died.reason.undefined").size())
                            .nextInt(getStringList("Player.died.reason.undefined").size()))
                    .replace("&", "§");
        if (dg.equals(DamageCause.BLOCK_EXPLOSION)) {
            return getPluginInstance().getPrefix() + getStringList("Player.died.reason.tnt")
                    .get(new Random(getStringList("Player.died.reason.tnt").size())
                            .nextInt(getStringList("Player.died.reason.tnt").size()))
                    .replace("&", "§");
        } else if (dg.equals(DamageCause.CONTACT)) {
            return getPluginInstance().getPrefix() + getStringList("Player.died.reason.cactus")
                    .get(new Random(getStringList("Player.died.reason.cactus").size())
                            .nextInt(getStringList("Player.died.reason.cactus").size()))
                    .replace("&", "§");
        } else if (dg.equals(DamageCause.CUSTOM)) {
            return getPluginInstance().getPrefix() + getStringList("Player.died.reason.undefined")
                    .get(new Random(getStringList("Player.died.reason.undefined").size())
                            .nextInt(getStringList("Player.died.reason.undefined").size()))
                    .replace("&", "§");
        } else if (dg.equals(DamageCause.DROWNING)) {
            return getPluginInstance().getPrefix() + getStringList("Player.died.reason.drowned")
                    .get(new Random(getStringList("Player.died.reason.undefined").size())
                            .nextInt(getStringList("Player.died.reason.undefined").size()))
                    .replace("&", "§");
        } else if (dg.equals(DamageCause.ENTITY_ATTACK) || dg.equals(DamageCause.PROJECTILE)) {
            return getPluginInstance().getPrefix() + getStringList("Player.died.reason.entity")
                    .get(new Random(getStringList("Player.died.reason.entity").size())
                            .nextInt(getStringList("Player.died.reason.entity").size()))
                    .replace("&", "§");
        } else if (dg.equals(DamageCause.FALL)) {
            return getPluginInstance().getPrefix() + getStringList("Player.died.reason.fall")
                    .get(new Random(getStringList("Player.died.reason.fall").size())
                            .nextInt(getStringList("Player.died.reason.fall").size()))
                    .replace("&", "§");
        } else if (dg.equals(DamageCause.FIRE) || dg.equals(DamageCause.FIRE_TICK) || dg.equals(DamageCause.LAVA)) {
            return getPluginInstance().getPrefix() + getStringList("Player.died.reason.fire")
                    .get(new Random(getStringList("Player.died.reason.fire").size())
                            .nextInt(getStringList("Player.died.reason.fire").size()))
                    .replace("&", "§");
        } else if (dg.equals(DamageCause.FIRE)) {
            return getPluginInstance().getPrefix() + getStringList("Player.died.reason.poison")
                    .get(new Random(getStringList("Player.died.reason.poison").size())
                            .nextInt(getStringList("Player.died.reason.poison").size()))
                    .replace("&", "§");
        } else if (dg.equals(DamageCause.STARVATION)) {
            return getPluginInstance().getPrefix() + getStringList("Player.died.reason.starved")
                    .get(new Random(getStringList("Player.died.reason.starved").size())
                            .nextInt(getStringList("Player.died.reason.starved").size()))
                    .replace("&", "§");
        } else if (dg.equals(DamageCause.SUFFOCATION)) {
            return getPluginInstance().getPrefix() + getStringList("Player.died.reason.suffocated")
                    .get(new Random(getStringList("Player.died.reason.suffocated").size())
                            .nextInt(getStringList("Player.died.reason.suffocated").size()))
                    .replace("&", "§");
        } else if (dg.equals(DamageCause.THORNS)) {
            return getPluginInstance().getPrefix() + getStringList("Player.died.reason.thorns")
                    .get(new Random(getStringList("Player.died.reason.thorns").size())
                            .nextInt(getStringList("Player.died.reason.thorns").size()))
                    .replace("&", "§");
        } else if (dg.equals(DamageCause.WITHER)) {
            return getPluginInstance().getPrefix() + getStringList("Player.died.reason.wither")
                    .get(new Random(getStringList("Player.died.reason.wither").size())
                            .nextInt(getStringList("Player.died.reason.wither").size()))
                    .replace("&", "§");
        }

        return getPluginInstance().getPrefix() + getStringList("Player.died.reason.undefined")
                .get(new Random(getStringList("Player.died.reason.undefined").size())
                        .nextInt(getStringList("Player.died.reason.undefined").size()))
                .replace("&", "§");
    }
}
