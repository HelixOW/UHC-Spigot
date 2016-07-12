package de.alpha.uhc.Listener;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import de.alpha.uhc.Core;
import de.alpha.uhc.GState;
import de.alpha.uhc.Registery;

public class LobbyListener implements Listener {
	
	private Core pl;
	private Registery r;
	
	public LobbyListener(Core c) {
		this.pl = c;
		this.r = pl.getRegistery();
	}

    private  final HashMap<Player, String> kit = new HashMap<>();
    private  String sel;
    private  String bought;
    private  String coinsneed;

    public  void setSel(String sel) {
        this.sel = sel;
    }

    public  void setBought(String bought) {
        this.bought = bought;
    }

    public  void setCoinsneed(String coinsneed) {
        this.coinsneed = coinsneed;
    }

    public  boolean hasSelKit(Player p) {
        return kit.containsKey(p);
    }

    public  String getSelKit(Player p) {
        if (kit.containsKey(p)) {
            return kit.get(p);
        }
        return "";
    }

    @EventHandler
    public void onChunkUnLoad(ChunkUnloadEvent e) {
        if (e.getWorld().getName().equals("world")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {

        if (GState.isState(GState.LOBBY) || GState.isState(GState.GRACE)) {
            e.setFoodLevel(20);
        }

    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        Player p = e.getPlayer();

        if (!(GState.isState(GState.LOBBY))) return;
        if (!r.getRegions().isLobby()) return;

        if (!r.getRegions().isInRegion(e.getTo())) {
            if (r.getSpawnFileManager().getLobby() == null) {
                p.teleport(p.getWorld().getSpawnLocation());
            } else {
                p.teleport(r.getSpawnFileManager().getLobby());
            }
        }

    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        if (GState.isState(GState.LOBBY)) {
            if(!(e.getEntity() instanceof ArmorStand)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onHurt(EntityDamageEvent e) {

        if (e.getEntity() instanceof Player) {
            if (GState.isState(GState.LOBBY) || GState.isState(GState.GRACE)) {
                e.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        if (GState.isState(GState.LOBBY) || GState.isState(GState.RESTART)) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {

        if (GState.isState(GState.LOBBY) || GState.isState(GState.RESTART)) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onInterAct(PlayerInteractEvent e) {

        if (!(GState.isState(GState.LOBBY))) return;
        if (e.getPlayer().getInventory().getItemInMainHand() == null) return;
        if (!(e.getPlayer().getInventory().getItemInMainHand().getType().equals(r.getPlayerJoinListener().getKitItem())))
            return;

        e.setCancelled(true);
        r.getGui().open(e.getPlayer());

    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {

        if (e.getClickedInventory() == null) return;
        if (!(e.getWhoClicked() instanceof Player)) return;
        if(e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
        	e.setCancelled(true);
        	return;
        }
        if (!(e.getClickedInventory().getName().equalsIgnoreCase(r.getGui().getTitle()))) return;
        
        Player p = (Player) e.getWhoClicked();
        
        e.setCancelled(true);

        for (String kits : r.getKitFile().getAllKits()) {
            if (e.getCurrentItem().getType().equals(Material.getMaterial(r.getKitFile().getMaterial(kits).toUpperCase()))) {
                if (r.getStats().getKits(p).contains(kits)) {
                    kit.put(p, kits);
                    sel = sel.replace("[Kit]", kits);
                    p.sendMessage(pl.getPrefix() + sel);
                    p.playSound(p.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1, 1);
                    sel = r.getMessageFile().getMSGFile().getColorString("Kits.GUI.Selected");
                    p.closeInventory();
                    break;
                } else if (r.getStats().getCoins(p) >= r.getKitFile().getPrice(kits)) {
                	r.getStats().removeCoins(r.getKitFile().getPrice(kits), p);
                	r.getStats().addKit(kits, p);
                    if (kit.containsKey(p)) {
                        kit.remove(p);
                    }
                    kit.put(p, kits);
                    bought = bought.replace("[Kit]", kits);
                    bought = bought.replace("[Coins]", Integer.toString(r.getKitFile().getPrice(kits)));
                    p.sendMessage(pl.getPrefix() + bought);
                    bought = r.getMessageFile().getMSGFile().getColorString("Kits.GUI.Bought");
                    p.closeInventory();
                    p.playSound(p.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1, 1);
                    r.getAScoreboard().setLobbyScoreboard(p);
                    break;
                } else {
                    p.sendMessage(pl.getPrefix() + coinsneed);
                }
            }
        }
    }

}
