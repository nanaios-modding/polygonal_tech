package com.nanaios.polygonal_tech.main.tile

import com.nanaios.polygonal_tech.core.capability.face.DirectionFace
import com.nanaios.polygonal_tech.core.capability.fluid.LongFluidTank
import com.nanaios.polygonal_tech.core.capability.io.IOMode
import com.nanaios.polygonal_tech.core.capability.item.ItemSlot
import com.nanaios.polygonal_tech.core.fluid.LongFluidStack
import com.nanaios.polygonal_tech.core.network.sync.SyncIntValue
import com.nanaios.polygonal_tech.core.network.sync.bind
import com.nanaios.polygonal_tech.core.network.sync.on
import com.nanaios.polygonal_tech.core.network.sync.type.SyncType
import com.nanaios.polygonal_tech.core.tile.SingleGuiMachineTile
import com.nanaios.polygonal_tech.core.translation.FaceName
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.ForgeHooks

/**
 * 燃料アイテムを消費して熱エネルギー（流体として扱われる熱等）を抽出し、外部に供給するための機械（ブロックエンティティ）を定義することを目的としたクラス。
 * メニュー（GUI）を開いた際に、燃焼時間や内部タンク、スロットの状態をプレイヤーへ同期する機能を持つ。
 *
 * @param id この機械固有の登録ID（[ResourceLocation]）
 * @param pos 機械が配置されているゲーム内の座標（[BlockPos]）
 * @param state 現在のブロックステート（[BlockState]）
 */
class ThermoelectricExtractionMachine(
    id: ResourceLocation,
    pos: BlockPos,
    state: BlockState
) : SingleGuiMachineTile(id, pos, state) {
    /**
     * 燃料が燃え尽きるまでの残り時間を管理し、随時GUI向けに数値を同期させることを目的とするプロパティ。
     */
    val burnTime:Int by SyncIntValue() on SyncType.GUI_OPENED bind this

    /**
     * 抽出された熱流体を一時的に貯蔵・管理し、特定の面から外部へ出力させることを目的とする流体タンク（[LongFluidTank]）。
     */
    val thermTank = LongFluidTank(LongFluidStack.EMPTY,0L,4000L) { stack -> true } on SyncType.GUI_OPENED bind this

    /**
     * プレイヤーが投入した燃料アイテムを保持し、燃焼可能なアイテムのみを受け入れるよう制限することを目的とするアイテムスロット（[ItemSlot]）。
     */
    val fuelSlot = ItemSlot(10,10, ItemStack.EMPTY) { stack -> ForgeHooks.getBurnTime(stack,null) > 0 } on SyncType.GUI_OPENED bind this

    init {
        capability {
            fluid(FaceName.OUTPUT_1,IOMode.OUTPUT, DirectionFace.DOWN) {
                +thermTank
            }
        }
    }
}