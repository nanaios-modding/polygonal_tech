package com.nanaios.polygonal_tech.core.register.single

import com.nanaios.polygonal_tech.core.register.IDeferredRegister
import net.minecraftforge.registries.DeferredRegister

/**
 * 単一の要素を登録するためのインターフェースです。
 * */
interface IDeferredSingleRegister<T> : IDeferredRegister<T> {
    /**
     * IDeferredSingleRegisterをDeferredRegisterにキャストして返します。
     * */
    val cast: DeferredRegister<T>
}