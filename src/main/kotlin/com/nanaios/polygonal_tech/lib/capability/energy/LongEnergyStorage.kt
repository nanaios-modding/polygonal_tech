package com.nanaios.polygonal_tech.lib.capability.energy

import com.nanaios.polygonal_tech.lib.util.sync.storage.EmptySyncValueStorage
import com.nanaios.polygonal_tech.lib.util.sync.storage.ISyncValueStorage
import com.nanaios.polygonal_tech.lib.util.sync.value.ISyncType
import net.minecraft.network.FriendlyByteBuf

open class LongEnergyStorage(
    protected var stored: Long,
    protected var capacity: Long,
    protected var extract: Boolean,
    protected var receive: Boolean,
    override val syncType: ISyncType
) : ILongEnergyStorage {
    protected var _isDirty = false
    protected var _storage: ISyncValueStorage = EmptySyncValueStorage

    override val longEnergyStored: Long
        get() = stored
    override val maxLongEnergyStored: Long
        get() = capacity
    override val storage: ISyncValueStorage
        get() = _storage

    override fun setStorage(storage: ISyncValueStorage) {
        _storage = storage
    }

    override fun receiveLongEnergy(maxReceive: Long, simulate: Boolean): Long {
        val received = maxReceive.coerceAtMost(capacity - stored)
        if (!simulate && received != 0L) {
            stored += received
            _isDirty = true
            storage.onSyncValueChanged(this)
        }
        return received
    }

    override fun extractLongEnergy(maxExtract: Long, simulate: Boolean): Long {
        val extracted = maxExtract.coerceAtMost(stored)
        if (!simulate && extracted != 0L) {
            stored -= extracted
            _isDirty = true
            storage.onSyncValueChanged(this)
        }
        return extracted
    }

    override fun canExtract() = extract
    override fun canReceive() = receive

    override val isDirty: Boolean
        get() = _isDirty

    override fun writeBuffer(buffer: FriendlyByteBuf) {
        buffer.writeLong(this.longEnergyStored)
        buffer.writeLong(this.capacity)
    }

    override fun readBuffer(buffer: FriendlyByteBuf) {
        this.stored = buffer.readLong()
        this.capacity = buffer.readLong()
    }

    override fun onSync() {
        _isDirty = false
    }
}

class InputOnlyLongEnergyStorage(
    storage:ILongEnergyStorage
): ILongEnergyStorage by storage {
    override fun extractLongEnergy(maxExtract: Long, simulate: Boolean): Long {
        return 0L
    }

    override fun canExtract() = false
}

class OutputOnlyLongEnergyStorage(
    storage: ILongEnergyStorage
): ILongEnergyStorage by storage {
    override fun receiveLongEnergy(maxReceive: Long, simulate: Boolean): Long {
        return 0L
    }

    override fun canReceive() = false
}