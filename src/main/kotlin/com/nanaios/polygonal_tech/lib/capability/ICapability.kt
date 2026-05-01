package com.nanaios.polygonal_tech.lib.capability

import com.nanaios.polygonal_tech.lib.util.sync.value.ISyncValue
import net.minecraft.nbt.CompoundTag
import net.minecraftforge.common.util.INBTSerializable

interface ICapability: ISyncValue, INBTSerializable<CompoundTag>