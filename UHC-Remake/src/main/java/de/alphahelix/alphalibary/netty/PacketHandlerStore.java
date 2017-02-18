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
package de.alphahelix.alphalibary.netty;

import java.util.ArrayList;

/**
 * Needs a rework
 */
public class PacketHandlerStore {

    private ArrayList<PacketHandler> allHandler = new ArrayList<>();

    public void addHandler(PacketHandler handler) {

        if (!allHandler.contains(handler)) {
            allHandler.add(handler);
        }

    }

    public void removeHandler(Class<?> clazz) {
        for (int i = 0; i < allHandler.size(); i++) {
            if (clazz.isAssignableFrom(allHandler.get(i).getClass())) {
                allHandler.remove(i);
            }
        }
    }

    public ArrayList<PacketHandler> getAllHandler() {
        return allHandler;
    }

}
