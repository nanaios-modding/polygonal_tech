package com.nanaios.polygonal_tech.lib.register

import net.minecraft.world.item.Item
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

open class DeferredItemRegister(modId: String): DeferredExtendRegister<Item>(DeferredRegister.create(ForgeRegistries.ITEMS,modId)) {
}