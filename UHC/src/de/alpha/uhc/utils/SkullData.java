package de.alpha.uhc.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullData extends ItemData{
	
	public SkullData(String name) {
		ownerName = name;
	}
	
	String ownerName = null;

	public void applyOn(ItemStack applyOn) throws WrongDataException{
		try {
			if(!(applyOn.getType() == Material.SKULL_ITEM)) {
				return;
			}
			
			applyOn.setDurability((short) 3);
			
			SkullMeta skullMeta = (SkullMeta) applyOn.getItemMeta();
			skullMeta.setOwner(ownerName);
			applyOn.setItemMeta(skullMeta);
			
		} catch (Exception e) {
			try {
				throw new WrongDataException(this);
			} catch (WrongDataException e1) {
				
			}
		}
	}
}