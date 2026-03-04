package com.nanaios.polygonal_tech.block_entity.base;

import com.nanaios.polygonal_tech.capability.PolygonalTechCapabilities;
import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;
import com.nanaios.polygonal_tech.capability.provider.EnergyStorageProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseMachine<M extends BaseMachine<M>> extends BaseBlockEntity<M> {
    protected EnergyStorageProvider energyStorageProvider = new EnergyStorageProvider();

    public BaseMachine(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    protected void initEnergyContainer() {
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ENERGY || cap == PolygonalTechCapabilities.LONG_ENERGY) {
            return energyStorageProvider.getCapability(cap, side);
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
    }
}
