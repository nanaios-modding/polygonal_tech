package com.nanaios.polygonal_tech.core.capability

import com.nanaios.polygonal_tech.core.capability.energy.CombinedLongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.energy.ILongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.energy.InputWrapperLongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.energy.OutputWrapperLongEnergyStorage
import com.nanaios.polygonal_tech.core.capability.face.IFace
import com.nanaios.polygonal_tech.core.capability.face.IItemSlotFaceBuilder
import com.nanaios.polygonal_tech.core.capability.face.ILongEnergyStorageFaceBuilder
import com.nanaios.polygonal_tech.core.capability.face.ILongFluidTankFaceBuilder
import com.nanaios.polygonal_tech.core.capability.fluid.ILongFluidHandler
import com.nanaios.polygonal_tech.core.capability.fluid.InputOnlyLongFluidHandler
import com.nanaios.polygonal_tech.core.capability.fluid.LongFluidHandler
import com.nanaios.polygonal_tech.core.capability.fluid.OutputOnlyLongFluidHandler
import com.nanaios.polygonal_tech.core.capability.io.IIOMode
import com.nanaios.polygonal_tech.core.capability.io.IOMode
import com.nanaios.polygonal_tech.core.capability.item.IItemSlotHandler
import com.nanaios.polygonal_tech.core.capability.item.InputOnlyItemSlotHandler
import com.nanaios.polygonal_tech.core.capability.item.ItemSlotHandler
import com.nanaios.polygonal_tech.core.capability.item.OutputOnlyItemSlotHandler
import net.minecraft.network.chat.Component

/**
 * タイルエンティティ（[SingleMachineTile]等）内で保持する各種Capability（エネルギー、流体、アイテム）について、
 * 宣言的なDSLを用いて簡潔にインスタンスと面（[IFace]）の紐付け・初期化を行うことを目的としたビルダー。
 *
 * @param longEnergyStorageList 追加するエネルギー関連Capabilityのリスト
 * @param longFluidHandlerList 追加する流体関連Capabilityのリスト
 * @param itemSlotHandlerList 追加するアイテムスロット関連Capabilityのリスト
 * @param longEnergyStorageFaceMap 面ごとのエネルギーCapabilityマッピング
 * @param longFluidHandlerFaceMap 面ごとの流体Capabilityマッピング
 * @param itemSlotHandlerFaceMap 面ごとのアイテムCapabilityマッピング
 */
open class CapabilityBuilder(
    protected val longEnergyStorageList: MutableList<Pair<Component, ILongEnergyStorage>>,
    protected val longFluidHandlerList: MutableList<Pair<Component, ILongFluidHandler>>,
    protected val itemSlotHandlerList: MutableList<Pair<Component, IItemSlotHandler>>,
    protected val longEnergyStorageFaceMap: MutableMap<IFace, Int>,
    protected val longFluidHandlerFaceMap: MutableMap<IFace, Int>,
    protected val itemSlotHandlerFaceMap: MutableMap<IFace, Int>,
) {
    /**
     * 指定された設定に基づき、エネルギー管理用のCapability（[ILongEnergyStorage]）を構築してタイルに紐付けることを目的とするメソッド。
     *
     * @param name GUI表示等で使われるCapability名（[Component]）
     * @param mode 入力専用か出力専用かを示す[IIOMode]
     * @param defaultFace インタラクトを許可するデフォルトのブロック面（[IFace]）
     * @param builder 内部のコンポーネントを定義する追加のDSLブロック
     */
    fun energy(name: Component, mode: IIOMode, defaultFace: IFace, builder: ILongEnergyStorageFaceBuilder.() -> Unit) {
        val energyBuilder = ILongEnergyStorageFaceBuilder()
        energyBuilder.builder()

        val (capabilities) = energyBuilder

        val combined = if (capabilities.size == 1) capabilities[0] else CombinedLongEnergyStorage(capabilities)

        val wrapped = if (mode is IOMode) when (mode) {
            IOMode.INPUT -> InputWrapperLongEnergyStorage(combined)
            IOMode.OUTPUT -> OutputWrapperLongEnergyStorage(combined)
            else -> combined
        } else combined

        val pair = Pair(name, wrapped)
        longEnergyStorageList.add(pair)
        longEnergyStorageFaceMap[defaultFace] = longEnergyStorageList.size - 1
    }

    /**
     * 指定された設定に基づき、流体管理用のCapability（[ILongFluidHandler]）を構築してタイルに紐付けることを目的とするメソッド。
     *
     * @param name GUI表示等で使われるCapability名（[Component]）
     * @param mode 入力専用か出力専用かを示す[IIOMode]
     * @param defaultFace インタラクトを許可するデフォルトのブロック面（[IFace]）
     * @param builder 内部のタンク構成を定義する追加のDSLブロック
     */
    fun fluid(name: Component, mode: IIOMode, defaultFace: IFace, builder: ILongFluidTankFaceBuilder.() -> Unit) {
        val fluidBuilder = ILongFluidTankFaceBuilder()
        fluidBuilder.builder()

        val (capabilities) = fluidBuilder

        val handler = if (mode is IOMode) when (mode) {
            IOMode.INPUT -> InputOnlyLongFluidHandler(capabilities)
            IOMode.OUTPUT -> OutputOnlyLongFluidHandler(capabilities)
            else -> LongFluidHandler(capabilities)
        } else LongFluidHandler(capabilities)

        val pair = Pair(name, handler)
        longFluidHandlerList.add(pair)
        longFluidHandlerFaceMap[defaultFace] = longFluidHandlerList.size - 1
    }

    /**
     * 指定された設定に基づき、アイテム管理用のCapability（[IItemSlotHandler]）を構築してタイルに紐付けることを目的とするメソッド。
     *
     * @param name GUI表示等で使われるCapability名（[Component]）
     * @param mode 入力専用か出力専用かを示す[IIOMode]
     * @param defaultFace インタラクトを許可するデフォルトのブロック面（[IFace]）
     * @param builder 内部のスロット構成を定義する追加のDSLブロック
     */
    fun item(name: Component, mode: IIOMode, defaultFace: IFace,  builder: IItemSlotFaceBuilder.()-> Unit) {
        val itemBuilder = IItemSlotFaceBuilder()
        itemBuilder.builder()

        val (capabilities) = itemBuilder

        val handler = if (mode is IOMode) when (mode) {
            IOMode.INPUT -> InputOnlyItemSlotHandler(capabilities)
            IOMode.OUTPUT -> OutputOnlyItemSlotHandler(capabilities)
            else -> ItemSlotHandler(capabilities)
        } else ItemSlotHandler(capabilities)

        val pair = Pair(name, handler)
        itemSlotHandlerList.add(pair)
        itemSlotHandlerFaceMap[defaultFace] = itemSlotHandlerList.size - 1
    }
}
