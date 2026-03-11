package com.nanaios.polygonal_tech.block_entity.base;

import com.nanaios.polygonal_tech.capability.PolygonalTechCapabilities;
import com.nanaios.polygonal_tech.capability.base.BaseProvider;
import com.nanaios.polygonal_tech.capability.interfaces.IItemSlot;
import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;
import com.nanaios.polygonal_tech.capability.interfaces.ILongFluidTank;
import com.nanaios.polygonal_tech.capability.provider.ItemSlotProvider;
import com.nanaios.polygonal_tech.capability.provider.LongEnergyStorageProvider;
import com.nanaios.polygonal_tech.capability.provider.LongFluidTankProvider;
import com.nanaios.polygonal_tech.container.ContainerBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseMachine<M extends BaseMachine<M>> extends BaseBlockEntity<M> {
    public final LongEnergyStorageProvider energyStorageProvider = new LongEnergyStorageProvider();
    public final LongFluidTankProvider fluidTankProvider = new LongFluidTankProvider();
    public final ItemSlotProvider itemSlotProvider = new ItemSlotProvider();

    public BaseMachine(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        initEnergyContainer().build(energyStorageProvider);
        initFluidContainer().build(fluidTankProvider);
        initItemContainer().build(itemSlotProvider);
    }

    protected ContainerBuilder<ILongEnergyStorage> initEnergyContainer() {
        return new ContainerBuilder<>();
    }

    protected ContainerBuilder<ILongFluidTank> initFluidContainer() {
        return new ContainerBuilder<>();
    }

    protected ContainerBuilder<IItemSlot> initItemContainer() {
        return new ContainerBuilder<>();
    }

    public int getProviderCount() {
        return 3;
    }

    public BaseProvider<?,?> getProvider(int index) {
        return switch (index) {
            case 0 -> energyStorageProvider;
            case 1 -> fluidTankProvider;
            case 2 -> itemSlotProvider;
            default -> null;
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY || cap == PolygonalTechCapabilities.LONG_ENERGY) {
            return energyStorageProvider.getCapability(cap, side);
        }
        if (cap == ForgeCapabilities.FLUID_HANDLER || cap == PolygonalTechCapabilities.LONG_FLUID_HANDLER) {
            return fluidTankProvider.getCapability(cap, side);
        }
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return itemSlotProvider.getCapability(cap, side);
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void save(CompoundTag tag) {
        super.save(tag);
        tag.put(LongEnergyStorageProvider.NBT_KEY, energyStorageProvider.serializeNBT());
        tag.put(LongFluidTankProvider.NBT_KEY, fluidTankProvider.serializeNBT());
        tag.put(ItemSlotProvider.NBT_KEY, itemSlotProvider.serializeNBT());
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);

        if (tag.contains(LongEnergyStorageProvider.NBT_KEY)) {
            energyStorageProvider.deserializeNBT(tag.getCompound(LongEnergyStorageProvider.NBT_KEY));
        }
        if (tag.contains(LongFluidTankProvider.NBT_KEY)) {
            fluidTankProvider.deserializeNBT(tag.getCompound(LongFluidTankProvider.NBT_KEY));
        }
        if (tag.contains(ItemSlotProvider.NBT_KEY)) {
            itemSlotProvider.deserializeNBT(tag.getCompound(ItemSlotProvider.NBT_KEY));
        }
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        energyStorageProvider.reviveCaps();
        fluidTankProvider.reviveCaps();
        itemSlotProvider.reviveCaps();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        energyStorageProvider.invalidateCaps();
        fluidTankProvider.invalidateCaps();
        itemSlotProvider.invalidateCaps();
    }
}
