package com.nanaios.polygonal_tech.util.impl;

import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.util.interfaces.IEvent;
import net.minecraft.resources.ResourceLocation;

public class Events {
    public static IEvent CONTAINER_UPDATE = new Impl(PolygonalTech.rl("container_update"));

    public static class Impl implements IEvent{
        private final ResourceLocation id;
        public Impl(ResourceLocation id) {
            this.id = id;
        }

        @Override
        public ResourceLocation getEventName() {
            return id;
        }
    }
}
