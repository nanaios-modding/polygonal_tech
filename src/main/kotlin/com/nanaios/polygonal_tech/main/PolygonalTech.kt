package com.nanaios.polygonal_tech.main

import com.nanaios.polygonal_tech.main.registry.PolygonalTechBlockRegistry
import com.nanaios.polygonal_tech.main.registry.PolygonalTechItemRegistry
import com.nanaios.polygonal_tech.main.registry.PolygonalTechMachineRegistry
import com.nanaios.polygonal_tech.main.registry.PolygonalTechTileTypeRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(PolygonalTech.MOD_ID)
open class PolygonalTech(context: FMLJavaModLoadingContext) {
    companion object {
        const val MOD_ID = "polygonal_tech"
        val LOGGER: Logger = LogManager.getLogger(MOD_ID)
    }

    init {
        val eventBus = context.modEventBus
        showBootLog(context)
        PolygonalTechItemRegistry.ITEMS.register(eventBus)
        PolygonalTechBlockRegistry.MACHINE_BLOCKS.register(eventBus)
        PolygonalTechTileTypeRegistry.MACHINE_TILES.register(eventBus)
        PolygonalTechMachineRegistry.MACHINES.register(eventBus)
    }

    /**
     * 遊び心のある起動ログを表示するだけのメソッド
     * */
    private fun showBootLog(context: FMLJavaModLoadingContext) {
        // バージョンを取得してタイトルを生成
        val version = context.container.modInfo.version.toString()
        val title = "    P O L Y G O N A L   T E C H v$version    "

        // 上下のボーダー部分を生成
        val border = "═".repeat(title.length)
        val subTitle = "~~ Industrial Mod ~~"

        // サブタイトル部分を生成
        val centeredSubTitle = subTitle.padStart((subTitle.length + title.length) / 2).padEnd(title.length)

        LOGGER.info("""
            
            ╔$border╗
            ║$title║
            ║$centeredSubTitle║
            ╚$border╝
        """.trimIndent())
    }
}