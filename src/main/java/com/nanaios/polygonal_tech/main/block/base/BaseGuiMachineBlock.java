package com.nanaios.polygonal_tech.main.block.base;

import com.nanaios.polygonal_tech.main.block_entity.base.BaseGuiMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class BaseGuiMachineBlock<M extends BaseGuiMachine<?>> extends BaseMachineBlock {
    public BaseGuiMachineBlock(RegistryObject<BlockEntityType<?>> blockEntityType) {
        super(blockEntityType);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        if (level.isClientSide) return InteractionResult.SUCCESS;

        BlockEntity blockEntity = level.getBlockEntity(pos);

        if (blockEntity instanceof BaseGuiMachine<?> machine) {
            NetworkHooks.openScreen(
                    (ServerPlayer) player,
                    machine,
                    pos
            );
        }

        return InteractionResult.CONSUME;
    }
}
