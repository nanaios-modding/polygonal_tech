package com.nanaios.polygonal_tech.registries.impl;

import com.nanaios.polygonal_tech.menu.base.BaseMenu;
import com.nanaios.polygonal_tech.registries.base.WrapperDeferredRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class DeferredMenuTypeRegister extends WrapperDeferredRegister<MenuType<?>> {
    public DeferredMenuTypeRegister(DeferredRegister<MenuType<?>> deferredRegister) {
        super(deferredRegister);
    }

    @SuppressWarnings("unchecked")
    public <I extends MenuType<?>> RegistryObject<I> register(String name) {
        RegistryObject<I>[] pointer = new RegistryObject[1];
        RegistryObject<I> registryObject = (RegistryObject<I>) super.register(
                name,
                () -> IForgeMenuType.create((id, inv, buf) -> {
                    BlockPos pos = buf.readBlockPos();
                    return new BaseMenu<>(pointer[0].get(),id, inv, pos);
                })
        );

        pointer[0] = registryObject;
        return registryObject;
    }
}
