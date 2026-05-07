package com.nanaios.polygonal_tech.core.register.single

import com.nanaios.polygonal_tech.core.register.registry.IRegistryObject
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraftforge.registries.ForgeRegistries
import java.util.*

/**
 * [Block]に限定して登録を管理し、登録されたオブジェクトを[ResourceLocation]から一元的に取得（検索）できるようにすることを目的とするクラス。
 * 内部で保持するキャッシュマップにより、IDをキーとして安全に各ブロックへアクセスできる。
 *
 * @param modId 対象のMODのID
 */
class DeferredSingleBlockRegister(modId: String): DeferredSingleRegister<Block>(
    ForgeRegistries.BLOCKS,modId, MAP
){
    companion object{
        private val MAP: MutableMap<ResourceLocation, IRegistryObject<out Block>> = mutableMapOf()

        /**
         * 登録済みの[Block]に関する[IRegistryObject]を、そのIDをキーとして取得制御することを目的とする。
         *
         * @param location 対象ブロックの識別IDである[ResourceLocation]
         * @return 対応する[Block]の[IRegistryObject]、存在しない場合は[null]を返す。
         */
        fun getBlockRegistryObject(location: ResourceLocation): IRegistryObject<out Block>? {
            Collections.unmodifiableMap(MAP)
            return MAP[location]
        }
    }
}