package com.nanaios.polygonal_tech.main

import com.nanaios.polygonal_tech.main.registry.PolygonalTechMachineRegistry
import com.nanaios.polygonal_tech.main.registry.PolygonalTechMenuTypeRegistry
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
        PolygonalTechMachineRegistry.MACHINES.register(eventBus)
        PolygonalTechMenuTypeRegistry.MENUS.register(eventBus)
    }

    /**
     * コンソールやログファイル上に、MODのアスキーアートロゴおよびバージョン情報を表示し、起動を装飾的に知らせることを目的とするメソッド。
     *
     * @param context インストールされているMODのバージョン等のメタ情報を取得するために使用される[FMLJavaModLoadingContext]。
     */
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