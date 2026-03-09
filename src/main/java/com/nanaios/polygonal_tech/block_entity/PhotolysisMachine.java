package com.nanaios.polygonal_tech.block_entity;

import com.nanaios.polygonal_tech.block_entity.base.BaseGuiMachine;
import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;
import com.nanaios.polygonal_tech.container.ContainerBuilder;
import com.nanaios.polygonal_tech.container.impl.LongEnergyContainer;
import com.nanaios.polygonal_tech.registries.PolygonalTechBlockEntityTypeRegister;
import com.nanaios.polygonal_tech.registries.PolygonalTechMenuTypeRegister;
import com.nanaios.polygonal_tech.util.Directions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class PhotolysisMachine extends BaseGuiMachine<PhotolysisMachine> {
    public LongEnergyContainer longEnergyContainer;

    public PhotolysisMachine(BlockPos pos, BlockState state) {
        super(PolygonalTechBlockEntityTypeRegister.PHOTOLYSIS_MACHINE, pos, state);
    }

    @Override
    public boolean serverTick(Level level, BlockPos pos, BlockState state, PhotolysisMachine blockEntity) {
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
}
