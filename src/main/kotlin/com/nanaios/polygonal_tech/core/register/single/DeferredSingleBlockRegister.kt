package com.nanaios.polygonal_tech.core.register.single

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraftforge.registries.ForgeRegistries

/**
 * [Block]に限定して登録を管理し、登録されたオブジェクトを[ResourceLocation]から一元的に取得（検索）できるようにすることを目的とするクラス。
 * 内部で保持するキャッシュマップにより、IDをキーとして安全に各ブロックへアクセスできる。
 *
 * @param modId 対象のMODのID
 */
class DeferredSingleBlockRegister(modId: String): DeferredSingleRegister<Block>(
    ForgeRegistries.BLOCKS,modId
)