package com.nanaios.polygonal_tech.core.client.gui

import com.nanaios.polygonal_tech.core.menu.MachineMenu
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

/**
 * サーバー側で定義された[MachineMenu]と紐付き、クライアント側でGUIを実際に描画・操作可能にすることを目的とする基底画面クラス。
 * インベントリ操作やデータの視覚化など、機械特有の操作パネルのベースとして使用される。
 *
 * @param M 描画対象となるメニューの型（[MachineMenu]の派生型）
 * @param menu コンテナのロジックやスロット配置を保持する[MachineMenu]
 * @param inventory クライアント上のプレイヤーインベントリ
 * @param title GUI上部に表示されるタイトルコンポーネント
 */
open class MachineScreen<M: MachineMenu>(
    menu:M,
    inventory: Inventory,
    title: Component,
): AbstractContainerScreen<M>(menu, inventory, title) {
    /**
     * GUIの背景領域を描画することを目的とするメソッド。
     * サブクラスでテクスチャなどの描画処理を記述する。
     *
     * @param guiGraphics 描画に使用される[GuiGraphics]
     * @param partialTick 前のTickからの経過割合（アニメーションの補間用）
     * @param mouseX マウスカーソルのX座標
     * @param mouseY マウスカーソルのY座標
     */
    override fun renderBg(guiGraphics: GuiGraphics,partialTick: Float, mouseX: Int, mouseY: Int) {
    }
}