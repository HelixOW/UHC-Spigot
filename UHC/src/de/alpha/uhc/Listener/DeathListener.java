package de.alpha.uhc.Listener;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DeathListener implements Listener {

    private static boolean pig;
    private static boolean zombie;
    private static boolean cow;
    private static boolean chicken;
    private static boolean spider;
    private static boolean skeleton;
    private static boolean sheep;
    private static boolean rabbit;

    private static int pigA;
    private static int zombieA;
    private static int cowA;
    private static int chickenA;
    private static int spiderA;
    private static int skeletonA;
    private static int sheepA;
    private static int rabbitA;

    private static Material pigM;
    private static Material zombieM;
    private static Material cowM;
    private static Material chickenM;
    private static Material spiderM;
    private static Material skeletonM;
    private static Material sheepM;
    private static Material rabbitM;


    public static void setPig(boolean pig) {
        DeathListener.pig = pig;
    }


    public static void setZombie(boolean zombie) {
        DeathListener.zombie = zombie;
    }


    public static void setCow(boolean cow) {
        DeathListener.cow = cow;
    }


    public static void setChicken(boolean chicken) {
        DeathListener.chicken = chicken;
    }


    public static void setSpider(boolean spider) {
        DeathListener.spider = spider;
    }


    public static void setSkeleton(boolean skeleton) {
        DeathListener.skeleton = skeleton;
    }


    public static void setSheep(boolean sheep) {
        DeathListener.sheep = sheep;
    }


    public static void setRabbit(boolean rabbit) {
        DeathListener.rabbit = rabbit;
    }


    public static void setPigA(int pigA) {
        DeathListener.pigA = pigA;
    }


    public static void setZombieA(int zombieA) {
        DeathListener.zombieA = zombieA;
    }


    public static void setCowA(int cowA) {
        DeathListener.cowA = cowA;
    }


    public static void setChickenA(int chickenA) {
        DeathListener.chickenA = chickenA;
    }


    public static void setSpiderA(int spiderA) {
        DeathListener.spiderA = spiderA;
    }


    public static void setSkeletonA(int skeletonA) {
        DeathListener.skeletonA = skeletonA;
    }


    public static void setSheepA(int sheepA) {
        DeathListener.sheepA = sheepA;
    }


    public static void setRabbitA(int rabbitA) {
        DeathListener.rabbitA = rabbitA;
    }


    public static void setPigM(Material pigM) {
        DeathListener.pigM = pigM;
    }


    public static void setZombieM(Material zombieM) {
        DeathListener.zombieM = zombieM;
    }


    public static void setCowM(Material cowM) {
        DeathListener.cowM = cowM;
    }


    public static void setChickenM(Material chickenM) {
        DeathListener.chickenM = chickenM;
    }


    public static void setSpiderM(Material spiderM) {
        DeathListener.spiderM = spiderM;
    }


    public static void setSkeletonM(Material skeletonM) {
        DeathListener.skeletonM = skeletonM;
    }


    public static void setSheepM(Material sheepM) {
        DeathListener.sheepM = sheepM;
    }


    public static void setRabbitM(Material rabbitM) {
        DeathListener.rabbitM = rabbitM;
    }


    @EventHandler
    public void onDie(EntityDeathEvent e) {

        Entity p = e.getEntity();

        if (p instanceof Player) {
            return;
        }

        if (p instanceof Pig) {
            if (!(pig)) return;
            e.getDrops().clear();
            p.getWorld().dropItem(p.getLocation(), new ItemStack(pigM, pigA));
        }

        if (p instanceof Zombie) {
            if (!(zombie)) return;
            e.getDrops().clear();
            p.getWorld().dropItem(p.getLocation(), new ItemStack(zombieM, zombieA));
        }

        if (p instanceof Cow) {
            if (!(cow)) return;
            e.getDrops().clear();
            p.getWorld().dropItem(p.getLocation(), new ItemStack(cowM, cowA));
        }

        if (p instanceof Chicken) {
            if (!(chicken)) return;
            e.getDrops().clear();
            p.getWorld().dropItem(p.getLocation(), new ItemStack(chickenM, chickenA));
        }

        if (p instanceof Spider) {
            if (!(spider)) return;
            e.getDrops().clear();
            p.getWorld().dropItem(p.getLocation(), new ItemStack(spiderM, spiderA));
        }

        if (p instanceof Skeleton) {
            if (!(skeleton)) return;
            e.getDrops().clear();
            p.getWorld().dropItem(p.getLocation(), new ItemStack(skeletonM, skeletonA));
        }

        if (p instanceof Sheep) {
            if (!(sheep)) return;
            e.getDrops().clear();
            p.getWorld().dropItem(p.getLocation(), new ItemStack(sheepM, sheepA));
        }

        if (p instanceof Rabbit) {
            if (!(rabbit)) return;
            e.getDrops().clear();
            p.getWorld().dropItem(p.getLocation(), new ItemStack(rabbitM, rabbitA));
        }
    }
}
