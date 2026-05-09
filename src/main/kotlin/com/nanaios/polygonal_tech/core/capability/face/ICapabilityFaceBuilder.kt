package com.nanaios.polygonal_tech.core.capability.face

import com.nanaios.polygonal_tech.core.capability.ICapability
import com.nanaios.polygonal_tech.core.capability.energy.ILongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.fluid.ILongFluidTank
import com.nanaios.polygonal_tech.core.capability.item.IItemSlot

/**
 * Capability（アイテム、流体、エネルギーなど）のリストを構築するためのDSL（DSLレシーバー）を提供し、
 * 単項演算子 `+` などを用いて直感的にCapabilityを追加・除外できるようにすることを目的とするインターフェース。
 *
 * @param C 構築対象の[ICapability]の型
 */
interface ICapabilityFaceBuilder<C: ICapability> {
    /** 構築中のCapabilityのリスト */
    var capabilities: MutableList<C>

    /**
     * `+capability` の形式でリストへ追加するための演算子オーバーロード。
     */
    operator fun C.unaryPlus() {
        capabilities.add(this)
    }

    /**
     * `-capability` の形式でリストから除外するための演算子オーバーロード。
     */
    operator fun C.unaryMinus() {
        capabilities.remove(this)
    }

    /**
     * 構造分解宣言 (`val (caps) = builder`) を使ってリストを簡単に取り出すための演算子オーバーロード。
     */
    operator fun component1() = capabilities
}

class ILongEnergyStorageFaceBuilder : ICapabilityFaceBuilder<ILongEnergyStorage> {
    override var capabilities: MutableList<ILongEnergyStorage> = arrayListOf()
}

class ILongFluidTankFaceBuilder : ICapabilityFaceBuilder<ILongFluidTank> {
    override var capabilities: MutableList<ILongFluidTank> = arrayListOf()
}

class IItemSlotFaceBuilder:ICapabilityFaceBuilder<IItemSlot> {
    override var capabilities: MutableList<IItemSlot> = arrayListOf()
}