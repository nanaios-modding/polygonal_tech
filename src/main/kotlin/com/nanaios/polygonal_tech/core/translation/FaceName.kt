package com.nanaios.polygonal_tech.core.translation

import com.nanaios.polygonal_tech.main.PolygonalTech
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.FormattedText
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Style
import java.util.Optional

/**
 * ブロック（機械など）の各面がどのような役割（入力、出力、入出力兼用など）を持っているのかを表す名前を、
 * 翻訳可能な[Component]として定義・提供することを目的とした列挙型。
 * [Component]インターフェースをデリゲートして実装することで、そのままチャットやGUIに表示可能なテキストコンポーネントとして振る舞う。
 *
 * @property component この列挙子が持つ、実際の翻訳データを持つ[Component]
 */
enum class FaceName(
    component: Component
): Component by component {
    INPUT_1(PolygonalTech.MOD_ID,"input_1"),
    INPUT_2(PolygonalTech.MOD_ID,"input_2"),
    INPUT_3(PolygonalTech.MOD_ID,"input_3"),
    INPUT_4(PolygonalTech.MOD_ID,"input_4"),
    INPUT_5(PolygonalTech.MOD_ID,"input_5"),
    OUTPUT_1(PolygonalTech.MOD_ID,"output_1"),
    OUTPUT_2(PolygonalTech.MOD_ID,"output_2"),
    OUTPUT_3(PolygonalTech.MOD_ID,"output_3"),
    OUTPUT_4(PolygonalTech.MOD_ID,"output_4"),
    OUTPUT_5(PolygonalTech.MOD_ID,"output_5"),
    INPUT_OUTPUT_1(PolygonalTech.MOD_ID,"input_output_1"),
    INPUT_OUTPUT_2(PolygonalTech.MOD_ID,"input_output_2"),
    INPUT_OUTPUT_3(PolygonalTech.MOD_ID,"input_output_3"),
    INPUT_OUTPUT_4(PolygonalTech.MOD_ID,"input_output_4"),
    INPUT_OUTPUT_5(PolygonalTech.MOD_ID,"input_output_5"),
    ;

    constructor(modId: String, name: String):this(Component.translatable("face.$modId.$name"))

    override fun getString(): String {
        return super.getString()
    }

    override fun getString(arg: Int): String {
        return super.getString(arg)
    }

    override fun plainCopy(): MutableComponent {
        return super.plainCopy()
    }

    override fun copy(): MutableComponent {
        return super.copy()
    }

    override fun <T> visit(consumer: FormattedText.StyledContentConsumer<T>, style: Style): Optional<T> {
        return super.visit(consumer, style)
    }

    override fun <T> visit(consumer: FormattedText.ContentConsumer<T>): Optional<T> {
        return super.visit(consumer)
    }

    override fun toFlatList(): List<Component> {
        return super.toFlatList()
    }

    override fun toFlatList(style: Style): List<Component> {
        return super.toFlatList(style)
    }

    override fun contains(component: Component): Boolean {
        return super.contains(component)
    }
}