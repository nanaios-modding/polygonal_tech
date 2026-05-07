package com.nanaios.polygonal_tech.main.registry

import com.nanaios.polygonal_tech.core.register.multi.DeferredMachineRegister
import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.world.level.block.Block

/**
 * MOD内で追加される機械類のブロック（[Block]）とそれに対応するタイルエンティティをひとまとめに登録・管理することを目的としたレジストリオブジェクト。
 */
object PolygonalTechMachineRegistry {
    /**
     * 機械の登録処理を委譲するためのレジストリインスタンス。これを介して実際のゲーム内要素を定義する。
     */
    val MACHINES = DeferredMachineRegister(PolygonalTech.MOD_ID)
}