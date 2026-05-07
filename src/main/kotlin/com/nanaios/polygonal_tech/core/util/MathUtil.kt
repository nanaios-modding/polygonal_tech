package com.nanaios.polygonal_tech.core.util

/**
 * [Long]型の数値を、オーバーフロー・アンダーフローを防ぎつつ[Int]型に落とし込むことを目的とした拡張関数。
 * 例えば外部とのやり取りでInt型が必要な場面において、安全にキャストするために使用する。
 *
 * @return 変換後の[Int]型数値。元の値が[Int.MAX_VALUE]を上回っていれば[Int.MAX_VALUE]、[Int.MIN_VALUE]を下回っていれば[Int.MIN_VALUE]、それ以外であればそのまま[Int]にキャストされた値。
 */
fun Long.roundInt(): Int {
    return when {
        this >= Int.MAX_VALUE -> Int.MAX_VALUE
        this <= Int.MIN_VALUE -> Int.MIN_VALUE
        else -> this.toInt()
    }
}

/**
 * 2つの[Long]型の数値を足し合わせる際に、オーバーフロー・アンダーフローを起こさないための拡張関数。
 * 大きな数値を扱う処理（例：エネルギーの蓄積量など）において、上限や下限を超えて値が反転してしまうことを防ぐ目的で使用される。
 *
 * @param other この[Long]値に加算する、もう一方の[Long]型数値。
 * @return 加算結果の[Long]型数値。加算によって[Long.MAX_VALUE]を超える場合は[Long.MAX_VALUE]、[Long.MIN_VALUE]を下回る場合は[Long.MIN_VALUE]、安全に計算できる場合は通常に合算された値を返す。
 */
fun Long.nonOverflowAdd(other: Long) : Long {
    return when {
        this > 0 && other > 0 && Long.MAX_VALUE - this < other -> Long.MAX_VALUE
        this < 0 && other < 0 && Long.MIN_VALUE - this > other -> Long.MIN_VALUE
        else -> this + other
    }
}