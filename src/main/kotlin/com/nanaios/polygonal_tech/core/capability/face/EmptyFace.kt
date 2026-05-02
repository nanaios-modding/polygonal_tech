package com.nanaios.polygonal_tech.core.capability.face

import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.resources.ResourceLocation

object EmptyFace: IFace {
    override val id: ResourceLocation = ResourceLocation.fromNamespaceAndPath(PolygonalTech.MOD_ID, "face/empty")

}