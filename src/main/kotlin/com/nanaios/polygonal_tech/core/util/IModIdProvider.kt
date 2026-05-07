package com.nanaios.polygonal_tech.core.util

/**
 * 各種要素（RegisterやBlockなど）が所属しているmodIdを提供することを目的としたinterface。
 * このインターフェイスを実装することで、一元的に所属先MODを管理・取得可能にする。
 */
interface IModIdProvider {
    /**
     * この[IModIdProvider]が属しているMODのID。
     * リソースの登録時やResourceLocationの生成時に使用される。
     */
    val modId: String
}