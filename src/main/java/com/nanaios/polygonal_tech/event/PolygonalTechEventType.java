package com.nanaios.polygonal_tech.event;

import com.nanaios.polygonal_tech.event.interfaces.IEventType;

public class PolygonalTechEventType {
    public static IEventType<CapabilityUpdateEvent> CAPABILITY_UPDATE = new IEventType<>() { };
    public static IEventType<IOModeUpdateEvent> IO_MODE_UPDATE = new IEventType<>() { };
}
