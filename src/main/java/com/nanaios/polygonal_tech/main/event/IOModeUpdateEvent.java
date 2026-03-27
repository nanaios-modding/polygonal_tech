package com.nanaios.polygonal_tech.main.event;

import com.nanaios.polygonal_tech.main.event.interfaces.IEvent;
import net.minecraft.core.Direction;

public record IOModeUpdateEvent(Direction side) implements IEvent { }
