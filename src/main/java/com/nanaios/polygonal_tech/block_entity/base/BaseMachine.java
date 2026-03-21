package com.nanaios.polygonal_tech.block_entity.base;

import com.mojang.datafixers.util.Pair;
import com.nanaios.polygonal_tech.PolygonalTech;
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
import com.nanaios.polygonal_tech.network.PolygonalTechNetwork;
import com.nanaios.polygonal_tech.network.packet.ClientBoundBlockEntityBufPacket;
import com.nanaios.polygonal_tech.util.save.SaveToNBTMap;
import com.nanaios.polygonal_tech.util.sync.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;
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
    protected boolean wantSync;
    protected Direction facing = Direction.NORTH;

    public BaseMachine(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        if(state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        }

        syncedFields = createSyncedField(SynchronizeMap.alwaysSynchronizedFields.getOrDefault(this.getClass(), List.of()));

        if (!syncedFields.isEmpty()) {
            markedForSync = new boolean[syncedFields.size()];
        }

        initEnergyStorage().register(energyProvider);
        initFluidTank().register(fluidProvider);
        initItemSlot().register(itemSlotProvider);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void save(CompoundTag tag) {
        super.save(tag);

        List<Pair<Field,String>> fields = SaveToNBTMap.saveToNBTFields.get(this.getClass());
        for (Pair<Field, String> pair : fields) {
            Field field = pair.getFirst();
            String key = pair.getSecond();

            Class fieldType = field.getType();
            Object value;
            try {
                value = field.get(this);
            } catch (IllegalAccessException e) {
                PolygonalTech.LOGGER.error("Failed to access field for SaveToNBT: {} in class {}", field.getName(), this.getClass().getName(), e);
                continue;
            }
            if(fieldType == int.class) {
                tag.putInt(key, (Integer) value);
            } else if(fieldType == boolean.class) {
                tag.putBoolean(key, (Boolean) value);
            } else if(fieldType == long.class) {
                tag.putLong(key, (Long) value);
            } else if(fieldType == float.class) {
                tag.putFloat(key, (Float) value);
            } else if(fieldType == double.class) {
                tag.putDouble(key, (Double) value);
            } else if(fieldType == String.class) {
                tag.putString(key, (String) value);
            } else if(INBTSerializable.class.isAssignableFrom(fieldType)) {
                tag.put(key, ((INBTSerializable) value).serializeNBT());
            } else {
                PolygonalTech.LOGGER.warn("Unsupported save field type for SaveToNBT: {} in class {}", fieldType.getName(), this.getClass().getName());
            }
        }
    }

    @SuppressWarnings({"rawtypes","unchecked"})
    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);

        List<Pair<Field,String>> fields = SaveToNBTMap.saveToNBTFields.get(this.getClass());
        for (Pair<Field, String> pair : fields) {
            Field field = pair.getFirst();
            String key = pair.getSecond();

            if(!tag.contains(key)) {
                PolygonalTech.LOGGER.warn("Missing key for SaveToNBT: {} in class {}", key, this.getClass().getName());
                continue;
            }

            Class fieldType = field.getType();
            try {
                if(fieldType == int.class) {
                    field.set(this, tag.getInt(key));
                } else if(fieldType == boolean.class) {
                    field.set(this, tag.getBoolean(key));
                } else if(fieldType == long.class) {
                    field.set(this, tag.getLong(key));
                } else if(fieldType == float.class) {
                    field.set(this, tag.getFloat(key));
                } else if(fieldType == double.class) {
                    field.set(this, tag.getDouble(key));
                } else if(fieldType == String.class) {
                    field.set(this, tag.getString(key));
                } else if(INBTSerializable.class.isAssignableFrom(fieldType)) {
                    INBTSerializable instance = (INBTSerializable) field.get(this);
                    instance.deserializeNBT(tag.get(key));
                } else {
                    PolygonalTech.LOGGER.warn("Unsupported load field type for SaveToNBT: {} in class {}", fieldType.getName(), this.getClass().getName());
                }
            } catch (IllegalAccessException e) {
                PolygonalTech.LOGGER.error("Failed to access field for SaveToNBT: {} in class {}", field.getName(), this.getClass().getName(), e);
            }
        }
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
        if (wantSync && level != null && !level.isClientSide) {
            ClientBoundBlockEntityBufPacket packet = ClientBoundBlockEntityBufPacket.create(this);
            if (packet == null) return;
            PolygonalTechNetwork.CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), packet);
            wantSync = false;

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

    public void writeSyncData(FriendlyByteBuf buf) {
        writeSyncDataFromFields(syncedFields, markedForSync, buf);
    }

    public void readSyncData(FriendlyByteBuf buf) {
        readSyncDataToFields(syncedFields, buf);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        wantSync = true;
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
        // 機械のfacingを基準に、ローカル方向をワールド方向へ変換する。これにより、BlockStateProperties.HORIZONTAL_FACINGで指定された向きに応じて、正面・背面・右・左の方向が自動的に切り替わるようになる。
        Direction face = toWorldDirection(side,facing);

        if (cap == ForgeCapabilities.ENERGY || cap == PolygonalTechCapabilities.LONG_ENERGY) {
            return energyProvider.getCapability(cap, face);
        }
        if (cap == ForgeCapabilities.FLUID_HANDLER || cap == PolygonalTechCapabilities.LONG_FLUID_HANDLER) {
            return fluidProvider.getCapability(cap, face);
        }
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return itemSlotProvider.getCapability(cap, face);
        }
        return super.getCapability(cap, face);
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


    ///機械のfacingを基準に、ローカル方向をワールド方向へ変換する
    ///@param facing  機械のfacing。BlockStateProperties.HORIZONTAL_FACINGで指定されていることを想定している
    ///@param local   機械内部で想定している基底の方向
    public static Direction toWorldDirection(Direction facing, Direction local) {
        return switch (local) {
            case NORTH -> facing;                        // 正面
            case SOUTH -> facing.getOpposite();          // 背面
            case EAST  -> facing.getCounterClockWise();  // 右
            case WEST  -> facing.getClockWise();         // 左
            case UP    -> Direction.UP;
            case DOWN  -> Direction.DOWN;
        };
    }
}
