package com.nanaios.polygonal_tech.lib.capability.energy

class ILongEnergyStorageBuilder {
}

fun energy(builder: ILongEnergyStorageBuilder.() -> Unit): ILongEnergyStorageBuilder {
    return ILongEnergyStorageBuilder().apply(builder)
}