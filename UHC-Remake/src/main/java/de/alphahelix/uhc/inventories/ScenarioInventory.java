package de.alphahelix.uhc.inventories;

import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.instances.Util;
import de.alphahelix.uhc.register.UHCFileRegister;
import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ScenarioInventory extends Util {

    private Inventory inv;
    private HashMap<String, Scenarios> votedFor = new HashMap<>(); // player ->
    // scenario
    private HashMap<Scenarios, Integer> votes = new HashMap<>(); // scenario ->
    // votes

    public ScenarioInventory(UHC uhc) {
        super(uhc);
        setInv(Bukkit.createInventory(null, 9, UHCFileRegister.getScenarioFile().getInventoryName()));
    }

    public void fillInventory() {
        for (int slot = 0; slot < getInv().getSize(); slot++) {
            getInv().setItem(slot,
                    new ItemBuilder(Material.STAINED_GLASS_PANE).setName(" ").setDamage((short) 7).build());
        }

        Scenarios[] toChoose = Scenarios.getScenariosToChoose();

        getInv().setItem(1, UHCFileRegister.getScenarioFile().getScenarioItem(toChoose[0]));
        getInv().setItem(3, UHCFileRegister.getScenarioFile().getScenarioItem(toChoose[1]));
        getInv().setItem(5, UHCFileRegister.getScenarioFile().getScenarioItem(toChoose[2]));
        getInv().setItem(7, UHCFileRegister.getScenarioFile().getScenarioItem(toChoose[3]));

        initVotes(toChoose);
    }

    public void openInventory(Player p) {
        p.openInventory(getInv());
    }

    public Inventory getInv() {
        return inv;
    }

    public void setInv(Inventory inv) {
        this.inv = inv;
    }

    public void initVotes(Scenarios... s) {
        for (Scenarios scen : s) {
            votes.put(scen, 0);
        }
    }

    public Scenarios getScenarioWithMostVotes() {
        Scenarios[] array = votes.keySet().toArray(new Scenarios[votes.keySet().size()]);

        if(array.length != 4) return Scenarios.MINECRAFT;

        int[] k = {votes.get(array[0]), votes.get(array[1]), votes.get(array[2]), votes.get(array[3])};

        List<Integer> ks = Arrays.asList(ArrayUtils.toObject(k));

        int maxVotes = Collections.max(ks);
        for (Scenarios s : array) {
            if (votes.get(s) == maxVotes)
                return s;
        }
        return array[0];
    }

    private void updateInv(Scenarios toUpdateFor) {
        getInv().setItem(
                getInv().first(UHCFileRegister.getScenarioFile().getScenarioItem(toUpdateFor)),
                new ItemBuilder(UHCFileRegister.getScenarioFile().getScenarioItem(toUpdateFor).getType())
                        .setAmount(votes.get(toUpdateFor))
                        .setName(UHCFileRegister.getScenarioFile().getScenarioItem(toUpdateFor).getItemMeta().getDisplayName())
                        .setLore(UHCFileRegister.getScenarioHelpFile()
                                .getScenarioDescription(toUpdateFor))
                        .build());
    }

    public void castVote(Player p, Scenarios toVoteFor) {
        if (hasVoted(p)) return;
        addVote(p, toVoteFor);
        updateInv(toVoteFor);
    }

    private void addVote(Player p, Scenarios s) {
        votedFor.put(p.getName(), s);
        votes.put(s, votes.get(s) + 1);
    }

    private boolean hasVoted(Player p) {
        return votedFor.containsKey(p.getName());
    }
}
