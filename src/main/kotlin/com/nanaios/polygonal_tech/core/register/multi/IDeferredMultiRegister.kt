package com.nanaios.polygonal_tech.core.register.multi

import com.nanaios.polygonal_tech.core.register.IDeferredRegister

/**
 * 2つのIDeferredRegisterをまとめるためのinterface。
 * 例えば、ItemとBlockのIDeferredRegisterをまとめるために使用できます。
 * */
interface IDeferredMultiRegister<T1,T2>: IDeferredRegister<T1> {
    val secondRegister: IDeferredRegister<T2>
}