package com.nanaios.polygonal_tech.core.register.single

import com.nanaios.polygonal_tech.core.capability.face.IFace
import com.nanaios.polygonal_tech.core.register.PolygonalTechRegistries
import net.minecraftforge.registries.DeferredRegister

class DeferredSingleFaceRegister(modId: String): DeferredSingleRegister<IFace>(
    DeferredRegister.createOptional(PolygonalTechRegistries.Keys.FACE, modId),
    modId
)