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
		
		DeathListener.setPig(file.getBoolean("Mobs.Pig.enabled"));
		DeathListener.setZombie(file.getBoolean("Mobs.Zombie.enabled"));
		DeathListener.setCow(file.getBoolean("Mobs.Cow.enabled"));
		DeathListener.setChicken(file.getBoolean("Mobs.Chicken.enabled"));
		DeathListener.setSpider(file.getBoolean("Mobs.Spider.enabled"));
		DeathListener.setSkeleton(file.getBoolean("Mobs.Skeleton.enabled"));
		DeathListener.setSheep(file.getBoolean("Mobs.Sheep.enabled"));
		DeathListener.setRabbit(file.getBoolean("Mobs.Rabbit.enabled"));
		
		DeathListener.setPigA(getAmountByString(file.getString("Mobs.Pig.drop")));
		DeathListener.setZombieA(getAmountByString(file.getString("Mobs.Zombie.drop")));
		DeathListener.setCowA(getAmountByString(file.getString("Mobs.Cow.drop")));
		DeathListener.setChickenA(getAmountByString(file.getString("Mobs.Chicken.drop")));
		DeathListener.setChickenA(getAmountByString(file.getString("Mobs.Chicken.drop")));
		DeathListener.setSpiderA(getAmountByString(file.getString("Mobs.Spider.drop")));
		DeathListener.setSkeletonA(getAmountByString(file.getString("Mobs.Skeleton.drop")));
		DeathListener.setSheepA(getAmountByString(file.getString("Mobs.Sheep.drop")));
		DeathListener.setRabbitA(getAmountByString(file.getString("Mobs.Rabbit.drop")));
		
		DeathListener.setPigM(getMaterialByString(file.getString("Mobs.Pig.drop")));
		DeathListener.setZombieM(getMaterialByString(file.getString("Mobs.Zombie.drop")));
		DeathListener.setCowM(getMaterialByString(file.getString("Mobs.Cow.drop")));
		DeathListener.setChickenM(getMaterialByString(file.getString("Mobs.Chicken.drop")));
		DeathListener.setSpiderM(getMaterialByString(file.getString("Mobs.Spider.drop")));
		DeathListener.setSkeletonM(getMaterialByString(file.getString("Mobs.Skeleton.drop")));
		DeathListener.setSheepM(getMaterialByString(file.getString("Mobs.Sheep.drop")));
		DeathListener.setRabbitM(getMaterialByString(file.getString("Mobs.Rabbit.drop")));
		
		
		MiningListener.setCoal(file.getBoolean("Blocks.Ore.Coal.enabled"));
		MiningListener.setIron(file.getBoolean("Blocks.Ore.Iron.enabled"));
		MiningListener.setGold(file.getBoolean("Blocks.Ore.Gold.enabled"));
		MiningListener.setDia(file.getBoolean("Blocks.Ore.Diamond.enabled"));
		MiningListener.setGravel(file.getBoolean("Blocks.Gravel.enabled"));
		MiningListener.setWood(file.getBoolean("Blocks.Wood.enabled"));
		
		MiningListener.setCoalA(getAmountByString(file.getString("Blocks.Ore.Coal.drop")));
		MiningListener.setIronA(getAmountByString(file.getString("Blocks.Ore.Iron.drop")));
		MiningListener.setGoldA(getAmountByString(file.getString("Blocks.Ore.Gold.drop")));
		MiningListener.setDiaA(getAmountByString(file.getString("Blocks.Ore.Diamond.drop")));
		MiningListener.setGravelA(getAmountByString(file.getString("Blocks.Gravel.drop")));
		
		MiningListener.setCoalM(getMaterialByString(file.getString("Blocks.Ore.Coal.drop")));
		MiningListener.setIronM(getMaterialByString(file.getString("Blocks.Ore.Iron.drop")));
		MiningListener.setGoldM(getMaterialByString(file.getString("Blocks.Ore.Gold.drop")));
		MiningListener.setDiaM(getMaterialByString(file.getString("Blocks.Ore.Diamond.drop")));
		MiningListener.setGravelM(getMaterialByString(file.getString("Blocks.Gravel.drop")));
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
