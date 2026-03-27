package com.nanaios.polygonal_tech.main.block_entity.base;

import com.nanaios.polygonal_tech.main.PolygonalTech;
import com.nanaios.polygonal_tech.main.menu.base.BaseMenu;
import com.nanaios.polygonal_tech.main.network.PolygonalTechNetwork;
import com.nanaios.polygonal_tech.main.network.packet.ClientBoundBlockEntityBufPacket;
import com.nanaios.polygonal_tech.lib.registration.impl.DeferredBlockEntityTypeRegister;
import com.nanaios.polygonal_tech.lib.registration.impl.DeferredMenuTypeRegister;
import com.nanaios.polygonal_tech.main.util.NamedToken;
import com.nanaios.polygonal_tech.main.util.sync.SyncedValue;
import com.nanaios.polygonal_tech.main.util.sync.SynchronizeMap;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseGuiMachine<M extends BaseGuiMachine<M>> extends BaseMachine<M> implements MenuProvider {
    protected final NamedToken token;
    @SuppressWarnings("rawtypes")
    protected final List<SyncedValue> syncedGuiFields;
    protected boolean[] markedForGuiSync;
    public final List<ServerPlayer> guiViewer = new ArrayList<>();

    public BaseGuiMachine(RegistryObject<BlockEntityType<M>> type, BlockPos pos, BlockState state) {
        super(type.get(), pos, state);

        // RegistryObject<BlockEntityType<M>>はRegistryObject<BlockEntityType<?>>のサブクラスであるため、キャスト可能
        token = DeferredBlockEntityTypeRegister.getToken(type);

        // GUIで同期するFieldを取得
        syncedGuiFields = createSyncedField(SynchronizeMap.inGuiSynchronizedFields.getOrDefault(this.getClass(), List.of()));
        if(!syncedGuiFields.isEmpty()) {
            markedForGuiSync = new boolean[syncedGuiFields.size()];
        }
    }

    public void forceWriteSyncGuiData(FriendlyByteBuf buf) {
        boolean[] allTrue = new boolean[syncedGuiFields.size()];
        Arrays.fill(allTrue, true);
        buf.writeBoolean(true);
        writeSyncDataFromFields(syncedGuiFields, allTrue, buf);
    }

    /// GUIを開いているときにのみ送るデータ。これにより、GUIを開いていないときのネットワーク負荷を減らすことができる。
    public void writeSyncGuiData(FriendlyByteBuf buf) {
        buf.writeBoolean(true);
        writeSyncDataFromFields(syncedGuiFields, markedForGuiSync, buf);
    }

    /// GUIを開いているときにのみ受け取るデータ。これにより、GUIを開いていないときのネットワーク負荷を減らすことができる。
    public void readSyncGuiData(FriendlyByteBuf buf) {
        readSyncDataToFields(syncedGuiFields, buf);
    }

    @SuppressWarnings("rawtypes")
    private void checkAndSyncGuiData() {
        if(level == null || level.isClientSide) return;

        boolean needsSync = false;
        // 同期対象のフィールドをチェックし、変更があった場合はmarkedForSyncを更新する。これにより、変更されたフィールドのみがクライアントに送信されるようになる。
        for (int i = 0; i < syncedGuiFields.size(); i++) {
            SyncedValue value = syncedGuiFields.get(i);
            if (value.isChanged()) {
                needsSync = true;
                markedForGuiSync[i] = true;
            }
        }

        if(!needsSync) return;

        // 対象Playerにパケットを送信
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        writeSyncGuiData(buf);

        for (ServerPlayer serverPlayer : guiViewer) {
            PolygonalTechNetwork.CHANNEL.send(
                    PacketDistributor.PLAYER.with(() -> serverPlayer),
                    new ClientBoundBlockEntityBufPacket(this.worldPosition, level.dimension(), buf)
            );
        }
        for (SyncedValue syncedGuiField : syncedGuiFields) {
            syncedGuiField.onSynced();
        }
    }

    @Override
    public boolean afterServerTick(Level level, BlockPos pos, BlockState state, M blockEntity) {
        checkAndSyncGuiData();
        return super.afterServerTick(level, pos, state, blockEntity);
    }

    @Override
    public void writeSyncData(FriendlyByteBuf buf) {
        // ClientBoundBlockEntityBufPacket.createによってwriteSyncDataが呼び出されたときはGUIデータではないと判断する
        buf.writeBoolean(false);
        super.writeSyncData(buf);
    }

    @Override
    public void readSyncData(FriendlyByteBuf buf) {
        // BaseGuiMachineはClientBoundBlockEntityBufPacketの先頭にGUIデータかを示すboolean値を書き込む。これにより、GUIを開いているときのみGUIデータを読み込むことができる。
        boolean isGuiData = buf.readBoolean();
        if (isGuiData) {
            readSyncGuiData(buf);
        } else {
            super.readSyncData(buf);
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.%1$s.%2$s".formatted(PolygonalTech.MODID,token.name()));
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, @NotNull Player player) {
        return new BaseMenu<>(DeferredMenuTypeRegister.getRegistry(token).get(), id, inv, this.worldPosition);
    }
}
