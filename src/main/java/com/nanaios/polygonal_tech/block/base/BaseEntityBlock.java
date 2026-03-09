package com.nanaios.polygonal_tech.block.base;

import com.nanaios.polygonal_tech.block_entity.base.BaseBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BaseEntityBlock extends Block implements EntityBlock {
    private final RegistryObject<BlockEntityType<?>> blockEntityType;

    public BaseEntityBlock(RegistryObject<BlockEntityType<?>> blockEntityType) {
        this(Block.Properties.of().noOcclusion(), blockEntityType);
    }

    public BaseEntityBlock(Properties properties, RegistryObject<BlockEntityType<?>> blockEntityType) {
        super(properties);
        this.blockEntityType = blockEntityType;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return blockEntityType.get().create(pos, state);
    }

    @Override
    public @Nullable <I extends BlockEntity> BlockEntityTicker<I> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<I> type) {
        return level.isClientSide ? BaseBlockEntity::clientTicker : BaseBlockEntity::serverTicker;
    }
}
