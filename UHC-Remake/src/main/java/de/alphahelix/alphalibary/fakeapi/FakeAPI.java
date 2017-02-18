/*
 * Copyright (C) <2017>  <AlphaHelixDev>
 *
 *       This program is free software: you can redistribute it under the
 *       terms of the GNU General Public License as published by
 *       the Free Software Foundation, either version 3 of the License.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU General Public License for more details.
 *
 *       You should have received a copy of the GNU General Public License
 *       along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.alphahelix.alphalibary.fakeapi;

import de.alphahelix.alphalibary.AlphaPlugin;
import de.alphahelix.alphalibary.fakeapi.instances.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class FakeAPI extends AlphaPlugin {

    private static FakeAPI fakeAPI;

    private static HashMap<String, ArrayList<FakePlayer>> fakePlayers = new HashMap<>();
    private static HashMap<String, ArrayList<FakeArmorstand>> fakeArmorstands = new HashMap<>();
    private static HashMap<String, ArrayList<FakeEndercrystal>> fakeEndercrystals = new HashMap<>();
    private static HashMap<String, ArrayList<FakeItem>> fakeItems = new HashMap<>();
    private static HashMap<String, ArrayList<FakeMob>> fakeMobs = new HashMap<>();
    private static HashMap<String, ArrayList<FakeBigItem>> fakeBigItems = new HashMap<>();
    private static HashMap<String, ArrayList<FakeXPOrb>> fakeXPOrbs = new HashMap<>();
    private static HashMap<String, ArrayList<FakePainting>> fakePaintings = new HashMap<>();

    /**
     * Gets a {@link HashMap} with all {@link Player}s and which {@link FakePlayer}s they can see
     *
     * @return a {@link HashMap} with all {@link Player}s and which {@link FakePlayer}s they can see
     */
    public static HashMap<String, ArrayList<FakePlayer>> getFakePlayer() {
        return fakePlayers;
    }

    /**
     * Adds a new {@link FakePlayer} for a specific {@link Player}
     *
     * @param p    the {@link Player} to add the {@link FakePlayer} for
     * @param fake the {@link FakePlayer} which should be added
     */
    public static void addFakePlayer(Player p, FakePlayer fake) {
        ArrayList<FakePlayer> list;
        if (getFakePlayer().containsKey(p.getName())) {
            list = getFakePlayer().get(p.getName());
            list.add(fake);
        } else {
            list = new ArrayList<>();
            list.add(fake);
        }
        getFakePlayer().put(p.getName(), list);
    }

    /**
     * Removes a {@link FakePlayer} for a specific {@link Player}
     *
     * @param p    the {@link Player} to remove the {@link FakePlayer} for
     * @param fake the {@link FakePlayer} which should be removed
     */
    public static void removeFakePlayer(Player p, FakePlayer fake) {
        ArrayList<FakePlayer> list;
        if (getFakePlayer().containsKey(p.getName())) {
            list = getFakePlayer().get(p.getName());
            list.remove(fake);
        } else {
            list = new ArrayList<>();
        }
        getFakePlayer().put(p.getName(), list);
    }

    /**
     * Checks if a specific {@link Player} looks at a {@link FakePlayer} in a certain range
     *
     * @param p     the {@link Player} to check for
     * @param range the range in which it should be checked
     * @return whether or not the {@link Player} looks at a {@link FakePlayer}
     */
    public static boolean isFakePlayerInRange(Player p, int range) {
        if (!getFakePlayer().containsKey(p.getName())) return false;
        for (Block b : p.getLineOfSight((Set<Material>) null, range)) {
            for (FakePlayer fakePlayer : getFakePlayer().get(p.getName())) {
                if ((b.getX() == fakePlayer.getCurrentlocation().getBlockX()
                        && b.getY() == fakePlayer.getCurrentlocation().getBlockY()
                        && b.getZ() == fakePlayer.getCurrentlocation().getBlockZ()
                        || (b.getX() == fakePlayer.getCurrentlocation().getBlockX()
                        && b.getY() == (fakePlayer.getCurrentlocation().getBlockY() + 1)
                        && b.getZ() == fakePlayer.getCurrentlocation().getBlockZ())))
                    return true;
            }
        }
        return false;
    }

    /**
     * Gets the {@link FakePlayer} at which the {@link Player} is currently looking at
     *
     * @param p     the {@link Player} which looks at the {@link FakePlayer}
     * @param range the range in which it should stand
     * @return the {@link FakePlayer} inside the range
     * @throws NoSuchFakeEntityException if there is nor {@link FakePlayer} in the range
     */
    public static FakePlayer getLookedAtFakePlayer(Player p, int range) throws NoSuchFakeEntityException {
        if (!getFakePlayer().containsKey(p.getName())) return null;

        for (Block b : p.getLineOfSight((Set<Material>) null, range)) {
            for (FakePlayer fakePlayer : getFakePlayer().get(p.getName())) {
                if ((b.getX() == fakePlayer.getCurrentlocation().getBlockX()
                        && b.getY() == fakePlayer.getCurrentlocation().getBlockY()
                        && b.getZ() == fakePlayer.getCurrentlocation().getBlockZ()
                        || (b.getX() == fakePlayer.getCurrentlocation().getBlockX()
                        && b.getY() == (fakePlayer.getCurrentlocation().getBlockY() + 1)
                        && b.getZ() == fakePlayer.getCurrentlocation().getBlockZ())))
                    return fakePlayer;
            }
        }
        return null;
    }

    /**
     * Gets a {@link ArrayList} with all {@link FakePlayer} inside a certain radius
     *
     * @param p      the {@link Player} for which it should search for
     * @param radius the radius in which should be searched for
     * @return a {@link ArrayList} with all {@link FakePlayer} inside the radius
     */
    public static ArrayList<FakePlayer> getFakePlayersInRadius(Player p, double radius) {
        if (!getFakePlayer().containsKey(p.getName())) return new ArrayList<>();
        ArrayList<FakePlayer> list = new ArrayList<>();
        for (FakePlayer fakePlayer : getFakePlayer().get(p.getName())) {
            if (fakePlayer.getCurrentlocation().distanceSquared(p.getLocation()) <= radius * radius) {
                list.add(fakePlayer);
            }
        }
        return list;
    }

    /**
     * Gets a {@link FakePlayer} by its NMSEntity
     *
     * @param p    the {@link Player} who can see this {@link FakePlayer}
     * @param fake the NMSEntity of the {@link FakePlayer}
     * @return the {@link FakePlayer} with his NMSEntity
     * @throws NoSuchFakeEntityException when there is no {@link FakePlayer} with this {@link Object}
     */
    public static FakePlayer getFakePlayerByObject(Player p, Object fake) throws NoSuchFakeEntityException {
        if (!getFakePlayer().containsKey(p.getName())) return null;
        for (FakePlayer fakePlayer : getFakePlayer().get(p.getName())) {
            if (fakePlayer.getNmsEntity() == fake) return fakePlayer;
        }
        return null;
    }

    /**
     * Gets a {@link FakePlayer} by its {@link Location}
     *
     * @param p   the {@link Player} who can see this {@link FakePlayer}
     * @param loc the {@link Location} of the {@link FakePlayer}
     * @return the {@link FakePlayer} at his {@link Location}
     * @throws NoSuchFakeEntityException when there is no {@link FakePlayer} at this {@link Location}
     */
    public static FakePlayer getFakePlayerByLocation(Player p, Location loc) throws NoSuchFakeEntityException {
        if (!getFakePlayer().containsKey(p.getName())) return null;
        for (FakePlayer fakePlayer : getFakePlayer().get(p.getName())) {
            if ((loc.getBlockX() == fakePlayer.getCurrentlocation().getBlockX()
                    && loc.getBlockY() == fakePlayer.getCurrentlocation().getBlockY()
                    && loc.getBlockZ() == fakePlayer.getCurrentlocation().getBlockZ()
                    || (loc.getBlockX() == fakePlayer.getCurrentlocation().getBlockX()
                    && loc.getBlockY() == (fakePlayer.getCurrentlocation().getBlockY() + 1)
                    && loc.getBlockZ() == fakePlayer.getCurrentlocation().getBlockZ()))) {
                return fakePlayer;
            }
        }
        return null;
    }

    /**
     * Gets a {@link HashMap} with all {@link Player}s and which {@link FakeArmorstand}s they can see
     *
     * @return a {@link HashMap} with all {@link Player}s and which {@link FakeArmorstand}s they can see
     */
    public static HashMap<String, ArrayList<FakeArmorstand>> getFakeArmorstand() {
        return fakeArmorstands;
    }

    /**
     * Adds a new {@link FakeArmorstand} for a specific {@link Player}
     *
     * @param p    the {@link Player} to add the {@link FakeArmorstand} for
     * @param fake the {@link FakeArmorstand} which should be added
     */
    public static void addFakeArmorstand(Player p, FakeArmorstand fake) {
        ArrayList<FakeArmorstand> list;
        if (getFakeArmorstand().containsKey(p.getName())) {
            list = getFakeArmorstand().get(p.getName());
            list.add(fake);
        } else {
            list = new ArrayList<>();
            list.add(fake);
        }
        getFakeArmorstand().put(p.getName(), list);
    }

    /**
     * Removes a {@link FakeArmorstand} for a specific {@link Player}
     *
     * @param p    the {@link Player} to remove the {@link FakeArmorstand} for
     * @param fake the {@link FakeArmorstand} which should be removed
     */
    public static void removeFakeArmorstand(Player p, FakeArmorstand fake) {
        ArrayList<FakeArmorstand> list;
        if (getFakeArmorstand().containsKey(p.getName())) {
            list = getFakeArmorstand().get(p.getName());
            list.remove(fake);
        } else {
            list = new ArrayList<>();
        }
        getFakeArmorstand().put(p.getName(), list);
    }

    /**
     * Checks if a specific {@link Player} looks at a {@link FakeArmorstand} in a certain range
     *
     * @param p     the {@link Player} to check for
     * @param range the range in which it should be checked
     * @return whether or not the {@link Player} looks at a {@link FakeArmorstand}
     */
    public static boolean isFakeArmorstandInRange(Player p, int range) {
        if (!getFakeArmorstand().containsKey(p.getName())) return false;
        for (Block b : p.getLineOfSight((Set<Material>) null, range)) {
            for (FakeArmorstand fakeArmorstand : getFakeArmorstand().get(p.getName())) {
                if ((b.getX() == fakeArmorstand.getCurrentlocation().getBlockX()
                        && b.getY() == fakeArmorstand.getCurrentlocation().getBlockY()
                        && b.getZ() == fakeArmorstand.getCurrentlocation().getBlockZ()
                        || (b.getX() == fakeArmorstand.getCurrentlocation().getBlockX()
                        && b.getY() == (fakeArmorstand.getCurrentlocation().getBlockY() + 1)
                        && b.getZ() == fakeArmorstand.getCurrentlocation().getBlockZ())))
                    return true;
            }
        }
        return false;
    }

    /**
     * Gets the {@link FakeArmorstand} at which the {@link Player} is currently looking at
     *
     * @param p     the {@link Player} which looks at the {@link FakeArmorstand}
     * @param range the range in which it should stand
     * @return the {@link FakeArmorstand} inside the range
     * @throws NoSuchFakeEntityException when there is no {@link FakeArmorstand} in range
     */
    public static FakeArmorstand getLookedAtFakeArmorstand(Player p, int range) throws NoSuchFakeEntityException {
        if (!getFakeArmorstand().containsKey(p.getName())) return null;
        for (Block b : p.getLineOfSight((Set<Material>) null, range)) {
            for (FakeArmorstand fakeArmorstand : getFakeArmorstand().get(p.getName())) {
                if ((b.getX() == fakeArmorstand.getCurrentlocation().getBlockX()
                        && b.getY() == fakeArmorstand.getCurrentlocation().getBlockY()
                        && b.getZ() == fakeArmorstand.getCurrentlocation().getBlockZ()
                        || (b.getX() == fakeArmorstand.getCurrentlocation().getBlockX()
                        && b.getY() == (fakeArmorstand.getCurrentlocation().getBlockY() + 1)
                        && b.getZ() == fakeArmorstand.getCurrentlocation().getBlockZ())))
                    return fakeArmorstand;
            }
        }
        return null;
    }

    /**
     * Gets a {@link ArrayList} with all {@link FakeArmorstand} inside a certain radius
     *
     * @param p      the {@link Player} for which it should search for
     * @param radius the radius in which should be searched for
     * @return a {@link ArrayList} with all {@link FakeArmorstand} inside the radius
     */
    public static ArrayList<FakeArmorstand> getFakeArmorstandsInRadius(Player p, double radius) {
        if (!getFakeArmorstand().containsKey(p.getName())) return new ArrayList<>();
        ArrayList<FakeArmorstand> list = new ArrayList<>();
        for (FakeArmorstand fakeArmorstand : getFakeArmorstand().get(p.getName())) {
            if (fakeArmorstand.getCurrentlocation().distanceSquared(p.getLocation()) <= radius * radius) {
                list.add(fakeArmorstand);
            }
        }
        return list;
    }

    /**
     * Gets a {@link FakeArmorstand} by its NMSEntity
     *
     * @param p    the {@link Player} who can see this {@link FakeArmorstand}
     * @param fake the NMSEntity of the {@link FakeArmorstand}
     * @return the {@link FakeArmorstand} with his NMSEntity
     * @throws NoSuchFakeEntityException when there is no {@link FakeArmorstand} with that {@link Object}
     */
    public static FakeArmorstand getFakeArmorstandByObject(Player p, Object fake) throws NoSuchFakeEntityException {
        if (!getFakeArmorstand().containsKey(p.getName())) return null;
        for (FakeArmorstand fakeArmorstand : getFakeArmorstand().get(p.getName())) {
            if (fakeArmorstand.getNmsEntity() == fake) return fakeArmorstand;
        }
        return null;
    }

    /**
     * Gets a {@link FakeArmorstand} by its {@link Location}
     *
     * @param p   the {@link Player} who can see this {@link FakeArmorstand}
     * @param loc the {@link Location} of the {@link FakeArmorstand}
     * @return the {@link FakeArmorstand} at his {@link Location}
     * @throws NoSuchFakeEntityException when there is no {@link FakeArmorstand} at this {@link Location}
     */
    public static FakeArmorstand getFakeArmorstandByLocation(Player p, Location loc) throws NoSuchFakeEntityException {
        if (!getFakeArmorstand().containsKey(p.getName())) return null;
        for (FakeArmorstand fakeArmorstand : getFakeArmorstand().get(p.getName())) {
            if ((loc.getBlockX() == fakeArmorstand.getCurrentlocation().getBlockX()
                    && loc.getBlockY() == fakeArmorstand.getCurrentlocation().getBlockY()
                    && loc.getBlockZ() == fakeArmorstand.getCurrentlocation().getBlockZ()
                    || (loc.getBlockX() == fakeArmorstand.getCurrentlocation().getBlockX()
                    && loc.getBlockY() == (fakeArmorstand.getCurrentlocation().getBlockY() + 1)
                    && loc.getBlockZ() == fakeArmorstand.getCurrentlocation().getBlockZ()))) {
                return fakeArmorstand;
            }
        }
        return null;
    }

    /**
     * Gets a {@link HashMap} with all {@link Player}s and which {@link FakeEndercrystal}s they can see
     *
     * @return a {@link HashMap} with all {@link Player}s and which {@link FakeEndercrystal}s they can see
     */
    public static HashMap<String, ArrayList<FakeEndercrystal>> getFakeEndercrystal() {
        return fakeEndercrystals;
    }

    /**
     * Adds a new {@link FakeEndercrystal} for a specific {@link Player}
     *
     * @param p    the {@link Player} to add the {@link FakeEndercrystal} for
     * @param fake the {@link FakeEndercrystal} which should be added
     */
    public static void addFakeEndercrystal(Player p, FakeEndercrystal fake) {
        ArrayList<FakeEndercrystal> list;
        if (getFakeEndercrystal().containsKey(p.getName())) {
            list = getFakeEndercrystal().get(p.getName());
            list.add(fake);
        } else {
            list = new ArrayList<>();
            list.add(fake);
        }
        getFakeEndercrystal().put(p.getName(), list);
    }

    /**
     * Removes a {@link FakeEndercrystal} for a specific {@link Player}
     *
     * @param p    the {@link Player} to remove the {@link FakeEndercrystal} for
     * @param fake the {@link FakeEndercrystal} which should be removed
     */
    public static void removeFakeEndercrystal(Player p, FakeEndercrystal fake) {
        ArrayList<FakeEndercrystal> list;
        if (getFakeEndercrystal().containsKey(p.getName())) {
            list = getFakeEndercrystal().get(p.getName());
            list.remove(fake);
        } else {
            list = new ArrayList<>();
        }
        getFakeEndercrystal().put(p.getName(), list);
    }

    /**
     * Checks if a specific {@link Player} looks at a {@link FakeEndercrystal} in a certain range
     *
     * @param p     the {@link Player} to check for
     * @param range the range in which it should be checked
     * @return whether or not the {@link Player} looks at a {@link FakeEndercrystal}
     */
    public static boolean isFakeEndercrystalInRange(Player p, int range) {
        if (!getFakeEndercrystal().containsKey(p.getName())) return false;
        for (Block b : p.getLineOfSight((Set<Material>) null, range)) {
            for (FakeEndercrystal fakeEndercrystal : getFakeEndercrystal().get(p.getName())) {
                if ((b.getX() == fakeEndercrystal.getCurrentlocation().getBlockX()
                        && b.getY() == fakeEndercrystal.getCurrentlocation().getBlockY()
                        && b.getZ() == fakeEndercrystal.getCurrentlocation().getBlockZ()
                        || (b.getX() == fakeEndercrystal.getCurrentlocation().getBlockX()
                        && b.getY() == (fakeEndercrystal.getCurrentlocation().getBlockY() + 1)
                        && b.getZ() == fakeEndercrystal.getCurrentlocation().getBlockZ())
                        || (b.getX() == fakeEndercrystal.getCurrentlocation().getBlockX()
                        && b.getY() == (fakeEndercrystal.getCurrentlocation().getBlockY() - 1)
                        && b.getZ() == fakeEndercrystal.getCurrentlocation().getBlockZ())))
                    return true;
            }
        }
        return false;
    }

    /**
     * Gets the {@link FakeEndercrystal} at which the {@link Player} is currently looking at
     *
     * @param p     the {@link Player} which looks at the {@link FakeEndercrystal}
     * @param range the range in which it should stand
     * @return the {@link FakeEndercrystal} inside the range
     * @throws NoSuchFakeEntityException when there is no {@link FakeEndercrystal} in the range
     */
    public static FakeEndercrystal getLookedAtFakeEndercrystal(Player p, int range) throws NoSuchFakeEntityException {
        if (!getFakeEndercrystal().containsKey(p.getName())) return null;
        for (Block b : p.getLineOfSight((Set<Material>) null, range)) {
            for (FakeEndercrystal fakeEndercrystal : getFakeEndercrystal().get(p.getName())) {
                if ((b.getX() == fakeEndercrystal.getCurrentlocation().getBlockX()
                        && b.getY() == fakeEndercrystal.getCurrentlocation().getBlockY()
                        && b.getZ() == fakeEndercrystal.getCurrentlocation().getBlockZ()
                        || (b.getX() == fakeEndercrystal.getCurrentlocation().getBlockX()
                        && b.getY() == (fakeEndercrystal.getCurrentlocation().getBlockY() - 1)
                        && b.getZ() == fakeEndercrystal.getCurrentlocation().getBlockZ())
                        || (b.getX() == fakeEndercrystal.getCurrentlocation().getBlockX()
                        && b.getY() == (fakeEndercrystal.getCurrentlocation().getBlockY() + 1)
                        && b.getZ() == fakeEndercrystal.getCurrentlocation().getBlockZ())))
                    return fakeEndercrystal;
            }
        }
        return null;
    }

    /**
     * Gets a {@link ArrayList} with all {@link FakeEndercrystal} inside a certain radius
     *
     * @param p      the {@link Player} for which it should search for
     * @param radius the radius in which should be searched for
     * @return a {@link ArrayList} with all {@link FakeEndercrystal} inside the radius
     */
    public static ArrayList<FakeEndercrystal> getFakeEndercrystalsInRadius(Player p, double radius) {
        if (!getFakeEndercrystal().containsKey(p.getName())) return new ArrayList<>();
        ArrayList<FakeEndercrystal> list = new ArrayList<>();
        for (FakeEndercrystal fakeEndercrystal : getFakeEndercrystal().get(p.getName())) {
            if (fakeEndercrystal.getCurrentlocation().distanceSquared(p.getLocation()) <= radius * radius) {
                list.add(fakeEndercrystal);
            }
        }
        return list;
    }

    /**
     * Gets a {@link FakeEndercrystal} by its NMSEntity
     *
     * @param p    the {@link Player} who can see this {@link FakeEndercrystal}
     * @param fake the NMSEntity of the {@link FakeEndercrystal}
     * @return the {@link FakeEndercrystal} with his NMSEntity
     * @throws NoSuchFakeEntityException when there is no {@link FakeEndercrystal} with this Object
     */
    public static FakeEndercrystal getFakeEndercrystalByObject(Player p, Object fake) throws NoSuchFakeEntityException {
        if (!getFakeEndercrystal().containsKey(p.getName())) return null;
        for (FakeEndercrystal fakeEndercrystal : getFakeEndercrystal().get(p.getName())) {
            if (fakeEndercrystal.getNmsEntity() == fake) return fakeEndercrystal;
        }
        return null;
    }

    /**
     * Gets a {@link FakeEndercrystal} by its {@link Location}
     *
     * @param p   the {@link Player} who can see this {@link FakeEndercrystal}
     * @param loc the {@link Location} of the {@link FakeEndercrystal}
     * @return the {@link FakeEndercrystal} at his {@link Location}
     * @throws NoSuchFakeEntityException when there is no {@link FakeEndercrystal} at this {@link Location}
     */
    public static FakeEndercrystal getFakeEndercrystalByLocation(Player p, Location loc) throws NoSuchFakeEntityException {
        if (!getFakeEndercrystal().containsKey(p.getName())) return null;
        for (FakeEndercrystal fakeEndercrystal : getFakeEndercrystal().get(p.getName())) {
            if ((loc.getBlockX() == fakeEndercrystal.getCurrentlocation().getBlockX()
                    && loc.getBlockY() == fakeEndercrystal.getCurrentlocation().getBlockY()
                    && loc.getBlockZ() == fakeEndercrystal.getCurrentlocation().getBlockZ()
                    || (loc.getBlockX() == fakeEndercrystal.getCurrentlocation().getBlockX()
                    && loc.getBlockY() == (fakeEndercrystal.getCurrentlocation().getBlockY() + 1)
                    && loc.getBlockZ() == fakeEndercrystal.getCurrentlocation().getBlockZ()))) {
                return fakeEndercrystal;
            }
        }
        return null;
    }

    /**
     * Gets a {@link HashMap} with all {@link Player}s and which {@link FakeItem}s they can see
     *
     * @return a {@link HashMap} with all {@link Player}s and which {@link FakeItem}s they can see
     */
    public static HashMap<String, ArrayList<FakeItem>> getFakeItem() {
        return fakeItems;
    }

    /**
     * Adds a new {@link FakeItem} for a specific {@link Player}
     *
     * @param p    the {@link Player} to add the {@link FakeItem} for
     * @param fake the {@link FakeItem} which should be added
     */
    public static void addFakeItem(Player p, FakeItem fake) {
        ArrayList<FakeItem> list;
        if (getFakeItem().containsKey(p.getName())) {
            list = getFakeItem().get(p.getName());
            list.add(fake);
        } else {
            list = new ArrayList<>();
            list.add(fake);
        }
        getFakeItem().put(p.getName(), list);
    }

    /**
     * Removes a {@link FakeItem} for a specific {@link Player}
     *
     * @param p    the {@link Player} to remove the {@link FakeItem} for
     * @param fake the {@link FakeItem} which should be removed
     */
    public static void removeFakeItem(Player p, FakeItem fake) {
        ArrayList<FakeItem> list;
        if (getFakeItem().containsKey(p.getName())) {
            list = getFakeItem().get(p.getName());
            list.remove(fake);
        } else {
            list = new ArrayList<>();
        }
        getFakeItem().put(p.getName(), list);
    }

    /**
     * Checks if a specific {@link Player} looks at a {@link FakeItem} in a certain range
     *
     * @param p     the {@link Player} to check for
     * @param range the range in which it should be checked
     * @return whether or not the {@link Player} looks at a {@link FakeItem}
     */
    public static boolean isFakeItemInRange(Player p, int range) {
        if (!getFakeItem().containsKey(p.getName())) return false;
        for (Block b : p.getLineOfSight((Set<Material>) null, range)) {
            for (FakeItem fakeItem : getFakeItem().get(p.getName())) {
                if ((b.getX() == fakeItem.getCurrentlocation().getBlockX()
                        && b.getY() == fakeItem.getCurrentlocation().getBlockY()
                        && b.getZ() == fakeItem.getCurrentlocation().getBlockZ()
                        || (b.getX() == fakeItem.getCurrentlocation().getBlockX()
                        && b.getY() == (fakeItem.getCurrentlocation().getBlockY() + 1)
                        && b.getZ() == fakeItem.getCurrentlocation().getBlockZ())))
                    return true;
            }
        }
        return false;
    }

    /**
     * Gets the {@link FakeItem} at which the {@link Player} is currently looking at
     *
     * @param p     the {@link Player} which looks at the {@link FakeItem}
     * @param range the range in which it should stand
     * @return the {@link FakeItem} inside the range
     * @throws NoSuchFakeEntityException when there is no {@link FakeItem} in the range
     */
    public static FakeItem getFakeItemInRange(Player p, int range) throws NoSuchFakeEntityException {
        if (!getFakeItem().containsKey(p.getName())) return null;
        for (Block b : p.getLineOfSight((Set<Material>) null, range)) {
            for (FakeItem fakeItem : getFakeItem().get(p.getName())) {
                if ((b.getX() == fakeItem.getCurrentlocation().getBlockX()
                        && b.getY() == fakeItem.getCurrentlocation().getBlockY()
                        && b.getZ() == fakeItem.getCurrentlocation().getBlockZ()
                        || (b.getX() == fakeItem.getCurrentlocation().getBlockX()
                        && b.getY() == (fakeItem.getCurrentlocation().getBlockY() + 1)
                        && b.getZ() == fakeItem.getCurrentlocation().getBlockZ())))
                    return fakeItem;
            }
        }
        return null;
    }

    /**
     * Gets a {@link ArrayList} with all {@link FakeItem} inside a certain radius
     *
     * @param p      the {@link Player} for which it should search for
     * @param radius the radius in which should be searched for
     * @return a {@link ArrayList} with all {@link FakeItem} inside the radius
     */
    public static ArrayList<FakeItem> getFakeItemsInRadius(Player p, double radius) {
        if (!getFakeItem().containsKey(p.getName())) return new ArrayList<>();
        ArrayList<FakeItem> list = new ArrayList<>();
        for (FakeItem fakeItem : getFakeItem().get(p.getName())) {
            if (fakeItem.getCurrentlocation().distanceSquared(p.getLocation()) <= radius * radius) {
                list.add(fakeItem);
            }
        }
        return list;
    }

    /**
     * Gets a {@link FakeItem} by its NMSEntity
     *
     * @param p    the {@link Player} who can see this {@link FakeItem}
     * @param fake the NMSEntity of the {@link FakeItem}
     * @return the {@link FakeItem} with his NMSEntity
     * @throws NoSuchFakeEntityException when there is no {@link FakeItem} with this {@link Object}
     */
    public static FakeItem getFakeItemByObject(Player p, Object fake) throws NoSuchFakeEntityException {
        if (!getFakeItem().containsKey(p.getName())) return null;
        for (FakeItem fakeItem : getFakeItem().get(p.getName())) {
            if (fakeItem.getNmsEntity() == fake) return fakeItem;
        }
        return null;
    }

    /**
     * Gets a {@link FakeItem} by its {@link Location}
     *
     * @param p   the {@link Player} who can see this {@link FakeItem}
     * @param loc the {@link Location} of the {@link FakeItem}
     * @return the {@link FakeItem} at his {@link Location}
     * @throws NoSuchFakeEntityException when there is no {@link FakeItem} at this {@link Location}
     */
    public static FakeItem getFakeItemByLocation(Player p, Location loc) throws NoSuchFakeEntityException {
        if (!getFakeItem().containsKey(p.getName())) return null;
        for (FakeItem fakeItem : getFakeItem().get(p.getName())) {
            if ((loc.getBlockX() == fakeItem.getCurrentlocation().getBlockX()
                    && loc.getBlockY() == fakeItem.getCurrentlocation().getBlockY()
                    && loc.getBlockZ() == fakeItem.getCurrentlocation().getBlockZ()
                    || (loc.getBlockX() == fakeItem.getCurrentlocation().getBlockX()
                    && loc.getBlockY() == (fakeItem.getCurrentlocation().getBlockY() + 1)
                    && loc.getBlockZ() == fakeItem.getCurrentlocation().getBlockZ()))) {
                return fakeItem;
            }
        }
        return null;
    }

    /**
     * Gets a {@link HashMap} with all {@link Player}s and which {@link FakeMob}s they can see
     *
     * @return a {@link HashMap} with all {@link Player}s and which {@link FakeMob}s they can see
     */
    public static HashMap<String, ArrayList<FakeMob>> getFakeMob() {
        return fakeMobs;
    }

    /**
     * Adds a new {@link FakeMob} for a specific {@link Player}
     *
     * @param p    the {@link Player} to add the {@link FakeMob} for
     * @param fake the {@link FakeMob} which should be added
     */
    public static void addFakeMob(Player p, FakeMob fake) {
        ArrayList<FakeMob> list;
        if (getFakeMob().containsKey(p.getName())) {
            list = getFakeMob().get(p.getName());
            list.add(fake);
        } else {
            list = new ArrayList<>();
            list.add(fake);
        }
        getFakeMob().put(p.getName(), list);
    }

    /**
     * Removes a {@link FakeMob} for a specific {@link Player}
     *
     * @param p    the {@link Player} to remove the {@link FakeMob} for
     * @param fake the {@link FakeMob} which should be removed
     */
    public static void removeFakeMob(Player p, FakeMob fake) {
        ArrayList<FakeMob> list;
        if (getFakeMob().containsKey(p.getName())) {
            list = getFakeMob().get(p.getName());
            list.remove(fake);
        } else {
            list = new ArrayList<>();
        }
        getFakeMob().put(p.getName(), list);
    }

    /**
     * Checks if a specific {@link Player} looks at a {@link FakeMob} in a certain range
     *
     * @param p     the {@link Player} to check for
     * @param range the range in which it should be checked
     * @return whether or not the {@link Player} looks at a {@link FakeMob}
     */
    public static boolean isFakeMobInRange(Player p, int range) {
        if (!getFakeMob().containsKey(p.getName())) return false;
        for (Block b : p.getLineOfSight((Set<Material>) null, range)) {
            for (FakeMob fakeMob : getFakeMob().get(p.getName())) {
                if ((b.getX() == fakeMob.getCurrentlocation().getBlockX()
                        && b.getY() == fakeMob.getCurrentlocation().getBlockY()
                        && b.getZ() == fakeMob.getCurrentlocation().getBlockZ()
                        || (b.getX() == fakeMob.getCurrentlocation().getBlockX()
                        && b.getY() == (fakeMob.getCurrentlocation().getBlockY() + 1)
                        && b.getZ() == fakeMob.getCurrentlocation().getBlockZ())))
                    return true;
            }
        }
        return false;
    }

    /**
     * Gets the {@link FakeMob} at which the {@link Player} is currently looking at
     *
     * @param p     the {@link Player} which looks at the {@link FakeMob}
     * @param range the range in which it should stand
     * @return the {@link FakeMob} inside the range
     * @throws NoSuchFakeEntityException when there is no {@link FakeMob} in range
     */
    public static FakeMob getLookedAtFakeMob(Player p, int range) throws NoSuchFakeEntityException {
        if (!getFakeMob().containsKey(p.getName())) return null;
        for (Block b : p.getLineOfSight((Set<Material>) null, range)) {
            for (FakeMob fakeMob : getFakeMob().get(p.getName())) {
                if ((b.getX() == fakeMob.getCurrentlocation().getBlockX()
                        && b.getY() == fakeMob.getCurrentlocation().getBlockY()
                        && b.getZ() == fakeMob.getCurrentlocation().getBlockZ()
                        || (b.getX() == fakeMob.getCurrentlocation().getBlockX()
                        && b.getY() == (fakeMob.getCurrentlocation().getBlockY() + 1)
                        && b.getZ() == fakeMob.getCurrentlocation().getBlockZ())))
                    return fakeMob;
            }
        }
        return null;
    }

    /**
     * Gets a {@link ArrayList} with all {@link FakeMob} inside a certain radius
     *
     * @param p      the {@link Player} for which it should search for
     * @param radius the radius in which should be searched for
     * @return a {@link ArrayList} with all {@link FakeMob} inside the radius
     */
    public static ArrayList<FakeMob> getFakeMobsInRadius(Player p, double radius) {
        if (!getFakeMob().containsKey(p.getName())) return new ArrayList<>();
        ArrayList<FakeMob> list = new ArrayList<>();
        for (FakeMob fakeMob : getFakeMob().get(p.getName())) {
            if (fakeMob.getCurrentlocation().distanceSquared(p.getLocation()) <= radius * radius) {
                list.add(fakeMob);
            }
        }
        return list;
    }

    /**
     * Gets a {@link FakeMob} by its NMSEntity
     *
     * @param p    the {@link Player} who can see this {@link FakeMob}
     * @param fake the NMSEntity of the {@link FakeMob}
     * @return the {@link FakeMob} with his NMSEntity
     * @throws NoSuchFakeEntityException when there is no {@link FakeMob} with this Object
     */
    public static FakeMob getFakeMobByObject(Player p, Object fake) throws NoSuchFakeEntityException {
        if (!getFakeMob().containsKey(p.getName())) return null;
        for (FakeMob fakeMob : getFakeMob().get(p.getName())) {
            if (fakeMob.getNmsEntity() == fake) return fakeMob;
        }
        return null;
    }

    /**
     * Gets a {@link FakeMob} by its {@link Location}
     *
     * @param p   the {@link Player} who can see this {@link FakeMob}
     * @param loc the {@link Location} of the {@link FakeMob}
     * @return the {@link FakeMob} at his {@link Location}
     * @throws NoSuchFakeEntityException when there is no {@link FakeMob} at this {@link Location}
     */
    public static FakeMob getFakeMobByLocation(Player p, Location loc) throws NoSuchFakeEntityException {
        if (!getFakeMob().containsKey(p.getName())) return null;
        for (FakeMob fakeMob : getFakeMob().get(p.getName())) {
            if ((loc.getBlockX() == fakeMob.getCurrentlocation().getBlockX()
                    && loc.getBlockY() == fakeMob.getCurrentlocation().getBlockY()
                    && loc.getBlockZ() == fakeMob.getCurrentlocation().getBlockZ()
                    || (loc.getBlockX() == fakeMob.getCurrentlocation().getBlockX()
                    && loc.getBlockY() == (fakeMob.getCurrentlocation().getBlockY() + 1)
                    && loc.getBlockZ() == fakeMob.getCurrentlocation().getBlockZ()))) {
                return fakeMob;
            }
        }
        return null;
    }

    /**
     * Gets a {@link HashMap} with all {@link Player}s and which {@link FakeBigItem}s they can see
     *
     * @return a {@link HashMap} with all {@link Player}s and which {@link FakeBigItem}s they can see
     */
    public static HashMap<String, ArrayList<FakeBigItem>> getFakeBigItem() {
        return fakeBigItems;
    }

    /**
     * Adds a new {@link FakeBigItem} for a specific {@link Player}
     *
     * @param p    the {@link Player} to add the {@link FakeBigItem} for
     * @param fake the {@link FakeBigItem} which should be added
     */
    public static void addFakeBigItem(Player p, FakeBigItem fake) {
        ArrayList<FakeBigItem> list;
        if (getFakeBigItem().containsKey(p.getName())) {
            list = getFakeBigItem().get(p.getName());
            list.add(fake);
        } else {
            list = new ArrayList<>();
            list.add(fake);
        }
        getFakeBigItem().put(p.getName(), list);
    }

    /**
     * Removes a {@link FakeBigItem} for a specific {@link Player}
     *
     * @param p    the {@link Player} to remove the {@link FakeBigItem} for
     * @param fake the {@link FakeBigItem} which should be removed
     */
    public static void removeFakeBigItem(Player p, FakeBigItem fake) {
        ArrayList<FakeBigItem> list;
        if (getFakeBigItem().containsKey(p.getName())) {
            list = getFakeBigItem().get(p.getName());
            list.remove(fake);
        } else {
            list = new ArrayList<>();
        }
        getFakeBigItem().put(p.getName(), list);
    }

    /**
     * Checks if a specific {@link Player} looks at a {@link FakeBigItem} in a certain range
     *
     * @param p     the {@link Player} to check for
     * @param range the range in which it should be checked
     * @return whether or not the {@link Player} looks at a {@link FakeBigItem}
     */
    public static boolean isFakeBigItemInRange(Player p, int range) {
        if (!getFakeBigItem().containsKey(p.getName())) return false;
        for (Block b : p.getLineOfSight((Set<Material>) null, range)) {
            for (FakeBigItem fakeBigItem : getFakeBigItem().get(p.getName())) {
                if ((b.getX() == fakeBigItem.getCurrentlocation().getBlockX()
                        && b.getY() == fakeBigItem.getCurrentlocation().getBlockY()
                        && b.getZ() == fakeBigItem.getCurrentlocation().getBlockZ()
                        || (b.getX() == fakeBigItem.getCurrentlocation().getBlockX()
                        && b.getY() == (fakeBigItem.getCurrentlocation().getBlockY() + 1)
                        && b.getZ() == fakeBigItem.getCurrentlocation().getBlockZ())))
                    return true;
            }
        }
        return false;
    }

    /**
     * Gets the {@link FakeBigItem} at which the {@link Player} is currently looking at
     *
     * @param p     the {@link Player} which looks at the {@link FakeBigItem}
     * @param range the range in which it should stand
     * @return the {@link FakeBigItem} inside the range
     * @throws NoSuchFakeEntityException when there is no {@link FakeBigItem} in range
     */
    public static FakeBigItem getFakeBigItemInRange(Player p, int range) throws NoSuchFakeEntityException {
        if (!getFakeBigItem().containsKey(p.getName())) return null;
        for (Block b : p.getLineOfSight((Set<Material>) null, range)) {
            for (FakeBigItem fakeBigItem : getFakeBigItem().get(p.getName())) {
                if ((b.getX() == fakeBigItem.getCurrentlocation().getBlockX()
                        && b.getY() == fakeBigItem.getCurrentlocation().getBlockY()
                        && b.getZ() == fakeBigItem.getCurrentlocation().getBlockZ()
                        || (b.getX() == fakeBigItem.getCurrentlocation().getBlockX()
                        && b.getY() == (fakeBigItem.getCurrentlocation().getBlockY() + 1)
                        && b.getZ() == fakeBigItem.getCurrentlocation().getBlockZ())))
                    return fakeBigItem;
            }
        }
        return null;
    }

    /**
     * Gets a {@link ArrayList} with all {@link FakeBigItem} inside a certain radius
     *
     * @param p      the {@link Player} for which it should search for
     * @param radius the radius in which should be searched for
     * @return a {@link ArrayList} with all {@link FakeBigItem} inside the radius
     */
    public static ArrayList<FakeBigItem> getFakeBigItemsInRadius(Player p, double radius) {
        if (!getFakeBigItem().containsKey(p.getName())) return new ArrayList<>();
        ArrayList<FakeBigItem> list = new ArrayList<>();
        for (FakeBigItem fakeBigItem : getFakeBigItem().get(p.getName())) {
            if (fakeBigItem.getCurrentlocation().distanceSquared(p.getLocation()) <= radius * radius) {
                list.add(fakeBigItem);
            }
        }
        return list;
    }

    /**
     * Gets a {@link FakeBigItem} by its NMSEntity
     *
     * @param p    the {@link Player} who can see this {@link FakeBigItem}
     * @param fake the NMSEntity of the {@link FakeBigItem}
     * @return the {@link FakeBigItem} with his NMSEntity
     * @throws NoSuchFakeEntityException when there is no {@link FakeBigItem} with this Object
     */
    public static FakeBigItem getFakeBigItemByObject(Player p, Object fake) throws NoSuchFakeEntityException {
        if (!getFakeBigItem().containsKey(p.getName())) return null;
        for (FakeBigItem fakeBigItem : getFakeBigItem().get(p.getName())) {
            if (fakeBigItem.getNmsEntity() == fake) return fakeBigItem;
        }
        return null;
    }

    /**
     * Gets a {@link FakeBigItem} by its {@link Location}
     *
     * @param p   the {@link Player} who can see this {@link FakeBigItem}
     * @param loc the {@link Location} of the {@link FakeBigItem}
     * @return the {@link FakeBigItem} at his {@link Location}
     * @throws NoSuchFakeEntityException when there is no {@link FakeBigItem} at this {@link Location}
     */
    public static FakeBigItem getFakeBigItemByLocation(Player p, Location loc) throws NoSuchFakeEntityException {
        if (!getFakeBigItem().containsKey(p.getName())) return null;
        for (FakeBigItem fakeBigItem : getFakeBigItem().get(p.getName())) {
            if ((loc.getBlockX() == fakeBigItem.getCurrentlocation().getBlockX()
                    && loc.getBlockY() == fakeBigItem.getCurrentlocation().getBlockY()
                    && loc.getBlockZ() == fakeBigItem.getCurrentlocation().getBlockZ()
                    || (loc.getBlockX() == fakeBigItem.getCurrentlocation().getBlockX()
                    && loc.getBlockY() == (fakeBigItem.getCurrentlocation().getBlockY() + 1)
                    && loc.getBlockZ() == fakeBigItem.getCurrentlocation().getBlockZ()))) {
                return fakeBigItem;
            }
        }
        return null;
    }

    /**
     * Gets a {@link HashMap} with all {@link Player}s and which {@link FakeXPOrb}s they can see
     *
     * @return a {@link HashMap} with all {@link Player}s and which {@link FakeXPOrb}s they can see
     */
    public static HashMap<String, ArrayList<FakeXPOrb>> getFakeXPOrb() {
        return fakeXPOrbs;
    }

    /**
     * Adds a new {@link FakeXPOrb} for a specific {@link Player}
     *
     * @param p    the {@link Player} to add the {@link FakeXPOrb} for
     * @param fake the {@link FakeXPOrb} which should be added
     */
    public static void addFakeXPOrb(Player p, FakeXPOrb fake) {
        ArrayList<FakeXPOrb> list;
        if (getFakeXPOrb().containsKey(p.getName())) {
            list = getFakeXPOrb().get(p.getName());
            list.add(fake);
        } else {
            list = new ArrayList<>();
            list.add(fake);
        }
        getFakeXPOrb().put(p.getName(), list);
    }

    /**
     * Removes a {@link FakeXPOrb} for a specific {@link Player}
     *
     * @param p    the {@link Player} to remove the {@link FakeXPOrb} for
     * @param fake the {@link FakeXPOrb} which should be removed
     */
    public static void removeFakeXPOrb(Player p, FakeXPOrb fake) {
        ArrayList<FakeXPOrb> list;
        if (getFakeXPOrb().containsKey(p.getName())) {
            list = getFakeXPOrb().get(p.getName());
            list.remove(fake);
        } else {
            list = new ArrayList<>();
        }
        getFakeXPOrb().put(p.getName(), list);
    }

    /**
     * Checks if a specific {@link Player} looks at a {@link FakeXPOrb} in a certain range
     *
     * @param p     the {@link Player} to check for
     * @param range the range in which it should be checked
     * @return
     */
    public static boolean isFakeXPOrbInRange(Player p, int range) {
        if (!getFakeXPOrb().containsKey(p.getName())) return false;
        for (Block b : p.getLineOfSight((Set<Material>) null, range)) {
            for (FakeXPOrb fakeXPOrb : getFakeXPOrb().get(p.getName())) {
                if ((b.getX() == fakeXPOrb.getCurrentlocation().getBlockX()
                        && b.getY() == fakeXPOrb.getCurrentlocation().getBlockY()
                        && b.getZ() == fakeXPOrb.getCurrentlocation().getBlockZ()
                        || (b.getX() == fakeXPOrb.getCurrentlocation().getBlockX()
                        && b.getY() == (fakeXPOrb.getCurrentlocation().getBlockY() + 1)
                        && b.getZ() == fakeXPOrb.getCurrentlocation().getBlockZ())))
                    return true;
            }
        }
        return false;
    }

    /**
     * Gets the {@link FakeXPOrb} at which the {@link Player} is currently looking at
     *
     * @param p     the {@link Player} which looks at the {@link FakeXPOrb}
     * @param range the range in which it should stand
     * @return the {@link FakeXPOrb} inside the range
     * @throws NullPointerException
     */
    public static FakeXPOrb getFakeXPOrbInRange(Player p, int range) throws NullPointerException {
        if (!getFakeXPOrb().containsKey(p.getName())) return null;
        for (Block b : p.getLineOfSight((Set<Material>) null, range)) {
            for (FakeXPOrb fakeXPOrb : getFakeXPOrb().get(p.getName())) {
                if ((b.getX() == fakeXPOrb.getCurrentlocation().getBlockX()
                        && b.getY() == fakeXPOrb.getCurrentlocation().getBlockY()
                        && b.getZ() == fakeXPOrb.getCurrentlocation().getBlockZ()
                        || (b.getX() == fakeXPOrb.getCurrentlocation().getBlockX()
                        && b.getY() == (fakeXPOrb.getCurrentlocation().getBlockY() + 1)
                        && b.getZ() == fakeXPOrb.getCurrentlocation().getBlockZ())))
                    return fakeXPOrb;
            }
        }
        return null;
    }

    /**
     * Gets a {@link ArrayList} with all {@link FakeXPOrb} inside a certain radius
     *
     * @param p      the {@link Player} for which it should search for
     * @param radius the radius in which should be searched for
     * @return a {@link ArrayList} with all {@link FakeXPOrb} inside the radius {@param radius}
     */
    public static ArrayList<FakeXPOrb> getFakeXPOrbsInRadius(Player p, double radius) {
        if (!getFakeXPOrb().containsKey(p.getName())) return new ArrayList<>();
        ArrayList<FakeXPOrb> list = new ArrayList<>();
        for (FakeXPOrb fakeXPOrb : getFakeXPOrb().get(p.getName())) {
            if (fakeXPOrb.getCurrentlocation().distanceSquared(p.getLocation()) <= radius * radius) {
                list.add(fakeXPOrb);
            }
        }
        return list;
    }

    /**
     * Gets a {@link FakeXPOrb} by its NMSEntity
     *
     * @param p    the {@link Player} who can see this {@link FakeXPOrb}
     * @param fake the NMSEntity of the {@link FakeXPOrb}
     * @return the {@link FakeXPOrb} with his NMSEntity
     * @throws NullPointerException
     */
    public static FakeXPOrb getFakeXPOrbByObject(Player p, Object fake) throws NullPointerException {
        if (!getFakeXPOrb().containsKey(p.getName())) return null;
        for (FakeXPOrb fakeXPOrb : getFakeXPOrb().get(p.getName())) {
            if (fakeXPOrb.getNmsEntity() == fake) return fakeXPOrb;
        }
        return null;
    }

    /**
     * Gets a {@link FakeXPOrb} by its {@link Location}
     *
     * @param p   the {@link Player} who can see this {@link FakeXPOrb}
     * @param loc the {@link Location} of the {@link FakeXPOrb}
     * @return the {@link FakeXPOrb} at his {@link Location}
     * @throws NullPointerException
     */
    public static FakeXPOrb getFakeXPOrbByLocation(Player p, Location loc) throws NullPointerException {
        if (!getFakeXPOrb().containsKey(p.getName())) return null;
        for (FakeXPOrb fakeXPOrb : getFakeXPOrb().get(p.getName())) {
            if ((loc.getBlockX() == fakeXPOrb.getCurrentlocation().getBlockX()
                    && loc.getBlockY() == fakeXPOrb.getCurrentlocation().getBlockY()
                    && loc.getBlockZ() == fakeXPOrb.getCurrentlocation().getBlockZ()
                    || (loc.getBlockX() == fakeXPOrb.getCurrentlocation().getBlockX()
                    && loc.getBlockY() == (fakeXPOrb.getCurrentlocation().getBlockY() + 1)
                    && loc.getBlockZ() == fakeXPOrb.getCurrentlocation().getBlockZ()))) {
                return fakeXPOrb;
            }
        }
        return null;
    }

    /**
     * Gets a {@link HashMap} with all {@link Player}s and which {@link FakePainting}s they can see
     *
     * @return a {@link HashMap} with all {@link Player}s and which {@link FakePainting}s they can see
     */
    public static HashMap<String, ArrayList<FakePainting>> getFakePainting() {
        return fakePaintings;
    }

    /**
     * Adds a new {@link FakePainting} for a specific {@link Player}
     *
     * @param p    the {@link Player} to add the {@link FakePainting} for
     * @param fake the {@link FakePainting} which should be added
     */
    public static void addFakePainting(Player p, FakePainting fake) {
        ArrayList<FakePainting> list;
        if (getFakePainting().containsKey(p.getName())) {
            list = getFakePainting().get(p.getName());
            list.add(fake);
        } else {
            list = new ArrayList<>();
            list.add(fake);
        }
        getFakePainting().put(p.getName(), list);
    }

    /**
     * Removes a {@link FakePainting} for a specific {@link Player}
     *
     * @param p    the {@link Player} to remove the {@link FakePainting} for
     * @param fake the {@link FakePainting} which should be removed
     */
    public static void removeFakePainting(Player p, FakePainting fake) {
        ArrayList<FakePainting> list;
        if (getFakePainting().containsKey(p.getName())) {
            list = getFakePainting().get(p.getName());
            list.remove(fake);
        } else {
            list = new ArrayList<>();
        }
        getFakePainting().put(p.getName(), list);
    }

    /**
     * Checks if a specific {@link Player} looks at a {@link FakePainting} in a certain range
     *
     * @param p     the {@link Player} to check for
     * @param range the range in which it should be checked
     * @return
     */
    public static boolean isFakePaintingInRange(Player p, int range) {
        if (!getFakePainting().containsKey(p.getName())) return false;
        for (Block b : p.getLineOfSight((Set<Material>) null, range)) {
            for (FakePainting fakePainting : getFakePainting().get(p.getName())) {
                if ((b.getX() == fakePainting.getCurrentlocation().getBlockX()
                        && b.getY() == fakePainting.getCurrentlocation().getBlockY()
                        && b.getZ() == fakePainting.getCurrentlocation().getBlockZ()
                        || (b.getX() == fakePainting.getCurrentlocation().getBlockX()
                        && b.getY() == (fakePainting.getCurrentlocation().getBlockY() + 1)
                        && b.getZ() == fakePainting.getCurrentlocation().getBlockZ())))
                    return true;
            }
        }
        return false;
    }

    /**
     * Gets the {@link FakePainting} at which the {@link Player} is currently looking at
     *
     * @param p     the {@link Player} which looks at the {@link FakePainting}
     * @param range the range in which it should stand
     * @return the {@link FakePainting} inside the range
     * @throws NullPointerException
     */
    public static FakePainting getFakePaintingInRange(Player p, int range) throws NullPointerException {
        if (!getFakePainting().containsKey(p.getName())) return null;
        for (Block b : p.getLineOfSight((Set<Material>) null, range)) {
            for (FakePainting fakePainting : getFakePainting().get(p.getName())) {
                if ((b.getX() == fakePainting.getCurrentlocation().getBlockX()
                        && b.getY() == fakePainting.getCurrentlocation().getBlockY()
                        && b.getZ() == fakePainting.getCurrentlocation().getBlockZ()
                        || (b.getX() == fakePainting.getCurrentlocation().getBlockX()
                        && b.getY() == (fakePainting.getCurrentlocation().getBlockY() + 1)
                        && b.getZ() == fakePainting.getCurrentlocation().getBlockZ())))
                    return fakePainting;
            }
        }
        return null;
    }

    /**
     * Gets a {@link ArrayList} with all {@link FakePainting} inside a certain radius
     *
     * @param p      the {@link Player} for which it should search for
     * @param radius the radius in which should be searched for
     * @return a {@link ArrayList} with all {@link FakePainting} inside the radius {@param radius}
     */
    public static ArrayList<FakePainting> getFakePaintingsInRadius(Player p, double radius) {
        if (!getFakePainting().containsKey(p.getName())) return new ArrayList<>();
        ArrayList<FakePainting> list = new ArrayList<>();
        for (FakePainting fakePainting : getFakePainting().get(p.getName())) {
            if (fakePainting.getCurrentlocation().distanceSquared(p.getLocation()) <= radius * radius) {
                list.add(fakePainting);
            }
        }
        return list;
    }

    /**
     * Gets a {@link FakePainting} by its NMSEntity
     *
     * @param p    the {@link Player} who can see this {@link FakePainting}
     * @param fake the NMSEntity of the {@link FakePainting}
     * @return the {@link FakePainting} with his NMSEntity
     * @throws NullPointerException
     */
    public static FakePainting getFakePaintingByObject(Player p, Object fake) throws NullPointerException {
        if (!getFakePainting().containsKey(p.getName())) return null;
        for (FakePainting fakePainting : getFakePainting().get(p.getName())) {
            if (fakePainting.getNmsEntity() == fake) return fakePainting;
        }
        return null;
    }

    /**
     * Gets a {@link FakePainting} by its {@link Location}
     *
     * @param p   the {@link Player} who can see this {@link FakePainting}
     * @param loc the {@link Location} of the {@link FakePainting}
     * @return the {@link FakePainting} at his {@link Location}
     * @throws NullPointerException
     */
    public static FakePainting getFakePaintingByLocation(Player p, Location loc) throws NullPointerException {
        if (!getFakePainting().containsKey(p.getName())) return null;
        for (FakePainting fakePainting : getFakePainting().get(p.getName())) {
            if ((loc.getBlockX() == fakePainting.getCurrentlocation().getBlockX()
                    && loc.getBlockY() == fakePainting.getCurrentlocation().getBlockY()
                    && loc.getBlockZ() == fakePainting.getCurrentlocation().getBlockZ()
                    || (loc.getBlockX() == fakePainting.getCurrentlocation().getBlockX()
                    && loc.getBlockY() == (fakePainting.getCurrentlocation().getBlockY() + 1)
                    && loc.getBlockZ() == fakePainting.getCurrentlocation().getBlockZ()))) {
                return fakePainting;
            }
        }
        return null;
    }


    public static int floor(double var0) {
        int var2 = (int) var0;
        return var0 < (double) var2 ? var2 - 1 : var2;
    }

    public static byte toAngle(float v) {
        return (byte) ((int) (v * 256.0F / 360.0F));
    }

    public static long toDelta(long v) {
        return ((v * 32) * 128);
    }

    public static Location lookAt(Location loc, Location lookat) {
        loc = loc.clone();
        double dx = lookat.getX() - loc.getX();
        double dy = lookat.getY() - loc.getY();
        double dz = lookat.getZ() - loc.getZ();

        if (dx != 0) {
            if (dx < 0) {
                loc.setYaw((float) (1.5 * Math.PI));
            } else {
                loc.setYaw((float) (0.5 * Math.PI));
            }
            loc.setYaw(loc.getYaw() - (float) Math.atan(dz / dx));
        } else if (dz < 0) {
            loc.setYaw((float) Math.PI);
        }

        double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));

        loc.setPitch((float) -Math.atan(dy / dxz));

        loc.setYaw(-loc.getYaw() * 180f / (float) Math.PI);
        loc.setPitch(loc.getPitch() * 180f / (float) Math.PI);

        return loc;
    }

    public static boolean isSameLocation(Location l1, Location l2) {
        return ((l1.getBlockX() == l2.getBlockX()) && (l1.getBlockY() == l2.getBlockY()) && (l1.getBlockZ() == l2.getBlockZ()));
    }

    public static Location getLocationBehindPlayer(Player p, int range) {
        World world = p.getWorld();
        Location behind = p.getLocation();
        int direction = (int) behind.getYaw();

        if (direction < 0) {
            direction += 360;
            direction = (direction + 45) / 90;
        } else {
            direction = (direction + 45) / 90;
        }

        switch (direction) {
            case 1:
                behind = new Location(world, behind.getX() + range, behind.getY(), behind.getZ(), behind.getYaw(), behind.getPitch());
                break;
            case 2:
                behind = new Location(world, behind.getX(), behind.getY(), behind.getZ() + range, behind.getYaw(), behind.getPitch());
                break;
            case 3:
                behind = new Location(world, behind.getX() - range, behind.getY(), behind.getZ(), behind.getYaw(), behind.getPitch());
                break;
            case 4:
                behind = new Location(world, behind.getX(), behind.getY(), behind.getZ() - range, behind.getYaw(), behind.getPitch());
                break;
            case 0:
                behind = new Location(world, behind.getX(), behind.getY(), behind.getZ() - range, behind.getYaw(), behind.getPitch());
                break;
            default:
                break;
        }

        return behind;
    }

    public static FakeAPI getFakeAPI() {
        return fakeAPI;
    }

    public double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    @Override
    public void onEnable() {
        new Register(this).initAll();
        fakeAPI = this;
    }
}
