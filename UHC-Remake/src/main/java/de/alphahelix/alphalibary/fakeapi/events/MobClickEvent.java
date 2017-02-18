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

import de.alphahelix.alphalibary.fakeapi.FakeMobType;
import de.alphahelix.alphalibary.fakeapi.instances.FakeArmorstand;
import de.alphahelix.alphalibary.fakeapi.instances.FakeMob;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;

public class MobClickEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private FakeMob fakeMob;
    private FakeMobType fakeMobType;
    private Action clickAction;

    public MobClickEvent(Player player, FakeMob fakeMob, Action clickAction) {
        this.player = player;
        this.fakeMob = fakeMob;
        this.fakeMobType = fakeMob.getFakeMobType();
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
     * Gets the player who clicked the {@link FakeArmorstand}
     *
     * @return the player who clicked
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the clicked {@link FakeMob}
     *
     * @return the clicked {@link FakeMob}
     */
    public FakeMob getFakeMob() {
        return fakeMob;
    }

    /**
     * Gets the {@link FakeMobType} of the clicked {@link FakeMob}
     *
     * @return the {@link FakeMobType} of the clicked {@link FakeMob}
     */
    public FakeMobType getFakeMobType() {
        return fakeMobType;
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
