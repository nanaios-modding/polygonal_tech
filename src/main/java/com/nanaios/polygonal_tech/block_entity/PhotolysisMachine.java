package com.nanaios.polygonal_tech.block_entity;

import com.nanaios.polygonal_tech.block_entity.base.BaseMachine;
import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;
import com.nanaios.polygonal_tech.container.ContainerBuilder;
import com.nanaios.polygonal_tech.container.impl.LongEnergyContainer;
import com.nanaios.polygonal_tech.registries.PolygonalTechBlockEntityTypeRegister;
import com.nanaios.polygonal_tech.util.Directions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class PhotolysisMachine extends BaseMachine<PhotolysisMachine> {
    public LongEnergyContainer longEnergyContainer;

    public PhotolysisMachine(BlockPos pos, BlockState state) {
        super(PolygonalTechBlockEntityTypeRegister.PHOTOLYSIS_MACHINE.get(), pos, state);
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
}
