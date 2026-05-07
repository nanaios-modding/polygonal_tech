package com.nanaios.polygonal_tech.core.capability.io

import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

/**
 * 基本的な4つの入出力モード（なし、入力のみ、出力のみ、入出力両方）を列挙として定義し、
 * インタラクト時の制御やGUIでの設定に使用することを目的とする列挙型。
 *
 * @param modId 所属するMODのID
 * @param name 列挙の名称
 * @param canInput アイテム・流体・エネルギー等の搬入を許可するかどうか
 * @param canOutput アイテム・流体・エネルギー等の搬出を許可するかどうか
 */
enum class IOMode(
    modId: String,
    name: String,
    val canInput:Boolean,
    val canOutput:Boolean,
): IIOMode {
    NONE(PolygonalTech.MOD_ID,"none", false, false),
    INPUT(PolygonalTech.MOD_ID,"input", true, false),
    OUTPUT(PolygonalTech.MOD_ID,"output", false, true),
    INPUT_OUTPUT(PolygonalTech.MOD_ID,"input_output", true, true);

    override val id: ResourceLocation = ResourceLocation.fromNamespaceAndPath(modId, "io_mode/$name")
    override val translation: MutableComponent = Component.translatable("io_mode.${PolygonalTech.MOD_ID}.$name")
}