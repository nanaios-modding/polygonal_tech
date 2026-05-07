package com.nanaios.polygonal_tech.core.block

import com.nanaios.polygonal_tech.core.tile.SingleGuiMachineTile
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.minecraftforge.network.NetworkHooks

/**
 * プレイヤーが右クリック等でアクセスした際に、GUIを持つ機械特有の画面（Screen・Menu）を開くことを目的としたブロッククラス。
 *
 * @param id ブロックに割り当てられる固有の識別子
 * @param properties ブロックの物理的な性質を定義する[Properties]
 */
class GuiMachineBlock(
    id: ResourceLocation,
    properties: Properties
): MachineBlock(id,properties) {
    /**
     * ブロックに対してプレイヤーがインタラクト（右クリック）した際に呼び出され、
     * サーバー側で対象のタイルエンティティを検証した上で専用のGUIを開くことを目的とするメソッド。
     *
     * @param state インタラクト時のブロックステート
     * @param level 現在のワールド
     * @param pos インタラクトされたブロック座標
     * @param player インタラクトを実行したプレイヤー
     * @param hand プレイヤーが使用した手（メインハンドかオフハンドか）
     * @param result 操作対象のレイキャスト（ヒット）結果
     * @return インタラクトが消費されたか等の[InteractionResult]
     */
    @Deprecated("Deprecated in Java")
    override fun use(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        result: BlockHitResult
    ): InteractionResult {
        if (level.isClientSide) return InteractionResult.SUCCESS

        val blockEntity = level.getBlockEntity(pos)
        if (blockEntity is SingleGuiMachineTile) {
            NetworkHooks.openScreen(
                player as ServerPlayer,
                blockEntity,
                pos,
            )
        }

        return InteractionResult.CONSUME
    }
}