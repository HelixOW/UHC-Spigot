package de.alphahelix.uhc.events.timers;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LobbyEndEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

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
}
