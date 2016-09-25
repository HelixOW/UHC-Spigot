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
		setMaterialStringList("Description", "Item:Amount:Durability:chance", "Add other items below each other");
		setMaterialStringList("Player", "gold ingot:8:0:1.0", "skull item:1:3:1.0");
		setMaterialStringList("Pig", "grilled pork:3:0:0.5", "pork:1:0:1.0");
		setMaterialStringList("Zombie", "grilled pork:3:0:0.5", "rotten flesh:1:0:0.5");
		setMaterialStringList("Cow", "leather:8:0:0.5", "cooked beef:1:0:0.5");
		setMaterialStringList("Chicken", "arrow:3:0:0.5", "feather:1:0:0.5");
		setMaterialStringList("Spider", "string:2:0:0.5", "spider eye:1:0:0.5");
		setMaterialStringList("Sheep", "cooked mutton:3:0:0.5", "wool:1:0:0.5");
		setMaterialStringList("Rabbit", "cooked rabbit:3:0:0.5", "leather:2:0:0.5");
		setMaterialStringList("Horse", "cooked beef:3:0:0.5", "leather:4:0:0.5");
		setMaterialStringList("Creeper", "gunpoweder:2:0:0.5", "tnt:1:0:0.5");
		setMaterialStringList("Leaves", "sapling:1:0:0.5", "apple:2:0:0.5");
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
	
	public Double getChance(String value, ItemStack s) {
		Double tr = 1.0;
		for(String chances : getMaterialStringList(value)) {
			if(Material.getMaterial(chances.split(":")[0].replace(" ", "_").toUpperCase()).equals(s.getType())) {
				tr = Double.parseDouble(chances.split(":")[3]);
			}
		}
		return tr;
	}
}
