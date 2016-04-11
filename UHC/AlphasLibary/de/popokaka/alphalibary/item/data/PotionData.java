package de.popokaka.alphalibary.item.data;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

public class PotionData extends ItemData{
	
	private ArrayList<PotionEffect> toApply = new ArrayList<PotionEffect>();
	
	public PotionData addEffect(SimplePotionEffect... effects) {
		for(SimplePotionEffect effect : effects) {
			this.toApply.add(effect.createEffect());
		}
		return this;
	}
	
	@Override
	public void applyOn(ItemStack applyOn) throws WrongDataException {
		try {
			
			PotionMeta meta = (PotionMeta) applyOn.getItemMeta();
			for(PotionEffect effect : toApply) {
				meta.addCustomEffect(effect, false);
			}
			applyOn.setItemMeta(meta);
			
		} catch (Exception e) {
			throw new WrongDataException(this);
		}
	}
}
