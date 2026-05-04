package com.nanaios.polygonal_tech.core.tile

import com.nanaios.polygonal_tech.core.network.PolygonalTechNetwork
import com.nanaios.polygonal_tech.core.network.s2c.SyncValuesPacket
import com.nanaios.polygonal_tech.core.network.sync.ISyncValue
import com.nanaios.polygonal_tech.core.network.sync.type.SyncType
import com.nanaios.polygonal_tech.core.register.single.DeferredSingleTileTypeRegister
import com.nanaios.polygonal_tech.core.register.single.TileType
import io.netty.buffer.Unpooled
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.network.PacketDistributor

abstract class SingleTile(
    protected val id: ResourceLocation,
    protected val tileType: TileType<*>,
    protected val pos: BlockPos,
    protected val state: BlockState
): BlockEntity(tileType, pos, state), ISingleTile {
    override val tileLevel: Level?
        get() = level
    override val tilePos: BlockPos
        get() = pos
    override val defaultFront: Direction
        get() = Direction.NORTH

    protected val syncValues: MutableList<ISyncValue> = mutableListOf()

    protected var isChanged = false

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

    final override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        save(tag)
    }

    open fun save(tag: CompoundTag) {
        syncValues.forEachIndexed { index, value ->
            if(value.isSaving) {
                tag.put("save_$index",value.serializeNBT())
            }
        }
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        syncValues.forEachIndexed { index, value ->
            if(tag.contains("save_$index")) {
                value.deserializeNBT(tag.getCompound("save_$index"))
            }
        }
    }

    override fun addValue(value: ISyncValue) {
        syncValues.add(value)
    }

    override fun removeValue(value: ISyncValue) {
        syncValues.remove(value)
    }

    override fun onSyncValueChanged(value: ISyncValue) {
        isChanged = true
        setChanged()
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
        if(!isChanged) return
        isChanged = false

        val buf = FriendlyByteBuf(Unpooled.buffer())
        var count = 0

        syncValues.forEachIndexed { index, value ->
            if(value.isDirty && value.type == SyncType.ALWAYS) {
                buf.writeInt(index)
                value.writeBuffer(buf)
                value.onSync()
                count++
            }
        }

        buf.writeInt(count)

        val level = this.level ?: return

        val packet = SyncValuesPacket(
            level.dimension().location(),
            pos,
            buf
        )

        PolygonalTechNetwork.CHANNEL.send(
            PacketDistributor.TRACKING_CHUNK.with { level.getChunkAt(pos) },
            packet,
        )
    }

    protected open fun onServerTick(level: Level, pos: BlockPos, state: BlockState) = Unit
    protected open fun onClientTick(level: Level, pos: BlockPos, state: BlockState) = Unit
}