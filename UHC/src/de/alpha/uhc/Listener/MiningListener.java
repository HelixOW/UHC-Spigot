package de.alpha.uhc.Listener;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.utils.BlockUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class MiningListener implements Listener {
	
	private Core pl;
	
	public MiningListener(Core c) {
		this.pl = c;
	}


    private static boolean wood;
    private static boolean coal;
    private static boolean iron;
    private static boolean gold;
    private static boolean dia;
    private static boolean gravel;

    private static int coalA;
    private static int ironA;
    private static int goldA;
    private static int diaA;
    private static int gravelA;

    private static Material coalM;
    private static Material ironM;
    private static Material goldM;
    private static Material diaM;
    private static Material gravelM;


    public static void setWood(boolean wood) {
        MiningListener.wood = wood;
    }


    public static void setCoal(boolean coal) {
        MiningListener.coal = coal;
    }


    public static void setIron(boolean iron) {
        MiningListener.iron = iron;
    }


    public static void setGold(boolean gold) {
        MiningListener.gold = gold;
    }


    public static void setDia(boolean dia) {
        MiningListener.dia = dia;
    }


    public static void setGravel(boolean gravel) {
        MiningListener.gravel = gravel;
    }


    public static void setCoalA(int coalA) {
        MiningListener.coalA = coalA;
    }


    public static void setIronA(int ironA) {
        MiningListener.ironA = ironA;
    }


    public static void setGoldA(int goldA) {
        MiningListener.goldA = goldA;
    }


    public static void setDiaA(int diaA) {
        MiningListener.diaA = diaA;
    }


    public static void setGravelA(int gravelA) {
        MiningListener.gravelA = gravelA;
    }


    public static void setCoalM(Material coalM) {
        MiningListener.coalM = coalM;
    }


    public static void setIronM(Material ironM) {
        MiningListener.ironM = ironM;
    }


    public static void setGoldM(Material goldM) {
        MiningListener.goldM = goldM;
    }


    public static void setDiaM(Material diaM) {
        MiningListener.diaM = diaM;
    }


    public static void setGravelM(Material gravelM) {
        MiningListener.gravelM = gravelM;
    }


    @EventHandler
    public void onMine(BlockBreakEvent e) {

        Block b = e.getBlock();
        Material m = b.getType();

        if (GState.isState(GState.LOBBY) || GState.isState(GState.RESTART)) return;

        if (m.equals(Material.LOG) || m.equals(Material.LOG_2)) {

            if (wood) {

                if (e.getPlayer() != null) {
                    e.setCancelled(true);
                    boolean inWood = false;

                    for (int y = -13; y <= 13; y++) {
                        Location loc = e.getBlock().getLocation().add(0, y, 0);
                        if (loc.getBlock().getType() == Material.LOG || loc.getBlock().getType() == Material.LOG_2) {

                            if (!inWood) {
                                inWood = true;
                            }
                            loc.getBlock().breakNaturally();
                            for (int i = -13; i<=13; i++) {
                                for (BlockFace bf : BlockUtil.getRelative()) {
                                    if (loc.getBlock().getRelative(bf).getType().equals(Material.LEAVES) || loc.getBlock().getRelative(bf).getType().equals(Material.LEAVES_2)) {
                                        loc.getBlock().getRelative(bf).breakNaturally();
                                        if (loc.getBlock().getRelative(bf, i).getType().equals(Material.LEAVES) || loc.getBlock().getRelative(bf, 2).getType().equals(Material.LEAVES_2))
                                            loc.getBlock().getRelative(bf, i).breakNaturally();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (m.equals(Material.IRON_ORE)) {
            if (!iron) {
                e.setCancelled(true);
                return;
            }
            b.setType(Material.AIR);
            b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(ironM, ironA));
            e.setCancelled(true);
        }

        if (m.equals(Material.GRAVEL)) {
            if (!gravel) return;
            b.setType(Material.AIR);
            b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(gravelM, gravelA));
            e.setCancelled(true);
        }

        if (m.equals(Material.GOLD_ORE)) {
            if (!gold) {
                e.setCancelled(true);
                return;
            }
            b.setType(Material.AIR);
            b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(goldM, goldA));
            e.setCancelled(true);
        }

        if (m.equals(Material.DIAMOND_ORE)) {
            if (!dia) {
                e.setCancelled(true);
                return;
            }
            b.setType(Material.AIR);
            b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(diaM, diaA));
            e.setCancelled(true);
        }

        if (m.equals(Material.COAL_ORE)) {
            if (!coal) {
                e.setCancelled(true);
                return;
            }
            b.setType(Material.AIR);
            b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(coalM, coalA));
            e.setCancelled(true);
        }
    }
}
