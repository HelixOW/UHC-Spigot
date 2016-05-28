package de.alpha.uhc.files;

import org.bukkit.Material;

import de.alpha.uhc.Core;
import de.alpha.uhc.Registery;
import de.popokaka.alphalibary.file.SimpleFile;

public class DropFile {
	
	private Core pl;
	private Registery r;
	
	public DropFile(Core c) {
		this.pl = c;
		this.r = pl.getRegistery();
	}

    private  final SimpleFile file = getDropFile();

    private  SimpleFile getDropFile() {
        return new SimpleFile("plugins/UHC", "drops.yml");
    }

    public  void addDrops() {

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

    public  void loadDrops() {

        r.getDeathListener().setPig(file.getBoolean("Mobs.Pig.enabled"));
        r.getDeathListener().setZombie(file.getBoolean("Mobs.Zombie.enabled"));
        r.getDeathListener().setCow(file.getBoolean("Mobs.Cow.enabled"));
        r.getDeathListener().setChicken(file.getBoolean("Mobs.Chicken.enabled"));
        r.getDeathListener().setSpider(file.getBoolean("Mobs.Spider.enabled"));
        r.getDeathListener().setSkeleton(file.getBoolean("Mobs.Skeleton.enabled"));
        r.getDeathListener().setSheep(file.getBoolean("Mobs.Sheep.enabled"));
        r.getDeathListener().setRabbit(file.getBoolean("Mobs.Rabbit.enabled"));

        r.getDeathListener().setPigA(getAmountByString(file.getString("Mobs.Pig.drop")));
        r.getDeathListener().setZombieA(getAmountByString(file.getString("Mobs.Zombie.drop")));
        r.getDeathListener().setCowA(getAmountByString(file.getString("Mobs.Cow.drop")));
        r.getDeathListener().setChickenA(getAmountByString(file.getString("Mobs.Chicken.drop")));
        r.getDeathListener().setChickenA(getAmountByString(file.getString("Mobs.Chicken.drop")));
        r.getDeathListener().setSpiderA(getAmountByString(file.getString("Mobs.Spider.drop")));
        r.getDeathListener().setSkeletonA(getAmountByString(file.getString("Mobs.Skeleton.drop")));
        r.getDeathListener().setSheepA(getAmountByString(file.getString("Mobs.Sheep.drop")));
        r.getDeathListener().setRabbitA(getAmountByString(file.getString("Mobs.Rabbit.drop")));

        r.getDeathListener().setPigM(getMaterialByString(file.getString("Mobs.Pig.drop")));
        r.getDeathListener().setZombieM(getMaterialByString(file.getString("Mobs.Zombie.drop")));
        r.getDeathListener().setCowM(getMaterialByString(file.getString("Mobs.Cow.drop")));
        r.getDeathListener().setChickenM(getMaterialByString(file.getString("Mobs.Chicken.drop")));
        r.getDeathListener().setSpiderM(getMaterialByString(file.getString("Mobs.Spider.drop")));
        r.getDeathListener().setSkeletonM(getMaterialByString(file.getString("Mobs.Skeleton.drop")));
        r.getDeathListener().setSheepM(getMaterialByString(file.getString("Mobs.Sheep.drop")));
        r.getDeathListener().setRabbitM(getMaterialByString(file.getString("Mobs.Rabbit.drop")));


        r.getMiningListener().setCoal(file.getBoolean("Blocks.Ore.Coal.enabled"));
        r.getMiningListener().setIron(file.getBoolean("Blocks.Ore.Iron.enabled"));
        r.getMiningListener().setGold(file.getBoolean("Blocks.Ore.Gold.enabled"));
        r.getMiningListener().setDia(file.getBoolean("Blocks.Ore.Diamond.enabled"));
        r.getMiningListener().setGravel(file.getBoolean("Blocks.Gravel.enabled"));
        r.getMiningListener().setWood(file.getBoolean("Blocks.Wood.enabled"));

        r.getMiningListener().setCoalA(getAmountByString(file.getString("Blocks.Ore.Coal.drop")));
        r.getMiningListener().setIronA(getAmountByString(file.getString("Blocks.Ore.Iron.drop")));
        r.getMiningListener().setGoldA(getAmountByString(file.getString("Blocks.Ore.Gold.drop")));
        r.getMiningListener().setDiaA(getAmountByString(file.getString("Blocks.Ore.Diamond.drop")));
        r.getMiningListener().setGravelA(getAmountByString(file.getString("Blocks.Gravel.drop")));

        r.getMiningListener().setCoalM(getMaterialByString(file.getString("Blocks.Ore.Coal.drop")));
        r.getMiningListener().setIronM(getMaterialByString(file.getString("Blocks.Ore.Iron.drop")));
        r.getMiningListener().setGoldM(getMaterialByString(file.getString("Blocks.Ore.Gold.drop")));
        r.getMiningListener().setDiaM(getMaterialByString(file.getString("Blocks.Ore.Diamond.drop")));
        r.getMiningListener().setGravelM(getMaterialByString(file.getString("Blocks.Gravel.drop")));
    }

    private  Material getMaterialByString(String block) {

        String blockAsArray[] = block.split(":");

        return Material.getMaterial(blockAsArray[0].toUpperCase());
    }

    private  int getAmountByString(String block) {

        String blockAsArray[] = block.split(":");

        return Integer.parseInt(blockAsArray[1]);
    }
}
