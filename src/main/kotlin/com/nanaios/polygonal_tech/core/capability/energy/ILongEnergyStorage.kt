package com.nanaios.polygonal_tech.core.capability.energy

import com.nanaios.polygonal_tech.core.capability.ICapability
import com.nanaios.polygonal_tech.core.util.roundInt
import net.minecraftforge.energy.IEnergyStorage

/**
 * 従来の[IEnergyStorage]を拡張し、大容量（[Int.MAX_VALUE]を超える[Long]型）のエネルギーを管理・同期することを目的としたCapabilityインターフェース。
 * 外部のMODとのやり取りにおいては、既存の[Int]型への丸め処理を通じて透過的に対話する。
 */
interface ILongEnergyStorage: IEnergyStorage, ICapability {
    /** 蓄積されている現在のエネルギー量（[Long]型） */
    val longEnergyStored: Long
    /** 蓄積可能なエネルギーの最大容量（[Long]型） */
    val maxLongEnergyStored: Long

    /**
     * 外部からのエネルギーを受け入れることを目的とするメソッド。
     *
     * @param maxReceive 受け入れを試みる最大エネルギー量
     * @param simulate 実際の受け入れを行うか、シミュレートかを示すフラグ
     * @return 実際に受け入れたエネルギー量
     */
    fun receiveLongEnergy(maxReceive: Long, simulate: Boolean): Long

    /**
     * 内部から外部へエネルギーを排出することを目的とするメソッド。
     *
     * @param maxExtract 排出を試みる最大エネルギー量
     * @param simulate 実際の排出を行うか、シミュレートかを示すフラグ
     * @return 実際に排出されたエネルギー量
     */
    fun extractLongEnergy(maxExtract: Long, simulate: Boolean): Long


    override fun canExtract() = allowInput
    override fun canReceive() = allowInput
    override fun getEnergyStored() = longEnergyStored.roundInt()
    override fun getMaxEnergyStored() = maxLongEnergyStored.roundInt()
    override fun receiveEnergy(maxReceive: Int, simulate: Boolean) = receiveLongEnergy(maxReceive.toLong(), simulate).roundInt()
    override fun extractEnergy(maxExtract: Int, simulate: Boolean) = extractLongEnergy(maxExtract.toLong(), simulate).roundInt()

    companion object {
        const val NBT_STORED_KEY = "stored"
        const val NBT_CAPACITY_KEY = "capacity"
    }
}