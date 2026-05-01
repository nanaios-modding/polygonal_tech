package com.nanaios.polygonal_tech.core.mixin;

import com.nanaios.polygonal_tech.core.register.registry.IRegistryObject;
import net.minecraftforge.registries.RegistryObject;
import org.spongepowered.asm.mixin.Mixin;

/**
 * IRegistryObjectをRegistryObjectに対してmixinする
 * ここでは、MixinRegistryObjectをabstract classとして定義することで、IRegistryObjectに定義されたメソッドに対する呼び出しはRegistryObjectに対して行われるようになる
 * これにより、RegistryObjectをIRegistryObjectとして扱うことができるようになる
 * */
@Mixin(RegistryObject.class)
public abstract class MixinRegistryObject<T> implements IRegistryObject<T> {
}
