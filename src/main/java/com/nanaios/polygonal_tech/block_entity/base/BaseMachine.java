package com.nanaios.polygonal_tech.block_entity.base;

import com.nanaios.polygonal_tech.capability.CapabilityBuilder;
import com.nanaios.polygonal_tech.capability.PolygonalTechCapabilities;
import com.nanaios.polygonal_tech.capability.energy.LongEnergyProvider;
import com.nanaios.polygonal_tech.capability.fluid.LongFluidProvider;
import com.nanaios.polygonal_tech.capability.interfaces.ICapability;
import com.nanaios.polygonal_tech.capability.interfaces.IItemSlot;
import com.nanaios.polygonal_tech.capability.interfaces.ILongEnergyStorage;
import com.nanaios.polygonal_tech.capability.interfaces.ILongFluidTank;
import com.nanaios.polygonal_tech.capability.item.ItemSlotProvider;
import com.nanaios.polygonal_tech.event.CapabilityUpdateEvent;
import com.nanaios.polygonal_tech.util.sync.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
    @SuppressWarnings("rawtypes")
    protected final List<SyncedValue> syncedFields;
    protected boolean[] markedForSync;

    public BaseMachine(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        syncedFields = createSyncedField(SynchronizeMap.alwaysSynchronizedFields.getOrDefault(this.getClass(), List.of()));

        if (!syncedFields.isEmpty()) {
            markedForSync = new boolean[syncedFields.size()];
        }

        initEnergyStorage().register(energyProvider);
        initFluidTank().register(fluidProvider);
        initItemSlot().register(itemSlotProvider);
    }

    @SuppressWarnings("rawtypes")
    public static void writeSyncDataFromFields(List<SyncedValue> syncedFields, boolean[] markedForSync, FriendlyByteBuf buf) {
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

    @SuppressWarnings("rawtypes")
    public static void readSyncDataToFields(List<SyncedValue> syncedFields, FriendlyByteBuf buf) {
        int size = buf.readInt();

        for (int i = 0; i < size; i++) {
            int index = buf.readInt();
            syncedFields.get(index).readFromFriendlyByteBuf(buf);
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void sendSyncPacket() {
        super.sendSyncPacket();
        if (level != null && !level.isClientSide) {
            for (SyncedValue syncedField : syncedFields) {
                syncedField.onSynced();
            }
        }
    }

    @SuppressWarnings("rawtypes")
    protected List<SyncedValue> createSyncedField(List<Field> fields) {
        List<SyncedValue> syncedFields = new ArrayList<>();
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            if (fieldType == int.class) {
                syncedFields.add(new SyncedInt(field, this));
            } else if (fieldType == boolean.class) {
                syncedFields.add(new SyncedBoolean(field, this));
            } else if (fieldType == long.class) {
                syncedFields.add(new SyncedLong(field, this));
            } else if (ICapability.class.isAssignableFrom(fieldType)) {
                syncedFields.add(new SyncedCapability(field, this));
            }
        }

        return syncedFields;
    }

    @Override
    public void writeSyncData(FriendlyByteBuf buf) {
        writeSyncDataFromFields(syncedFields, markedForSync, buf);
    }

    @Override
    public void readSyncData(FriendlyByteBuf buf) {
        readSyncDataToFields(syncedFields, buf);
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

    /// 入力・出力問わず外部に公開する必要のあるILongEnergyStorageを初期化するためのメソッド
    /// このメソッドによってCapabilityBuilderに登録されたILongEnergyStorageは、BlockEntityのgetCapability()メソッドで自動的に公開されるようになる。
    public CapabilityBuilder<ILongEnergyStorage> initEnergyStorage() {
        return new CapabilityBuilder<>();
    }

    /// 入力・出力問わず外部に公開する必要のあるILongFluidTankを初期化するためのメソッド
    /// このメソッドによってCapabilityBuilderに登録されたILongFluidTankは、BlockEntityのgetCapability()メソッドで自動的に公開されるようになる。
    public CapabilityBuilder<ILongFluidTank> initFluidTank() {
        return new CapabilityBuilder<>();
    }

    /// 入力・出力問わず外部に公開する必要のあるIItemSlotを初期化するためのメソッド
    /// このメソッドによってCapabilityBuilderに登録されたIItemSlotは、BlockEntityのgetCapability()メソッドで自動的に公開されるようになる。
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
    public void reviveCaps() {
        super.reviveCaps();
        energyProvider.reviveCaps();
        fluidProvider.reviveCaps();
        itemSlotProvider.reviveCaps();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        energyProvider.invalidateCaps();
        fluidProvider.invalidateCaps();
        itemSlotProvider.invalidateCaps();
    }
}
