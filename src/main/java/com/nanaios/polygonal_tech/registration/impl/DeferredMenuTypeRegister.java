package com.nanaios.polygonal_tech.registration.impl;

import com.nanaios.polygonal_tech.menu.base.BaseMenu;
import com.nanaios.polygonal_tech.registration.base.WrapperDeferredRegister;
import com.nanaios.polygonal_tech.util.NamedToken;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class DeferredMenuTypeRegister extends WrapperDeferredRegister<MenuType<?>> {
    private static final Map<NamedToken, RegistryObject<MenuType<?>>> REGISTRY_OBJECT_MAP = new HashMap<>();
    private static final Map<RegistryObject<MenuType<?>>,NamedToken> REVERSE_REGISTRY_OBJECT_MAP = new HashMap<>();

    public DeferredMenuTypeRegister(DeferredRegister<MenuType<?>> deferredRegister) {
        super(deferredRegister, REGISTRY_OBJECT_MAP, REVERSE_REGISTRY_OBJECT_MAP);
    }

    @SuppressWarnings("unchecked")
    public <I> RegistryObject<I> register(NamedToken name) {
        return (RegistryObject<I>) super.register(
                name,
                () -> IForgeMenuType.create((id, inv, buf) -> {
                    BlockPos pos = buf.readBlockPos();
                    return new BaseMenu<>(registryObjectMap.get(name).get(), id, inv, pos);
                }));
    }


    /// tokenに対応するRegistryObjectを返す
    public static RegistryObject<MenuType<?>> getRegistry(NamedToken token) {
        return REGISTRY_OBJECT_MAP.get(token);
    }
}
