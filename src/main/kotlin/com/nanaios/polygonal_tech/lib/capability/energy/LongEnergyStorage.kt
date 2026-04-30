package com.nanaios.polygonal_tech.lib.capability.energy

import com.nanaios.polygonal_tech.lib.util.sync.storage.EmptySyncValueStorage
import com.nanaios.polygonal_tech.lib.util.sync.storage.ISyncValueStorage
import com.nanaios.polygonal_tech.lib.util.sync.value.ISyncType
import com.nanaios.polygonal_tech.lib.util.sync.value.SyncType
import net.minecraft.network.FriendlyByteBuf

open class LongEnergyStorage(
    stored: Long,
    capacity: Long,
    protected var extract: Boolean,
    protected var receive: Boolean,
) : ILongEnergyStorage {
    override var longEnergyStored = stored
        protected set
    override var maxLongEnergyStored = capacity
        protected set
    override var type: ISyncType = SyncType.NONE
    override var storage: ISyncValueStorage = EmptySyncValueStorage
    override var isDirty = false
        protected set

    override fun receiveLongEnergy(maxReceive: Long, simulate: Boolean): Long {
        val received = maxReceive.coerceAtMost(maxLongEnergyStored - longEnergyStored)
        if (!simulate && received != 0L) {
            longEnergyStored += received
            isDirty = true
            storage.onSyncValueChanged(this)
        }
        return received
    }

    override fun extractLongEnergy(maxExtract: Long, simulate: Boolean): Long {
        val extracted = maxExtract.coerceAtMost(longEnergyStored)
        if (!simulate && extracted != 0L) {
            longEnergyStored -= extracted
            isDirty = true
            storage.onSyncValueChanged(this)
        }
        return extracted
    }

    override fun canExtract() = extract
    override fun canReceive() = receive

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

class InputOnlyLongEnergyStorage(
    storage: ILongEnergyStorage
) : ILongEnergyStorage by storage {
    override fun extractLongEnergy(maxExtract: Long, simulate: Boolean): Long {
        return 0L
    }

    override fun canExtract() = false
}

class OutputOnlyLongEnergyStorage(
    storage: ILongEnergyStorage
) : ILongEnergyStorage by storage {
    override fun receiveLongEnergy(maxReceive: Long, simulate: Boolean): Long {
        return 0L
    }

    override fun canReceive() = false
}