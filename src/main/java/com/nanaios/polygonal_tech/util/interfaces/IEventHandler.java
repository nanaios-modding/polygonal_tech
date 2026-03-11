package com.nanaios.polygonal_tech.util.interfaces;

import java.util.function.Consumer;

public interface IEventHandler {
    <E extends IEvent> void addListener(E type, Consumer<E> listener);

    <E extends IEvent> void removeListener(E type, Consumer<E> listener);

     <E extends IEvent> void triggerEvent(E event);
}
