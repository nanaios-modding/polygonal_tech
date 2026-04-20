package com.nanaios.polygonal_tech.lib.util

import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.core.Direction
import net.minecraft.resources.ResourceLocation

interface IFace {
    val id: ResourceLocation
}

enum class DirectionFace(
    override val id: ResourceLocation,
    val direction: Direction?
): IFace {
    DOWN(ResourceLocation.fromNamespaceAndPath(PolygonalTech.MOD_ID,"down"), Direction.DOWN),
    UP(ResourceLocation.fromNamespaceAndPath(PolygonalTech.MOD_ID,"up"), Direction.UP),
    NORTH(ResourceLocation.fromNamespaceAndPath(PolygonalTech.MOD_ID,"north"), Direction.NORTH),
    SOUTH(ResourceLocation.fromNamespaceAndPath(PolygonalTech.MOD_ID,"south"), Direction.SOUTH),
    WEST(ResourceLocation.fromNamespaceAndPath(PolygonalTech.MOD_ID,"west"), Direction.WEST),
    EAST(ResourceLocation.fromNamespaceAndPath(PolygonalTech.MOD_ID,"east"), Direction.EAST),
    INTERNAL(ResourceLocation.fromNamespaceAndPath(PolygonalTech.MOD_ID,"internal"), null);

    companion object {
        fun from(direction: Direction?): DirectionFace {
            return when (direction) {
                Direction.NORTH -> NORTH
                Direction.DOWN -> DOWN
                Direction.UP -> UP
                Direction.SOUTH -> SOUTH
                Direction.WEST -> WEST
                Direction.EAST -> EAST
                null -> INTERNAL
            }
        }
    }
}