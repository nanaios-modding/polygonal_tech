package com.nanaios.polygonal_tech.lib.capability.energy

import com.nanaios.polygonal_tech.lib.capability.ICapability
import com.nanaios.polygonal_tech.lib.util.roundInt
import com.nanaios.polygonal_tech.lib.util.sync.value.ISyncValue
import net.minecraftforge.energy.IEnergyStorage

interface ILongEnergyStorage: IEnergyStorage, ICapability {
    val longEnergyStored: Long
    val maxLongEnergyStored: Long
    fun receiveLongEnergy(maxReceive: Long, simulate: Boolean): Long
    fun extractLongEnergy(maxExtract: Long, simulate: Boolean): Long

    override fun getEnergyStored() = longEnergyStored.roundInt()
    override fun getMaxEnergyStored() = maxLongEnergyStored.roundInt()
    override fun receiveEnergy(maxReceive: Int, simulate: Boolean) = receiveLongEnergy(maxReceive.toLong(), simulate).roundInt()
    override fun extractEnergy(maxExtract: Int, simulate: Boolean) = extractLongEnergy(maxExtract.toLong(), simulate).roundInt()
}