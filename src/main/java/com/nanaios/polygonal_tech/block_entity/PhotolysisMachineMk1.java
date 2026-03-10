package com.nanaios.polygonal_tech.block_entity;

import com.nanaios.polygonal_tech.block_entity.base.BaseGuiMachine;
import com.nanaios.polygonal_tech.capability.interfaces.IItemSlot;
import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;
import com.nanaios.polygonal_tech.container.ContainerBuilder;
import com.nanaios.polygonal_tech.container.impl.ItemContainer;
import com.nanaios.polygonal_tech.container.impl.LongEnergyContainer;
import com.nanaios.polygonal_tech.registries.PolygonalTechBlockEntityTypeRegister;
import com.nanaios.polygonal_tech.util.Directions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class PhotolysisMachineMk1 extends BaseGuiMachine<PhotolysisMachineMk1> {
    public LongEnergyContainer longEnergyContainer;
    public ItemContainer itemContainer;

    public int progress = 0;

    public PhotolysisMachineMk1(BlockPos pos, BlockState state) {
        super(PolygonalTechBlockEntityTypeRegister.PHOTOLYSIS_MACHINE_MK1, pos, state);
    }

    @Override
    public boolean serverTick(Level level, BlockPos pos, BlockState state, PhotolysisMachineMk1 blockEntity) {
        return super.serverTick(level, pos, state, blockEntity);
    }

    @Override
    protected ContainerBuilder<ILongEnergyStorage> initEnergyContainer() {
        ContainerBuilder<ILongEnergyStorage> builder = new ContainerBuilder<>();
        builder.add(
                longEnergyContainer = LongEnergyContainer.create(() -> 10000),
                new Directions().inputOnly(Direction.DOWN).outputOnly(Direction.UP)
        );

        return builder;
    }

    @Override
    protected ContainerBuilder<IItemSlot> initItemContainer() {
        ContainerBuilder<IItemSlot> builder = new ContainerBuilder<>();

        builder.add(
                itemContainer = ItemContainer.create(stack -> true,30,30),
                new Directions().inputOnly(Direction.NORTH).outputOnly(Direction.SOUTH)
        );

        return builder;
    }
}
