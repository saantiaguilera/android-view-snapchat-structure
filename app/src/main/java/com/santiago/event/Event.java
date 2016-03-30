package com.santiago.event;

import com.santiago.event.anotation.EventMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by santiaguilera@theamalgama.com on 01/03/16.
 */
public class Event {

    /**
     * Dispatchs to an object (listener) the event through all the hierarchy of the Event class (since you wont be dispatching an event
     * of class Event, probably a child of it)
     *
     * This means that it will first try to dispatch it to this instance(a child), then to his parent, and like that
     * until it reaches to Event.class (non inclusive)
     *
     * <strong> Make sure your proguard runs keepattributes '*Annotation*' , in case Proguard removes all of them, and since
     * this works in RunTime we wont catch a single method because proguard removed all of the anotations. With that line we are
     * telling the proguard to dont remove them. Check in your sdk folder inside the proguard for the files were you have your settings
     * (Check where you read your proguard settings from the build.gradle. There you will see if its doing it or not. In my case it has been done
     * by default</strong>
     *
     * @param listener object that will respond to the event
     */
    public void dispatchEventTo(Object listener) {
        for (Class eventClass = this.getClass(); eventClass != Event.class; eventClass = eventClass.getSuperclass())
            dispatchMethod(listener, eventClass);
    }

    /**
     * Invokes each method from the object (listener) that has an EventMethod anotation referencing "this" class
     *
     * <strong> NOTE: "this" class wont be necesarilly our particular class from which we called dispatchEventTo,
     * it can be its super or someone among that hierarchy, since we will be dispatching this event among all of them</strong>
     * @param listener
     * @param eventClass
     */
    private void dispatchMethod(Object listener, Class eventClass) {
        //Iterate through all the methods of our object that will respond to the event
        for(Method method : listener.getClass().getDeclaredMethods()) {
            //Get the method anotation of type EventMethod, if its value is the same as this class invoke it
            EventMethod anotation = method.getAnnotation(EventMethod.class);
            if (anotation!=null && anotation.value()==eventClass)
               invokeMethod(method, listener);
        }
    }

    /**
     * Invokes the method m on the receiver.
     * Will only invoke methods of form:
     *    methodName();
     *    methodName(MyEvent event);
     *
     * @param method
     * @param receiver
     * @throws IllegalArgumentException if method with the anotation uses different paramenters from the ones we try to invoke
     * @throws IllegalAccessException if some AsyncException occurs or you are implementing a security manager, then we cant turn the method accesible
     * @throws InvocationTargetException if an exception was throw inside the method we invoked (this is an error of that method, but we catch it here)
     * @throws NullPointerException if receiver is null (Wont happen unless you implement your own dispatcher)
     */
    private void invokeMethod(Method method, Object receiver) {
        try {
            //Set it accessible in case it has private access (which is most certain cases)
            method.setAccessible(true);

            //If it doesnt have parameters try to invoke it without them, else invoke them with ourselves as a param
            if (method.getParameterTypes().length == 0)
                method.invoke(receiver);
            else method.invoke(receiver, this);
        } catch ( IllegalArgumentException e) {
            //The user has the method defined with wrong arguments, throw an Ex telling him
            throw new IllegalArgumentException("You have wrong arguments in your method: " + method.getName() + ". Only methods that can be invoked are either with no args or with a param of type " + this.getClass().getSimpleName());
        } catch ( Exception e ) {
            //IllegalAccess and InvocationTarget are Api19, so we catch Exception for not raising our ApiLvl
            throw new RuntimeException(e);
        }
    }

}
