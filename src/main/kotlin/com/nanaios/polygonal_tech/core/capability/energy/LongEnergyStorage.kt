package com.nanaios.polygonal_tech.core.capability.energy

import com.nanaios.polygonal_tech.core.capability.energy.ILongEnergyStorage.Companion.NBT_CAPACITY_KEY
import com.nanaios.polygonal_tech.core.capability.energy.ILongEnergyStorage.Companion.NBT_STORED_KEY
import com.nanaios.polygonal_tech.core.network.sync.storage.EmptySyncValueStorage
import com.nanaios.polygonal_tech.core.network.sync.storage.ISyncValueStorage
import com.nanaios.polygonal_tech.core.network.sync.type.ISyncType
import com.nanaios.polygonal_tech.core.network.sync.type.SyncType
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import kotlin.math.min

/**
 * [ILongEnergyStorage]の標準的な実装クラスであり、
 * NBTへの永続化、ネットワークへのバイトバッファ送受信機能（[ISyncValue]）を内包することを目的とする。
 *
 * @param stored 初期状態で保持するエネルギー量
 * @param capacity 蓄電可能な最大エネルギー容量
 */
open class LongEnergyStorage(
    stored: Long,
    capacity: Long,
) : ILongEnergyStorage {
    override var longEnergyStored = stored
        protected set
    override var maxLongEnergyStored = capacity
        protected set
    override var type: ISyncType = SyncType.NONE
    override var storage: ISyncValueStorage = EmptySyncValueStorage
    override var isDirty = false
        protected set

    protected fun markDirty() {
        isDirty = true
        storage.onSyncValueChanged(this)
    }

    override fun serializeNBT(): CompoundTag {
        val tag = CompoundTag()
        tag.putLong(NBT_STORED_KEY, this.longEnergyStored)
        tag.putLong(NBT_CAPACITY_KEY, this.maxLongEnergyStored)
        return tag
    }

    override fun deserializeNBT(nbt: CompoundTag) {
        this.longEnergyStored = nbt.getLong(NBT_STORED_KEY)
        this.maxLongEnergyStored = nbt.getLong(NBT_CAPACITY_KEY)
    }

    override fun receiveLongEnergy(maxReceive: Long, simulate: Boolean): Long {
        val received = min(maxReceive,maxLongEnergyStored - longEnergyStored)
        if (!simulate && received != 0L) {
            longEnergyStored += received
            markDirty()
        }
        return received
    }

    override fun extractLongEnergy(maxExtract: Long, simulate: Boolean): Long {
        val extracted = min(maxExtract, longEnergyStored)
        if (!simulate && extracted != 0L) {
            longEnergyStored -= extracted
            markDirty()
        }
        return extracted
    }

    override fun writeBuffer(buffer: FriendlyByteBuf) {
        buffer.writeLong(this.longEnergyStored)
        buffer.writeLong(this.maxLongEnergyStored)
    }

    override fun readBuffer(buffer: FriendlyByteBuf) {
        this.longEnergyStored = buffer.readLong()
        this.maxLongEnergyStored = buffer.readLong()
    }

    override fun onSync() {
        isDirty = false
    }
}