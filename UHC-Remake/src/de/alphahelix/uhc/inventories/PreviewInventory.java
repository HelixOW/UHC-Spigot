package de.alphahelix.uhc.inventories;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.Kit;
import de.alphahelix.uhc.instances.Util;
import de.popokaka.alphalibary.inventorys.SimpleMovingInventory;

public class PreviewInventory extends Util{
	
	public PreviewInventory(UHC uhc) {
		super(uhc);
	}

	public void fillInventory(Player p, Kit k) {
		ArrayList<ItemStack> stacks = new ArrayList<>();
		for(ItemStack is : k.getInventory().getContents()) {
			if(is == null) continue;
			stacks.add(is);
		}
		new SimpleMovingInventory(getUhc(), stacks, getRegister().getKitsFile().getColorString("Preview GUI.Name").replace("[kit]", k.getName().replace("_", " ")), p, 9*5);
	}
}
