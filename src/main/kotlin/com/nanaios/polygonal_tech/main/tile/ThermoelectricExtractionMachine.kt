package com.nanaios.polygonal_tech.main.tile

import com.nanaios.polygonal_tech.core.capability.fluid.LongFluidTank
import com.nanaios.polygonal_tech.core.capability.item.ItemSlot
import com.nanaios.polygonal_tech.core.fluid.LongFluidStack
import com.nanaios.polygonal_tech.core.network.sync.SyncIntValue
import com.nanaios.polygonal_tech.core.network.sync.bind
import com.nanaios.polygonal_tech.core.network.sync.on
import com.nanaios.polygonal_tech.core.network.sync.type.SyncType
import com.nanaios.polygonal_tech.core.tile.SingleGuiMachineTile
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.ForgeHooks

class ThermoelectricExtractionMachine(
    id: ResourceLocation,
    pos: BlockPos,
    state: BlockState
) : SingleGuiMachineTile(id, pos, state) {
    val burnTime:Int by SyncIntValue() on SyncType.GUI_OPENED bind this
    val thermTank = LongFluidTank(LongFluidStack.EMPTY,0L,4000L) { stack -> true } on SyncType.GUI_OPENED bind this
    val fuelSlot = ItemSlot(10,10, ItemStack.EMPTY) { stack -> ForgeHooks.getBurnTime(stack,null) > 0 } on SyncType.GUI_OPENED bind this

    init {
        capability {
        }
    }
}