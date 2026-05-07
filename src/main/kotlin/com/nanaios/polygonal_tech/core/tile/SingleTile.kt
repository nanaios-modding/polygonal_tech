package com.nanaios.polygonal_tech.core.tile

import com.nanaios.polygonal_tech.core.network.PolygonalTechNetwork
import com.nanaios.polygonal_tech.core.network.s2c.SyncValuesPacket
import com.nanaios.polygonal_tech.core.network.sync.ISyncValue
import com.nanaios.polygonal_tech.core.network.sync.type.SyncType
import com.nanaios.polygonal_tech.core.register.single.DeferredSingleTileTypeRegister
import com.nanaios.polygonal_tech.core.register.single.TileType
import com.nanaios.polygonal_tech.main.PolygonalTech
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
import java.util.Collections

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

    val isClientSide: Boolean
        get() = level?.isClientSide ?: false

    protected val _syncValues: MutableList<ISyncValue> = mutableListOf()
    /** 登録されている全同期対象値（[ISyncValue]）への読み取り専用アクセス。 */
    val syncValues: List<ISyncValue> = Collections.unmodifiableList(_syncValues)

    /**
     * TileTypeが[DeferredSingleTileTypeRegister]（もしくはその継承クラス）を通して登録されていることを前提とし、
     * IDから自動的に[TileType]を逆引きして生成することを目的としたショートカットコンストラクタ。
     * 万が一これ以外のレジストリを使用している場合は、[TileType]を直接渡すコンストラクタを使用すること。
     *
     * @param id タイルの識別子
     * @param pos 配置座標
     * @param state ブロックステート
     */
    constructor(id:ResourceLocation, pos:BlockPos, state:BlockState):this(
        id,
        DeferredSingleTileTypeRegister.getTileTypeRegistryObject(id)?.get()!!,
        pos,
        state
    )

    override fun onLoad() {
        super.onLoad()
        if(isClientSide) return
        PolygonalTech.LOGGER.debug("Loading {} in {}:{}", id,level,pos)
        PolygonalTech.LOGGER.debug("syncValue:{}", _syncValues)
    }

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

    /**
     * 全[ISyncValue]の中でセーブ対象となっているフィールドをNBTへ書き込むことを目的とする。
     * 
     * @param tag 保存先の[CompoundTag]
     */
    open fun save(tag: CompoundTag) {
        _syncValues.forEachIndexed { index, value ->
            if(value.isSaving) {
                tag.put("save_$index",value.serializeNBT())
            }
        }
    }

    /**
     * NBTタグから値を読み出し、各[ISyncValue]に復元することを目的とする。
     *
     * @param tag 読み込み元の[CompoundTag]
     */
    override fun load(tag: CompoundTag) {
        super.load(tag)
        _syncValues.forEachIndexed { index, value ->
            if(tag.contains("save_$index")) {
                value.deserializeNBT(tag.getCompound("save_$index"))
            }
        }
    }

    override fun addValue(value: ISyncValue) {
        _syncValues.add(value)
    }

    override fun removeValue(value: ISyncValue) {
        _syncValues.remove(value)
    }

    override fun onSyncValueChanged(value: ISyncValue) {
        setChanged()
    }

    open fun readSyncValue(buf: FriendlyByteBuf) {
        if(buf.writerIndex() < Int.SIZE_BYTES) return

        val count = buf.getInt(buf.writerIndex() - Int.SIZE_BYTES)
        for(i in 0 until count) {
            val index = buf.readInt()
            _syncValues[index].readBuffer(buf)
        }
    }

    /**
     * 内部で保持している[ISyncValue]のうち、同期が必要であると判定された値だけを抽出し、
     * クライアント側に同期用パケット（[SyncValuesPacket]）として送信することを目的とする。
     */
    protected open fun sendSyncPacket() {
        val buf = FriendlyByteBuf(Unpooled.buffer())
        var count = 0

        _syncValues.forEachIndexed { index, value ->
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

    /**
     * サーバー側で定期実行される個別のビジネスロジックを実装することを目的としたメソッド。
     * 継承先で具象処理（例: エネルギーの消費や精錬の進行など）を記述する。
     *
     * @param level 実行されているワールド
     * @param pos 対象位置
     * @param state 現在のブロックステート
     */
    protected open fun onServerTick(level: Level, pos: BlockPos, state: BlockState) = Unit
    /**
     * クライアント側で定期実行される描画やアニメーション更新のロジックを提供することを目的とするメソッド。
     *
     * @param level 実行されているワールド
     * @param pos 対象位置
     * @param state 現在のブロックステート
     */
    protected open fun onClientTick(level: Level, pos: BlockPos, state: BlockState) = Unit
}