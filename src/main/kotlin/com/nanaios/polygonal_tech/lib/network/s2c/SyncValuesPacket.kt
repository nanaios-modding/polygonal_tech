package com.nanaios.polygonal_tech.lib.network.s2c

import com.nanaios.polygonal_tech.lib.tile.TileBlockEntity
import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

open class SyncValuesPacket(
    protected val dimension: ResourceLocation,
    protected val pos: BlockPos,
    protected val buf: FriendlyByteBuf
) {
    fun encode(buf: FriendlyByteBuf) {
        buf.writeResourceLocation(dimension)
        buf.writeBlockPos(pos)
        buf.writeBytes(this.buf)
    }

    companion object {
        fun decode(buf: FriendlyByteBuf): SyncValuesPacket {
            return SyncValuesPacket(
                buf.readResourceLocation(),
                buf.readBlockPos(),
                buf
            )
        }

        fun handle(packet: SyncValuesPacket, ctx: Supplier<NetworkEvent.Context>) {
            val ctx = ctx.get()
            ctx.enqueueWork {
                val level = Minecraft.getInstance().level ?: return@enqueueWork

                if(level.dimension().location() != packet.dimension) return@enqueueWork

                val blockEntity = level.getBlockEntity(packet.pos)
                if(blockEntity !is TileBlockEntity) return@enqueueWork
                blockEntity.readSyncValue(packet.buf)
            }
            ctx.packetHandled = true
        }
    }
}