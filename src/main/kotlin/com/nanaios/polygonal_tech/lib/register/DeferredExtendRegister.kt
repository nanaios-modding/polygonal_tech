package com.nanaios.polygonal_tech.lib.register

import net.minecraftforge.registries.DeferredRegister

open class DeferredExtendRegister<T> private constructor(register: IDeferredRegister<T>): IDeferredRegister<T> by register {
    constructor(register: DeferredRegister<T>) : this(register.cast())
}