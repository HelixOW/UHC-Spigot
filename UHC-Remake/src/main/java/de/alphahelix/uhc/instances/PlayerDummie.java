package de.alphahelix.uhc.instances;

import de.alphahelix.alphaapi.utils.MinecraftVersion;
import de.alphahelix.alphaapi.uuid.UUIDFetcher;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerDummie {

    private static ArrayList<PlayerDummie> dummies = new ArrayList<>();

    private UUID id;
    private Inventory inv;
    private Location location;
    private Villager dummie;

    public PlayerDummie(Player p) {
        this.id = UUIDFetcher.getUUID(p);
        this.location = p.getLocation();

        Villager villager = location.getWorld().spawn(location, Villager.class);

        villager.setCustomNameVisible(true);
        villager.setCustomName(p.getName());

        villager.getEquipment().setArmorContents(p.getInventory().getArmorContents());
        villager.setHealth(p.getHealth());

        try {
            if (MinecraftVersion.getServer() != MinecraftVersion.EIGHT)
                Class.forName("org.bukkit.entity.LivingEntity").getMethod("setAI", boolean.class).invoke(villager, false);
            else {
                villager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        this.dummie = villager;
        this.inv = p.getInventory();

        if(getDummie(id) != null) dummies.remove(getDummie(id));

        dummies.add(this);
    }

    public static PlayerDummie getDummie(UUID owner) {
        for (PlayerDummie playerDummie : dummies) {
            if (owner.equals(playerDummie.getId())) return playerDummie;
        }
        return null;
    }

    public static void restore(Player p) {
        PlayerDummie pd = getDummie(UUIDFetcher.getUUID(p));

        if(pd == null) return;

        for(ItemStack is : pd.getInv()) {
            if(is != null)
                pd.getLocation().getWorld().dropItemNaturally(pd.getLocation().clone().add(0, 1, 0), is);
        }

        p.setHealth(pd.getDummie().getHealth());
        p.teleport(pd.getLocation());

        pd.getDummie().remove();
        dummies.remove(pd);
    }

    public UUID getId() {
        return id;
    }

    public Inventory getInv() {
        return inv;
    }

    public Location getLocation() {
        return location;
    }

    public Villager getDummie() {
        return dummie;
    }
}
