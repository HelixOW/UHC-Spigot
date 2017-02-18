/*
 *     Copyright (C) <2016>  <AlphaHelixDev>
 *
 *     This program is free software: you can redistribute it under the
 *     terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.alphahelix.alphalibary.utils;

import de.alphahelix.alphalibary.AlphaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.logging.Logger;

public class Util<P extends AlphaPlugin> {

    private P pl;

    public Util(P plugin) {
        setPluginInstance(plugin);
    }

    public P getPluginInstance() {
        return pl;
    }

    private void setPluginInstance(P pl) {
        this.pl = pl;
    }

    public Logger getLog() {
        return Bukkit.getLogger();
    }

    public double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public double calcSec(int milisec) {
        return (milisec * 60);
    }

    public double calcMin(int sec) {
        return (sec / (3600 / 60.0));
    }

    public double calcHours(int sec) {
        return sec / 3600;
    }

    public <T> void cooldown(int length, final T key, final List<T> cooldownList) {
        cooldownList.add(key);
        new BukkitRunnable() {
            public void run() {
                cooldownList.remove(key);
            }
        }.runTaskLaterAsynchronously(getPluginInstance(), length);
    }

    @SuppressWarnings("unchecked")
    public <T> T[] makeArray(T... types) {
        return types;
    }

    public <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        Set<T> keys = new HashSet<T>();
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

    public <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
