package com.nanaios.polygonal_tech.core.tile

import com.nanaios.polygonal_tech.core.menu.MachineMenu
import com.nanaios.polygonal_tech.core.network.PolygonalTechNetwork
import com.nanaios.polygonal_tech.core.network.s2c.SyncValuesPacket
import com.nanaios.polygonal_tech.core.network.sync.type.SyncType
import com.nanaios.polygonal_tech.core.register.registry.IRegistryObject
import com.nanaios.polygonal_tech.core.register.single.DeferredSingleMenuTypeRegister
import io.netty.buffer.Unpooled
import net.minecraft.Util
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.network.PacketDistributor

open class SingleGuiMachineTile(
    id: ResourceLocation,
    pos: BlockPos,
    state: BlockState
): SingleMachineTile(id,pos,state), MenuProvider {
    val viewGuiPlayers = mutableListOf<Player>()

    override fun getDisplayName(): Component = Component.translatable(Util.makeDescriptionId("block",id))

    open fun sendSyncGuiPacket() {
        val buf = FriendlyByteBuf(Unpooled.buffer())
        var count = 0

        _syncValues.forEachIndexed { index, value ->
            if(value.isDirty && value.type == SyncType.GUI_OPENED) {
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

        viewGuiPlayers.forEach { player ->
            PolygonalTechNetwork.CHANNEL.send(
                PacketDistributor.PLAYER.with { player as ServerPlayer },
                packet,
            )

        }
    }

    override fun sendSyncPacket() {
        super.sendSyncPacket()
        sendSyncGuiPacket()
    }

    override fun createMenu(
        windowId: Int,
        inventory: Inventory,
        player: Player
    ): AbstractContainerMenu? {
        val menuType = DeferredSingleMenuTypeRegister.getMenuTypeRegistryObject(id)
        return if(menuType != null) MachineMenu(id,menuType.get(),windowId,inventory,pos) else null
    }
}