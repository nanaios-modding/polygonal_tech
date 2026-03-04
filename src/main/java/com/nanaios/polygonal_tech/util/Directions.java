package com.nanaios.polygonal_tech.util;

import net.minecraft.core.Direction;

public class Directions {
    public Direction[] inputs;
    public Direction[] outputs;

    public Directions input(Direction... directions) {
        this.inputs = directions;
        return this;
    }

    public Directions output(Direction... directions) {
        this.outputs = directions;
        return this;
    }
}
