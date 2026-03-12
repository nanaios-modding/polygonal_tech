package com.nanaios.polygonal_tech.event;

import com.nanaios.polygonal_tech.util.interfaces.IEvent;
import net.minecraft.core.Direction;

public record IOModeUpdateEvent(Direction side) implements IEvent { }
