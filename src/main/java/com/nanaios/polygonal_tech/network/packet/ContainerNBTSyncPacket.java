package com.nanaios.polygonal_tech.network.packet;

import com.nanaios.polygonal_tech.network.base.BasePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class ContainerNBTSyncPacket extends BasePacket<ContainerNBTSyncPacket> {
    private int providerIndex;
    private int containerIndex;
    private BlockPos pos;
    private CompoundTag nbt;
    public ContainerNBTSyncPacket(int providerIndex, int containerIndex, BlockPos pos, CompoundTag nbt) {
        this.providerIndex = providerIndex;
        this.containerIndex = containerIndex;
        this.pos = pos;
        this.nbt = nbt;
    }

    public ContainerNBTSyncPacket() {}
    @Override
    public void handle(ContainerNBTSyncPacket msg, NetworkEvent.Context context) {
        context.enqueueWork(() -> {});
    }

    @Override
    public void encode(ContainerNBTSyncPacket msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.providerIndex);
        buffer.writeInt(msg.containerIndex);
        buffer.writeBlockPos(msg.pos);
        buffer.writeNbt(msg.nbt);
    }

    @Override
    public ContainerNBTSyncPacket decode(FriendlyByteBuf buffer) {
        return new ContainerNBTSyncPacket(
                buffer.readInt(),
                buffer.readInt(),
                buffer.readBlockPos(),
                buffer.readNbt()
        );
    }
}
