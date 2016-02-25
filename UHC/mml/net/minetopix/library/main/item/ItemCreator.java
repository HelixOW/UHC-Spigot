package net.minetopix.library.main.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.minetopix.library.main.item.data.ItemData;
import net.minetopix.library.main.item.data.WrongDataException;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemCreator {
	
	private String name = "";
	private Material m = Material.AIR;
	private int amount = 1;
	private short damage = 0;
	private List<String> lore = new ArrayList<>();
	private HashMap<Enchantment,Integer> enchantments = new HashMap<>();
	private ArrayList<ItemData> itemData = new ArrayList<ItemData>();
	
	
	public ItemCreator(Material m) {
		this.m = m;
	}

	public ItemCreator addEnchantment(Enchantment e,int level){
		enchantments.put(e,level);
		return this;
	}
	public ItemCreator setName(String name) {
		this.name = name;
		return this;
	}
	public ItemCreator setAmount(int amount) {
		this.amount = amount;
		return this;
	}
	public ItemCreator setDamage(short damage) {
		this.damage = damage;
		return this;
	}
	public ItemCreator setLore(String[] lore) {
		ArrayList<String> newLore = new ArrayList<>();
		for(int i = 0;i < lore.length;i++){
			newLore.add(lore[i]);
		}
		this.lore = newLore;
		return this;
	}
	
	public ItemCreator addItemData(ItemData data) {
		itemData.add(data);
		return this;
	}
	
	public ArrayList<ItemData> getAllData(){
		return itemData;
	}
	public HashMap<Enchantment,Integer> getAllEnchantments(){
		return enchantments;
	}
	public String getName() {
		return name;
	}

	public Material getMaterial() {
		return m;
	}

	public int getAmount() {
		return amount;
	}

	public short getDamage() {
		return damage;
	}

	public List<String> getLore() {
		return lore;
	}
	
	public ItemCreator clone() {
		ItemCreator c = new ItemCreator(
				getMaterial()).
				setAmount(getAmount()).
				setDamage(getDamage()).
				setLore(getLore().toArray(new String[0]));
		
		for(Enchantment e : getAllEnchantments().keySet()) {
			c.addEnchantment(e, getAllEnchantments().get(e));
		}
		
		for(ItemData d : getAllData()) {
			c.addItemData(d);
		}
		
		return c;
	}
	
	public ItemStack build() {
		ItemStack s = new ItemStack(m);
		s.setAmount(amount);
		s.setDurability(damage);
		ItemMeta m = s.getItemMeta();
		m.setDisplayName(name);
		m.setLore(lore);
		s.setItemMeta(m);
		for(Entry<Enchantment, Integer> temp : enchantments.entrySet()) {
			s.addUnsafeEnchantment(temp.getKey(), temp.getValue());
		}
		for(ItemData data : itemData) {
			try {
				data.applyOn(s);
			} catch (WrongDataException e) {
				e.printStackTrace();
			}
		}
		return s;
	}

}


