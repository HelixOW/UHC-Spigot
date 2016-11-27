package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class InventorsListener extends SimpleListener {

	private ArrayList<ItemStack> hasBeenCrafted = new ArrayList<>();

	public InventorsListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onCraft(CraftItemEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.INVENTORS))
			return;

		if (!hasBeenCrafted.contains(e.getRecipe().getResult())) {
			hasBeenCrafted.add(e.getRecipe().getResult());
			for (Player p : makeArray(getRegister().getPlayerUtil().getAll())) {
				p.sendMessage(getUhc().getPrefix() + getRegister().getMessageFile().getColorString("First crafted Item")
						.replace("[player]", e.getWhoClicked().getName())
						.replace("[item]", e.getRecipe().getResult().getType().name().toLowerCase().replace("_", " ")));
			}
		}
	}
}
