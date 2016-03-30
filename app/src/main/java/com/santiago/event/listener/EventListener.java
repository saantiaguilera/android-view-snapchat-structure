package com.santiago.event.listener;

import com.santiago.event.Event;

/**
 * Created by santiaguilera@theamalgama.com on 01/03/16.
 */
public interface EventListener {

    /**
     * Broadcast the event to all the ones listening
     * @param event
     */
    void broadcastEvent(Event event);

}
