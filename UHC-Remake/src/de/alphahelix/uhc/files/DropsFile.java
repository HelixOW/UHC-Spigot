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
		setMaterialStringList("Description", "Item:Amount:Durability", "Add other items below each other");
		setMaterialStringList("Player", "gold ingot:8:0", "skull item:1:3");
		setMaterialStringList("Pig", "grilled pork:3:0", "pork:1:0");
		setMaterialStringList("Zombie", "grilled pork:3:0", "rotten flesh:1:0");
		setMaterialStringList("Cow", "leather:8:0", "cooked beef:1:0");
		setMaterialStringList("Chicken", "arrow:3:0", "feather:1:0");
		setMaterialStringList("Spider", "string:2:0", "spider eye:1:0");
		setMaterialStringList("Sheep", "cooked mutton:3:0", "wool:1:0");
		setMaterialStringList("Rabbit", "cooked rabbit:3:0", "leather:2:0");
		setMaterialStringList("Horse", "cooked beef:3:0", "leather:4:0");
		setMaterialStringList("Creeper", "gunpoweder:2:0", "tnt:1:0");
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
