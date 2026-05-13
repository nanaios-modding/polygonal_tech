package com.nanaios.polygonal_tech.core

import com.nanaios.polygonal_tech.core.capability.face.IFace
import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.NewRegistryEvent
import net.minecraftforge.registries.RegistryBuilder
import java.util.function.Supplier

@Mod.EventBusSubscriber(modid = PolygonalTech.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object PolygonalTechRegistries {
    val FACE: IForgeRegistry<IFace>
        get() = FACE_SUP.get()

    private lateinit var FACE_SUP: Supplier<IForgeRegistry<IFace>>

    object Keys {
        val FACE: ResourceLocation = ResourceLocation.fromNamespaceAndPath(PolygonalTech.MOD_ID, "face")
    }

    @JvmStatic
    @SubscribeEvent
    fun onNewRegistry(event: NewRegistryEvent) {
        FACE_SUP = event.create(
            RegistryBuilder<IFace>()
                .setName(Keys.FACE)
        )
    }
}