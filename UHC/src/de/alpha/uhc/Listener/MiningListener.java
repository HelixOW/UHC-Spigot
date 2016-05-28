package de.alpha.uhc.Listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;

public class MiningListener implements Listener {
	
	
	public MiningListener(Core c) {}
	

    private  boolean wood;
    private  boolean coal;
    private  boolean iron;
    private  boolean gold;
    private  boolean dia;
    private  boolean gravel;

    private  int coalA;
    private  int ironA;
    private  int goldA;
    private  int diaA;
    private  int gravelA;

    private  Material coalM;
    private  Material ironM;
    private  Material goldM;
    private  Material diaM;
    private  Material gravelM;


    public  void setWood(boolean wood) {
        this.wood = wood;
    }


    public  void setCoal(boolean coal) {
    	this.coal = coal;
    }


    public  void setIron(boolean iron) {
    	this.iron = iron;
    }


    public  void setGold(boolean gold) {
    	this.gold = gold;
    }


    public  void setDia(boolean dia) {
    	this.dia = dia;
    }


    public  void setGravel(boolean gravel) {
    	this.gravel = gravel;
    }


    public  void setCoalA(int coalA) {
    	this.coalA = coalA;
    }


    public  void setIronA(int ironA) {
    	this.ironA = ironA;
    }


    public  void setGoldA(int goldA) {
    	this.goldA = goldA;
    }


    public  void setDiaA(int diaA) {
    	this.diaA = diaA;
    }


    public  void setGravelA(int gravelA) {
    	this.gravelA = gravelA;
    }


    public  void setCoalM(Material coalM) {
    	this.coalM = coalM;
    }


    public  void setIronM(Material ironM) {
    	this.ironM = ironM;
    }


    public  void setGoldM(Material goldM) {
    	this.goldM = goldM;
    }


    public  void setDiaM(Material diaM) {
    	this.diaM = diaM;
    }


    public  void setGravelM(Material gravelM) {
    	this.gravelM = gravelM;
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
                                for (BlockFace bf : Core.getInstance().getRegistery().getBlockUtil().getRelative()) {
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
