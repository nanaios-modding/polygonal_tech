package com.nanaios.polygonal_tech.core.util

/**
 * 単一の値を内包し、参照渡しのようにその状態を外部から変更できるようにすることを目的としたラッパークラス。
 * 主にコールバック処理内での値の更新や、クロージャの外側から値を変更させたい場合に利用される。
 *
 * @param T 保持する値の型
 * @property value ラップされている実際の値。初期値はnullであり、後から値を変更可能。
 */
data class PointerLike<T>(var value: T? = null)