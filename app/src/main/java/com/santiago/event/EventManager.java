package com.santiago.event;

import android.content.Context;

import com.santiago.event.listener.EventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Class in charge of receiving events (from listeners), manage them and/or broadcast them to the other listeners.
 */
public class EventManager implements EventListener {

    private Context context = null;

    private List<Object> objectsListening = new ArrayList<>(); //List of all the instances willing to receive events

    public EventManager(Context context){

        if(context == null)
            throw new NullPointerException("context cannot be null in EventManager");

        this.context = context;

    }

    public Context getContext() {
        return context;
    }

    /**
     * Adds an instance to the list of all the
     * classes that will be notified in the income of an event
     * @param object
     */
    public void addListener(Object object){

        if(object==null)
            return;

        objectsListening.add(object);

    }

    /**
     * Removes an instance to the list of all the classes that will be notified in the income of an event
     * @param object
     * @return if it was successfully removed
     */
    public boolean removeListener(Object object){

        if(object==null)
            return false;

        return objectsListening.remove(object);

    }

    /**
     * Method called (by one sending an event) for the purpose of managing that event, and/or else broadcast it to the
     * list of classes listening
     * @param event
     */
    @Override
    public void broadcastEvent(Event event) {
        //Dispatch the event to ourselves
        event.dispatchEventTo(this);

        //Iterate through all the objects listening and dispatch it too
        for(Object object : new ArrayList<>(objectsListening)){
            if(object!=null)
                event.dispatchEventTo(object);
        }

    }

}
