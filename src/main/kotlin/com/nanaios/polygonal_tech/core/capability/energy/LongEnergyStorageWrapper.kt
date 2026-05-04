package com.nanaios.polygonal_tech.core.capability.energy

import com.nanaios.polygonal_tech.core.network.sync.storage.EmptySyncValueStorage
import com.nanaios.polygonal_tech.core.network.sync.storage.ISyncValueStorage
import com.nanaios.polygonal_tech.core.network.sync.type.ISyncType
import com.nanaios.polygonal_tech.core.network.sync.type.SyncType
import com.nanaios.polygonal_tech.core.util.nonOverflowAdd
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf

class InputWrapperLongEnergyStorage(
    storage: ILongEnergyStorage
) : ILongEnergyStorage by storage {
    override val allowOutput = false
    override fun extractLongEnergy(maxExtract: Long, simulate: Boolean): Long {
        return 0L
    }
}

class OutputWrapperLongEnergyStorage(
    storage: ILongEnergyStorage
) : ILongEnergyStorage by storage {
    override val allowInput = false
    override fun receiveLongEnergy(maxReceive: Long, simulate: Boolean): Long {
        return 0L
    }

    override fun canReceive() = false
}


class CombinedLongEnergyStorage(
    private val storages: MutableList<ILongEnergyStorage>
) : ILongEnergyStorage {
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
            if (wantReceived <= 0) break
        }
        return wantReceived
    }

    override fun extractLongEnergy(maxExtract: Long, simulate: Boolean): Long {
        var wantExtracted = maxExtract
        for (storage in storages) {
            wantExtracted -= storage.extractLongEnergy(wantExtracted, simulate)
            if (wantExtracted <= 0) break
        }
        return wantExtracted
    }

    override var storage: ISyncValueStorage = EmptySyncValueStorage
    override var type: ISyncType = SyncType.NONE
    override val isDirty = false
    override val allowInput: Boolean
        get() {
            storages.forEach { storage ->
                if (storage.allowInput) {
                    return true
                }
            }
            return false
        }

    override val allowOutput: Boolean
        get() {
            storages.forEach { storage ->
                if (storage.allowOutput) {
                    return true
                }
            }
            return false
        }

    override fun writeBuffer(buffer: FriendlyByteBuf) = Unit
    override fun readBuffer(buffer: FriendlyByteBuf) = Unit
    override fun onSync() = Unit
    override fun serializeNBT() = CompoundTag()
    override fun deserializeNBT(nbt: CompoundTag) = Unit
}