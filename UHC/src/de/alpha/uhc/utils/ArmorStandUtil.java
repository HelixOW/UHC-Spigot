package de.alpha.uhc.utils;

import de.alpha.uhc.Core;
import de.alpha.uhc.aclasses.ATeam;
import de.popokaka.alphalibary.item.ItemBuilder;
import de.popokaka.alphalibary.item.data.SkullData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ArmorStandUtil {

    public static void spawn(Location l, String name) {
        if (!(ATeam.getTeamNames().contains(name))) {
            String a = ATeam.getNoExist().replace("[team]", name);
            String b = ATeam.getAllTeams().replace("[teams]", "" + name);
            Bukkit.getConsoleSender().sendMessage(Core.getPrefix() + a + "\n" + b);
            return;
        }

        ArmorStand as = l.getWorld().spawn(l, ArmorStand.class);

        ItemStack head = new ItemBuilder(Material.SKULL_ITEM).addItemData(new SkullData("AlphaHelix")).build();
        ItemStack chest = new ItemBuilder(Material.LEATHER_CHESTPLATE).build();
        ItemStack pants = new ItemBuilder(Material.LEATHER_LEGGINGS).build();
        ItemStack boots = new ItemBuilder(Material.LEATHER_BOOTS).build();

        LeatherArmorMeta a = (LeatherArmorMeta) chest.getItemMeta();
        LeatherArmorMeta b = (LeatherArmorMeta) pants.getItemMeta();
        LeatherArmorMeta c = (LeatherArmorMeta) boots.getItemMeta();

        a.setColor(ATeam.getTeamItemColor(name));
        b.setColor(ATeam.getTeamItemColor(name));
        c.setColor(ATeam.getTeamItemColor(name));

        chest.setItemMeta(a);
        pants.setItemMeta(b);
        boots.setItemMeta(c);

        as.setHelmet(head);
        as.setChestplate(chest);
        as.setLeggings(pants);
        as.setBoots(boots);

        as.setBasePlate(false);
        as.setArms(true);
        as.setCustomName(ATeam.getTeamColor(name) + name);
        as.setVisible(true);
        as.setGravity(false);
        as.setCustomNameVisible(true);
    }
}
