package com.nanaios.polygonal_tech.block_entity.base;

import com.nanaios.polygonal_tech.capability.CapabilityBuilder;
import com.nanaios.polygonal_tech.capability.PolygonalTechCapabilities;
import com.nanaios.polygonal_tech.capability.energy.LongEnergyProvider;
import com.nanaios.polygonal_tech.capability.fluid.LongFluidProvider;
import com.nanaios.polygonal_tech.capability.interfaces.IItemSlot;
import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;
import com.nanaios.polygonal_tech.capability.interfaces.ILongFluidTank;
import com.nanaios.polygonal_tech.capability.item.ItemSlotProvider;
import com.nanaios.polygonal_tech.event.CapabilityUpdateEvent;
import com.nanaios.polygonal_tech.util.sync.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseMachine<M extends BaseMachine<M>> extends BaseBlockEntity<M> {
    public final LongEnergyProvider energyProvider = new LongEnergyProvider(this::capabilityUpdateListener);
    public final LongFluidProvider fluidProvider = new LongFluidProvider(this::capabilityUpdateListener);
    public final ItemSlotProvider itemSlotProvider = new ItemSlotProvider(this::capabilityUpdateListener);
    protected final List<Field> alwaysSyncFields;
    @SuppressWarnings("rawtypes")
    protected final List<SyncedValue> syncedFields = new ArrayList<>();
    protected final boolean[] markedForSync;

    public BaseMachine(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        // 同期対象のフィールドを取得。これにより、サブクラスで@Synchronizeアノテーションが付けられたフィールドも自動的に同期されるようになる。
        alwaysSyncFields = SynchronizeMap.alwaysSynchronizedFields.getOrDefault(this.getClass(), List.of());
        for (Field field : alwaysSyncFields) {
            Class<?> fieldType = field.getType();

            if (fieldType == int.class) {
                syncedFields.add(new SyncedInt(field, this));
            } else if (fieldType == boolean.class) {
                syncedFields.add(new SyncedBoolean(field, this));
            } else if (fieldType == long.class) {
                syncedFields.add(new SyncedLong(field, this));
            }
        }

        markedForSync = new boolean[syncedFields.size() - 1];

        initEnergyStorage().register(energyProvider);
        initFluidTank().register(fluidProvider);
        initItemSlot().register(itemSlotProvider);
    }

    @Override
    public void writeSyncData(FriendlyByteBuf buf) {
        int size = 0;
        for (int i = 0; i < syncedFields.size(); i++) {
            if (markedForSync[i]) {
                size++;
            }
        }

        // 変更されたフィールドの数を最初に書き込む。これにより、クライアントは受信するフィールドの数を知ることができる。
        buf.writeInt(size);

        // 変更されたフィールドのインデックスと値を順番に書き込む。これにより、クライアントはどのフィールドが変更されたかを知ることができる。
        for (int i = 0; i < syncedFields.size(); i++) {
            if (markedForSync[i]) {
                buf.writeInt(i);
                syncedFields.get(i).writeToFriendlyByteBuf(buf);
                markedForSync[i] = false;
            }
        }
    }

    @Override
    public void readSyncData(FriendlyByteBuf buf) {
        int size = buf.readInt();

        for (int i = 0; i < size; i++) {
            int index = buf.readInt();
            syncedFields.get(index).readFromFriendlyByteBuf(buf);
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean afterServerTick(Level level, BlockPos pos, BlockState state, M blockEntity) {
        boolean needsSync = false;
        // 同期対象のフィールドをチェックし、変更があった場合はmarkedForSyncを更新する。これにより、変更されたフィールドのみがクライアントに送信されるようになる。
        for (int i = 0; i < syncedFields.size(); i++) {
            SyncedValue value = syncedFields.get(i);
            if (value.isChanged()) {
                needsSync = true;
                markedForSync[i] = true;
            }
        }
        return needsSync;
    }

    public CapabilityBuilder<ILongEnergyStorage> initEnergyStorage() {
        return new CapabilityBuilder<>();
    }

    public CapabilityBuilder<ILongFluidTank> initFluidTank() {
        return new CapabilityBuilder<>();
    }

    public CapabilityBuilder<IItemSlot> initItemSlot() {
        return new CapabilityBuilder<>();
    }

    /// Capabilityの状態が更新されたときに呼び出されるリスナー。BlockEntityの状態を更新するためにsetChanged()を呼び出す。
    public void capabilityUpdateListener(CapabilityUpdateEvent event) {
        setChanged();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY || cap == PolygonalTechCapabilities.LONG_ENERGY) {
            return energyProvider.getCapability(cap, side);
        }
        if (cap == ForgeCapabilities.FLUID_HANDLER || cap == PolygonalTechCapabilities.LONG_FLUID_HANDLER) {
            return fluidProvider.getCapability(cap, side);
        }
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return itemSlotProvider.getCapability(cap, side);
        }
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
