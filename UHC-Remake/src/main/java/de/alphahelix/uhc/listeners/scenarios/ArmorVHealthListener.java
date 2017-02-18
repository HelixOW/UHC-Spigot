package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.ArmorBar;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.events.armor.ArmorEquipEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class ArmorVHealthListener extends SimpleListener {

    public ArmorVHealthListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onEquip1(ArmorEquipEvent e) {
        Player p = e.getPlayer();

        if (!scenarioCheck(Scenarios.ARMOR_V_HEALTH))
            return;

        ArmorBar bar = ArmorBar.getArmorBarByMaterial(p.getInventory().getItemInHand().getType());

        if ((p.getMaxHealth() - bar.getPoints()) <= 0)
            return;

        if (p.getInventory().getBoots() == null) {
            p.setMaxHealth(p.getMaxHealth() - bar.getPoints());
            p.setHealthScale(p.getMaxHealth());
            p.setHealth(p.getMaxHealth());
        } else if (p.getInventory().getLeggings() == null) {
            p.setMaxHealth(p.getMaxHealth() - bar.getPoints());
            p.setHealthScale(p.getMaxHealth());
            p.setHealth(p.getMaxHealth());
        } else if (p.getInventory().getChestplate() == null) {
            p.setMaxHealth(p.getMaxHealth() - bar.getPoints());
            p.setHealthScale(p.getMaxHealth());
            p.setHealth(p.getMaxHealth());
        } else if (p.getInventory().getHelmet() == null) {
            p.setMaxHealth(p.getMaxHealth() - bar.getPoints());
            p.setHealthScale(p.getMaxHealth());
            p.setHealth(p.getMaxHealth());
        }
    }
}
