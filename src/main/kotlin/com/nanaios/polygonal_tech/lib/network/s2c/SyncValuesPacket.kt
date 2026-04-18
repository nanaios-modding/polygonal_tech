package com.nanaios.polygonal_tech.lib.network.s2c

import com.nanaios.polygonal_tech.lib.tile.TileBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

open class SyncValuesPacket(
    val pos: BlockPos
) {
    protected var bufWriterSup = { buf:FriendlyByteBuf-> }
    fun encode(buf: FriendlyByteBuf) {
        buf.writeBlockPos(pos)
        bufWriterSup(buf)
    }
    fun setBufWriter(writer: (FriendlyByteBuf) -> Unit) {
        bufWriterSup = writer
    }

    companion object {
        fun decode(buf: FriendlyByteBuf): SyncValuesPacket {
            return SyncValuesPacket(
                buf.readBlockPos()
            )
        }

        fun handle(packet: SyncValuesPacket, ctx: Supplier<NetworkEvent.Context>) {
            val ctx = ctx.get()
            ctx.enqueueWork {
                val sender = ctx.sender ?: return@enqueueWork
                val level = sender.level()
                val blockEntity = level.getBlockEntity(packet.pos)
                if(blockEntity !is TileBlockEntity) return@enqueueWork


            }
            ctx.packetHandled = true
        }
    }
}