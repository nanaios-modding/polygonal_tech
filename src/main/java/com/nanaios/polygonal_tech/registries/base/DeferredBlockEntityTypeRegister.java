package com.nanaios.polygonal_tech.registries.base;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class DeferredBlockEntityTypeRegister extends WrapperDeferredRegister<BlockEntityType<?>> {

    public DeferredBlockEntityTypeRegister(DeferredRegister<BlockEntityType<?>> deferredRegister) {
        super(deferredRegister);
    }

    @Override
    public <I extends BlockEntityType<?>> RegistryObject<I> register(String name, Supplier<? extends I> sup) {
        return super.register(name, sup);
    }
}