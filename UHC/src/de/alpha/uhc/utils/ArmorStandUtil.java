package de.alpha.uhc.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import de.alpha.uhc.Core;
import de.alpha.uhc.aclasses.ATeam;
import net.minetopix.library.main.item.ItemCreator;
import net.minetopix.library.main.item.data.SkullData;

public class ArmorStandUtil{
	
	public static void spawn(Location l, String name) {
		if(!(ATeam.teamNames.contains(name))) {
			String a = ATeam.noExist.replace("[team]", name);
			String b = ATeam.allTeams.replace("[teams]", ""+name);
			Bukkit.getConsoleSender().sendMessage(Core.getPrefix() + a + "\n" + b);
			return;
		}
		
		ArmorStand as = l.getWorld().spawn(l, ArmorStand.class);
		
		ItemStack head = new ItemCreator(Material.SKULL_ITEM).addItemData(new SkullData("AlphaHelix")).build();
		ItemStack chest = new ItemCreator(Material.LEATHER_CHESTPLATE).build();
		ItemStack pants = new ItemCreator(Material.LEATHER_LEGGINGS).build();
		ItemStack boots = new ItemCreator(Material.LEATHER_BOOTS).build();
		
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
