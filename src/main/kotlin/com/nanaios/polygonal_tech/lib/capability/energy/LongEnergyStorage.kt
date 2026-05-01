package com.nanaios.polygonal_tech.lib.capability.energy

import com.nanaios.polygonal_tech.lib.util.nonOverflowAdd
import com.nanaios.polygonal_tech.lib.util.sync.storage.EmptySyncValueStorage
import com.nanaios.polygonal_tech.lib.util.sync.storage.ISyncValueStorage
import com.nanaios.polygonal_tech.lib.util.sync.value.ISyncType
import com.nanaios.polygonal_tech.lib.util.sync.value.SyncType
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import kotlin.math.min

open class LongEnergyStorage(
    stored: Long,
    capacity: Long,
    protected var extract: Boolean,
    protected var receive: Boolean,
) : ILongEnergyStorage {
    companion object {
        const val NBT_STORED_KEY = "stored"
        const val NBT_CAPACITY_KEY = "capacity"
    }

    override var longEnergyStored = stored
        protected set
    override var maxLongEnergyStored = capacity
        protected set
    override var type: ISyncType = SyncType.NONE
    override var storage: ISyncValueStorage = EmptySyncValueStorage
    override var isDirty = false
        protected set

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
            isDirty = true
            storage.onSyncValueChanged(this)
        }
        return received
    }

    override fun extractLongEnergy(maxExtract: Long, simulate: Boolean): Long {
        val extracted = min(maxExtract,longEnergyStored)
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

class MultiLongEnergyStorage(
    private val storages: MutableList<ILongEnergyStorage>
):ILongEnergyStorage {
    override val longEnergyStored: Long
        get() {
            var total: Long = 0
            for (storage in storages) {
                total = total.nonOverflowAdd(storage.longEnergyStored)
            }
            return total
        }
    override val maxLongEnergyStored: Long
        get() {
            var total: Long = 0
            for (storage in storages) {
                total = total.nonOverflowAdd(storage.maxLongEnergyStored)
            }
            return total
        }

    override fun receiveLongEnergy(maxReceive: Long, simulate: Boolean): Long {
        var wantReceived = maxReceive
        for (storage in storages) {
            wantReceived -= storage.receiveLongEnergy(wantReceived, simulate)
            if(wantReceived <= 0) break
        }
        return wantReceived
    }

    override fun extractLongEnergy(maxExtract: Long, simulate: Boolean): Long {
        var wantExtracted = maxExtract
        for (storage in storages) {
            wantExtracted -= storage.extractLongEnergy(wantExtracted, simulate)
            if(wantExtracted <= 0) break
        }
        return wantExtracted
    }

    override fun canExtract(): Boolean {
        storages.forEach { storage ->
            if(storage.canExtract()) {
                return true
            }
        }
        return false
    }

    override fun canReceive(): Boolean {
        storages.forEach { storage ->
            if(storage.canReceive()) {
                return true
            }
        }
        return false
    }

    override var storage: ISyncValueStorage = EmptySyncValueStorage
    override var type: ISyncType = SyncType.NONE
    override val isDirty = false

    override fun writeBuffer(buffer: FriendlyByteBuf) = Unit
    override fun readBuffer(buffer: FriendlyByteBuf) = Unit
    override fun onSync() = Unit
    override fun serializeNBT() = CompoundTag()
    override fun deserializeNBT(nbt: CompoundTag) = Unit
}