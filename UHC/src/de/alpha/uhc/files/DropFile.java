package de.alpha.uhc.files;

import org.bukkit.Material;

import de.alpha.uhc.Listener.DeathListener;
import de.alpha.uhc.Listener.MiningListener;
import de.popokaka.alphalibary.file.SimpleFile;

public class DropFile {
	
	public static SimpleFile getDropFile() {
		return new SimpleFile("plugins/UHC", "drops.yml");
	}
	
	private static SimpleFile file = getDropFile();
	
	public static void addDrops() {
		
		file.setDefault("Blocks.Ore.Coal.enabled", true);
		file.setDefault("Blocks.Ore.Coal.drop", "coal:2");
		
		file.setDefault("Blocks.Ore.Iron.enabled", true);
		file.setDefault("Blocks.Ore.Iron.drop", "iron_ingot:2");
		
		file.setDefault("Blocks.Ore.Gold.enabled", true);
		file.setDefault("Blocks.Ore.Gold.drop", "gold_ingot:5");
		
		file.setDefault("Blocks.Ore.Diamond.enabled", true);
		file.setDefault("Blocks.Ore.Diamond.drop", "diamond:7");
		
		file.setDefault("Blocks.Gravel.enabled", true);
		file.setDefault("Blocks.Gravel.drop", "arrow:3");
		
		file.setDefault("Blocks.Wood.enabled", true);
		
		file.setDefault("Mobs.Pig.enabled", true);
		file.setDefault("Mobs.Pig.drop", "grilled_pork:3");
		
		file.setDefault("Mobs.Zombie.enabled", true);
		file.setDefault("Mobs.Zombie.drop", "grilled_pork:3");
		
		file.setDefault("Mobs.Cow.enabled", true);
		file.setDefault("Mobs.Cow.drop", "leather:8");
		
		file.setDefault("Mobs.Chicken.enabled", true);
		file.setDefault("Mobs.Chicken.drop", "arrow:3");
		
		file.setDefault("Mobs.Spider.enabled", true);
		file.setDefault("Mobs.Spider.drop", "string:2");
		
		file.setDefault("Mobs.Skeleton.enabled", true);
		file.setDefault("Mobs.Skeleton.drop", "bow:1");
		
		file.setDefault("Mobs.Sheep.enabled", true);
		file.setDefault("Mobs.Sheep.drop", "cooked_mutton:3");
		
		file.setDefault("Mobs.Rabbit.enabled", true);
		file.setDefault("Mobs.Rabbit.drop", "cooked_rabbit:3");
		
	}
	
	public static void loadDrops() {
		
		DeathListener.pig = file.getBoolean("Mobs.Pig.enabled");
		DeathListener.zombie = file.getBoolean("Mobs.Zombie.enabled");
		DeathListener.cow = file.getBoolean("Mobs.Cow.enabled");
		DeathListener.chicken = file.getBoolean("Mobs.Chicken.enabled");
		DeathListener.spider = file.getBoolean("Mobs.Spider.enabled");
		DeathListener.skeleton = file.getBoolean("Mobs.Skeleton.enabled");
		DeathListener.sheep = file.getBoolean("Mobs.Sheep.enabled");
		DeathListener.rabbit = file.getBoolean("Mobs.Rabbit.enabled");
		
		DeathListener.pigA = getAmountByString(file.getString("Mobs.Pig.drop"));
		DeathListener.zombieA = getAmountByString(file.getString("Mobs.Zombie.drop"));
		DeathListener.cowA = getAmountByString(file.getString("Mobs.Cow.drop"));
		DeathListener.chickenA = getAmountByString(file.getString("Mobs.Chicken.drop"));
		DeathListener.spiderA = getAmountByString(file.getString("Mobs.Spider.drop"));
		DeathListener.skeletonA = getAmountByString(file.getString("Mobs.Skeleton.drop"));
		DeathListener.sheepA = getAmountByString(file.getString("Mobs.Sheep.drop"));
		DeathListener.rabbitA = getAmountByString(file.getString("Mobs.Rabbit.drop"));
		
		DeathListener.pigM = getMaterialByString(file.getString("Mobs.Pig.drop"));
		DeathListener.zombieM = getMaterialByString(file.getString("Mobs.Zombie.drop"));
		DeathListener.cowM = getMaterialByString(file.getString("Mobs.Cow.drop"));
		DeathListener.chickenM = getMaterialByString(file.getString("Mobs.Chicken.drop"));
		DeathListener.spiderM = getMaterialByString(file.getString("Mobs.Spider.drop"));
		DeathListener.skeletonM = getMaterialByString(file.getString("Mobs.Skeleton.drop"));
		DeathListener.sheepM = getMaterialByString(file.getString("Mobs.Sheep.drop"));
		DeathListener.rabbitM = getMaterialByString(file.getString("Mobs.Rabbit.drop"));
		
		MiningListener.coal = file.getBoolean("Blocks.Ore.Coal.enabled");
		MiningListener.iron = file.getBoolean("Blocks.Ore.Iron.enabled");
		MiningListener.gold = file.getBoolean("Blocks.Ore.Gold.enabled");
		MiningListener.dia = file.getBoolean("Blocks.Ore.Diamond.enabled");
		MiningListener.gravel = file.getBoolean("Blocks.Gravel.enabled");
		MiningListener.wood = file.getBoolean("Blocks.Wood.enabled");
		
		MiningListener.coalA = getAmountByString(file.getString("Blocks.Ore.Coal.drop"));
		MiningListener.ironA = getAmountByString(file.getString("Blocks.Ore.Iron.drop"));
		MiningListener.goldA = getAmountByString(file.getString("Blocks.Ore.Gold.drop"));
		MiningListener.diaA = getAmountByString(file.getString("Blocks.Ore.Diamond.drop"));
		MiningListener.gravelA = getAmountByString(file.getString("Blocks.Gravel.drop"));
		
		MiningListener.coalM = getMaterialByString(file.getString("Blocks.Ore.Coal.drop"));
		MiningListener.ironM = getMaterialByString(file.getString("Blocks.Ore.Iron.drop"));
		MiningListener.goldM = getMaterialByString(file.getString("Blocks.Ore.Gold.drop"));
		MiningListener.diaM = getMaterialByString(file.getString("Blocks.Ore.Diamond.drop"));
		MiningListener.gravelM = getMaterialByString(file.getString("Blocks.Gravel.drop"));
		
	}
	
	public static Material getMaterialByString(String block) {
		
		String blockAsArray[] = block.split(":");
		
		return Material.getMaterial(blockAsArray[0].toUpperCase());
	}
	
	public static int getAmountByString(String block) {
		
		String blockAsArray[] = block.split(":");
		
		return Integer.parseInt(blockAsArray[1]);
	}
}
