package com.nanaios.polygonal_tech.lib.mixin;

import com.nanaios.polygonal_tech.lib.register.IDeferredRegister;
import net.minecraftforge.registries.DeferredRegister;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DeferredRegister.class)
public abstract class MixinDeferredRegister<T> implements IDeferredRegister<T> {
}