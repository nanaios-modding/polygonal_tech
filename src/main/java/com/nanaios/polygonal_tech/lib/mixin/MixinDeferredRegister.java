package com.nanaios.polygonal_tech.lib.mixin;

import com.nanaios.polygonal_tech.lib.register.IDeferredRegister;
import net.minecraftforge.registries.DeferredRegister;
import org.spongepowered.asm.mixin.Mixin;

/**
 * IDeferredRegisterをDeferredRegisterに対してmixinする
 * ここでは、MixinDeferredRegisterをabstract classとして定義することで、IDeferredRegisterに定義されたメソッドに対する呼び出しはDeferredRegisterに対して行われるようになる
 * これにより、DeferredRegisterをIDeferredRegisterとして扱うことができるようになる
 * */
@Mixin(DeferredRegister.class)
public abstract class MixinDeferredRegister<T> implements IDeferredRegister<T> {
}