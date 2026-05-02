package com.nanaios.polygonal_tech.core.capability.energy

import com.nanaios.polygonal_tech.core.network.sync.storage.EmptySyncValueStorage
import com.nanaios.polygonal_tech.core.network.sync.storage.ISyncValueStorage
import com.nanaios.polygonal_tech.core.network.sync.type.ISyncType
import com.nanaios.polygonal_tech.core.network.sync.type.SyncType
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf

object EmptyLongEnergyStorage : ILongEnergyStorage {
    override val longEnergyStored = 0L
    override val maxLongEnergyStored = 0L
    override var storage: ISyncValueStorage = EmptySyncValueStorage
    override var type: ISyncType = SyncType.NONE
    override val isDirty = false

    override fun receiveLongEnergy(maxReceive: Long, simulate: Boolean) = 0L
    override fun extractLongEnergy(maxExtract: Long, simulate: Boolean) = 0L
    override fun canExtract() = false
    override fun canReceive() = false
    override fun writeBuffer(buffer: FriendlyByteBuf) = Unit
    override fun readBuffer(buffer: FriendlyByteBuf) = Unit
    override fun onSync() = Unit
    override fun serializeNBT() = CompoundTag()
    override fun deserializeNBT(nbt: CompoundTag) = Unit
}