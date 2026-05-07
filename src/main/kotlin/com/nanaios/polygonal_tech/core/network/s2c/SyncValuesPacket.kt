package com.nanaios.polygonal_tech.core.network.s2c

import com.nanaios.polygonal_tech.core.tile.SingleTile
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

/**
 * サーバー側で変更が検知された[ISyncValue]の更新データを、クライアント側へ一括送信することを目的としたパケット。
 * 複数の同期対象フィールドのバイナリ表現と、対象となるタイルエンティティの位置情報を内包する。
 *
 * @param dimension 対象となるワールドのディメンションID（[ResourceLocation]）
 * @param pos 対象となるブロックエンティティが存在する座標（[BlockPos]）
 * @param buf [ISyncValue]が書き込まれたバイナリペイロード（[FriendlyByteBuf]）
 */
open class SyncValuesPacket(
    protected val dimension: ResourceLocation,
    protected val pos: BlockPos,
    protected val buf: FriendlyByteBuf
) {
    /**
     * パケットをネットワークに送信する際、情報をバイトバッファへ書き込むことを目的とするメソッド。
     *
     * @param buf 送信用の[FriendlyByteBuf]
     */
    fun encode(buf: FriendlyByteBuf) {
        buf.writeResourceLocation(dimension)
        buf.writeBlockPos(pos)
        buf.writeBytes(this.buf)
    }

    companion object {
        /**
         * ネットワークから受信したバイナリデータを読み取り、[SyncValuesPacket]インスタンスを復元することを目的とするメソッド。
         *
         * @param buf 受信した[FriendlyByteBuf]
         * @return 復元された[SyncValuesPacket]
         */
        fun decode(buf: FriendlyByteBuf): SyncValuesPacket {
            return SyncValuesPacket(
                buf.readResourceLocation(),
                buf.readBlockPos(),
                buf
            )
        }

        /**
         * 受信したパケットを解釈し、クライアント側のワールドに存在する該当のタイルエンティティ（[SingleTile]）に対して
         * 同期データの読み込み処理を委譲（[SingleTile.readSyncValue]を実行）することを目的とするメソッド。
         * 実際の処理はメインスレッドでエンキューされて安全に実行される。
         *
         * @param packet 受信した同期パケット
         * @param ctx ネットワークイベントコンテキストを提供する[Supplier]
         */
        fun handle(packet: SyncValuesPacket, ctx: Supplier<NetworkEvent.Context>) {
            val ctx = ctx.get()
            ctx.enqueueWork {
                val level = Minecraft.getInstance().level ?: return@enqueueWork

                if(level.dimension().location() != packet.dimension) return@enqueueWork

                val blockEntity = level.getBlockEntity(packet.pos)
                if(blockEntity !is SingleTile) return@enqueueWork
                blockEntity.readSyncValue(packet.buf)
            }
            ctx.packetHandled = true
        }
    }
}