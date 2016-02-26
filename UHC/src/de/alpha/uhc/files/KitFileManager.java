package de.alpha.uhc.files;

import java.util.ArrayList;

import org.bukkit.inventory.Inventory;

import de.alpha.uhc.manager.KitManager;
import net.minetopix.library.main.file.SimpleFile;

public class KitFileManager {
	
	public SimpleFile getKitFile() {
        return new SimpleFile("plugins/UHC", "kits.yml");
    }
	
	private SimpleFile file = getKitFile();
	
	public void addKit(String kitName, Inventory i, String block, int slot, String lore, int price) {
		
		if(!(file.contains(kitName))) {
			file.setDefault(kitName+".GUI.Lore", lore);
			file.setDefault(kitName+".GUI.Slot", slot);
			file.setDefault(kitName+".GUI.Block", block);
			file.setDefault(kitName+".price", price);
			file.setDefault(kitName+".Contents", KitManager.InventoryToString(i));
		} else {
			file.set(kitName+".GUI.Lore", lore);
			file.set(kitName+".GUI.Slot", slot);
			file.set(kitName+".GUI.Block", block);
			file.set(kitName+".price", price);
			file.set(kitName+".Contents", KitManager.InventoryToString(i));
			file.save();
		}
	}
	
	public int getPrice(String kitName) {
		return file.getInt(kitName+".price");
	}
	
	public String getLore(String kitName) {
		return file.getColorString(kitName+".GUI.Lore");
	}
	
	public int getSlot(String kitName) {
		return file.getInt(kitName+".GUI.Slot");
	}
	
	public String getMaterial(String kitName) {
		return file.getString(kitName+".GUI.Block");
	}
	
	public Inventory getContents(String kitName) {
		return KitManager.StringToInventory(file.getString(kitName+".Contents"));
	}
	
	public ArrayList<String> getAllKits() {
		
		ArrayList<String> kits = new ArrayList<String>();
		
		for(String kit: file.getKeys(false)) {
			kits.add(kit);
		}
		return kits;
	}

}
