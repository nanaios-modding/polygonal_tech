package com.nanaios.polygonal_tech.block_entity;

import com.nanaios.polygonal_tech.block_entity.base.BaseGuiMachine;
import com.nanaios.polygonal_tech.capability.CapabilityBuilder;
import com.nanaios.polygonal_tech.capability.interfaces.IItemSlot;
import com.nanaios.polygonal_tech.capability.item.ItemSlot;
import com.nanaios.polygonal_tech.registries.PolygonalTechBlockEntityTypeRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class PhotolysisMachineMk1 extends BaseGuiMachine<PhotolysisMachineMk1> {
    private ItemSlot inputSlot;
    private int progress = 0;

    public PhotolysisMachineMk1(BlockPos pos, BlockState state) {
        super(PolygonalTechBlockEntityTypeRegister.PHOTOLYSIS_MACHINE_MK1, pos, state);
    }

    @Override
    public CapabilityBuilder<IItemSlot> initItemSlot() {
        CapabilityBuilder<IItemSlot> builder = new CapabilityBuilder<>();
        builder.add(inputSlot = new ItemSlot(true,true,stack -> true,80,80), Direction.NORTH,Direction.SOUTH);
        return builder;
    }

    @Override
    public boolean serverTick(Level level, BlockPos pos, BlockState state, PhotolysisMachineMk1 blockEntity) {
        return super.serverTick(level, pos, state, blockEntity);
    }
}
