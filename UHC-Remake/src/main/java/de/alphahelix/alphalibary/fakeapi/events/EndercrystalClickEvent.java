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

package de.alphahelix.alphalibary.fakeapi.events;

import de.alphahelix.alphalibary.fakeapi.instances.FakeEndercrystal;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;

public class EndercrystalClickEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private FakeEndercrystal fakeEndercrystal;
    private Action clickAction;

    public EndercrystalClickEvent(Player player, FakeEndercrystal fakeEndercrystal, Action clickAction) {
        this.player = player;
        this.fakeEndercrystal = fakeEndercrystal;
        this.clickAction = clickAction;
    }

    /**
     * Gets a list of handlers handling this event.
     *
     * @return A list of handlers handling this event.
     */
    public final static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Gets a list of handlers handling this event.
     *
     * @return A list of handlers handling this event.
     */
    @Override
    public final HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Gets the player who clicked the {@link FakeEndercrystal}
     *
     * @return the player who clicked
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the clicked {@link FakeEndercrystal}
     *
     * @return the clicked {@link FakeEndercrystal}
     */
    public FakeEndercrystal getFakeEndercrystal() {
        return fakeEndercrystal;
    }

    /**
     * gets the {@link Action}
     *
     * @return the {@link Action}
     */
    public Action getClickAction() {
        return clickAction;
    }
}
