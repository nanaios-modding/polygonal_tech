package com.nanaios.polygonal_tech.lib.util.face

import com.nanaios.polygonal_tech.lib.capability.ICapability
import com.nanaios.polygonal_tech.lib.capability.energy.ILongEnergyStorage
import com.nanaios.polygonal_tech.lib.capability.energy.InputOnlyLongEnergyStorage
import com.nanaios.polygonal_tech.lib.capability.energy.OutputOnlyLongEnergyStorage
import com.nanaios.polygonal_tech.lib.util.IIOMode
import com.nanaios.polygonal_tech.lib.util.IOMode
import net.minecraft.network.chat.Component

open class FaceConfigBuilder(
    builder: FaceConfigBuilder.() -> Unit
) {
    val capabilities = mutableMapOf<Component, ICapability>()

    init {
        builder()
    }

    fun energy(name: Component, builder: LongEnergyBuilder.() -> Unit) {
        val instance = LongEnergyBuilder()
        builder(instance)

        val (mode, cap) = instance
        val wrapping = if (mode is IOMode) when (mode) {
            IOMode.INPUT -> InputOnlyLongEnergyStorage(cap)
            IOMode.OUTPUT -> OutputOnlyLongEnergyStorage(cap)
            else -> cap
        } else cap

        capabilities[name] = wrapping
    }
}

interface IInnerFaceConfigBuilder<T : ICapability> {
    var mode: IIOMode
    var cap: T

    operator fun component1(): IIOMode = mode
    operator fun component2(): T = cap
}

class LongEnergyBuilder : IInnerFaceConfigBuilder<ILongEnergyStorage> {
    override lateinit var mode: IIOMode
    override lateinit var cap: ILongEnergyStorage
}