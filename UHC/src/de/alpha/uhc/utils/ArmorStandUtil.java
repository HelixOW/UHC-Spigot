package de.alpha.uhc.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import de.alpha.uhc.Core;
import de.alpha.uhc.Registery;
import de.popokaka.alphalibary.item.ItemBuilder;
import de.popokaka.alphalibary.item.data.SkullData;

public class ArmorStandUtil {
	
	private Core pl;
	private Registery r;
	
	public ArmorStandUtil(Core c) {
		this.pl = c;
		this.r = pl.getRegistery();
	}

    public void spawn(Location l, String name) {
        if (!(r.getATeam().getTeamNames().contains(name))) {
            String a = r.getATeam().getNoExist().replace("[team]", name);
            String b = r.getATeam().getAllTeams().replace("[teams]", "" + name);
            Bukkit.getConsoleSender().sendMessage(pl.getPrefix() + a + "\n" + b);
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

        a.setColor(r.getATeam().getTeamItemColor(name));
        b.setColor(r.getATeam().getTeamItemColor(name));
        c.setColor(r.getATeam().getTeamItemColor(name));

        chest.setItemMeta(a);
        pants.setItemMeta(b);
        boots.setItemMeta(c);

        as.setHelmet(head);
        as.setChestplate(chest);
        as.setLeggings(pants);
        as.setBoots(boots);

        as.setBasePlate(false);
        as.setArms(true);
        as.setCustomName(r.getATeam().getTeamColor(name) + name);
        as.setVisible(true);
        as.setGravity(false);
        as.setCustomNameVisible(true);
        
        r.getArmorstandFile().addArmorStand(l);
    }
    
    public  void removeArmorStand(Location l) {
    	for(Entity e : l.getWorld().getEntitiesByClass(ArmorStand.class)) {
    		if(e.getLocation().getBlockX() == l.getBlockX() && e.getLocation().getBlockY() == l.getBlockY() && e.getLocation().getBlockZ() == l.getBlockZ()) {
    			e.remove();
    		}
    	}
    }
}
