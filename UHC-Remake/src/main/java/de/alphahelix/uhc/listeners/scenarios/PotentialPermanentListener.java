package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.alphaapi.utils.Util;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.enums.Sounds;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.util.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.InvocationTargetException;

public class PotentialPermanentListener extends SimpleListener {

    @EventHandler
    public void onend(LobbyEndEvent e) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.POTENTIAL_PERMANENT))
            return;

        PotionEffect effect = new PotionEffect(PotionEffectType.ABSORPTION, Short.MAX_VALUE, 4);

        for (Player p : Util.makePlayerArray(PlayerUtil.getSurvivors())) {
            p.addPotionEffect(effect);
            p.setMaxHealth(20.0);
        }
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        if (event.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.POTENTIAL_PERMANENT))
            return;

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null)
            return;

        if (item.getType() == Material.MILK_BUCKET) {
            event.setItem(new ItemStack(Material.AIR));
            event.setCancelled(true);
            return;
        }

        if (item.getType() != Material.GOLDEN_APPLE)
            return;

        Class<?> handle = null;
        float absHearts = 0;
        try {
            handle = player.getClass().getMethod("getHandle").getReturnType();
            absHearts = (float) handle.getMethod("getAbsorptionHearts").invoke(null);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
        player.getWorld().playSound(player.getLocation(), Sounds.BURP.bukkitSound(), 1, 1);
        player.setSaturation(player.getSaturation() + 9.6f);
        player.setFoodLevel(player.getFoodLevel() + 4);
        event.setCancelled(true);

        item.setAmount(1);
        player.getInventory().removeItem(item);

        if (absHearts != 0) {
            float toTake = Math.min(4, absHearts);

            player.setMaxHealth(player.getMaxHealth() + toTake);
            try {
                handle.getMethod("setAbsorptionHearts", float.class).invoke(handle, absHearts - toTake);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e1) {
                e1.printStackTrace();
            }
        }
    }

}
