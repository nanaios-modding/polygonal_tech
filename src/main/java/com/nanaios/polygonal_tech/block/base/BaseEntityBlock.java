package com.nanaios.polygonal_tech.block.base;

import com.nanaios.polygonal_tech.block_entity.base.BaseBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BaseEntityBlock<T extends BlockEntity> extends Block implements EntityBlock {
    private final RegistryObject<BlockEntityType<T>> blockEntityType;

    public BaseEntityBlock(RegistryObject<BlockEntityType<T>> blockEntityType) {
        this(Block.Properties.of().noOcclusion(), blockEntityType);

        this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }

    public BaseEntityBlock(Properties properties, RegistryObject<BlockEntityType<T>> blockEntityType) {
        super(properties);
        this.blockEntityType = blockEntityType;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());

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
