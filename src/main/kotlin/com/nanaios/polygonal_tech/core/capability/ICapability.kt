package com.nanaios.polygonal_tech.core.capability

import com.nanaios.polygonal_tech.core.network.sync.ISyncValue
import net.minecraft.nbt.CompoundTag
import net.minecraftforge.common.util.INBTSerializable

interface ICapability: ISyncValue, INBTSerializable<CompoundTag>