package com.nanaios.polygonal_tech.core.register.single

import com.nanaios.polygonal_tech.core.register.registry.IRegistryObject
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.registries.ForgeRegistries

/** タイルエンティティのタイプを指すエイリアス */
typealias TileType<T> = BlockEntityType<T>
/** タイルエンティティ本体を指すエイリアス */
typealias Tile = BlockEntity

/**
 * [BlockEntity]のタイプ定義（[TileType]）を単一のレジストリとして登録・管理し、
 * 対応するブロックとタイルエンティティの紐づけを容易に行うことを目的とするクラス。
 *
 * @param modId MODの識別子
 */
class DeferredSingleTileTypeRegister(modId: String) : DeferredSingleRegister<TileType<*>>(
    ForgeRegistries.BLOCK_ENTITY_TYPES, modId
) {

    /**
     * 特定のブロックに対して紐づくタイルエンティティのタイプを登録し、
     * ゲーム内でのプロビジョニング構成を定義することを目的とするメソッド。
     *
     * @param I 登録するタイルエンティティの派生型
     * @param name タイルエンティティの登録名（通常、対応するブロックと同じ名称）
     * @param block このタイルエンティティを保持する対象の[Block]の[IRegistryObject]
     * @param sup レベル内でタイルエンティティのインスタンスを生成するために呼ばれる関数。[ResourceLocation], [BlockPos], [BlockState]を引数に取る。
     * @return ビルド・登録されたタイルエンティティタイプ（[TileType]）への[IRegistryObject]
     */
    fun <I : Tile> register(
        name: String,
        block: IRegistryObject<out Block>,
        sup: (ResourceLocation, BlockPos, BlockState) -> I
    ): IRegistryObject<TileType<I>> {
        return super.register(name) {
            BlockEntityType.Builder.of(
                { pos, state -> sup(block.id, pos, state) },
                block.get()
            ).build(null)
        }
    }

    /**
     * [Block]の登録ID（パス部分）を自動的に名前として借用し、対象ブロック向けのタイルエンティティを簡潔に登録する目的のショートカットメソッド。
     *
     * @param I タイルエンティティの型
     * @param block 紐づける[Block]の[IRegistryObject]
     * @param sup タイルエンティティ生成関数
     * @return 構築された[TileType]の[IRegistryObject]
     */
    fun <I : Tile> register(block: IRegistryObject<out Block>, sup: (ResourceLocation, BlockPos, BlockState) -> I) =
        register(
            block.id.path,
            block,
            sup
        )
}