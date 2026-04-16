package com.nanaios.polygonal_tech.lib.mixin;

import com.nanaios.polygonal_tech.lib.register.IDeferredRegister;
import com.nanaios.polygonal_tech.lib.register.IRegistryObject;
import com.nanaios.polygonal_tech.lib.util.PointerLike;
import kotlin.jvm.functions.Function1;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Supplier;

/**
 * IDeferredRegisterをDeferredRegisterに対してmixinする
 * ここでは、MixinDeferredRegisterをabstract classとして定義することで、IDeferredRegisterに定義されたメソッドに対する呼び出しはDeferredRegisterに対して行われるようになる
 * これにより、DeferredRegisterをIDeferredRegisterとして扱うことができるようになる
 * */
@Mixin(value = DeferredRegister.class,remap = false)
public abstract class MixinDeferredRegister<T> implements IDeferredRegister<T> {
    @Shadow
    public abstract <I extends T> RegistryObject<I> register(String name, Supplier<? extends I> sup);

    @Override
    @NotNull
    @SuppressWarnings("unchecked")
    public <I extends T> IRegistryObject<I> register(@NotNull String name, @NotNull Function1<? super @NotNull ResourceLocation, ? extends I> sup) {
        PointerLike<ResourceLocation> pointer = new PointerLike<>();
        RegistryObject<? extends I> registry = this.register(name,() -> sup.invoke(pointer.getPointer()));
        pointer.setPointer(registry.getId());
        return (IRegistryObject<I>)(Object) registry;
    }
}