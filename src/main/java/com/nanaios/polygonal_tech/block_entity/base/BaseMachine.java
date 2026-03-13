package com.nanaios.polygonal_tech.block_entity.base;

import com.nanaios.polygonal_tech.capability.energy.LongEnergyProvider;
import com.nanaios.polygonal_tech.capability.fluid.LongFluidProvider;
import com.nanaios.polygonal_tech.capability.item.ItemSlotProvider;
import com.nanaios.polygonal_tech.event.CapabilityUpdateEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseMachine<M extends BaseMachine<M>> extends BaseBlockEntity<M> {
    public final LongEnergyProvider energyProvider = new LongEnergyProvider(this::capabilityUpdateListener);
    public final LongFluidProvider fluidProvider = new LongFluidProvider(this::capabilityUpdateListener);
    public final ItemSlotProvider itemSlotProvider = new ItemSlotProvider(this::capabilityUpdateListener);

    public BaseMachine(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    /// Capabilityの状態が更新されたときに呼び出されるリスナー。BlockEntityの状態を更新するためにsetChanged()を呼び出す。
    public void capabilityUpdateListener(CapabilityUpdateEvent event) {
        setChanged();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return super.getCapability(cap, side);
    }

    @Override
    public void save(CompoundTag tag) {
        super.save(tag);
        tag.put(LongEnergyProvider.NBT_LONG_ENERGY, energyProvider.serializeNBT());
        tag.put(LongFluidProvider.NBT_LONG_FLUID, fluidProvider.serializeNBT());
        tag.put(ItemSlotProvider.NBT_ITEM_SLOTS, itemSlotProvider.serializeNBT());
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        if (tag.contains(LongEnergyProvider.NBT_LONG_ENERGY)) {
            energyProvider.deserializeNBT(tag.getCompound(LongEnergyProvider.NBT_LONG_ENERGY));
        }
        if (tag.contains(LongFluidProvider.NBT_LONG_FLUID)) {
            fluidProvider.deserializeNBT(tag.getCompound(LongFluidProvider.NBT_LONG_FLUID));
        }
        if (tag.contains(ItemSlotProvider.NBT_ITEM_SLOTS)) {
            itemSlotProvider.deserializeNBT(tag.getCompound(ItemSlotProvider.NBT_ITEM_SLOTS));
        }
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
