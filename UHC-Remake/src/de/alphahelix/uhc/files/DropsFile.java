package de.alphahelix.uhc.files;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;

public class DropsFile extends EasyFile {

	public DropsFile(UHC uhc) {
		super("drops.uhc", uhc);
	}
	
	@Override
	public void addValues() {
		setDefault("Deathchest", true);
		setMaterialStringList("Player", "gold ingot:8:0", "skull item:1:3");
	}
	
	public ArrayList<ItemStack> readValues(String value) {
		List<String> matAndDataList = getMaterialStringList(value);
		ArrayList<ItemStack> toReturn = new ArrayList<>();
		for(String matAndData : matAndDataList) {
			String[] matAndDataArray = matAndData.split(":");
			
			Material m = Material.getMaterial(matAndDataArray[0].replace(" ", "_").toUpperCase());
			int amount = Integer.parseInt(matAndDataArray[1]);
			short damage = Short.parseShort(matAndDataArray[2]);
			toReturn.add(new ItemStack(m, amount, damage));
		}
		return toReturn;
	}
}
