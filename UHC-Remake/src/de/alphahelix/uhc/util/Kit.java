package de.alphahelix.uhc.util;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.alphahelix.uhc.UHC;

public class Kit {

	private int price;
	private int guiSlot;
	private String name;
	private ItemStack guiBlock;
	private Inventory inventory;

	public Kit(String name, int price, Inventory inventory, int guiSlot, ItemStack guiBlock) {
		setName(name);
		setPrice(price);
		setGuiSlot(guiSlot);
		setGuiBlock(guiBlock);
		setInventory(inventory);
	}

	public void registerKit() {
		UHC.getInstance().getRegister().getKitsFile().addKit(name, inventory, guiBlock.getType().name(),
				guiSlot,
				price);
	}
	
	public int getPrice() {
		return price;
	}

	private void setPrice(int price) {
		this.price = price;
	}

	public int getGuiSlot() {
		return guiSlot;
	}

	private void setGuiSlot(int guiSlot) {
		this.guiSlot = guiSlot;
	}

	public String getName() {
		return name.replace("&", "§");
	}

	private void setName(String name) {
		this.name = name;
	}

	public ItemStack getGuiBlock() {
		return guiBlock;
	}

	private void setGuiBlock(ItemStack guiBlock) {
		this.guiBlock = guiBlock;
	}

	public Inventory getInventory() {
		return inventory;
	}

	private void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
}
