package de.alphahelix.uhc.files;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;
import de.alphahelix.uhc.instances.Kit;

public class KitsFile extends EasyFile {

	private HashMap<String, Kit> kits = new HashMap<>();

	public KitsFile(UHC uhc) {
		super("Kits.uhc", uhc);
	}

	@Override
	public void addValues() {
		setDefault("GUI.Name", "&6Kits");
		setDefault("Kits", true);
		setDefault("Kit.Item", "IRON SWORD");
		setDefault("Kit.Item Name", "&6Kits");
		setDefault("Kit.Item Slot", 0);
		save();
	}

	public void addKit(String kitName, Inventory i, String block, int slot, int price) {
		if (!contains(kitName)) {
			setDefault(kitName + ".GUI.Slot", slot);
			setDefault(kitName + ".GUI.Block", block);
			setDefault(kitName + ".price", price);
			setDefault(kitName + ".Contents", InventoryToString(i));
		} else {
			set(kitName + ".GUI.Slot", slot);
			set(kitName + ".GUI.Block", block);
			set(kitName + ".price", price);
			set(kitName + ".Contents", InventoryToString(i));
			save();
		}
	}

	public Kit getKit(String kitName) {
		kitName = kitName.replace("§", "&");
		kitName = kitName.replace("0º", "&");
		kitName = kitName.contains("0&") ? kitName.replace("0&", "&") : kitName;
		if (this.contains(kitName)) {
				return new Kit(kitName, getInt(kitName + ".price"), StringToInventory(getString(kitName + ".Contents")),
						getInt(kitName + ".GUI.Slot"),
						new ItemStack(Material.getMaterial(getString(kitName + ".GUI.Block").toUpperCase())));
		}
		getLog().log(Level.SEVERE, "The kit " + kitName + " doesn't exist inside the Kits.uhc");
		return null;
	}

	public List<Kit> getKits() {
		LinkedList<Kit> kits = new LinkedList<>();
		for (String kitName : getKeys(false)) {
			try {
				kits.add(getKit(kitName));
			} catch (Exception e) {
				continue;
			}
		}
		return kits;
	}

	public void setKit(Player p, Kit k) {
		kits.put(p.getName(), k);
	}

	public Kit getKitByPlayer(Player p) {
		return kits.containsKey(p.getName()) ? kits.get(p.getName()) : null;
	}

	public boolean hasKit(Player p) {
		if (getKitByPlayer(p) == null)
			return false;
		return true;
	}

	@SuppressWarnings("deprecation")
	public String InventoryToString(Inventory invInventory) {
		String serialization = invInventory.getSize() + ";";
		for (int i = 0; i < invInventory.getSize(); i++) {
			ItemStack is = invInventory.getItem(i);
			if (is != null) {
				String serializedItemStack = "";

				String isType = String.valueOf(is.getType().getId());
				serializedItemStack += "t@" + isType;

				if (is.getDurability() != 0) {
					String isDurability = String.valueOf(is.getDurability());
					serializedItemStack += ":d@" + isDurability;
				}

				if (is.getAmount() != 1) {
					String isAmount = String.valueOf(is.getAmount());
					serializedItemStack += ":a@" + isAmount;
				}

				Map<Enchantment, Integer> isEnch = is.getEnchantments();
				if (isEnch.size() > 0) {
					for (Entry<Enchantment, Integer> ench : isEnch.entrySet()) {
						serializedItemStack += ":e@" + ench.getKey().getId() + "@" + ench.getValue();
					}
				}

				serialization += i + "#" + serializedItemStack + ";";
			}
		}
		return serialization;
	}

	@SuppressWarnings("deprecation")
	public Inventory StringToInventory(String invString) {
		String[] serializedBlocks = invString.split(";");
		Inventory deserializedInventory = Bukkit.getServer().createInventory(null, 45);

		for (int i = 1; i < serializedBlocks.length; i++) {
			String[] serializedBlock = serializedBlocks[i].split("#");
			int stackPosition = Integer.valueOf(serializedBlock[0]);

			if (stackPosition >= deserializedInventory.getSize()) {
				continue;
			}

			ItemStack is = null;
			Boolean createdItemStack = false;

			String[] serializedItemStack = serializedBlock[1].split(":");
			for (String itemInfo : serializedItemStack) {
				String[] itemAttribute = itemInfo.split("@");
				if (itemAttribute[0].equals("t")) {
					is = new ItemStack(Material.getMaterial(Integer.valueOf(itemAttribute[1])));
					createdItemStack = true;
				} else if (itemAttribute[0].equals("d") && createdItemStack) {
					is.setDurability(Short.valueOf(itemAttribute[1]));
				} else if (itemAttribute[0].equals("a") && createdItemStack) {
					is.setAmount(Integer.valueOf(itemAttribute[1]));
				} else if (itemAttribute[0].equals("e") && createdItemStack) {
					is.addEnchantment(Enchantment.getById(Integer.valueOf(itemAttribute[1])),
							Integer.valueOf(itemAttribute[2]));
				}
			}
			deserializedInventory.setItem(stackPosition, is);
		}

		return deserializedInventory;
	}

}
