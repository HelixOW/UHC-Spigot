package de.alphahelix.uhc.listeners.scenarios;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;
import de.popokaka.alphalibary.item.ItemBuilder;

public class LootchestListener extends SimpleListener {

	public LootchestListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onEnd(LobbyEndEvent e) {
		if (!scenarioCheck(Scenarios.LOOTCRATES))
			return;

		new BukkitRunnable() {
			public void run() {
				for (Player p : makeArray(getRegister().getPlayerUtil().getSurvivors())) {
					int i = new Random().nextInt(2);

					p.getInventory().addItem(new ItemBuilder(i == 0 ? Material.ENDER_CHEST : Material.CHEST)
							.setName("§aLootcrate §7: §a" + (i == 0 ? "2" : "1")).build());
				}
			}
		}.runTaskTimer(getUhc(), 10800, 12000);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getItem() == null) {
			return;
		}

		Player p = e.getPlayer();
		ItemStack item = e.getItem();

		if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
			if (item.getType() == Material.CHEST && item.getItemMeta().getDisplayName().equals("§aLootcrate §7: §a1")) {
				int i = new Random().nextInt(5);
				e.setCancelled(true);

				switch (i) {
				case 0:
					p.getInventory().setItemInMainHand(new ItemStack(Material.IRON_PICKAXE));
					break;
				case 1:
					p.getInventory().setItemInMainHand(new ItemStack(Material.APPLE, 2));
					break;
				case 2:
					p.getInventory().setItemInMainHand(new ItemStack(Material.COOKED_BEEF, 8));
					break;
				case 3:
					p.getInventory().setItemInMainHand(new ItemStack(Material.CAKE));
					break;
				case 4:
					p.getInventory().setItemInMainHand(new ItemStack(Material.RAW_FISH, 64, (short) 3));
					break;
				case 5:
					p.getInventory().setItemInMainHand(new ItemStack(Material.DIRT, 32));
					break;
				}
				return;
			}

			if (item.getType() == Material.ENDER_CHEST
					&& item.getItemMeta().getDisplayName().equals("§aLootcrate §7: §a2")) {
				int i = new Random().nextInt(10);
				e.setCancelled(true);

				switch (i) {
				case 0:
					p.getInventory().setItemInMainHand(new ItemStack(Material.DIAMOND));
					break;
				case 1:
					p.getInventory().setItemInMainHand(new ItemStack(Material.GOLD_INGOT, 3));
					break;
				case 2:
					p.getInventory().setItemInMainHand(new ItemStack(Material.IRON_INGOT, 5));
					break;
				case 3:
					p.getInventory().setItemInMainHand(new ItemStack(Material.BOW));
					break;
				case 4:
					p.getInventory().setItemInMainHand(new ItemStack(Material.ENCHANTMENT_TABLE));
					break;
				case 5:
					p.getInventory().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
					break;
				case 6:
					p.getInventory().setItemInMainHand(new ItemStack(Material.DIAMOND_HELMET));
					break;
				case 7:
					p.getInventory().setItemInMainHand(new ItemStack(Material.DIAMOND_BOOTS));
					break;
				case 8:
					p.getInventory().setItemInMainHand(new ItemStack(Material.ARROW, 32));
					break;
				case 9:
					p.getInventory().setItemInMainHand(new ItemStack(Material.TNT));
					break;
				case 10:
					p.getInventory().setItemInMainHand(new ItemStack(Material.FLINT_AND_STEEL));
					break;
				}
			}
		}
	}

}
