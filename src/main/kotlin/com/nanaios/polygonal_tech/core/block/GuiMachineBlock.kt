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


class GuiMachineBlock(
    id: ResourceLocation,
    properties: Properties
): MachineBlock(id,properties) {
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