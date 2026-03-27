package com.nanaios.polygonal_tech.main.block.pipe;

import com.nanaios.polygonal_tech.main.block.base.BasePipeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.registries.RegistryObject;

public class FluidPipeBlock extends BasePipeBlock {
    public FluidPipeBlock(RegistryObject<BlockEntityType<?>> blockEntityType) {
        super(blockEntityType);
    }

    @Override
    protected boolean canConnect(Level level, BlockPos pos, Direction dir) {
        BlockPos neighborPos = pos.relative(dir);
        BlockState neighborState = level.getBlockState(neighborPos);

        if (neighborState.getBlock() instanceof FluidPipeBlock) return true;

        BlockEntity be = level.getBlockEntity(neighborPos);
        if (be != null) {
            return be.getCapability(ForgeCapabilities.FLUID_HANDLER, dir.getOpposite()).isPresent();
        }
        return false;
    }
}