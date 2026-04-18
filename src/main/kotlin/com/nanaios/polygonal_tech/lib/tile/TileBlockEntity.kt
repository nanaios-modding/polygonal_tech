package com.nanaios.polygonal_tech.lib.tile

import com.nanaios.polygonal_tech.lib.network.PolygonalTechNetwork
import com.nanaios.polygonal_tech.lib.network.s2c.SyncValuesPacket
import com.nanaios.polygonal_tech.lib.register.single.DeferredSingleTileTypeRegister
import com.nanaios.polygonal_tech.lib.register.single.TileType
import com.nanaios.polygonal_tech.lib.util.sync.value.ISyncValue
import com.nanaios.polygonal_tech.lib.util.sync.value.SyncType
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.network.PacketDistributor

open class TileBlockEntity(
    protected val id: ResourceLocation,
    protected val tileType: TileType<*>,
    protected val pos: BlockPos,
    protected val state: BlockState
): BlockEntity(tileType, pos, state), ITile {
    protected val syncValues: MutableList<ISyncValue> = mutableListOf()

    /**
     * TileTypeが[DeferredSingleTileTypeRegister]もしくはその継承クラスを通して登録されていることを前提としたconstructor。
     * 上記以外のDeferredRegisterを使用している場合は、TileTypeを直接渡すconstructorを使用してください。
     * */
    constructor(id:ResourceLocation, pos:BlockPos, state:BlockState):this(
        id,
        DeferredSingleTileTypeRegister.getTileTypeRegistryObject(id)?.get()!!,
        pos,
        state
    )

    final override fun serverTick(
        level: Level,
        pos: BlockPos,
        state: BlockState
    ) {
        onServerTick(level, pos, state)
        sendSyncPacket()
    }

    final override fun clientTick(
        level: Level,
        pos: BlockPos,
        state: BlockState
    ) {
        onClientTick(level, pos, state)
    }

    override fun addValue(value: ISyncValue) {
        syncValues.add(value)
    }

    fun readSyncValue(buf: FriendlyByteBuf) {
        if(buf.writerIndex() < Int.SIZE_BYTES) return

        val count = buf.getInt(buf.writerIndex() - Int.SIZE_BYTES)
        for(i in 0 until count) {
            val index = buf.readInt()
            syncValues[index].readBuffer(buf)
        }
    }

    protected fun sendSyncPacket() {
        val packet = SyncValuesPacket(pos)
        packet.setBufWriter { buf ->
            var count = 0

            syncValues.forEachIndexed { index, value ->
                if(value.syncType == SyncType.ALWAYS && value.isDirty) {
                    // indexは値の種類を識別するためのもの。受信側でこのindexをもとにどの値が送られてきたのかを判断する。
                    // valueは実際の値を書き込む。処理はISyncValueの継承クラスで定義されている。
                    buf.writeInt(index)
                    value.writeBuffer(buf)
                    value.onSync()
                    count++
                }
            }

            buf.writeInt(count)
        }

        PolygonalTechNetwork.CHANNEL.send(
            PacketDistributor.TRACKING_CHUNK.with { level?.getChunkAt(pos) },
            packet,
        )
    }

    protected open fun onServerTick(level: Level, pos: BlockPos, state: BlockState) = Unit
    protected open fun onClientTick(level: Level, pos: BlockPos, state: BlockState) = Unit
}